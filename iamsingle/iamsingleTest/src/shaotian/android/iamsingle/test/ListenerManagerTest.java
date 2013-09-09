package shaotian.android.iamsingle.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;

import shaotian.android.iamsingle.ChatListActivity;
import shaotian.android.iamsingle.ListenerManager;
import shaotian.android.iamsingle.netsdk.model.Message;
import shaotian.android.iamsingle.netsdk.model.MessageHistory;
import shaotian.android.iamsingle.socketsdk.INetProvider;
import shaotian.android.iamsingle.socketsdk.MessageManager;

public class ListenerManagerTest extends ActivityInstrumentationTestCase2<ChatListActivity>{

	private ChatListActivity activity=null;
	private MessageManager msgmgr=null;
	private int myuid=1;
	public ListenerManagerTest() {
		super("shaotian.android.iamsingle", ChatListActivity.class);
		// TODO Auto-generated constructor C
	}   
	   
    @Override
    protected void setUp() throws Exception {
		msgmgr=MessageManager.initialize(29, new ProviderMock());

		activity=this.getActivity();
		
		
		assertTrue(activity.getListView().getAdapter().getCount()==0);
	}
	
	public void testIListenderInterface_with_ListenerManager() throws InterruptedException
	{
		/*listenerNamager mgr
		  mgr.addListener(activity)
		  this.addMesagehist()
		  mgr.notifyListeners()
		  assert(activity's list changed accoridng to new msghistory in msgmgr)
		*/
		
		ListenerManager lismgr=new ListenerManager();
		lismgr.addListener(activity);
		
		this.addMsgHistry(29, "sender 29");
		lismgr.notifyListeners();
		Thread.sleep(1000);
		assertEquals(activity.getListView().getAdapter().getCount(),1);
		this.addMsgHistry(29, "sender 29");
		lismgr.notifyListeners();
		Thread.sleep(1000);
		assertEquals(activity.getListView().getAdapter().getCount(),1);
		this.addMsgHistry(33, "sender 29");
		lismgr.notifyListeners();
		Thread.sleep(1000);
		assertEquals(activity.getListView().getAdapter().getCount(),2);

	}
	
	private void addMsgHistry(int senderid,String senderNe)
	{		
		Message msg=new Message(senderid, senderNe, senderid, senderNe);
		msgmgr.addToMessageHistory(senderid, msg);
	}
	


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
				return "26 alohaUser "+arr[index++];
			}

			@Override
			public void disconnect() throws IOException {
			}}
	   
}
