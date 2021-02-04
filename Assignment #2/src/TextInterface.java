// CS2210 Assignment #2
// @author Pieter van Gaalen

import java.io.*;
import java.util.Arrays;


public class TextInterface {
	
	/* Class main function
     * @param is the file name as input argument
     */
	public static void main(String[] args) {
		
		String fileName = "";
		
		// Store user input
		StringReader keyboard = new StringReader();
		
		// Store filename
		if(args.length == 1) {
			fileName = args[0];
		}
		
		// Error - Program will not run
		else {
			System.err.println("Error: Input file not specified");
			System.exit(1);
		}
		
		// Build dictionary from input file 
		OrderedDictionary dictionary = buildDictionary(fileName);
		
		// Print out valid commands to the user
		System.out.println(
				"\n"
				+ "List of commands: \n"
				+ "================ \n"
				+ "1. get name \n"
				+ "2. remove name type \n"
				+ "3. add name type content \n"
				+ "4. list prefix \n"
				+ "5. first \n"
				+ "6. last \n"
				+ "7. print \n"
				+ "8. end \n"
				+ "9. help \n");
	
		
		// Program run loop
		while(true) {
			
			// Program output 
			String line = keyboard.read("Enter next command: ");
			
			// Spilt user input so program can evaluate each input parameter individually
			String command[] = line.split(" ");
			
			// Get Command - used to get dataItem for any type of dataItem
			if(command[0].equals("get")) {
				
				// Check for valid amount of input parameters
				if(command.length == 2) {
					String kind[] = {"definition", "sound", "picture", "url", "program"};
					boolean found = false;
					
					// Get every type of content
					for(int i =0; i< kind.length; i++) {
					
						// Search dictionary
						Key key = new Key(command[1], kind[i]);
						DataItem dataItem = dictionary.get(key);
						
						// If key is found in dictionary
						if(dataItem != null) {
							
							// Track if item has been retrieved
							found = true;
							
							// Use provided classes to handle every file type
							
							// Sound
							if(kind[i].equals("sound")) {
								try {
									SoundPlayer player = new SoundPlayer();
									player.play(dataItem.getContent());								
								}
								
								catch(MultimediaException e) {
									System.out.println(e.getMessage());
								}					
							}
							
							// Picture
							else if(kind[i].equals("picture")) {
								try {
									PictureViewer viewer = new PictureViewer();
									viewer.show(dataItem.getContent());	
								}
								
								catch(MultimediaException e) {
									System.out.println(e.getMessage());
								}					
							}
							
							// Web
							else if(kind[i].equals("url")) {
								try {
									ShowHTML web = new ShowHTML();
									web.show(dataItem.getContent());
								}
								
								catch(Exception e) {
									System.out.println(e.getMessage());
								}					
							}
							
							// Program
							else if(kind[i].equals("program")) {
								try {
									RunCommand exe = new RunCommand();
									exe.run(dataItem.getContent());
								}
								
								catch(Exception e) {
									System.out.println(e.getMessage());
								}					
							}
							
							// Definition
							else {
								System.out.println(dataItem.getContent());
							}
							
						}						
					}
					
					// If no item has been found after searching all types
					if(!found){
						System.out.println("The word " + command[1] + " is not in the ordered dictionary");
						// TODO
					}
				}
				
				// Inform user of improper use of the command
				else {
					System.err.println("Error: Incorrect amount of parameters specified. Use: get name");
				}
			}
			
			// Remove command - used to remove dataItem from dictionary
			else if(command[0].equals("remove")) {
				
				// Check for valid amount of input parameters
				if(command.length == 3) {
					
					// Build dataItem that needs to be removed
					Key key = new Key(command[1], command[2]);
					
					// Check if record exists in the dictionary
					DataItem dataItem = dictionary.get(key);
					
					// DataItem exists in dictionary
					if(dataItem != null) {
						
						// Remove dataItem
						try {
							dictionary.remove(key);
							System.out.println("Success: Key removed from dictionary");
						}
						catch(DictionaryException e) {
						    System.err.println("Error: Failed to remove key from dictionary");
						}
						
					}
					
					// Key does not exist in dictionary
					else {
						System.out.println("No record in the ordered dictionary has key (w,k)");
					}
				}
				
				// Inform user of improper use of the command
				else {
					System.err.println("Error: Incorrect amount of parameters specified. Use: remove name type");
				}
			}
			
			// Add command - used to add a new dataItem to the dictionary
			else if(command[0].equals("add")) {
				
				// Check for valid amount of input parameters
				// Note: Special Case for type definition: Definition will contain more inputs parameters since input string is split by spaces
				// A definition will be a sentence containing multiple words, thus must be handled 
				if(command.length == 4 || (command.length >= 4 && command[2].equals("definition"))) {
					
					if(!command[2].equals("definition") && !command[2].equals("sound") && !command[2].equals("picture") && !command[2].equals("url") && !command[2].equals("program")) {
						System.err.println("Type Not Supprted: Use definition, sound, picture, url, or program");
					}
					
					else {
						// Special Handling for type definition
						String content = command[3];
						if(command[2].equals("definition")) {
							
							// Rejoin inputs after the command 'definition' 
							content = String.join(" ", Arrays.copyOfRange(command, 3, command.length));
						}
						
						if(!command[2].equals("definition") && !command[2].equals("sound") && !command[2].equals("picture") && !command[2].equals("url") && !command[2].equals("program")) {
							System.err.println("Type Not Supprted: Use definition, sound, picture, url, or program");
						}
						
						// Build new dataItem and use input parameters 
						Key key = new Key(command[1], command[2]);
						DataItem dataItem = new DataItem(key, content);
						
						// Add new dataItem to dictionary
						try {
							dictionary.put(dataItem);
							System.out.println("Success: DataItem added to dictionary");
						}
						catch(DictionaryException e) {
						    System.err.println("Error: Failed to add key to dictionary");
						}
					}					
				}
				
				// Inform user of improper use of the command
				else {
					System.err.println("Error: Incorrect amount of parameters specified. Use: add name type content");
				}
			}
			
			// List command - used to list all dataItem in the dictionary start in the same prefix in their name
			else if(command[0].equals("list")) {
				
				// Check for valid amount of input parameters
				if(command.length == 2) {
					
					// Build dataItem to search dictionary
					Boolean found = false;
					Key key = new Key(command[1], "");
					DataItem dataItem = dictionary.successor(key);
					
					// Check if dataItem exist and matches input prefix
					while(dataItem != null && dataItem.getKey().getName().startsWith(command[1]))  {
						
						key = dataItem.getKey();
						
						// Print out matching node
						if(found) System.out.print(", " + key.getName());
						else System.out.print(key.getName());
						
						// Find next closest node
						dataItem = dictionary.successor(key);
						
						found = true;
					}
					
				
					// No matching name prefix found
					if(found == false) {
						System.out.println("No name attributes in the ordered dictionary start with prefix " + command[1]);
					}
					
					else {
						System.out.print("\n");
					}

				}
				
				// Inform user of improper use of the command
				else {
					System.err.println("Error: Incorrect amount of parameters specified. Use: list prefix");
				}
				
			}
			
			// First command - used to print the smallest stored dataItem (lexicographical order by name)
			else if(command[0].equals("first") && command.length == 1) {
				
			
				DataItem dataItem = dictionary.smallest();
				Key key = dataItem.getKey();
				System.out.println(key.getName() + ", " + key.getKind() + ", " + dataItem.getContent());

			}
			
			// Last command - used to print the largest stored dataItem (lexicographical order by name)
			else if(command[0].equals("last") && command.length == 1) {
				
				DataItem dataItem = dictionary.largest();
				Key key = dataItem.getKey();
				System.out.println(key.getName() + ", " + key.getKind() + ", " + dataItem.getContent());
	
			}
			
			// End command - terminates program
			else if(command[0].equals("end") && command.length == 1) {
				
				System.out.println("Terminating Program");
				
				// Ends program run loop
				break;
			}
			
			// Print command - used to print out a visual representation of the dictionary 
			// Used for testing purposes of the code 
			else if(command[0].equals("print") && command.length == 1) {
				dictionary.printDict();
			}
			
			// Help command - used to show the use valid commands that the program accepts
			else if(command[0].equals("help") && command.length == 1) {
				System.out.println(
						"\n"
						+ "List of commands: \n"
						+ "================ \n"
						+ "1. get name \n"
						+ "2. remove name type \n"
						+ "3. add name type content \n"
						+ "4. list prefix \n"
						+ "5. first \n"
						+ "6. last \n"
						+ "7. print \n"
						+ "8. end \n"
						+ "9. help \n");
			}
			
			// Invalid command
			else {
				System.err.println("Invalid Command. Type 'help' to list all commands");
			}
		}

	}
	
	/* Private Helper Method
	 * This method builds a BTS tree based on the contents of an inputed text file 
	 * @param fileName - String containing the name of the file
	 * @return - dictionary 
	 */
	private static OrderedDictionary buildDictionary(String fileName){
		
		// Build new dictionary
		OrderedDictionary dictionary=new OrderedDictionary();
		
		try {
			
			// Read input file
			FileReader file = new FileReader(fileName);		
			BufferedReader input  = new BufferedReader(file);

			
			String name = input.readLine();
			String content = input.readLine();
	
			// Read input file by looping through each line 
			while(name != null) {
				
				String kind = "";
				
				// Determine the type of content based on the content string read form input file
				if(content.endsWith(".wav")|| content.endsWith(".mid")) kind = "sound";
				else if(content.endsWith(".gif")||content.endsWith(".jpg")) kind = "picture";
				else if(content.endsWith(".html")) kind = "url";
				else if(content.endsWith(".exe")) kind = "program";
				else kind = "definition";
				
				// Create new dataItem 
				Key key = new Key(name, kind);
				DataItem dataItem = new DataItem(key, content);
				
				// Store new data in dictionary
				dictionary.put(dataItem);
				
				// Read next two line of file
				name = input.readLine();
				content = input.readLine();
			}
			
			// Close file after done reading
			input.close();
			file.close();
		}
		
		// Error: Occurs when program:
		// is unable to read input file
		// or an error occurs when calling the put method
		catch (IOException e) {
		    System.err.println("Error: Unable to build dictionary");
		}
		
		// Return newly built dictionary
		return dictionary;
	}

}
