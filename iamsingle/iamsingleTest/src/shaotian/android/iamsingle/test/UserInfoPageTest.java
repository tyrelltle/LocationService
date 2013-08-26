package shaotian.android.iamsingle.test;

import org.junit.Test;

import shaotian.android.iamsingle.IInfoContainer;
import shaotian.android.iamsingle.async.AsyncInfoCtrUpdate;

import shaotian.android.iamsingle.netsdk.model.IModel;
import shaotian.android.iamsingle.netsdk.model.UserInfo;
import shaotian.android.iamsingle.wssdk.WSFactory;
import shaotian.android.iamsingle.wssdk.WSFactory.Methods;

import junit.framework.TestCase;

public class UserInfoPageTest extends TestCase {

	private class InfoContainerStub implements IInfoContainer{
		public String userinfo="";
		@Override
		public Methods getWSMethodType() {
			// TODO Auto-generated method stub
			return WSFactory.Methods.LogonUser;
		}

		@Override
		public void updateControls(IModel ui) {
			this.userinfo=((UserInfo)ui).hobby;
		}


		
	}
 
	public void testInfoContainer_Test()
	{
		IInfoContainer i=new InfoContainerStub();
		assertEquals(WSFactory.Methods.LogonUser,i.getWSMethodType());
		UserInfo ui=new UserInfo();
		ui.hobby="test hobby";
				
				
		i.updateControls(ui);
		assertEquals("test hobby", ((InfoContainerStub)i).userinfo);
	}
	
	

}
