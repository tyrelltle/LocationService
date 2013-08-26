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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;

import shaotian.android.iamsingle.socketsdk.INetProvider;
import shaotian.android.iamsingle.socketsdk.TcpProvider;

/*
 * two threads: sender and reciever
 * sender sends 100 messages to reciever
 * reciever recieve messages and fill into recievedMsgList array
 * sender wait until recievedMsgList array.size=100
 * 
 * */
public class SenderReceveTest  {

	private static ArrayList<String> recievedMsgList=new ArrayList<String>();
	private static final String serverip = "192.168.0.10";
	private static class ReceiverRun  implements Runnable{

		@Override
		public void run() {
			try {
				INetProvider provider=new TcpProvider(serverip,12000);
				provider.send("regip 29");
				assertEquals("ack",provider.receive());
				int i=0;
				while(true)
				{
					String recv=provider.receive();
					if(recv.equals("close"))
						break;
					recievedMsgList.add(recv.split(" ")[1]);
					provider.send("ack");
					i++;
				}
				
				
				
			} catch (Exception e) {

			}
		}
		
		
	}

	private static class SenderRun  implements Runnable {

		

		@Override
		public void run() {
			
			try{
				INetProvider provider=new TcpProvider(serverip,12000);

				provider.send("reg 29");
				assertEquals("ack",provider.receive());
				for(int i=0;i<100;i++)
				{
								provider.send("msg 29 "+i);
								assertEquals("ack",provider.receive());

				}
				
				provider.send("close");
				assertEquals("ack",provider.receive());
			}
			
			
			catch(Exception e){}
			
		}
		
		
	}
	

	@Test
	public void test() throws IOException {
		
		ExecutorService  executor=Executors.newFixedThreadPool(2);
		executor.execute(new ReceiverRun());
		executor.execute(new SenderRun());
		executor.shutdown();
		java.util.Date date= new java.util.Date();
		long starttime=date.getTime();
		while(!executor.isTerminated()){
			
			if(new java.util.Date().getTime()-starttime>2000)
				executor.shutdownNow();
			
		}
		final ArrayList<String> expectedlis=new ArrayList<String>();
		for(int i=0;i<100;i++){
			expectedlis.add(String.valueOf(i));
		}
		assertEquals(expectedlis,recievedMsgList);
	}

}
