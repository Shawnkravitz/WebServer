import java.io.*;
import java.net.*;
import java.util.HashMap;
import javax.json.Json;
import javax.json.JsonObject;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WebServer{

    // Declare a server socket and a client socket for the server

    static  Socket clientSocket = null;
    static  ServerSocket serverSocket = null;
    public static HashMap array = new HashMap();
    protected static Database db = new Database();
    private static CreateNode cn = new CreateNode();


    public static void main(String args[]) {

        // add in some objects just for testing
        // localhost:2224/node/1
        // localhost:2224/node/1
        JsonObject node1 = cn.createNode("light1", "on");
        JsonObject node2 = cn.createNode("light2", "off");
        db.addNode(1, node1);
        db.addNode(2, node2);


        // Set default port number 2223
        int port_number=2224;

        System.out.println("WebServer"+
                "\nUsing port number="+port_number);


        // Try to open a server socket on port port_number
        try {
            serverSocket = new ServerSocket(port_number);
        }
        catch (IOException e){
            System.out.println("Couldn't open server socket");
        }

        // waits for incoming connection, creates thread for request
        while(true){
            try {
                clientSocket = serverSocket.accept();
                new clientThreadForURLConnection(clientSocket).start();
            }
            catch (IOException e) {
                System.out.println("Error: "+e);
            }
        }
    }


}

// As long as we receive data from the URL connection, send the data to the client.
class clientThreadForURLConnection extends Thread{

    Socket clientSocket = null;

    // patterns for matching different types of HTTP request using regex
    Pattern getNodePattern = Pattern.compile( "^GET\\s+/node/\\d+\\s.*" );
    Pattern getAllNodesPattern = Pattern.compile( "^GET\\s+/node\\s" );
    Pattern buildNodePattern = Pattern.compile( "POST\\s+/node/build/.*");



    public clientThreadForURLConnection(Socket clientSocket){
        this.clientSocket=clientSocket;
    }

    public void run()
    {
        DataInputStream is = null;
        DataInputStream in = null;
        PrintStream os = null;
        String urlString = null;
        String buffer=null;

        try{
            // Declare an input and an output stream

            is = new DataInputStream(clientSocket.getInputStream());
            os = new PrintStream(clientSocket.getOutputStream());

            // Getting the URL string from the client
            urlString = is.readLine();

        } catch(IOException ioe) {
            System.out.println("IOException: " + ioe.toString());
        } catch(Exception e) {
            System.out.println("Error: " + e.toString());
        }

        if (os != null && is != null) {
            try{

                System.out.println("Request received: " + urlString); // for troubleshooting

                // for getting single node
                Matcher m = getNodePattern.matcher(urlString);
                boolean match = m.matches();
                if (match){
                    // get node
                    System.out.println("Fetching node ...");
                    os.println(WebServer.db.getNode(1)); // cheating here, need to extract node number form URL
                    System.out.println("Response: " + WebServer.db.getNode(1)); // for trouble shooting
                }

                // for getting all nodes, not functional
                m = getAllNodesPattern.matcher(urlString);
                match = m.matches();
                if (match){
                    // get all nodes
                }

                // for building node and inserting into DB, not functional
                m = buildNodePattern.matcher(urlString);
                match = m.matches();
                if (match){
                    //add node
                }


                is.close();
                os.close();
                clientSocket.close();
            } catch(IOException e){
                System.out.println("Error: " + e.toString());
            } catch(Exception e) {
                System.out.println("Error: " + e.toString());
            }

        }
    }

}

