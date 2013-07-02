package shaotian.android.iamsingle.async;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import shaotian.android.iamsingle.MapActivity;
import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.netsdk.WorldModeCommunicator;
import shaotian.android.iamsingle.netsdk.auth.AuthManager;
import shaotian.android.iamsingle.netsdk.util.LocationList;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;


public class AsyncRegister extends AsyncTask<Void, Void, JSONObject> {
	 //Async classes 
  


	
		private Context context;
		private String email,pwd;
    	public AsyncRegister(Context context, String email,String pwd)
    	{
    		this.email=email;
    		this.pwd=pwd;
    		this.context=context;
    	
    		
    	}
    	
		@Override
		protected JSONObject doInBackground(Void... params) {
			ApplicationInfo ai;
			LocationList ret=null;
			try {
				ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
				Bundle bundle = ai.metaData;

				AuthManager am=new AuthManager();
				return am.register(bundle.getString("wsserverip"),email,pwd);
				/*WorldModeCommunicator comm=new WorldModeCommunicator();
				comm.setServer(bundle.getString("serverip"), bundle.getInt("serverport"));
				ret=(LocationList) comm.getMap(new shaotian.android.iamsingle.netsdk.model.Location(1,location.getAltitude(),location.getLatitude(),location.getLongitude()));*/
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}      

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			try {
				if(result==null)
					return;
				int uid=result.getInt("userid");
				if(uid==-1)
					return;
				//store to sharedpreference
				SharedPreferences settings = context.getSharedPreferences(SharedUtil.SHARED_PREFERENCES, 0);
			      SharedPreferences.Editor editor = settings.edit();
			      editor.putInt(SharedUtil.SHARED_UID, uid);
			      editor.commit();
			      Intent i=new Intent(context, MapActivity.class);
			      context.startActivity(i);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
  
}