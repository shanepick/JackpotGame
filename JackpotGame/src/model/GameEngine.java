package model;

import java.util.Random;

import view.Display;

public class GameEngine {

	public static final int NUM_TILES = 9;
	public static final int NUM_DIE_FACES = 6;
	private boolean tilesState[];
	private Random randomGenerator;
	private int numTilesFlipped = 0;
	private int numWins = 0;
	private int numLosses = 0;
	private Display display;
	private DiceResult currentRoll = null;
	public enum InvalidTileStatus { TILE_ALREADY_FLIPPED, INVALID_MOVE };
	
	public GameEngine(){
		tilesState = new boolean[NUM_TILES];
		resetTilesState();
		randomGenerator = new Random();
	}
	
	public void addDisplay(Display display){
		this.display = display;
	}
	
	public boolean rollDice(){
		int die1,die2;
		die1=randomGenerator.nextInt(NUM_DIE_FACES)+1;
		die2=randomGenerator.nextInt(NUM_DIE_FACES)+1;
		currentRoll=new DiceResult(die1,die2);
		display.updateDiceFinal(currentRoll);
		if(numTilesFlipped > 0){
			if(!hasValidMoves()){
				++numLosses;
				display.gameLostUpdate();
				resetTilesState();
				return false;
			}
		}
		return true;
	}
	
	public boolean flipTile(int tileNum){
		if(tileNum <= 0 || tileNum > NUM_TILES)
			throw new IllegalArgumentException(Integer.toString(tileNum));
		if(tilesState[tileNum-1] ==  false  && isValidMove(tileNum)){
			tilesState[tileNum-1] = true;
			display.updateTiles(tilesState);
			if(++numTilesFlipped == NUM_TILES){
				numWins++;
				display.gameWonUpdate();
			}
			return true;
		}
		//tile could not be flipped
		if(tilesState[tileNum-1] ==  true)
			display.invalidTileUpdate(InvalidTileStatus.TILE_ALREADY_FLIPPED);
		else
			display.invalidTileUpdate(InvalidTileStatus.INVALID_MOVE);
		return false;
		
	};
	
	public void newGame(){
		resetTilesState();
		display.newGameUpdate();
	}
	
	private boolean isValidMove(int tileNum){
		if(tileNum == currentRoll.getDie1())
			return true;
		if(tileNum == currentRoll.getDie2())
			return true;
		if(tileNum == currentRoll.getTotal())
			return true;
		return false;
	}
	
	private boolean hasValidMoves(){
		if(tilesState[currentRoll.getDie1()-1] == false)
			return true;
		if(tilesState[currentRoll.getDie2()-1] == false)
			return true;
		if( currentRoll.getTotal() <=9 && tilesState[currentRoll.getTotal()-1] == false)
			return true;
		return false;
	}
	
	private void resetTilesState(){
		//false corresponds to a tile not being flipped, and true for flipped.
		for(int i = 0; i < tilesState.length; ++i)
			tilesState[i] = false;
		numTilesFlipped = 0;
	}
	
}
