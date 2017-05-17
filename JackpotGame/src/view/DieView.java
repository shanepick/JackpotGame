package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class DieView extends BufferedImage{

	public final static int DICESIZE=51;
	public final static int DOTSIZE=11;
	Graphics2D g2d;
	
	public DieView(int dieValue) {
		super(DICESIZE, DICESIZE, TYPE_INT_ARGB);
		g2d = (Graphics2D) createGraphics();
		g2d.setColor(Color.black);
        g2d.drawRect(0,0, DICESIZE, DICESIZE);
        setDieValue(dieValue);
		setDieValue(dieValue);
	}
	
	
	public void setDieValue(int dieValue){
		
		if(dieValue<1||dieValue>6)
			throw new IllegalArgumentException("invalid dieValue");
		
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0 + 1, 0 + 1, DICESIZE-1, DICESIZE-1);
		g2d.setColor(Color.black);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		if(dieValue==1){
			g2d.fillOval(DICESIZE/2-DOTSIZE/2, 
					DICESIZE/2-DOTSIZE/2, DOTSIZE, DOTSIZE);
		}
		if(dieValue==2){
			g2d.fillOval(DICESIZE/5-DOTSIZE/2,
					DICESIZE/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2,
					(4*DICESIZE)/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
		}
		if(dieValue==3){
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2,
					DICESIZE/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval(DICESIZE/5-DOTSIZE/2,
					(4*DICESIZE)/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval(DICESIZE/2-DOTSIZE/2, 
					DICESIZE/2-DOTSIZE/2, DOTSIZE, DOTSIZE);
		}
		if(dieValue==4){
			g2d.fillOval(DICESIZE/5-DOTSIZE/2,
					DICESIZE/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2,
					(4*DICESIZE)/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2,
					DICESIZE/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval(DICESIZE/5-DOTSIZE/2,
					(4*DICESIZE)/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
		}
		if(dieValue==5){
			g2d.fillOval(DICESIZE/5-DOTSIZE/2,
					+DICESIZE/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2,
					(4*DICESIZE)/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2,
					DICESIZE/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval(DICESIZE/5-DOTSIZE/2,
					(4*DICESIZE)/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval(DICESIZE/2-DOTSIZE/2, 
					DICESIZE/2-DOTSIZE/2, DOTSIZE, DOTSIZE);
		}
		if(dieValue==6){
			g2d.fillOval(DICESIZE/5-DOTSIZE/2,
					DICESIZE/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2,
					(4*DICESIZE)/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2,
					DICESIZE/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval(DICESIZE/5-DOTSIZE/2,
					(4*DICESIZE)/5-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval(DICESIZE/5-DOTSIZE/2,
					DICESIZE/2-DOTSIZE/2,DOTSIZE,DOTSIZE);
			g2d.fillOval((4*DICESIZE)/5-DOTSIZE/2,
					DICESIZE/2-DOTSIZE/2,DOTSIZE,DOTSIZE);
		}
	}

}
