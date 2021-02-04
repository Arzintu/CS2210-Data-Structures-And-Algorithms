// CS2210 Assignment #2
// @author Pieter van Gaalen

public class OrderedDictionary implements OrderedDictionaryADT{

	// Class Attributes 
	private Node root;
	private int size;
	
	// Class Constructor
	public OrderedDictionary() {
		root = new Node();
		size = 0;
	}
	
	
	/* Accesor Method
	 * This method gets data record from dictionary
	 * @param k - key of dataItem to be retrieved
	 * @return - dataItem with match key, null if no dataItem is found   
	 */
	public DataItem get(Key k) {
		
		// Find key in ordered dictionary
		Node node = search(k);
		
		// Check if retrieved node exists
		if(node != null && node.hasData()) {
			return node.getData();
		}
		return null;
	}

	/* Mutator Method
	 * This method inserts a new dataItem in the dictionary
	 * @param d - new dataItem to be added to the dictionary
	 */
	public void put(DataItem d) throws DictionaryException {
		Node node = root;
		Key k = d.getKey();
		
		// Traverse BTS
		while(node.hasData()) {	
			int compare = k.compareTo(node.getData().getKey());	
			
			// Throw error if dataItem already exists in the ordered dictionary
			if(compare == 0) {
				throw new DictionaryException();
			}
			
			// New dataItem is smaller than dataItem in current node
			else if(compare == -1) {
				node = node.getLeft();
			}
			
			// New dataItem is larger than dataItem in current node
			else {
				node = node.getRight();
			}
		}
		
		// Insert dataItem at empty leaf node
		// Creates new left and right empty leaf nodes
		node.setToInternal(d);	
		
		// Track increase of size in dictionary
		size++;
	}

	
	/* Mutator Method
	 * This method deletes a dataItem in the dictionary
	 * @param k - key of dataItem to be removed
	 */
	public void remove(Key k) throws DictionaryException {

		Node node = search(k);
		
		// Case 0: Remove node that does not exist in dictionary - throw error
		if(node == null || node.isLeaf()) {
			throw new DictionaryException();
		}
		
		
		int numChildren = node.countChildren();
		
		// Case 1: Remove node that has no children - delete node contents
		if(numChildren == 0) {
			node.setToLeaf();
		}
		
		
		// Case 2: Remove node that has one child - move contents from child to node, delete child
		else if(numChildren == 1) {
			if(node.hasLeft()) {
				node.setData(node.getLeft());
				node.getLeft().setToLeaf();
			}
			else {
				node.setData(node.getRight());
				node.getRight().setToLeaf();
			}
		}
		
		// Case 3: Remove node that has both children - move contents from smallest node to node, delete smallest
		else {
			Node replace = smallestNode(node.getRight());
			node.setData(replace.getData());
			replace.setToLeaf();
		}
		
		// Track decrease of size in dictionary
		size--;
	
	}

	/* Accesor Method
	 * This method gets smallest key larger than k
	 * @param k - key to find successor of
	 * @return - dataItem if successor exist, null otherwise
	 */
	public DataItem successor(Key k) {
		
		// Use helper method
		Node node = successorNode(k);
		if(node != null) {
			return node.getData();
		}
		return null;
	}
	
	/* Helper Method
	 * This method gets smallest node with key larger than k
	 * @param k - key to find successor of
	 * @return - dataItem if successor exist, null otherwise
	 */
	private Node successorNode(Key k) {
		
		Node node = root;
		Node candidate = null;
		
		// Traverse ordered dictionary
		while(node.hasData()) {
			
			int compare = k.compareTo(node.getData().getKey());
			
			// Node smaller or equal to key 
			if(compare >= 0) {
				node = node.getRight();
			}
			
			// Node larger than key
			else{
				
				// Update successor
				candidate = node;
				node = node.getLeft();
			}
		}
		return candidate;
	}

	/* Accesor Method
	 * This method gets largest key smaller than k
	 * @param k - key to find predecessor of
	 * @return - dataItem if predecessor exist, null otherwise
	 */
	public DataItem predecessor(Key k) {
		
		// Use helper function
		Node node = predecessorNode(k);
		if(node != null) {
			return node.getData();
		}
		return null;
	}
	
	/* Helper Method
	 * This method gets largest node with key smaller than k
	 * @param k - key to find predecessor of
	 * @return - dataItem if predecessor exist, null otherwise
	 */
	private Node predecessorNode(Key k) {
		
		Node node = root;
		Node candidate = null;
		
		// Traverse ordered dictionary
		while(node.hasData()) {
			
			int compare = k.compareTo(node.getData().getKey());
			
			// Node smaller than key
			if(compare == 1) {
				// Update predecessor
				candidate = node;
				node = node.getRight();
			}
			
			// Node bigger or equal to key
			else{
				node = node.getLeft();
			}
		}
		return candidate;
	}
	

	/* Accesor Method
	 * This method gets smallest dataItem
	 * @return - dataItem if smallest dataItem exist, null otherwise
	 */
	public DataItem smallest() {
		
		// Use helper function
		Node smallest = smallestNode(root);
		if(smallest != null) {
			return smallest.getData();
		}
		return null;
	}
	
	/* Helper Method
	 * This method gets smallest dataItem node
	 * @return - node if smallest exist, null otherwise
	 */
	private Node smallestNode(Node node) {
		if(node == null) {
			return null;
		}
		
		// Traverse left most branch of tree
		while(node.hasLeft()) {
			node = node.getLeft();
		}
		return node;
	}

	/* Accesor Method
	 * This method gets largest dataItem
	 * @return - dataItem if largest exist, null otherwise
	 */
	public DataItem largest() {
		
		// Use Helper method
		Node largest = largestNode(root);
		
		if(largest != null) {
			return largest.getData();
		}
		return null;
	}
	
	/* Helper Method
	 * This method gets largest dataItem
	 * @return - dataItem if largest exist, null otherwise
	 */
	private Node largestNode(Node node) {
			
		if(node == null) {
			return null;
		}

		// Traverse right most branch
		while(node.hasRight()) {
			node = node.getRight();
		}
		return node;
	}
	
	/* Helper Method
	 * This method gets dataItem from ordered dictionary using a key
	 * @return - dataItem with matching key if exist, null otherwise
	 */	
	private Node search(Key k) {
		return search(root, k);
	}
	
	/* @ Overload
	 * Helper Method
	 * This method gets dataItem from ordered dictionary using a key
	 * @param node - node in ordered dictionary to search
	 * @param k - key to find in ordered dictionary
	 * @return - node with dataItem matching key if exist, null otherwise
	 */		
	private Node search(Node node, Key k) {
		
		// Check if node is valid 
		if(node.hasData()) {
			// System.out.println("Search Node: " + node.getData().getKey().getName());
			int compare = k.compareTo(node.getData().getKey());

			if(compare == 0) {
				return node;
			}
			
			else if(compare == -1 && node.hasLeft()) {
				return search(node.getLeft(), k);
			}
			
			else if(compare == 1 && node.hasRight()) {
				return search(node.getRight(), k);
			}
		}
		return null;
	}

	
	// Generic Accessor Function
	private int getSize() {
		return size;
	}
	
	
	// Generic Accessor Function
	private boolean isEmpty() {
		return size == 0;
	}
	
	/* Test Method
	 * Not required by assignment, but left it public to help TAs check my code if needed
	 * This method prints out a visual representation of the ordered dictionary 
	 */
	public void printDict() {

		System.out.println("===== TREE ====");
		printDict(root, "", 0);
	}
		
	// @Overload
	public void printDict(Node node, String branch, int depth) {
		
		if(node.hasData()) {
			
			Key key = node.getData().getKey();
			String name = key.getName();
			String kind = key.getKind();
			
			String side = "N - ";
			if(node.isRight()) side = "R - ";
			else if(node.isLeft()) side = "L - ";
			
			// Print Data
			System.out.println(branch + side + name + " " + kind);	
			
			
			
			// String representation of branches in terms of node depth
			if(node.isRight()) branch = branch.replace(Integer.toString(depth-1), " ");
			if(node.hasLeft() && node.hasRight()) branch = branch + Integer.toString(depth) + " ";	
			else branch = branch + "  ";
			


			// Recursion
			printDict(node.getLeft(), branch, depth + 1);
			printDict(node.getRight(), branch, depth + 1);
		}
	}

}
