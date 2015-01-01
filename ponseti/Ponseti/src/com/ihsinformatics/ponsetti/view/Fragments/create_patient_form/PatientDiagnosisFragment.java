package com.ihsinformatics.ponsetti.view.Fragments.create_patient_form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.screens.CreatePatient;
import com.ihsinformatics.ponsetti.utils.DateSelector;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;

public class PatientDiagnosisFragment extends MyFragment implements OnCheckedChangeListener {
	private LinearLayout llNumberOfPreviousTreatments, llAtPregnancyWeek, llTypeOfPreviousTreatments, llConfirmedAtBirth;
	private EditText etEvaluationDate, etNumberOfPreviuosTreatments, etAtPregnancyWeek, etDiagnosisComments;
	private Spinner spNameOfEvaluator, spFeetAffected, spDiagnosis;
	RadioGroup rgDeformityPresentAtBirth, rgAnyPreviousTreatment, rgDiagnosedParentally,
					rgConfirmedAtBirth;
	private CheckBox cbCastingAboveKnee, cbCastingBelowKnee, cbPhysiotherapy, cbUnspecified, cbOther;
	List<String> evaluatorsNames;
	List<Form> forms;
	
	public PatientDiagnosisFragment() {
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		forms = new FormDAO(rootView.getContext()).getForms(Form.COLUMN_TYPE_ID+"= '"+FormsTypes.EVALUATOR_FORM+"'");
		evaluatorsNames = new AnswerDAO(rootView.getContext()).getEvaluatorsNames(forms);
		spNameOfEvaluator = (Spinner) rootView.findViewById(R.id.spNameOfEvaluator);
		spNameOfEvaluator.setAdapter(new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_dropdown_item, evaluatorsNames));
		etEvaluationDate = (EditText) rootView.findViewById(R.id.etEvaluationDate);
		etEvaluationDate.setFocusable(false);
		etEvaluationDate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), DateSelector.class);
				i.putExtra(DateSelector.DATE_TYPE, DateSelector.DATE_TYPE_DATE);
				getActivity().startActivityForResult(i, CreatePatient.DATE_OF_EVALUATION_REQUEST);
			}
		});
		
		etNumberOfPreviuosTreatments = (EditText) rootView.findViewById(R.id.etNumberOfPreviousTreatments);
		etAtPregnancyWeek = (EditText) rootView.findViewById(R.id.etAtPregnancyWeek);
		etDiagnosisComments = (EditText) rootView.findViewById(R.id.etDiagnisisComments);
		
		spFeetAffected = (Spinner) rootView.findViewById(R.id.spFeetAffected);
		spDiagnosis = (Spinner) rootView.findViewById(R.id.spDiagnosis);
		rgDeformityPresentAtBirth = (RadioGroup) rootView.findViewById(R.id.rgDeformityPresentAtBirth);
		rgAnyPreviousTreatment = (RadioGroup) rootView.findViewById(R.id.rgAnyPreviousTreatment);
		rgDiagnosedParentally = (RadioGroup) rootView.findViewById(R.id.rgDiagnosedPrenatally);
		rgConfirmedAtBirth = (RadioGroup) rootView.findViewById(R.id.rgConfirmedAtBirth);
		
		cbCastingAboveKnee = (CheckBox) rootView.findViewById(R.id.cbCastingAboveKnee);
		cbCastingBelowKnee = (CheckBox) rootView.findViewById(R.id.cbCastingBelowKnee);
		cbPhysiotherapy = (CheckBox) rootView.findViewById(R.id.cbPhysiotherapy);
		cbUnspecified = (CheckBox) rootView.findViewById(R.id.cbUnspecified);
		cbOther = (CheckBox) rootView.findViewById(R.id.cbOther);
		
		llNumberOfPreviousTreatments = (LinearLayout) rootView.findViewById(R.id.llNumberOfPreviousTreatments);
		llAtPregnancyWeek = (LinearLayout) rootView.findViewById(R.id.llAtPregnancyWeek);
		llTypeOfPreviousTreatments = (LinearLayout) rootView.findViewById(R.id.llTypeOfPreviousTreatment);
		llConfirmedAtBirth = (LinearLayout) rootView.findViewById(R.id.llConfirmedAtBirth);
		
		rgAnyPreviousTreatment.setOnCheckedChangeListener(this);
		rgDiagnosedParentally.setOnCheckedChangeListener(this);
		
		checkAndFillData();
		
		return rootView;
	}
	
	@Override
	public List<Answer> getAnswers(int formId) {
		answers = new ArrayList<Answer>();
		answers.add(new Answer(forms.get(evaluatorsNames.indexOf(tools.getViewText(spNameOfEvaluator))).getIcrId(), CreatePatient.QUESTION_NAME_OF_EVALUATOR, formId));
		answers.add(new Answer(tools.getViewText(etEvaluationDate), CreatePatient.QUESTION_EVALUATION_DATE, formId));
		
		answers.add(new Answer(tools.getViewText(spFeetAffected), CreatePatient.QUESTION_FEET_AFFECTED, formId));
		answers.add(new Answer(tools.getViewText(spDiagnosis), CreatePatient.QUESTION_DIAGNOSIS, formId));
		answers.add(new Answer(tools.getViewText(rgDeformityPresentAtBirth), CreatePatient.QUESTION_DEFORMITY_PRESENT_AT_BIRTH, formId));
		answers.add(new Answer(tools.getViewText(rgAnyPreviousTreatment), CreatePatient.QUESTION_ANY_PREVIOUS_TREATMENT, formId));
		answers.add(new Answer(tools.getViewText(etNumberOfPreviuosTreatments), CreatePatient.QUESTION_HOW_MANY_PREVIOUS_TREATMENTS, formId));
		
		answers.add(new Answer(addCheckBoxesAnswers(cbCastingAboveKnee, cbCastingBelowKnee, cbPhysiotherapy, cbUnspecified, cbOther),
				CreatePatient.QUESTION_TYPE_OF_PREVIOUS_TREATMENTS, formId));
		
		answers.add(new Answer(tools.getViewText(rgDiagnosedParentally), CreatePatient.QUESTION_DIAGNOSED_PARENTALLY, formId));
		answers.add(new Answer(tools.getViewText(etAtPregnancyWeek), CreatePatient.QUESTION_AT_PREGNANCY_WEEK, formId));
		answers.add(new Answer(tools.getViewText(rgConfirmedAtBirth), CreatePatient.QUESTION_CONFIRMED_AT_BIRTH, formId));
		answers.add(new Answer(tools.getViewText(etDiagnosisComments), CreatePatient.QUESTION_DIAGNOSIS_COMMENTS, formId));
		
		return answers;
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
					if (questionId == CreatePatient.QUESTION_NAME_OF_EVALUATOR) {
						spNameOfEvaluator.setSelection(evaluatorsNames.indexOf(a.getText()));

					} else if (questionId == CreatePatient.QUESTION_EVALUATION_DATE) {
						etEvaluationDate.setText(ans);

					} else if (questionId == CreatePatient.QUESTION_FEET_AFFECTED) {
						List<String> array = Arrays.asList(getResources().getStringArray(R.array.left_right_both)) ;
						spFeetAffected.setSelection(array.indexOf(a.getText()));

					} else if (questionId == CreatePatient.QUESTION_DIAGNOSIS) {
						List<String> array = Arrays.asList(getResources().getStringArray(R.array.diagnosis)) ;
						spDiagnosis.setSelection(array.indexOf(a.getText()));

					} else if (questionId == CreatePatient.QUESTION_DEFORMITY_PRESENT_AT_BIRTH) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgDeformityPresentAtBirth, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgDeformityPresentAtBirth, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgDeformityPresentAtBirth, getString(R.string.unspecified)).setChecked(true);
	
						}

					} else if (questionId == CreatePatient.QUESTION_EVALUATION_DATE) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgAnyPreviousTreatment, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgAnyPreviousTreatment, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgAnyPreviousTreatment, getString(R.string.unspecified)).setChecked(true);
	
						}

					} else if (questionId == CreatePatient.QUESTION_HOW_MANY_PREVIOUS_TREATMENTS) {
						etNumberOfPreviuosTreatments.setText(ans);

					} else if (questionId == CreatePatient.QUESTION_TYPE_OF_PREVIOUS_TREATMENTS) {
						List<String> selectedValues = tools.findSelectedCheckboxesValues(ans);
						 for(String val : selectedValues) {
							if(cbCastingAboveKnee.getText().toString().equals(val)) {
								cbCastingAboveKnee.setChecked(true);
							} else if(cbCastingBelowKnee.getText().toString().equals(val)) {
								cbCastingBelowKnee.setChecked(true);
							} else if(cbPhysiotherapy.getText().toString().equals(val)) {
								cbPhysiotherapy.setChecked(true);
							} else if(cbOther.getText().toString().equals(val)) {
								cbOther.setChecked(true);
							} else if(cbUnspecified.getText().toString().equals(val)) {
								cbUnspecified.setChecked(true);
							}
						 }
						

					} else if (questionId == CreatePatient.QUESTION_DIAGNOSED_PARENTALLY) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgDiagnosedParentally, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgDiagnosedParentally, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgDiagnosedParentally, getString(R.string.unspecified)).setChecked(true);
	
						}

					} else if (questionId == CreatePatient.QUESTION_CONFIRMED_AT_BIRTH) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgConfirmedAtBirth, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgConfirmedAtBirth, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgConfirmedAtBirth, getString(R.string.unspecified)).setChecked(true);
	
						}

					} else if (questionId == CreatePatient.QUESTION_DIAGNOSIS_COMMENTS) {
						etDiagnosisComments.setText(ans);

					}
				}
			}
		}
	}

	@Override
	public ValidationFailureInformation validate() {
		ValidationFailureInformation validationInformation = null;
		if(!tools.validate(spNameOfEvaluator)){
			validationInformation = new ValidationFailureInformation(false, "Please select an evaluator");
		} else if(!tools.validate(etEvaluationDate)) {
			validationInformation = new ValidationFailureInformation(false, "Please select evaluation date");
		}
		
		return validationInformation;
	}

	public void setEtDateOfEvaluation(String date) {
		etEvaluationDate.setText(date);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton rb = ((RadioButton)rootView.findViewById(group.getCheckedRadioButtonId()));
		if(group == rgAnyPreviousTreatment) {
			if(getString(R.string.yes).equals(rb.getText())) {
				llNumberOfPreviousTreatments.setVisibility(View.VISIBLE);
				llTypeOfPreviousTreatments.setVisibility(View.VISIBLE);
			} else {
				llNumberOfPreviousTreatments.setVisibility(View.GONE);
				llTypeOfPreviousTreatments.setVisibility(View.GONE);
			}
		} else if(group == rgDiagnosedParentally) {
			if(getString(R.string.yes).equals(rb.getText())) {
				llAtPregnancyWeek.setVisibility(View.VISIBLE);
				llConfirmedAtBirth.setVisibility(View.VISIBLE);
			} else {
				llAtPregnancyWeek.setVisibility(View.GONE);
				llConfirmedAtBirth.setVisibility(View.GONE);
			}
		}
		
	}
}
