package Server;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Execute.StringHandling;
import Execute.Queue;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class Test extends JFrame {
	
	public static int currentPort = 5002;
	public static int server1Port = 5001;
	public static int server3Port = 5003;
	public static int time_logic = 0;
	public static StringHandling handle = new StringHandling();
	public static int accessQ = 0;
	
	public static String[] queue = new String[10];
	public static int top = -1;
	public static boolean CS = false;
	
	public static JTextArea txt = new JTextArea();
	public static JButton btnREQ = new JButton("REQ");
	public static JButton btnREL = new JButton("REL");
	private JPanel contentPane;
	
	public static void main(String[] args) throws Exception{
		new Test();
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
				pushQ(from_client);
				if(top >= 1)
				{
					sortQ();
				}
				txt.append("\n"+from_client);
				System.out.println("Hang doi hien tai:");
				printQ();
				Thread.sleep(1000);
				sendMess("ACQ", time_logic, currentPort, dis);
			}
			else if(mess.equalsIgnoreCase("ACQ"))
			{
				accessQ += 1;
				txt.append("\n"+from_client);
				if(accessQ >= 1)
				{
					btnREQ.setEnabled(false);
					btnREL.setEnabled(true);
					txt.append("\nĐã vào đoạn găng!");
				}
			}
			else if(mess.equalsIgnoreCase("REL"))
			{
				popQ();
				printQ();
				txt.append("\n"+from_client);
			}
		}
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnREQ.setBounds(73, 22, 89, 23);
		contentPane.add(btnREQ);
		
		btnREL.setBounds(253, 22, 89, 23);
		contentPane.add(btnREL);
		btnREL.setEnabled(false);
		
		txt.setBounds(10, 67, 414, 183);
		txt.setText("Server ready!");
		contentPane.add(txt);
				
		this.setTitle("Server");
		this.setVisible(true);
		
		btnREQ.addActionListener(new reqAction());
		btnREL.addActionListener(new relAction());
	}
	public static void sendMess(String mess, int time, int source, int dis) throws Exception
	{
		time += 1;
		String output = mess+"-"+source+"-"+time;
		if(mess == "REQ")
		{
			pushQ(output);
		}
		//Tạo socket cho client kết nối đến server qua ID address và port
		Socket server1Socket = new Socket("localhost",dis);
		//Tạo output stream nối với Socket
		DataOutputStream outToServer = new DataOutputStream(server1Socket.getOutputStream());
		//Gửi thông tin tới Server thông qua output stream đã nối với socket
		outToServer.writeBytes(output);
		server1Socket.close();
	}
	class reqAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				sendMess("REQ", time_logic, currentPort, server1Port);
				printQ();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	class relAction implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int temp = handle.portSplit(queue[top]);
				if(temp == currentPort)
				{
					sendMess("REL", time_logic, currentPort, server1Port);
					popQ();
					accessQ = 0;
					btnREL.setEnabled(false);
					btnREQ.setEnabled(true);
				}
				else
				{
					txt.append("\n Đoạn găng đang bận!");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//Xử lý
	public static void pushQ(String mess)
	{
		top += 1;
		queue[top] = mess;
	}
	public static void popQ()
	{
		top -= 1;
	}
	public static void printQ()
	{
		for(int i = top; i >=0; i--)
		{
			System.out.println(queue[i]);
		}
	}
	public static void sortQ()
	{
		for(int i = 0; i<= top-1; i++)
		{
			for(int j=i+1; j <= top; j++)
			{
				int tempI = handle.timelogicSplit(queue[i]);
				int tempJ = handle.timelogicSplit(queue[j]);
				if(tempI < tempJ)
				{
					swap(queue[i], queue[j]);
				}
				else if(tempI == tempJ)
				{
					int portI = handle.portSplit(queue[i]);
					int portJ = handle.portSplit(queue[j]);
					if(portI > portJ)
					{
						swap(queue[i], queue[j]);
					}
				}
			}
		}
	}
	public static void swap(String a, String b)
	{
		String temp = a;
		a = b;
		b = temp;
	}
}
