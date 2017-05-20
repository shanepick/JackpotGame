package view;

import java.util.Arrays;
import java.util.Scanner;

import model.DiceResult;

public class TextDisplay implements Display{
	
	char startTiles[] = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	char flippedTiles[] = { '*', 'J', 'A', 'C', 'K', 'P', 'O', 'T', '*' };
	char Tiles[];
	Scanner scan;
	
	public TextDisplay(){
		Tiles = Arrays.copyOf(startTiles,startTiles.length);
	}
	
	@Override
	public void updateDiceIntermediate(DiceResult dice) {
		
		
	}

	@Override
	public void updateDiceFinal(DiceResult dice) {
		System.out.println("Dice Result is: " + dice.toString() );
	}

	@Override
	public void flipTile(int tileNum) {
		Tiles[tileNum - 1] = flippedTiles[tileNum - 1];
        for(int i = 0; i < Tiles.length; i++)
        	System.out.print(Tiles[i] + " ");
        System.out.println();
	}

	/*
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
	}*/

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
		Tiles = Arrays.copyOf(startTiles,startTiles.length);
        for(int i = 0; i < Tiles.length; i++)
        	System.out.print(Tiles[i] + " ");
        System.out.println();
	}

}
