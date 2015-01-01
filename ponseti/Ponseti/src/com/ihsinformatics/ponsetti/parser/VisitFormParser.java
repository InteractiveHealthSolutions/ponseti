package com.ihsinformatics.ponsetti.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.screens.AddVisit;

public class VisitFormParser extends FormParser {

	

	@Override
	public void parse(String jsonData, Form form) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonData);
		
//		 jsonObject.getString("type");
//		 jsonObject.getJSONArray("field_patient_consent_inclusion").getJSONObject(0).getString("value");
		
		addAnswer(AddVisit.QUESTION_HOSPITAL_OR_CLINIC, jsonObject.getJSONArray("field_hospital").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_NAME_OF_EVALUATOR, jsonObject.getJSONArray("field_evaluator_name").getJSONObject(0).getString("nid"));
		addAnswer(AddVisit.QUESTION_DATE_OF_VISIT, jsonObject.getJSONArray("field_visit_date").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_IS_FINAL_VISIT, jsonObject.getJSONArray("field_is_last_visit").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_DID_EXPERIENCE_RELAPSE, jsonObject.getJSONArray("field_relapse").getJSONObject(0).getString("value"));
		
		addAnswer(AddVisit.QUESTION_RIGHT_VARUS, jsonObject.getJSONArray("field_varus_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_CAVUS, jsonObject.getJSONArray("field_cavus_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_ABDUCTUS, jsonObject.getJSONArray("field_abductus_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_ENQUINUS, jsonObject.getJSONArray("field_equinus_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_POSTERIOR_CREASE, jsonObject.getJSONArray("field_pc_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_EMPTY_HEEL, jsonObject.getJSONArray("field_eh_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_RIGID_ENQUINUS, jsonObject.getJSONArray("field_re_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_MEDIAL_CREASE, jsonObject.getJSONArray("field_mc_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_TALAR_HEAD_COVERAGE, jsonObject.getJSONArray("field_thc_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_CURVED_LATERAL_BORDER, jsonObject.getJSONArray("field_clb_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_TREATMENT, jsonObject.getJSONArray("field_treatment_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_CASTER, jsonObject.getJSONArray("field_primary_caster_right").getJSONObject(0).getString("nid"));
		addAnswer(AddVisit.QUESTION_RIGHT_CAST_NUMBER, jsonObject.getJSONArray("field_cast_number_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_DEGREES_ABDUCTION, jsonObject.getJSONArray("field_abduction_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_DEGREES_DORSIFLEXION, jsonObject.getJSONArray("field_dorsiflexion_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_BRACE_COMPILANCE, jsonObject.getJSONArray("field_brace_compliance_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_PROBLEMS, jsonObject.getJSONArray("field_brace_problems_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_ACTION_TAKEN, jsonObject.getJSONArray("field_brace_action_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_SURGERY_TYPE, jsonObject.getJSONArray("field_surgery_type_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_SURGERY_COMMENTS, jsonObject.getJSONArray("field_surgery_comments_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_GIVE_DETAILS, jsonObject.getJSONArray("field_other_details_right").getJSONObject(0).getString("value"));		
		addAnswer(AddVisit.QUESTION_RIGHT_HFCS, jsonObject.getJSONArray("field_hfcs_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_MFCS, jsonObject.getJSONArray("field_mfcs_right").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RIGHT_TS, jsonObject.getJSONArray("field_ts_right").getJSONObject(0).getString("value"));
		
		addAnswer(AddVisit.QUESTION_LEFT_VARUS, jsonObject.getJSONArray("field_varus_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_CAVUS, jsonObject.getJSONArray("field_cavus_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_ABDUCTUS, jsonObject.getJSONArray("field_abductus_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_ENQUINUS, jsonObject.getJSONArray("field_equinus_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_POSTERIOR_CREASE, jsonObject.getJSONArray("field_pc_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_EMPTY_HEEL, jsonObject.getJSONArray("field_eh_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_RIGID_ENQUINUS, jsonObject.getJSONArray("field_re_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_MEDIAL_CREASE, jsonObject.getJSONArray("field_mc_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_TALAR_HEAD_COVERAGE, jsonObject.getJSONArray("field_thc_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_CURVED_LATERAL_BORDER, jsonObject.getJSONArray("field_clb_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_TREATMENT, jsonObject.getJSONArray("field_treatment_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_CASTER, jsonObject.getJSONArray("field_primary_caster_left").getJSONObject(0).getString("nid"));
		addAnswer(AddVisit.QUESTION_LEFT_CAST_NUMBER, jsonObject.getJSONArray("field_cast_number_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_DEGREES_ABDUCTION, jsonObject.getJSONArray("field_abduction_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_DEGREES_DORSIFLEXION, jsonObject.getJSONArray("field_dorsiflexion_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_BRACE_COMPILANCE, jsonObject.getJSONArray("field_brace_compliance_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_PROBLEMS, jsonObject.getJSONArray("field_brace_problems_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_ACTION_TAKEN, jsonObject.getJSONArray("field_brace_action_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_SURGERY_TYPE, jsonObject.getJSONArray("field_surgery_type_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_SURGERY_COMMENTS, jsonObject.getJSONArray("field_surgery_comments_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_GIVE_DETAILS, jsonObject.getJSONArray("field_other_details_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_HFCS, jsonObject.getJSONArray("field_hfcs_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_MFCS, jsonObject.getJSONArray("field_mfcs_left").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_LEFT_TS, jsonObject.getJSONArray("field_ts_left").getJSONObject(0).getString("value"));
		
		addAnswer(AddVisit.QUESTION_COMPLICATIONS, jsonObject.getJSONArray("field_complications").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_DESCRIPION_OF_COMPLICATIONS, jsonObject.getJSONArray("field_complications_description").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_TREATMENT_OF_COMPLICATIONS, jsonObject.getJSONArray("field_complications_treatment").getJSONObject(0).getString("value"));
		addAnswer(AddVisit.QUESTION_RESULTS_AFTER_TREATMENT, jsonObject.getJSONArray("field_complications_results").getJSONObject(0).getString("value"));
		
		addAnswer(AddVisit.QUESTION_PARENT_NODE, jsonObject.getString("parent_node"));
	}


}
