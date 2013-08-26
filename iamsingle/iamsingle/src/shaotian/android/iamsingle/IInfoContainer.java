package shaotian.android.iamsingle;

import shaotian.android.iamsingle.netsdk.model.IModel;
import shaotian.android.iamsingle.netsdk.model.UserInfo;
import shaotian.android.iamsingle.wssdk.WSFactory;

public interface IInfoContainer {
	WSFactory.Methods getWSMethodType();

	void updateControls(IModel ui);
}
