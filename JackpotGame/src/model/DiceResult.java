package model;

public class DiceResult {
	
	private int die1Value;
	private int die2Value;
	private int numOfFaces;
	
	public DiceResult(int die1Value, int die2Value, int numOfFaces){
		this.die1Value=die1Value;
		this.die2Value=die2Value;
		this.numOfFaces=numOfFaces;
	}
	
	public DiceResult(int die1,int die2){
		this(die1,die2,6);
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
	
	public String toString(){
		return "(" + die1Value + "," + die2Value + ")";
	}

}
