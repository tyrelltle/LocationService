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

import org.junit.Test;

import shaotian.android.iamsingle.netsdk.model.Message;
import shaotian.android.iamsingle.netsdk.model.MessageHistory;
import shaotian.android.iamsingle.netsdk.model.MessageHistory.MessageIterator;
import shaotian.android.iamsingle.netsdk.model.MessageHistoryList.Node;
import shaotian.android.iamsingle.netsdk.model.MessageHistoryList;
import shaotian.android.iamsingle.netsdk.util.Pair;
import shaotian.android.iamsingle.socketsdk.NetManager;
import shaotian.android.iamsingle.socketsdk.INetProvider;
import shaotian.android.iamsingle.socketsdk.MessageManager;
import shaotian.android.iamsingle.socketsdk.TcpProvider;


public class MessageHistoryTest {
  


	
	@Test
	public void MessageTest() throws IOException {
		Message msg=new Message(29,56,"helloworld Msg");
		assertEquals(msg.sender,29);
		assertEquals(msg.reciever,56);
		assertEquals(msg.message,"helloworld Msg");
	}
	
	
	@Test
	public void MessageHistoryTest() throws IOException {
		Message msg=new Message(29,56,"helloworld Msg1");
		Message msg2=new Message(29,56,"helloworld Msg2");
		Message msg3=new Message(29,56,"helloworld Msg3");
		MessageHistory history=new MessageHistory();
		history.addMessage(msg);
		history.addMessage(msg2);
		history.addMessage(msg3);
		assertTrue(history.hasNewMsg());
		history.setHasNewMsgFalse();
		assertFalse(history.hasNewMsg());

		for(int i =0;i<3;i++)
		{
			assertEquals(history.getMessage(i).message,"helloworld Msg"+(i+1));
		}
		
		history.remove(0);
		assertEquals(history.getMessage(0).message,"helloworld Msg2");
		
	}
	
	@Test
	public void MessageHistoryIteratorTest() throws IOException {
		Message msg=new Message(29,"alohaUser",56,"helloworld Msg1");
		Message msg2=new Message(29,"alohaUser",56,"helloworld Msg2");
		Message msg3=new Message(29,"alohaUser",56,"helloworld Msg3");
		MessageHistory history=new MessageHistory();
		history.addMessage(msg);
		history.addMessage(msg2);
		history.addMessage(msg3);
		
		
		MessageIterator i=history.getIterator();
		int yoho=1;
		int aloho=0;
		while(i.hasNext())
		{
			Message m=i.next();
			assertEquals(m.message,"helloworld Msg"+yoho);
			
			String tmp=m.getSenderName();
			if(tmp!=null)
			{	aloho++;
				assertEquals(tmp,"alohaUser");
			}
			yoho++;
			
		}
		assertEquals(aloho,3);
	}
	
	@Test
	public void MessageHistoryList_IteratorTest()
	{
		
		MessageHistoryList lis=new MessageHistoryList();
		for(int i=0;i<10;i++)
		{	
			MessageHistoryList.Node node=new MessageHistoryList.Node();
			node.senderId=i;
			
			lis.lis.add(node);
		
		}
		Iterator it=lis.getIterator();
		int tmp=0;
		while(it.hasNext())
		{
			assertTrue(((MessageHistoryList.Node)it.next()).senderId==tmp++);
			
		}

		
	}
	
	@Test
	public void MessageHistory_GetSenderNameTest()
	{
		Message msg=new Message(29,56,"helloworld Msg1");
		Message msg2=new Message(29,56,"helloworld Msg2");
		Message msg3=new Message(29,"alohaUser",56,"helloworld Msg3");
		MessageHistory history=new MessageHistory();
		history.addMessage(msg);
		history.addMessage(msg2);
		history.addMessage(msg3);
		assertEquals(history.getSenderName(),"alohaUser");
		
	}
	
	
	
}
