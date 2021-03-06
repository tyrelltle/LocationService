package shaotian.android.iamsingle.wssdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import shaotian.android.iamsingle.wssdk.AuthManager;
import shaotian.android.iamsingle.wssdk.IWSProvider;
import shaotian.android.iamsingle.wssdk.UserProfileManager;
import shaotian.android.iamsingle.wssdk.WSFactory;
import shaotian.android.iamsingle.netsdk.model.UserInfo;
import shaotian.android.iamsingle.wssdk.WSFactory.Methods;


public class WSFactory {

	private static WSFactory instance;
	private IWSProvider provider;
	
	
	public static enum Methods{
		RegisterUser,LogonUser,GetUserInfo,GetUserIcon
		
	}
	private WSFactory(IWSProvider provider)
	{
		this.provider=provider;
	}
	public static WSFactory Initialize(IWSProvider provider)
	{
		instance=new WSFactory(provider);
		
		return instance;
	}
	
	public static WSFactory Instance() {
	
		return instance;
	}
	
	
	//-----------factory methods--------------
	public FriendManager getFriendManager(){
		
		return FriendManager.Instance(this.provider);
		
	}
	public JSONObject RegisterUser(String username, String password) throws UnsupportedEncodingException, JSONException {
		AuthManager mgr=new AuthManager(this.provider);
	    return mgr.register(username, password);
	}
	public JSONObject LogonUser(String string, String string2) throws UnsupportedEncodingException, JSONException {
		AuthManager mgr=new AuthManager(this.provider);
	    return mgr.logon(string, string2);
	}
	public UserInfo GetUserInfo(int i) throws Exception {
		UserProfileManager mgr=new UserProfileManager(this.provider);
		return mgr.getUserInfo(i);
	}
	public byte[] GetUserIcon(int i) throws ClientProtocolException, IOException {
		UserProfileManager mgr=new UserProfileManager(this.provider);
		return mgr.GetUserImage(i);
	}


	
}
