package shaotian.android.iamsingle.socketsdk;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Hashtable;

import shaotian.android.iamsingle.netsdk.model.Message;
import shaotian.android.iamsingle.netsdk.model.MessageHistory;
import shaotian.android.iamsingle.netsdk.model.MessageHistoryList;
import shaotian.android.iamsingle.netsdk.util.Helpers;

public class MessageManager extends NetManager{

	private static MessageManager instance=null;
	private Hashtable<Integer, MessageHistory> mHistories=new Hashtable<Integer, MessageHistory>();

	

	public MessageHistory getMessageHistory(int i) {
		
		return mHistories.get(i);
	}
	
	public int getHistoryCount()
	{
			return mHistories.size();
		
	}
	
	public void addToMessageHistory(int uid,Message msg) {
		MessageHistory hist=this.mHistories.get(uid);
		if(hist==null){
			hist=new MessageHistory();
			this.mHistories.put(uid, hist);
		}
		hist.addMessage(msg);
		
	}
	
	private MessageManager(int id,INetProvider provider) {
		super(id,provider);
	}

	public static synchronized  MessageManager initialize(int uid,INetProvider provider)
	{
		if(instance!=null)
			throw new IllegalStateException("MessageManager already initialized");
		instance=new MessageManager(uid,provider);
		return instance;
		
	}
	
	public static synchronized  MessageManager instance()
	{
		
		return instance;
	}

	public void registerToServer() throws IOException {
		mProvider.send("reg "+myUserId);
	    if(!("ack".equals(mProvider.receive())))
			throw new IllegalStateException("server not acked after sender 'reg' ");
	}
	
	public void unRegister() throws IOException {
		mProvider.send("close");
	/*    if(!("ack".equals(mProvider.receive())))
			throw new IllegalStateException("server not acked after sender 'close' ");*/
		//mProvider.disconnect();
	    
	}


	public void sendMessage(int toid, String string) throws IOException {
		mProvider.send("msg "+toid+" "+string);
		String res=mProvider.receive();
		/*if(!("ack".equals(res)))
			throw new IllegalStateException("server not acked after sending message to uid ="+toid);
		*/
		//create a new message in message history with this user
		Message msg=new Message(myUserId, toid, string);
		addToMessageHistory(toid,msg);
	}


	public MessageListener CreateMessageListener(INetProvider provider) throws InstantiationException, IllegalAccessException {
		
		
		return new MessageListener(this.myUserId, provider ) ;
	}

	public MessageHistoryList getHistoryList() {
		MessageHistoryList ret=new MessageHistoryList();
		for(int i : this.mHistories.keySet())
		{
			MessageHistory cur= this.mHistories.get(i);
			MessageHistoryList.Node node=new MessageHistoryList.Node();
			node.hasNewMsg=cur.hasNewMsg();
			node.senderId=i;
			node.senderName=cur.getSenderName();
			ret.add(node);
			
		}
		
		return ret;
	}


	

	
}
