import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import shaotian.android.iamsingle.wssdk.IWSProvider;
import shaotian.android.iamsingle.wssdk.WSProvider;


public class ProviderTest {

	@Test
	public void test_WSProvider_logon_sucess() throws JSONException, UnsupportedEncodingException  {
		IWSProvider p=new WSProvider("localhost");
		int s=0;
		
			p.setAction(WSProvider.METHOD.POST,"/webservice/userauth.svc/logon");
			JSONObject json=new JSONObject();
			json.put("email", "");
			json.put("password", "");
			p.setJSONContent(json);
			JSONObject json2=p.getJSONFromResult();
			s=(Integer) json2.get("userid");
		
		assertEquals(120,s);
	}
	
	@Test
	public void test_WSProvider_logon_fail() throws JSONException, UnsupportedEncodingException  {
		WSProvider p=new WSProvider("localhost");
		int s=0;
		
			p.setAction(WSProvider.METHOD.POST,"/webservice/userauth.svc/logon");
			JSONObject json=new JSONObject();
			json.put("email", "2");
			json.put("password", "");
			p.setJSONContent(json);
			JSONObject json2=p.getJSONFromResult();
			s=(Integer) json2.get("userid");
		
		assertEquals(-1,s);
	}
	
	@Test
	public void test_WSProvider_get() throws JSONException, UnsupportedEncodingException  {
		WSProvider p=new WSProvider("localhost");
		
		
			p.setAction(WSProvider.METHOD.GET,"/webservice/userinfo.svc/getuserinfo?uid=60");
			JSONObject json=new JSONObject();
			json.put("email", "2");
			json.put("password", "");
			
			JSONObject json2=p.getJSONFromResult();
			
		
		assertEquals("{\"username\":\"alohaUser\",\"userdescription\":\"test desc\",\"email\":\"\",\"userhobby\":\"test hobby\",\"password\":\"\"}",
					 json2.toString());
	}
}
