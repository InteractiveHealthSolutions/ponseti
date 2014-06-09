package com.ihsinformatics.ponsetti.database.data_access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ihsinformatics.ponsetti.database.DatabaseHandler;
import com.ihsinformatics.ponsetti.database.pojos.User;

public class UserDAO {

	public UserDAO() {

	}

	public void insert(Context context, User user) {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(User.COLUMN_USERNAME, user.getUsername());
	    values.put(User.COLUMN_PASSWORD, user.getPassword());
	    values.put(User.COLUMN_CLIENT_KEY, user.getClientKey());
	    
	    db.insert(DatabaseHandler.TABLE_USERS, null, values);
	}

	public void insert(User... users) {
		// ...statements...
	}

	public User getUser(Context context, String username, String password) {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.query(DatabaseHandler.TABLE_USERS, null, User.COLUMN_USERNAME+" = '"+username+"' and "+User.COLUMN_PASSWORD+" = '"+password+"'", null, null, null, null);
		if (c.moveToFirst()) {
			return new User(c.getString(0), c.getString(1), c.getString(2));
		}
		
		return null;
	}

}
