package com.ihsinformatics.ponsetti.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.screens.CreateEvaluator;

public class EvaluatorFormParser extends FormParser {

	

	@Override
	public void parse(String jsonData, Form form) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonData);
		
		// jsonObject.getString("type");
		// jsonObject.getJSONArray("field_patient_consent_inclusion").getJSONObject(0).getString("value");
		addAnswer(CreateEvaluator.QUESTION_LAST_NAME, jsonObject.getJSONArray("field_evaluator_last_name").getJSONObject(0).getString("value"));
		addAnswer(CreateEvaluator.QUESTION_MIDDLE_NAME, jsonObject.getJSONArray("field_evaluator_middle_name").getJSONObject(0).getString("value"));
		addAnswer(CreateEvaluator.QUESTION_FIRST_NAME, jsonObject.getJSONArray("field_evaluator_first_name").getJSONObject(0).getString("value"));
		addAnswer(CreateEvaluator.QUESTION_TITLE, jsonObject.getJSONArray("field_evaluator_title").getJSONObject(0).getString("value"));

	}

}
