// @author Pieter van Gaalen

import java.util.LinkedList;


public class Dictionary implements DictionaryADT{
	
	
	// Class Attributes 
	private int entries = 0, size;
    private LinkedList<Data>[] table;
    
	/* Class Constructor
     * @param size is the size of the dictionary
     */
	public Dictionary(int size) {
		
		// Set Size
		this.size = size;
		
		// Hash Table Initialization 
		table = new LinkedList[size];
	} 
	
	/* Helper Method
	 * This method get the hash code for a game configuration 
	 * @param config - configuration for the game
	 * @return - Integer hash code 
	 */
	private int getIndex(String config) {
		
		int index = 0;
		int prime = 11;
		
		// Horner's Rule
		for(int i = config.length() - 1; i >= 0; i--) {
			int coef = config.charAt(i);
			
			// Modulus to prevent overflow 
			index = ( index * prime + coef ) % size;
		}
		return index;
	}

	
	/* Mutator Method
	 * This method puts new data record in dictionary
	 * @param record - game state data 
	 * @return - 1 for collision 0 otherwise 
	 */
	public int put(Data record) throws DuplicatedKeyException {
		
		// Find Hash Table Index 
		int collision = 1;
		int index = getIndex(record.getKey());

		// Check for collision
		if (get(record.getKey()) == null) {	
			
			// Check if separate chain exists 
			if(table[index] == null) {
				collision = 0;
				table[index] = new LinkedList<Data>();
			}
			table[index].add(record);
			entries++;
			
			return collision;
		}
		
		// Collision
		else throw new DuplicatedKeyException();
	}
	
	/* Mutator Method
	 * This method removes data record from dictionary
	 * @param config - game configuration
	 */
	public void remove(String config) throws InexistentKeyException {
		// Remove configuration
		try {
			table[getIndex(config)].remove(config);
			entries --;
		} 
		// Failed to remove configuration
		catch(Exception e) {
			throw new InexistentKeyException();
		}		
	}
	
	
	/* Accesor Method
	 * This method gets data record from dictionary
	 * @param record - game configuration
	 * @return - game record data  
	 */
	public Data get(String config) {
			
		// Find Hash Table Index 
		int index = getIndex(config);
		
		// Separate chain exists
		if(table[index] != null) {
			
			// Find location in separate chain
			int listindex = getListIndex(config);
			if(listindex != -1) {
				return table[index].get(listindex);
			}
		}
		
		// Configuration does not exist
		return null;
	}
	
	private int getListIndex(String config) {
		
		// Find Hash Table Index 
		int index = getIndex(config);
		
		// Separate chain exists
		if(table[index] != null) {
			
			// Traverse linked list
			for(int i = 0; i < table[index].size(); i++) {
				
				// Find value in linked list
				if(table[index].get(i).getKey().equals(config)) {
					return i;
				}
			}
		}
		
		// Configuration does not exist
		return -1;
	}
		
	// Generic Mutator and Accessor Functions
	public int numDataItems() {
		return entries;
	}


	public void setEntries(int entries) {
		this.entries = entries;
	}
	
}
