package com.ihsinformatics.ponsetti.view.Fragments.add_visit_form;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.data_access.HospitalDAO;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.screens.AddVisit;
import com.ihsinformatics.ponsetti.screens.CreatePatient;
import com.ihsinformatics.ponsetti.utils.DateSelector;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;

public class VisitGeneralInfoFragment extends MyFragment implements OnCheckedChangeListener, OnClickListener {

	Spinner spHospital, spNameOfEvaluator;
	EditText etDateOfVisit, etDateOfNextVisit;
	RadioGroup rgDidPatientExpeirneceRelapse, rgIsThisPatientsFinalTreatmentVisit;
	LinearLayout llDateOfNextVisit;
	List<String> hospitalsNames, evaluatorsNames;
	List<Form> forms;
	public VisitGeneralInfoFragment() {
		super();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		hospitalsNames = new HospitalDAO().getHospitalsNames(rootView.getContext());
		// hospitalsNames = new ArrayList<String>();hospitalsNames.add("Indus Hospital");
		
		forms = new FormDAO(rootView.getContext()).getForms(FormsTypes.EVALUATOR_FORM);
		evaluatorsNames = new AnswerDAO(rootView.getContext()).getEvaluatorsNames(forms);
		
		spHospital = (Spinner) rootView.findViewById(R.id.spHospital);
		spHospital.setAdapter(new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, hospitalsNames));
		
		spNameOfEvaluator = (Spinner) rootView.findViewById(R.id.spNameOfEvaluator);
		spNameOfEvaluator.setAdapter(new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, evaluatorsNames));
		
		llDateOfNextVisit = (LinearLayout) rootView.findViewById(R.id.llDateOfNextVisit); 
		
		rgDidPatientExpeirneceRelapse = (RadioGroup) rootView.findViewById(R.id.rgDidPatientExpeirneceRelapse);
		rgIsThisPatientsFinalTreatmentVisit = (RadioGroup) rootView.findViewById(R.id.rgIsThisFinalTreatmentVisit);
		rgIsThisPatientsFinalTreatmentVisit.setOnCheckedChangeListener(this);
		etDateOfVisit = (EditText) rootView.findViewById(R.id.etDateOfVisit);
		etDateOfNextVisit = (EditText) rootView.findViewById(R.id.etDateOfNextVisit);
		etDateOfVisit.setOnClickListener(this);
		etDateOfNextVisit.setOnClickListener(this);
		
		checkAndFillData();
		return rootView;
	}
	
	@Override
	public List<Answer> getAnswers(int formId) {
		List<Answer> answers = new ArrayList<Answer>();
		answers.add(new Answer(tools.getViewText(spHospital), AddVisit.QUESTION_HOSPITAL_OR_CLINIC, formId));
		answers.add(new Answer(forms.get(evaluatorsNames.indexOf(tools.getViewText(spNameOfEvaluator))).getIcrId(), AddVisit.QUESTION_NAME_OF_EVALUATOR, formId));
		answers.add(new Answer(tools.getViewText(rgDidPatientExpeirneceRelapse), AddVisit.QUESTION_DID_EXPERIENCE_RELAPSE, formId));
		answers.add(new Answer(tools.getViewText(rgIsThisPatientsFinalTreatmentVisit), AddVisit.QUESTION_IS_FINAL_VISIT, formId));
		answers.add(new Answer(tools.getViewText(etDateOfVisit), AddVisit.QUESTION_DATE_OF_VISIT, formId));
		answers.add(new Answer(tools.getViewText(etDateOfNextVisit), AddVisit.QUESTION_DATE_OF_NEXT_VISIT, formId));
		
		return answers;
	}
	
	public void setEtDateOfVisit(String dateOfVisit) {
		etDateOfVisit.setText(dateOfVisit);
	}
	public void setEtDateOfNextVisit(String dateOfNextVisit) {
		etDateOfNextVisit.setText(dateOfNextVisit);
	}
	
	@Override
	protected void checkAndFillData() {
		if(dataLoadRequest!= null && dataLoadRequest) {
			String ans;
			int questionId;
			for (Answer a : answers) {
				ans = a.getText();
				questionId = a.getQuestion_id();
				
				if (ans != null && !"null".equals(ans)) {
					if (questionId == AddVisit.QUESTION_HOSPITAL_OR_CLINIC) {
						spHospital.setSelection(hospitalsNames.indexOf(a.getText()));

					} else if (questionId == AddVisit.QUESTION_NAME_OF_EVALUATOR) {
						spNameOfEvaluator.setSelection(evaluatorsNames.indexOf(a.getText()));

					} else if (questionId == AddVisit.QUESTION_DID_EXPERIENCE_RELAPSE) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgDidPatientExpeirneceRelapse, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgDidPatientExpeirneceRelapse, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgDidPatientExpeirneceRelapse, getString(R.string.unspecified)).setChecked(true);
	
						}

					} else if (questionId == AddVisit.QUESTION_IS_FINAL_VISIT) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgIsThisPatientsFinalTreatmentVisit, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgIsThisPatientsFinalTreatmentVisit, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgIsThisPatientsFinalTreatmentVisit, getString(R.string.unspecified)).setChecked(true);
	
						}

					} else if (questionId == AddVisit.QUESTION_DATE_OF_VISIT) {
						etDateOfVisit.setText(a.getText());

					} else if (questionId == AddVisit.QUESTION_DATE_OF_NEXT_VISIT) {
						etDateOfNextVisit.setText(a.getText());

					}
				}
			}
		}
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(group == rgIsThisPatientsFinalTreatmentVisit) {
			if("No".equals(((RadioButton)rootView.findViewById(group.getCheckedRadioButtonId())).getText().toString())) {
				llDateOfNextVisit.setVisibility(View.VISIBLE);
			} else {
				llDateOfNextVisit.setVisibility(View.GONE);
			}
		}
		
	}

	@Override
	public ValidationFailureInformation validate() {
		ValidationFailureInformation validationInformation = null;
		if(!tools.validate(spHospital)){
			validationInformation = new ValidationFailureInformation(false, "Please select a hospital");
		} else if(!tools.validate(spNameOfEvaluator)) {
			validationInformation = new ValidationFailureInformation(false, "Please select an evaluator");
		} else if(!tools.validate(etDateOfVisit)) {
			validationInformation = new ValidationFailureInformation(false, "Please give date of visit");
		}
		
		return validationInformation;
	}

	 
	@Override
	public void onClick(View v) {
		Intent i = new Intent(getActivity(), DateSelector.class);
		i.putExtra(DateSelector.DATE_TYPE, DateSelector.DATE_TYPE_DATE);
		if(v==etDateOfVisit) {
			getActivity().startActivityForResult(i, AddVisit.DATE_OF_VISIT);
			
		} else if(v == etDateOfNextVisit) {
			getActivity().startActivityForResult(i, AddVisit.DATE_OF_NEXT_VISIT);
			
		}
		
	}
}
