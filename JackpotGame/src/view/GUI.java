package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.EmptyBorder;

import model.GameEngine;

@SuppressWarnings("serial")
public class GUI extends JFrame{
	
	
	public static final int NUM_TILES = 9;
	private GameBoxPanel gameBoxPanel;
	private GameEngine gameEngine;
	private JMenuBar menu; 
	
	public GUI(GameEngine gameEngine){
		super("Jackpot Game");
		this.gameEngine = gameEngine;
		menu = new GameMenuBar(gameEngine);
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);
		JLabel title = new JLabel("JACKPOT GAME");
		//title.setBackground(Color.YELLOW);
		//title.setOpaque(true);
		title.setFont(new Font("serif",0,34));
		//title.setAlignmentX(CENTER_ALIGNMENT);
		title.setBorder(new EmptyBorder(30,30,30,30));
		
		gameBoxPanel = new GameBoxPanel(gameEngine);
		//gameBoxPanel.setAlignmentX(CENTER_ALIGNMENT);
		Container contentPane = this.getContentPane();
		//contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		contentPane.setLayout(new GridBagLayout());
		/*for(int i = 0; i < tiles.length; ++i){
			tiles[i] = new TileView(i+1);
			contentPane.add(tiles[i]);
		}*/
		GridBagConstraints titleConstraints = new GridBagConstraints();
		titleConstraints.gridx = 0;
		titleConstraints.gridy = 0;
		titleConstraints.anchor = GridBagConstraints.PAGE_START;
		titleConstraints.weighty = 0.01;
		//titleConstraints.insets = new Insets(30,30,30,30);
		//titleConstraints.ipady = 50;
		GridBagConstraints gameBoxConstraints = new GridBagConstraints();
		gameBoxConstraints.gridx = 0;
		gameBoxConstraints.gridy = 1;
		gameBoxConstraints.weighty = 0.7;
		gameBoxConstraints.anchor = GridBagConstraints.PAGE_START;
		contentPane.add(title,titleConstraints);
		contentPane.add(gameBoxPanel,gameBoxConstraints);
		this.setJMenuBar(menu);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public GameBoxPanel getGameBoxPanel(){
		return gameBoxPanel;
	}
}

