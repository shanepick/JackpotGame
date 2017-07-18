package view;

import model.DiceResult;

/**
 * This is the interface to be implemented by the class that's responsible for displaying
 * changes to the game after various game events.  Classes that implement Display can then be added
 * to the gameEngine using the addDisplay() method in the GameEngine class.
 * @author shane
 *
 */
public interface Display {
	
	/**
	 * For displaying the new dice values as specified in the dice parameter.
	 * Used for displaying intermediate dice rolls to simulate a dice animation.
	 * Will be called from GameEngine's rollDice(int numRolls, int delay) method.
	 * This method can be left blank if don't wish to display intermediate dice rolls and only
	 * interested in final result.
	 * If no intermediate rolls required, then GameEgine's rollDice() can be called instead of 
	 * rollDice(int numRolls, int delay), as rollDice() will not call this method.
	 * @param dice is the DiceResult to be displayed.
	 */
	public void updateDiceIntermediate(DiceResult dice);
	
	/**
	 * For displaying the final dice value as specified in the dice parameter.
	 * This method should also be used to implement any other changes to the display required
	 * after the dice have been rolled (player instructions etc).
	 * @param dice is the DiceResult to be displayed.
	 */
	public void updateDiceFinal(DiceResult dice);
	
	/**
	 * For making changes to the display of the tiles to reflect the fact that the tile specified
	 * by tileNum is now in a flipped state.
	 * @param tileNum the tile to be flipped and displayed accordingly.
	 */
	public void flipTile(int tileNum);
	
	/**
	 * For implementing any changes to the display required after a game is lost.
	 */
	public void gameLostUpdate();
	
	/**
	 * For implementing any changes to the display required after a game is won.
	 */
	public void gameWonUpdate();
	
	/**
	 * For making any changes to the display required in preparation for a new game 
	 * (resetting tile images etc).
	 */
	public void newGameUpdate();
	
}
