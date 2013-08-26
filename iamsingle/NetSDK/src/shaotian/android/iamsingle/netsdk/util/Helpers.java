package shaotian.android.iamsingle.netsdk.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Helpers {
	public static Constructor getClassConstructor(Class T,Class[] parameters)
	{
		Constructor [] ctors=T.getConstructors();
		for(Constructor c:ctors)
		{
			Class [] ts=c.getParameterTypes();
			if(ts.length==parameters.length)
			{
				for(int i =0;i<ts.length;i++)
					if(!ts[i].equals(parameters[i]))
							return null;
				return c;
			}
		}
		return null;
	}
	
	public static Object NewInstanceFromClass(Class T,Object[] parameters) throws Exception
	{
		Class [] types=new Class[parameters.length];
		for(int i=0;i<parameters.length;i++)
		{
			types[i]=parameters[i].getClass();
			
		}
		
		boolean find=true;
		Constructor [] ctors=T.getConstructors();
		for(Constructor c:ctors)
		{
			Class [] ts=c.getParameterTypes();
			if(ts.length==types.length)
			{
				for(int i =0;i<ts.length;i++)
					if(!ts[i].equals(types[i]))
					{	find=false; break;}
				if(find)
					return c.newInstance(parameters);
			}
		}
		return null;
	}
}
