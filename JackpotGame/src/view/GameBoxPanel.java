package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.TileListener;

@SuppressWarnings("serial")
public class GameBoxPanel extends JPanel{
	
	private BufferedImage image;
	private BufferedImage startTileImages[];
	private BufferedImage flippedTileImages[];
	public static final int TILE_WIDTH = 60;
	public static final int TILE_HEIGHT = 80;
	public static final int EDGE_WIDTH = 20;
	AffineTransform scaleTransform;
	boolean tilesState[];
	JPanel test = new JPanel();
	GridBagConstraints c = new GridBagConstraints();
	
	public GameBoxPanel() {
		tilesState = new boolean[GUI.NUM_TILES];
		Arrays.fill(tilesState, false);
		try {                
			image = ImageIO.read(new File("images/board.png"));
	    } catch (IOException ex) {
	        System.err.println("An error occurred: GameBox image file could not be opened.");
	        System.exit(1);
	    }
		startTileImages = new BufferedImage[GUI.NUM_TILES];
		flippedTileImages = new BufferedImage[GUI.NUM_TILES];
		try {
			for(int i = 0; i < startTileImages.length; ++i){
				startTileImages[i] = ImageIO.read(new File("images/tile" + (i+1) + ".png"));
			}
			flippedTileImages[0] = ImageIO.read(new File("images/tileDot.png"));
			//TODO change filename
			flippedTileImages[1] = ImageIO.read(new File("images/test.png"));
			flippedTileImages[2] = ImageIO.read(new File("images/tileA.png"));
			flippedTileImages[3] = ImageIO.read(new File("images/tileC.png"));
			flippedTileImages[4] = ImageIO.read(new File("images/tileK.png"));
			flippedTileImages[5] = ImageIO.read(new File("images/tileP.png"));
			flippedTileImages[6] = ImageIO.read(new File("images/tileO.png"));
			flippedTileImages[7] = ImageIO.read(new File("images/tileT.png"));
			flippedTileImages[8] = flippedTileImages[0];
			
		} catch (IOException ex) {
	        System.err.println("An error occurred: tile image file could not be opened.");
	        System.exit(1);
	    }
		this.setSize(new Dimension(580,340));
		this.addMouseListener(new TileListener());
		this.setBackground(Color.cyan);
		
		
		
		this.setLayout(new GridBagLayout());
		//this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		//this.setLayout(null);
		//test.setBackground(Color.BLACK);
		//test.setPreferredSize(new Dimension(200,100));
		
		//c.weighty = 0.5;
		//c.anchor = GridBagConstraints.PAGE_END;
		//c.insets = new Insets(200,30,30,30);
		//c.fill = GridBagConstraints.VERTICAL;
		//this.add(test,c);
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
		//TODO crate AffineTransform once in constructor instead of every time.
		scaleTransform = new AffineTransform();
		scaleTransform.scale(scaleFactor, scaleFactor);
		g2d.setTransform(scaleTransform);
		g2d.drawImage(image,60,0,this);
		for(int i = 0; i < tilesState.length; ++i){
			int xCor = 60 + EDGE_WIDTH + i * TILE_WIDTH;
			if(tilesState[i] == false)
				g2d.drawImage(startTileImages[i], xCor, EDGE_WIDTH, this);
			else
				g2d.drawImage(flippedTileImages[i], xCor, EDGE_WIDTH -14, this);
		}
		c.insets = new Insets( (int) scaleFactor * 200, (int) scaleFactor * 30, (int) scaleFactor * 30, (int) scaleFactor * 30);
		revalidate();
		super.paintComponent(g);
		g.drawImage(bufferedImage,0,0,this);
		
		//Point dicePanelDimensions = new Point();
		//scaleTransform.transform(new Point(250,60),dicePanelDimensions);
		//test.setSize(dicePanelDimensions.x,dicePanelDimensions.y);
		
		
		
	}
	
	public AffineTransform getScaleTransform() {
		return scaleTransform;
	}
	
	public void flipTiles(int tileNum){
		tilesState[tileNum - 1] = true;
		this.repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		Container parent = this.getParent();
		int width = parent.getWidth();
	    return new Dimension(width, (int) (width/2.0) );
	}
	
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
}
