// Homework 5 - Christian Auspland
// CFG Extension to lexaard



import java.util.*;
import Collections;
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
	public List<String> header = new ArrayList<String>();
	// A 2-D Arraylist to hold transition table
	public List<List<String>> transtab = new ArrayList<List<String>>();
	// For printing	
	public boolean is_automaton;
	public boolean is_regex;

	// Constructors
	public lexaard(String oname, String typename){
		name = oname;
		type = typename;
	}

	// New class for rules in a grammar
	// Class for rules
	public static class rule{
		// String containing the variable for this rule
		public String rule_var;

		// List of possible variables/terminals for this rule
		public List<String> rule_term = new ArrayList<String>();
		// COOL NEW form of rule_term with nested arraylist for maximum confusion
		public List<List<String>> rule_term = new ArrayList<List<String>>();


		public rule(){}
	}
	// New class for grammar formatting
	public static class grammar{
		// Name of grammar
		public String name;

		// Description of grammar
		public String description;

		// List of terminals
		public List<String> terminal = new ArrayList<String>();

		// List of variables
		public List<String> variable = new ArrayList<String>();

		// Array List of Rules
		public List<rule> rules = new ArrayList<rule>();

		// Constructor for grammar
		public grammar(String new_name){
			name = new_name;
		}

	}
	public lexaard(String oname, String typename, boolean is_am, boolean is_rgx){
		name = oname;
		type = typename;
		is_automaton = is_am;
		is_regex = is_rgx;
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
		String temp;


		// Set up standard input reading
		Scanner scan = new Scanner(System.in);
		// List of all defined automata
		List<lexaard> automata = new ArrayList<lexaard>();
		// List of all defined cfgs
		List<grammar> cfg = new ArrayList<grammar>();

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
					boolean found_cfg = false;

					// First, search see if the name is a cfg or an automata
					for (i = 0; i < cfg.size(); i++){
						if (Objects.equals(cfg.get(i).name, inputsplit[1])){
							// We have a match!
							found_cfg = true;
							print_position = i;
							break;	
						}
					}

					// We found a cfg, so let's print it out
					if (found_cfg){
						print_cfg(cfg.get(found_cfg));
					}
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
					if (automata.get(print_position).is_regex == true){
						System.out.print("");
					}
					if (found_flag){
						if ((automata.get(print_position).is_automaton == false) && (automata.get(print_position).is_regex == false)){
							System.out.println(automata.get(print_position).type);
							break;
						}
						// determine if regex
						if (automata.get(print_position).is_regex == true){
							System.out.print(automata.get(print_position).type);
							break;
						}
						if (Objects.equals(automata.get(print_position).iden, "gnfa")){
							System.out.print(automata.get(print_position).iden + "\n");
							for (i = 0; i < automata.get(print_position).alphabet.size(); i++){
								System.out.print(automata.get(print_position).alphabet.get(i) + " ");
							}
							for (i = 0; i < automata.get(print_position).header.size(); i++){
								System.out.print("     " + automata.get(print_position).header.get(i));
							}	
						}
						else{
							System.out.print(automata.get(print_position).iden + "\n");
							System.out.print("    ");
							for (i = 0; i < automata.get(print_position).alphabet.size(); i++){
								System.out.print("  " + automata.get(print_position).alphabet.get(i));
							}
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
						case "regex2fsa":
								

							break;

						case "cfg":
							// Search if name is already defined
							found_flag = false;

							for (i = 0; i < cfg.size(); i++){
								if (Objects.equals(cfg.get(i).name, inputsplit[1])){
									found_flag = true;
									def_position = i;
									break;
								}
							}
							// If the cfg already exists, rename it
							if (found_flag){
								cfg.get(def_position).name = inputsplit[1];
							} 
							// Otherwise, declare a new cfg
							else{
								cfg.add(new grammar(inputsplit[1]));	
								def_position = cfg.size() - 1;
							}
							// Read in next line (description)
							input = scan.nextLine();
							cfg.get(def_position).description = input;
							
							// From here, three lines are possible. A line defining a rule,
							// a line defining a set of terminals, and a line defining a set of 
							// variables. The input will read continuously until two 
							// newline characters are read.


							inputsplit = input.split("\\s+");

							loop_flag = true;
							int rule_pos = 0;
							while(loop_flag){
								input = scan.nextLine();
								inputsplit = input.split("\\s+");

								//  Checks to see if the input was empty(indicating a second)
								// newline character was sent
								if (input.trim().isEmpty()){
									loop_flag = false;
								}

								// First check for a rule
								if(Objects.equals(inputsplit[1], "->")){
									// It's time for a new rule!
									cfg.get(def_position).rules.add(new rule());
									rule_pos = cfg.get(def_position).rules.size() - 1;

									// Save rule variable, and add to list of variables
									cfg.get(def_position).rules.get(rule_pos).rule_var = inputsplit[0];	
									cfg.get(def_position).variable.add(inputsplit[0]);

									// Goes through 
									for (i = 2; i < inputsplit.length; i++){
										cfg.get(def_position).rules.get(rule_pos).rule_term.add(inputsplit[i]);
										cfg.get(def_position).terminal.add(inputsplit[i]);
									}
								}
								// Check for list of terminals
								else if (Objects.equals(inputsplit[0], "..")){
									for (i = 0; i < inputsplit.length; i++){
										cfg.get(def_position).terminal.add(inputsplit[i]);
									}
								}
								// The last possible input is a series of variables
								else {
									for (i = 0; i < inputsplit.length; i++){
										cfg.get(def_position).variable.add(inputsplit[i]);
									}	
								}
							
							}

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
									automata.get(def_position).is_regex = false;
								} else {
									automata.add(new lexaard(inputsplit[1], inputsplit[2], false, false));
								}
							// Assume that if the string starts with "(" character,
							// then it's a regex being defined.
							} else if (inputsplit[2].startsWith("(")){
								if(found_flag) {
									automata.get(def_position).name = inputsplit[1];
									automata.get(def_position).type = inputsplit[2];
									automata.get(def_position).is_automaton = false;
									automata.get(def_position).is_regex = true;
								} else {
									automata.add(new lexaard(inputsplit[1], inputsplit[2], false, true));
									// Change position to most recently added
									def_position = automata.size() - 1;
									// Add rest of the string
									for (i = 3; i < inputsplit.length; i++){
										automata.get(def_position).type = automata.get(def_position).type.concat(inputsplit[i]);
										System.out.println("how many times?" + i);
									}
									System.out.println(automata.get(def_position).type);
								}
								
							// It is an FSA
							} else {
								// Create new automata and store type(or edit old one)
								if (found_flag){
									automata.get(def_position).name = inputsplit[1];
									automata.get(def_position).type = inputsplit[2];
									automata.get(def_position).is_automaton = true;
									automata.get(def_position).is_regex = false;
								} else {
									automata.add(new lexaard(inputsplit[1], inputsplit[2], true, false));
									def_position = automata.size() - 1;	
								}
								// Scan and store identifier
								input = scan.nextLine();
								//inputsplit = input.split("\\s+");
								automata.get(def_position).iden = input; 
								
								// Scan and store alphabet								
								if (Objects.equals(automata.get(def_position).iden, "gnfa")){
									input = scan.nextLine();
									inputsplit = input.split("\\s+");
									for (i = 0; i < inputsplit.length; i++){
										automata.get(def_position).alphabet.add(inputsplit[i]);
									}
									input = scan.nextLine();
									inputsplit = input.split("\\s+");
									for (i = 0; i < inputsplit.length; i++){
										automata.get(def_position).header.add(inputsplit[i]);
									}
								
								} else {
									input = scan.nextLine();
									inputsplit = input.split("\\s+");
									for (i = 0; i < inputsplit.length; i++){
										automata.get(def_position).alphabet.add(inputsplit[i]);
									}
								}
								
								// Continuously look at StdIn until a single newline character is found
								// then the loop exits. The loops populates the transition table with
								// whatever it finds on StdIn.
								loop_iter = 0;
								boolean pflag = false;
								String t;
								int stay = 0;
								int ptotal = 0;
								char [] current_string;
								while(loop_flag){
									if (automata.get(def_position).transtab.size() <= loop_iter){
										automata.get(def_position).transtab.add(new ArrayList<String>());
									}
									input = scan.nextLine();
									inputsplit = input.split("\\s+");

									if (input.trim().isEmpty()){
										loop_flag = false;
									} else {
										stay = -1;
										for (i = 0; i < inputsplit.length; i++){
											current_string = inputsplit[i].toCharArray();
											if (inputsplit[i].startsWith("(")){
												if (pflag == false){
													automata.get(def_position).transtab.get(loop_iter).add(inputsplit[i]);
													stay = stay + 1;
												}
												pflag = true;
												ptotal = ptotal + 1;
											} 
											else {
												for (j = 0; j < current_string.length; j++) {
													if (Objects.equals(current_string[j], ')')) {
														ptotal = ptotal - 1;
														
													}
												}
												if (ptotal == 0){
													pflag = false;
												}
											}
											if (pflag){
												
												t = automata.get(def_position).transtab.get(loop_iter).get(stay).concat(inputsplit[i]);
												automata.get(def_position).transtab.get(loop_iter).set(stay, t);
											} else {
											automata.get(def_position).transtab.get(loop_iter).add(inputsplit[i]);
											stay = stay + 1;
											}
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


	public static void regex2fsa(lexaard a, String s){
		int i = 0;
		char [] current_string = s.toCharArray();
		String argument;

		// Check the 3rd character to decide what to do next
		for (i = 0; i < current_string.length; i++){
			// If another ( is found, then it's time to 
			// call the function again.
			if (Objects.equals(current_string[i], '(')){
				argument = new String(current_string, i, current_string.length - i);	
				regex2fsa(a, argument);

			// The end of a path has been reached.	
			} else if (Objects.equals(current_string[i], ')')){
				return;
			}
				
		}
		// Check the 3rd character to decide what to do next
		switch(current_string[2]){
			case '*':
				break;
			case '/':
				break;
			case '|':
				break;
			case '.':
				break;	
			default:
				break;
		}
	}
	/*public static String parse_regex(lexaard a, String s){	
		current_string = s.toCharArray();		
		
	}*/

	public static void print_cfg(grammar cfg){

		// temp lists for terminals and variables with no rules
		List<String> term_temp = new ArrayList<String>();
		List<String> var_temp = new ArrayList<String>();


		System.out.println(cfg.get(print_position).name);
		System.out.println(cfg.get(print_position).description);

		// Print out the rules, keeping track of what terminals
		// and variables have been used
		for (i = 0; i < cfg.get(print_position).rules.size(); i++){
			// Print and add present rule variable
			System.out.print(cfg.get(print_position).rules.get(i).rule_var + " ");
			var_temp.add(cfg.get(print_position).rules.get(i).rule_var);
			
			System.out.print("->");

			// Print and add all terminals
			for(k = 0; k < cfg.get(print_position).rules.get(i).rule_term.size(); i++){
				if(Objects.equals(cfg.get(print_position.rules.get(i).rule_term.get(k), "|"){
					// Do nothing
				} else {
					
				}	
			}
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

