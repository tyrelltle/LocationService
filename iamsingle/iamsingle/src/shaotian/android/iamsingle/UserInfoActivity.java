package shaotian.android.iamsingle;

import shaotian.android.iamsingle.async.AsyncUpdateLocation;
import shaotian.android.iamsingle.async.AsyncInfoCtrUpdate;
import shaotian.android.iamsingle.async.AsyncUserOp;
import shaotian.android.iamsingle.netsdk.WSFactory;
import shaotian.android.iamsingle.netsdk.WSFactory.Methods;
import shaotian.android.iamsingle.netsdk.model.IModel;
import shaotian.android.iamsingle.netsdk.model.UserInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends Activity implements IInfoContainer{

	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		context=this;
		Bundle extras=this.getIntent().getExtras();
		AsyncInfoCtrUpdate task=new AsyncInfoCtrUpdate(extras.getInt("uid"),	this);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			 task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			 task.execute();	
		
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
		UserInfo result=(UserInfo)ui;
		Bitmap bm= BitmapFactory.decodeByteArray(result.usericon, 0, result.usericon.length);
		((ImageView) this.findViewById(R.id.img_usericon)).setImageBitmap(bm);
		((TextView)this.findViewById(R.id.txt_desc)).setText(result.selfintro);
		((TextView)this.findViewById(R.id.txt_hobby)).setText(result.hobby);
		((TextView)this.findViewById(R.id.txt_username)).setText("test user name");
		
	
		
	}

}
