
import java.io.*; 
import java.util.*; 
import java.net.*;
    /**
     *
     * @author Bappy
     */
    class ClientHandler implements Runnable  
    { 
        Scanner scn = new Scanner(System.in); 
        private String name; 
        final DataInputStream dis; 
        final DataOutputStream dos; 
        Socket s; 
        boolean isloggedin; 


        // constructor 
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
                    Server.area.append("\n"+this.name+" : "+received);
                    System.out.println(this.name+" : "+received); 

                    if(received.equals("logout")){ 
                       --Server.i;
                       Server.area.append("\n"+this.name+" left");
                       for (ClientHandler mc : Server.active)  
                    { 
                            mc.dos.writeUTF(this.name+" left"); 

                    }
                        this.isloggedin=false; 
                        this.s.close(); 
                        break; 
                    }else{ 

                    for (ClientHandler mc : Server.active)  
                    {   
                        mc.dos.writeUTF(this.name+" : "+received); 

                    }
                   }
                      
                } catch (IOException e) { 
                    Server.area.append("\nSocket shutdown unexpectedly!!!!!");
                    //debugging socket close problem
                    e.printStackTrace();
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