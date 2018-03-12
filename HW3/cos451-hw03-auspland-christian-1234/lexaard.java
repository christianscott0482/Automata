// Homework 3 - Chrsitian Auspland
// Completed Questions:   1
// 
// Uncompleted Questions: 2
//			  3
//			  4
//			  5
//			  6




import java.util.*;
class lexaard
{
	// Name of Object
	public String name;
	// Type of atomaton
	public String type;
	// Identifier of the automaton
	public String iden;	
	// ArrayList of strings that are the alphabet of the automaton
	public List<String> alphabet = new ArrayList<String>();
	// A 2-D Arraylist to hold transition table
	public List<List<String>> transtab = new ArrayList<List<String>>();
	// For printing	
	public boolean is_automaton;

	// Constructors
	public lexaard(String oname, String typename){
		name = oname;
		type = typename;
	}

	public lexaard(String oname, String typename, boolean is_am){
		name = oname;
		type = typename;
		is_automaton = is_am;
	}

	//public lexaard(String typename, String idenname)

	public static void main(String args[])
	{
		boolean found_flag = false;
		boolean loop_flag = true;
		int loop_iter = 0;
		int print_position = 0;
		int def_position = 0;
		int run_position = 0;
		int am_position = 0;
		int new_position = 0;
		int i,j,k;
		List<String> states = new ArrayList<String>();
		List<List<String>> powerset = new ArrayList<List<String>>();

		String temp;


		// Set up standard input reading
		Scanner scan = new Scanner(System.in);
		// List of all defined automata
		List<lexaard> automata = new ArrayList<lexaard>();

		String input;
		String [] inputsplit;

		while(true){
			input = scan.nextLine();
			inputsplit = input.split("\\s+");

			// Find the input statement, exit if none found
			switch(inputsplit[0]){
				// Quit the interpreter
				case "quit":
					System.exit(0);

				// Print the automaton described on StdIn
				case "print":
					found_flag = false;

					// Seach through all automata identifiers, and set
					// flag whenever one is found.
					for (i = 0; i < automata.size(); i++){
						if (Objects.equals(automata.get(i).name, inputsplit[1])){
							// We have a match!
							found_flag = true;
							print_position = i;
							break;	
						}
					}
					// Check for the found flag, then print the contents
					// of the automaton in proper formatting
					if (found_flag){
						if (automata.get(print_position).is_automaton == false){
							System.out.println(automata.get(print_position).type);
							break;
						}
						//System.out.print(automata.get(print_position).name + "\n");
						//System.out.print(automata.get(print_position).type + "\n");
						System.out.print(automata.get(print_position).iden + "\n");
						System.out.print("    ");
						for (i = 0; i < automata.get(print_position).alphabet.size(); i++){
							System.out.print("  " + automata.get(print_position).alphabet.get(i));
						}
						System.out.print("\n");
						for (i = 0; i <  automata.get(print_position).transtab.size(); i++){
							for (j = 0; j < automata.get(print_position).transtab.get(i).size(); j++){
								System.out.print("  " + automata.get(print_position).transtab.get(i).get(j));
							}
							System.out.print("\n");
						}

					} else { 
						System.out.print("");
					}

					break;

				// Define new automaton
				case "define":	
					switch(inputsplit[2]){
						case "nfa2dfa":
							/*found_flag = false;
							print_position = 0;

							automata.add(new lexaard(input[1], "", true);

							new_position = automata.size();

							// Search for the automaton in question
							for (i = 0; i < automata.size(); i++){
								if (Objects.equals(automata.get(i).name, input[3])){
									found_flag = true;
									print_position = i;
								}		
							}	
							// After finding the automaton, get all of its states together.
							for (i = 0; i < automata.get(print_position).transtab.size(); i++{
								states.add(automata.get(print_position).transtab.get(i).get(0));
							}
							// Calculate Power Set

							for (i = 0; i < (1<<states.size()); i++){
								powerset.add(new ArrayList<String>());
								for (j = 0; j < states.size(); j++){
									if ((i & (1 << j)) > 0)
										powerset.get(j).add(states.get(j));	
								}
							}
							System.out.println(powerset);

							*/



							break;
						case "dfaUnion":
							break;
						case "nfaUnion":
							break;
						case "nfaConcat":
							break;
						case "nfaStar":
							break;
						case "prune":
							break;
						case "fsaEquivP":
							break;
						case "minDFA":
							break;
						default:
							// Search the already established automata to see if this
							// name has already been used.
							found_flag = false;

							for (i = 0; i < automata.size(); i++){
								if (Objects.equals(automata.get(i).name, inputsplit[1])){
									found_flag = true;
									def_position = i;
									break;
								}
							}
							// Check the type of input
							if (inputsplit[2].startsWith("\"")){
								// Trim off quotes
								inputsplit[2] = inputsplit[2].replace('\"', ' ');
								inputsplit[2] = inputsplit[2].trim();

								// Redefine if already exists, else create new automaton
								if (found_flag){
									automata.get(def_position).name = inputsplit[1];
									automata.get(def_position).type = inputsplit[2];
									automata.get(def_position).is_automaton = false;
								} else {
									automata.add(new lexaard(inputsplit[1], inputsplit[2], false));
								}
							} else {
								// Create new automata and store type(or edit old one)
								if (found_flag){
									automata.get(def_position).name = inputsplit[1];
									automata.get(def_position).type = inputsplit[2];
									automata.get(def_position).is_automaton = true;
								} else {
									automata.add(new lexaard(inputsplit[1], inputsplit[2], true));
									def_position = automata.size() - 1;	
								}
								// Scan and store identifier
								input = scan.nextLine();
								//inputsplit = input.split("\\s+");
								automata.get(def_position).iden = input; 
								
								// Scan and store alphabet								
								input = scan.nextLine();
								inputsplit = input.split("\\s+");
								for (i = 0; i < inputsplit.length; i++){
									automata.get(def_position).alphabet.add(inputsplit[i]);
								}
								
								// Continuously look at StdIn until a single newline character is found
								// then the loop exits. The loops populates the transition table with
								// whatever it finds on StdIn.
								loop_iter = 0;
								while(loop_flag){
									if (automata.get(def_position).transtab.size() <= loop_iter){
										automata.get(def_position).transtab.add(new ArrayList<String>());
									}
									input = scan.nextLine();
									inputsplit = input.split("\\s+");

									if (input.trim().isEmpty()){
										loop_flag = false;
									} else {
										for (i = 0; i < inputsplit.length; i++){
											automata.get(def_position).transtab.get(loop_iter).add(inputsplit[i]);
										}
									}
									loop_iter++;
								}
							}
					}
					break;

				// Run a string through the automaton
				case "run":
					found_flag = false;


					inputsplit[2] = inputsplit[2].replace('\"', ' ');
					inputsplit[2] = inputsplit[2].trim();

					// Check if the input is a predefined string
					for (i = 0; i < automata.size(); i++){
						if (Objects.equals(automata.get(i).name, inputsplit[2])){
							found_flag = true;
							run_position = i;
							break;
						}
					}
					// Find the automaton that will be ran
					for (i = 0; i < automata.size(); i++){
						if (Objects.equals(automata.get(i).name, inputsplit[1])){
							am_position = i;
							break;
						}
					}
					if (found_flag){
						// Chop off quotes from the predefined string
						// and run the function
						temp = automata.get(run_position).type.replace('\"', ' ');
						temp = temp.trim();
						run_automaton(automata.get(am_position), temp);
					} else {
						run_automaton(automata.get(am_position), inputsplit[2]);
					}

					break;

				// Incorrect formatting on StdIn
				default:
					System.exit(0);
			
			}
			// Loop cleanup
			found_flag = false;
			loop_flag = true;

		}
	}

	public static void run_automaton(lexaard a, String s){
		boolean found_path = false;
		boolean ep_flag = false;
		int char_position = 0;
		int ep_index = 0;
		int i,j,k;
		String temp_string = "bogus";
		String [] temp_data;
		String [] input = s.split("");
		String [] possible_next_states;
		List<String> next_state = new ArrayList<String>();
		List<Integer> current_state = new ArrayList<>();
		char [] final_state;


		current_state.add(0);
		next_state.add(a.transtab.get(0).get(0));
		

		// Look for an epsilon in the alphabet, and make note of it's index
		for (i = 0; i < a.alphabet.size(); i++) {
			if (Objects.equals(a.alphabet.get(i), "..")){
				// Add one to index to account for extra column
				// in transition table.
				ep_index = i + 1;
				ep_flag = true;
			}
		}
		// Loop through all values of the input string
		for (i = 0; i < input.length; i++){
			// Find position of the character on transition table
			for (j = 0; j < a.alphabet.size(); j++){
				if (Objects.equals(a.alphabet.get(j), input[i])){
					// Plus 1 is to account for formatting of 
					// the transition table arraylist
					char_position = j + 1;
					break;
				}
			}
			// Find the next state to move to
			// Split the state string by ',' to get all the possible
			// transitions.

			int next_state_size = next_state.size();
			
			for (k = 0; k < next_state_size; k++){
				// Look to see number of possible next states
				possible_next_states = a.transtab.get(current_state.get(k)).get(char_position).split(",");

				if (Objects.equals(next_state.get(k), "$")){
					// Do nothing, skip the step
					
				} else {
					// Set the first of the states (or the only one) as the next state
					next_state.set(k, possible_next_states[0]);
				


					// If the number of possible next states is more than 1....
					if (possible_next_states.length > 1){
						
						// Go through each of them, besides the first possible state
						// and add new next states as well current states to be
						// ran concurrently.
						for (j = 1; j < possible_next_states.length; j++){	
							// Newest State equal to current position of 
							// the possible new states
							next_state.add(possible_next_states[j]);
							// Each new current state will be the same,
							// changing later.
							current_state.add(current_state.get(k));
						}

					}	
					if (ep_flag){
						if (Objects.equals(a.transtab.get(current_state.get(k)).get(ep_index), "..")){
							// If the transition under the epsilon is empty, then do nothing
						} else {
							// Else add new states for the epsilon transitions.
							next_state.add(a.transtab.get(current_state.get(k)).get(ep_index));
							current_state.add(current_state.get(k));
						}					
					}
				}
			}
			for (k = 0; k < next_state.size(); k++){
				// If there is no next state for a certain input,
				// then stay at the same state.
				if (Objects.equals(next_state.get(k), "..")){
					next_state.set(k, a.transtab.get(current_state.get(k)).get(0));
				}
			}

			
			// Go through and find the next state for each concurrent path
			for (k = 0; k < next_state.size(); k++){
				found_path = false;
				// Check to 
				if (Objects.equals(next_state.get(k), "$")){
					break;
				}
				// Find the index of where that next state is
				for (j = 0; j < a.transtab.size(); j++){
					// Cutoff *, accept or reject isn't important 
					// right now.
					temp_data = a.transtab.get(j).get(0).split("");
					if (Objects.equals(temp_data[0], "*")){
						temp_string = a.transtab.get(j).get(0).replace('*', ' ');
						temp_string = temp_string.trim();
					} else {
						temp_string = a.transtab.get(j).get(0);
					}
					// Set the new current_state
					if (Objects.equals(temp_string, next_state.get(k))){
						current_state.set(k, j);
						found_path = true;
						break;
					}
				}
				if (!found_path){
					// Path hasn't been found, so we will use "$" string
					// to denote that the end of the path has been
					// reached, and to ignore path calculations with it
					next_state.set(k, "$");
				}
			}
		}
		// After all the strings have been evaluated,
		// determine if we are in an accept or 
		// reject state
		for (i = 0; i  < next_state.size(); i++){
			if (Objects.equals(next_state.get(i), "$")){
				// Do nothing, skip it
			} else{
				final_state = a.transtab.get(current_state.get(i)).get(0).toCharArray();
				if (Objects.equals(final_state[0], '*')){
					System.out.println("accept");
					return;
				}
			}	
		}
		System.out.println("reject"); 
		return;
	}
}

