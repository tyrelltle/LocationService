package shaotian.android.iamsingle.wssdk;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import shaotian.android.iamsingle.wssdk.IWSProvider;
import shaotian.android.iamsingle.wssdk.WSManager;
import shaotian.android.iamsingle.wssdk.WSProvider;
import shaotian.android.iamsingle.netsdk.util.WSUtil;
public class AuthManager extends WSManager{
	


	
	public AuthManager(IWSProvider wsProvider) {
		super(wsProvider);
	}

	public JSONObject register(String email,String pwd) throws UnsupportedEncodingException, JSONException
	{
		
		this.mWsProvider.setAction(WSProvider.METHOD.PUT, "/webservice/userauth.svc/register");
		
		JSONObject json=new JSONObject();
		json.put("email", email);
		json.put("password", pwd);
		this.mWsProvider.setJSONContent(json);
		return this.mWsProvider.getJSONFromResult();
		
	}
	
	public JSONObject logon(String email,String pwd) throws UnsupportedEncodingException, JSONException
	{
		
		this.mWsProvider.setAction(WSProvider.METHOD.POST, "/webservice/userauth.svc/logon");
		
		JSONObject json=new JSONObject();
		json.put("email", email);
		json.put("password", pwd);
		this.mWsProvider.setJSONContent(json);
		return this.mWsProvider.getJSONFromResult();

	}
}
