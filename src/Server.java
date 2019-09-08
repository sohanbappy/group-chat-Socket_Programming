


import java.io.*; 
import java.util.*; 
import java.net.*; 
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
    /**
     *
     * @author Bappy
     */

 
    public class Server extends JFrame  
    { 
        //variables 
        private JPanel contentPane;
	private JTextField textField_1;
        static JTextArea area;
        // Vector to store active clients 
        static Vector<ClientHandler> active = new Vector<>();
        // counter for clients 
        static int i = 0; 
        
        public static void main(String[] args) throws IOException  
        { 
            Server obj = new Server();
        } 
        public Server() throws IOException{
                //for UI
                setBackground(Color.WHITE);
		setVisible(true);
		setResizable(false);
		setTitle("Server Module");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200,50, 400, 610);
                
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
                area = new JTextArea();
		area.setBounds(28, 105, 330, 400);
		area.setColumns(10);
		area.setEditable(false);
                area.setAutoscrolls(true);
//              JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(389, 66, 841, 202);
//		area.add(scrollPane);
		contentPane.add(area);
		
		JLabel lblId = new JLabel("*Server Started------");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId.setForeground(Color.BLACK);
		lblId.setBounds(35, 20, 200, 20);
		contentPane.add(lblId);
		
		JLabel lblId_1 = new JLabel("*Room Key==> rokomari_intern");
		lblId_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblId_1.setForeground(Color.BLACK);
		lblId_1.setBounds(35, 50, 250, 20);
		contentPane.add(lblId_1);
		
		JButton btnNewButton_1 = new JButton("About");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "Rocking Socket\nDeveloped as an assessment!!");
                        }
		});
		btnNewButton_1.setBounds(110, 515, 142, 34);
		contentPane.add(btnNewButton_1);
        
        
            //here Starts coding for Server
            ServerSocket ss = new ServerSocket(1234); 
            area.setText("Server Started---- ");
            area.append("\nRoom Key==> rokomari_intern ");
            area.append("\n===========================");
            Socket s; 

            /*
                running infinite loop for getting 
                client request 
             */
            while (true)  
            { 
                // Accept the incoming client request 
                s = ss.accept();
                area.append("\nNew client ==>"+s.getPort());
                
                // obtain input and output streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 

                // Create a new handler object for handling  request. 
                ClientHandler client= new ClientHandler(s,"guest#"+s.getPort(), dis, dos);
                Thread t = new Thread(client); 
                // add this client to active clients list 
                active.add(client);  
                t.start();
                // increment i for new client.
                i++; 
                area.append("\nActive : "+i); 
            } 
        }
    }
