package demo;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server2 {
	//Host of server
	private static final String currentSV = "Server2";
	private static final String server1 = "Server1";
	private static final String server3 = "Server3";
	
	private static final int currentPort = 5002;
	private static final int PORT1 = 5001; //Port of server1
	private static final int PORT3 = 5003; //Port of server3
	private static Registry re;
	
	private Server2() {}  

	public void Regist() throws Exception
	{
		re = LocateRegistry.createRegistry(currentPort);
		
		HelloImpl obj = new HelloImpl();
		
		Hello stub = (Hello)UnicastRemoteObject.exportObject(obj,0);
		
		re.rebind(currentSV, obj);
		System.out.println("Server2 Readdy!");
	}
	public void ComunicateWithServer1() throws Exception
	{
		System.out.println("REQ to Server1!");
		
		re = LocateRegistry.getRegistry("Localhost", PORT1);
		Hello hello = (Hello)re.lookup(server1);
		
		System.out.println(hello.printMsg(currentSV,server1));
		
	}
	public void ComunicateWithServer3() throws Exception
	{
		System.out.println("REQ to Server3!");
		re = LocateRegistry.getRegistry("Localhost", PORT3);
		Hello hello = (Hello)re.lookup(server3);
		hello.printMsg(currentSV,server3);
		System.out.println(hello.printMsg(currentSV,server3));
	}
	public static void main(String[] args) throws Exception
	{
		int chose;
		Server2 sv2 = new Server2();
		sv2.Regist();
		do
		{
			System.out.println("1.REQ to Server1");
			System.out.println("2.REQ to Server3");
			System.out.println("3.REQ to All");
			System.out.print("Chose your option: ");
			Scanner sc = new Scanner(System.in);
			chose = sc.nextInt();
			switch(chose)
			{
				case 1:
				{
					sv2.ComunicateWithServer1();
				}break;
				case 2:
				{
					sv2.ComunicateWithServer3();
				}break;
				case 3:
				{
					sv2.ComunicateWithServer1();
					sv2.ComunicateWithServer3();
				}
			}
		}
		while(chose != 1 && chose != 2 && chose != 3);
	}
	
}
