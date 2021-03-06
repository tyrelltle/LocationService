package shaotian.android.iamsingle.UIShared;

import java.lang.reflect.Constructor;

import com.google.android.gms.maps.model.Marker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

public final class SharedUtil {
	public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	// Milliseconds per second
	public static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
    // Update frequency in milliseconds
    public static final long UPDATE_INTERVAL =MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    public static final int FASTEST_INTERVAL_IN_SECONDS = 5;
    // A fast frequency ceiling in milliseconds
    public static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    
    public static final String SHARED_PREFERENCES = "shaotian.android.iamsingle.location.SHARED_PREFERENCES";
    

    public static final String SHARED_EMAIL="email";
    public static final String SHARED_PWD="password";
    public static final String SHARED_UID="uid";
    public static final String SHARED_ALTITUTE="altitute";
    public static final String SHARED_LONGTITUDE="longtitude";
    public static final String SHARED_LATITUDE="latitude";

	public static class MapMarkerInvalidStateException extends Exception
	{
		private static final long serialVersionUID = 1L;
		
		public MapMarkerInvalidStateException(String message)
		{
			super(message);
		}
	}
    
    //retrieve application configuration settings
    public static Object getConfig(Class c,String nm, Context context)
    {
    	Object ret=null;
    	ApplicationInfo ai;
		try {
			ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
			
			if(String.class.equals(c))
				ret= bundle.getString(nm);
			if(Integer.class.equals(c))
				ret= bundle.getInt(nm);
			
		}finally
		{
			return ret;
		}
		
    }
	
	
	
}
