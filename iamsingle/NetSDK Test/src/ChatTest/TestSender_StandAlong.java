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



	@Test
	public void concrettest() throws Exception{
		MessageManager mgr = MessageManager.initialize(59,new TcpProvider("192.168.0.12",12000));
		mgr.registerToServer();
		mgr.sendMessage(120, "helloworld");
		mgr.unRegister();
	}
}
