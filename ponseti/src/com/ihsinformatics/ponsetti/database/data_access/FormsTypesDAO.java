package com.ihsinformatics.ponsetti.database.data_access;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ihsinformatics.ponsetti.database.DatabaseHandler;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;

public class FormsTypesDAO {
	
	public void insert(Context context, List<FormsTypes> formsTypes) {
		String query = "insert into " + DatabaseHandler.TABLE_FORMS_TYPES + " values";
		
		for (int i = 0; i < formsTypes.size(); i++) {
			if (i < formsTypes.size() - 1) {
				query += "('" + formsTypes.get(i).getTypeId() + "', '"+formsTypes.get(i).getTypeName()+"'),";
			} else {
				query += "('" + formsTypes.get(i).getTypeId() + "', '"+formsTypes.get(i).getTypeName()+"')";
			}
		}
		
		query += ";";
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		db.execSQL(query);
	}
	
	public void insert(Context context, List<FormsTypes> formsTypes, SQLiteDatabase db) {
		String query = "insert into " + DatabaseHandler.TABLE_FORMS_TYPES + " values";
		
		for (int i = 0; i < formsTypes.size(); i++) {
			if (i < formsTypes.size() - 1) {
				query += "('" + formsTypes.get(i).getTypeId() + "', '"+formsTypes.get(i).getTypeName()+"'),";
			} else {
				query += "('" + formsTypes.get(i).getTypeId() + "', '"+formsTypes.get(i).getTypeName()+"')";
			}
		}
		
		query += ";";
		
		db.execSQL(query);
	}
}
