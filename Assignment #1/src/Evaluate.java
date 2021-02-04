// @author Pieter van Gaalen

public class Evaluate {
	
	// Game State Variables
	private int boardRows;
	private int boardColumns;
	private int tilesNeeded;
	private int maxLevels;
	
	public char[][] gameBoard;
	
	
	/* Class Constructor
     * @param boardRow is the number of rows in the game board
     * @param boardColumn is the number of columns in the game board
     * @param tilesNeeded the number of adjacent tiles needed to win
     * @param maxLevels the maximum number of moves the computer explores
     */
	public Evaluate (int boardRows, int boardColumns, int tilesNeeded, int maxLevels) {
		
		// Set variables
		this.setBoardRows(boardRows);
		this.setBoardColumns(boardColumns);
		this.setTilesNeeded(tilesNeeded);
		this.setMaxLevels(maxLevels);
		
		// Create Board 
		gameBoard = new char[boardRows][boardColumns];
		for(int i = 0; i < boardRows; i++) {
		    for(int j = 0; j < boardColumns; j++) {
			gameBoard[i][j] = 'g';
		    }
		}
	}
	
	
	/* Mutator Method
     * This method creates a dictionary 
     * For this assignment the size of the dictionary must between 5 000 and 10 000 and  prime number
     * A size of 9 777 was chosen
     * @return - new dictionary
     */
	public Dictionary createDictionary() {
		// Create Dictionary of Size 97777 (prime number) 
		Dictionary dict = new Dictionary(97777);
		return dict;
	}
	
	/* Accessor Method
     * This method checks if game configuration is already stored in the dictionary
     * @param dict - stores all game configurations 
     * @return - data of repeated game configuration 
     */
	public Data repeatedConfig(Dictionary dict) {

		String config = createStringConfig();
		Data record = dict.get(config);
		return record;
	}
	
	/* Mutator Method
     * This method inserts game configuration in the dictionary
     * @param dict - stores all game configurations 
     * @param score - outcome of the game
     * @param level - number of moves 
     */
	public void insertConfig(Dictionary dict, int score, int level) {
		String config = createStringConfig();
		Data record = new Data(config, score, level);
		dict.put(record);
	}

	/* Mutator Method
     * This method stores the new move in the game board
     * @param row - row of the game board 
     * @param col - column of the game board
     * @param symbol - player making the move
     */
	public void storePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}
	
	/* Accesor Method
     * This method checks if square is empty
     * @param row - row of the game board 
     * @param col - column of the game board
     * @return - True if empty false otherwise
     */
	public boolean squareIsEmpty (int row, int col) {
		return (gameBoard[row][col] == 'g');
	}
	
	/* Accesor Method
	 * This method checks if tile belongs to computer
	 * @param row - row of the game board 
	 * @param col - column of the game board
	 * @return - True if computer false otherwise
	 */
	public boolean tileOfComputer (int row, int col) {
		return (gameBoard[row][col] == 'o');
	}

	/* Accesor Method
	 * This method checks if tile belongs to human
	 * @param row - row of the game board 
	 * @param col - column of the game board
	 * @return - True if human false otherwise
	 */
	public boolean tileOfHuman (int row, int col) {
		return (gameBoard[row][col] == 'b');
	}	
		
	/* Accesor Method
	 * This method checks if player won game
	 * @param symbol - player
	 */
	public boolean wins (char symbol) {
		// Check all win conditions 
		return this.checkRows(symbol) || this.checkColumns(symbol) || this.checkDiagonal(symbol);
	}
	
	
	/* Helper Method
	 * This method checks rows that satisfy win condition
	 * @param symbol - player
	 * @return - True if player won false otherwise 
	 */
	private boolean checkRows(char symbol) {
		
		// Row
		for(int row = 0; row < gameBoard.length; row++) {
			
			// Iterate and check columns for every row
			int count = 0;
			for(int col = 0; col < gameBoard[row].length; col++) {
				
				count++;
				// Win condition not satisfied 
				if(symbol != gameBoard[row][col]) count = 0;
				else if(count == tilesNeeded) return true;
			}
		}
		return false;
	}
	
	/* Helper Method
	 * This method checks column that satisfy win condition
	 * @param symbol - player
	 * @return - True if player won false otherwise
	 */
	private boolean checkColumns(char symbol) {
		
		// Columns
		for(int col = 0; col< boardColumns; col++) {
			
			// Iterate and check rows for every column
			int count = 0;
			for(int row = 0; row < boardRows; row++) {
				
				count++;
				// Win condition not satisfied 
				if(symbol != gameBoard[row][col]) count = 0;
				else if(count == tilesNeeded) return true;
			}
		}
		return false;
	}
	
	/* Helper Method
	 * This method checks diagonals that satisfy win condition
	 * @param symbol - player
	 * @return - True if player won false otherwise
	 */
	private boolean checkDiagonal(char symbol) {
		
		// Only examine diagonals that have enough adjacent tiles to satisfy win condition
		int lower = tilesNeeded - boardRows;
		int upper = boardColumns - tilesNeeded;
		
		// Check Ascending Diagonal (/)
		for(int k = lower; k <= upper; k++) {
			
			int count = 0;
			for(int j = 0; j < boardRows; j++) {
				
				// Check if in array bounds
				if(0 <= (j+k) && (j+k) < boardColumns) {
					// Debugging 
					// if(symbol == 'b') System.out.print(Integer.toString(count) + " " +Integer.toString(boardRows - j -1) + " " + Integer.toString(j+k) + "| ");

					//Record adjacent tiles
					count++;
					
					// Win condition not satisfied 
					if(symbol != gameBoard[boardRows - j - 1][j+k]) count = 0;
					else if(count == tilesNeeded) return true;
				}
			}
			
			// Debugging
			// if(symbol == 'b') System.out.println();					

		}

		// Check Descending Diagonal (\)
		for(int k = (tilesNeeded - boardRows); k <= (boardColumns - tilesNeeded); k++) {
			
			int count = 0;
			for(int j = 0; j < boardRows; j++) {
				
				// Check if in array bounds
				if(0 <= (j+k) && (j+k) < boardColumns) {
					
					// Debugging
					//if(symbol == 'b') System.out.print(Integer.toString(count) + " " +Integer.toString(j) + " " + Integer.toString(j+k) + "| ");
					
					//Record adjacent tiles
					count++;
					
					// Check Win 
					if(symbol != gameBoard[j][j+k]) count = 0;
					else if(count == tilesNeeded) return true;
				}
			}
			// Debugging
			//if(symbol == 'b') System.out.println();		

		}
		return false;
	}		

	/* Accesor Method
	 * This method checks if there are valid moves
	 * @return - True if draw false otherwise
	 */	
	public boolean isDraw() {
		for(int row = 0; row < gameBoard.length; row++) {
			for(int col = 0; col < gameBoard[row].length; col++) {
				if(gameBoard[row][col] == 'g') {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/* Accesor Method
	 * This method is the value that the computer needs to min max 
	 * @return - 3 for computer 
	 * 		  - 2 for human
	 * 		  - 2 for draw
	 * 		  - 1 for undecided
	 */	
	public int evalBoard() {
				
		// Computer Won
		if(this.wins('o')) {
			return 3;
		}
		
		// Human Won
		else if(this.wins('b')) {
			return 0;
		}
		
		// Draw
		else if(this.isDraw()) {
			return 2;
		}
		
		// Undecided Game
		else {
			return 1;
		}

	}
	
	/* Accesor Method
	 * This method converts game board to string representation 
	 * @return - string configuration of game board
	 */	
    private String createStringConfig() {
        String config = "";
        for (int i = 0; i < boardRows; i++) {
		    for (int j = 0; j < boardColumns; j++) {
		        config = config + gameBoard[i][j];
		    }
        }
        return config;
    }
	
	
	// Generic Mutator and Accessor Functions
	public int getBoardRows() {
		return boardRows;
	}

	public void setBoardRows(int boardRows) {
		this.boardRows = boardRows;
	}

	public int getTilesNeeded() {
		return tilesNeeded;
	}

	public void setTilesNeeded(int tilesNeeded) {
		this.tilesNeeded = tilesNeeded;
	}

	public int getBoardColumns() {
		return boardColumns;
	}

	public void setBoardColumns(int boardColumns) {
		this.boardColumns = boardColumns;
	}

	public int getMaxLevels() {
		return maxLevels;
	}

	public void setMaxLevels(int maxLevels) {
		this.maxLevels = maxLevels;
	}
	

}
