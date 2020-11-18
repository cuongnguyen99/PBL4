package demo;
import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server1
{
	//Host of server
	private static final String currentSV = "Server1";
	private static final String server2 = "Server2";
	private static final String server3 = "Server3";
	
	private static final int currentPort = 5001; //Port of server1
	private static final int PORT2 = 5002; //Port of server2
	private static final int PORT3 = 5003; //Port of server3
	private static Registry re;
	
	public Server1() {} 
	
	public void Regist() throws Exception 
	{
		re = LocateRegistry.createRegistry(currentPort);
		
		HelloImpl obj = new HelloImpl();
		Hello stub = (Hello)UnicastRemoteObject.exportObject(obj,0);
		re.rebind(currentSV, obj);
		System.out.println("Server1 is readdy!");
	}
	
	public void ComunicateWithServer2() throws Exception
	{
		System.out.println("REQ to Server2!");
		re = LocateRegistry.getRegistry("Localhost", PORT2);
		Hello hello = (Hello)re.lookup(server2);
		
		System.out.println(hello.printMsg(currentSV,server2));
		
	}
	public void ComunicateWithServer3() throws Exception
	{
		System.out.println("REQ to Server3!");
		re = LocateRegistry.getRegistry("Localhost", PORT3);
		Hello hello = (Hello)re.lookup(server3);
		System.out.println(hello.printMsg(currentSV,server3));
	}
	
	public static void main(String[] args) throws Exception
	{
		int chose;
		Server1 sv1 = new Server1();
		sv1.Regist();
		//Send Request
		do
		{
			System.out.println("1.REQ to Server2");
			System.out.println("2.REQ to Server3");
			System.out.println("3.REQ to All");
			System.out.println("Chose your option: ");
			Scanner sc = new Scanner(System.in);
			chose = sc.nextInt();
			switch(chose)
			{
				case 1:
				{
					sv1.ComunicateWithServer2();
				}break;
				case 2:
				{
					sv1.ComunicateWithServer3();
				}break;
				case 3:
				{
					sv1.ComunicateWithServer2();
					sv1.ComunicateWithServer3();
				}
			}
		}
		while(chose != 1 && chose != 2 && chose != 3);
		
	}
}
