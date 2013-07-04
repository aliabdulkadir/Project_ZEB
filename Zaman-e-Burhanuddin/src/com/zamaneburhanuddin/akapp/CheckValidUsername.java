package com.zamaneburhanuddin.akapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.zamaneburhanuddin.util.AppCommon;
import com.zamaneburhanuddin.util.JSONParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class CheckValidUsername extends AsyncTask<String, String, String>{

	private ProgressDialog pDialog;
	
	JSONParser jsonParser = new JSONParser();
	
	private int responseResult;

	//url to hit server api for checking username password valid or not
	private static String url_authentication = "http://localhost/project_zeb/authentication_request.php";
	
	private static final String TAG_SUCCESS = "success";
	
	private String username = null;
	private String password = null;
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		/*pDialog = new ProgressDialog(AppCommon.getApplicationContext());
		pDialog.setMessage("Loading Please Wait...");
		pDialog.setCancelable(true);
		pDialog.show();*/
	}
	
	@Override
	protected String doInBackground(String... args) {
		//Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		
		//getting JSON Object
		JSONObject jObject = jsonParser.makeHttpRequest(url_authentication, "POST", params);
		
		//Log.d("Check Credentials :", jObject.toString());
		
		//Check for Success
		int success;
		try {
			success = jObject.getInt(TAG_SUCCESS);
			setResponseResult(success);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		//pDialog.dismiss();
	}

	public void setUsernamePassword(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getResponseResult() {
		return responseResult;
	}

	public void setResponseResult(int responseResult) {
		this.responseResult = responseResult;
	}
}
