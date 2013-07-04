package com.zamaneburhanuddin.util;

import android.content.Context;

public class AppCommon {

	private static Context ApplicationContext = null;

	public static Context getApplicationContext() {
		return ApplicationContext;
	}

	public static void setApplicationContext(Context applicationContext) {
		ApplicationContext = applicationContext;
	}
	
}
