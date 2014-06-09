package com.ihsinformatics.ponsetti.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.screens.CreatePatient;

public class PatientFormParser extends FormParser {

	
	
	@Override
	public void parse(String jsonData, Form form) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonData);
		
		// jsonObject.getString("type");
		// jsonObject.getJSONArray("field_patient_consent_inclusion").getJSONObject(0).getString("value");
		addAnswer(CreatePatient.QUESTION_GUARDIAN_CONSENT,  jsonObject.getJSONArray("field_patient_consent_inclusion").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_PHOTO_CONSENT,  jsonObject.getJSONArray("field_patient_consent_photos").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_HOSPITAL,  jsonObject.getJSONArray("field_hospital").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_LAST_NAME,  jsonObject.getJSONArray("field_patient_last_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_FIRST_NAME,  jsonObject.getJSONArray("field_patient_first_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_MIDDLE_NAME,  jsonObject.getJSONArray("field_patient_middle_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_SEX,  jsonObject.getJSONArray("field_patient_sex").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_RACE,  jsonObject.getJSONArray("field_patient_race").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_DATE_OF_BIRTH,  jsonObject.getJSONArray("field_patient_dob").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_TRIBE,  jsonObject.getJSONArray("field_patient_tribe").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_ADDRESS_1,  jsonObject.getJSONArray("field_patient_address1").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_ADDRESS_2,  jsonObject.getJSONArray("field_patient_address2").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_VILLAGE,  jsonObject.getJSONArray("field_patient_city").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_STATE,  jsonObject.getJSONArray("field_patient_state").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_COUNTRY,  jsonObject.getJSONArray("field_patient_country").getJSONObject(0).getString("value"));
		
		addAnswer(CreatePatient.QUESTION_PARENT_LAST_NAME,  jsonObject.getJSONArray("field_guardian1_last_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_PARENT_FIRST_NAME,  jsonObject.getJSONArray("field_guardian1_first_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_PARENT_MIDDLE_NAME,  jsonObject.getJSONArray("field_guardian1_middle_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_PARENT_RELATON_TO_PATIENT,  jsonObject.getJSONArray("field_guardian1_relationship").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_PARENT_PHONE_NUMBER_1,  jsonObject.getJSONArray("field_guardian1_phone1").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_PARENT_PHONE_NUMBER_2,  jsonObject.getJSONArray("field_guardian1_phone2").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_SECONDARY_PARENT,  jsonObject.getJSONArray("field_guardian2").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_SECONDARY_PARENT_LAST_NAME,  jsonObject.getJSONArray("field_guardian2_last_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_SECONDARY_PARENT_FIRST_NAME,  jsonObject.getJSONArray("field_guardian2_first_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_SECONDARY_PARENT_MIDDLE_NAME,  jsonObject.getJSONArray("field_guardian2_middle_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_SECONDARY_PARENT_RELATON_TO_PATIENT,  jsonObject.getJSONArray("field_guardian2_relationship").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_SECONDARY_PARENT_PHONE_NUMBER_1,  jsonObject.getJSONArray("field_guardian2_phone1").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_SECONDARY_PARENT_PHONE_NUMBER_2,  jsonObject.getJSONArray("field_guardian2_phone2").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_EMERGENCY_CONTACT,  jsonObject.getJSONArray("field_emergency_contact").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_EMERGENCY_PARENT_LAST_NAME,  jsonObject.getJSONArray("field_emergency_last_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_EMERGENCY_PARENT_FIRST_NAME,  jsonObject.getJSONArray("field_emergency_first_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_EMERGENCY_PARENT_MIDDLE_NAME,  jsonObject.getJSONArray("field_emergency_middle_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_EMERGENCY_PARENT_RELATON_TO_PATIENT,  jsonObject.getJSONArray("field_emergency_relationship").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_EMERGENCY_PARENT_PHONE_NUMBER_1,  jsonObject.getJSONArray("field_emergency_phone1").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_EMERGENCY_PARENT_PHONE_NUMBER_2,  jsonObject.getJSONArray("field_emergency_phone2").getJSONObject(0).getString("value"));
		
		addAnswer(CreatePatient.QUESTION_ANY_RELATIVES_WITH_CF,  jsonObject.getJSONArray("field_affected_relatives").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_HOW_MANY,  jsonObject.getJSONArray("field_num_affected_relatives").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_LENGHT_OF_PREGNANCY,  jsonObject.getJSONArray("field_pregnancy_length").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_MOTHER_COMPLICATION,  jsonObject.getJSONArray("field_pregnancy_complications").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_WHAT_WERE_COMPLICATIONS,  jsonObject.getJSONArray("field_preg_complications_type").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_MOTHER_ALCOHOL,  jsonObject.getJSONArray("field_pregnancy_drinking").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_MOTHER_SMOKE,  jsonObject.getJSONArray("field_pregnancy_smoking").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_BIRTH_COMPLICATIONS,  jsonObject.getJSONArray("field_birth_complications").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_BIRTH_PLACE,  jsonObject.getJSONArray("field_birth_place").getJSONObject(0).getString("value"));
		
		addAnswer(CreatePatient.QUESTION_REFERAL_SOURCE,  jsonObject.getJSONArray("field_referral_source").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_DOCTOR_NAME,  jsonObject.getJSONArray("field_referral_doctor_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_HOSPITAL_OR_CLINIC_NAME,  jsonObject.getJSONArray("field_referral_hospital_name").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_PLEASE_SPECIFY,  jsonObject.getJSONArray("field_referral_other").getJSONObject(0).getString("value"));
		
		addAnswer(CreatePatient.QUESTION_NAME_OF_EVALUATOR,  jsonObject.getJSONArray("field_evaluator_name").getJSONObject(0).getString("nid"));
		addAnswer(CreatePatient.QUESTION_EVALUATION_DATE,  jsonObject.getJSONArray("field_evaluation_date").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_FEET_AFFECTED,  jsonObject.getJSONArray("field_feet_affected").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_DIAGNOSIS,  jsonObject.getJSONArray("field_diagnosis").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_DEFORMITY_PRESENT_AT_BIRTH,  jsonObject.getJSONArray("field_deformity_at_birth").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_ANY_PREVIOUS_TREATMENT,  jsonObject.getJSONArray("field_previous_treatments").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_HOW_MANY_PREVIOUS_TREATMENTS,  jsonObject.getJSONArray("field_num_previous_treatments").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_TYPE_OF_PREVIOUS_TREATMENTS,  jsonObject.getJSONArray("field_previous_treatments_type").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_DIAGNOSED_PARENTALLY,  jsonObject.getJSONArray("field_prenatal_diagnosis").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_AT_PREGNANCY_WEEK,  jsonObject.getJSONArray("field_prenatal_week").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_CONFIRMED_AT_BIRTH,  jsonObject.getJSONArray("field_prenatal_confirmed").getJSONObject(0).getString("value"));
		addAnswer(CreatePatient.QUESTION_DIAGNOSIS_COMMENTS,  jsonObject.getJSONArray("field_diagnosis_comments").getJSONObject(0).getString("value"));
		
		
		JSONArray abnormalitiesArray = jsonObject.getJSONArray("field_abnormalities");
		JSONArray weaknessArray =  jsonObject.getJSONArray("field_weakness");
		
		for(int i=0; i<abnormalitiesArray.length(); i++) {
			if((i+1) < abnormalitiesArray.length()) {
				addAnswer(CreatePatient.QUESTION_ANY_ABNORMALITIES, abnormalitiesArray.getJSONObject(i).getString("value"));
			}
		}
		
		for(int i=0; i<weaknessArray.length(); i++) {
			if((i+1) < weaknessArray.length()) {
				addAnswer(CreatePatient.QUESTION_ANY_WEAKNESS,  weaknessArray.getJSONObject(i).getString("value"));
			}
		}
		
		
		
		
		
	}
	
}
