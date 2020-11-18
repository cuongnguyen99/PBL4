package demo;
import java.rmi.Remote; 
import java.rmi.RemoteException;

public interface Hello extends Remote{
	public String printMsg(String source, String destination) throws RemoteException;
}
