package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Controller.TileListener;

public class GameBoxPanel extends JPanel{
	
	private BufferedImage image;
	private BufferedImage tileImages[];
	public static final int TILE_WIDTH = 60;
	public static final int TILE_HEIGHT = 80;
	public static final int EDGE_WIDTH = 20;
	AffineTransform scaleTransform;
	boolean tilesState[];
	
	
	public GameBoxPanel() {
		tilesState = new boolean[9];
		for(int i = 0; i < tilesState.length; ++i)
			tilesState[i] = false;
		try {                
			image = ImageIO.read(new File("images/board.png"));
	    } catch (IOException ex) {
	        System.err.println("An error occurred: GameBox image file could not be opened.");
	        System.exit(1);
	    }
		tileImages = new BufferedImage[GUI.NUM_TILES];
		try {
			for(int i = 0; i < tileImages.length; ++i){
				tileImages[i] = ImageIO.read(new File("images/tile" + (i+1) + ".png"));
			}
			tileImages[0] = ImageIO.read(new File("images/test.png"));
		} catch (IOException ex) {
	        System.err.println("An error occurred: tile image file could not be opened.");
	        System.exit(1);
	    }
		this.setSize(new Dimension(580,340));
		this.addMouseListener(new TileListener());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//perhaps change to paint everything to buffered image first. 
		int height, width;
		double scaleFactor;
		height = getHeight();
		width = getWidth();
		//Graphics2D g2d = (Graphics2D) g;
		BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) bufferedImage.createGraphics();
		//super.paintComponent(g2d);

		//g2d.drawImage(image,60,0,this);
		System.out.println(width);
		scaleFactor = width / 698.0;
		//g2d.scale(scaleFactor,scaleFactor);
		scaleTransform = new AffineTransform();
		scaleTransform.scale(scaleFactor, scaleFactor);
		g2d.setTransform(scaleTransform);
		g2d.drawImage(image,60,0,this);
		for(int i = 0; i < tileImages.length; ++i){
			if(i > 0)
				g2d.drawImage(tileImages[i],60 + EDGE_WIDTH + i * TILE_WIDTH, EDGE_WIDTH, this);	
		}
		g2d.drawImage(tileImages[0], 60 + EDGE_WIDTH, EDGE_WIDTH -14, this);
		super.paintComponent(g);
		g.drawImage(bufferedImage,0,0,this);
		
	}
	
	public AffineTransform getScaleTransform() {
		return scaleTransform;
	}
}
