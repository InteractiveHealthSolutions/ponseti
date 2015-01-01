package com.ihsinformatics.ponsetti.database.data_access;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import com.ihsinformatics.ponsetti.database.DatabaseHandler;
import com.ihsinformatics.ponsetti.database.pojos.Hospital;

public class HospitalDAO {

	public HospitalDAO() {
		
	}

	public void insert(Context context, String hospital) {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(Hospital.COLUMN_HOSPITAL_NAME, hospital);
	    
	    db.insert(DatabaseHandler.TABLE_HOSPITALS, null, values);
	}

	public void insert(Context context, List<Hospital> hospitals) {
		String query = "insert into " + DatabaseHandler.TABLE_HOSPITALS + " ("+Hospital.COLUMN_HOSPITAL_NAME+") values";
		for (int i = 0; i < hospitals.size(); i++) {
			if (i < hospitals.size() - 1) {
				query += "('" + hospitals.get(i).getHospitalName() + "'),";
			} else {
				query += "('" + hospitals.get(i).getHospitalName() + "')";
			}
			
		}
		
		query += ";";
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		db.execSQL(query);
	}
	
	public Object[] getHospitals(Context context) {
		
		List<Object> hospitals = new ArrayList<Object>();
		Object[] hospitalsArray = null;
		Cursor c = DatabaseHandler.getInstance(context).getWritableDatabase().query(DatabaseHandler.TABLE_HOSPITALS, null, null, null, null, null, null);
		if(c != null) {
			
			while (c.moveToNext()) {
				hospitals.add(new Hospital(c.getString(0)));		
			}
			
			hospitalsArray = hospitals.toArray();
		}
		c.close();
		return hospitalsArray;
	}

	public List<String> getHospitalsNames(Context context) {
		List<String> hospitalsNames = new ArrayList<String>();
		Cursor c = DatabaseHandler.getInstance(context).getWritableDatabase().query(DatabaseHandler.TABLE_HOSPITALS, null, null, null, null, null, null);
		if(c != null) {
			while (c.moveToNext()) {
				hospitalsNames.add(c.getString(1));		
			}
			
			
		}
		c.close();
		return hospitalsNames;
	}
}
