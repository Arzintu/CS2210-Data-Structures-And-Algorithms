// CS2210 Assignment #3
// @author Pieter van Gaalen


import java.util.Iterator;
import java.util.ArrayList;


public class Graph implements GraphADT{
	
	// Class Parameters
	private Node nodeList[];
	private Edge edgeList[][];
	private int size;
	
	// Graph Constructor
	public Graph(int n){
		nodeList = new Node[n];
		edgeList = new Edge[n][n];
		size = n;
		
		for(int i = 0; i < n; i++) {
			nodeList[i] = new Node(i);
		}	
	}
	
	// Create a new Edge better two nodes 
	// Throw error if node already exists
	public void insertEdge(Node nodeu, Node nodev, int type) throws GraphException{
		insertEdge(nodeu, nodev, type, null);
	}

	// Overload Function 
	// Create a new Edge better two nodes 
	// Throw error if node already exists
	public void insertEdge(Node nodeu, Node nodev, int type, String label) throws GraphException {	
		int u = nodeu.getName();
		int v = nodev.getName();
				
		// Check that nodes are in graph bounds 
		if(0 <= v && v < size && 0 <= u && u < size)  {
			
			// Check that edge doesn't already exist
			if (v < size && u < size && edgeList[u][v] == null) {
				edgeList[u][v] = new Edge(nodeu, nodev, type, label);
				edgeList[v][u] = new Edge(nodev, nodeu, type, label);
			}
			
			// Throw error if edge exists
			else throw new GraphException("InsertEdge Error: Edge already connects nodes in graph");
		}
		
		// Throw error if nodes are out of bounds
		else throw new GraphException("InsertEdge Error: Node out of graph bounds");
	}

	// Accessor Function
	// Return node if node exists
	// Throw error if node does not exist
	public Node getNode(int u) throws GraphException {
		
		// Check bounds are inside graph
		if(0 <= u && u <= size) {
			return nodeList[u];
		}
		else throw new GraphException("GetNode Error: Node not in graph");
	}

	// Get edges
	// Return a list iterator of edges for a specified node
	public Iterator<Edge> incidentEdges(Node nodeu) throws GraphException {
		
		int u = nodeu.getName();
		ArrayList<Edge> adjacentList = new ArrayList<Edge>();
		
		// Check node bounds are inside graph
		if(0 <= u && u < size) {
			
			// Check node exists in graph
			if(nodeList[u] != null) {
				for(int v = 0; v < size; v++) {
					
					// Check if nodes are connected
					if(areAdjacent(nodeList[u], nodeList[v])) {
						
						// Ad neighbor to list
						adjacentList.add(edgeList[u][v]);
					}
				}
			}
			else throw new GraphException("IncidentEdges Error: Node not in graph");
		}
		else throw new GraphException("IncidentEdges Error: Node out of graph bounds");
	
		
		return adjacentList.iterator();
	}

	// Get a edge between two nodes 
	// Throw error if nodes are outside graph bounds
	public Edge getEdge(Node nodeu, Node nodev) throws GraphException {
		int u = nodeu.getName();
		int v = nodev.getName();
		
		// Check node bounds are inside graph
		if(0 <= u && u < size && 0 <= v && v < size) {
			return edgeList[u][v];
		}
		else throw new GraphException("getEdge Error: Node outside graph bounds");
	}


	public boolean areAdjacent(Node nodeu, Node nodev) throws GraphException {
		
		int u = nodeu.getName();
		int v = nodev.getName();
		
		// Check node bounds are inside graph
		if(0 <= u && u < size && 0 <= v && v < size) {
			
			// Check that both nodes exist inside the graph
			if(nodeList[u] != null && nodeList[v] != null) {
				
				// Check if nodes are connected
				if(edgeList[u][v] != null) {
					return true;
				}
			}
			else throw new GraphException("AreAdjacent Error: Node not in graph");
		}
		else throw new GraphException("AreAdjacent Error: Node out of graph bounds");
		
		
		return false;
	}
}
