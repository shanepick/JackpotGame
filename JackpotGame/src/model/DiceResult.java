package model;

import java.util.Random;

/**
 * This class is for storing the values of a dice roll where there are two dice.
 * Die values are specified in the constructor, or alternatively a DiceResult with 
 * random values can be created using the static createRandomDiceResult() method.
 * @author shane
 *
 */
public class DiceResult {
	
	private int die1Value;
	private int die2Value;
	private int numOfFaces;
	private static final Random randomGenerator = new Random();
	private static final int DEFAULT_NUM_FACES = 6;
	
	/**
	 * Constructs a DiceResult
	 * @param die1Value - The value of the first die.
	 * @param die2Value - The value of the second die.
	 * @param numOfFaces - the number of faces the die has (while standard die has 
	 * six, it is possible to have more).
	 */
	public DiceResult(int die1Value, int die2Value, int numOfFaces){
		this.die1Value=die1Value;
		this.die2Value=die2Value;
		this.numOfFaces=numOfFaces;
	}
	
	/**
	 * Constructs a DiceResult where both dice have six sides.
	 * @param die1 - The value of the first die.
	 * @param die2 - The value of the second die.
	 */
	public DiceResult(int die1,int die2){
		this(die1,die2, DEFAULT_NUM_FACES);
	}
	
	public int getDie1() {
		return die1Value;
	}

	public int getDie2() {
		return die2Value;
	}

	public int getNumFaces() {
		return numOfFaces;
	}
	
	public int getTotal() {
		return die1Value + die2Value;
	}
	
	@Override
	public String toString(){
		return "(" + die1Value + "," + die2Value + ")";
	}
	
	/**
	 * Creates a new DiceResult with random values for each die.
	 * Die Values will be between 1 and num_die_faces.
	 * @param num_die_faces - the number of faces for each die.
	 * @return the newly created DiceResult.
	 */
	public static DiceResult createRandomDiceResult(int num_die_faces){
		int die1,die2;
		die1 = randomGenerator.nextInt(num_die_faces)+1;
		die2 = randomGenerator.nextInt(num_die_faces)+1;
		return new DiceResult(die1,die2);
	}
	
	/**
	 * Creates a new DiceResult with random values for each die, where each die has six sides.
	 * @return the newly created DiceResult.
	 */
	public static DiceResult createRandomDiceResult(){
		return createRandomDiceResult(DEFAULT_NUM_FACES);
	}

}
