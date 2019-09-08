


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
  
/**
 *
 * @author Bappy
 */

    public class Client  extends JFrame
    { 
        //variable declaration
	private JPanel contentPane;
	private JTextField textField,textField_1,textField_2;
        final static int ServerPort = 1234; 
        
        public static void main(String args[]) throws UnknownHostException, IOException  
        { 
            Client obj = new Client();
        } 
        public Client() throws UnknownHostException, IOException{
        
            //for UI
            //	JOptionPane.showMessageDialog(null, "Client Module initialized");
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
		
		textField = new JTextField();
		textField.setBounds(28, 62, 165, 34);
		textField.setColumns(10);
		textField.setText("enter room_key");
		contentPane.add(textField);
		
		JButton btnNewButton_1 = new JButton("Connect");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
			}
		});
		btnNewButton_2.setBounds(340, 62, 220, 34);
		contentPane.add(btnNewButton_2);
		
		
		textField_1 = new JTextField();
		textField_1.setBounds(28, 105, 310, 400);
		textField_1.setColumns(10);
		textField_1.setEditable(false);
		contentPane.add(textField_1);
		
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
            
            //CLI coding
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