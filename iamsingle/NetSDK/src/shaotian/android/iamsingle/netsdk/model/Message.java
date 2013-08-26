package shaotian.android.iamsingle.netsdk.model;
/*
 * message used in chatroom feature. 
 * Each message has sender,reciever as user id , and actually msg string
 * */
public class Message implements IModel{
	
	public int sender;
	public int reciever;
	public String message;
	
	public Message(int s, int r, String m)
	{
		sender=s;
		reciever=r;
		message=m;
	}
}
