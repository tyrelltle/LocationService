/*
 * Protocol input:   loc userid.altitude.latitude.longtitude 
 * Note: param.input dosent contatin 'loc'
 * 

*/

package shaotian.android.iamsingle.netsdk;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import shaotian.android.iamsingle.netsdk.util.Constant;
import shaotian.android.iamsingle.netsdk.util.*;




public class WorldModeCommunicator implements INetCommunicator{
	
	Pair<String,Integer> server=null;
	
	
	@Override
	public void setServer(String ip, int port) {
		server=new Pair<String,Integer>(ip, new Integer(port));
	}


	@Override
	public void updateLoc(INetParam para) throws Exception {
		if(!(para instanceof LocParam))
			throw new Exception("need to pass LocParam to WorldModeCommunicator.updateLoc()");
		LocParam param=(LocParam)para;
		DatagramSocket s;
		try {
			s = new DatagramSocket();
	
		InetAddress local;
		byte[] ret=new byte[1024];
		local = InetAddress.getLocalHost();

		String data=String.format("loc %d_%f_%f_%f",param.userid, param.altitude,param.latitude,param.longtitude);
		DatagramPacket p=new DatagramPacket(data.getBytes(),data.length(),InetAddress.getByName(server.first),server.second);
		s.send(p);
		
		
		s.close();
	
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public Object getMap(INetParam para) throws Exception {
		if(!(para instanceof LocParam))
			throw new Exception("need to pass LocParam to WorldModeCommunicator.updateLoc()");
		LocParam param=(LocParam)para;
		DatagramSocket s;
		try {
			s = new DatagramSocket();
	
		InetAddress local;
		byte[] ret=new byte[1024];
		local = InetAddress.getLocalHost();
		
		
		
		String data=String.format("map %d_%f_%f_%f",param.userid, param.altitude,param.latitude,param.longtitude);
		DatagramPacket p=new DatagramPacket(data.getBytes(),data.length(),InetAddress.getByName(server.first),server.second);
		s.send(p);
		
		
		//recieve server responses

		DatagramPacket r=new DatagramPacket(ret,ret.length,InetAddress.getByName(server.first),server.second);
		s.setSoTimeout(Constant.RECIEVE_TIMEOUT);
		ArrayList<String> lis=new ArrayList<String>();
		while(true){
			try{
				s.receive(r);
				String d=new String(ret,0,r.getLength());
				lis.add(d);
				
				//terminate if message number equals to lis size
				if(d.substring(0, d.indexOf("_")).equals(String.valueOf(lis.size())))
					break;
				
				System.out.println("******[srvr rsp] "+ d);
				r.setLength(ret.length);
			}catch(SocketTimeoutException e)
			{
				break;
			}

		}
		s.close();
	
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}   
}
