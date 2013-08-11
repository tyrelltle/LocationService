package shaotian.android.iamsingle.netsdk;
import shaotian.android.iamsingle.netsdk.util.*;
public interface ILocationCommunicator {

	public void setServer(String ip,int port);
	
	public void updateLoc(Object data) throws Exception;
	
	public Object getMap(Object data) throws Exception;

}
