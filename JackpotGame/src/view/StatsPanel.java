package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatsPanel extends JPanel{

	JLabel numWinsLabel, numLossesLabel, percentWinLabel;
	
	public StatsPanel(){
		//this.setBorder(BorderFactory.createLineBorder(Color.black));
		numWinsLabel = new JLabel();
		numLossesLabel = new JLabel();
		percentWinLabel = new JLabel();
		this.updateStats(0, 0);
		this.hideStats();
		this.setLayout(new FlowLayout());
		this.add(numWinsLabel);
		this.add(numLossesLabel);
		this.add(percentWinLabel);
		//this.setMaximumSize(new Dimension(400,30));
	}
	
	public void updateStats(int numWins, int numLosses){
		String numWinsString = "Number of Wins: " + numWins + "   ";
		String numLossesString = "Number of Losses : " + numLosses + "   ";
		double percentWin;
		if(numLosses + numWins == 0)
			percentWin = 0;
		else
			percentWin =  ( (double) numWins/(numLosses + numWins)) * 100;
		String percentWinString = String.format("%% Win: %.1f%%", percentWin);
		numWinsLabel.setText(numWinsString);
		numLossesLabel.setText(numLossesString);
		percentWinLabel.setText(percentWinString);
		this.revalidate();
		this.repaint();
	}
	
	public void showStats(){
		numWinsLabel.setVisible(true);
		numLossesLabel.setVisible(true);
		percentWinLabel.setVisible(true);
	}
	
	public void hideStats(){
		numWinsLabel.setVisible(false);
		numLossesLabel.setVisible(false);
		percentWinLabel.setVisible(false);
	}
	
	
}
