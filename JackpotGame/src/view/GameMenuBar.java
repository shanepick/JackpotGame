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

public class GameMenuBar extends JMenuBar{

	private GameEngine gameEngine;
	private JMenu gameMenu, viewMenu, colorMenu;
	private JMenuItem newGame, exitGame, blue, green, red, black;
	private MenuListener menuListener;
	
	public GameMenuBar(GameEngine gameEngine){
		this.gameEngine = gameEngine;
		menuListener = new MenuListener();
		initializeGameMenu();
		initializeViewMenu();
		this.add(gameMenu);
		this.add(viewMenu);
		//createViewMenu();
		
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
		JMenuItem blue = new JRadioButtonMenuItem("Blue");
		blue.setMnemonic(KeyEvent.VK_B);
		JMenuItem green = new JRadioButtonMenuItem("Green");
		green.setMnemonic(KeyEvent.VK_G);
		JMenuItem red = new JRadioButtonMenuItem("Red");
		red.setMnemonic(KeyEvent.VK_R);
		JMenuItem black = new JRadioButtonMenuItem("Black");
		black.setMnemonic(KeyEvent.VK_L);
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
		JMenuItem showStats = new JCheckBoxMenuItem("Show Win/Loss Statistics");
		showStats.setMnemonic(KeyEvent.VK_S);
		viewMenu.add(showStats);
		viewMenu.add(colorMenu);
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
		}

	}
}
