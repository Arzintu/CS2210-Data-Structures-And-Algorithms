// CS2210 Assignment #3
// @author Pieter van Gaalen

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

public class Labyrinth {
	
	// Class Parameters
	private Graph graph;
	private int width, length, start, finish;
	private int key[] = new int[10];
	
	
	// Labyrinth Constructor
	// Creates a Labyrinth based on an input text file
	public Labyrinth(String inputFile) throws LabyrinthException{
		
		try {
			
			// Read input file
			FileReader file = new FileReader(inputFile);		
			BufferedReader input  = new BufferedReader(file);
			
			String scale = input.readLine();
			
			// Store Labyrinth Parameters
			width = Integer.parseInt(input.readLine());
			length = Integer.parseInt(input.readLine());
			
			// Build Graph
			graph = new Graph(width*length);
			
			// Save Key Data
			String keyString[] = input.readLine().split(" ");
			for(int i = 0; i < key.length; i++) {
				key[i] = Integer.parseInt(keyString[i]);
			}
			
			// Read input file by looping through each line	
			String line = input.readLine();

			int row = 0;

			// Read the labyrinth data
			while(line != null) {
				
				System.out.println(line);
				for(int col = 0; col < line.length(); col++) {
					
					int n = width * (row /2) + col / 2;
					char inputChar = Character.toLowerCase(line.charAt(col));

					// Room
					if(inputChar == 'i') {

					}				
	
					// Door
					else if(Character.isDigit(inputChar)) {
						// Vertical Corridor
						if(row % 2 == 1) {
							//System.out.printf("Door Edge %d %d \n", n, n+width);
							graph.insertEdge(graph.getNode(n), graph.getNode(n + width), Character.getNumericValue(inputChar), "Door");
						}
						else {
							//System.out.printf("Door Edge %d %d \n", n, n+1);
							graph.insertEdge(graph.getNode(n), graph.getNode(n + 1), Character.getNumericValue(inputChar), "Door");

						}
					}
					
					// Corridor
					else if(inputChar == 'c') {
						// Vertical Corridor
						if(row % 2 == 1) {
							//System.out.printf("Core Edge %d %d \n", n, n+width);
							graph.insertEdge(graph.getNode(n), graph.getNode(n + width), 0, "Corridor");
						}
						
						// Horizontal Corridor
						else {
							//System.out.printf("Core Edge %d %d \n", n, n+1);
							graph.insertEdge(graph.getNode(n), graph.getNode(n + 1), 0, "Corridor");
						}
			
					}
					
					// Wall
					else if(inputChar == 'w') {
						
					}
					
					// Start
					// Record start node position
					else if(inputChar == 's') {
						start = n;
					}
					
					// Exit
					// Record finish node position
					else if(inputChar == 'x') {
						finish = n;
					}
					
					
			
				}
				
				// Read next line of file
				line = input.readLine();
				row++;
			}
			
			// Close file after done reading
			input.close();
			file.close();
		}
		
		// Error: Occurs when program
		// is unable to read input file
		// or an error occurs when calling the put method
		catch (IOException | GraphException e) {
		    System.err.println("Error: Unable to build dictionary");
		}
	}
	
	public Iterator<Node> solve() {

		try {
			
			// Use helper method to get solution path for the labyrinth
			Stack<Node> path =  solve(graph.getNode(start), key);
			if(path != null) {
				return path.iterator();
			}
			
		// Error when calling helper function
		} catch (GraphException e) {
			 System.err.println("Error encountered solving maze");
		}
		
		// No solution 
		return null;
	}
	
	
	// Helper Method 
	// Modified Depth First Search 
	// Explore the first neighbor in the adjacency list
	// If neighbor is not a solution explore next neighbor
	// Check all neighbor nodes until solution is found or there are no more neighbors
	// Return null if there is no solution
	private Stack<Node> solve(Node node, int[] key) {
		

		Stack<Node> path = new Stack<Node>();
		Stack<Node> subpath = new Stack<Node>();

		// Set node to explored
		path.push(node);
		node.setMark(true);

		try {
			
			// Check if Node is at Finish
			if(node.getName() != graph.getNode(finish).getName()) {

				// Search neighboring nodes
				Iterator<Edge> neighbors = graph.incidentEdges(node);
				while(neighbors.hasNext()) {

					
					// Get neighbor nodes
					Edge edge = neighbors.next();
					Node neighbor = edge.secondEndpoint();
					System.out.printf("%d Neighbor %d \n", node.getName(), neighbor.getName());	
					
					int minKey = 0;
					boolean resetKey = false;

					// Check if neighbor is explored
					if(neighbor.getMark() == false) {
							
						// Check for Door
						if(edge.getLabel().equals("Door")) {
							
							//  Check key for door
							if(hasKey(key, edge.getType())) {
								minKey = getMinKey(key, edge.getType());
								resetKey = true;
								
								// Remove key
								key[minKey]--; 
							}
							
							// No key to open door
							// Skip neighbor evaluation
							else continue;
						}

						// Debugging Output 
						// System.out.printf("Edge %d %d \n", node.getName(), neighbor.getName());	
						// System.out.println(Arrays.toString(key));	
						//System.out.println(Arrays.toString(key));
						
						// Evaluate Neighbor
						subpath = solve(neighbor, key);
						
						// Check if solution found in sub path
						if(subpath != null && subpath.peek() == graph.getNode(finish)) {
							
							// Add sub path to path
							path.addAll(subpath);
							return path;
						}
						
						// Reset Key 
						// In this case the door is not part of the solution
						// So the key should become available for future solutions
						if(resetKey) {
							key[minKey]++;
						}
					}
				}
				
				// Remove mark on node
				// In this case the node is not part of the solution 
				// However, there might be an easier way to reach the node that uses less keys
				// These cases must be explored
				node.setMark(false);
				
				// Explored node is not a solution
				return null;
			}
			
			else return path;
			
			
		} catch (GraphException e) {
			System.out.println("Error: Encountered Error while solving");
			e.printStackTrace();
		}
				
		return null;		
	}
	
	
	// Helper Function
	// Used to evaluate if there is a key available to open a door
	private boolean hasKey(int[] key, int door) {
		for(int i = door; i<key.length; i++) {
			if(key[i] >= 1) {
				return true;
			}
		}
		return false;
	}
	
	// Helper Function
	// Used to determine which key to use open a door
	private int getMinKey(int[] key, int door) {
		for(int i = door; i<key.length; i++) {
			if(key[i] > 0) {
				return i;
			}
		}
		return 0;
	}
	
	
}
