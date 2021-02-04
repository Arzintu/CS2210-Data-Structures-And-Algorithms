// CS2210 Assignment #3
// @author Pieter van Gaalen

public class Node {
	
	// Class Parameters
	private int name;
	private boolean mark;
	
	// Node Constructor
	public Node(int name) {
		this.setName(name);
		this.setMark(false);
	}
	
	// Generic Mutator and Accessor Functions for Node class
	
	public int getName() {
		return name;
	}


	public void setName(int name) {
		this.name = name;
	}
	
	public boolean getMark() {
		return mark;
	}

	public void setMark(boolean mark) {
		this.mark = mark;
	}
}
