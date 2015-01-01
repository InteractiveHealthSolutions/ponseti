package com.ihsinformatics.ponsetti.database.data_access;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

import com.ihsinformatics.ponsetti.database.DatabaseHandler;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.database.pojos.Question;
import com.ihsinformatics.ponsetti.screens.CreateEvaluator;
import com.ihsinformatics.ponsetti.screens.CreatePatient;

public class AnswerDAO {

	private Context context;
	
	public AnswerDAO(Context context) {
		this.context = context;
	}
	
	public void insert(List<Answer> answers) {
		
		String query = "insert into " + DatabaseHandler.TABLE_ANSWERS + " ("+Answer.COLUMN_TEXT+", "+Answer.COLUMN_QUESTION_ID+", "+Answer.COLUMN_FORM_ID+") values";
		for (int i = 0; i < answers.size(); i++) {
			if (i < answers.size() - 1) {
				query += "('" + answers.get(i).getText() + "', '" + answers.get(i).getQuestion_id() + "', " + answers.get(i).getFormId() + "),";
			} else {
				query += "('" + answers.get(i).getText() + "', '" + answers.get(i).getQuestion_id() + "', " + answers.get(i).getFormId() + ")";
			}
			
		}
		
		query += ";";
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		
		db.execSQL(query);
	}
	
	public void update(List<Answer> answers) {
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		for(Answer answer: answers) {
		    ContentValues values = new ContentValues();
		    values.put(Answer.COLUMN_TEXT, answer.getText());
		    
		    db.update(DatabaseHandler.TABLE_ANSWERS, values, Answer.COLUMN_QUESTION_ID+" = "+answer.getQuestion_id()+" and "+Answer.COLUMN_FORM_ID+" = "+answer.getFormId(), null);
		}
	}
	
	public List<Answer> getAnswers(String whereClause, String orderBy) {
		List<Answer> answers = new ArrayList<Answer>();
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.query(DatabaseHandler.TABLE_ANSWERS, null, whereClause, null, null, null, orderBy);
		try {
			if(c.getCount() > 0) {
				while(c.moveToNext()){
					answers.add( new Answer(c.getInt(0), c.getString(1), Integer.parseInt(c.getString(2)), Integer.parseInt(c.getString(3))));
				}	
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			c.close();
		}
		
		return answers;
	}
	
	
	
	public Answer getAnswer(int formId, int questionId) {
		Answer answer = null;
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.query(DatabaseHandler.TABLE_ANSWERS, null, Answer.COLUMN_QUESTION_ID+"= "+questionId+" and "+Answer.COLUMN_FORM_ID+"="+formId, null, null, null, null);
		try {
			if(c.getCount() > 0) {
				c.moveToFirst();
				answer =  new Answer(c.getInt(0), c.getString(1), Integer.parseInt(c.getString(2)), Integer.parseInt(c.getString(3)) );	
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		} finally {
			c.close();
		}
		
		return answer;
	}
	
	public List<String[]> getAnswers(String formType) {
		String[] row;
		List<String[]> data = new ArrayList<String[]>();
		String query = "select f."+Form.COLUMN_ICR_ID+", q."+Question.COLUMN_QUESTION_ID+", q."+Question.COLUMN_TEXT+", a."+Answer.COLUMN_TEXT            
					+ " from "+DatabaseHandler.TABLE_ANSWERS+" a inner join "
					+ DatabaseHandler.TABLE_FORMS +" f on a."
					+ Form.COLUMN_FORM_ID+" = f."+Answer.COLUMN_FORM_ID+" inner join "
					+ DatabaseHandler.TABLE_QUESTIONS +" q on q."
					+ Question.COLUMN_QUESTION_ID+" = a."+Answer.COLUMN_QUESTION_ID
					+ " where f."+Form.COLUMN_TYPE_ID +" = '"+formType+"' order by f."+Form.COLUMN_ICR_ID+",q."+Question.COLUMN_QUESTION_ID+" ASC";
		
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.rawQuery(query, null);
		try {
			if (c.getCount() > 0) {

				while (c.moveToNext()) {

					row = new String[4];
					row[0] = c.getString(0);
					row[1] = c.getString(2);
					row[2] = c.getString(3);
					row[3] = c.getString(1);
					data.add(row);
				}

			}
		} catch (Exception e) {
			data = null;
			e.printStackTrace();
		} finally {
			c.close();
		}
		
		return processData(data, formType);
		
	}
	
	private List<String[]> processData(List<String[]> data, String formType) {
		List<String[]> toReturn = new ArrayList<String[]>();
		String query = "select count(*) from "+DatabaseHandler.TABLE_QUESTIONS+" where "+Question.COLUMN_TYPE_ID+" = '"+formType+"'";
		SQLiteDatabase db = DatabaseHandler.getInstance(context).getWritableDatabase();
		Cursor c = db.rawQuery(query, null);
		c.moveToFirst();
		
		boolean headersConsumed = false;
		int length = c.getInt(0);
		// int numberOfRecords = data.size()/length;
		
		c.close();
		
		String[] row = new String[length+1];
		String[] header = new String[length+1];
		header[0] = "ICR ID";
		int i=1;
		boolean isFirst = true;
		for(String[] s:data) {
			if(isFirst) {
				row[0] = s[0];
			}
			if(i<=length) {
				if(!headersConsumed) {
					header[i] = (s[1]);
					row[i] = s[2];
				} else {
					row[i] = s[2];
				}
				isFirst = false;
			} else {
				i = 1;
				isFirst = true;
				if(!headersConsumed) {
					toReturn.add(header);
				}
				
				toReturn.add(row);
				row = new String[length+1];
				row[i] = s[2];
				
				headersConsumed = true;
			}
			
			i++;
		}
		
		toReturn.add(row);
		return toReturn;
	}
	
	public List<String> getEvaluatorsNames(List<Form> forms) {
		List <String> evaluatorNames = new ArrayList<String>();
		for(Form form: forms) {
			evaluatorNames.add(getEvaluatorName(form.getFormId()));
		}
		
		return evaluatorNames;
	}
	
	public String getEvaluatorName(int formId) {
		String name = "";
		Answer firstName, middleName, lastName;
		
		firstName =  getAnswer(formId, CreateEvaluator.QUESTION_FIRST_NAME);
		middleName = getAnswer(formId, CreateEvaluator.QUESTION_MIDDLE_NAME);
		lastName = getAnswer(formId, CreateEvaluator.QUESTION_LAST_NAME);
		
		if(firstName != null) {
			name += firstName.getText();
		}
		if(middleName != null) {
			name += " "+middleName.getText();		
		}
		if(lastName != null) {
			name += " "+lastName.getText();	
		}
		
		return name.replace("null", "");
	}
	
	public List<String> getNames(List<Form> forms) {
		List <String> names = new ArrayList<String>();
		for(Form form: forms) {
			if(form.getTypeId().equals(FormsTypes.EVALUATOR_FORM)) {
				names.add(getEvaluatorName(form.getFormId()));
			} else if(form.getTypeId().equals(FormsTypes.PATIENT_FORM)) {
				names.add(getPatientName(form.getFormId()));
			}
			
		}
		
		return names;
	}
	
	public String getPatientName(int formId) {
		String name = ""; 
		Answer firstName, middleName, lastName;
		firstName =  getAnswer(formId, CreatePatient.QUESTION_FIRST_NAME);
		middleName = getAnswer(formId, CreatePatient.QUESTION_MIDDLE_NAME);
		lastName = getAnswer(formId, CreatePatient.QUESTION_LAST_NAME);
		if(firstName != null) {
			name += firstName.getText();
		}
		if(middleName != null) {
			name += " "+middleName.getText();		
		}
		if(lastName != null) {
			name += " "+lastName.getText();	
		}
		
		return name.replace("null", "");
	}
}
