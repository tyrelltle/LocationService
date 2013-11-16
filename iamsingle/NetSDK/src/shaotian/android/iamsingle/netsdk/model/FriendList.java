package shaotian.android.iamsingle.netsdk.model;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import shaotian.android.iamsingle.netsdk.util.Pair;

public class FriendList implements IModel{
	public ArrayList<Friend> lis=new ArrayList<Friend>();
	
	public FriendList(JSONObject json) throws JSONException{
		
		JSONArray arr=json.getJSONArray("Friends");
		for(int i=0;i<arr.length();i++)
		{
			JSONObject j=arr.getJSONObject(i);
			lis.add(new Friend(j));
		}
	}
	public void add(Friend n) {
		
		lis.add(n);
	}

	public Friend get(int i )
	{
		return lis.get(i);
	}

	public Iterator getIterator() {
		// TODO Auto-generated method stub
		return new FriendyListIterator(lis);
	}
	
	
	
	public static class Node
	{
		public String senderName;
		public int senderId;
		public boolean hasNewMsg;
		
		
	}
	public static class FriendyListIterator implements Iterator
	{
		ArrayList<Friend> lis;
		private int index=0;
		public FriendyListIterator(ArrayList<Friend> list) {
			lis=list;
		}

		@Override
		public boolean hasNext() {
			return index<lis.size();
		}

		@Override
		public Object next() {
			return lis.get(index++);
		}

		@Override
		public void remove() {
			
		}
		
		
	}
}
