package com.rockingsocket;

import java.io.*; 
import java.net.*; 
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
import javax.swing.JScrollPane;
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
        JButton btnNewButton_1,btnNewButton_2,btnNewButton_3;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200,50, 600, 610);
                
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
                JLabel title = new JLabel("Rocking Client");
                title.setFont(new Font("Tahoma", Font.BOLD, 18));
                title.setBounds(220, 10, 165, 34);
                contentPane.add(title);
                
		textField = new JTextField();
		textField.setBounds(28, 62, 165, 34);
		textField.setColumns(10);
		textField.setText("rokomari_intern");
		contentPane.add(textField);
		
		btnNewButton_1 = new JButton("Connect");
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
		
		btnNewButton_2 = new JButton("Log Out");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2.setBackground(Color.RED);
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                            try {
                                sendTask("logout");
                                //disabling send button
                                btnNewButton_2.setEnabled(false);
                                btnNewButton_3.setEnabled(false);
                                btnNewButton_1.setEnabled(true);
                            } catch (IOException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
		});
		btnNewButton_2.setBounds(340, 62, 240, 34);
                btnNewButton_2.setEnabled(false);
		contentPane.add(btnNewButton_2);
		
		
		area = new JTextArea();
		area.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                scrollPane.setBounds(28, 105, 330, 400);
		contentPane.add(scrollPane);
		
		textField_2 = new JTextField();
		textField_2.setBounds(28, 510, 310, 34);
		textField_2.setColumns(10);
		textField_2.setText("ur text goes here");
		contentPane.add(textField_2);
		
		btnNewButton_3 = new JButton("Send");
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_3.setBackground(Color.DARK_GRAY);
		btnNewButton_3.setForeground(Color.GREEN);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                            try {
                                sendTask(textField_2.getText());
                            } catch (IOException ex) {
                                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                            }
			}
		});
		btnNewButton_3.setBounds(340, 510, 240, 34);
                btnNewButton_3.setEnabled(false);
		contentPane.add(btnNewButton_3);
		
		JLabel lblId = new JLabel("*Enter Room Key");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId.setForeground(Color.BLACK);
		lblId.setBounds(360, 130, 200, 20);
		contentPane.add(lblId);
		
		JLabel lblId_1 = new JLabel("*Followed by ur typing is ur ID");
		lblId_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId_1.setForeground(Color.BLACK);
		lblId_1.setBounds(360, 155, 200, 20);
		contentPane.add(lblId_1);
		
		JLabel lblId_2 = new JLabel("*Press logout ==> to left ");
		lblId_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId_2.setForeground(Color.BLACK);
		lblId_2.setBounds(360, 180, 200, 20);
		contentPane.add(lblId_2);
                
           
            area.setText("Enter room keyword to Enter: ");
            
        }
         void connectTask() throws UnknownHostException, IOException{
            //CLI coding
            
            room_key = textField.getText();
            if(room_key.equalsIgnoreCase("rokomari_intern")){
                //disabling connect button
                btnNewButton_1.setEnabled(false);
                btnNewButton_2.setEnabled(true);
                btnNewButton_3.setEnabled(true);
            //help tips 
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
                        } catch (Exception e) { 
                            area.append("\nYou Left!!");
                            break;
                        } 
                    } 
                } 
            }); 
            readMessage.start();
            
            }else{
            area.append("\nInvalid Room Key!!!!");    
            }
        }
         void sendTask(String msg1) throws UnknownHostException,IOException{
            // sendMessage thread 
            Thread sendMessage = new Thread(new Runnable()  
            { 
                @Override
                public void run(){ 
                    while (true) { 
                            
                      // read the message to deliver.
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