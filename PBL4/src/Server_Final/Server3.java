package Server_Final;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Execute.StringHandling;

public class Server3 extends JFrame implements ActionListener{
	public static int currentPort = 5003;
	public static int server1Port = 5001;
	public static int server2Port = 5002;
	public static int time_logic = 0;
	public static StringHandling handle = new StringHandling();
	
	public static JTextArea txt = new JTextArea();
	JButton btn = new JButton("REQ");
	private JPanel contentPane;

	public static void main(String[] args) throws Exception{
		new Server3();
		String from_client;
		String to_client;
		//Tạo socket server, chờ tại cổng 5002
		ServerSocket welcomSocket = new ServerSocket(currentPort);
		while(true)
		{
			String mess;
			int dis;
			int time;
			//Chờ yêu cầu từ client
			Socket con = welcomSocket.accept();
			//Tạo input stream, nối tới socket
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(con.getInputStream()));
			//Đọc thông tin từ socket
			from_client = inFromClient.readLine();
			from_client.trim();
			mess = handle.messSplit(from_client);
			dis = handle.portSplit(from_client);
			time = handle.timelogicSplit(from_client);
			time_logic = Math.max(time, time_logic);
			if(mess.equalsIgnoreCase("REQ"))
			{
				txt.append("\n"+from_client);
				Thread.sleep(2000);
				time_logic++;
				sendMess("ACQ", time_logic, currentPort, dis);
			}
			else if(mess.equalsIgnoreCase("ACQ"))
			{
				txt.append("\n"+from_client);
				Thread.sleep(2000);
				time_logic++;
				sendMess("REL", time_logic,currentPort,dis);
			}
			else if(mess.equalsIgnoreCase("REL"))
			{
				time_logic++;
				txt.append("\n"+from_client);
			}
		}
	}

	public Server3() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btn.setBounds(172, 25, 89, 23);
		contentPane.add(btn);
		
		txt.setBounds(10, 67, 414, 183);
		txt.setText("Server ready!");
		contentPane.add(txt);
		
		this.setTitle("Server3");
		this.setVisible(true);
		
		btn.addActionListener(this);
	}
	
	public static void sendMess(String mess, int time, int source, int dis) throws Exception
	{
		String output = mess+"-"+source+"-"+time;
		//Tạo socket cho client kết nối đến server qua ID address và port
		Socket server1Socket = new Socket("localhost",dis);
		//Tạo output stream nối với Socket
		DataOutputStream outToServer = new DataOutputStream(server1Socket.getOutputStream());
		//Gửi thông tin tới Server thông qua output stream đã nối với socket
		outToServer.writeBytes(output);
		server1Socket.close();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			time_logic++;
			sendMess("REQ", time_logic, currentPort, server1Port);
			sendMess("REQ", time_logic, currentPort, server2Port);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
