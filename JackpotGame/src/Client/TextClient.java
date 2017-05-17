package Client;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.GameEngine;
import view.Display;
import view.TextDisplay;

public class TextClient {
	
	GameEngine gameEngine;
	Display display;
	
	public TextClient(){
		gameEngine = new GameEngine();
		display = new TextDisplay();
		gameEngine.addDisplay(display);
	}
	
	public void runGame(){
		boolean playerContinue = true;
		Scanner sc = new Scanner(System.in);
		display.newGameUpdate();
		while(playerContinue){
			int tilesFlipped = 0;
			do{
				int tileSelection;
				//if dice roll has no corresponding valid moves and game is over.
				if(gameEngine.rollDice() == false)
					break;
				boolean tileSelected = false;
				while(!tileSelected){
					System.out.print("Select a tile: ");
					try{
						tileSelection = sc.nextInt();
						tileSelected = gameEngine.flipTile(tileSelection);
					}
					catch(IllegalArgumentException | InputMismatchException e){
						sc.nextLine();
					}
				}
				//add gameState variable in model instead.
			} while(++tilesFlipped < GameEngine.NUM_TILES); //while game has not been won yet.
			
			System.out.print("Play Again (y/n)?");
			sc.nextLine();
			String input = sc.nextLine();
			if(input.charAt(0) != 'y')
				playerContinue = false;
			else
				gameEngine.newGame();
		}
		sc.close();
	}
	
	public static void main(String Args[]){
		TextClient client = new TextClient();
		client.runGame();
	}
}
