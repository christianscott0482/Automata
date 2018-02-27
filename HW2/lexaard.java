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
		int i,j;
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
						
						input = scan.nextLine();
						inputsplit = input.split("\\s+");
						// Load in identifier
						automata.get(def_position).iden = inputsplit[0]; 
						
						
						input = scan.nextLine();
						inputsplit = input.split("\\s+");
						// Load in alphabet
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
					//print_automata(automata.get(def_position));

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
							System.out.println("hello");
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
		int current_state = 0;
		int char_position = 0;
		int i,j;
		String temp_string = "bogus";
		String [] temp_data;
		String next_state;
		String [] input = s.split("");
		char [] final_state;

		// Loop through all values of the input string
		for (i = 0; i < input.length; i++){
			// Find position of the character on transition table
			//
			System.out.println(a.alphabet.size() + "  " + input[i]); 
			for (j = 0; j < a.alphabet.size(); j++){
				if (Objects.equals(a.alphabet.get(j), input[i])){
					// Plus 1 is to account for formatting of 
					// the transition table arraylist
					char_position = j + 1;
					System.out.println("Do we make it here?");
					break;
				}
			}
			// Find the next state to move to
			System.out.println("Current state is: " + current_state + "char position is: " + char_position);
			next_state = a.transtab.get(current_state).get(char_position);


			// Find the index of where that next state is
			for (j = 0; j < a.transtab.size(); j++){
				System.out.println("j = " + j);
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
				if (Objects.equals(temp_string, next_state)){
					current_state = j;
					break;
				}
			}
		}
		// After all the strings have been evaluated,
		// determine if we are in an accept or 
		// reject state
		final_state = a.transtab.get(current_state).get(0).toCharArray();
		if (final_state[0] == '*'){
			System.out.println("accept");
		} else {
			System.out.println("reject");
		}
	return;	
	}
}

