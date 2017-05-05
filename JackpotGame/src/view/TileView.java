package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class TileView extends JLabel{

	public TileView(int numDisplay){
		if(numDisplay < 1 || numDisplay > 9)
			throw new IllegalArgumentException(Integer.toString(numDisplay));
		this.setIcon(new ImageIcon("images/tile" + numDisplay + ".png"));
	}
	
}
