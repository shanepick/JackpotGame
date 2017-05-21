package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import model.DiceResult;
import model.GameEngine;



@SuppressWarnings("serial")
public class GameBoxPanel extends JPanel implements Display{
	
	private BufferedImage gameBoximage;
	private BufferedImage startTileImages[];
	private BufferedImage flippedTileImages[];
	public static final int TILE_WIDTH = 60;
	public static final int TILE_HEIGHT = 80;
	public static final int EDGE_WIDTH = 20;
	private static final int DICE_GAP = 20;
	private AffineTransform scaleTransform;
	private boolean tilesState[];
	private DieView dieImage1, dieImage2;
	private GridBagConstraints c = new GridBagConstraints();
	private GridBagConstraints d = new GridBagConstraints();
	private JPanel instructionPanel;
	private JLabel instructionLabel, instructionLabel2;
	private int leftOffset = 0;
	private int die1_xCor, die2_xCor, die_yCor = 150;
	private String[] instructions = { "Click on dice to roll", "Click on a numbered tile to flip it",
			"You Win!", "You Lose.", "No More Moves Possible"};
	public enum FeltColor { BLUE, GREEN, RED, BLACK };
	private FeltColor colorChoice;
	private JButton newGameButton;
	private GameEngine gameEngine;
	
	
	public GameBoxPanel(GameEngine gameEngine) {
		
		this.gameEngine = gameEngine;
		tilesState = new boolean[GUI.NUM_TILES];
		colorChoice = FeltColor.BLUE;
		Arrays.fill(tilesState, false);
		dieImage1 = new DieView(1);
		dieImage2 = new DieView(1);
		try {                
			gameBoximage = ImageIO.read(new File("images/board.png"));
	    } catch (IOException ex) {
	        System.err.println("An error occurred: GameBox image file could not be opened.");
	        System.exit(1);
	    }
		startTileImages = new BufferedImage[GUI.NUM_TILES];
		flippedTileImages = new BufferedImage[GUI.NUM_TILES];
		try {
			for(int i = 0; i < startTileImages.length; ++i){
				startTileImages[i] = ImageIO.read(new File("images/tile" + (i+1) + ".png"));
			}
			flippedTileImages[0] = ImageIO.read(new File("images/tileDot.png"));
			//TODO change filename
			flippedTileImages[1] = ImageIO.read(new File("images/test.png"));
			flippedTileImages[2] = ImageIO.read(new File("images/tileA.png"));
			flippedTileImages[3] = ImageIO.read(new File("images/tileC.png"));
			flippedTileImages[4] = ImageIO.read(new File("images/tileK.png"));
			flippedTileImages[5] = ImageIO.read(new File("images/tileP.png"));
			flippedTileImages[6] = ImageIO.read(new File("images/tileO.png"));
			flippedTileImages[7] = ImageIO.read(new File("images/tileT.png"));
			flippedTileImages[8] = flippedTileImages[0];
			
		} catch (IOException ex) {
	        System.err.println("An error occurred: tile image file could not be opened.");
	        System.exit(1);
	    }
		//this.setSize(new Dimension(580,340));
		this.addMouseListener(new TileListener());
		//this.setBackground(Color.cyan);
		instructionLabel = new JLabel(instructions[0]);
		//instructionLabel.setVerticalAlignment(JLabel.NORTH);
		//instructionLabel.setBackground(Color.MAGENTA);
		//instructionLabel.setOpaque(true);
		instructionLabel.setForeground(Color.WHITE);
		instructionLabel.setFont(new Font("SansSerif",Font.BOLD,18));
		instructionLabel.setAlignmentX(CENTER_ALIGNMENT);
		instructionLabel2 = new JLabel(instructions[4]);
		instructionLabel2.setForeground(Color.WHITE);
		instructionLabel2.setFont(new Font("SansSerif",Font.BOLD,18));
		instructionLabel2.setAlignmentX(CENTER_ALIGNMENT);
		instructionLabel2.setVisible(false);
		newGameButton = new JButton("New Game");
		newGameButton.setAlignmentX(CENTER_ALIGNMENT);
		newGameButton.setVisible(false);
		newGameButton.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameEngine.newGame();
			}
		});
		
		//instructionLabel2 = newJLabel("")
		
		instructionPanel = new JPanel();
		instructionPanel.setBackground(Color.YELLOW);
		instructionPanel.setOpaque(false);
		instructionPanel.setLayout(new BoxLayout(instructionPanel,BoxLayout.Y_AXIS));
		instructionPanel.add(instructionLabel);
		instructionPanel.add(instructionLabel2);
		instructionPanel.add(newGameButton);
		
		
		this.setLayout(new GridBagLayout());
		//this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		//this.setLayout(null);
		//test.setBackground(Color.BLACK);
		//test.setPreferredSize(new Dimension(200,100));
		
		c.weighty = 0.7;
		//c.anchor = GridBagConstraints.PAGE_END;
		//c.insets = new Insets(200,30,30,30);
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		
		d.weighty = 0.2;
		d.fill = GridBagConstraints.VERTICAL;
		d.gridx = 0;
		d.gridy = 1;
		//this.add(test,c);
		this.add(new JLabel(""),c);
		this.add(instructionPanel,d);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		//perhaps change to paint everything to buffered image first. 
		int height, width;
		double scaleFactor;
		height = getHeight();
		width = getWidth();
		//Graphics2D g2d = (Graphics2D) g;
		BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) bufferedImage.createGraphics();
		//super.paintComponent(g2d);

		//g2d.drawImage(image,60,0,this);
		System.out.println("width is " +width);
		System.out.println("height is: " +height);
		scaleFactor = Math.min( (width / 698.0), (height/349.0));
		System.out.println("scale factor is " + scaleFactor);
		g2d.scale(scaleFactor,scaleFactor);
		scaleTransform = new AffineTransform();
		scaleTransform.scale(scaleFactor, scaleFactor);
		g2d.setTransform(scaleTransform); 
		g2d.drawImage(gameBoximage,0,0,this);
		switch(colorChoice){
		case GREEN: 
			g2d.setColor(Color.getHSBColor(0.35f, 0.82f, 0.45f));
			g2d.fillRect(20, 97, 540, 223);
			break;
		case RED:
			g2d.setColor(Color.getHSBColor(0.036f, 0.90f, 0.51f));
			g2d.fillRect(20, 97, 540, 223);
			break;
		case BLACK:
			g2d.setColor(Color.getHSBColor(0.65f, 0.06f, 0.17f));
			g2d.fillRect(20, 97, 540, 223);
			break;
		default:
			break;
		}

		die1_xCor = (580/2) - DieView.DICESIZE - (DICE_GAP/ 2);
		System.out.println(die1_xCor);
		die2_xCor = die1_xCor + DieView.DICESIZE + DICE_GAP;
		System.out.println(die2_xCor);
		g2d.drawImage(dieImage1,die1_xCor,die_yCor,this);
		g2d.drawImage(dieImage2,die2_xCor,die_yCor,this);
		for(int i = 0; i < tilesState.length; ++i){
			int xCor = EDGE_WIDTH + i * TILE_WIDTH;
			if(tilesState[i] == false)
				g2d.drawImage(startTileImages[i], xCor, EDGE_WIDTH, this);
			else
				g2d.drawImage(flippedTileImages[i], xCor, EDGE_WIDTH -14, this);
		}
		//c.insets = new Insets( (int) scaleFactor * 200, (int) scaleFactor * 30, (int) scaleFactor * 30, (int) scaleFactor * 30);
		//revalidate();
		super.paintComponent(g);
		leftOffset = (int) ((width - (scaleFactor * 580))/2 );
		System.out.println("leftOffset is" + leftOffset);
		g.drawImage(bufferedImage,leftOffset,0,this);
		
		//Point dicePanelDimensions = new Point();
		//scaleTransform.transform(new Point(250,60),dicePanelDimensions);
		//test.setSize(dicePanelDimensions.x,dicePanelDimensions.y);
		
	}
	
	public void setColorChoice(FeltColor color){
		colorChoice = color;
		this.repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		//Dimension test = super.getPreferredSize();
		//System.out.print("PreferredSize is" + test);
		//return test;
		Container parent = this.getParent();
		int width = parent.getWidth();
	    return new Dimension(width, (int) (width/2.0) );
	}
	
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	@Override
	public void updateDiceIntermediate(DiceResult dice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDiceFinal(DiceResult dice) {
		dieImage1.setDieValue(dice.getDie1());
		dieImage2.setDieValue(dice.getDie2());
		instructionLabel.setText(instructions[1]);
		repaint();
		
	}

	@Override
	public void flipTile(int tileNum) {
		instructionLabel.setText(instructions[0]);
		tilesState[tileNum - 1] = true;
		this.repaint();
	}

	@Override
	public void gameLostUpdate() {
		instructionLabel.setText(instructions[3]);
		instructionLabel2.setVisible(true);
		newGameButton.setVisible(true);
		this.repaint();
	}

	@Override
	public void gameWonUpdate() {
		instructionLabel.setText(instructions[2]);
		newGameButton.setVisible(true);
		this.repaint();
		
	}

	@Override
	public void newGameUpdate() {
		Arrays.fill(tilesState, false);
		instructionLabel.setText(instructions[0]);
		instructionLabel2.setVisible(false);
		newGameButton.setVisible(false);
		this.repaint();
	}
	
	private class TileListener extends MouseAdapter{

		public void mouseClicked(MouseEvent e){
			AffineTransform inverseTransform;
			try {
				inverseTransform = scaleTransform.createInverse();
				Point deScaled = new Point();
				inverseTransform.transform(new Point(e.getX() - leftOffset,e.getY()), deScaled);
				int x = deScaled.x;
				int y = deScaled.y;
				System.out.println(x + "," + y);
				int tileSelected = 0;
				//TODO change to left click only.
				
				//int panelWidth = gameBoxPanel.getWidth();
				if(y > EDGE_WIDTH && y < TILE_HEIGHT + EDGE_WIDTH){
					for(int i = 0; i < GUI.NUM_TILES; i++){
						if(x > 20 + i * TILE_WIDTH && x <  20 + (i+1) * TILE_WIDTH){
							gameEngine.flipTile(i+1);
							break;
						}
					}
				}
				else if(y >= die_yCor && y<= die_yCor + DieView.DICESIZE  
						&& x >= die1_xCor && x <= die2_xCor + DieView.DICESIZE){
					gameEngine.rollDice();

				}
				//System.out.println("tile selected was " + tileSelected);
			} catch (NoninvertibleTransformException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}


	
	
}
