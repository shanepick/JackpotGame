package model;

import java.util.Arrays;
import java.util.Random;

import view.Display;

public class GameEngine {

	public static final int NUM_TILES = 9;
	public static final int NUM_DIE_FACES = 6;
	//contains tile state: false corresponds to a tile not being flipped, and true for flipped.
	private boolean tilesState[]; 
	private Random randomGenerator;
	private int numTilesFlipped = 0;
	private int numWins = 0;
	private int numLosses = 0;
	private Display display;
	private DiceResult currentRoll = null; //the player's last roll.
	public enum FlipTileOutcome { TILE_ALREADY_FLIPPED, INVALID_MOVE, DICE_NOT_ROLLED, SUCCESS }
	public enum GameState { PRE_DICE_ROLL, DURING_DICE_ROLL, POST_DICE_ROLL, GAME_WON, GAME_LOST };
	private GameState gameState;
	
	public GameEngine(){
		tilesState = new boolean[NUM_TILES];
		resetTilesState();
		randomGenerator = new Random();
		gameState = GameState.PRE_DICE_ROLL;
	}
	
	public void addDisplay(Display display){
		this.display = display;
	}
	
	public boolean rollDice(){
		if(gameState != GameState.PRE_DICE_ROLL)
			return false;
		gameState = GameState.DURING_DICE_ROLL;
		int die1,die2;
		die1 = randomGenerator.nextInt(NUM_DIE_FACES)+1;
		die2 = randomGenerator.nextInt(NUM_DIE_FACES)+1;
		currentRoll = new DiceResult(die1,die2);
		display.updateDiceFinal(currentRoll);
		if(!hasValidMoves()){
			gameState = GameState.GAME_LOST;
			++numLosses;
			display.gameLostUpdate();
		}
		else
			gameState = GameState.POST_DICE_ROLL;
		return true;
	}
	
	public FlipTileOutcome flipTile(int tileNum){
		if(tileNum <= 0 || tileNum > NUM_TILES)
			throw new IllegalArgumentException(Integer.toString(tileNum));
		if(gameState != GameState.POST_DICE_ROLL){
			return FlipTileOutcome.DICE_NOT_ROLLED;
		}
		//tile is able to be flipped
		if(tilesState[tileNum-1] ==  false  && isValidMove(tileNum)){
			tilesState[tileNum-1] = true;
			display.flipTile(tileNum);
			if(++numTilesFlipped == NUM_TILES){
				gameState = GameState.GAME_WON;
				numWins++;
				display.gameWonUpdate();
			}
			else
				gameState = GameState.PRE_DICE_ROLL;
			return FlipTileOutcome.SUCCESS;
		}
		//tile could not be flipped 
		if(tilesState[tileNum-1] ==  true)
			return FlipTileOutcome.TILE_ALREADY_FLIPPED;
		else
			return FlipTileOutcome.INVALID_MOVE;	
	};
	
	public void newGame(){
		gameState = GameState.PRE_DICE_ROLL;
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
		if( currentRoll.getTotal() <= 9 && tilesState[currentRoll.getTotal()-1] == false)
			return true;
		return false;
	}
	
	private void resetTilesState(){
		Arrays.fill(tilesState, false);
		numTilesFlipped = 0;
	}
	
	public GameState getGameState(){
		return gameState;
	}

	public int getNumWins() {
		return numWins;
	}

	public int getNumLosses() {
		return numLosses;
	}
	
}
