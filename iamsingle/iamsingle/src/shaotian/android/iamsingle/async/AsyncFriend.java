package shaotian.android.iamsingle.async;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import shaotian.android.iamsingle.ChatListActivity;
import shaotian.android.iamsingle.IListener;
import shaotian.android.iamsingle.MapActivity;
import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.async.ServiceMessageSystem.ServiceMessageSystemBinder;
import shaotian.android.iamsingle.async.ServiceUpdateLocation.ServiceUpdateLocBinder;

import shaotian.android.iamsingle.netsdk.model.FriendList;
import shaotian.android.iamsingle.netsdk.model.IModel;
import shaotian.android.iamsingle.netsdk.util.LocationList;
import shaotian.android.iamsingle.wssdk.WSFactory;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;


public class AsyncFriend extends AsyncTask<Void, Void, IModel> {
	 

		IListener liser=null;
		int uid=-1;
		int touid=-1;
		private FriendList pointer;
    	public AsyncFriend(IListener liser, int uid, int touid)
    	{
    		this.uid=uid;
    		this.liser=liser;
    		this.touid=touid;
    	}
    	public AsyncFriend(IListener liser, int uid)
    	{
    		this.uid=uid;
    		this.liser=liser;
    		
    	}
    	
		@Override
		protected IModel doInBackground(Void... params) {
			try {
				switch(touid){
				case -1:return WSFactory.Instance().getFriendManager().getFriendList(uid);
				default:return WSFactory.Instance().getFriendManager().addFriend(uid,touid);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}      

		@Override
		protected void onPostExecute(IModel result) {
			super.onPostExecute(result); 
			liser.handleChange(result);
			
		}
		
		
}
