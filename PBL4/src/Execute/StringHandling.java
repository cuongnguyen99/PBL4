package Execute;

public class StringHandling {
	public String messSplit(String a)
	{
		String mess =""; 
		for(int i = 0; i<a.length(); i++)
		{
			if(i>0 && i<=3)
			{
				mess += a.charAt(i); 
			}
		}
		return mess;
	}
	public int portSplit(String a)
	{
		String mess ="";
		int port;
		for(int i = 0; i<a.length(); i++)
		{
			if(i>4 && i<=8)
			{
				mess += a.charAt(i); 
			}
		}
		port = Integer.parseInt(mess);
		return port;
	}
}
