package shaotian.android.iamsingle.UIShared;

public final class SharedUtil {
	public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	// Milliseconds per second
	public static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    public static final int UPDATE_INTERVAL_IN_SECONDS = 10;
    // Update frequency in milliseconds
    public static final long UPDATE_INTERVAL =MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    public static final int FASTEST_INTERVAL_IN_SECONDS = 10;
    // A fast frequency ceiling in milliseconds
    public static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    
    public static final String SHARED_PREFERENCES = "shaotian.android.iamsingle.location.SHARED_PREFERENCES";
    

    public static final String SHARED_EMAIL="email";
    public static final String SHARED_PWD="password";
    public static final String SHARED_UID="uid";

    
    	
    	
}
