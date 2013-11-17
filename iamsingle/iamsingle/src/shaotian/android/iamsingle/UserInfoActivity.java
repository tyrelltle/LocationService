package shaotian.android.iamsingle;

import java.util.Iterator;

import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.async.AsyncFriend;
import shaotian.android.iamsingle.async.AsyncUpdateLocation;
import shaotian.android.iamsingle.async.AsyncInfoCtrUpdate;
import shaotian.android.iamsingle.async.AsyncUserAuth;

import shaotian.android.iamsingle.netsdk.model.Friend;
import shaotian.android.iamsingle.netsdk.model.IModel;
import shaotian.android.iamsingle.netsdk.model.ReturnStatus;
import shaotian.android.iamsingle.netsdk.model.UserInfo;
import shaotian.android.iamsingle.wssdk.WSFactory;
import shaotian.android.iamsingle.wssdk.WSFactory.Methods;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoActivity extends Activity implements IInfoContainer, IListener{
	private Handler handler=null;
	Context context;
	IListener lisref;
	int uid;ReturnStatus stat;
	boolean is_friend=false;
	Button friend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		context=this;	
		lisref=this;
		Bundle extras=this.getIntent().getExtras();
		uid=extras.getInt("uid");
		handler=new Handler();
		isFriend();
		
		friend=(Button)this.findViewById(R.id.btn_friend);
		friend.setVisibility(View.INVISIBLE);
		friend.setOnClickListener(new View.OnClickListener(){
		SharedPreferences settings = context.getSharedPreferences(SharedUtil.SHARED_PREFERENCES, 0);
			@Override
			public void onClick(View v) {
				 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
						new AsyncFriend(lisref, settings.getInt(SharedUtil.SHARED_UID, -1),uid ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					else
						new AsyncFriend(lisref, settings.getInt(SharedUtil.SHARED_UID, -1),uid ).execute();		
			}});
		
		Button chat=(Button)this.findViewById(R.id.btn_chat);
		chat.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i=new Intent(context, ChatActivity.class);
				
				i.putExtra("uid", uid);
			    context.startActivity(i);				
			}});
		
		
		Button find=(Button)this.findViewById(R.id.btn_find);
		find.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent i=new Intent(context, MapActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);				
				i.putExtra("specificMarker", uid);
			    context.startActivity(i);				
			}});
		
		
		AsyncInfoCtrUpdate task=new AsyncInfoCtrUpdate(uid,	this);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			 task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			 task.execute();	
		
	}

	private void isFriend() {
		//determine if this user is friend
		Iterator i=WSFactory.Instance().getFriendManager().list.getIterator();
		while(i.hasNext()){
			if(((Friend)i.next()).uid==uid)
			{
				this.is_friend=true;
				break;
			}
		
		}
	
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auth, menu);
		return true;
	}

	@Override
	public Methods getWSMethodType() {
		
		return WSFactory.Methods.GetUserInfo;
	}

	@Override
	public void updateControls(IModel ui) {
		if(ui==null)
			return;
		UserInfo result=(UserInfo)ui;
		
		Bitmap bm= BitmapFactory.decodeByteArray(result.usericon, 0, result.usericon.length);
		((ImageView) this.findViewById(R.id.img_usericon)).setImageBitmap(bm);
		((TextView)this.findViewById(R.id.txt_desc)).setText(result.selfintro);
		((TextView)this.findViewById(R.id.txt_hobby)).setText(result.hobby);
		if(!this.is_friend){
			((TextView)this.findViewById(R.id.txt_username)).setText(result.username);
			friend.setVisibility(View.VISIBLE);

		}
		else{			
			((TextView)this.findViewById(R.id.txt_username)).setText(result.username+" (friend)");
			friend.setVisibility(View.INVISIBLE);

		}


	
		
	}

	@Override
	public void handleChange(Object data) {
		stat=(ReturnStatus)data;
		handler.post(new Runnable(){

			@Override
			public void run() {
				Toast.makeText(context, stat.msg, Toast.LENGTH_LONG).show();
				
			}});
		
	}

}
