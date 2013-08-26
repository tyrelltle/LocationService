package shaotian.android.iamsingle.wssdk;

import shaotian.android.iamsingle.wssdk.IWSProvider;

public class WSManager {

	protected IWSProvider mWsProvider;
	public  WSManager(IWSProvider wsProvider) {
		this.mWsProvider=wsProvider;
	}

}
