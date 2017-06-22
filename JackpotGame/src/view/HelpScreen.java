package view;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class HelpScreen extends JFrame {
	
	private String howToPlayText, heading;
	
	public HelpScreen(GUI gui){
		super("Help Screen");
		this.setSize(500,380);
		this.setLocationRelativeTo(gui);
		this.setVisible(true);
		initializeStrings();
		addTextToScreen();
	}

	private void initializeStrings(){
		heading = "HOW TO PLAY JACKPOT GAME";
		howToPlayText = "\n\nThe object of the game is to flip over all the numbered tiles and"
			+ " spell out \"JACKPOT\".\n\nTo start, click on the dice to roll.  Once the dice are" 
			+ " rolled you must then select a tile to flip over.  You must select a tile that"
			+ " matches either die or the combined total of both dice.  For example, if you roll"
			+ " a 3 and a 5, then you can flip over either tile number 3, tile number 5, or tile"
			+ " number 8.  Tiles are selected by simply clicking on them.  Once the tile has been"
			+ " flipped, roll the dice again and follow the same process. Play continues until"
			+ " there are no tiles that match your dice roll (loss of game), or until all tiles"
			+ " are flipped over (win the game).  \n\nAt the end of the game, a new game can be"
			+ " started by clicking on the new game button or by selecting \"New Game\" from the"
			+ " \"Game\" menu.";
		
	}
	
	private void addTextToScreen() {
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        StyleContext sc = new StyleContext();
        Style headingStyle = sc.addStyle("headingStyle", null);
        //SimpleAttributeSet headingSet = new SimpleAttributeSet();
        StyleConstants.setAlignment(headingStyle, StyleConstants.ALIGN_CENTER); 
        StyleConstants.setBold(headingStyle, true);
        StyleConstants.setFontSize(headingStyle, 18);
        
        
        Style mainStyle = sc.addStyle("mainStyle", null);
        StyleConstants.setAlignment(mainStyle, StyleConstants.ALIGN_JUSTIFIED);
        StyleConstants.setBold(mainStyle,false);
        StyleConstants.setFontSize(mainStyle, 14);
        
        
        
        try {
			doc.insertString(doc.getLength(), heading, headingStyle);
			doc.insertString(doc.getLength(), howToPlayText, mainStyle);
			//doc.setParagraphAttributes(doc.getLength(), length, s, true);
			doc.setParagraphAttributes(0, heading.length(), headingStyle, true);
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        this.getContentPane().add(textPane);
		
	}
}
