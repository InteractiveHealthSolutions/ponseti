package com.ihsinformatics.ponsetti.view.Fragments.create_patient_form;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.HospitalDAO;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.screens.CreatePatient;
import com.ihsinformatics.ponsetti.utils.DateSelector;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.utils.interfaces.ValidationInterface;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;


public class PatientGeneralInfoFragment extends MyFragment implements ValidationInterface {
	
	EditText etLastName, etFirstName, etMiddleName, etRace, etTribe, etDateOfBirth, 
		etAddress1, etAddress2, etVillage, etState, etCountry;
	RadioGroup rgGender;
	RadioButton rbMale, rbFemale;
	Spinner spHospital;
	List<String> hospitalNames;
	
	public PatientGeneralInfoFragment() {
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		etLastName = (EditText) rootView.findViewById(R.id.etSurName);
		etMiddleName = (EditText) rootView.findViewById(R.id.etMiddleName);
		etFirstName = (EditText) rootView.findViewById(R.id.etFirstName);
		rgGender = (RadioGroup) rootView.findViewById(R.id.rgSex);
		etRace = (EditText) rootView.findViewById(R.id.etRace);
		
		etTribe = (EditText) rootView.findViewById(R.id.etTribe);
		
		etAddress1 = (EditText) rootView.findViewById(R.id.etAddress1);
		etAddress2 = (EditText) rootView.findViewById(R.id.etAddress2);
		etVillage = (EditText) rootView.findViewById(R.id.etVillageTownCity);
		etState = (EditText) rootView.findViewById(R.id.etStateProvince);
		etCountry = (EditText) rootView.findViewById(R.id.etCountry);		
		etDateOfBirth = (EditText) rootView.findViewById(R.id.etDateOfBirth);
		spHospital = (Spinner) rootView.findViewById(R.id.spHospital);
		hospitalNames = new HospitalDAO().getHospitalsNames(rootView.getContext());
		// hospitalNames = new ArrayList<String>();hospitalNames.add("Indus Hospital");
		ArrayAdapter<String> hospitalsAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, hospitalNames);
		spHospital.setAdapter(hospitalsAdapter);
		
		rbMale = (RadioButton) rootView.findViewById(R.id.rbMale);
		rbFemale = (RadioButton) rootView.findViewById(R.id.rbFemale);
		
		etDateOfBirth.setFocusable(false);
		etDateOfBirth.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), DateSelector.class);
				i.putExtra(DateSelector.DATE_TYPE, DateSelector.DATE_TYPE_DATE);
				getActivity().startActivityForResult(i, CreatePatient.DATE_OF_BIRTH_REQUEST);
			}
		});
		
		
		checkAndFillData();
		return rootView;
	}
	
	public void setEtDateOfBirth(String d) {
		etDateOfBirth.setText(d);
	}
		
	@Override
	public List<Answer> getAnswers(int formId) {
		answers = new ArrayList<Answer>();
		answers.add(new Answer(tools.getViewText(spHospital), CreatePatient.QUESTION_HOSPITAL, formId));
		answers.add(new Answer(tools.getViewText(etLastName), CreatePatient.QUESTION_LAST_NAME, formId));
		answers.add(new Answer(tools.getViewText(etFirstName), CreatePatient.QUESTION_FIRST_NAME, formId));
		answers.add(new Answer(tools.getViewText(etMiddleName), CreatePatient.QUESTION_MIDDLE_NAME, formId));
		answers.add(new Answer(tools.getViewText(etRace), CreatePatient.QUESTION_RACE, formId));
		answers.add(new Answer(tools.getViewText(rgGender), CreatePatient.QUESTION_SEX, formId));
		answers.add(new Answer(tools.getViewText(etTribe), CreatePatient.QUESTION_TRIBE, formId));
		answers.add(new Answer(tools.getViewText(etDateOfBirth), CreatePatient.QUESTION_DATE_OF_BIRTH, formId));
		answers.add(new Answer(tools.getViewText(etAddress1), CreatePatient.QUESTION_ADDRESS_1, formId));
		answers.add(new Answer(tools.getViewText(etAddress2), CreatePatient.QUESTION_ADDRESS_2, formId));
		answers.add(new Answer(tools.getViewText(etVillage), CreatePatient.QUESTION_VILLAGE, formId));
		answers.add(new Answer(tools.getViewText(etState), CreatePatient.QUESTION_STATE, formId));
		answers.add(new Answer(tools.getViewText(etCountry), CreatePatient.QUESTION_COUNTRY, formId));
		
		return answers;
	}
	 
	@Override
	protected void checkAndFillData() {
		if(dataLoadRequest!= null && dataLoadRequest) {
			rlMain = (RelativeLayout) rootView.findViewById(R.id.rlMain);
			tools.freezeAllInViewGroup(rlMain);
			String ans;
			int questionId;
			for (Answer a : answers) {
				ans = a.getText();
				questionId = a.getQuestion_id();
				
				if (ans != null && !"null".equals(ans)) {
					if (questionId == CreatePatient.QUESTION_HOSPITAL) {
						spHospital.setSelection(hospitalNames.indexOf(a.getText()));

					} else if (questionId == CreatePatient.QUESTION_LAST_NAME) {
						etLastName.setText(ans);

					} else if (questionId == CreatePatient.QUESTION_MIDDLE_NAME) {
						etMiddleName.setText(ans);

					} else if (questionId == CreatePatient.QUESTION_FIRST_NAME) {
						etFirstName.setText(ans);

					} else if(questionId == CreatePatient.QUESTION_RACE) {
						etRace.setText(ans);
					} else if (questionId == CreatePatient.QUESTION_SEX) {
						if (ans.equals(getString(R.string.male))) {
							getRadioButtonByText(rgGender, getString(R.string.male)).setChecked(true);

						} else if (ans.equals(getString(R.string.female))) {
							getRadioButtonByText(rgGender, getString(R.string.female)).setChecked(true);

						}
					} else if (questionId == CreatePatient.QUESTION_TRIBE) {
						etTribe.setText(ans);
					}  else if(questionId == CreatePatient.QUESTION_DATE_OF_BIRTH) {
						etDateOfBirth.setText(ans);
					}  else if(questionId == CreatePatient.QUESTION_ADDRESS_1) {
						etAddress1.setText(ans);
					}  else if(questionId == CreatePatient.QUESTION_ADDRESS_2) {
						etAddress2.setText(ans);
					}  else if(questionId == CreatePatient.QUESTION_VILLAGE) {
						etVillage.setText(ans);
					}  else if(questionId == CreatePatient.QUESTION_STATE) {
						etState.setText(ans);
					}  else if(questionId == CreatePatient.QUESTION_COUNTRY) {
						etCountry.setText(ans);
					}
				}
			}
		}
	}

	@Override
	public void onValidationFalse(View view, View errorLabel) {
	
		
	}

	@Override
	public ValidationFailureInformation validate() {
		ValidationFailureInformation validationInformation = null;
		if(!tools.validate(spHospital)){
			validationInformation = new ValidationFailureInformation(false, "Please select a hopsital");
		} else if(!tools.validate(etLastName)) {
			validationInformation = new ValidationFailureInformation(false, "Please enter patient's last name");
		} else if(!tools.validate(etFirstName)) {
			validationInformation = new ValidationFailureInformation(false, "Please enter patient's first name");
		} else if(!tools.validate(etDateOfBirth)) {
			validationInformation = new ValidationFailureInformation(false, "Please select patient's date of birth");
		}
		
		return validationInformation;
	}
}
