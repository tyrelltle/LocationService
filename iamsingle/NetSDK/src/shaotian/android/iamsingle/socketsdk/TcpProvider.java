package shaotian.android.iamsingle.socketsdk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpProvider implements INetProvider {
	Socket srvrSocket;
	PrintWriter outStream;
	BufferedReader inStream;
	public TcpProvider(String srvrAddr, int srvrport) throws Exception
	{
		//InetAddress serverAddr = InetAddress.getByName(srvrAddr);
	    srvrSocket = new Socket(srvrAddr, srvrport);

	    outStream  = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(srvrSocket.getOutputStream())), true);
	    inStream = new BufferedReader(
				new InputStreamReader(srvrSocket.getInputStream()));
		
	}
	@Override
	public void send(String msg) {
		outStream.write(msg, 0, msg.length());
		outStream.flush();
	}

	@Override
	public String receive() throws IOException {
		// TODO Auto-generated method stub
		String ret= inStream.readLine();
		return ret;
	}
	@Override
	public void disconnect() throws IOException {
		srvrSocket.close();
		
	}

}
