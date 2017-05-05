package Controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import view.GUI;
import view.GameBoxPanel;

public class TileListener extends MouseAdapter{

	public void mouseClicked(MouseEvent e){
		GameBoxPanel gameBoxPanel = (GameBoxPanel) e.getSource();
		AffineTransform inverseTransform;
		try {
			inverseTransform = gameBoxPanel.getScaleTransform().createInverse();
			Point deScaled = new Point();
			inverseTransform.transform(new Point(e.getX(),e.getY()), deScaled);
			int x = deScaled.x;
			int y = deScaled.y;
			System.out.println(x + "," + y);
			int tileSelected = 0;
			//TODO change to left click only.
			
			//int panelWidth = gameBoxPanel.getWidth();
			if(y > GameBoxPanel.EDGE_WIDTH && y < GameBoxPanel.TILE_HEIGHT + GameBoxPanel.EDGE_WIDTH){
				for(int i = 0; i < GUI.NUM_TILES; i++){
					if(x > 80 + i * GameBoxPanel.TILE_WIDTH && x < 80 + (i+1) * GameBoxPanel.TILE_WIDTH){
						tileSelected = i + 1;
						break;
					}
				}
			}
			System.out.println("tile selected was " + tileSelected);
		} catch (NoninvertibleTransformException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
