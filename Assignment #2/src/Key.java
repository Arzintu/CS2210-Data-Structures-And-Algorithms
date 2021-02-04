// CS2210 Assignment #2
// @author Pieter van Gaalen

public class Key {
	
	// Class Attributes
	private String name;
	private String kind;
	
	/* Class Constructor
     * @param word is the name parameter of Key object
     * @param type is the kind parameter of Key object
     */
	public Key(String word, String type) {
		// Use mutator functions
		setName(word);
		setKind(type);
	}
	
	// Comparison function
	// @param k - input key to compare with this key
	// Returns 0 - if this Key object is equal to input k
	// Returns -1 - if this Key object is smaller than k
	// Returns 1 - if this Key object is larger than k
	public int compareTo(Key k) {
		
		// Compare name and kind of this.key and input key
		int compare_name = name.compareTo(k.getName());
		int compare_kind = kind.compareTo(k.getKind());
		
		// Return conditions 
		if(compare_name == 0 && compare_kind == 0) return 0;
		if(compare_name > 0 || (compare_name == 0 && compare_kind > 0)) return 1;
		return -1;
	}


	// Returns the string stored in instance variable name of this Key object
	public String getName() {
		return name;
	}

	// Set the string stored in instance variable name of this Key object
	private void setName(String name) {
		// Assignment requirement
		this.name = name.toLowerCase();
	}

	// Returns the string stored in instance variable kind of this Key object
	public String getKind() {
		return kind;
	}

	// Sets the string stored in instance variable kind of this Key object
	private void setKind(String kind) {
		this.kind = kind;
	}
}
