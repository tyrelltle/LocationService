package ChatTest;
import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;


import shaotian.android.iamsingle.netsdk.model.Message;
import shaotian.android.iamsingle.netsdk.model.MessageHistory;
import shaotian.android.iamsingle.netsdk.model.MessageHistoryList;
import shaotian.android.iamsingle.netsdk.model.MessageHistory.MessageIterator;
import shaotian.android.iamsingle.socketsdk.MessageListener;
import shaotian.android.iamsingle.socketsdk.INetProvider;
import shaotian.android.iamsingle.socketsdk.MessageManager;


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
					   	 int senderid;
					   	 final String[]arr={"helloworld","close"};
						public ListenerProviderMock(int i) {
							senderid=i;
						}

						@Override
						public void send(String msg) {}
					
						@Override
						public String receive() throws IOException {
							if(index==2)
								return "";
							return senderid+" alohaUser "+arr[index++];
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
    	mgr.registerToServer();
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
		MessageListener listener= mgr.CreateMessageListener(new ListenerProviderMock(26));
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
		assertEquals(observer.msg.senderName,"alohaUser");
		assertTrue(observer.msg.reciever==66);
		assertEquals(observer.msg.message,"helloworld");
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void MessageManager_GetHistoryList() throws Exception
	{	
		Hashtable<Integer, MessageHistory> mHistories;
		Field fields = mgr.getClass().getDeclaredField("mHistories");
		fields.setAccessible(true);
		mHistories=(Hashtable<Integer, MessageHistory>) fields.get(mgr);
		MessageHistoryList lis=mgr.getHistoryList();
		int i=0;
		for(int mgr_hist : mHistories.keySet())
		{
			
			boolean boo=mHistories.get(mgr_hist).hasNewMsg();
			MessageHistoryList.Node node=lis.get(i);
			assertEquals(mgr_hist,node.senderId);
			assertEquals(boo,node.hasNewMsg);
			assertEquals(mHistories.get(mgr_hist).getSenderName(),node.senderName);
			i++;
		}
	}
	
	@Test
	public void MessageManager_Listener_HistoryCount() throws IOException, InstantiationException, IllegalAccessException, InterruptedException  {
		
		ListenerObserver observer=new ListenerObserver();
		MessageListener listener= mgr.CreateMessageListener(new ListenerProviderMock(26));
		listener.addObserver(observer);
		new Thread(listener).start();
		while(true)
		{
			synchronized(observer){
				if(observer.msg!=null)
					break;
			}
			
		}
		assertEquals(true,mgr.getMessageHistory(26)!=null);

		
		listener= mgr.CreateMessageListener(new ListenerProviderMock(55));
		listener.addObserver(observer);
		new Thread(listener).start();
		while(true)
		{
			synchronized(observer){
				if(observer.msg!=null)
					break;
			}
			
		}
		Thread.sleep(500);
		assertEquals(true,mgr.getMessageHistory(55)!=null);
		assertEquals(3,mgr.getHistoryCount());
		
		
	}
	

}
