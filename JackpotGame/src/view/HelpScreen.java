package view;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class HelpScreen extends JFrame {
	
	private String howToPlayText, howToPlayHeading, settingsHeading, settingsSubHeading1,
			settingsText1, settingsSubHeading2, settingsText2, settingsSubHeading3, settingsText3,
			settingsSubHeading4, settingsText4, settingsSubHeading5, settingsText5, 
			settingsSubHeading6, settingsText6;
	
	private JTabbedPane tabs;
	private StyleContext sc;
	public enum Tab { HOW_TO_PLAY, SETTINGS };
	
	public HelpScreen(GUI gui,Tab tab){
		super("Help Screen");
		this.setSize(670,500);
		this.setLocationRelativeTo(gui);

		initializeStrings();
		sc = new StyleContext();
		createStyles();
		tabs = new JTabbedPane();
		JScrollPane scrollPane1 = new JScrollPane(createSettingsScreen());
        scrollPane1.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
        	public void run() { 
        		scrollPane1.getVerticalScrollBar().setValue(0);
        	}
        });
		tabs.addTab("How To Play", createHowToPlayScreen());
		tabs.addTab("Settings", scrollPane1);
		if(tab == Tab.SETTINGS)
			tabs.setSelectedIndex(1);
		this.getContentPane().add(tabs);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}

	private void initializeStrings(){
		howToPlayHeading = "HOW TO PLAY JACKPOT GAME";
		howToPlayText = "\n\nThe object of the game is to flip over all the numbered tiles and"
			+ " spell out \"JACKPOT\".\n\nTo start, click on the dice to roll.  Once the dice are" 
			+ " rolled you must then select a tile to flip over.  You must select a tile that"
			+ " matches either die or the combined total of both dice.  For example, if you roll"
			+ " a 3 and a 5, then you can flip over either tile number 3, tile number 5, or tile"
			+ " number 8.  Tiles are selected by simply clicking on them.  Once the tile has been"
			+ " flipped, roll the dice again and follow the same process. Play continues until"
			+ " there are no tiles that match your dice roll (loss of game), or until all tiles"
			+ " are flipped over (win the game).  \n\nAt the end of the game, a new game can be"
			+ " started by clicking on the New Game button or by selecting \"New Game\" from the"
			+ " \"Game\" menu.";
		settingsHeading = "GAME SETTINGS";
		settingsSubHeading1 = "\nShow Instructions";
		settingsText1 = "\nBy default the game will instruct the player when to roll the dice"
			+ " and click on a tile. These instructions can be hidden by unchecking the \"Show"
			+ " Instructions\" option from the View Menu.";
		settingsSubHeading2 = "\n\nShow Win/Loss Statistics";
		settingsText2 = "\nIf this option is selected from the View Menu, then the game"
			+ " will display statistics on the bottom of the screen for: number of wins, number"
			+ " of losses, and the percentage of games won.";
		settingsSubHeading3 = "\n\nShow Dice Animations";
		settingsText3 = "\nBy default, dice animations are displayed. Dice animations can be"
			+ " disabled by unchecking this option from the Settings Menu. The dice"
			+ " values for a given roll will then be displayed immediately after rolling the dice.";
		settingsSubHeading4 = "\n\nSelect Felt Color";
		settingsText4 = "\nSelecting this option from the View Menu allows the player to"
			+ " select a felt color from the sub menu. Available colors are blue, green, red and"
			+ " black. The selected color will then be applied to the felt (background of the game"
			+ " box).";
		settingsSubHeading5 = "\n\nAutomatic Dice Roll";
		settingsText5 = "\nSelect this option from the Settings Menu to enable automatic dice"
			+ " rolling. Once enabled, every time a player flips a tile (or at the start of a"
			+ " new game), the dice will automatically be rolled without having to click on the"
			+ " dice.";
		settingsSubHeading6 = "\n\nShow Error Messages";
		settingsText6 = "\nBy default, an error message will be displayed when the player tries"
			+ " to flip a tile that does not match the current dice roll. Uncheck this option in"
			+ " the View Menu to disable these error messages.";
		
	}
	
	public void createStyles() {
        Style headingStyle = sc.addStyle("headingStyle", null);
        StyleConstants.setAlignment(headingStyle, StyleConstants.ALIGN_CENTER); 
        StyleConstants.setBold(headingStyle, true);
        StyleConstants.setFontSize(headingStyle, 20);
        
        Style subHeadingStyle = sc.addStyle("subHeadingStyle", null);
        StyleConstants.setBold(subHeadingStyle, true);
        StyleConstants.setFontSize(subHeadingStyle, 16);;
        
        Style mainStyle = sc.addStyle("mainStyle", null);
        StyleConstants.setBold(mainStyle,false);
        StyleConstants.setFontSize(mainStyle, 16);
	}
	
	private JTextPane createHowToPlayScreen() {
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
                
        try {
			doc.insertString(doc.getLength(), howToPlayHeading, sc.getStyle("headingStyle"));
			doc.insertString(doc.getLength(), howToPlayText, sc.getStyle("mainStyle"));
			doc.setParagraphAttributes(0, howToPlayHeading.length(), sc.getStyle("headingStyle"), true);
			
		} catch (BadLocationException e) {
			System.err.println("Error: Help screen text was not displayed correctly.");
		}
        return textPane;
		
	}
	
	private JTextPane createSettingsScreen() {
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
                
        try {
			doc.insertString(doc.getLength(), settingsHeading, sc.getStyle("headingStyle"));
			doc.insertString(doc.getLength(), settingsSubHeading1, sc.getStyle("subHeadingStyle"));
			doc.insertString(doc.getLength(), settingsText1, sc.getStyle("mainStyle"));
			doc.insertString(doc.getLength(), settingsSubHeading6, sc.getStyle("subHeadingStyle"));
			doc.insertString(doc.getLength(), settingsText6, sc.getStyle("mainStyle"));
			doc.insertString(doc.getLength(), settingsSubHeading2, sc.getStyle("subHeadingStyle"));
			doc.insertString(doc.getLength(), settingsText2, sc.getStyle("mainStyle"));
			doc.insertString(doc.getLength(), settingsSubHeading4, sc.getStyle("subHeadingStyle"));
			doc.insertString(doc.getLength(), settingsText4, sc.getStyle("mainStyle"));
			doc.insertString(doc.getLength(), settingsSubHeading5, sc.getStyle("subHeadingStyle"));
			doc.insertString(doc.getLength(), settingsText5, sc.getStyle("mainStyle"));
			doc.insertString(doc.getLength(), settingsSubHeading3, sc.getStyle("subHeadingStyle"));
			doc.insertString(doc.getLength(), settingsText3, sc.getStyle("mainStyle"));
			doc.setParagraphAttributes(0, settingsHeading.length(), sc.getStyle("headingStyle"), true);
			
		} catch (BadLocationException e) {
			System.err.println("Error: Help screen text was not displayed correctly.");
		}
        return textPane;
		
	}
}
