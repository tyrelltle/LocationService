package shaotian.android.iamsingle;

import shaotian.android.iamsingle.async.AsyncUpdateLocation;
import shaotian.android.iamsingle.async.AsyncUserInfo;
import shaotian.android.iamsingle.async.AsyncUserOp;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends Activity {

	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		context=this;
		//	public UserInfoUIContainer(TextView un,TextView ud, TextView uh, ImageView img)
		Bundle extras=this.getIntent().getExtras();
		AsyncUserInfo task=new AsyncUserInfo(extras.getInt("uid"),	context, (TextView)(this.findViewById(R.id.txt_username)),
										(TextView)(this.findViewById(R.id.txt_desc)),
										(TextView)(this.findViewById(R.id.txt_hobby)),
										(ImageView)(this.findViewById(R.id.img_usericon)));
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

}
