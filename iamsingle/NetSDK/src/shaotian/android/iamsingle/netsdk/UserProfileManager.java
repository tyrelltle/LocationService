package shaotian.android.iamsingle.netsdk;
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

import shaotian.android.iamsingle.netsdk.model.UserInfo;
import shaotian.android.iamsingle.netsdk.util.WSUtil;
public class UserProfileManager {
	
    private String server=null;
	public UserProfileManager(String server)
	{
		this.server=server;
	}
	
	public UserInfo getUserInfo(int uid) throws Exception
	{
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpget = new HttpGet("http://"+server+ "/webservice/userinfo.svc/getUserInfo?uid="+uid);
		
		String text = null;
		HttpResponse response = httpClient.execute(httpget, localContext);
		HttpEntity entity = response.getEntity();
		text = WSUtil.getContentFromEntity(entity);
		JSONObject json= new JSONObject(text);
		
		
		UserInfo ret=new UserInfo(json);
		ret.usericon=GetUserImage( uid);
		return ret;
		
		
		
	
	}
	
	
	private byte[] GetUserImage(int uid) 
	{
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpget = new HttpGet("http://"+server+ "/webservice/userinfo.svc/getUserIcon?uid="+uid);
		byte []ret=null;
		try {
		HttpResponse response = httpClient.execute(httpget, localContext);
		HttpEntity entity = response.getEntity();
		  
		ret= WSUtil.getBinaryFromEntity(entity);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return ret;
		
	}
	

}
