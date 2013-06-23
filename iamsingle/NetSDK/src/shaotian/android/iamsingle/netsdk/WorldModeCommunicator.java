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

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.util.Pair;



public class WorldModeCommunicator implements INetCommunicator{
	
	Pair<String,Integer> server=null;
	LocParam param=null;
	@SuppressLint("NewApi")
	@Override
	public void updateLoc(INetParam data) throws Exception {
		if(!(data instanceof LocParam))
			throw new Exception("need to pass LocParam to WorldModeCommunicator.updateLoc()");
		param=(LocParam)data;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			new AsyncSend().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			new AsyncSend().execute();
        
		getMap(data);
	}

	
	@Override
	public void setServer(String ip, int port) {
		server=new Pair<String,Integer>(ip, new Integer(port));
	}

	
	
	private class AsyncSend extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... params) {
			
			
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
			

	        
			return null;
		}      

        
  }


	private class AsyncGet extends AsyncTask<Void, Void, Void> {
			
			@Override
			protected Void doInBackground(Void... params) {
				
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
						
						Log.v("******[srvr rsp] ", d);
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
	




	@Override
	public void getMap(INetParam data) throws Exception {
		if(!(data instanceof LocParam))
			throw new Exception("need to pass LocParam to WorldModeCommunicator.updateLoc()");
		param=(LocParam)data;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			new AsyncGet().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			new AsyncGet().execute();
		
	}   
}
