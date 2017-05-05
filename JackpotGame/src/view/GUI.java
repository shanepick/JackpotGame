package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import model.GameEngine;

@SuppressWarnings("serial")
public class GUI extends JFrame{
	
	JLabel tiles[];
	public static final int NUM_TILES = 9;
	
	
	public GUI(String s){
		super(s);
		this.setSize(700, 600);
		this.setLocationRelativeTo(null);
		JLabel Title = new JLabel("JACKPOT GAME");
		Title.setFont(new Font("serif",0,34));
		Title.setAlignmentX(CENTER_ALIGNMENT);
		Title.setBorder(new EmptyBorder(30,30,30,30));
		GameBoxPanel gameBoxPanel = new GameBoxPanel();
		gameBoxPanel.setAlignmentX(CENTER_ALIGNMENT);
		tiles = new TileView[GameEngine.NUM_TILES];
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane,BoxLayout.Y_AXIS));
		/*for(int i = 0; i < tiles.length; ++i){
			tiles[i] = new TileView(i+1);
			contentPane.add(tiles[i]);
		}*/
		contentPane.add(Title);
		contentPane.add(gameBoxPanel);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String Args[]){
		new GUI("Jackpot Game");
	}
}

