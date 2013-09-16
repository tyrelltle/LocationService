package shaotian.android.iamsingle.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Runner;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import shaotian.android.iamsingle.ChatActivity;
import shaotian.android.iamsingle.ChatListActivity;
import shaotian.android.iamsingle.ListenerManager;
import shaotian.android.iamsingle.netsdk.model.Message;
import shaotian.android.iamsingle.netsdk.model.MessageHistory;
import shaotian.android.iamsingle.socketsdk.INetProvider;
import shaotian.android.iamsingle.socketsdk.MessageManager;

public class ChatActivityTest extends ActivityInstrumentationTestCase2<ChatActivity>{

	private ChatActivity activity=null;
	private ListView listview=null;
	private Button submitbtn=null;
	private EditText msginput=null;
	private MessageManager msgmgr=null;
	private int myuid=1;
	public ChatActivityTest() {
		super("shaotian.android.iamsingle", ChatActivity.class);
		// TODO Auto-generated constructor C
	}   
	   
    @Override
    protected void setUp() throws Exception {
		msgmgr=MessageManager.initialize(29, new ProviderMock());

		activity=this.getActivity();
		
		listview=(ListView) activity.findViewById(shaotian.android.iamsingle.R.id.lst_msgs);
		submitbtn=(Button) activity.findViewById(shaotian.android.iamsingle.R.id.btn_submitmsg);
		msginput=(EditText) activity.findViewById(shaotian.android.iamsingle.R.id.txt_entermsg);

	}
	
	
	public void test()
	{		

		this.getInstrumentation().runOnMainSync(new Runnable(){

			@Override
			public void run() {
				msginput.setText("test msg");
				
			}});
		submitbtn.callOnClick();
		assertEquals(listview.getAdapter().getCount(),1);
	}
	


	private class ProviderMock implements INetProvider{
			public String sentmsg=null;
			public boolean nextRecieveReturnAck=false;
			@Override
			public void send(String msg) {
				sentmsg=msg;	
			}
		
			@Override
			public String receive() throws IOException {
				return nextRecieveReturnAck?"ack":"hello world msg";
			}

			@Override
			public void disconnect() throws IOException {
			}}
	   
	   private class ListenerProviderMock implements INetProvider{
		   	 int index=0;
		   	 final String[]arr={"helloworld","close"};
			@Override
			public void send(String msg) {}
		
			@Override
			public String receive() throws IOException {
				if(index==2)
					return "";
				return "26 alohaUser "+arr[index++];
			}

			@Override
			public void disconnect() throws IOException {
			}}
	   
}
