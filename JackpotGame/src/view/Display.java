package view;

import model.DiceResult;
import model.GameEngine;

public interface Display {
	
	public void updateDiceIntermediate(DiceResult dice);
	
	public void updateDiceFinal(DiceResult dice);
	
	public void flipTile(int tileNum);
	
//	public void invalidTileUpdate(GameEngine.InvalidTileStatus status);
	
	public void gameLostUpdate();
	
	public void gameWonUpdate();
	
	public void newGameUpdate();
	
}
