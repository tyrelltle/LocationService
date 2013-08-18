package shaotian.android.iamsingle.mocks;

import java.util.Date;

import shaotian.android.iamsingle.UIShared.TimedMarker;

import com.google.android.gms.maps.model.Marker;

public class TimedMarkerStub extends TimedMarker	
{



	public TimedMarkerStub(Marker m) {
		super(m);
	}

	@Override
	public boolean timeout() {
		if(super.time==null)
			return false;
		return (new Date().getTime())-time.getTime()>0;
	}
	


}
