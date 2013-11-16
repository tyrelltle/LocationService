package shaotian.android.iamsingle.netsdk.model;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * return status from webservice call
 * */
public class ReturnStatus implements IModel{
	public String msg;
	public int errno;
	
	public ReturnStatus(JSONObject json) throws JSONException
	{
		errno=json.getInt("statuscode");
		msg=json.getString("msg");
	}
	
	public ReturnStatus(String msg,int errno)
	{
		this.msg=msg;
		this.errno=errno;
	}
}
