package model;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import view.Display;

/**
 * This class is the game engine, which contains methods for performing player actions as well 
 * as keeping track of the game state.
 * @author shane
 *
 */
public class GameEngine {

	private static final int NUM_TILES = 9;
	//contains tile state: false corresponds to a tile not being flipped, and true for flipped.
	private boolean tilesState[]; 
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
		gameState = GameState.PRE_DICE_ROLL;
	}
	
	public void addDisplay(Display display){
		this.display = display;
	}

	/**
	 * Rolls the dice for the player and stores the DiceResult as the current roll.
	 * Will call updateDiceFinal() for the display, so the dice can be displayed.
	 * @return true if dice roll completed, otherwise returns false.
	 */
	public boolean rollDice(){
		//don't want to allow player to roll dice again if already rolled
		if(gameState != GameState.PRE_DICE_ROLL)
			return false;
		gameState = GameState.DURING_DICE_ROLL;
		currentRoll = DiceResult.createRandomDiceResult();
		display.updateDiceFinal(currentRoll);
		//change state to POST_DICE_ROLL or GAME_LOST if no moves possible.
		updateGameState();
		return true;
		
	}
	
	/**
	 * Rolls the dice for the player, and generates several intermediate DiceResults before
	 * producing the final DiceResult to be stored as the current roll. These intermediate 
	 * DiceResults can be used by the Display to simulate a rolling dice animation.
	 * For the intermediate DiceResults updateDiceIntermediate() is called for the display.
	 * For the final DiceResult updateDiceFinal() is called for the display.
	 * The timing between each successive dice update is controlled by the delay parameter.
	 * When numRolls is set to n, n-1 intermediate DiceResults will be produced, followed by 1
	 * final DiceResult.
	 * @param numRolls is the number of diceResults to be generated (including the final 
	 * DiceResult).
	 * @param delay is the number of milliseconds between each successive diceResult.
	 * @throws IllegalArgumentException when numRolls is < 2 or delay < 0.
	 * @return true if dice roll completed, otherwise returns false.
	 */
	public boolean rollDice(int numRolls, int delay){
		
		if(numRolls < 2)
			throw new IllegalArgumentException("numRolls must be positive integer");
		if(delay < 0)
			throw new IllegalArgumentException("delay value cannot be negative");
		if(gameState != GameState.PRE_DICE_ROLL)
			return false;
		gameState = GameState.DURING_DICE_ROLL;
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
		ses.schedule(new Runnable(){
			int count = 1;
			@Override
			public void run(){
				if(count <= numRolls){
					DiceResult intermediateRoll = DiceResult.createRandomDiceResult();
					if(count++ < numRolls){
						display.updateDiceIntermediate(intermediateRoll);
						ses.schedule(this, delay, TimeUnit.MILLISECONDS);
					}
					else{
						currentRoll = intermediateRoll;
						display.updateDiceFinal(currentRoll);
						updateGameState();
					}
				}
			}
		}, 0, TimeUnit.SECONDS);
		return true;
	}
	
	private void updateGameState(){
		if(!hasValidMoves()){
			gameState = GameState.GAME_LOST;
			++numLosses;
			display.gameLostUpdate();
		}
		else
			gameState = GameState.POST_DICE_ROLL;
	}
	
	/**
	 * Changes the state of the nth tile (where n is specified by tileNum) to indicate 
	 * the tile has been flipped.
	 * @param tileNum - the tile number of the tile to be flipped.
	 * (1st tile is tile number 1, not 0).
	 * @return a FlipTileOutcome where the value is either:
	 * 		DICE_NOT_ROLLED if not able to flip because the dice haven't been rolled yet.
	 * 		TILE_ALREADY_FLIPPED if the tile specified by tileNum is already in a flipped state.
	 * 		INVALID_MOVE if the tile specified by tileNum is not a valid move based on the current dice roll.
	 * 		SUCCESS if the flipTile method completed successfully and the tile's state was changed.
	 * @throws IllegalArgumentException if the tileNum is <= 0 or > NUM_TILES
	 */
	public FlipTileOutcome flipTile(int tileNum){
		if(tileNum <= 0 || tileNum > NUM_TILES)
			throw new IllegalArgumentException(Integer.toString(tileNum) + " is invalid tile number");
		if(gameState != GameState.POST_DICE_ROLL){
			return FlipTileOutcome.DICE_NOT_ROLLED;
		}
		//tile is able to be flipped
		if(tilesState[tileNum-1] ==  false && isValidMove(tileNum)){
			tilesState[tileNum-1] = true;
			display.flipTile(tileNum);
			//if all tiles are flipped, then game is won.
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
	
	/**
	 * Resets the gameState to PRE_DICE_ROLL and resets all the tiles to an unflipped state.
	 * newGameUpdate() is called for the display.
	 */
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
