package shaotian.android.iamsingle.netsdk.model;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * user details when clicking on map markers
 * */
public class UserInfo {
	public byte [] usericon=null;
	public int uid;
	public String username;
	public String hobby;
	public String selfintro;
	
	
	public UserInfo()
	{}
	public UserInfo(JSONObject json) throws JSONException
	{
		username=json.getString("username");
		hobby=json.getString("userhobby");
		selfintro=json.getString("userdescription");
	}
	
}
