package shaotian.android.iamsingle;

import shaotian.android.iamsingle.netsdk.WSFactory;
import shaotian.android.iamsingle.netsdk.model.IModel;
import shaotian.android.iamsingle.netsdk.model.UserInfo;

public interface IInfoContainer {
	WSFactory.Methods getWSMethodType();

	void updateControls(IModel ui);
}
