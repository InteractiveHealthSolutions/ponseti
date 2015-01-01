package com.ihsinformatics.ponsetti.database.pojos;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -1003145854002833455L;
	
	public static String COLUMN_USERNAME = "username";
	public static String COLUMN_PASSWORD = "password";
	public static String COLUMN_CLIENT_KEY = "client_key";
	
	private String username;
	private String password;
	private String clientKey;

	public User() {
		
	}

	public User(String username, String password, String clientKey) {
		super();
		this.username = username;
		this.password = password;
		this.clientKey = clientKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

}
