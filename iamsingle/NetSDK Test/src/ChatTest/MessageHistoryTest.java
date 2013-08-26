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
		for(int i =0;i<3;i++)
		{
			assertEquals(history.getMessage(i).message,"helloworld Msg"+(i+1));
		}
		
		history.remove(0);
		assertEquals(history.getMessage(0).message,"helloworld Msg2");
		
	}
	
	@Test
	public void MessageHistoryIteratorTest() throws IOException {
		Message msg=new Message(29,56,"helloworld Msg1");
		Message msg2=new Message(29,56,"helloworld Msg2");
		Message msg3=new Message(29,56,"helloworld Msg3");
		MessageHistory history=new MessageHistory();
		history.addMessage(msg);
		history.addMessage(msg2);
		history.addMessage(msg3);
		
		
		MessageIterator i=history.getIterator();
		int yoho=1;
		while(i.hasNext())
		{
			
			assertEquals(i.next().message,"helloworld Msg"+yoho);
			yoho++;
			
		}
		
	}
}
