package shaotian.android.iamsingle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import shaotian.android.iamsingle.async.ServiceMessageSystem;
import shaotian.android.iamsingle.netsdk.model.MessageHistoryList;
import shaotian.android.iamsingle.netsdk.util.Pair;
import shaotian.android.iamsingle.socketsdk.MessageManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ChatListActivity extends ListActivity implements IListener{

	Context context;
	MessageManager mgr;
	ListView lisView;
	private Handler handler=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_chat_list);
		this.handler=new Handler();
		ServiceMessageSystem.getListenerManager().addListener(this);
		context=this;		
		Bundle extras=this.getIntent().getExtras();
		mgr=MessageManager.instance();
		MessageHistoryList msgLis=mgr.getHistoryList();
		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.activity_chat_list,
                new String[]{"senderid"},
                new int[]{R.id.txt_sendername_histlis});
        setListAdapter(adapter);
		
	}
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> ret=new ArrayList<Map<String, Object>>();
		MessageHistoryList lis=mgr.getHistoryList();
		Iterator it=lis.getIterator();
		while(it.hasNext())
		{
			Map<String, Object> map = new HashMap<String, Object>();
			String uname=((MessageHistoryList.Node)it.next()).senderName;
			map.put("senderid", uname);
			ret.add(map);
			
		}
		return ret;
	}
	@Override
	public void handleChange(Object data) {
		
		this.handler.post(new Runnable(){

			@Override
			public void run() {
					SimpleAdapter adapter = new SimpleAdapter(context,getData(),R.layout.activity_chat_list,
                new String[]{"senderid"},
                new int[]{R.id.txt_sendername_histlis});
					setListAdapter(adapter);
			}});
	
	}



}