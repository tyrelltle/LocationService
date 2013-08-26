package shaotian.android.iamsingle.async;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import shaotian.android.iamsingle.IInfoContainer;
import shaotian.android.iamsingle.MapActivity;
import shaotian.android.iamsingle.UIShared.SharedUtil;

import shaotian.android.iamsingle.netsdk.model.IModel;
import shaotian.android.iamsingle.netsdk.model.UserInfo;
import shaotian.android.iamsingle.netsdk.util.LocationList;
import shaotian.android.iamsingle.wssdk.WSFactory;
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


public class AsyncInfoCtrUpdate extends AsyncTask<Void, Void, IModel> {
	 //Async classes 
  
		TextView txt_username=null;
		TextView txt_userdesc=null;
		TextView txt_userhobby=null;
		ImageView img_usericon=null;
		int uid;
	
		private IInfoContainer container;

    	
		public AsyncInfoCtrUpdate(int uid2, IInfoContainer container) {
			this.container=container;
			this.uid=uid2;
		}

		@Override
		protected IModel doInBackground(Void... params) {
			ApplicationInfo ai;
			IModel ret=null;
			
			try {
				
					switch(this.container.getWSMethodType())
					{
			
					
					case GetUserInfo: return WSFactory.Instance().GetUserInfo(this.uid);
										
					default:return null;
					
					
					}
					
					
			
				
			
				
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
		protected void onPostExecute(IModel result) {
			super.onPostExecute(result);
			this.container.updateControls(result);

		}
  
}
