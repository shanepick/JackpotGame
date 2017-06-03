package model;

import java.util.Random;

public class DiceResult {
	
	private int die1Value;
	private int die2Value;
	private int numOfFaces;
	private static final Random randomGenerator = new Random();
	private static final int DEFAULT_NUM_FACES = 6;
	
	public DiceResult(int die1Value, int die2Value, int numOfFaces){
		this.die1Value=die1Value;
		this.die2Value=die2Value;
		this.numOfFaces=numOfFaces;
	}
	
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
	
	public static DiceResult createRandomDiceResult(int num_die_faces){
		int die1,die2;
		die1 = randomGenerator.nextInt(num_die_faces)+1;
		die2 = randomGenerator.nextInt(num_die_faces)+1;
		return new DiceResult(die1,die2);
	}
	
	public static DiceResult createRandomDiceResult(){
		return createRandomDiceResult(DEFAULT_NUM_FACES);
	}

}
