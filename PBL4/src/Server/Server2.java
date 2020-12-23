package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import Execute.StringHandling;

public class Server2 extends Thread{
	public static int currentPort = 5002;
	public static int server1Port = 5001;
	public static int time_logic = 0;
	public static boolean cs = false; 			//True : Đang tồn tại 1 tiến trình trong đoạn găng || False : Đoạn găng đang trống
	public static StringHandling handle = new StringHandling();
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
	public static void main(String[] args) throws Exception
	{
		String from_client;
		String to_client;
		//Tạo socket server, chờ tại cổng 5002
		ServerSocket welcomSocket = new ServerSocket(currentPort);
		System.out.println("Server2 already...!");
		while (true)
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
				System.out.println(from_client);
				Thread.sleep(3000);
				sendMess("ACQ", time_logic, currentPort, dis);
			}
			else if(mess.equalsIgnoreCase("ACQ"))
			{
				System.out.println(from_client);
				System.out.println("Da tien vao doan gang, chuan bi giai phong tai nguyen...");
				sendMess("REL", time_logic,currentPort,dis);
				System.out.println("Da giai phong tai nguyen!");
			}
			else if(mess.equalsIgnoreCase("REL"))
			{
				System.out.println(from_client);
			}
		}
	}
}
