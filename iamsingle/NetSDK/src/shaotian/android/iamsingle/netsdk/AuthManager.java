package shaotian.android.iamsingle.netsdk;
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

import shaotian.android.iamsingle.netsdk.util.WSUtil;
public class AuthManager {
	
    private String server=null;
	public AuthManager(String server)
	{
		this.server=server;
	}
	
	public JSONObject register(String email,String pwd) throws UnsupportedEncodingException, JSONException
	{
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPut httpPut = new HttpPut("http://"+server+ "/webservice/userauth.svc/register");
		//get
		JSONObject json=new JSONObject();
		json.put("email", email);
		json.put("password", pwd);
		StringEntity input = new StringEntity(json.toString());
		input.setContentType("application/json");
		httpPut.setEntity(input);
		String text = null;
		try {
		HttpResponse response = httpClient.execute(httpPut, localContext);
		HttpEntity entity = response.getEntity();
		text = WSUtil.getContentFromEntity(entity);
		return new JSONObject(text);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return null;
		
	}
	
	public JSONObject logon(String email,String pwd) throws UnsupportedEncodingException, JSONException
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost("http://"+server+ "/webservice/userauth.svc/logon");
		
		JSONObject json=new JSONObject();
		json.put("email", email);
		json.put("password", pwd);
		StringEntity input = new StringEntity(json.toString());
		input.setContentType("application/json");
		httpPost.setEntity(input);
		String text = null;
		try {
		HttpResponse response = httpClient.execute(httpPost, localContext);
		HttpEntity entity = response.getEntity();
		text = WSUtil.getContentFromEntity(entity);
		return new JSONObject(text);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return null;
		
	}
}
