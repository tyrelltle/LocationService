package shaotian.android.iamsingle.wssdk;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import shaotian.android.iamsingle.wssdk.IWSProvider;
import shaotian.android.iamsingle.wssdk.WSManager;
import shaotian.android.iamsingle.wssdk.WSProvider;
import shaotian.android.iamsingle.netsdk.model.UserInfo;
import shaotian.android.iamsingle.netsdk.util.WSUtil;
public class UserProfileManager extends WSManager{
	
	public UserProfileManager(IWSProvider wsProvider) {
		super(wsProvider);
	}
	
	public UserInfo getUserInfo(int uid) throws Exception
	{
		
		
		this.mWsProvider.setAction(WSProvider.METHOD.GET, "/webservice/userinfo.svc/getUserInfo?uid="+uid);
		
		
		JSONObject json=this.mWsProvider.getJSONFromResult();
		
		
		UserInfo ret=new UserInfo(json);
		ret.usericon=GetUserImage( uid);
		return ret;
		
		
		
	
	}
	
	
	public byte[] GetUserImage(int uid) throws ClientProtocolException, IOException 
	{
		this.mWsProvider.setAction(WSProvider.METHOD.GET, "/webservice/userinfo.svc/getUserIcon?uid="+uid);
		return this.mWsProvider.getBinaryFromResult();
		
	
		
	}
	

}
