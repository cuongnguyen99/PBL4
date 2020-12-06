package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
	public static int currentPort = 5002;
	public static int server1Port = 5001;
	public static int time_logic = 0;
//	public void printQueue()
//	{
//		
//	}
	public static void sendMess(String mess, int time_logic, int port) throws Exception
	{
		//Tạo socket cho client kết nối đến server qua ID address và port
		Socket server1Socket = new Socket("localhost",port);
		//Tạo output stream nối với Socket
		DataOutputStream outToServer = new DataOutputStream(server1Socket.getOutputStream());
		//Gửi thông tin tới Server thông qua output stream đã nối với socket
		outToServer.writeBytes(mess);
		server1Socket.close();
	}
	public static void receiveMess()
	{
		
	}
	public static void main(String[] args) throws Exception
	{
		String from_client;
		String to_client;
		//Tạo socket server, chờ tại cổng 5002
		ServerSocket welcomSocket = new ServerSocket(currentPort);
		
		while (true)
		{
			//Chờ yêu cầu từ client
			Socket con = welcomSocket.accept();
			//Tạo input stream, nối tới socket
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(con.getInputStream()));
			//Tạo output stream, nối tới socket
			DataOutputStream outToClient = new DataOutputStream(con.getOutputStream());
			//Đọc thông tin từ socket
			from_client = inFromClient.readLine();
			from_client.trim();
//			System.out.println(from_client);
			if(from_client.equalsIgnoreCase("REQ"))
			{
				System.out.println("REQ");
				sendMess("ACQ", time_logic, server1Port);
			}
			else if(from_client.equalsIgnoreCase("REL"))
			{
				System.out.println("REL");
			}
		}
	}
}
