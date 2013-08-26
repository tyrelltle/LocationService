/*
 * Protocol input:   loc userid_altitude_latitude_longtitude 
 * Note: param.input dosent contatin 'loc'
 * 
 * 
 * numOfMessages userid_altitude_latitude_longtitude  userid_altitude_latitude_longtitude    userid_altitude_latitude_longtitude
 * numOfMessages userid_altitude_latitude_longtitude  userid_altitude_latitude_longtitude    userid_altitude_latitude_longtitude
 * numOfMessages userid_altitude_latitude_longtitude  userid_altitude_latitude_longtitude    userid_altitude_latitude_longtitude

*/

package shaotian.android.iamsingle.wssdk;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.util.Log;

import shaotian.android.iamsingle.wssdk.ILocationCommunicator;
import shaotian.android.iamsingle.netsdk.util.*;
import shaotian.android.iamsingle.netsdk.model.*;



public class LocationCommunicator implements ILocationCommunicator{
	
	Pair<String,Integer> server=null;
	
	
	@Override
	public void setServer(String ip, int port) {
		server=new Pair<String,Integer>(ip, new Integer(port));
	}


	@Override
	public void updateLoc(Object para) throws Exception {
		if(!(para instanceof Location))
			throw new Exception("need to pass LocParam to LocationCommunicator.updateLoc()");
		Location param=(Location)para;
		DatagramSocket s;
		try {
			s = new DatagramSocket();
	
		InetAddress local;
		byte[] ret=new byte[1024];
		local = InetAddress.getLocalHost();

		String data=String.format("loc %d_%f_%f",param.userid,param.latitude,param.longtitude);
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
	public Object getMap(Object para) throws Exception {
		if(!(para instanceof LocBoundBox))
			throw new Exception("need to pass LocParam to LocationCommunicator.updateLoc()");
		LocBoundBox param=(LocBoundBox)para;
		LocationList lis=new LocationList();
		DatagramSocket s;
		try {
			s = new DatagramSocket();
	
		InetAddress local;
		byte[] ret=new byte[1024];
		local = InetAddress.getLocalHost();
		
		
		
		String data=String.format("map %f_%f_%f_%f",param.northEast_lat, param.northEast_lng,param.southWest_lat,param.southWest_lng);
		DatagramPacket p=new DatagramPacket(data.getBytes(),data.length(),InetAddress.getByName(server.first),server.second);
		s.send(p);
		
		
		//recieve server responses

		DatagramPacket r=new DatagramPacket(ret,ret.length,InetAddress.getByName(server.first),server.second);
		s.setSoTimeout(Constant.RECIEVE_TIMEOUT);
		int packetRecieved=0;
		long starttime=System.currentTimeMillis();
		while(true){
			try{
				s.receive(r);
				packetRecieved++;
				String d=new String(ret,0,r.getLength());
				String []arr=d.split(" ");
				
				for(int i=1;i<arr.length;i++)
				{//ignore first index, which is num_of_packages
					if(arr[i]!=null||!arr[i].equals(""))
					lis.add(arr[i]);
				}
					
				
				
				//terminate if message number equals to lis size
				if(arr[0].equals(String.valueOf(packetRecieved)))
					break;
				
				r.setLength(ret.length);
			}catch(SocketTimeoutException e)
			{
				break;
			}

		}
		s.close();
		Log.d("app log", "loc map retrive- ms: "+(System.currentTimeMillis()-starttime)+" len:"+lis.size());
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
		
		return lis;
		
	}   
}
