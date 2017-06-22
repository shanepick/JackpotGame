package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import model.GameEngine;
import view.GameBoxPanel.FeltColor;

@SuppressWarnings("serial")
public class GameMenuBar extends JMenuBar{

	private GameEngine gameEngine;
	private GUI gui;
	private JMenu gameMenu, viewMenu, settingsMenu, colorMenu;
	private JMenuItem newGame, exitGame, blue, green, red, black; 
	private JCheckBoxMenuItem showStats,  showInstructions, showAnimation, autoDiceRoll;
	private MenuListener menuListener;
	
	public GameMenuBar(GameEngine gameEngine, GUI gui){
		this.gameEngine = gameEngine;
		this.gui = gui;
		menuListener = new MenuListener();
		initializeGameMenu();
		initializeViewMenu();
		initializeSettingsMenu();
		this.add(gameMenu);
		this.add(viewMenu);
		this.add(settingsMenu);
		
	}
	
	private void initializeGameMenu(){
		gameMenu = new JMenu("Game");
		gameMenu.setMnemonic(KeyEvent.VK_G);
		newGame= new JMenuItem("New game",KeyEvent.VK_N);
		newGame.addActionListener(menuListener);
		gameMenu.add(newGame);
		exitGame = new JMenuItem("Exit game",KeyEvent.VK_X);
		exitGame.addActionListener(menuListener);
		gameMenu.add(exitGame);
	}
	
	private void initializeViewMenu(){
		viewMenu = new JMenu("View");
		viewMenu.setMnemonic(KeyEvent.VK_V);
		colorMenu = new JMenu("Select Felt Color");
		colorMenu.setMnemonic(KeyEvent.VK_F);
		showInstructions = new JCheckBoxMenuItem("Show Instructions");
		showInstructions.setSelected(true);
		showInstructions.addActionListener(menuListener);
		blue = new JRadioButtonMenuItem("Blue");
		blue.setMnemonic(KeyEvent.VK_B);
		blue.addActionListener(menuListener);
		green = new JRadioButtonMenuItem("Green");
		green.setMnemonic(KeyEvent.VK_G);
		green.addActionListener(menuListener);
		red = new JRadioButtonMenuItem("Red");
		red.setMnemonic(KeyEvent.VK_R);
		red.addActionListener(menuListener);
		black = new JRadioButtonMenuItem("Black");
		black.setMnemonic(KeyEvent.VK_L);
		black.addActionListener(menuListener);
		ButtonGroup bg = new ButtonGroup();
		bg.add(blue);
		bg.add(green);
		bg.add(red);
		bg.add(black);
		blue.setSelected(true);
		colorMenu.add(blue);
		colorMenu.add(green);
		colorMenu.add(red);
		colorMenu.add(black);
		showStats = new JCheckBoxMenuItem("Show Win/Loss Statistics");
		showStats.setMnemonic(KeyEvent.VK_S);
		showStats.addActionListener(menuListener);
		showStats.setActionCommand("Stats");
		showAnimation = new JCheckBoxMenuItem("Show Dice Animation");
		showAnimation.setSelected(true);
		showAnimation.addActionListener(menuListener);
		viewMenu.add(showInstructions);
		viewMenu.add(showStats);
		viewMenu.add(showAnimation);
		viewMenu.add(colorMenu);
	}
	
	private void initializeSettingsMenu(){
		settingsMenu = new JMenu("Settings");
		settingsMenu.setMnemonic(KeyEvent.VK_S);
		autoDiceRoll = new JCheckBoxMenuItem("Automatic Dice Roll");
		autoDiceRoll.setSelected(false);
		autoDiceRoll.addActionListener(menuListener);
		settingsMenu.add(autoDiceRoll);
	}
	
	public class MenuListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getActionCommand().equals(newGame.getText())){
				gameEngine.newGame();
			}
			else if(event.getActionCommand().equals(exitGame.getText())){
				System.exit(0);
			}
			else if(event.getActionCommand().equals("Blue")){
				gui.getGameBoxPanel().setColorChoice(FeltColor.BLUE);
			}
			else if(event.getActionCommand().equals("Green")){
				gui.getGameBoxPanel().setColorChoice(FeltColor.GREEN);
			}
			else if(event.getActionCommand().equals("Red")){
				gui.getGameBoxPanel().setColorChoice(FeltColor.RED);
			}
			else if(event.getActionCommand().equals("Black")){
				gui.getGameBoxPanel().setColorChoice(FeltColor.BLACK);
			}
			else if(event.getActionCommand().equals("Stats")){
				if(showStats.getState())
					gui.getStatsPanel().showStats();
				else
					gui.getStatsPanel().hideStats();
					
			}
			else if(event.getActionCommand().equals("Show Instructions")){
				if(showInstructions.getState())
					gui.getGameBoxPanel().showInstructions();
				else
					gui.getGameBoxPanel().hideInstructions();
			}
			else if(event.getActionCommand().equals("Show Dice Animation")){
				gui.getGameBoxPanel().setShowAnimation(showAnimation.getState());
			}
			else if(event.getActionCommand().equals("Automatic Dice Roll")){
				gui.getGameBoxPanel().setAutoDiceRoll(autoDiceRoll.getState());
			}
		}

	}
}
