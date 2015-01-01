package com.ihsinformatics.ponsetti.database.data_access;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import com.ihsinformatics.ponsetti.database.DatabaseHandler;
import com.ihsinformatics.ponsetti.database.pojos.Question;

public class QuestionDAO {
	Context context;
	
	public QuestionDAO(Context context) {
		this.context = context;
	}
	
	public void insert(Question question) {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		 
	    ContentValues values = new ContentValues();
	    values.put(Question.COLUMN_TEXT, question.getText());
	    values.put(Question.COLUMN_TYPE_ID, question.getTypeId());
	    
	    db.insert(DatabaseHandler.TABLE_USERS, null, values);
	}
	
	public void insert(List<Question> questions, SQLiteDatabase db) {
		String query = "insert into " + DatabaseHandler.TABLE_QUESTIONS + " ("+Question.COLUMN_QUESTION_ID+", "+Question.COLUMN_TEXT+", "+Question.COLUMN_TYPE_ID+", "+Question.COLUMN_FIELD_NAME+") values";
		Question q;
		for (int i = 0; i < questions.size(); i++) {
			q = questions.get(i);
			if (i < questions.size() - 1) {
				query += "(" + q.getQuestionId() + ", '"  + q.getText() + "', '" + q.getTypeId() +"', '" + q.getFieldName()+"'),";
			} else {
				query += "(" + q.getQuestionId() + ", '" + q.getText() + "', '" + q.getTypeId() +"', '" + q.getFieldName()+"')";
			}
		}
		
		query += ";";
		
		db.execSQL(query);
	}
	
	public Question getQuestions(int questionId) {
		String query = "select * from "+DatabaseHandler.TABLE_QUESTIONS +" where "+Question.COLUMN_QUESTION_ID+"="+questionId; 
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.rawQuery(query, null);
		Question question = null;
		if(c.getCount()>0) {
			c.moveToFirst();
			question = new Question(c.getInt(0), c.getString(2), c.getString(1), c.getString(3));
			
		}
			
		return question;
		
	
	}
}
