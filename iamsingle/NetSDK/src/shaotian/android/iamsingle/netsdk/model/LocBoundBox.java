package shaotian.android.iamsingle.netsdk.model;



public class LocBoundBox implements IModel{
	public double northEast_lat,
		   northEast_lng,
		   southWest_lat,
		   southWest_lng=0;
	
	public LocBoundBox(double a,double b,double c,double d)
	{
		northEast_lat=a;
		northEast_lng=b;
		southWest_lat=c;
		southWest_lng=d;
	}
}
