package com.ihsinformatics.ponsetti.database;

import android.content.Context;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.database.pojos.Hospital;
import com.ihsinformatics.ponsetti.database.pojos.Photo;
import com.ihsinformatics.ponsetti.database.pojos.Question;
import com.ihsinformatics.ponsetti.database.pojos.User;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static DatabaseHandler databaseHandler;
	Context context;
	
	// Database Version
    private static final int DATABASE_VERSION = 2;
 
    // Database Name
    private static final String DATABASE_NAME = "ponseti";
 
    // Table names
    public static final String TABLE_HOSPITALS = "hospitals";
    public static final String TABLE_FORMS_TYPES = "forms_types";
    public static final String TABLE_FORMS = "forms";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_QUESTIONS = "questions";
    public static final String TABLE_ANSWERS = "answers";
    public static final String TABLE_PHOTOS = "photos";
    
	// Constructor
    private DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
    
    public static DatabaseHandler getInstance(Context context) {
    	if(databaseHandler == null) {
    		databaseHandler = new DatabaseHandler(context);
    	}
    	
    	return databaseHandler;
    }
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String t0 = "CREATE TABLE IF NOT EXISTS "+DatabaseHandler.TABLE_USERS+" ("
	            
				+User.COLUMN_USERNAME + " TEXT NOT NULL,"
	            +User.COLUMN_PASSWORD + " TEXT NOT NULL,"
	            +User.COLUMN_CLIENT_KEY + " TEXT NOT NULL,"
	            
	            + " PRIMARY KEY ("+User.COLUMN_USERNAME+"))";
		
		String t1 = "CREATE TABLE IF NOT EXISTS "+DatabaseHandler.TABLE_HOSPITALS+" ("
				
				+Hospital.COLUMN_HOSPITAL_ID + " INTEGER PRIMARY KEY NOT NULL,"
				+Hospital.COLUMN_HOSPITAL_NAME + " TEXT NOT NULL)";
		
		String t2 = "CREATE TABLE IF NOT EXISTS "+DatabaseHandler.TABLE_FORMS_TYPES+" ("
				
				+FormsTypes.COLUMN_TYPE_ID + " TEXT NOT NULL,"
				+FormsTypes.COLUMN_TYPE_NAME + " TEXT NOT NULL," 
	            
				+ " PRIMARY KEY ("+FormsTypes.COLUMN_TYPE_ID+"))";
		
		String t3 = "CREATE TABLE IF NOT EXISTS "+DatabaseHandler.TABLE_FORMS+" ("
	            
				+Form.COLUMN_FORM_ID + " INTEGER PRIMARY KEY NOT NULL,"
				+Form.COLUMN_ICR_ID + " TEXT UNIQUE NULL," 
	            +Form.COLUMN_TIME_STAMP + " INTEGER NULL,"
	            +Form.COLUMN_TYPE_ID + " TEXT NOT NULL,"

	            + " FOREIGN KEY ("+Form.COLUMN_TYPE_ID+") REFERENCES "+DatabaseHandler.TABLE_FORMS_TYPES+" ("+FormsTypes.COLUMN_TYPE_ID+"));";
		
		
		String t4 = "CREATE TABLE IF NOT EXISTS "+DatabaseHandler.TABLE_QUESTIONS+" ("
				
				+Question.COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY NOT NULL," 
	            +Question.COLUMN_TEXT + " TEXT NOT NULL,"
	            +Question.COLUMN_TYPE_ID + " TEXT NOT NULL,"
	            +Question.COLUMN_FIELD_NAME + " TEXT NOT NULL,"

	            + " FOREIGN KEY ("+Question.COLUMN_TYPE_ID+") REFERENCES "+DatabaseHandler.TABLE_FORMS_TYPES+" ("+FormsTypes.COLUMN_TYPE_ID+"));";
		
		String t5 = "CREATE TABLE IF NOT EXISTS "+DatabaseHandler.TABLE_ANSWERS+" ("
				
				+Answer.COLUMN_ANSWER_ID + " INTEGER PRIMARY KEY NOT NULL," 
	            +Answer.COLUMN_TEXT + " TEXT DEFAULT 'null',"
	            +Answer.COLUMN_QUESTION_ID + " INTEGER NOT NULL,"
	            +Answer.COLUMN_FORM_ID + " INTEGER NOT NULL,"

	            + " FOREIGN KEY ("+Answer.COLUMN_QUESTION_ID+") REFERENCES "+DatabaseHandler.TABLE_QUESTIONS+" ("+Question.COLUMN_QUESTION_ID+"), "
	            + " FOREIGN KEY ("+Answer.COLUMN_FORM_ID+") REFERENCES "+DatabaseHandler.TABLE_FORMS+" ("+Form.COLUMN_FORM_ID+"));";
		
		String t6 = "CREATE TABLE IF NOT EXISTS "+DatabaseHandler.TABLE_PHOTOS+" ("
				
				+Photo.COLUMN_PHOTO_ID + " INTEGER PRIMARY KEY NOT NULL,"
	            +Photo.COLUMN_CONTENT + " TEXT NOT NULL,"
	            +Photo.COLUMN_IS_UPLOADED + " INTEGER NOT NULL,"
	    	    +Photo.COLUMN_PARENT_NODE + " TEXT NOT NULL,"

	            + " FOREIGN KEY ("+Photo.COLUMN_PARENT_NODE+") REFERENCES "+DatabaseHandler.TABLE_FORMS+" ("+Form.COLUMN_ICR_ID+"));";
		
		db.execSQL(t0);
		db.execSQL(t1);
		db.execSQL(t2);
		db.execSQL(t3);
		db.execSQL(t4);
		db.execSQL(t5);
		db.execSQL(t6);
		
		new DefaultData().inserDefaultData(context, db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String t0 = "drop table if exists "+DatabaseHandler.TABLE_USERS;
		String t1 = "drop table if exists "+DatabaseHandler.TABLE_HOSPITALS;
		String t2 = "drop table if exists "+DatabaseHandler.TABLE_FORMS_TYPES;
		String t3 = "drop table if exists "+DatabaseHandler.TABLE_FORMS;
		String t4 = "drop table if exists "+DatabaseHandler.TABLE_QUESTIONS;
		String t5 = "drop table if exists "+DatabaseHandler.TABLE_ANSWERS;
		String t6 = "drop table if exists "+DatabaseHandler.TABLE_PHOTOS;
		
		db.execSQL(t0);
		db.execSQL(t1);
		db.execSQL(t2);
		db.execSQL(t3);
		db.execSQL(t4);
		db.execSQL(t5);
		db.execSQL(t6);
		
		onCreate(db);
	}
	
	public void closeDb() {
		getWritableDatabase().close();
	}
	
	public SQLiteDatabase getWritableDatabase() {
		SQLiteDatabase.loadLibs(context);
		return getWritableDatabase("ihs123");
	}
}
