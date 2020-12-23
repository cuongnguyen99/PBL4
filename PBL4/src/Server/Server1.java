package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Execute.StringHandling;

public class Server1 extends Thread{
	public static int currentPort = 5001;
	public static int server2Port = 5002;
	public static int server3Port = 5003;
	public static int time_logic = 0;
	public static int tam = 0;
	public static StringHandling handle = new StringHandling();
	public static int accessQ = 0;
	
	
	public static String[] queue = new String[10];
	public static int top = -1;
	public static boolean CS = false;
	
	public static void sendMess(String mess, int time, int source, int dis) throws Exception
	{
		time += 1;
		String output = mess+"-"+source+"-"+time;
		if(mess == "REQ")
		{
			top += 1;
			queue[top] = output;
		}
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
		ServerSocket welcomSocket = new ServerSocket(currentPort);
		System.out.println("Server1 already...!");
		Thread.sleep(3000);
//		sendMess("REQ", time_logic, currentPort, 5002);
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
				pushQ(from_client);
				sortQ();
				System.out.println(from_client);
				printQ();
				Thread.sleep(1000);
				sendMess("ACQ", time_logic, currentPort, dis);
			}
			else if(mess.equalsIgnoreCase("ACQ"))
			{
				accessQ += 1;
				System.out.println(from_client);
				if(accessQ >= 1)
				{
					System.out.println("Đã vào đoạn găng!");
					Thread.sleep(10000);
					sendMess("REL", time_logic, currentPort, dis);
					popQ();
					printQ();
				}
			}
			else if(mess.equalsIgnoreCase("REL"))
			{
				System.out.println(from_client);
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
			System.out.println("Hàng chờ hiện tại:");
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
