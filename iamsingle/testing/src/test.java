import shaotian.android.iamsingle.netsdk.WorldModeCommunicator;
import shaotian.android.iamsingle.netsdk.model.Location;
import shaotian.android.iamsingle.netsdk.util.LocationList;


public class test {
	public static void main(String []args) throws Exception
	{
		LocationList ret;
		WorldModeCommunicator wc=new WorldModeCommunicator();
		wc.setServer("192.168.0.11", 11000);
		ret=(LocationList)wc.getMap(new Location(1,0,49.280156,-122.784035));
		wc.setServer("0", 0);

	}
	
	
}
