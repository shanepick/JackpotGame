package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.DiceResult;
import model.GameEngine;

@SuppressWarnings("serial")
public class GUI extends JFrame implements Display{
	
	private static final int FRAME_WIDTH = 700, FRAME_HEIGHT = 540;
	private GameBoxPanel gameBoxPanel;
	private GameEngine gameEngine;
	private JMenuBar menu;
	private StatsPanel statsPanel = new StatsPanel();
	//Panel to combine gameBoxPanel and statsPanel into the one panel
	private JPanel combinedPanel;
	
	public GUI(GameEngine gameEngine){
		super("Jackpot Game");
		this.gameEngine = gameEngine;
		menu = new GameMenuBar(gameEngine, this);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLocationRelativeTo(null);
		JLabel title = new JLabel("JACKPOT GAME");
		title.setFont(new Font("serif",0,34));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBorder(new EmptyBorder(30,30,30,30));
		combinedPanel = new JPanel();
		combinedPanel.setLayout(new BoxLayout(combinedPanel,BoxLayout.Y_AXIS));
		gameBoxPanel = new GameBoxPanel(gameEngine);
		gameBoxPanel.setAlignmentX(CENTER_ALIGNMENT);
		Container contentPane = this.getContentPane();
		combinedPanel.add(gameBoxPanel);
		combinedPanel.add(statsPanel);
		contentPane.add(title,BorderLayout.NORTH);
		contentPane.add(combinedPanel);
		
		//contentPane.add(statsPanel);
		this.setJMenuBar(menu);
		this.setVisible(true);
		this.addComponentListener(new resizeListener());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	public class resizeListener extends ComponentAdapter{
	    public void componentResized(ComponentEvent e) {
			gameBoxPanel.scaleText();
			double statsTextScaleFactor = (double) getWidth() / FRAME_WIDTH;
			System.out.println("statsTextScaleFactor " + statsTextScaleFactor);
			statsPanel.setTextSize(statsTextScaleFactor);
	    }
	}

	public void showHelpScreen() {
		System.out.println("test");
		HelpScreen help = new HelpScreen(this);
	}
}

