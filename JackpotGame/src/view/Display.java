package view;

import model.DiceResult;
import model.GameEngine;
import model.GameEngine.InvalidTileStatus;

public interface Display {
	
	public void updateDiceIntermediate(DiceResult dice);
	
	public void updateDiceFinal(DiceResult dice);
	
	public void updateTiles(boolean tileStates[]);
	
	public void invalidTileUpdate(GameEngine.InvalidTileStatus status);
	
	public void gameLostUpdate();
	
	public void gameWonUpdate();
	
	public void newGameUpdate();
	
}
