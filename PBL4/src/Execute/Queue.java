package Execute;
import java.awt.*;
import java.io.*;

public class Queue {
	public static int top = -1;
	public static String Q[];
	public static int time = 0;
	public static boolean Cs = true;
	
	public Queue()
	{
		top = -1;
	}
	//In thông điệp trong hàng đợi
	public void printQueue()
	{
		for(int i = 0; i< Q.length; i++)
		{
			System.out.println(Q[i]);
		}
	}
	//Thêm thông điệp vào hàng đợi
	public void pushQueue(String mess, int time_logic, int source)
	{
		++top;
		String temp = "("+mess+","+time_logic+","+source+")";
		Q[top] = temp;
	}
	//Lấy phần giá trị ở đỉnh của hàng đợi
	public String Top()
	{
		if(top==-1)
		{
			System.out.println("Queue is empty!");
			return "";
		}
		else
		{
			return Q[top];
		}
	}
}
