package com.zamaneburhanuddin.akapp;

import com.zamaneburhanuddin.util.AppCommon;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText etUsername = null;
	private EditText etPassword = null;
	private Button bSignIn = null;
	private String username = null;
	private String password = null;
	CheckValidUsername objCheck = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		AppCommon.setApplicationContext(getApplicationContext());
		
		objCheck = new CheckValidUsername();
		
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		bSignIn = (Button) findViewById(R.id.bSignIn);
		
		bSignIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				username = etUsername.getText().toString();
				password = etPassword.getText().toString();
				objCheck.setUsernamePassword(username,password);
				objCheck.execute();
				
				if(objCheck.getResponseResult() == 1){
					Intent intent = new Intent(LoginActivity.this, DummyActivity.class);
					startActivity(intent);
				}
				else{
					Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
