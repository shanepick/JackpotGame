package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.DiceResult;
import model.GameEngine;

@SuppressWarnings("serial")
public class GUI extends JFrame implements Display{
	
	
	public static final int NUM_TILES = 9;
	private GameBoxPanel gameBoxPanel;
	private GameEngine gameEngine;
	private JMenuBar menu;
	private StatsPanel statsPanel = new StatsPanel();
	private JPanel combinedPanel;
	
	public GUI(GameEngine gameEngine){
		super("Jackpot Game");
		this.gameEngine = gameEngine;
		menu = new GameMenuBar(gameEngine, this);
		this.setSize(700, 540);
		this.setLocationRelativeTo(null);
		JLabel title = new JLabel("JACKPOT GAME");
		//title.setBackground(Color.YELLOW);
		title.setOpaque(true);
		title.setFont(new Font("serif",0,34));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		//title.setAlignmentX(CENTER_ALIGNMENT);
		title.setBorder(new EmptyBorder(30,30,30,30));
		//Panel to combine gameBoxPanel and statsPanel into the one panel
		combinedPanel = new JPanel();
		combinedPanel.setLayout(new BoxLayout(combinedPanel,BoxLayout.Y_AXIS));
		gameBoxPanel = new GameBoxPanel(gameEngine);
		gameBoxPanel.setAlignmentX(CENTER_ALIGNMENT);
		Container contentPane = this.getContentPane();
		
		//contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		//contentPane.setLayout(new GridBagLayout());
		/*for(int i = 0; i < tiles.length; ++i){
			tiles[i] = new TileView(i+1);
			contentPane.add(tiles[i]);
		}*/
	/*	GridBagConstraints titleConstraints = new GridBagConstraints();
		titleConstraints.gridx = 0;
		titleConstraints.gridy = 0;
		titleConstraints.anchor = GridBagConstraints.PAGE_START;
		titleConstraints.weighty = 0.01;*/
		//titleConstraints.insets = new Insets(30,30,30,30);
		//titleConstraints.ipady = 50;
	/*	GridBagConstraints gameBoxConstraints = new GridBagConstraints();
		gameBoxConstraints.gridx = 0;
		gameBoxConstraints.gridy = 1;
		gameBoxConstraints.weighty = 0.2;
		gameBoxConstraints.anchor = GridBagConstraints.PAGE_START;*/
		
	/*	GridBagConstraints statsConstraints = new GridBagConstraints();
		statsConstraints.gridx = 0;
		statsConstraints.gridy = 2;
		statsConstraints.weighty = 100;
		statsConstraints.anchor = GridBagConstraints.PAGE_START;
		statsConstraints.insets = new Insets(0,0,10,0);
		statsConstraints.ipady = 20;*/
		//statsConstraints.fill = GridBagConstraints.VERTICAL;
		
		/*contentPane.add(title,titleConstraints);
		contentPane.add(gameBoxPanel,gameBoxConstraints);
		contentPane.add(statsPanel, statsConstraints);*/
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
		// TODO Auto-generated method stub
		
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
	
	/*public void showStatsPanel() {
		
	}*/
	
	public class resizeListener extends ComponentAdapter{
	    public void componentResized(ComponentEvent e) {
			gameBoxPanel.scaleText();
			double statsTextScaleFactor = getWidth()/700.0;
			System.out.println("statsTextScaleFactor " + statsTextScaleFactor);
			statsPanel.setTextSize(statsTextScaleFactor);
	    }
	}
}

