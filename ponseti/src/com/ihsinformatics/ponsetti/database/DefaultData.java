package com.ihsinformatics.ponsetti.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ihsinformatics.ponsetti.database.data_access.FormsTypesDAO;
import com.ihsinformatics.ponsetti.database.data_access.QuestionDAO;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.database.pojos.Question;
import com.ihsinformatics.ponsetti.screens.AddVisit;
import com.ihsinformatics.ponsetti.screens.CreateEvaluator;
import com.ihsinformatics.ponsetti.screens.CreatePatient;

public class DefaultData {
	public void inserDefaultData(Context context, SQLiteDatabase db) {
		try {
			List<FormsTypes> formsTypes = new ArrayList<FormsTypes>();
			formsTypes.add(new FormsTypes(FormsTypes.PATIENT_FORM, FormsTypes.PATIENT_FORM));
			formsTypes.add(new FormsTypes(FormsTypes.EVALUATOR_FORM, FormsTypes.EVALUATOR_FORM));
			formsTypes.add(new FormsTypes(FormsTypes.VISIT_FORM, FormsTypes.VISIT_FORM));
			new FormsTypesDAO().insert(context, formsTypes, db);
			
			List<Question> questions = new ArrayList<Question>();
			
			///////////////////////////////// Create patient form questions //////////////////////////////////
			questions.add(new Question(CreatePatient.QUESTION_GUARDIAN_CONSENT, FormsTypes.PATIENT_FORM, "Doe''s the parent or guardian consent to being included", "field_patient_consent_inclusion"));
			questions.add(new Question(CreatePatient.QUESTION_PHOTO_CONSENT, FormsTypes.PATIENT_FORM, "Doe''s the parent or guardian consent to photographs of the patient being used for PIA evaluation and marketing purposes", "field_patient_consent_photos"));
			questions.add(new Question(CreatePatient.QUESTION_HOSPITAL, FormsTypes.PATIENT_FORM, "Hospital/Clinic", "field_hospital"));
			questions.add(new Question(CreatePatient.QUESTION_LAST_NAME, FormsTypes.PATIENT_FORM, "Last Name/ Surname", "field_patient_last_name"));
			questions.add(new Question(CreatePatient.QUESTION_FIRST_NAME, FormsTypes.PATIENT_FORM, "First Name", "field_patient_first_name"));
			questions.add(new Question(CreatePatient.QUESTION_MIDDLE_NAME, FormsTypes.PATIENT_FORM, "Middle Name", "field_patient_middle_name"));
			questions.add(new Question(CreatePatient.QUESTION_SEX, FormsTypes.PATIENT_FORM, "Sex", "field_patient_sex"));
			questions.add(new Question(CreatePatient.QUESTION_RACE, FormsTypes.PATIENT_FORM, "Race", "field_patient_race"));
			questions.add(new Question(CreatePatient.QUESTION_DATE_OF_BIRTH, FormsTypes.PATIENT_FORM, "Date of birth", "field_patient_dob"));
			questions.add(new Question(CreatePatient.QUESTION_TRIBE, FormsTypes.PATIENT_FORM, "Tribe", "field_patient_tribe"));
			questions.add(new Question(CreatePatient.QUESTION_ADDRESS_1, FormsTypes.PATIENT_FORM, "Address 1", "field_patient_address1"));
			questions.add(new Question(CreatePatient.QUESTION_ADDRESS_2, FormsTypes.PATIENT_FORM, "address 2", "field_patient_address2"));
			questions.add(new Question(CreatePatient.QUESTION_VILLAGE, FormsTypes.PATIENT_FORM, "Village/ town/ city", "field_patient_city"));
			questions.add(new Question(CreatePatient.QUESTION_STATE, FormsTypes.PATIENT_FORM, "State/ province", "field_patient_state"));
			questions.add(new Question(CreatePatient.QUESTION_COUNTRY, FormsTypes.PATIENT_FORM, "Country", "field_patient_country"));
			
			questions.add(new Question(CreatePatient.QUESTION_PARENT_LAST_NAME, FormsTypes.PATIENT_FORM, "Last Name/ Surname", "field_guardian1_last_name"));
			questions.add(new Question(CreatePatient.QUESTION_PARENT_FIRST_NAME, FormsTypes.PATIENT_FORM, "First Name", "field_guardian1_first_name"));
			questions.add(new Question(CreatePatient.QUESTION_PARENT_MIDDLE_NAME, FormsTypes.PATIENT_FORM, "Middle Name", "field_guardian1_middle_name"));
			questions.add(new Question(CreatePatient.QUESTION_PARENT_RELATON_TO_PATIENT, FormsTypes.PATIENT_FORM, "Relationship to patient", "field_guardian1_relationship"));
			questions.add(new Question(CreatePatient.QUESTION_PARENT_PHONE_NUMBER_1, FormsTypes.PATIENT_FORM, "Phone number 1", "field_guardian1_phone1"));
			questions.add(new Question(CreatePatient.QUESTION_PARENT_PHONE_NUMBER_2, FormsTypes.PATIENT_FORM, "Phone number 2", "field_guardian1_phone2"));
			questions.add(new Question(CreatePatient.QUESTION_SECONDARY_PARENT, FormsTypes.PATIENT_FORM, "Seecondary parent?", "field_guardian2"));
			questions.add(new Question(CreatePatient.QUESTION_SECONDARY_PARENT_LAST_NAME, FormsTypes.PATIENT_FORM, "Last Name/ Surname", "field_guardian2_last_name"));
			questions.add(new Question(CreatePatient.QUESTION_SECONDARY_PARENT_FIRST_NAME, FormsTypes.PATIENT_FORM, "First Name", "field_guardian2_first_name"));
			questions.add(new Question(CreatePatient.QUESTION_SECONDARY_PARENT_MIDDLE_NAME, FormsTypes.PATIENT_FORM, "Middle Name", "field_guardian2_middle_name"));
			questions.add(new Question(CreatePatient.QUESTION_SECONDARY_PARENT_RELATON_TO_PATIENT, FormsTypes.PATIENT_FORM, "Relationship to patient", "field_guardian2_relationship"));
			questions.add(new Question(CreatePatient.QUESTION_SECONDARY_PARENT_PHONE_NUMBER_1, FormsTypes.PATIENT_FORM, "Phone number 1", "field_guardian2_phone1"));
			questions.add(new Question(CreatePatient.QUESTION_SECONDARY_PARENT_PHONE_NUMBER_2, FormsTypes.PATIENT_FORM, "Phone number 2", "field_guardian2_phone2"));
			questions.add(new Question(CreatePatient.QUESTION_EMERGENCY_CONTACT, FormsTypes.PATIENT_FORM, "Emergency contact", "field_emergency_contact"));
			questions.add(new Question(CreatePatient.QUESTION_EMERGENCY_PARENT_LAST_NAME, FormsTypes.PATIENT_FORM, "Last Name/ Surname", "field_emergency_last_name"));
			questions.add(new Question(CreatePatient.QUESTION_EMERGENCY_PARENT_FIRST_NAME, FormsTypes.PATIENT_FORM, "First Name", "field_emergency_first_name"));
			questions.add(new Question(CreatePatient.QUESTION_EMERGENCY_PARENT_MIDDLE_NAME, FormsTypes.PATIENT_FORM, "Middle Name", "field_emergency_middle_name"));
			questions.add(new Question(CreatePatient.QUESTION_EMERGENCY_PARENT_RELATON_TO_PATIENT, FormsTypes.PATIENT_FORM, "Relationship to patient", "field_emergency_relationship"));
			questions.add(new Question(CreatePatient.QUESTION_EMERGENCY_PARENT_PHONE_NUMBER_1, FormsTypes.PATIENT_FORM, "Phone number 1", "field_emergency_phone1"));
			questions.add(new Question(CreatePatient.QUESTION_EMERGENCY_PARENT_PHONE_NUMBER_2, FormsTypes.PATIENT_FORM, "Phone number 2", "field_emergency_phone2"));
			
			questions.add(new Question(CreatePatient.QUESTION_ANY_RELATIVES_WITH_CF, FormsTypes.PATIENT_FORM, "Any relatives with clubfoot deformity?", "field_affected_relatives"));
			questions.add(new Question(CreatePatient.QUESTION_HOW_MANY, FormsTypes.PATIENT_FORM, "How many?", "field_num_affected_relatives"));
			questions.add(new Question(CreatePatient.QUESTION_LENGHT_OF_PREGNANCY, FormsTypes.PATIENT_FORM, "Length of pregnancy", "field_pregnancy_length"));
			questions.add(new Question(CreatePatient.QUESTION_MOTHER_COMPLICATION, FormsTypes.PATIENT_FORM, "Did mother have any complications at pregnancy?", "field_pregnancy_complications"));
			questions.add(new Question(CreatePatient.QUESTION_WHAT_WERE_COMPLICATIONS, FormsTypes.PATIENT_FORM, "What were the complications?", "field_preg_complications_type"));
			questions.add(new Question(CreatePatient.QUESTION_MOTHER_ALCOHOL, FormsTypes.PATIENT_FORM, "Did mother consume alcohol during pregnancy?", "field_pregnancy_drinking"));
			questions.add(new Question(CreatePatient.QUESTION_MOTHER_SMOKE, FormsTypes.PATIENT_FORM, "Did mother smoke during pregnancy?", "field_pregnancy_smoking"));
			questions.add(new Question(CreatePatient.QUESTION_BIRTH_COMPLICATIONS, FormsTypes.PATIENT_FORM, "Birth complications", "field_birth_complications"));
			questions.add(new Question(CreatePatient.QUESTION_BIRTH_PLACE, FormsTypes.PATIENT_FORM, "Birth place?", "field_birth_place"));
			
			questions.add(new Question(CreatePatient.QUESTION_REFERAL_SOURCE, FormsTypes.PATIENT_FORM, "Referal source?", "field_referral_source"));
			questions.add(new Question(CreatePatient.QUESTION_DOCTOR_NAME, FormsTypes.PATIENT_FORM, "Doctor name", "field_referral_doctor_name"));
			questions.add(new Question(CreatePatient.QUESTION_HOSPITAL_OR_CLINIC_NAME, FormsTypes.PATIENT_FORM, "Hospital/ clinic name", "field_referral_hospital_name"));
			questions.add(new Question(CreatePatient.QUESTION_PLEASE_SPECIFY, FormsTypes.PATIENT_FORM, "Please specify", "field_referral_other"));
			
			questions.add(new Question(CreatePatient.QUESTION_NAME_OF_EVALUATOR, FormsTypes.PATIENT_FORM, "Name of evaluator", "field_evaluator_name"));
			questions.add(new Question(CreatePatient.QUESTION_EVALUATION_DATE, FormsTypes.PATIENT_FORM, "Evaluation date", "field_evaluation_date"));
			questions.add(new Question(CreatePatient.QUESTION_FEET_AFFECTED, FormsTypes.PATIENT_FORM, "Feet affected", "field_feet_affected"));
			questions.add(new Question(CreatePatient.QUESTION_DIAGNOSIS, FormsTypes.PATIENT_FORM, "Diagnosis", "field_diagnosis"));
			questions.add(new Question(CreatePatient.QUESTION_DEFORMITY_PRESENT_AT_BIRTH, FormsTypes.PATIENT_FORM, "Deformity present at birth?", "field_deformity_at_birth"));
			questions.add(new Question(CreatePatient.QUESTION_ANY_PREVIOUS_TREATMENT, FormsTypes.PATIENT_FORM, "Any previous treatments", "field_previous_treatments"));
			questions.add(new Question(CreatePatient.QUESTION_HOW_MANY_PREVIOUS_TREATMENTS, FormsTypes.PATIENT_FORM, "Number of previous treatments", "field_num_previous_treatments"));
			questions.add(new Question(CreatePatient.QUESTION_TYPE_OF_PREVIOUS_TREATMENTS, FormsTypes.PATIENT_FORM, "Type of previous treatments", "field_previous_treatments_type"));
			questions.add(new Question(CreatePatient.QUESTION_DIAGNOSED_PARENTALLY, FormsTypes.PATIENT_FORM, "Diagnosed parentally", "field_prenatal_diagnosis"));
			questions.add(new Question(CreatePatient.QUESTION_AT_PREGNANCY_WEEK, FormsTypes.PATIENT_FORM, "At pregnancy week", "field_prenatal_week"));
			questions.add(new Question(CreatePatient.QUESTION_CONFIRMED_AT_BIRTH, FormsTypes.PATIENT_FORM, "Confirmed at birth", "field_prenatal_confirmed"));
			questions.add(new Question(CreatePatient.QUESTION_DIAGNOSIS_COMMENTS, FormsTypes.PATIENT_FORM, "Diagnosis comments", "field_diagnosis_comments"));
			
			questions.add(new Question(CreatePatient.QUESTION_ANY_ABNORMALITIES, FormsTypes.PATIENT_FORM, "Any abnormalities", "field_abnormalities"));
			questions.add(new Question(CreatePatient.QUESTION_ANY_WEAKNESS, FormsTypes.PATIENT_FORM, "Any weakness", "field_weakness"));
			
			//////////////////////////////////// Visit form questions /////////////////////////////////////
			questions.add(new Question(AddVisit.QUESTION_HOSPITAL_OR_CLINIC, FormsTypes.VISIT_FORM, "Hospital/Clinic", "field_hospital"));
			questions.add(new Question(AddVisit.QUESTION_NAME_OF_EVALUATOR, FormsTypes.VISIT_FORM, "Name of evaluator", "field_evaluator_name"));
			questions.add(new Question(AddVisit.QUESTION_DATE_OF_VISIT, FormsTypes.VISIT_FORM, "Date of visit", "field_visit_date"));
			questions.add(new Question(AddVisit.QUESTION_DATE_OF_NEXT_VISIT, FormsTypes.VISIT_FORM, "Date of next visit", "field_next_visit_date"));
			questions.add(new Question(AddVisit.QUESTION_IS_FINAL_VISIT, FormsTypes.VISIT_FORM, "Is this the patient''s final treatment visit", "field_is_last_visit"));
			questions.add(new Question(AddVisit.QUESTION_DID_EXPERIENCE_RELAPSE, FormsTypes.VISIT_FORM, "Did the patient experience a relapse", "field_relapse"));
			
			questions.add(new Question(AddVisit.QUESTION_RIGHT_VARUS, FormsTypes.VISIT_FORM, "Varus", "field_varus_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_CAVUS, FormsTypes.VISIT_FORM, "Cavus", "field_cavus_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_ABDUCTUS, FormsTypes.VISIT_FORM, "Abductus", "field_abductus_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_ENQUINUS, FormsTypes.VISIT_FORM, "Equinus", "field_equinus_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_POSTERIOR_CREASE, FormsTypes.VISIT_FORM, "Posterior crease", "field_pc_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_EMPTY_HEEL, FormsTypes.VISIT_FORM, "Empty heel", "field_eh_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_RIGID_ENQUINUS, FormsTypes.VISIT_FORM, "Rigid enquinus", "field_re_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_MEDIAL_CREASE, FormsTypes.VISIT_FORM, "Medial crease", "field_mc_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_TALAR_HEAD_COVERAGE, FormsTypes.VISIT_FORM, "Talar head coverage", "field_thc_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_CURVED_LATERAL_BORDER, FormsTypes.VISIT_FORM, "Curved lateral border", "field_clb_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_TREATMENT, FormsTypes.VISIT_FORM, "Treatment", "field_treatment_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_CASTER, FormsTypes.VISIT_FORM, "Caster", "field_primary_caster_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_CAST_NUMBER, FormsTypes.VISIT_FORM, "Cast number", "field_cast_number_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_DEGREES_ABDUCTION, FormsTypes.VISIT_FORM, "Degrees abduction", "field_abduction_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_DEGREES_DORSIFLEXION, FormsTypes.VISIT_FORM, "Degrees dorsiflextion", "field_dorsiflexion_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_BRACE_COMPILANCE, FormsTypes.VISIT_FORM, "Brace compilance", "field_brace_compliance_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_PROBLEMS, FormsTypes.VISIT_FORM, "Problems", "field_brace_problems_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_ACTION_TAKEN, FormsTypes.VISIT_FORM, "Action taken", "field_brace_action_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_SURGERY_TYPE, FormsTypes.VISIT_FORM, "Surgery type", "field_surgery_type_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_SURGERY_COMMENTS, FormsTypes.VISIT_FORM, "Surgery comments", "field_surgery_comments_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_GIVE_DETAILS, FormsTypes.VISIT_FORM, "Give details", "field_other_details_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_HFCS, FormsTypes.VISIT_FORM, "Right HFCS", "field_hfcs_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_MFCS, FormsTypes.VISIT_FORM, "Right MFCS", "field_mfcs_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_TS, FormsTypes.VISIT_FORM, "Right TS", "field_ts_right"));
			questions.add(new Question(AddVisit.QUESTION_RIGHT_OTHER_SURGERY_TYPE, FormsTypes.VISIT_FORM, "Other surgery type", "field_surgery_other_type_right"));
			
			questions.add(new Question(AddVisit.QUESTION_LEFT_VARUS, FormsTypes.VISIT_FORM, "Varus", "field_varus_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_CAVUS, FormsTypes.VISIT_FORM, "Cavus", "field_cavus_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_ABDUCTUS, FormsTypes.VISIT_FORM, "Abductus", "field_abductus_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_ENQUINUS, FormsTypes.VISIT_FORM, "Equinus", "field_equinus_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_POSTERIOR_CREASE, FormsTypes.VISIT_FORM, "Posterior crease", "field_pc_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_EMPTY_HEEL, FormsTypes.VISIT_FORM, "Empty heel", "field_eh_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_RIGID_ENQUINUS, FormsTypes.VISIT_FORM, "Rigid enquinus", "field_re_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_MEDIAL_CREASE, FormsTypes.VISIT_FORM, "Medial crease", "field_mc_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_TALAR_HEAD_COVERAGE, FormsTypes.VISIT_FORM, "Talar head coverage", "field_thc_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_CURVED_LATERAL_BORDER, FormsTypes.VISIT_FORM, "Curved lateral border", "field_clb_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_TREATMENT, FormsTypes.VISIT_FORM, "Treatment", "field_treatment_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_CASTER, FormsTypes.VISIT_FORM, "Caster", "field_primary_caster_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_CAST_NUMBER, FormsTypes.VISIT_FORM, "Cast number", "field_cast_number_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_DEGREES_ABDUCTION, FormsTypes.VISIT_FORM, "Degrees abduction", "field_abduction_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_DEGREES_DORSIFLEXION, FormsTypes.VISIT_FORM, "Degrees dorsiflextion", "field_dorsiflexion_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_BRACE_COMPILANCE, FormsTypes.VISIT_FORM, "Brace compilance", "field_brace_compliance_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_PROBLEMS, FormsTypes.VISIT_FORM, "Problems", "field_brace_problems_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_ACTION_TAKEN, FormsTypes.VISIT_FORM, "Action taken", "field_brace_action_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_SURGERY_TYPE, FormsTypes.VISIT_FORM, "Surgery type", "field_surgery_type_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_SURGERY_COMMENTS, FormsTypes.VISIT_FORM, "Surgery comments", "field_surgery_comments_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_GIVE_DETAILS, FormsTypes.VISIT_FORM, "Give details", "field_other_details_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_HFCS, FormsTypes.VISIT_FORM, "Left HFCS", "field_hfcs_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_MFCS, FormsTypes.VISIT_FORM, "Left MFCS", "field_mfcs_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_TS, FormsTypes.VISIT_FORM, "Left TS", "field_ts_left"));
			questions.add(new Question(AddVisit.QUESTION_LEFT_OTHER_SURGERY_TYPE, FormsTypes.VISIT_FORM, "Other surgery type", "field_surgery_other_type_left"));
			
			questions.add(new Question(AddVisit.QUESTION_COMPLICATIONS, FormsTypes.VISIT_FORM, "Complications", "field_complications"));
			questions.add(new Question(AddVisit.QUESTION_DESCRIPION_OF_COMPLICATIONS, FormsTypes.VISIT_FORM, "Description of complication(s)", "field_complications_description"));
			questions.add(new Question(AddVisit.QUESTION_TREATMENT_OF_COMPLICATIONS, FormsTypes.VISIT_FORM, "Treatment of complication(s)", "field_complications_treatment"));
			questions.add(new Question(AddVisit.QUESTION_RESULTS_AFTER_TREATMENT, FormsTypes.VISIT_FORM, "Results after treatment", "field_complications_results"));
			questions.add(new Question(AddVisit.QUESTION_PARENT_NODE, FormsTypes.VISIT_FORM, "Parent node", "parent_node"));
			questions.add(new Question(AddVisit.QUESTION_GENERAL_COMMENTS, FormsTypes.VISIT_FORM, "General comments", "field_general_comments"));
			
			///////////////////////////////// Create evaluator form questions //////////////////////////////////
			questions.add(new Question(CreateEvaluator.QUESTION_LAST_NAME, FormsTypes.EVALUATOR_FORM, "Last Name/ Surname", "field_evaluator_last_name"));
			questions.add(new Question(CreateEvaluator.QUESTION_FIRST_NAME, FormsTypes.EVALUATOR_FORM, "First Name", "field_evaluator_first_name"));
			questions.add(new Question(CreateEvaluator.QUESTION_MIDDLE_NAME, FormsTypes.EVALUATOR_FORM, "Middle Name", "field_evaluator_middle_name"));
			questions.add(new Question(CreateEvaluator.QUESTION_TITLE, FormsTypes.EVALUATOR_FORM, "Title of evaluator", "field_evaluator_title"));
			
			questions.add(new Question(CreateEvaluator.QUESTION_SEX, FormsTypes.EVALUATOR_FORM, "Sex", ""));
			
			new QuestionDAO(context).insert(questions, db);
			
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
}
