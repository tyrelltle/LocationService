package shaotian.android.iamsingle.UIShared;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class CustomDialogFragment extends DialogFragment{

	private Dialog mDialog;
	
	public CustomDialogFragment (){
		super();
		mDialog=null;
		
	}
	
	public void setDialog(Dialog dialog)
	{
		mDialog=dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		return mDialog;
	}

	
}
