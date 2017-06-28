package Client;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.GameEngine;
import model.GameEngine.FlipTileOutcome;
import model.GameEngine.GameState;
import view.Display;
import view.TextDisplay;

public class TextClient {
	
	private GameEngine gameEngine;
	private Display display;
	
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
			//loop to repeatedly have player roll dice, followed by selecting a tile, until game is over.
			do{
				int tileSelection;
				gameEngine.rollDice();
				if(gameEngine.getGameState() == GameState.GAME_LOST)
					break;
				//initializing flipTileOutcome to something other than SUCCESS
				FlipTileOutcome flipTileOutcome = FlipTileOutcome.INVALID_MOVE;
				//ask player to select a tile until they provide a valid one.
				while(flipTileOutcome != FlipTileOutcome.SUCCESS){
					System.out.print("Select a tile: ");
					try{
						tileSelection = sc.nextInt();
						flipTileOutcome = gameEngine.flipTile(tileSelection);
						if(flipTileOutcome == FlipTileOutcome.TILE_ALREADY_FLIPPED)
							System.out.println("Tile has already been flipped");
						else if(flipTileOutcome == FlipTileOutcome.INVALID_MOVE)
							System.out.println("Invalid move: must select tile number that " + 
									"matches either die or the combined total of the dice");
					}
					catch(IllegalArgumentException | InputMismatchException e){
						System.out.println("Must enter a tile number between 1 and 9");
						//clear buffer
						sc.nextLine();
					}
				}
			} while(gameEngine.getGameState() != GameState.GAME_WON); 			
			System.out.print("Play Again (y/n)?");
			//clears buffer to get rid of \n char
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
