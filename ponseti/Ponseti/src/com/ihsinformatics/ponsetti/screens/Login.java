package com.ihsinformatics.ponsetti.screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.DatabaseHandler;
import com.ihsinformatics.ponsetti.database.data_access.UserDAO;
import com.ihsinformatics.ponsetti.database.pojos.User;
import com.ihsinformatics.ponsetti.network.ServerCommunicationAdapter;
import com.ihsinformatics.ponsetti.network.ServerCommunicator;
import com.ihsinformatics.ponsetti.utils.Global;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.view.NetworkProgressDialog;

public class Login extends Activity implements ServerCommunicationAdapter {
	
	private Button btnSignin;
	private EditText etUsername, etPassword;
	private CheckBox cbRemember;
	private Tools tools;
	private String username;
	private String password;
	private NetworkProgressDialog networkProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_login);
		networkProgressDialog = new NetworkProgressDialog(this, "Verifying credentials, please wait...\nLooking into device records");
		tools = Tools.getInstance();
		DatabaseHandler.getInstance(this);
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		cbRemember = (CheckBox) findViewById(R.id.cbRemember);
		etUsername.setText("ihsdeveloper");
		etPassword.setText("Safevar123");
		
		final String clientKey = PreferenceManager.getDefaultSharedPreferences(Login.this).getString("client_key", null);
		if(clientKey != null) {
			login(clientKey);
			
		} 
		
		btnSignin = (Button) findViewById(R.id.btnSignin);
		btnSignin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(clientKey == null) {
					networkProgressDialog.show();
					new AsyncTask<String, Integer, String>() {
	
						@Override
						protected String doInBackground(String... params) {
							username = tools.getViewText(etUsername);
							password = tools.getViewText(etPassword);
							User user = new UserDAO().getUser(Login.this, username, password);
							if(user!=null) {
								networkProgressDialog.dismiss();
								return user.getClientKey();
							}
							
							return null;
						}
						
						@Override
						protected void onPostExecute(String result) {
							onLocalDBChecked(result);
							
							super.onPostExecute(result);
						}
						
					}.execute();
				}
			}
		});
	}
	
	private void onLocalDBChecked(String result) {
		if(result != null) {
			login(result);
		} else {
			networkProgressDialog.setMessage("Verifying credentials, please wait...\nVerifying from server");
			new ServerCommunicator(Login.this, Login.this, 0, Global.REQUEST_CLIENT_KEY).execute(username, password);
		}
		
	}
	
	private void login(String clientKey) {
		if(cbRemember.isChecked()) {
			PreferenceManager.getDefaultSharedPreferences(this).edit().putString("client_key", clientKey).apply();
		}
		
		Global.CLIENT_KEY = clientKey;
		networkProgressDialog.dismiss();
		startActivity(new Intent(Login.this, MainMenu.class));
		
		finish();
	}
	
	@Override
	public void xmlrpcCallResponse(Object res, int requestId) {
		if(res != null && !res.toString().contains("XMLRPC Fault")) {
			new UserDAO().insert(this, new User(username, password, res.toString()));
			login(res.toString());
			
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
			builder
			.setNegativeButton("Retry", null)
			.setTitle("Login Failed")
			.setMessage("Invalid Username or Password")
			.show();
		}
		
	}
	
	@Override
	public void xmlrpcCallResponse(Object res, String requestId) {
		// TODO Auto-generated method stub
		
	}

}
