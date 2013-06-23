package shaotian.android.iamsingle.netsdk;

public interface INetCommunicator {

	public void setServer(String ip,int port);
	
	public void updateLoc(INetParam data) throws Exception;
	
	public void getMap(INetParam data) throws Exception;

}
