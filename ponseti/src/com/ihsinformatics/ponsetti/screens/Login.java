package com.ihsinformatics.ponsetti.screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidxmlrpc.XMLRPCClient;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.DatabaseHandler;
import com.ihsinformatics.ponsetti.database.data_access.UserDAO;
import com.ihsinformatics.ponsetti.database.pojos.User;
import com.ihsinformatics.ponsetti.network.ServerCommunicator;
import com.ihsinformatics.ponsetti.network.ServerCommunicationAdapter;
import com.ihsinformatics.ponsetti.utils.Global;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.view.NetworkProgressDialog;

public class Login extends Activity implements ServerCommunicationAdapter {
	
	Button btnSignin;
	EditText etUsername, etPassword;
	XMLRPCClient client;
	
	Tools tools;
	String username;
	String password;
	
	NetworkProgressDialog networkProgressDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_login);
		networkProgressDialog = new NetworkProgressDialog(this, "Verifying credentials, please wait...");
		tools = Tools.getInstance();
		DatabaseHandler.getInstance(this);
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		
		/*etUsername.setText("ihsdeveloper");
		etPassword.setText("Safevar123");*/
		client = new XMLRPCClient(Global.HTTPS_URL, this);
		
		btnSignin = (Button) findViewById(R.id.btnSignin);
		btnSignin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				networkProgressDialog.show();
				username = tools.getViewText(etUsername);
				password = tools.getViewText(etPassword);
				User user = new UserDAO().getUser(Login.this, username, password);
				if(user!=null) {
					networkProgressDialog.dismiss();
					login(user.getClientKey());
				} else {
					new ServerCommunicator(Login.this, Login.this, 0, Global.REQUEST_CLIENT_KEY).execute(username, password);
				}
			}
		});
	}

	

	@Override
	public void xmlrpcCallResponse(Object res, int requestId) {
		networkProgressDialog.dismiss();
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
	
	private void login(String clientKey) {
		Global.CLIENT_KEY = clientKey;
		startActivity(new Intent(Login.this, MainMenu.class));
		finish();
	}



	@Override
	public void xmlrpcCallResponse(Object res, String requestId) {
		// TODO Auto-generated method stub
		
	}

}
