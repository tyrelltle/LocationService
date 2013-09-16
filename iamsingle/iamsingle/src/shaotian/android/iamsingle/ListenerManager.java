package shaotian.android.iamsingle;

import java.util.ArrayList;


public class ListenerManager {
	ArrayList<IListener> listeners=new ArrayList<IListener>();
	public void addListener(IListener listener) {
		listeners.add(listener);
	}
	public void notifyListeners() {
		this.notifyListeners(null);
	}
	
	public void notifyListeners(Object obj)
	{
		for(IListener i : listeners)
		{
			i.handleChange(obj);
			
		}
	}
	public int listenerCount() {
		return listeners.size();
	}

}
