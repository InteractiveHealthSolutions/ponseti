package com.ihsinformatics.ponsetti.database.data_access;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ihsinformatics.ponsetti.database.DatabaseHandler;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.Question;

public class FormDAO {

	Context context;
	public FormDAO(Context context) {
		this.context = context;
	}

	public long insert(Form form) {
		
		
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(Form.COLUMN_TYPE_ID, form.getTypeId());
	    if(form.getIcrId() == null){
	    	values.put(Form.COLUMN_ICR_ID, "OFF"+new DecimalFormat("#00000000").format(getNumberOfStoredForms()+1));
		} else {
			values.put(Form.COLUMN_ICR_ID, form.getIcrId());
		}
	    values.put(Form.COLUMN_TIME_STAMP, form.getTimeStamp());
	    
	    return db.insert(DatabaseHandler.TABLE_FORMS, null, values);
	}
	
	public long update(Form form, String icrId) {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(Form.COLUMN_TIME_STAMP, form.getTimeStamp());
	    values.put(Form.COLUMN_ICR_ID, form.getIcrId());
	    
	    return db.update(DatabaseHandler.TABLE_FORMS, values, Form.COLUMN_ICR_ID+" = '"+icrId+"'", null);
	}

	public void insert(List<Form> forms) {
		String query = "insert into " + DatabaseHandler.TABLE_FORMS + " ("+Form.COLUMN_ICR_ID+", "+Form.COLUMN_TIME_STAMP+", "+Form.COLUMN_TYPE_ID+") values";
		
		for (int i = 0; i < forms.size(); i++) {
			if (i < forms.size() - 1) {
				query += "('" + forms.get(i).getIcrId() + "', '" + forms.get(i).getTimeStamp() + "', '" + forms.get(i).getTypeId()+"'),";
			} else {
				query += "('" + forms.get(i).getIcrId() + "', '" + forms.get(i).getTimeStamp() + "', '" + forms.get(i).getTypeId()+"')";
			}
		}
		
		query += ";";
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		db.execSQL(query);
	}
	
	/*public Form getForm(String column, String check) {
		Form form = null;
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.query(DatabaseHandler.TABLE_FORMS, null, column+" = '"+check+"'", null, null, null, null);
		if(c.moveToFirst()) {
			form = new Form(Integer.parseInt(c.getString(0)), c.getString(1), Integer.parseInt(c.getString(2)), c.getString(3));
		}
		c.close();
		return form;
	}*/
	
	public Form getForm(String whereClauseCheck) {
		Form form = null;
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.query(DatabaseHandler.TABLE_FORMS, null, whereClauseCheck, null, null, null, null);
		if(c.moveToFirst()) {
			form =  new Form(Integer.parseInt(c.getString(0)), c.getString(1), Integer.parseInt(c.getString(2)), c.getString(3));
		}
		c.close();
		return form;
	}
	
	public List<Form> getForms(String formType) {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		List<Form> forms = null;
		Cursor c = db.query(DatabaseHandler.TABLE_FORMS, null, Form.COLUMN_TYPE_ID+"= '"+formType+"'", null, null, null, null/*Form.COLUMN_TIME_STAMP+" ASC"*/);
		
		if(c != null) {
			forms = new ArrayList<Form>();
			while (c.moveToNext()) {
				forms.add(new Form(Integer.parseInt(c.getString(0)), c.getString(1), Integer.parseInt(c.getString(2)), c.getString(3) ));		
			}	
		}
		c.close();
		return forms;
	}
	
	public List<Form> getUploadableForms(String formType) {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		List<Form> forms = null;
		Cursor c = db.query(DatabaseHandler.TABLE_FORMS, null, Form.COLUMN_TYPE_ID+"= '"+formType+"' and "+Form.COLUMN_ICR_ID+" like 'OFF%'", null, null, null, null/*Form.COLUMN_TIME_STAMP+" ASC"*/);
		
		if(c != null) {
			forms = new ArrayList<Form>();
			while (c.moveToNext()) {
				forms.add(new Form(Integer.parseInt(c.getString(0)), c.getString(1), Integer.parseInt(c.getString(2)), c.getString(3) ));		
			}	
		}
		c.close();
		return forms;
	}
	
	
	public int getNumberOfStoredForms() {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		int i = 0;
		Cursor c = db.rawQuery("select count(*) from "+DatabaseHandler.TABLE_FORMS, null);
		if (c != null) {
			c.moveToFirst();
			i = Integer.parseInt(c.getString(0));
			c.close();
		}
		
		return i;
	}
	
	public int getNumberOfUploadableForms() {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		int i = 0;
		Cursor c = db.rawQuery("select count(*) from "+DatabaseHandler.TABLE_FORMS+" where "+Form.COLUMN_ICR_ID+" like 'OFF%'", null);
		if (c != null) {
			c.moveToFirst();
			i = Integer.parseInt(c.getString(0));
			c.close();
		}
		
		return i;
	}
}
