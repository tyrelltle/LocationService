package shaotian.android.iamsingle.wssdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import shaotian.android.iamsingle.wssdk.IWSProvider;
import shaotian.android.iamsingle.netsdk.util.WSUtil;
import shaotian.android.iamsingle.wssdk.WSProvider.METHOD;

//web service provider
public class WSProvider implements IWSProvider{

	protected HttpClient httpClient = new DefaultHttpClient();
	protected HttpContext localContext = new BasicHttpContext();
	protected HttpRequestBase mMethod;
	protected  String mServerIP;
	protected  String mAction;
	protected  METHOD mMethodType;
	public enum METHOD{
		GET,PUT,POST
	}
	
	
	
	public WSProvider(String serverIp) {
		this.mServerIP=serverIp;
	}



	public void setAction(METHOD method, String actionurl) {
		mMethodType=method;
		switch(method){
		
		case POST:mMethod = new HttpPost("http://"+mServerIP+ actionurl);
				  break;
		case GET: mMethod = new HttpGet("http://"+mServerIP+ actionurl);
				  break;
		case PUT: mMethod = new HttpPut("http://"+mServerIP+ actionurl);
		  		  break;
		}
	}



	public void setJSONContent(JSONObject json) throws UnsupportedEncodingException {
		StringEntity input = new StringEntity(json.toString());
		input.setContentType("application/json");
		switch(mMethodType)
		{
		case PUT:
			((HttpPut)mMethod).setEntity(input);
			break;
		case POST:
			((HttpPost)mMethod).setEntity(input);
			break;
		default: return;
		
		
		}
		
		
	}



	public JSONObject getJSONFromResult() throws UnsupportedEncodingException {

		
		String text = null;
		try {
		HttpResponse response = httpClient.execute(mMethod, localContext);
		HttpEntity entity = response.getEntity();
		text = WSUtil.getContentFromEntity(entity);
		return new JSONObject(text);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		
		
		return null;
	}



	@Override
	public byte[] getBinaryFromResult() throws ClientProtocolException, IOException {
		HttpResponse response = httpClient.execute(mMethod, localContext);
		HttpEntity entity = response.getEntity();
		return WSUtil.getBinaryFromEntity(entity);
	}

}
