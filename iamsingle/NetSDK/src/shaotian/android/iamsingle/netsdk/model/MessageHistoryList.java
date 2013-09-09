package shaotian.android.iamsingle.netsdk.model;

import java.util.ArrayList;
import java.util.Iterator;

import shaotian.android.iamsingle.netsdk.util.Pair;

public class MessageHistoryList implements IModel{
	public ArrayList<Node> lis=new ArrayList<Node>();
	public void add(Node n) {
		
		lis.add(n);
	}

	public Node get(int i )
	{
		return lis.get(i);
	}

	public Iterator getIterator() {
		// TODO Auto-generated method stub
		return new MessageHistoryListIterator(lis);
	}
	
	
	
	public static class Node
	{
		public String senderName;
		public int senderId;
		public boolean hasNewMsg;
		
		
	}
	public static class MessageHistoryListIterator implements Iterator
	{
		ArrayList<Node> lis;
		private int index=0;
		public MessageHistoryListIterator(ArrayList<Node> list) {
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
