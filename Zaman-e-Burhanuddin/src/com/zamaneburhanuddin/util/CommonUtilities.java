package com.zamaneburhanuddin.util;

import android.content.Context;
import android.content.Intent;

/*
 * @author : Ali Abdulkadir
 * Common Class used within application context.
 */
public final class CommonUtilities {

	//Server Registration URL
	public static final String SERVER_URL = "http://localhost/project_zeb_push_notification/register.php";
	
	//Google Project ID
	public static final String SENDER_ID = "900896998107";
	
	//TAG for logs
	public static final String TAG = "Zaman-e-burhanuddin:GCM";
	
	public static final String DISPLAY_MESSAGE_ACTION = "com.zamaneburhanuddin.util.DISPLAY_MESSAGE";
	
	public static final String EXTRA_MESSAGE = "message";
	
	//Notify UI to display message
	public static void displayMessage(Context context, String message){
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}
