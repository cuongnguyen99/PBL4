package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import Interface.Server1_Interface;

public class Server1 {
	public static int currentPort = 5001;
	public static int server2Port = 5002;
	public static int time_logic = 0;
//	public static Socket server1Socket;
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
//	public void receiveMess()
//	{
//		
//	}
	public static void main(String[] args) throws Exception
	{
		ServerSocket welcomSocket = new ServerSocket(currentPort);
		sendMess("REQ",time_logic, server2Port);
		//Chờ yêu cầu từ client
		Socket con = welcomSocket.accept();
		//Tạo input stream, nối tới socket
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(con.getInputStream()));
		//Tạo output stream, nối tới socket
		DataOutputStream outToClient = new DataOutputStream(con.getOutputStream());
		//Đọc thông tin từ socket
		String from_client = inFromClient.readLine();
		from_client.trim();
		if(from_client.equalsIgnoreCase("ACQ"))
		{
			System.out.println("ACQ");
			System.out.println("Da tien vao doan gang, chuan bi giai phong tai nguyen...");
			sendMess("REL", time_logic, server2Port);
			System.out.println("Da giai phong tai nguyen!");
		}
	}
}
