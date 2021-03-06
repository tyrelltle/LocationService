package shaotian.android.iamsingle;

import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.async.AsyncUserAuth;
import shaotian.android.iamsingle.wssdk.WSFactory;
import shaotian.android.iamsingle.wssdk.WSProvider;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AuthActivity extends Activity {

	EditText txt_email,txt_pwd;
	Button register,logon;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
		
		WSFactory.Initialize(new WSProvider((String) SharedUtil.getConfig(String.class, "wsserverip", this)));
		
		context=this;
		txt_email=(EditText) this.findViewById(R.id.email);
		txt_pwd=(EditText) this.findViewById(R.id.pwd);
		register=(Button)this.findViewById(R.id.btnregister);
		register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AsyncUserAuth reg=new AsyncUserAuth(AsyncUserAuth.ACTION.REGISTER, context, txt_email.getText().toString(), txt_pwd.getText().toString());
        		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        			reg.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        		else
        			reg.execute();	
            }
        });
		
		logon=(Button)this.findViewById(R.id.btnlogon);
		logon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AsyncUserAuth reg=new AsyncUserAuth(AsyncUserAuth.ACTION.LOGON, context, txt_email.getText().toString(), txt_pwd.getText().toString());
        		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        			reg.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        		else
        			reg.execute();	
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auth, menu);
		return true;
	}

}
