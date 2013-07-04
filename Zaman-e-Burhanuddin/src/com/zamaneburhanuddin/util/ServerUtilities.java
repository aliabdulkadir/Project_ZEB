package com.zamaneburhanuddin.util;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.zamaneburhanuddin.akapp.R;

import static com.zamaneburhanuddin.util.CommonUtilities.TAG;
import static com.zamaneburhanuddin.util.CommonUtilities.SERVER_URL;
import static com.zamaneburhanuddin.util.CommonUtilities.displayMessage;
import android.content.Context;
import android.util.Log;

/*
 * @author : Ali Abdulkadir
 * Register Device with Server
 */
public final class ServerUtilities {
	private static final int MAX_ATTEMPTS = 5;
	private static final int BACKOFF_MILLI_SECONDS = 2000;
	private static final Random random = new Random();
	
	public static void register(final Context context, String name, String itsNo, String email, String contactNo, final String regId){
		Log.i(TAG,"Registering (regId : "+regId+")");
		String serverUrl = SERVER_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("itsNo", itsNo);
		params.put("email", email);
		params.put("contactNo", contactNo);
		params.put("regId", regId);
		
		long backOff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
		
		for(int i=0; i<MAX_ATTEMPTS; i++){
			Log.i(TAG,"Attempt # " +i+" to Register");
			try{
				displayMessage(context, context.getString(R.string.server_registering, i, MAX_ATTEMPTS));
				post(serverUrl, params);
				GCMRegistrar.setRegisteredOnServer(context,true);
				String message = context.getString(R.string.server_registered);
				CommonUtilities.displayMessage(context, message);
				return;
			}
			catch(IOException e){
				Log.e(TAG, "Failed to Register on Attempt # "+ i + " : "+e);
				if(i==MAX_ATTEMPTS)
					break;
				try{
					Log.d(TAG, "Sleeping for " + backOff + " ms before retry");
                    Thread.sleep(backOff);
				}catch(InterruptedException e1){
					 Log.d(TAG, "Thread interrupted: abort remaining retries!");
	                    Thread.currentThread().interrupt();
	                    return;
				}
				backOff *= 2;
			}
		}
		String message = context.getString(R.string.server_register_error, MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);
	}

	//Unregister device from server
	public static void unRegister(final Context context, final String regId) {
        Log.i(TAG, "unregistering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        try {
            post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
            CommonUtilities.displayMessage(context, message);
        } catch (IOException e) {
            // At this point the device is unregistered from GCM, but still
            // registered in the server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
            String message = context.getString(R.string.server_unregister_error, e.getMessage());
            CommonUtilities.displayMessage(context, message);
        }
    }
	
	/**
     * Issue a POST request to the server.
     */
    private static void post(String endpoint, Map<String, String> params) throws IOException {    
         
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
              throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
      }
}
