package com.ihsinformatics.ponsetti.view.Fragments.create_patient_form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.screens.CreatePatient;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;

@SuppressLint("NewApi")

public class PatientGardianInfoFragment extends MyFragment implements OnCheckedChangeListener, OnItemSelectedListener {
	
	EditText  etParentLastName, etParentFirstName, etParentMiddleName, etParentPhoneNumber1, etParentPhoneNumber2,
	etSecondaryParentLastName, etSecondaryParentFirstName, etSecondaryParentMiddleName, etSecondaryParentPhoneNumber1, etSecondaryParentPhoneNumber2,
	etEmergencyParentLastName, etEmergencyParentFirstName, etEmergencyParentMiddleName, etEmergencyParentPhoneNumber1, etEmergencyParentPhoneNumber2;
	
	Spinner spParentRelation, spSecondaryParentRelation, spEmergencyParentRelation, spEmergencyContact;
	CheckBox cbSecondaryParent;
	RelativeLayout rlSecondaryGuardian, rlEmergencyGuardian;
	
	public PatientGardianInfoFragment() {
		super();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		etParentLastName = (EditText) rootView.findViewById(R.id.etParentSurName);
		etParentMiddleName = (EditText) rootView.findViewById(R.id.etParentMiddleName);
		etParentFirstName = (EditText) rootView.findViewById(R.id.etParentFirstName);
		spParentRelation = (Spinner) rootView.findViewById(R.id.spParentRelationToPatient);
		etParentPhoneNumber1 = (EditText) rootView.findViewById(R.id.etParentPhoneNumber);
		etParentPhoneNumber2 = (EditText) rootView.findViewById(R.id.etParentPhoneNumber2);
		
		etSecondaryParentLastName = (EditText) rootView.findViewById(R.id.etSecondaryParentSurName);
		etSecondaryParentMiddleName = (EditText) rootView.findViewById(R.id.etSecondaryParentMiddleName);
		etSecondaryParentFirstName = (EditText) rootView.findViewById(R.id.etSecondaryParentFirstName);
		spSecondaryParentRelation = (Spinner) rootView.findViewById(R.id.spSecondaryParentRelationToPatient);
		etSecondaryParentPhoneNumber1 = (EditText) rootView.findViewById(R.id.etSecondaryParentPhoneNumber);
		etSecondaryParentPhoneNumber2 = (EditText) rootView.findViewById(R.id.etSecondaryParentPhoneNumber2);
		
		etEmergencyParentLastName = (EditText) rootView.findViewById(R.id.etEmergencyParentSurName);	
		etEmergencyParentMiddleName = (EditText) rootView.findViewById(R.id.etEmergencyParentMiddleName);
		etEmergencyParentFirstName = (EditText) rootView.findViewById(R.id.etEmergencyParentFirstName);
		spEmergencyParentRelation = (Spinner) rootView.findViewById(R.id.spEmergencyParentRelationToPatient);
		etEmergencyParentPhoneNumber1 = (EditText) rootView.findViewById(R.id.etEmergencyParentPhoneNumber);
		etEmergencyParentPhoneNumber2 = (EditText) rootView.findViewById(R.id.etEmergencyParentPhoneNumber2);
		
		
		spEmergencyContact = (Spinner) rootView.findViewById(R.id.spEmergencyContact);
		
		rlSecondaryGuardian = (RelativeLayout) rootView.findViewById(R.id.rlSecondaryGuardian);
		rlEmergencyGuardian = (RelativeLayout) rootView.findViewById(R.id.rlEmergencyGuardian);
		
		spEmergencyContact.setOnItemSelectedListener(this);
		
		cbSecondaryParent = (CheckBox) rootView.findViewById(R.id.cbIsSecondaryGuardian);
		cbSecondaryParent.setOnCheckedChangeListener(this);
		
		
		
		checkAndFillData();
		
		return rootView;
	}
	
	@Override
	public List<Answer> getAnswers(int formId) {
		answers = new ArrayList<Answer>();
		answers.add(new Answer(tools.getViewText(etParentLastName), CreatePatient.QUESTION_PARENT_LAST_NAME, formId));
		answers.add(new Answer(tools.getViewText(etParentFirstName), CreatePatient.QUESTION_PARENT_FIRST_NAME, formId));
		answers.add(new Answer(tools.getViewText(etParentMiddleName), CreatePatient.QUESTION_PARENT_MIDDLE_NAME, formId));
		answers.add(new Answer(tools.getViewText(spParentRelation), CreatePatient.QUESTION_PARENT_RELATON_TO_PATIENT, formId));
		answers.add(new Answer(tools.getViewText(etParentPhoneNumber1), CreatePatient.QUESTION_PARENT_PHONE_NUMBER_1, formId));
		answers.add(new Answer(tools.getViewText(etParentPhoneNumber2), CreatePatient.QUESTION_PARENT_PHONE_NUMBER_2, formId));
		answers.add(new Answer(tools.getViewText(etSecondaryParentLastName), CreatePatient.QUESTION_SECONDARY_PARENT_LAST_NAME, formId));
		answers.add(new Answer(tools.getViewText(etSecondaryParentFirstName), CreatePatient.QUESTION_SECONDARY_PARENT_FIRST_NAME, formId));
		answers.add(new Answer(tools.getViewText(etSecondaryParentMiddleName), CreatePatient.QUESTION_SECONDARY_PARENT_MIDDLE_NAME, formId));
		answers.add(new Answer(tools.getViewText(spSecondaryParentRelation), CreatePatient.QUESTION_SECONDARY_PARENT_RELATON_TO_PATIENT, formId));
		answers.add(new Answer(tools.getViewText(etSecondaryParentPhoneNumber1), CreatePatient.QUESTION_SECONDARY_PARENT_PHONE_NUMBER_1, formId));
		answers.add(new Answer(tools.getViewText(etSecondaryParentPhoneNumber2), CreatePatient.QUESTION_SECONDARY_PARENT_PHONE_NUMBER_2, formId));
		answers.add(new Answer(tools.getViewText(etEmergencyParentLastName), CreatePatient.QUESTION_EMERGENCY_PARENT_LAST_NAME, formId));
		answers.add(new Answer(tools.getViewText(etEmergencyParentFirstName), CreatePatient.QUESTION_EMERGENCY_PARENT_FIRST_NAME, formId));
		answers.add(new Answer(tools.getViewText(etEmergencyParentMiddleName), CreatePatient.QUESTION_EMERGENCY_PARENT_MIDDLE_NAME, formId));
		answers.add(new Answer(tools.getViewText(spEmergencyParentRelation), CreatePatient.QUESTION_EMERGENCY_PARENT_RELATON_TO_PATIENT, formId));
		answers.add(new Answer(tools.getViewText(etEmergencyParentPhoneNumber1), CreatePatient.QUESTION_EMERGENCY_PARENT_PHONE_NUMBER_1, formId));
		answers.add(new Answer(tools.getViewText(etEmergencyParentPhoneNumber2), CreatePatient.QUESTION_EMERGENCY_PARENT_PHONE_NUMBER_2, formId));
		answers.add(new Answer((cbSecondaryParent.isChecked())?getString(R.string.yes):getString(R.string.no), CreatePatient.QUESTION_SECONDARY_PARENT, formId));
		answers.add(new Answer(tools.getViewText(spEmergencyContact), CreatePatient.QUESTION_EMERGENCY_CONTACT, formId));
		
		return answers;
	}
	
	@Override
	protected void checkAndFillData() {
		if(dataLoadRequest!= null && dataLoadRequest) {
			rlMain = (RelativeLayout) rootView.findViewById(R.id.rlMain);
			tools.freezeAllInViewGroup(rlMain);
			List<String> relations = Arrays.asList(getResources().getStringArray(R.array.relations)) ;
			for(Answer a: answers) {
				if(a.getQuestion_id() == CreatePatient.QUESTION_PARENT_LAST_NAME) {
					etParentLastName.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_PARENT_MIDDLE_NAME) {
					etParentMiddleName.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_PARENT_FIRST_NAME) {
					etParentFirstName.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_PARENT_RELATON_TO_PATIENT) {
					
					spParentRelation.setSelection(relations.indexOf(a.getText()));
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_PARENT_PHONE_NUMBER_1) {
					etParentPhoneNumber1.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_PARENT_PHONE_NUMBER_2) {
					etParentPhoneNumber2.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_PARENT_PHONE_NUMBER_2) {
					cbSecondaryParent.setChecked(Boolean.parseBoolean(a.getText().toLowerCase(Locale.ENGLISH)));
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_SECONDARY_PARENT_LAST_NAME) {
					etSecondaryParentLastName.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_SECONDARY_PARENT_MIDDLE_NAME) {
					etSecondaryParentMiddleName.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_SECONDARY_PARENT_FIRST_NAME) {
					etSecondaryParentFirstName.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_SECONDARY_PARENT_RELATON_TO_PATIENT) {
					
					spSecondaryParentRelation.setSelection(relations.indexOf(a.getText()));
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_SECONDARY_PARENT_PHONE_NUMBER_1) {
					etSecondaryParentPhoneNumber1.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_SECONDARY_PARENT_PHONE_NUMBER_2) {
					etSecondaryParentPhoneNumber2.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_EMERGENCY_CONTACT) {
					List<String> array = Arrays.asList(getResources().getStringArray(R.array.emergency_contact)) ;
					spEmergencyContact.setSelection(array.indexOf(a.getText()));
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_EMERGENCY_PARENT_LAST_NAME) {
					etEmergencyParentLastName.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_EMERGENCY_PARENT_MIDDLE_NAME) {
					etEmergencyParentMiddleName.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_EMERGENCY_PARENT_FIRST_NAME) {
					etEmergencyParentFirstName.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_EMERGENCY_PARENT_RELATON_TO_PATIENT) {
					
					spEmergencyParentRelation.setSelection(relations.indexOf(a.getText()));
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_EMERGENCY_PARENT_PHONE_NUMBER_1) {
					etEmergencyParentPhoneNumber1.setText(a.getText());
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_EMERGENCY_PARENT_PHONE_NUMBER_2) {
					etEmergencyParentPhoneNumber2.setText(a.getText());
					
				}
			}
		}
	}

	// private boolean b;
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (buttonView == cbSecondaryParent) {
			if (isChecked) {
				rlSecondaryGuardian.setVisibility(View.VISIBLE);
				cbSecondaryParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_filled));

			} else {
				
				rlSecondaryGuardian.setVisibility(View.GONE);
				
				cbSecondaryParent.setBackgroundDrawable(getResources().getDrawable(R.drawable.border));

			}
		}
	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg0 == spEmergencyContact) {
			if ("Other".equals(arg0.getItemAtPosition(arg2))) {
				rlEmergencyGuardian.setVisibility(View.VISIBLE);
			} else if("Secondary".equals(arg0.getItemAtPosition(arg2))) {
				cbSecondaryParent.setChecked(true);
				rlEmergencyGuardian.setVisibility(View.GONE);
			} else {
				rlEmergencyGuardian.setVisibility(View.GONE);
			}
		} else {

		}
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		
	}


	@Override
	public ValidationFailureInformation validate() {
		ValidationFailureInformation validationInformation = null;
		if(!tools.validate(etParentPhoneNumber1)){
			validationInformation = new ValidationFailureInformation(false, "Please enter phone number1 of primary guardian");
		} else if(!tools.validate(etSecondaryParentPhoneNumber1)) {
			if(rlSecondaryGuardian.getVisibility() == View.VISIBLE) {
				validationInformation = new ValidationFailureInformation(false, "Please enter phone number1 of secondary guardian");
			}
		} else if(rlEmergencyGuardian.getVisibility() == View.VISIBLE) {
			if(!tools.validate(etEmergencyParentPhoneNumber1)) {
				validationInformation = new ValidationFailureInformation(false, "Please enter phone number1 of emergency guardian");
			}
		}
		
		return validationInformation;
	}
}
