package Client;

import model.GameEngine;
import view.GUI;

public class GuiClient {
	
	private void runGame(){
		GameEngine gameEngine = new GameEngine();
		GUI gui = new GUI(gameEngine);
		gameEngine.addDisplay(gui.getGameBoxPanel());
	}
	
	public static void main(String Args[]){
		GuiClient client = new GuiClient();
		client.runGame();
	}
}
