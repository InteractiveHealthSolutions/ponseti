package com.ihsinformatics.ponsetti.database;

import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.Hospital;
import com.ihsinformatics.ponsetti.database.pojos.User;

public class EncryptedDatabaseHandler extends SQLiteOpenHelper implements DatabaseErrorHandler {

	// Database Version
    private static final int DATABASE_VERSION = 3;
 
    // Database Name
    private static final String DATABASE_NAME = "ponseti";
 
    // Table names
    public static final String TABLE_HOSPITALS = "hospitals";
    public static final String TABLE_FORMS = "forms";
    public static final String TABLE_USERS = "users";
 
    /*CREATE TABLE IF NOT EXISTS identifiers (
            col1 TEXT NOT NULL COLLATE NOCASE, 
            col2 TEXT NOT NULL COLLATE NOCASE, 
            col3 TEXT NOT NULL UNIQUE COLLATE NOCASE, 
            col4 TEXT COLLATE NOCASE, 
            PRIMARY KEY (col1, col2))*/
    
    
	// Constructors
    public EncryptedDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
    
    @Override
	public void onCreate(net.sqlcipher.database.SQLiteDatabase db) {
		String t0 = "CREATE TABLE IF NOT EXISTS "+EncryptedDatabaseHandler.TABLE_USERS+" ("
	            +User.COLUMN_USERNAME + " TEXT NOT NULL,"
	            +User.COLUMN_PASSWORD + " TEXT NOT NULL,"
	            +User.COLUMN_CLIENT_KEY + " TEXT NOT NULL,"
	            +"PRIMARY KEY ("+User.COLUMN_USERNAME+"))";
		
		String t1 = "CREATE TABLE IF NOT EXISTS "+EncryptedDatabaseHandler.TABLE_HOSPITALS+" ("
	            +Hospital.COLUMN_HOSPITAL_NAME + " TEXT NOT NULL," 
	            +"PRIMARY KEY ("+Hospital.COLUMN_HOSPITAL_NAME+"))";
		
		String t2 = "CREATE TABLE IF NOT EXISTS "+EncryptedDatabaseHandler.TABLE_FORMS+" ("
	            +Form.COLUMN_ICR_ID + " TEXT NOT NULL,"
	            +Form.COLUMN_TIME_STAMP + " TEXT NOT NULL,"
	            // +Form.COLUMN_FORM_NAME + " TEXT,"
	            +"PRIMARY KEY ("+Form.COLUMN_ICR_ID+"))";
		
		db.execSQL(t0);
		db.execSQL(t1);
		db.execSQL(t2);
		
	}

	@Override
	public void onUpgrade(net.sqlcipher.database.SQLiteDatabase db, int oldVersion, int newVersion) {
		String t0 = "drop table if exists "+EncryptedDatabaseHandler.TABLE_USERS;
		String t1 = "drop table if exists "+EncryptedDatabaseHandler.TABLE_HOSPITALS;
		String t2 = "drop table if exists "+EncryptedDatabaseHandler.TABLE_FORMS;
		
		db.execSQL(t0);
		db.execSQL(t1);
		db.execSQL(t2);
		
		onCreate(db);
		
	}
    
	@Override
	public void onCorruption(SQLiteDatabase dbObj) {
		// TODO Auto-generated method stub
		
	}
	
}
