


import java.io.*; 
import java.util.*; 
import java.net.*; 
    /**
     *
     * @author Bappy
     */

 
    public class Server  
    { 

        // Vector to store active clients 
        static Vector<ClientHandler> active = new Vector<>(); 

        // counter for clients 
        static int i = 0; 

        public static void main(String[] args) throws IOException  
        { 
            // server is listening on port 1234 
            ServerSocket ss = new ServerSocket(1234); 
            System.out.println("Server Started: ");
            System.out.println("Room Key: rokomari_intern ");
            System.out.println("===========================");
            Socket s; 

            // running infinite loop for getting 
            // client request 
            while (true)  
            { 
                // Accept the incoming client request 
                s = ss.accept(); 
                System.out.println("New client ==>" +s.getPort()); 

                // obtain input and output streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 


                // Create a new handler object for handling this request. 
                ClientHandler client= new ClientHandler(s,"guest#"+s.getPort(), dis, dos); 

                // Create a new Thread with this object. 
                Thread t = new Thread(client); 


                // add this client to active clients list 
                active.add(client); 

                // start the thread. 
                t.start(); 

                // increment i for new client. 
                // i is used for naming only, and can be replaced 
                // by any naming scheme 
                i++; 
                System.out.println("Active : "+i); 
            } 
        } 
    }
