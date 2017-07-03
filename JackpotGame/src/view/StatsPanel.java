package view;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StatsPanel extends JPanel{

	private JLabel numWinsLabel, numLossesLabel, percentWinLabel;
	private boolean showStats = false;
	
	public StatsPanel(){
		numWinsLabel = new JLabel();
		numLossesLabel = new JLabel();
		percentWinLabel = new JLabel();
		this.updateStats(0, 0);
		this.setShowStats(false);
		this.setLayout(new FlowLayout());
		this.add(numWinsLabel);
		this.add(numLossesLabel);
		this.add(percentWinLabel);
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
	
	public void setShowStats(boolean state){
		showStats = state;
		numWinsLabel.setVisible(state);
		numLossesLabel.setVisible(state);
		percentWinLabel.setVisible(state);
	}
	
	public boolean getShowStats(){
		return showStats;
	}
	
	/*
	public void showStats(){
		numWinsLabel.setVisible(true);
		numLossesLabel.setVisible(true);
		percentWinLabel.setVisible(true);
	}*/
	
	/*
	public void hideStats(){
		numWinsLabel.setVisible(false);
		numLossesLabel.setVisible(false);
		percentWinLabel.setVisible(false);
	}*/
	


	public void setTextSize(double statsTextScaleFactor) {
		if(statsTextScaleFactor > 1){
			int textSize = (int) Math.min(statsTextScaleFactor * 14, 26);
			Font font = new Font("SansSerif",Font.BOLD,textSize);
			numWinsLabel.setFont(font);
			numLossesLabel.setFont(font);
			percentWinLabel.setFont(font);
		}
	}
	
	
}
