package Server;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;

import Execute.StringHandling;

public class SVTest extends JFrame implements ActionListener {
	public static int currentPort = 5002;
	public static int server1Port = 5001;
	public static int server3Port = 5003;
	public static int time_logic = 0;
	public static StringHandling handle = new StringHandling();
	public static TextArea txt = new TextArea();
	
	Button bt;
	Panel pnMain, pnButton, pnText;
	int l = 50, s = 30, n = 15;
	
	public static void main(String[] args) throws Exception
	{
		new SVTest();
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
			//Tạo output stream, nối tới socket
			DataOutputStream outToClient = new DataOutputStream(con.getOutputStream());
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
				Thread.sleep(3000);
				sendMess("ACQ", time_logic, currentPort, dis);
			}
			else if(mess.equalsIgnoreCase("ACQ"))
			{
				txt.append("\n"+from_client);
				sendMess("REL", time_logic,currentPort,dis);
			}
			else if(mess.equalsIgnoreCase("REL"))
			{
				txt.append("\n"+from_client);
			}
		}
	}
	public SVTest() throws InterruptedException
	{
		txt.setText("Server 2!");
		bt = new Button("REQ");
		
		pnMain = new Panel(new GridLayout(2, 1));
		pnButton = new Panel(new FlowLayout());
		pnText = new Panel(new GridLayout());

		pnButton.add(bt);
		pnText.add(txt);
		pnMain.add(pnButton);
		pnMain.add(pnText);
		this.add(pnMain);
		this.setSize(l * 2 + s * n, l * 2 + s * n);
		this.setVisible(true);
		
		bt.addActionListener(this);
////		this.add(bt);
////		this.bt.setLocation(0, 0);
////		this.add(bt);
//		this.add(txt);
//		this.txt.setText("Server 2!");
//		this.setSize(l * 2 + s * n, l * 2 + s * n);
//		this.setVisible(true);
	}
	
	public static void sendMess(String mess, int time, int source, int dis) throws Exception
	{
		time += 1;
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
		// TODO Auto-generated method stub
		try {
			sendMess("REQ", time_logic, currentPort, server1Port);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
