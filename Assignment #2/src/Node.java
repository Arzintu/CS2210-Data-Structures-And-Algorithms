// CS2210 Assignment #2
// @author Pieter van Gaalen

public class Node {
	
	// Class Parameters
	private Node parent, left, right;
    private DataItem data;

	// Constructor Internal Node
	public Node(DataItem data) {
		this.data = data;
		parent = null;
		setParent(null);
		setLeft(new Node(this));
		setRight(new Node(this));
	}
	
	// Constructor Leaf Node
	public Node(Node parent) {
		data = null;
		setParent(parent);
		setLeft(null);
		setRight(null);
	}
	
	// Constructor Root Node
	public Node() {
		data = null;
		setParent(null);
		setLeft(null);
		setRight(null);
		
	}
	
	// Generic Mutator and Accessor Functions for Node class
	// Most of these function are self explanatory 
	// Not all functions were used, but makes the class more robust
	
	public DataItem getData() {
		return data;
	}

	// Set Data - DataItem Input
	public void setData(DataItem data) {
		this.data = data;
	}
	
	// @Overload
	// Set Data  - Node Input
	public void setData(Node node) {
		this.data = node.getData();
	}
	
	public boolean isRoot() {
		return parent == null;
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public void setLeft(Node left) {
		// Set Parent of child
		if(left != null) left.setParent(this);
		this.left = left;		
	}
	
	// Check if node is left child of parent
	public boolean isLeft() {
		if (parent != null)	return this == parent.getLeft(); 
		else return false;
	}
	
	// Check if node has left child
	public boolean hasLeft() {
		return left.getRight() != null;
	}
	
	public Node getRight() {
		return right;
	}
	
	public void setRight(Node right) {
		// Set Parent of child
		if(right != null) right.setParent(this);
		this.right = right;
	}
	
	// Check if node is right child of parent
	public boolean isRight() {
		if(parent != null) return this == parent.getRight();
		else return false;

	}
	
	// Check if node has right child
	public boolean hasRight() {
		return right.getData() != null;
	}
	
	// Count number of children (max = 2)
	public int countChildren() {
		int count = 0;
		if(hasRight()) count++;
		if(hasLeft()) count++;
		return count;
	}
	
	public void setNode(DataItem data, Node left, Node right) {
		setData(data);
		setLeft(left);
		setRight(right);
	}	
	
	public boolean isLeaf() {
		return data == null;
	}
	
	public boolean hasData() {
		return data != null;
	}
	
	public void setToLeaf() {
		DataItem empty = null;
		
		// Delete data
		setData(empty);
		
		// Delete children
		setLeft(null);
		setRight(null);
	}
	
	public void setToInternal(DataItem data) {
		
		// Set Data
		setData(data);
		
		// Add leaf node children
		setLeft(new Node(this));
		setRight(new Node(this));
	}
}
