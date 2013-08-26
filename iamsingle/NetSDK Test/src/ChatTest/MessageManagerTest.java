package ChatTest;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shaotian.android.iamsingle.netsdk.model.Message;
import shaotian.android.iamsingle.netsdk.model.MessageHistory;
import shaotian.android.iamsingle.netsdk.model.MessageHistory.MessageIterator;
import shaotian.android.iamsingle.socketsdk.MessageListener;
import shaotian.android.iamsingle.socketsdk.NetManager;
import shaotian.android.iamsingle.socketsdk.INetProvider;
import shaotian.android.iamsingle.socketsdk.MessageManager;
import shaotian.android.iamsingle.socketsdk.TcpProvider;


public class MessageManagerTest {
				   private class ProviderMock implements INetProvider{
						public String sentmsg=null;
						public boolean nextRecieveReturnAck=false;
						@Override
						public void send(String msg) {
							sentmsg=msg;	
						}
					
						@Override
						public String receive() throws IOException {
							return nextRecieveReturnAck?"ack":"hello world msg";
						}
	
						@Override
						public void disconnect() throws IOException {
						}}
				   
				   private class ListenerProviderMock implements INetProvider{
					   	 int index=0;
					   	 final String[]arr={"helloworld","close"};
						@Override
						public void send(String msg) {}
					
						@Override
						public String receive() throws IOException {
							if(index==2)
								return "";
							return "26 "+arr[index++];
						}
	
						@Override
						public void disconnect() throws IOException {
						}}
				   
				   private static class ListenerObserver implements Observer{
					  public Message msg;
					@Override
					public void update(Observable o, Object arg) {
						synchronized(this){
							msg=(Message)arg;
						}
					}}
				   
				   
    int sender=66;
	
	final String[] msglis={"one","two","three","four","five","six"};
	MessageManager mgr=null;
	
	INetProvider provider=new ProviderMock();
	
	@Before
    public void init() throws IOException{
    	if((mgr=MessageManager.instance())!=null)
    	{	return;}
    	else
    		
    	{	mgr=MessageManager.initialize(sender,provider);}
    	((ProviderMock)provider).nextRecieveReturnAck=true;
    	mgr.registerToServer(sender);
    	//get message history with uid 29
    	assertTrue(mgr.getMessageHistory(29)==null);
    	((ProviderMock)provider).nextRecieveReturnAck=true;			   
    				   
    	
    	
    }
	
	

	@Test
	public void MessageManager_addToMessageHistory() throws IOException {

		for(String i :msglis)
		{
			
			mgr.sendMessage(29,i);
			
		}

		MessageHistory hist=mgr.getMessageHistory(29);
		MessageIterator it=hist.getIterator();
		int itera=0;
		while(it.hasNext())
		{
			Message tmp=it.next();
			assertTrue(tmp.message.equals(msglis[itera]));
			assertTrue(tmp.reciever==29);
			assertTrue(tmp.sender==sender);
			itera++;
		}
	}
	@Test
	public void MessageManager_Reciever() throws IOException, InstantiationException, IllegalAccessException  {
		
		ListenerObserver observer=new ListenerObserver();
		MessageListener listener= mgr.CreateMessageListener(new ListenerProviderMock());
		listener.addObserver(observer);
		new Thread(listener).start();
		while(true)
		{
			synchronized(observer){
				if(observer.msg!=null)
					break;
			}
			
		}
		assertTrue(observer.msg.sender==26);
		assertTrue(observer.msg.reciever==66);
		assertTrue(observer.msg.message.equals("helloworld"));
	}
	

}
