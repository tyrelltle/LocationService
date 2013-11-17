package shaotian.android.iamsingle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.async.AsyncFriend;
import shaotian.android.iamsingle.async.ServiceMessageSystem;
import shaotian.android.iamsingle.netsdk.model.Friend;
import shaotian.android.iamsingle.netsdk.model.FriendList;
import shaotian.android.iamsingle.netsdk.model.Message;
import shaotian.android.iamsingle.netsdk.model.MessageHistoryList;
import shaotian.android.iamsingle.netsdk.model.MessageHistoryList.Node;
import shaotian.android.iamsingle.netsdk.util.Pair;
import shaotian.android.iamsingle.socketsdk.MessageManager;
import shaotian.android.iamsingle.wssdk.WSFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FriendListActivity extends ListActivity implements IListener{

	Context context;
	MessageManager mgr;
	ListView lisView;
	private Handler handler=null;
	Map<String, Object> listmap=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_chat_list);
		this.handler=new Handler();
	    this.context=this;
	    updateListView();
	  /*  SharedPreferences settings = context.getSharedPreferences(SharedUtil.SHARED_PREFERENCES, 0);
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			new AsyncFriend(this, settings.getInt(SharedUtil.SHARED_UID, -1) ).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			new AsyncFriend(this, settings.getInt(SharedUtil.SHARED_UID, -1) ).execute();	
        */
		
	}
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> ret=new ArrayList<Map<String, Object>>();
		Iterator it=WSFactory.Instance().getFriendManager().list.getIterator();
		while(it.hasNext())
		{
			listmap = new HashMap<String, Object>();
			Friend node=(Friend) it.next();
			String uname=node.name;
			int uid=node.uid;
			listmap.put("senderid", uname);
			listmap.put("hiddenuid", String.valueOf(uid));
			ret.add(listmap);
			
		}
		return ret;
	}
	
	protected void updateListView() {
		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.activity_chat_list,
                new String[]{"senderid","hiddenuid"},
                new int[]{R.id.txt_sendername_histlis,R.id.hidden_uid});
        setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Map<String, String> map=(Map<String, String>) l.getItemAtPosition(position);
		
		Intent i=new Intent(context, UserInfoActivity.class);
		i.putExtra("uid", Integer.valueOf(map.get("hiddenuid")));
	    startActivity(i);	
	}
	@Override
	public void handleChange(Object data) {
		handler.post(new Runnable(){

			@Override
			public void run() {

				updateListView();				
			}});
		
		
	}



}
