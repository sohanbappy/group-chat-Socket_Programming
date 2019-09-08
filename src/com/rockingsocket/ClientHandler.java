package com.rockingsocket;


import java.io.*; 
import java.util.*; 
import java.net.*;
    /**
     *
     * @author Bappy
     */
    class ClientHandler implements Runnable  
    {  
        private String name; 
        final DataInputStream dis; 
        final DataOutputStream dos; 
        Socket s; 
        boolean isloggedin; 


        // constructor for client
        public ClientHandler(Socket s, String name,DataInputStream dis, DataOutputStream dos) { 
            this.dis = dis; 
            this.dos = dos; 
            this.name = name; 
            this.s = s; 
            this.isloggedin = true; 
        } 


        @Override
        public void run() { 

            String received; 
            while (true)  
            { 
                try
                { 
                    // receive the string 
                    received = dis.readUTF();
                    
                    if(received.equalsIgnoreCase("logout")){
                    //decrement user
                    --Server.i;
                    //removing from active users
                    Server.active.remove(this);
                    Server.area.append("\n"+this.name+" left");
                       for (ClientHandler mc : Server.active)  
                        { 
                                mc.dos.writeUTF(this.name+" left"); 

                        }
                        break; 
                    }
                    Server.area.append("\n"+this.name+" : "+received);
                    for (ClientHandler mc : Server.active)  
                    {   
                        mc.dos.writeUTF(this.name+" : "+received); 

                    }
                    
                } catch (IOException e) { 
                    Server.area.append("\nSocket shutdown unexpectedly!!!!!");
                    break;
                } 


            } 
            try
            { 
                // closing resources 
                this.dis.close(); 
                this.dos.close(); 

            }catch(IOException e){ 
                e.printStackTrace(); 
            } 
        } 
} 