package ChatTest;
/*
 * this is not a unit test. it is only sending msg to server
 * */
import static org.junit.Assert.*;

import org.junit.Test;

import shaotian.android.iamsingle.socketsdk.INetProvider;
import shaotian.android.iamsingle.socketsdk.MessageManager;
import shaotian.android.iamsingle.socketsdk.TcpProvider;

public class TestSender_StandAlong {

	/*@Test
	public void test() throws Exception {
		INetProvider provider=new TcpProvider("192.168.0.11",12000);

		provider.send("reg 59");
		assertEquals("ack",provider.receive());
		
						provider.send("msg 120 lkjn");
						assertEquals("ack",provider.receive());

		
		
		//provider.send("close");
		//assertEquals("ack",provider.receive());
	
	}
*/
	@Test
	public void concrettest() throws Exception{
		MessageManager mgr = MessageManager.initialize(59,new TcpProvider("192.168.0.11",12000));
		mgr.registerToServer();
		mgr.sendMessage(120, "helloworld");
		mgr.unRegister();
	}
}
