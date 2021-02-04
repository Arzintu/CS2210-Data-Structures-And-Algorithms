// CS2210 Assignment #2
// @author Pieter van Gaalen

public class DataItem {
	
	// Class Attributes
	private Key key;
	private String content;
	
	/* Class Constructor
     * @param k is the key parameter of DataItem object
     * @param data is the content parameter of DataItem object
     */
	public DataItem(Key k, String data) {
		
		// Use mutator functions
		setKey(k);
		setContent(data);
	}
	
	// Returns the Key stored in instance variable key of this DataItem object
	public Key getKey() {
		return key;
	}
	
	// Sets the Key stored in instance variable key of this DataItem object
	private void setKey(Key key) {
		this.key = key;
	}

	// Returns the content stored in instance variable content of this DataItem object
	public String getContent() {
		return content;
	}

	// Sets the content stored in instance variable content of this DataItem object
	private void setContent(String content) {
		this.content = content;
	}
}
