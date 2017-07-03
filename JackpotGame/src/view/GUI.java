package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import model.DiceResult;
import model.GameEngine;
import view.GameBoxPanel.FeltColor;
import view.HelpScreen.Tab;

@SuppressWarnings("serial")
public class GUI extends JFrame implements Display{
	
	private static final int FRAME_WIDTH = 700, FRAME_HEIGHT = 540;
	private static final String FILENAME = "game_settings.txt";
	private GameBoxPanel gameBoxPanel;
	private GameEngine gameEngine;
	private JMenuBar menu;
	private StatsPanel statsPanel;
	//Panel to combine gameBoxPanel and statsPanel into the one panel
	private JPanel combinedPanel;
	private int lastWidth, lastHeight, last_xCor, last_yCor;
	private JLabel title;
	
	
	public GUI(GameEngine gameEngine){
		super("Jackpot Game");
		this.gameEngine = gameEngine;
		gameBoxPanel = new GameBoxPanel(gameEngine);
		gameBoxPanel.setAlignmentX(CENTER_ALIGNMENT);
		statsPanel = new StatsPanel();;
		if(loadSettings()==false){
			this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
			this.setLocationByPlatform(true);
		}
		menu = new GameMenuBar(gameEngine, this);
		title = new JLabel("JACKPOT GAME");
		title.setFont(new Font("serif",0,34));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBorder(new EmptyBorder(30,30,30,30));
		combinedPanel = new JPanel();
		combinedPanel.setLayout(new BoxLayout(combinedPanel,BoxLayout.Y_AXIS));
		combinedPanel.add(gameBoxPanel);
		combinedPanel.add(statsPanel);
		Container contentPane = this.getContentPane();
		contentPane.add(title,BorderLayout.NORTH);
		contentPane.add(combinedPanel);
		this.setJMenuBar(menu);
		GUI_Listener listener = new GUI_Listener();
		this.addWindowListener(listener);
		this.addWindowStateListener(listener);
		this.addComponentListener(new resizeListener());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameEngine.addDisplay(this);
		this.setVisible(true);
		gameBoxPanel.newGameUpdate();
			
	}
	
	public GameBoxPanel getGameBoxPanel(){
		return gameBoxPanel;
	}

	@Override
	public void updateDiceIntermediate(DiceResult dice) {
		gameBoxPanel.updateDiceIntermediate(dice);
	}

	@Override
	public void updateDiceFinal(DiceResult dice) {
		gameBoxPanel.updateDiceFinal(dice);
		
	}

	@Override
	public void flipTile(int tileNum) {
		gameBoxPanel.flipTile(tileNum);
		
	}

	@Override
	public void gameLostUpdate() {
		gameBoxPanel.gameLostUpdate();
		statsPanel.updateStats(gameEngine.getNumWins(), gameEngine.getNumLosses());
	}

	@Override
	public void gameWonUpdate() {
		gameBoxPanel.gameWonUpdate();
		statsPanel.updateStats(gameEngine.getNumWins(), gameEngine.getNumLosses());
		
	}

	@Override
	public void newGameUpdate() {
		gameBoxPanel.newGameUpdate();
	}

	public StatsPanel getStatsPanel() {
		return statsPanel;
		
	}
	
/*	public void setShowStats(boolean state){
		showStats = state;
		statsPanel.setVisible(showStats);
	}
	
	public boolean getShowStats(){
		return showStats;
	}*/
	
	public void showHelpScreen(Tab tab) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	new HelpScreen(GUI.this, tab);
            }
        });
	}
	
	public void saveSettings(){
		File file = new File(FILENAME);
		Properties settings = new Properties();
		int xCor, yCor, width, height;
		if(this.getExtendedState()==Frame.NORMAL){
			Point location = this.getLocationOnScreen();
			xCor = location.x;
			yCor = location.y;
			width = getWidth();
			height = getHeight();
		}
		else{
			xCor = last_xCor;
			yCor = last_yCor;
			width = lastWidth;
			height = lastHeight;
		}
		settings.setProperty("x", String.valueOf(xCor));
		settings.setProperty("y", String.valueOf(yCor));
		settings.setProperty("w", String.valueOf(width));
		settings.setProperty("h", String.valueOf(height));
		//gameBoxPanel.saveSettings(settings);
		settings.setProperty("showInstructions", String.valueOf(gameBoxPanel.getShowInstructions()));
		settings.setProperty("showAnimation", String.valueOf(gameBoxPanel.getShowAnimation()));
		settings.setProperty("autoDiceRoll", String.valueOf(gameBoxPanel.getAutoDiceRoll()));
		settings.setProperty("showErrorMessage", String.valueOf(gameBoxPanel.getShowErrorMessage()));
		settings.setProperty("showStats", String.valueOf(statsPanel.getShowStats()));
		settings.setProperty("color", String.valueOf(gameBoxPanel.getColorChoice()));
		
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(file));
			settings.store(br, "Game Settings");
			br.close();
		} catch (IOException e) {
			System.err.println("Error: could not create file, settings not saved.");
		}
	}
	
	private boolean loadSettings(){
		File file = new File(FILENAME);
        Properties settings = new Properties();
        BufferedReader br;
        int x,y,h,w;
        boolean showInstructions, showAnimation, autoDiceRoll, showErrorMessage, showStats;
        FeltColor color;
		try {
			br = new BufferedReader(new FileReader(file));
			settings.load(br);
		} catch (Exception e) {
			return false;
		}
		x = Integer.parseInt(settings.getProperty("x","0"));
		y = Integer.parseInt(settings.getProperty("y","0"));
		String h_string = settings.getProperty("h");
		String w_string = settings.getProperty("w");
		if(h_string == null || w_string == null){
			h = FRAME_HEIGHT;
			w = FRAME_WIDTH;
		}
		else{
			h = Integer.parseInt(h_string);
			w = Integer.parseInt(w_string);
		}
		showInstructions = Boolean.parseBoolean(settings.getProperty("showInstructions","true"));
		showAnimation = Boolean.parseBoolean(settings.getProperty("showAnimation","true"));
		autoDiceRoll = Boolean.parseBoolean(settings.getProperty("autoDiceRoll","false"));
		showErrorMessage = Boolean.parseBoolean(settings.getProperty("showErrorMessage","true"));
		showStats = Boolean.parseBoolean(settings.getProperty("showStats","false"));
		color = FeltColor.valueOf(settings.getProperty("color","BLUE"));
		gameBoxPanel.setShowInstructions(showInstructions);
		gameBoxPanel.setShowAnimation(showAnimation);
		gameBoxPanel.setAutoDiceRoll(autoDiceRoll);
		gameBoxPanel.setShowErrorMessage(showErrorMessage);
		gameBoxPanel.setColorChoice(color);
		this.getStatsPanel().setShowStats(showStats);
		this.setSize(w, h);
		this.setLocation(x, y);
        return true;
	}
	
	public class resizeListener extends ComponentAdapter{
	    @Override
		public void componentResized(ComponentEvent e) {
			double scaleFactor = gameBoxPanel.scaleText();
			//double statsTextScaleFactor = (double) getWidth() / FRAME_WIDTH;
			statsPanel.setTextSize(scaleFactor);
			double diff = scaleFactor - 1;
			diff = diff/2;
			double titleScaleFactor = 1 + diff;
			int titleFontSize = (int) ( 34 * titleScaleFactor);
			title.setFont(new Font("serif",0,titleFontSize));
	    }
	}
	
	public class GUI_Listener extends WindowAdapter{
		@Override
		public void windowStateChanged(WindowEvent e){
			if(e.getOldState()==Frame.NORMAL){
				lastWidth = getWidth();
				lastHeight = getHeight();
				last_xCor = getX();
				last_yCor = getY();
			}
		}
		
		public void windowClosing(WindowEvent e){
			saveSettings();
		}
	}

}

