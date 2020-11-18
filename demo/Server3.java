package demo;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server3 {
	//Host of server
		private static final String currentSV = "Server3";
		private static final String server1 = "Server1";
		private static final String server2 = "Server2";
		
		private static final int currentPort = 5003;
		private static final int PORT1 = 5001; //Port of server1
		private static final int PORT2 = 5002; //Port of server2
		private static Registry re;
	
	private Server3() {}  

	public void Regist() throws Exception
	{
		re = LocateRegistry.createRegistry(currentPort);
		
		HelloImpl obj = new HelloImpl();
		
		Hello stub = (Hello)UnicastRemoteObject.exportObject(obj,0);
		
		re.rebind(currentSV, obj);
		System.out.println("Server3 Readdy!");
	}
	
	public void ComunicateWithServer1() throws Exception
	{
		re = LocateRegistry.getRegistry("Localhost", PORT1);
		Hello hello = (Hello)re.lookup(server1);
		System.out.println(hello.printMsg(currentSV,server1));
	}
	public void ComunicateWithServer2() throws Exception
	{
		System.out.println("REQ to Server2!");
		re = LocateRegistry.getRegistry("Localhost", PORT2);
		Hello hello = (Hello)re.lookup(server2);
		System.out.println(hello.printMsg(currentSV,server2));	
	}
	
	public static void main(String[] args) throws Exception
	{
		int chose;
		Server3 sv3 = new Server3();
		sv3.Regist();
		do
		{
			System.out.println("1.REQ to Server1");
			System.out.println("2.REQ to Server2");
			System.out.println("3.REQ to All");
			System.out.println("Chose your option: ");
			Scanner sc = new Scanner(System.in);
			chose = sc.nextInt();
			switch(chose)
			{
				case 1:
				{
					sv3.ComunicateWithServer1();
				}break;
				case 2:
				{
					sv3.ComunicateWithServer2();
				}break;
				case 3:
				{
					sv3.ComunicateWithServer1();
					sv3.ComunicateWithServer2();
				}
			}
		}
		while(chose != 1 && chose != 2 && chose != 3);
	}
	
}
