package shaotian.android.iamsingle.UIShared;

import java.sql.Timestamp;
import java.util.Date;

import com.google.android.gms.maps.model.Marker;

public class TimedMarker
{
	public static final int MAX_IDLE_TIME=5000;
	private Marker marker;
	protected Timestamp  time=null;
	
	public TimedMarker()
	{}
	
	public TimedMarker(Marker m)
	{
		marker=m;
		
	}
	
	public boolean isSame(Marker m) {
		
		
		return this.marker.equals(m);
	}



	public void startTime()
	{
		if(time==null)
			time=new Timestamp(new Date().getTime());
	}
	
	public void clearTime()
	{
		time=null;
	}
	public Marker getMarker()
	{
		return marker;
	}
	
	

	public boolean timeout()
	{
		if(time==null)
			return false;
		return (new Date().getTime())-time.getTime()>=this.MAX_IDLE_TIME;
	}
}