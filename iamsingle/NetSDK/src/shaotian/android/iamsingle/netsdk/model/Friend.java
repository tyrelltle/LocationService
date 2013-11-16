package shaotian.android.iamsingle.netsdk.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Friend implements IModel {

	public String name;
	public int uid;
	
	public Friend(JSONObject json) throws JSONException{
		name=json.getString("Name");
		uid=json.getInt("Uid");	
	}
	public Friend(String uname,int uid) throws JSONException{
		name=uname;
		uid=uid;	
	}
}
