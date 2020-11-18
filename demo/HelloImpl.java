package demo;

public class HelloImpl implements Hello{
	// Implementing the interface method 
	   public String printMsg(String source, String destionation) 
	   {  
	      System.out.println("REQ from "+source);
	      return ("ACK from "+destionation+"!");
	   }
}
