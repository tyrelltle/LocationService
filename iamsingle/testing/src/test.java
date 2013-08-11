import shaotian.android.iamsingle.netsdk.LocationCommunicator;
import shaotian.android.iamsingle.netsdk.UserProfileManager;
import shaotian.android.iamsingle.netsdk.model.Location;
import shaotian.android.iamsingle.netsdk.model.UserInfo;
import shaotian.android.iamsingle.netsdk.util.LocationList;


public class test {
	public static void main(String []args) throws Exception
	{
		/*LocationList ret;
		LocationCommunicator wc=new LocationCommunicator();
		wc.setServer("192.168.0.11", 11000);
		ret=(LocationList)wc.getMap(new Location(1,49.280156,-122.784035));
		wc.setServer("0", 0);
*/
		UserProfileManager m=new UserProfileManager("172.16.42.29");
		UserInfo json=m.getUserInfo(0);
		System.out.print(json);
	}
	
	
}

