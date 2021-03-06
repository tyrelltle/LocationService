package shaotian.android.iamsingle.netsdk.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;

public class WSUtil {

	public static String getContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException
	{
		InputStream in=entity.getContent();
		StringBuffer out=new StringBuffer();
		int n=1;
		while(n>0)
		{
			byte[] b=new byte[4096];
			n=in.read(b);
			if(n>0)
				out.append(new String(b,0,n));
			
		}
		
		
		return out.toString();
		
		
		
	}
	
	public static byte[] getBinaryFromEntity(HttpEntity entity) throws IllegalStateException, IOException
	{
		byte[] arr=new byte[32768];
		ByteArrayOutputStream buffer=new ByteArrayOutputStream();
		InputStream in=entity.getContent();
		int numRead=0;
		while((numRead=in.read(arr, 0, arr.length))!=-1){
			
			buffer.write(arr,0, numRead);
				
		}
		buffer.flush();
		
		return buffer.toByteArray();
		
	}
}
