// Homework 6 - Christian Auspland
// PDA define and print implementation


import java.util.*;
class lexaard
{
	/*
	 *
	 *
	 *	Lexaard class structure
	 *
	 *
	 */
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

	/*
	 *
	 *
	 *	Grammar Class Structure
	 *
	 *
	 */

	// New class for rules in a grammar
	// Class for rules
	public static class rule{
		// String containing the variable for this rule
		public String rule_var;

		// List of possible variables/terminals for this rule
		//public List<String> rule_term = new ArrayList<String>();

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

	/*
	 *
	 *
	 *	PDA Class Structure
	 *
	 *
	 */

	// Class containing pair info
	public static class token_pair{
	
		// String containing next state
		public String next_state;

		// String containing next stack operation
		public String next_op;

		// Empty Flag
		public boolean empty = false;

		public token_pair(){
		
		}
	}

	// Class containing the information in a token
	public static class token{

		// Array list of pairs in the token	
		public List<token_pair> pairs = new ArrayList<token_pair>();

		// A width meant for spacing in printing; starts 2 by default
		public int space_width = 2;

		// Number of pairs
		public int num_pairs = 0;

		// Constructor
		public token(){
		
		}
		
	}


	// Class containing the information in a group of tokens
	public static class token_group{
	
		// Hash map of groups. 
		// Key: stack alphabet member
		// Value: token 
		HashMap<String, token> myTokens = new HashMap<String, token>();

		public token_group(){
		}
	}

	// Class containing the information in a state of a PDA
	public static class PDA_state{
		// Boolean stating if the state is an accept state
		boolean accept = false;

		// Name of the state
		public String name;

		// Hashmap of groups of tokens within the state.
		// Key: input alphabet member
		// Value: group
		public HashMap<String, token_group> myGroups = new HashMap<String, token_group>();

		public PDA_state(){
		} 

	}

	// PDA Structure
	public static class PDA{
		// identifier of the PDA
		public String iden = "pda";

		// Name of the PDA
		public String pda_name;

		// Description of the PDA
		public String desc;

		// List of characters in the input alphabet
		public List<String> input_alph = new ArrayList<String>();

		// List of characters in the stack alphabet
		public List<String> stack_alph = new ArrayList<String>();

		// HashMap of states in the PDA
		// Key: name of state
		// Value: State

		//HashMap<String, PDA_state> myStates = new HashMap<String, PDA_state>();
		public List<PDA_state> myStates = new ArrayList<PDA_state>();

		public String start_state;


		public PDA(String name_pda)
		{
			pda_name = name_pda;	
		}
	}


	/*
	 *
	 *	End PDA Class
	 *
	 */




	public lexaard(String oname, String typename, boolean is_am, boolean is_rgx){
		name = oname;
		type = typename;
		is_automaton = is_am;
		is_regex = is_rgx;
	}


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
		// List of all defined PDAs
		List<PDA> myPDAs = new ArrayList<PDA>();

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
					boolean found_pda = false;

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
						print_cfg(cfg.get(print_position), print_position);
						break;
					}

					// Search to find a PDA
					for (i = 0; i < myPDAs.size(); i++){
						if (Objects.equals(myPDAs.get(i).pda_name, inputsplit[1])){
							found_pda = true;
							print_position = i;
							break;
						}
					}

					// If we found a PDA, let's print it out
					if ( found_pda){
						print_pda(myPDAs.get(print_position));
						break;
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
						case "cfgGen":
							// Evaluate a cfg for an input string
							// using the method described by theorem
							// 4.7 in the book
							break;
						case "chomskyNF":
							/*found_flag = false;

							for (i = 0; i < cfg.size(); i++){
								if (Objects.equals(cfg.get(i).name, inputsplit[1])){
									found_flag = true;
									def_position = i;
									break;
								}
							}
							if (found_flag)
								cfg.add(chomsky(cfg.get(def_position)));
							*/
							break;
						
						case "pda":
							found_flag = false;
							String current_name;

							// Search if name is already defined
							for (i = 0; i < myPDAs.size(); i++){
								if (Objects.equals(myPDAs.get(i).pda_name, inputsplit[1])){
									found_flag = true;
									def_position = i;
									break;
								}	
							}
							// If the pda already exists, rename it
							if (found_flag){
								myPDAs.get(def_position).pda_name = inputsplit[1];
								current_name = inputsplit[1];
							}
							else{
								myPDAs.add(new PDA(inputsplit[1]));
								def_position = myPDAs.size() - 1;
								current_name = inputsplit[1];
							}

							// Read in next line(description)
							input = scan.nextLine();	
							myPDAs.get(def_position).desc = input;

							// Read in next line(input alphabet)
							input = scan.nextLine();
							inputsplit = input.split("\\s+");

							for (i = 0; i < inputsplit.length; i++){
								myPDAs.get(def_position).input_alph.add(inputsplit[i]);	
							}

							// Read in next line(stack alphabet)
							input = scan.nextLine();
							inputsplit = input.split("\\s+");

							for (i = 0; i < inputsplit.length; i++){
								myPDAs.get(def_position).stack_alph.add(inputsplit[i]);
							}

							//Read in next line(States from here on)

							// While loop stuff
							loop_flag = true;
							char [] accept_test;
							PDA_state loop_state = new PDA_state();
							String current_state_name;
							boolean started = false;

							// Inner For Loop stuff
							token_group tg_loop = new token_group();
							token token_loop = new token();
							token_pair pair_loop = new token_pair();
							String [] split2;
							char [] width_test;
							String input_current;
							String stack_current;
							int current_state = 0;
							int current_pair = 0;


							// Loop until two consecutive new line characters are read
							while(loop_flag){
								input = scan.nextLine();
								inputsplit = input.split("\\s+");

								// Checks to see if the input was empty(indicating a second)
								// newline character was sent
								if (input.trim().isEmpty()){
									loop_flag = false;
									break;
								}
								current_state_name = inputsplit[0];
								if (!started){
									myPDAs.get(def_position).start_state = current_state_name;
									started = true;
								}

								// Update present state
								loop_state.name = current_state_name;

								// New element of hashmap
								myPDAs.get(def_position).myStates.add(new PDA_state());
								current_state = myPDAs.get(def_position).myStates.size() - 1;

								myPDAs.get(def_position).myStates.get(current_state).name = current_state_name;

								accept_test = current_state_name.toCharArray();
								if (Objects.equals(accept_test[0], '*')){
									myPDAs.get(def_position).myStates.get(current_state).accept = true;
								}

								// First for loop is the input alphabet
								for(i = 0; i < myPDAs.get(def_position).input_alph.size(); i++){
									// Kind of wild but here's what this is doing:
									// Adding a new element to the hashmap of groups in the
									// current state.
									input_current = myPDAs.get(def_position).input_alph.get(i);
									myPDAs.get(def_position).myStates.get(current_state).myGroups.put(input_current, new token_group());

									// Second for loop is the stack alphabet
									for (j = 0; j < myPDAs.get(def_position).stack_alph.size(); j++){

										stack_current = myPDAs.get(def_position).stack_alph.get(j);

										// I realize it's nearly impossible to see what
										// is going on here.
										// I'm adding a new element to the hashmap of
										// tokens in the present group
										myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.put(stack_current, new token()); 
										// Split on commas
										split2 = inputsplit[(3*i)+j+1].split(",");

										width_test=inputsplit[(3*i)+j+1].toCharArray();
										myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).space_width = width_test.length;
										if(split2.length == 1){
									//		pair_loop.next_state = "0";
									//		pair_loop.next_op = "0";
											myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).pairs.add(new token_pair());
											
											current_pair = myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).pairs.size()-1;

											myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).pairs.get(current_pair).next_state = "..";
											
											myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).pairs.get(current_pair).next_op = "..";
											
											myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).num_pairs += 1;
											myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).pairs.get(current_pair).empty = true;


										}
										else{
											int pair_num = split2.length;
											for (k = 0; k < (pair_num / 2); k++){
												//pair_loop.next_state = split2[2*k];
												//pair_loop.next_op = split2[(2*k)+1];
												//myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).pairs.add(pair_loop);

												myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).pairs.add(new token_pair());
											
											current_pair = myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).pairs.size()-1;

											myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).pairs.get(current_pair).next_state = split2[2*k];
											
											myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).pairs.get(current_pair).next_op = split2[(2*k)+1];
											
											myPDAs.get(def_position).myStates.get(current_state).myGroups.get(input_current).myTokens.get(stack_current).num_pairs += 1;

											}
										
										}	

									}
								}

							}

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
							String [] inputsplit2;
							String [] inputsplit3;

							while(loop_flag){
								input = scan.nextLine();
								inputsplit = input.split("\\s+");

								//  Checks to see if the input was empty(indicating a second)
								// newline character was sent
								if (input.trim().isEmpty()){
									loop_flag = false;
								}

								// First check for a rule
								else if(Objects.equals(inputsplit[1], "->")){

									// It's time for a new rule!
									cfg.get(def_position).rules.add(new rule());
									rule_pos = cfg.get(def_position).rules.size() - 1;

									// Save rule variable, and add to list of variables
									cfg.get(def_position).rules.get(rule_pos).rule_var = inputsplit[0];	
									cfg.get(def_position).variable.add(inputsplit[0]);

									// Change the split for formatting
									inputsplit = input.split("->");
									// split second string (terminals strings)
									inputsplit2 = inputsplit[1].split("\\|");
									
									for (i = 0; i < inputsplit2.length; i++){
										cfg.get(def_position).rules.get(rule_pos).rule_term.add( new ArrayList<String>());
										// Split terminal strings into individual 
										// characters(or strings)
										inputsplit3 = inputsplit2[i].split("\\s+");
										for (k = 0; k < inputsplit3.length; k++){
											// Add terminals
											cfg.get(def_position).rules.get(rule_pos).rule_term.get(i).add(inputsplit3[k]);
											cfg.get(def_position).terminal.add(inputsplit3[k]);
										}
									}
								}
								// Check for list of terminals
								else if (Objects.equals(inputsplit[0], "..")){
									for (i = 1; i < inputsplit.length; i++){
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
	
	public static grammar chomsky(grammar seeeffgee){
		// Four steps to converting to chomsky normal form according to 
		// theorem 2.9 in the book. Step one is create a new start state.	
		grammar cfg = seeeffgee;
		// create new rule
		rule start_state = new rule();
		// new variable for the rule is S_0
		start_state.rule_var = "S0";
		start_state.rule_term.add(new ArrayList<String>());
		start_state.rule_term.get(0).add(cfg.rules.get(0).rule_var);
		cfg.rules.add(0, start_state); 

		// Step 2 is to take care of all epsilon rules
		// aaaaaand it's 4 am...
		// Step 3 is to handle unit rules

		// Finally, convert remaining rules to proper form
		
		return cfg;
	}
	public static void print_pda(PDA pda)
	{
		char [] size_buf;
		int i,j,k;
		int space_count = 0;
		String input_index;
		String stack_index;
		
		// Print the type
		System.out.println("pda");

		// Type the name and description of the PDA
		System.out.println(pda.pda_name + " " + pda.desc);

		// Print the input alphabet
		System.out.print("       ");
		for (i = 0; i < pda.input_alph.size(); i++){
			input_index = pda.input_alph.get(i);
			System.out.print(pda.input_alph.get(i));
			for (k = 0; k < pda.stack_alph.size(); k ++){	
				stack_index = pda.stack_alph.get(k);
				for (j = 0; j < pda.myStates.get(0).myGroups.get(input_index).myTokens.get(stack_index).space_width; j++){
					if ((j == 0) && (i == 0 )){
						// Do nothing here
					}	
					else{
						System.out.print(" ");
					}
				}
				System.out.print(" ");
			}
			System.out.print("  ");
		}
		System.out.print("\n");

		// Print the Stack Alphabet
		System.out.print("       ");
		for (i = 0; i < pda.input_alph.size(); i ++){
			input_index = pda.input_alph.get(i);
			for (j = 0; j < pda.stack_alph.size(); j++){
				stack_index = pda.stack_alph.get(j);
				space_count = 0;
				size_buf = pda.stack_alph.get(j).toCharArray();
				space_count = size_buf.length;
				System.out.print(pda.stack_alph.get(j));
				for (k = space_count; k < pda.myStates.get(0).myGroups.get(input_index).myTokens.get(stack_index).space_width; k++){
					System.out.print(" ");
				}
				System.out.print(" ");
			}
			System.out.print(" ");
		}
		System.out.print("\n");

		int iter = 0;
		int num_pairs = 0;
		// Print the states and their data
		while(iter < pda.myStates.size()){
			if (pda.myStates.get(iter).accept){
				System.out.print("*");
			} else{
				System.out.print(" ");
			}


			System.out.print(pda.myStates.get(iter).name);
			System.out.print("    ");
			for (i = 0; i < pda.input_alph.size(); i++){
				input_index = pda.input_alph.get(i);
				space_count = 0;
				for (j = 0; j < pda.stack_alph.size(); j++){
					stack_index = pda.stack_alph.get(j);
					num_pairs = pda.myStates.get(iter).myGroups.get(input_index).myTokens.get(stack_index).num_pairs;
					for (k = 0; k < num_pairs; k++)
					{
					//	if ((k + 1) == num_pairs){
						if (pda.myStates.get(iter).myGroups.get(input_index).myTokens.get(stack_index).pairs.get(0).empty){
							System.out.print("..");	
						}
						else{
							System.out.print(pda.myStates.get(iter).myGroups.get(input_index).myTokens.get(stack_index).pairs.get(k).next_state);
							System.out.print(",");
							System.out.print(pda.myStates.get(iter).myGroups.get(input_index).myTokens.get(stack_index).pairs.get(k).next_op);
						}
					}
					System.out.print(" ");
				}
				System.out.print(" ");
			}
			System.out.print("\n");
			iter++;
		}
	}

	public static void print_cfg(grammar cfg, int position){
		
		// temp lists for terminals and variables with no rules
		List<String> term_temp = new ArrayList<String>(cfg.terminal);
		List<String> var_temp = new ArrayList<String>(cfg.variable);
		int i,j,k,p;

		
		System.out.println("cfg");
		System.out.print(cfg.name + " ");
		System.out.println(cfg.description);

		// Start printing rules
		// iterate through rules
		for (i = 0; i < cfg.rules.size(); i++){
			System.out.print(cfg.rules.get(i).rule_var + " ->");

			// remove a variable from var_term if it's used
			for (j = 0; j < var_temp.size(); j++){
				if (Objects.equals(var_temp.get(j), cfg.rules.get(i).rule_var)){
					var_temp.remove(j);
				}
			}
			// iterate through terminal strings
			for (j = 0; j < cfg.rules.get(i).rule_term.size(); j++){

				// iterate through through terminals
				for (k = 0; k < cfg.rules.get(i).rule_term.get(j).size(); k++){
					System.out.print(" " + cfg.rules.get(i).rule_term.get(j).get(k));

					// remove a terminal from term_temp if it's used
					for (p = 0; p < term_temp.size(); p++){
						if (Objects.equals(term_temp.get(p), cfg.rules.get(i).rule_term.get(j).get(k))){
							term_temp.remove(p);
						}
					}
				}
				if (j < cfg.rules.get(i).rule_term.size() - 1)
					System.out.print(" |");
			}
			if (i < cfg.rules.size()){
				System.out.print("\n");
			}
		}
		if (var_temp.size() > 0){
			for (i = 0; i < var_temp.size(); i++){
				System.out.print(var_temp.get(i) + " ");
			}
		}
		if (term_temp.size() > 0){
			System.out.print("\n.. ");
			for (i = 0; i < term_temp.size(); i++){
				System.out.print(term_temp.get(i) + " ");
			}
			System.out.print("\n");
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
