// CS2210 Assignment #3
// @author Pieter van Gaalen

public class Edge {
	
	// Class Parameters
	private Node nodeu, nodev;
	private int type;
	private String label;

	// Edge Constructor for Corridor
	public Edge(Node u, Node v, int type) {
		nodeu  = u;
		nodev = v;
		this.setType(type);
	}
	
	// Edge Constructor for Door Type
	public Edge(Node u, Node v, int type, String label) {
		nodeu = u;
		nodev = v;
		this.setType(type);
		this.setLabel(label);
	}
	
	// Generic Mutator and Accessor Functions for Node class
	public Node firstEndpoint() {
		return nodeu;
	}
	
	public Node secondEndpoint() {
		return nodev;
	}

	public int getType() {
		return type;
	}

	public void setType(int newType) {
		this.type = newType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
