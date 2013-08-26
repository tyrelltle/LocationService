package shaotian.android.iamsingle.test;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.android.gms.internal.dm;
import com.google.android.gms.maps.model.Marker;

import shaotian.android.iamsingle.UIShared.MapMarkerManager;
import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.UIShared.TimedMarker;
import shaotian.android.iamsingle.netsdk.util.Helpers;

public class UtilsTest extends TestCase{

	
	
	

	
	public void testReflectCtor2() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {

		Constructor c=Helpers.getClassConstructor(TimedMarker.class, new Class[]{});
		TimedMarker inst=(TimedMarker) c.newInstance();
		assertTrue(inst!=null);
	}

}
