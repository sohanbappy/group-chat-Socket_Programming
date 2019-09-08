


import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
  
/**
 *
 * @author Bappy
 */

    public class Client  extends JFrame
    { 
        //variable declaration
	private JPanel contentPane;
	JTextField textField,textField_2;
        static JTextArea area;
        final static int ServerPort = 1234; 
        String room_key;
        DataOutputStream dos;
        public static void main(String args[]) throws UnknownHostException, IOException  
        { 
            Client obj = new Client();
        } 
        public Client() throws UnknownHostException, IOException{
        
            //for UI
           
		setBackground(Color.WHITE);
		setVisible(true);
		setResizable(false);
		setTitle("Client Module");
		
		setBounds(200,50, 600, 610);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textField = new JTextField();
		textField.setBounds(28, 62, 165, 34);
		textField.setColumns(10);
		textField.setText("rokomari_intern");
		contentPane.add(textField);
		
		JButton btnNewButton_1 = new JButton("Connect");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                            try {  
                                connectTask();
                            } catch (IOException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		});
		btnNewButton_1.setBounds(195, 62, 142, 34);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Log Out");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2.setBackground(Color.RED);
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                            try {
                                logOutTask();
                            } catch (IOException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
		});
		btnNewButton_2.setBounds(340, 62, 220, 34);
		contentPane.add(btnNewButton_2);
		
		
		area = new JTextArea();
		area.setBounds(28, 105, 310, 400);
		area.setColumns(10);
		area.setEditable(false);
                area.setAutoscrolls(true);
		contentPane.add(area);
		
		textField_2 = new JTextField();
		textField_2.setBounds(28, 510, 310, 34);
		textField_2.setColumns(10);
		textField_2.setText("ur text goes here");
		contentPane.add(textField_2);
		
		JButton btnNewButton_3 = new JButton("Send");
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_3.setBackground(Color.DARK_GRAY);
		btnNewButton_3.setForeground(Color.GREEN);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                            try {
                                sendTask();
                            } catch (IOException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		});
		btnNewButton_3.setBounds(340, 510, 220, 34);
		contentPane.add(btnNewButton_3);
		
		JLabel lblId = new JLabel("*Enter Room Key");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId.setForeground(Color.BLACK);
		lblId.setBounds(340, 130, 200, 20);
		contentPane.add(lblId);
		
		JLabel lblId_1 = new JLabel("*Followed by ur typing is ur ID");
		lblId_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId_1.setForeground(Color.BLACK);
		lblId_1.setBounds(340, 155, 200, 20);
		contentPane.add(lblId_1);
		
		JLabel lblId_2 = new JLabel("*Type=> logout <= to left ");
		lblId_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId_2.setForeground(Color.BLACK);
		lblId_2.setBounds(340, 180, 200, 20);
		contentPane.add(lblId_2);
                
            Scanner scn = new Scanner(System.in);
            System.out.println("Enter room keyword to Enter: ");
            area.setText("Enter room keyword to Enter: ");
            
        }
         void connectTask() throws UnknownHostException, IOException{
            //CLI coding
            
            room_key = textField.getText();
            if(room_key.equalsIgnoreCase("rokomari_intern")){
            //help tips 
            System.out.println("===========================");
            System.out.println("Connected to room.......");
            System.out.println("--Followed by ur typing is ur ID--");
            System.out.println("type==> logout ==> to left");
            System.out.println("===========================");
            area.append("\n===========================\nConnected to room.......\n--Followed by ur typing is ur ID--");
            area.append("\ntype==> logout ==> to left\n===========================");
            // getting localhost ip 
            InetAddress ip = InetAddress.getByName("localhost"); 

            // establish the connection 
            Socket s = new Socket(ip, ServerPort); 
            // obtaining input and out streams 
            DataInputStream dis = new DataInputStream(s.getInputStream()); 
            dos = new DataOutputStream(s.getOutputStream()); 

            // readMessage thread 
            Thread readMessage = new Thread(new Runnable()  
            { 
                @Override
                public void run() { 

                    while (true) { 
                        try { 
                            // read the message sent from client 
                            String msg = dis.readUTF();
                            area.append("\n"+msg);
                            System.out.println(msg); 
                        } catch (Exception e) { 
                            area.append("\nYou Left!!");
                            System.out.println("You Left!!");
                            break;
                           
                        } 
                    } 
                } 
            }); 
            readMessage.start();
            
            }else{
            area.append("\nInvalid Room Key!!!!");    
            System.out.println("Invalid Room Key!!!!");
            }
        }
         void sendTask() throws UnknownHostException,IOException{
                        // sendMessage thread 
            Thread sendMessage = new Thread(new Runnable()  
            { 
                @Override
                public void run(){ 
                    while (true) { 

                      // read the message to deliver.
                        String msg1 = textField_2.getText();
                        if(!msg1.isEmpty()||msg1!=""){  
                        try { 
                           // write on the output stream 
                            dos.writeUTF(msg1);//to server
                            break;
                        } catch (Exception e) { 
                            e.printStackTrace(); 
                        }
                       }
                    } 
                } 
            }); 

            sendMessage.start(); 
            
        }
            void logOutTask() throws UnknownHostException,IOException{
                        // sendMessage thread 
            Thread sendMessage = new Thread(new Runnable()  
            { 
                @Override
                public void run(){ 
                    while (true) { 

                      // read the message to deliver.
                        String msg1 = "logout";
                        if(!msg1.isEmpty()||msg1!=""){  
                        try { 
                           // write on the output stream 
                            dos.writeUTF(msg1);//to server
                            break;
                        } catch (Exception e) { 
                            e.printStackTrace(); 
                        }
                       }
                    } 
                } 
            }); 

            sendMessage.start(); 
            
        }
        
    } 