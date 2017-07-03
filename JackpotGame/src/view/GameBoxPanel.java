package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.DiceResult;
import model.GameEngine;
import model.GameEngine.FlipTileOutcome;
import model.GameEngine.GameState;



@SuppressWarnings("serial")
public class GameBoxPanel extends JPanel{
	
	private static final int NUM_TILES = 9;
	private static final int TILE_WIDTH = 60;
	private static final int TILE_HEIGHT = 80;
	//width of the outer edge surrounding the game box.
	private static final int EDGE_WIDTH = 20;
	private static final int GAME_BOX_WIDTH = 580;
	//the gap to be put between the two die when displaying them.
	private static final int DICE_GAP = 20;
	//y coordinate of the top of where the rectangle of colored felt starts.
	private static final int FELT_Y_COR = 98;
	//y coordinate for flipped tile. 
	private static final int FLIPPED_TILE_Y_COR = 5;
	//size of the y dimension in felt rectangle;
	private static final int FELT_Y_SIZE = 222;
	//initial panel height and width, used to determine image scaling.
	private static final int INITIAL_PANEL_HEIGHT = 696; 
	private static final int INITIAL_PANEL_WIDTH = 349;
	//number of different dice values generated during dice roll for animated dice simulation.
	private static final int NUM_DICE_VALUES = 5;
	//delay between the display of each dice value during dice roll animation (in milliseconds).
	private static final int DELAY = 60;
	//image to represent the game box on which the game is played.
	private BufferedImage gameBoximage;
	//images of tiles with numbers on them, ie before being flipped.
	private BufferedImage startTileImages[];
	//images of the tiles when flipped over, spelling ".JACKPOT.".
	private BufferedImage flippedTileImages[];
	//images of the numbered tiles to be displayed when mouse cursor is over the tile.
	private BufferedImage mouseOverTileImages[];
	//transform used to scale the gameBox Image when window is re-sized.
	private AffineTransform scaleTransform;
	//gameBoxPanels copy of the tiles' states - updated when flipTile() called by gameEngine.
	private boolean tilesState[];
	private DieImage dieImage1, dieImage2;
	private GridBagConstraints emptyLabelConstraints, instructionPanelConstraints;
	private JPanel instructionPanel;
	private JLabel instructionLabel, instructionLabel2, errorLabel;
	//left position within the Panel that the gameBox will be drawn. 
	private int leftOffset = 0;
	//x and y coordinates for dice positions, both dice have the same y coordinates.
	private int die1_xCor, die2_xCor, die_yCor = 150;
	//player instructions for display
	private String[] instructions = { 	"Click on dice to roll", 
										"Click on a numbered tile to flip it",
										"You Win!", 
										"You Lose.", 
										"No More Moves Possible."};
	private String errorMsg = "Invalid tile Selection";
	
	public enum FeltColor { BLUE, GREEN, RED, BLACK };
	//Contains the players current felt color preference.
	private FeltColor colorChoice;
	private int lastMouseOver = -1;
	private boolean showInstructions = true;
	private boolean showAnimation = true;
	private boolean autoDiceRoll = false;
	private boolean showErrorMessage = true;
	private JButton newGameButton;
	private GameEngine gameEngine;
	
	
	public GameBoxPanel(GameEngine gameEngine) {
		
		this.gameEngine = gameEngine;
		tilesState = new boolean[NUM_TILES];
		//All tiles start off un-flipped.
		Arrays.fill(tilesState, false);
		colorChoice = FeltColor.BLUE;
		dieImage1 = new DieImage(1);
		dieImage2 = new DieImage(1);
		try {                
			gameBoximage = ImageIO.read(new File("images/gameBox.png"));
	    } catch (IOException ex) {
	        System.err.println("An error occurred: GameBox image file could not be opened.");
	        System.exit(1);
	    }
		startTileImages = new BufferedImage[NUM_TILES];
		mouseOverTileImages = new BufferedImage[NUM_TILES];
		flippedTileImages = new BufferedImage[NUM_TILES];
		try {
			for(int i = 0; i < startTileImages.length; ++i){
				startTileImages[i] = ImageIO.read(new File("images/tile" + (i+1) + ".png"));
				mouseOverTileImages[i] = ImageIO.read(new File("images/tile" + (i+1) + "s.png"));
			}
			String tileLetters[] = { "Dot", "J", "A", "C","K", "P", "O", "T" };
			for(int i = 0; i < tileLetters.length; ++i){
				String fileName = "images/tile" + tileLetters[i] + ".png";
				flippedTileImages[i] = ImageIO.read(new File(fileName));
			}
			//the last tile is the same image as the first (tile with a dot on it).
			flippedTileImages[8] = flippedTileImages[0];
			
		} catch (IOException ex) {
	        System.err.println("An error occurred: tile image file could not be opened.");
	        System.exit(1);
	    }
		GameBoxListener listener = new GameBoxListener();
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		createInstructionPanel();
		this.setLayout(new GridBagLayout());
		
		//As a bit of a hack, an empty label (not visible) is added above the 
		//instruction Panel and weighted so that the instructionPanel appears in the 
		//desired position.
		emptyLabelConstraints = new GridBagConstraints();
		instructionPanelConstraints = new GridBagConstraints();
		
		emptyLabelConstraints.weighty = 0.7;
		emptyLabelConstraints.fill = GridBagConstraints.VERTICAL;
		emptyLabelConstraints.gridx = 0;
		emptyLabelConstraints.gridy = 0;
		
		instructionPanelConstraints.weighty = 0.20;
		instructionPanelConstraints.fill = GridBagConstraints.VERTICAL;
		instructionPanelConstraints.gridx = 0;
		instructionPanelConstraints.gridy = 1;
		
		this.add(new JLabel(""),emptyLabelConstraints);
		this.add(instructionPanel,instructionPanelConstraints);
	}
	
	private void createInstructionPanel(){
		instructionPanel = new JPanel();
		instructionLabel = new JLabel(instructions[0]);
		instructionLabel.setForeground(Color.WHITE);
		Font instructionFont = new Font("SansSerif",Font.BOLD,18);
		instructionLabel.setFont(instructionFont);
		instructionLabel.setAlignmentX(CENTER_ALIGNMENT);
		
		instructionLabel2 = new JLabel(instructions[4]);
		instructionLabel2.setForeground(Color.WHITE);
		instructionLabel2.setFont(instructionFont);
		instructionLabel2.setAlignmentX(CENTER_ALIGNMENT);
		instructionLabel2.setVisible(false);
		
		errorLabel = new JLabel(errorMsg);
		errorLabel.setAlignmentX(CENTER_ALIGNMENT);
		errorLabel.setForeground(Color.getHSBColor(0.65f, 0.15f, 0.55f));
		errorLabel.setFont(instructionFont);
		errorLabel.setVisible(false);
		
		newGameButton = new JButton("New Game");
		newGameButton.setAlignmentX(CENTER_ALIGNMENT);
		//newGameButton should only be visible when game is over.
		newGameButton.setVisible(false);
		newGameButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameEngine.newGame();
			}
		});
		instructionPanel.setOpaque(false);
		instructionPanel.setLayout(new BoxLayout(instructionPanel,BoxLayout.Y_AXIS));
		
		Dimension dim2 = new Dimension(0,20);
		Dimension dim = new Dimension(0,5);
		//empty filler components are added to ensure space between components.
		instructionPanel.add(new Box.Filler(dim2,dim2,dim2));
		instructionPanel.add(errorLabel);
		instructionPanel.add(instructionLabel);
		instructionPanel.add(instructionLabel2);
		instructionPanel.add(new Box.Filler(dim, dim, dim));
		instructionPanel.add(newGameButton);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {

		int width = getParent().getWidth();
		int height = getHeight();
		
		//Images of gameBox, tiles, and dice will be drawn on top of bufferedImage.
		//After finished drawing on bufferedImage, bufferedImage is drawn onto GameBoxPanel.
		//Size of drawn images will be scaled up or down based on size of Panel.
		//ScaleFactor computed such that aspect ratio of gameBox will be retained.
		//An affineTransform is used to achieve the scaling, as it transforms 
		//original coordinates to scaled coordinates.
		
		BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) bufferedImage.createGraphics();
		double scaleFactor = computeScaleFactor(width, height);
		scaleTransform = new AffineTransform();
		scaleTransform.scale(scaleFactor, scaleFactor);
		g2d.setTransform(scaleTransform);
		g2d.drawImage(gameBoximage,0,0,this);
		//The felt color is blue by default on the loaded image. 
		//For other colors it must be drawn over.
		int feltRectWidth = GAME_BOX_WIDTH - 2 * EDGE_WIDTH;
		
		switch(colorChoice){
		case GREEN: 
			g2d.setColor(Color.getHSBColor(0.3f, 0.82f, 0.40f));
			g2d.fillRect(EDGE_WIDTH, FELT_Y_COR, feltRectWidth, FELT_Y_SIZE);
			break;
		case RED:
			g2d.setColor(Color.getHSBColor(0.02f, 0.93f, 0.7f));
			g2d.fillRect(EDGE_WIDTH, FELT_Y_COR, feltRectWidth, FELT_Y_SIZE);
			break;
		case BLACK:
			g2d.setColor(Color.getHSBColor(0.65f, 0.06f, 0.17f));
			g2d.fillRect(EDGE_WIDTH, FELT_Y_COR, feltRectWidth, FELT_Y_SIZE);
			break;
		default:
			break;
		}
		//draw in our dice.
		die1_xCor = (GAME_BOX_WIDTH / 2) - dieImage1.getDiceSize() - (DICE_GAP / 2);
		die2_xCor = die1_xCor + dieImage2.getDiceSize() + DICE_GAP;
		g2d.drawImage(dieImage1, die1_xCor, die_yCor, this);
		g2d.drawImage(dieImage2, die2_xCor, die_yCor, this);
		//draw in the tiles, one by one, next to each other.
		for(int i = 0; i < tilesState.length; ++i){
			int xCor = EDGE_WIDTH + i * TILE_WIDTH;
			if(tilesState[i] == false){
				g2d.drawImage(startTileImages[i], xCor, EDGE_WIDTH, this);
			}
			else{
				//flipped tiles need to appear higher-up then non-flipped tiles.
				g2d.drawImage(flippedTileImages[i], xCor, FLIPPED_TILE_Y_COR, this);
			}
		}
		//if mouse is currently over a tile, and tile is not flipped, override image with 
		//mouse-over tile image.
		if(lastMouseOver >= 0 && tilesState[lastMouseOver] == false && 
				gameEngine.getGameState() == GameState.POST_DICE_ROLL){
			int xCor = EDGE_WIDTH + lastMouseOver * TILE_WIDTH;
			g2d.drawImage(mouseOverTileImages[lastMouseOver], xCor, EDGE_WIDTH, this);
		}
		super.paintComponent(g);
		//calculate left coordinate such that gameBox image is centred on panel.
		leftOffset = (int) ((width - (scaleFactor * GAME_BOX_WIDTH))/2 );
		g.drawImage(bufferedImage,leftOffset,0,this);
	}
	
	public void setColorChoice(FeltColor color){
		colorChoice = color;
		this.repaint();
	}

	@Override
	public Dimension getMaximumSize() {
		Container parent = this.getParent();
		int width = parent.getWidth();
	    return new Dimension(width, (int) (width/2.0) );
	}
	
	@Override
	public Dimension getPreferredSize() {
		Container parent = this.getParent();
		int width = parent.getWidth();
	    return new Dimension(width, (int) (width/2.0) );
	}

	public void updateDiceFinal(DiceResult dice) {
		dieImage1.setDieValue(dice.getDie1());
		dieImage2.setDieValue(dice.getDie2());
		instructionLabel.setText(instructions[1]);
		repaint();
	}
	
	public void updateDiceIntermediate(DiceResult dice) {
		dieImage1.setDieValue(dice.getDie1());
		dieImage2.setDieValue(dice.getDie2());
		repaint();
	}

	public void flipTile(int tileNum) {
		//if dice rolled automatically, no need to give roll dice instruction.
		if(!autoDiceRoll)
			instructionLabel.setText(instructions[0]);
		else
			instructionLabel.setText("");
		tilesState[tileNum - 1] = true;
		this.repaint();
	}

	public void gameLostUpdate() {
		instructionLabel.setText(instructions[3]);
		//even if player has chosen to hide instructions, still want to 
		//display "You lose" message to player, so must set label to visible.
		instructionLabel.setVisible(true);
		instructionLabel2.setVisible(true);
		newGameButton.setVisible(true);
		this.repaint();
	}

	public void gameWonUpdate() {
		//even if player has chosen to hide instructions, still want to
		//display winning message to player.
		instructionLabel.setVisible(true);
		instructionLabel.setText(instructions[2]);
		newGameButton.setVisible(true);
		this.repaint();
		
	}

	public void newGameUpdate() {
		Arrays.fill(tilesState, false);
		if(!autoDiceRoll)
			instructionLabel.setText(instructions[0]);
		else
			instructionLabel.setText("");
		if(showInstructions == false){
			instructionLabel.setVisible(false);
		}
		instructionLabel2.setVisible(false);
		newGameButton.setVisible(false);
		this.repaint();
		if(autoDiceRoll)
			rollDice();
	}
	
	public void scaleText() {
		double scaleFactor = computeScaleFactor(getParent().getWidth(), getHeight());
		int textSize = (int) Math.ceil(Math.max(8, 18 * scaleFactor));
		int buttonTextSize = (int) Math.ceil(Math.max(6, 14 * scaleFactor));
		instructionLabel.setFont(new Font("SansSerif",Font.BOLD,textSize));
		instructionLabel2.setFont(new Font("SansSerif",Font.BOLD,textSize));
		errorLabel.setFont(new Font("SansSerif",Font.BOLD,textSize));
		newGameButton.setFont(new Font("SansSerif",Font.BOLD,buttonTextSize));
	}
	
	private double computeScaleFactor(int width, int height){
		return Math.min( ( (double) width / INITIAL_PANEL_HEIGHT), 
				( (double) height/ INITIAL_PANEL_WIDTH));
	}
	
	public void setShowInstructions(boolean state){
		showInstructions = state;
		if(showInstructions){
			instructionLabel.setVisible(true);
		}
		else{
			String currentLabelText = instructionLabel.getText();
			//if not currently displaying win/loss message.
			if(!(currentLabelText == instructions[2] || currentLabelText == instructions[3]))
				instructionLabel.setVisible(false);
		}
	}
	
	public void setShowErrorMessage(boolean state) {
		showErrorMessage = state;
	}
	
	public void setShowAnimation(boolean state){
		showAnimation = state;
	}
	
	public void setAutoDiceRoll(boolean state){
		autoDiceRoll = state;
	}
	
	public boolean getShowInstructions(){
		return showInstructions;
	}
	
	public boolean getShowErrorMessage(){
		return showErrorMessage;
	}
	
	public boolean getShowAnimation(){
		return showAnimation;
	}
	
	public boolean getAutoDiceRoll(){
		return autoDiceRoll;
	}
	
	private void rollDice(){
		if(showAnimation)
			gameEngine.rollDice(NUM_DICE_VALUES,DELAY);
		else
			gameEngine.rollDice();
	}
	

	
/*	public void saveSettings(Properties settings) {
		settings.setProperty("showInstructions", String.valueOf(showInstructions));
		settings.setProperty("showAnimation", String.valueOf(showAnimation));
		settings.setProperty("autoDiceRoll", String.valueOf(autoDiceRoll));
		settings.setProperty("showErrorMessage", String.valueOf(showErrorMessage));
	}*/


	
	private class GameBoxListener extends MouseAdapter{
		
		//de-scaled mouse coordinates
		int x,y;
		
		public void mouseClicked(MouseEvent e){
			
			//have to subtract offset to account for space on the left and work out 
			//position on the actual gameBox image.  Then must convert
			//scaled image coordinates to original image coordinates.
			unscaleCoords(e.getX() - leftOffset, e.getY());
			//check if clicked on a tile.
			if(y > EDGE_WIDTH && y < TILE_HEIGHT + EDGE_WIDTH){
				for(int i = 0; i < NUM_TILES; i++){
					if(x > EDGE_WIDTH + i * TILE_WIDTH  && x < EDGE_WIDTH + (i+1) * TILE_WIDTH){
						if(gameEngine.flipTile(i+1) == FlipTileOutcome.INVALID_MOVE 
								&& showErrorMessage == true)
							errorLabel.setVisible(true);
						else
							errorLabel.setVisible(false);
						if(autoDiceRoll)
							rollDice();
						break;
					}
				}
			}
			//check if clicked on dice.
			else if(y >= die_yCor && y<= die_yCor + dieImage1.getDiceSize()  
					&& x >= die1_xCor && x <= die2_xCor + dieImage2.getDiceSize()){
					
				if(showAnimation)
					rollDice();
			}
		}
		
		public void mouseMoved(MouseEvent e){
			int currentMouseOver = - 1;
			unscaleCoords(e.getX() - leftOffset, e.getY());
			if(y > EDGE_WIDTH && y < TILE_HEIGHT + EDGE_WIDTH){
				for(int i = 0; i < NUM_TILES; i++){
					if(x > EDGE_WIDTH + i * TILE_WIDTH 
							&& x <  EDGE_WIDTH + (i+1) * TILE_WIDTH){
						currentMouseOver = i;
						
					}
				}
			}
			if(currentMouseOver != lastMouseOver){
				lastMouseOver = currentMouseOver;
				GameBoxPanel.this.repaint();
			}
		}
		
		private void unscaleCoords(int scaledX, int scaledY){
			try{
				AffineTransform inverseTransform = scaleTransform.createInverse();
				Point deScaled = new Point();
				inverseTransform.transform(new Point(scaledX, scaledY), deScaled);
				x = deScaled.x;
				y = deScaled.y;
			//This exception can never happen since our simple Transform will 
			//always have an inverse.
			} catch (NoninvertibleTransformException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}



	
}
