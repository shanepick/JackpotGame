package Client;

import javax.swing.SwingUtilities;

import model.GameEngine;
import view.GUI;

public class GuiClient {
	
	private void runGame(){
		GameEngine gameEngine = new GameEngine();
		GUI gui = new GUI(gameEngine);
	}
	
	public static void main(String Args[]){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	GuiClient client = new GuiClient();
            	client.runGame();
            }
        });
	}
}
