package Execute;

import java.util.Scanner;

import Server.Server1;
import Server.Server2;

public class Controll {
	public static int server3Port = 5003;
	public static int server1Port = 5001;
	public static int server2Port = 5002;
	public static void main(String[] args) throws Exception
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("1: Server1 to Server2");
		System.out.println("2: Server1 to Server3");
		int chose = sc.nextInt();
		int time = 0;
		switch(chose)
		{
			case 1:
			{
				Server1 sv1 = new Server1();
				sv1.sendMess("REQ", time, server1Port, server2Port);
			}
			case 2:
			{
				Server2 sv2 = new Server2();
			}
		}
		
	}
}
