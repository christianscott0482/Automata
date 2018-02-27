import java.util.*;

public class vertex
{
    public char name;                 //Name of the vertex
    public ArrayList<Character> conedge = new ArrayList<Character>();   //list of edges connected to vertex
 
    public vertex(char vertexname){
        name = vertexname;
    }
    
    public vertex(char vertexname, char pairing){
        name = vertexname;
        conedge.add(pairing);
    }
    
    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        
        //get rid of white space and create character array
        char[] myGraph = input.replaceAll(" ","").toCharArray();
        int size = myGraph.length;
        boolean found = false;
        ArrayList<vertex> myVertices = new ArrayList<vertex>();
        int i = 0;
        
        //iterate through input string to find new vertexes.
        //starts at one and stops at size - 1 to handle the parentheses
        //surrounding the whole graph data set.
        for (i = 1; i < (size - 1); i++){
            //checks for start of new element
            if (myGraph[i] == '('){
                //checks if the element is a lone vertex or a pair
                if (myGraph[i+2] == ')'){
                    //if the element is a lone vertex, search to see if it's already been created
                    for (vertex vert : myVertices){
                        if (vert.name == myGraph[i+1]){
                            //vertex already found, move onto next element of input
                            found = true;
                            break;
                        } else {
                            //vertex hasn't been found yet, keep searching through myVertices
                            found = false;
                        }
                    }
                    //If the character was found, skip adding the vertex to myVertices.
                    //If character wasn't found, add it to myVertices
                    if (found == false){
                        myVertices.add(new vertex(myGraph[i+1])); 
                    }
                }
                //Checks if the element is a pair
                else if(myGraph[i+3] == ')'){
                    //searches for the first vertex in the list of myVertices
                    for (vertex vert : myVertices){
                        if (vert.name == myGraph[i+1]){
                            found = true;
                            //Add second vertex to list of vertices that vertex 1 is connected to
                            vert.conedge.add(myGraph[i+2]);
                            break;
                        } else {
                            found = false;
                        }
                    }
                    //if no matching vertex was found, add it
                    if (found == false){
                        myVertices.add(new vertex(myGraph[i+1], myGraph[i+2]));
                    }
                    //searches for the second vertex in the list myVertices
                    for (vertex vert : myVertices){
                        if (vert.name == myGraph[i+2]){
                            found = true;
                            //Add first identifier to list of vertices that vertex 2 is connected to
                            vert.conedge.add(myGraph[i+1]);
                            break;
                        } else {
                            found = false;
                        }
                    }
                    if (found == false){
                        //creates new vertex out of second identifier and connects it with the first
                        myVertices.add(new vertex(myGraph[i+2], myGraph[i+1]));
                    }
                }
                else {
                    //Something has gone wrong. Code broke or the input string was bad
                    System.out.println("Something has gone wrong processing the input graph");
                    System.exit(0);
                }
            }
        }
        
        //All data has been parsed and a list of vertices and every edge it connects to 
        //is stored in myVertices.
        
        ArrayList<vertex> A = new ArrayList<vertex>();
        ArrayList<vertex> B = new ArrayList<vertex>();
        size = myVertices.size();
        found = false;
        
        //buffer for holding location of vertices that need to be deleted
        ArrayList<vertex> dbuffer = new ArrayList<vertex>();
        
        //go through all the vertices and organize them into their proper section, A or B
        for (vertex vert : myVertices) {
            //reduce size of remaining vertices
            size -= 1;
            //check to see if the vertex belongs in A or B
            if(vert.conedge.size() > (0.5 * size)){
                //loop through the current pile A and remove any vertices that aren't 
                //connected to the vertice being added.
                found = false;
                for (vertex Avert : A) {
                    //loop through the the vertices connected edges for checking
                    for (char edge : vert.conedge){
                        //if an stored edge connection matches a vertex, don't delete the vertex
                        if (edge == Avert.name){
                            found = true;
                        }
                    }
                    //if no connection was found, store in deletion buffer
                    if (found == false){    
                        dbuffer.add(Avert);
                    }
                    //reset found flag
                    found = false;
                }
                //delete stored vertices
                for (vertex buffer : dbuffer) {
                    A.remove(buffer);
                }
                dbuffer.clear();
                A.add(vert);
            }
            else{
                //loop through the current pile B and remove any vertices that
                //it shares with the connections of the vertice being added
                for (vertex Bvert : B) {
                    //loop through the vertices' connected edges to find common vertices
                    for (char edge : vert.conedge) {
                        //if there is a match, send to deletion buffer
                        if (edge == Bvert.name) {
                            dbuffer.add(Bvert);
                        }
                    }
                }
                //delete vertices stored in deletion buffer
                for (vertex buffer : dbuffer) {
                    B.remove(buffer);
                }
                dbuffer.clear();
                B.add(vert);
            }
        }
        
        //Go through pile A and B and print them to standard out
        System.out.print("(");
        if (A.size() != 0) 
            System.out.print("(");
        for (vertex vert : A){
            System.out.print(vert.name + " ");
            
        }
        if(A.size() != 0)
            System.out.print(")");
        System.out.print(")" + "\n" + "(");
        if(B.size() != 0)
            System.out.print("("); 
        for (vertex vert : B) {
            System.out.print(vert.name + " ");
        }
        if (B.size() != 0)
            System.out.print(")");
        System.out.println(")");
    }
}