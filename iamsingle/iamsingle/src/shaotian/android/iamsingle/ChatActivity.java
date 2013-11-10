package shaotian.android.iamsingle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import shaotian.android.iamsingle.async.ServiceMessageSystem;
import shaotian.android.iamsingle.netsdk.model.IModel;
import shaotian.android.iamsingle.netsdk.model.Message;
import shaotian.android.iamsingle.netsdk.model.MessageHistory;
import shaotian.android.iamsingle.netsdk.model.MessageHistory.MessageIterator;
import shaotian.android.iamsingle.netsdk.model.MessageHistoryList;
import shaotian.android.iamsingle.socketsdk.MessageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ChatActivity extends Activity implements IListener {
	@Override
	protected void onStop() {
		AsyncTask asnc=new AsyncTask(){
			@Override
			protected Object doInBackground(Object... params) {
				try {
					mgr.unRegister();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}};
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			asnc.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			asnc.execute();	
		super.onPause();
	}


	protected void registerSrvr() {
		AsyncTask asnc=new AsyncTask(){
			@Override
			protected Object doInBackground(Object... params) {
				try {
					mgr.registerToServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}};
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			asnc.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			asnc.execute();	
		super.onPause();		super.onResume();
	}

	MessageManager mgr;
	Context context;
	ListView lis;
	Button btn;
	EditText inputtxt;
	Handler handler=new Handler();
	int touid=-1;
	Message tmpmsg;
	MessageHistory hist;
	
	List<Map<String, Object>> msgList=new ArrayList<Map<String, Object>>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		context=this;
		
		try{
			touid=(Integer) this.getIntent().getExtras().get("uid");
		}catch(Exception e){}
		mgr=MessageManager.instance();
		lis=(ListView) this.findViewById(R.id.lst_msgs);
		btn=(Button) this.findViewById(R.id.btn_submitmsg);
		btn.setOnClickListener(new onclicklistener());
		hist=mgr.getMessageHistory(touid);
		initMsgList();
		registerSrvr();
		updateListView();
		inputtxt=(EditText) this.findViewById(R.id.txt_entermsg);
		ServiceMessageSystem.getListenerManager().addListener(this);
		
	}

	
	private void updateListView()
	{
		
		
		
		SimpleAdapter adapter = new SimpleAdapter(this,this.msgList,R.layout.chat_list_item,
                new String[]{"msg"},
                new int[]{R.id.txt_chat_msg});
		lis.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	private void initMsgList()
	{
		if(hist==null)
			return;
		MessageIterator it=hist.getIterator();
		while(it.hasNext()){
			updateMsgList(it.next());
			
		}
		
	}
	private void updateMsgList(Message msg) {
		
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("msg", "From "+msg.senderName+" msg:"+msg.message);
			msgList.add(map);
			
		
	}
	
	private void updateMsgList(String msgstr) {
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "From me"+" msg:"+msgstr);
		msgList.add(map);
		
	
}
	
	private class onclicklistener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			
			AsyncTask asnc=new AsyncTask(){
				String msg;
				@Override
				protected void onPostExecute(Object result) {
					super.onPostExecute(result);
					updateMsgList(msg);
					updateListView();
				}

				@Override
				protected Object doInBackground(Object... params) {

					
					if((msg=inputtxt.getText().toString())==null)
						return null;
					
						try {
							mgr.sendMessage(touid, msg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					return null;
				}};
			
			
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        			asnc.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        		else
        			asnc.execute();	
			
		}}

	@Override
	public void handleChange(Object data) {
		
		
		tmpmsg=(Message)data;
		handler.post(new Runnable(){

			@Override
			public void run() {
				updateMsgList(tmpmsg);
				updateListView();				
			}});
		
		
	}
	

}
