package shaotian.android.iamsingle.socketsdk;

public abstract class NetManager {
	INetProvider mProvider = null;
	int myUserId=-1;
	public NetManager(int id, INetProvider prov) {
		mProvider = prov;
		myUserId=id;
	}

}
