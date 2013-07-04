package com.zamaneburhanuddin.util;

import com.zamaneburhanuddin.akapp.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class AlertDialogManager {

	/*
	 * @author : Ali Abdulkadir
	 * Common class to display alert messages.
	 */
	
	public void showAlertDialog(Context context, String title, String message, Boolean status){
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		
		if(status != null){
			alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
		}
		
		alertDialog.setButton("Ok", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		
		//Show Alert Dialog
		alertDialog.show();
	}
}
