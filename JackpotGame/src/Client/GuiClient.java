package Client;

import javax.swing.SwingUtilities;

import model.GameEngine;
import view.GUI;

/**
 * Class contains the main method for running the GUI version of the Jackpot Game.
 * @author shane
 *
 */
public class GuiClient {
	
	private void runGame(){
		GameEngine gameEngine = new GameEngine();
		new GUI(gameEngine);
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
