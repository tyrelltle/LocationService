

import static org.junit.Assert.*;

import org.junit.Test;

import shaotian.android.iamsingle.netsdk.util.Helpers;

public class UtilTest {

	public static class testClass{
		public String ma,mb;
		public testClass(String a,String b)
		{
			ma=a;
			mb=b;
		}
		
	}
	@Test
	public void testNewInstanceFromClass() throws Exception {
		Object obj=Helpers.NewInstanceFromClass(testClass.class, new String[]{"aloha","haloa"});
		assertTrue(obj.getClass().equals(testClass.class));
		assertTrue(((testClass)obj).ma.equals("aloha"));
		assertTrue(((testClass)obj).mb.equals("haloa"));

		

	}

}
