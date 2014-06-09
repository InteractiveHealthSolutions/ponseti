package com.ihsinformatics.ponsetti.database.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.QuestionDAO;
import com.ihsinformatics.ponsetti.utils.Tools;

public class Form implements Serializable {
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public static String COLUMN_FORM_ID = "form_id";
	public static String COLUMN_ICR_ID = "icr_id";
	public static String COLUMN_TIME_STAMP = "timestamp";
	public static String COLUMN_TYPE_ID = "type_id";
	
	int formId;
	String icrId;
	int timeStamp;
	String typeId;
	
	public Form() {
		
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public Form(String icrId, int timeStamp, String typeId) {
		super();
		this.icrId = icrId;
		this.timeStamp = timeStamp;
		this.typeId = typeId;
	}

	public Form(int formId, String icrId, int timeStamp, String typeId) {
		super();
		this.icrId = icrId;
		this.timeStamp = timeStamp;
		this.typeId = typeId;
		this.formId = formId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getIcrId() {
		return icrId;
	}

	public void setIcrId(String icrId) {
		this.icrId = icrId;
	}

	public int getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public JSONObject getJsonizedFormData(Context context) {
		Tools tools = Tools.getInstance();
		AnswerDAO answerDAO = new AnswerDAO(context);
		QuestionDAO questionDAO = new QuestionDAO(context);
		List<Answer> answersList = answerDAO.getAnswers(formId);
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			String ans;
			for(Answer answer : answersList) {
				String fieldName = questionDAO.getQuestions(answer.getQuestion_id()).getFieldName();
				ans = answer.getText();
				JSONArray jsonArray = new JSONArray();
				JSONObject tempObject = new JSONObject();
				if(tools.isICRID(ans)) {
					tempObject.put("nid", ans);
				} else {
					tempObject.put("value", ans);
				}
				
				jsonArray.put(tempObject);
				jsonObject.put(fieldName, jsonArray);
				
			}
			jsonObject.put("type", typeId);
			if(typeId.equals(FormsTypes.VISIT_FORM)) {
				jsonObject.put("title", icrId);
				String parent_node = jsonObject.getJSONArray("parent_node").getJSONObject(0).getString("nid");
				jsonObject.remove("parent_node");
				jsonObject.put("parent_node", parent_node);
				
			} else if(typeId.equals(FormsTypes.PATIENT_FORM)) {
				jsonObject.put("title", icrId+" "+jsonObject.getJSONArray("field_patient_last_name").getJSONObject(0).getString("value")+","+jsonObject.getJSONArray("field_patient_first_name").getJSONObject(0).getString("value"));
				
			} else if(typeId.equals(FormsTypes.EVALUATOR_FORM)) {
				jsonObject.put("title", icrId);
				
			}
			
		} catch(JSONException jsonException) {
			jsonException.printStackTrace();
		}
		
		return jsonObject;
	}
	
	public static List<Form> parseForms(HashMap<String, String> icrIdTimeStampPair, String formType) {
		List<Form> forms = new ArrayList<Form>();
		Iterator<String> itr = icrIdTimeStampPair.keySet().iterator();
		String temp;
		while(itr.hasNext()) {
			temp = itr.next();
			forms.add(new Form(temp, Integer.parseInt(icrIdTimeStampPair.get(temp)), formType));
		}
		
		return forms;
	}
	
}
