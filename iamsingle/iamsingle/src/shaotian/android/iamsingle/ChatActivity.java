package shaotian.android.iamsingle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import shaotian.android.iamsingle.async.ServiceMessageSystem;
import shaotian.android.iamsingle.netsdk.model.Message;
import shaotian.android.iamsingle.netsdk.model.MessageHistoryList;
import shaotian.android.iamsingle.socketsdk.MessageManager;
import android.os.AsyncTask;
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

public class ChatActivity extends Activity implements IListener {
	MessageManager mgr;
	Context context;
	ListView lis;
	Button btn;
	EditText inputtxt;
	Handler handler=new Handler();
	int touid=-1;
	Message tmpmsg;
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
			String msg;
			if((msg=inputtxt.getText().toString())==null)
				return;
			updateMsgList(msg);
			updateListView();
			
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
