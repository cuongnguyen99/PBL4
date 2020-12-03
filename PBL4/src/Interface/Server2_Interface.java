package Interface;

import java.rmi.RemoteException;

public interface Server2_Interface {
	public void printQueue() throws RemoteException;
	public void sendMess(String mess, int time_logic, int source) throws RemoteException;
	public void receiveMess() throws RemoteException;
}
