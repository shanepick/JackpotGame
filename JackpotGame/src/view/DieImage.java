package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * This class is for creating a buffered image that represents a face of a die.
 * It extend the BufferedImage class.
 * The die value must be specified in the constructor but can also be modified
 * using the setDieValue() method.
 * @author shane
 *
 */
public class DieImage extends BufferedImage{

	private final static int DICESIZE=51; //size of square to represent die.
	private final static int DOTSIZE=11; //size of the dots to represent numbers on die.
	private final static int BORDERWIDTH = 1;
	private Graphics2D g2d;
	
	public DieImage(int dieValue) {
		super(DICESIZE, DICESIZE, TYPE_INT_ARGB);
		g2d = (Graphics2D) createGraphics();
		g2d.setColor(Color.black);
		//fills in a black square of size DICESIZE, later a white square is 
		//drawn over it, leaving a black border.
        g2d.fillRect(0,0, DICESIZE, DICESIZE);
        setDieValue(dieValue);
	}
	
	/**
	 * draws over the die face such that it displays the value dieValue.
	 * @param dieValue is the value to be shown on the die.
	 */
	public void setDieValue(int dieValue){
		
		if(dieValue < 1||dieValue > 6)
			throw new IllegalArgumentException("invalid dieValue");
		g2d.setColor(Color.WHITE);
		//next we draw in the white dice face, leaving a black border of width BORDERWIDTH.
		int whiteSquareSize = DICESIZE - 2 * BORDERWIDTH;
		g2d.fillRect(0 + BORDERWIDTH, 0 + BORDERWIDTH, whiteSquareSize, whiteSquareSize);
		g2d.setColor(Color.black);
		//Rendering Hints necessary so that the dice's dots appear more 
		//circular and less pixelated.
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		//coordinates of 1/5 DICESIZE, 1/2 DICESIZE, and 4/5 DICESIZE are used to position dice 
		//dots in left, middle, and right locations on die. Same for top, middle, and bottom.
		//Since these are the coordinates that the centre of the dot should be, subtracting 
		//dotsize/2 gives the top left hand corner of the square that contains the dot, as 
		//required by fillOval().
		if(dieValue==1){
			g2d.fillOval(DICESIZE/2 - DOTSIZE/2, DICESIZE/2 - DOTSIZE/2, DOTSIZE, DOTSIZE);
		}
		if(dieValue==2){
			g2d.fillOval(DICESIZE/5 - DOTSIZE/2, DICESIZE/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5 - DOTSIZE/2, (4*DICESIZE)/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
		}
		if(dieValue==3){
			g2d.fillOval((4*DICESIZE)/5 - DOTSIZE/2, DICESIZE/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval(DICESIZE/5 - DOTSIZE/2, (4*DICESIZE)/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval(DICESIZE/2 - DOTSIZE/2, DICESIZE/2 - DOTSIZE/2, DOTSIZE, DOTSIZE);
		}
		if(dieValue==4){
			g2d.fillOval(DICESIZE/5 - DOTSIZE/2,DICESIZE/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5 - DOTSIZE/2, (4*DICESIZE)/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5 - DOTSIZE/2, DICESIZE/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval(DICESIZE/5 - DOTSIZE/2, (4*DICESIZE)/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
		}
		if(dieValue==5){
			g2d.fillOval(DICESIZE/5 - DOTSIZE/2, DICESIZE/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5 - DOTSIZE/2, (4*DICESIZE)/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5 - DOTSIZE/2, DICESIZE/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval(DICESIZE/5 - DOTSIZE/2, (4*DICESIZE)/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval(DICESIZE/2 - DOTSIZE/2, DICESIZE/2 - DOTSIZE/2, DOTSIZE, DOTSIZE);
		}
		if(dieValue==6){
			g2d.fillOval(DICESIZE/5 - DOTSIZE/2, DICESIZE/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2, (4*DICESIZE)/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2, DICESIZE/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval(DICESIZE/5 - DOTSIZE/2, (4*DICESIZE)/5 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval(DICESIZE/5 - DOTSIZE/2, DICESIZE/2 - DOTSIZE/2, DOTSIZE, DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5 - DOTSIZE/2, DICESIZE/2 - DOTSIZE/2, DOTSIZE, DOTSIZE);
		}
	}
	
	public int getDiceSize(){
		return DICESIZE;
	}

}
