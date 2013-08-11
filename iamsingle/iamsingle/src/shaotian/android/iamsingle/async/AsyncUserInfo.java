package shaotian.android.iamsingle.async;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import shaotian.android.iamsingle.MapActivity;
import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.netsdk.AuthManager;
import shaotian.android.iamsingle.netsdk.LocationCommunicator;
import shaotian.android.iamsingle.netsdk.UserProfileManager;
import shaotian.android.iamsingle.netsdk.model.UserInfo;
import shaotian.android.iamsingle.netsdk.util.LocationList;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AsyncUserInfo extends AsyncTask<Void, Void, UserInfo> {
	 //Async classes 
  
		TextView txt_username=null;
		TextView txt_userdesc=null;
		TextView txt_userhobby=null;
		ImageView img_usericon=null;
		int uid;
	
		private Context context;
    	public AsyncUserInfo(int uid,Context context,TextView un,TextView ud, TextView uh, ImageView img)
    	{
    		
    		this.context=context;
    		this.txt_username=un;
    		this.txt_userdesc=ud;
    		this.txt_userhobby=uh;
    		this.img_usericon=img;
    		this.uid=uid;
    		
    	}
    	
		@Override
		protected UserInfo doInBackground(Void... params) {
			ApplicationInfo ai;
			LocationList ret=null;
			UserInfo res=null;
			try {
				ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
				Bundle bundle = ai.metaData;

				UserProfileManager um=new UserProfileManager(bundle.getString("wsserverip"));
				res= um.getUserInfo(uid);
			
				
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
		}      

		@Override
		protected void onPostExecute(UserInfo result) {
			super.onPostExecute(result);
			Bitmap bm= BitmapFactory.decodeByteArray(result.usericon, 0, result.usericon.length);
			this.img_usericon.setImageBitmap(bm);
			this.txt_userdesc.setText(result.selfintro);
			this.txt_userhobby.setText(result.hobby);
			this.txt_username.setText("test user name");

		}
  
}
