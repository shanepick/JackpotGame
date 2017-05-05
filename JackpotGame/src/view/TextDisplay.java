package view;

import java.util.Arrays;
import java.util.Scanner;

import model.DiceResult;
import model.GameEngine.InvalidTileStatus;

public class TextDisplay implements Display{
	
	char startTiles[] = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	char flippedTiles[] = { '*', 'J', 'A', 'C', 'K', 'P', 'O', 'T', '*' };
	private static final int NUM_TILES = 9;
	Scanner scan;
	
	public TextDisplay(){
	}
	
	@Override
	public void updateDiceIntermediate(DiceResult dice) {
		
		
	}

	@Override
	public void updateDiceFinal(DiceResult dice) {
		System.out.println("Dice Result is: " + dice.toString() );
	}

	@Override
	public void updateTiles(boolean tilesState[]) {
        for(int i = 0; i < NUM_TILES; i++){
        	if(tilesState[i] == false)
        		System.out.print(startTiles[i] + " ");
        	else
        		System.out.print(flippedTiles[i] + " ");
        }
        System.out.println();
	}

	@Override
	public void invalidTileUpdate(InvalidTileStatus status) {
		switch(status){
		case TILE_ALREADY_FLIPPED:
			System.out.println("Tile has already been flipped");
			break;
		case INVALID_MOVE:
			System.out.println("Invalid move: must select tile number that " + 
					"matches either die or the combined total of the dice");
			break;
		}
	}

	@Override
	public void gameLostUpdate() {
		System.out.println("You lose, no moves are possible");
			
	}

	@Override
	public void gameWonUpdate() {
		System.out.println("Congratulations, you win!");
	}
	

	@Override
	public void newGameUpdate() {
        for(int i = 0; i < startTiles.length; i++)
        	System.out.print(startTiles[i] + " ");
        System.out.println();
	}

}
