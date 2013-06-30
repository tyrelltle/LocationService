import java.io.UnsupportedEncodingException;

import shaotian.android.iamsingle.netsdk.auth.AuthManager;


public class entry {

	public static void main(String []args)
	{
		AuthManager auth=new AuthManager();
		try {
			auth.register();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
