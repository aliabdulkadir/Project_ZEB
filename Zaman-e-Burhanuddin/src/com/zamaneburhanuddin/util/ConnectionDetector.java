package com.zamaneburhanuddin.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
 *  @author : Ali Abdulkadir
 *  Class to detect network availability.
 *  Checking for internet availability.
 */

public class ConnectionDetector {
	
	private Context context;
	
	public ConnectionDetector(Context context){
		this.context = context;
	}
	
	public boolean isConnectingToIneternet(){
		ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(conManager != null){
			NetworkInfo[] netInfo = conManager.getAllNetworkInfo();
			if(netInfo != null){
				for(int i=0; i<netInfo.length; i++){
					if(netInfo[i].getState() == NetworkInfo.State.CONNECTED){
						return true;
					}
				}
			}
		}
		return false;
	}
}
