package view;

import model.DiceResult;

public interface Display {
	
	public void updateDiceIntermediate(DiceResult dice);
	
	public void updateDiceFinal(DiceResult dice);
	
	public void flipTile(int tileNum);
	
	public void gameLostUpdate();
	
	public void gameWonUpdate();
	
	public void newGameUpdate();
	
}
