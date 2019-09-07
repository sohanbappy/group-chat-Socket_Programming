

/**
 *
 * @author Bappy
 */
import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.util.logging.Level;
import java.util.logging.Logger;
  
    public class Client  
    { 
        final static int ServerPort = 1234; 

        public static void main(String args[]) throws UnknownHostException, IOException  
        { 
            Scanner scn = new Scanner(System.in); 

            System.out.println("Enter room keyword to Enter: ");
            String room_key = scn.nextLine();
            if(room_key.equalsIgnoreCase("rokomari_intern")){
            //help tips 
            System.out.println("===========================");
            System.out.println("Connected to room.......");
            System.out.println("--Followed by ur typing is ur ID--");
            System.out.println("type==> logout ==> to left");
            System.out.println("===========================");
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost"); 

            // establish the connection 
            Socket s = new Socket(ip, ServerPort); 

            // obtaining input and out streams 
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 


            // sendMessage thread 
            Thread sendMessage = new Thread(new Runnable()  
            { 
                @Override
                public void run() { 
                    while (true) { 

                        // read the message to deliver. 
                        String msg = scn.nextLine(); 
                        if(!msg.isEmpty()||msg!=""){  
                        try { 
                           // write on the output stream 
                            dos.writeUTF(msg); 
                        } catch (IOException e) { 
                            e.printStackTrace(); 
                        }
                       }
                    } 
                } 
            }); 

            // readMessage thread 
            Thread readMessage = new Thread(new Runnable()  
            { 
                @Override
                public void run() { 

                    while (true) { 
                        try { 
                            // read the message sent from client 
                            String msg = dis.readUTF(); 
                            System.out.println(msg); 
                        } catch (IOException e) { 

                            System.out.println("You Left!!");
                            try {
                                s.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;
                           
                        } 
                    } 
                } 
            }); 

            sendMessage.start(); 
            readMessage.start(); 
            }else{
            System.out.println("Invalid Room Key!!!!");
            }
        } 
    } 