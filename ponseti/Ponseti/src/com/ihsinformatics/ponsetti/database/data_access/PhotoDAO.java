package com.ihsinformatics.ponsetti.database.data_access;

import java.util.ArrayList;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;

import com.ihsinformatics.ponsetti.database.DatabaseHandler;
import com.ihsinformatics.ponsetti.database.pojos.Photo;

public class PhotoDAO {

	Context context;
	public PhotoDAO(Context context) {
		this.context = context;
	}

	public long insert(Photo photo) {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		
	    ContentValues values = new ContentValues();
	    values.put(Photo.COLUMN_CONTENT, photo.getContent());
	    values.put(Photo.COLUMN_IS_UPLOADED, photo.getUploaded());
	    values.put(Photo.COLUMN_PARENT_NODE, photo.getParentNode());
		
	    return db.insert(DatabaseHandler.TABLE_PHOTOS, null, values);
	}
		
	public long update(Photo photo, String icrId) {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		
	    ContentValues values = new ContentValues();
	    values.put(Photo.COLUMN_PARENT_NODE, photo.getParentNode());
	    values.put(Photo.COLUMN_IS_UPLOADED, photo.getUploaded());
	    
	    return db.update(DatabaseHandler.TABLE_PHOTOS, values, Photo.COLUMN_PARENT_NODE+" = '"+icrId+"'", null);
	}
	
	public ArrayList<Photo> getPhotos(String whereClauseCheck) {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.query(DatabaseHandler.TABLE_PHOTOS, null, whereClauseCheck, null, null, null, null);
		if(c!=null) {
			for(int i=0; i<c.getCount(); i++) {
				c.moveToPosition(i);
				photos.add(new Photo(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3)));
			}
			
			c.close();
		}
		
		return photos;
	}
	
	public ArrayList<Photo> getReadyToUploadPhotos() {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.query(DatabaseHandler.TABLE_PHOTOS, null, Photo.COLUMN_IS_UPLOADED+" = 0 and "+Photo.COLUMN_PARENT_NODE+" not like 'OFF%'", null, null, null, null);
		c.moveToFirst();
		for(int i=0; i<c.getCount(); i++) {
			c.moveToPosition(i);
			photos.add(new Photo(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3)));
		}
		c.close();
		
		return photos;
	}
	
	public ArrayList<Photo> getAllUnUploadedPhotos() {
		ArrayList<Photo> photos = new ArrayList<Photo>();
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.query(DatabaseHandler.TABLE_PHOTOS, null, Photo.COLUMN_IS_UPLOADED+" = 0", null, null, null, null);
		c.moveToFirst();
		for(int i=0; i<c.getCount(); i++) {
			c.moveToPosition(i);
			photos.add(new Photo(c.getInt(0), c.getString(1), c.getInt(2), c.getString(3)));
		}
		c.close();
		
		return photos;
	}
	
}
