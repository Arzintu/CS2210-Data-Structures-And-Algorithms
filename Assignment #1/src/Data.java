// @author Pieter van Gaalen

public class Data {
	
	// Class Variables 
    private String key;    /* Row and column of the play */
    private int score;    /* Play's score               */  
    private int level;    /* Play's level               */

	
    /* Class Constructor
     * @param key - string representation of the configuration of the game board
     * @param score - integer that represents the outcome of the game
     * @param level - integer that represents the number of moves in the game 
     */
	public Data(String key, int score, int level) {
		this.key = key;
		this.score = score;
		this.level = level;
	}
	
	// Generic Mutator and Accessor Functions
	public String getKey() {
		return key;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
}
