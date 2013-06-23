package shaotian.android.iamsingle.netsdk;
import shaotian.android.iamsingle.netsdk.util.*;
public interface INetCommunicator {

	public void setServer(String ip,int port);
	
	public void updateLoc(INetParam data) throws Exception;
	
	public Object getMap(INetParam data) throws Exception;

}
