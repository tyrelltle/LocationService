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
import shaotian.android.iamsingle.netsdk.model.FriendList;
import shaotian.android.iamsingle.netsdk.model.ReturnStatus;
import shaotian.android.iamsingle.netsdk.model.UserInfo;
import shaotian.android.iamsingle.netsdk.util.WSUtil;
public class FriendManager extends WSManager{
	private static FriendManager instance=null;
	
	public static FriendManager Instance(IWSProvider provider){
		if(instance==null)
			instance = new FriendManager(provider);
		return instance;
		
	}
	
	
	public FriendManager(IWSProvider wsProvider) {
		super(wsProvider);
	}
	
	public FriendList getFriendList(int uid) throws Exception
	{
				
		this.mWsProvider.setAction(WSProvider.METHOD.GET, "/webservice/friendservice.svc/getfriends?uid="+uid);
		
		
		JSONObject json=this.mWsProvider.getJSONFromResult();
		
		
		FriendList ret=new FriendList(json);
		
		return ret;

	}
	
	public ReturnStatus addFriend (int uid,int touid) throws Exception
	{
		String str=String.format("/webservice/friendservice.svc/addFriendById?myuid=%d&touid=%d", uid,touid);
		this.mWsProvider.setAction(WSProvider.METHOD.PUT, str);
		
		
		JSONObject json=this.mWsProvider.getJSONFromResult();
		
		
		ReturnStatus ret=new ReturnStatus(json);
		
		return ret;

	}
	

	

}
