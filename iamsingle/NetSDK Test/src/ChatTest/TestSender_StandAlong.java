package ChatTest;
/*
 * this is not a unit test. it is only sending msg to server
 * */
import static org.junit.Assert.*;

import org.junit.Test;

import shaotian.android.iamsingle.socketsdk.INetProvider;
import shaotian.android.iamsingle.socketsdk.TcpProvider;

public class TestSender_StandAlong {

	@Test
	public void test() throws Exception {
		INetProvider provider=new TcpProvider("192.168.0.114",12000);

		provider.send("reg 57");
		assertEquals("ack",provider.receive());
		
						provider.send("msg 120 lkjn");
						assertEquals("ack",provider.receive());

		
		
		provider.send("close");
		assertEquals("ack",provider.receive());
	
	}

}
