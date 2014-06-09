package com.ihsinformatics.ponsetti.view.Fragments.add_visit_form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.screens.AddVisit;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;

public class VisitRightFootFragment extends MyFragment implements OnItemSelectedListener {

	private Spinner spVarus, spCavus, spTreatment, spBraceCompilance;
	
	private EditText etAbductus, etEnquinus, etCaster, etCastNumber,
			etDegreesAbduction, etDegreesDorsiflexion, etProblems,
			etActionTaken, etGiveDetails, etSurgeryComments, etOtherSurgeryType;
	
	private RadioGroup rgPosteriorCrease, rgEmptyHeel, rgRigidEnquinus, rgMedialCrease,
			rgTalarHeadCoverage, rgCurvedLateralBorder;
	
	private LinearLayout llCaster, llCastNumber, llDegreesAbduction,
			llDegreesDorsiflexion, llBraceCompilance, llProblems, llOtherSurgeryType,
			llActionTAken, llGiveDetails, llSurgeryType, llSurgeryComments;
	
	private CheckBox cbPosterioRelease, cbMedialRelease, cbSubatalarRelease,
			cbPlantarRelease, cbAchillesLengthening, cbTendonTransfers,
			cbOsteotomies, cbArthrodesis, cbTalectomy, cbOther;
	
	private List<View> treatmentHideAbleViews;
	
	public VisitRightFootFragment() {
		super();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		super.onCreateView(inflater, container, savedInstanceState);
		
		spVarus = (Spinner) rootView.findViewById(R.id.spVarus); 
		spCavus = (Spinner) rootView.findViewById(R.id.spCavus);
		spTreatment = (Spinner) rootView.findViewById(R.id.spTreatment);
		spTreatment.setOnItemSelectedListener(this);
		spBraceCompilance = (Spinner) rootView.findViewById(R.id.spBraceCompliance);
		
		etAbductus = (EditText) rootView.findViewById(R.id.etAbductus);
		etEnquinus = (EditText) rootView.findViewById(R.id.etEnquinus);
		etCaster = (EditText) rootView.findViewById(R.id.etCaster);
		etCastNumber = (EditText) rootView.findViewById(R.id.etCastNumber);
		etDegreesAbduction = (EditText) rootView.findViewById(R.id.etDegreesAbduction);
		etDegreesDorsiflexion = (EditText) rootView.findViewById(R.id.etDegreesDorsiflexion);
		etProblems = (EditText) rootView.findViewById(R.id.etProblems);
		etActionTaken = (EditText) rootView.findViewById(R.id.etActionTaken);
		etSurgeryComments = (EditText) rootView.findViewById(R.id.etSurgeryComments);
		etOtherSurgeryType = (EditText) rootView.findViewById(R.id.etOtherSurgeryType);
		etGiveDetails = (EditText) rootView.findViewById(R.id.etGiveDetails);
		
		rgPosteriorCrease = (RadioGroup) rootView.findViewById(R.id.rgPosteriorCrease);
		rgEmptyHeel = (RadioGroup) rootView.findViewById(R.id.rgEmptyHeel);
		rgRigidEnquinus = (RadioGroup) rootView.findViewById(R.id.rgRigidEnquinus);
		rgMedialCrease = (RadioGroup) rootView.findViewById(R.id.rgMedialCrease);
		rgTalarHeadCoverage = (RadioGroup) rootView.findViewById(R.id.rgTalarHeadCoverage);
		rgCurvedLateralBorder = (RadioGroup) rootView.findViewById(R.id.rgCurvedLateralBorder);
		
		cbAchillesLengthening = (CheckBox) rootView.findViewById(R.id.cbAchillesLengthening);
		cbArthrodesis = (CheckBox) rootView.findViewById(R.id.cbArthrodesis);
		cbMedialRelease = (CheckBox) rootView.findViewById(R.id.cbMedialRelease);
		cbOsteotomies = (CheckBox) rootView.findViewById(R.id.cbOsteotomies);
		cbPlantarRelease = (CheckBox) rootView.findViewById(R.id.cbPlantarRelease);
		cbPosterioRelease = (CheckBox) rootView.findViewById(R.id.cbPosterialRelease);
		cbSubatalarRelease = (CheckBox) rootView.findViewById(R.id.cbSubatalarRelease);
		cbTalectomy = (CheckBox) rootView.findViewById(R.id.cbTalectomy);
		cbTendonTransfers = (CheckBox) rootView.findViewById(R.id.cbTendonTransfers);
		cbOther = (CheckBox) rootView.findViewById(R.id.cbOther);
		
		cbOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked) {
					tools.manageViewsVisibility(treatmentHideAbleViews, llSurgeryType, llOtherSurgeryType, llSurgeryComments);
				}
				
			}
		});
		
		llCaster = (LinearLayout) rootView.findViewById(R.id.llCaster);
		llCastNumber = (LinearLayout) rootView.findViewById(R.id.llCastNumber);
		llDegreesAbduction = (LinearLayout) rootView.findViewById(R.id.llDegreesAbduction);
		llDegreesDorsiflexion = (LinearLayout) rootView.findViewById(R.id.llDegreesDorsiflexion);
		llBraceCompilance = (LinearLayout) rootView.findViewById(R.id.llBraceCompliance);
		llProblems = (LinearLayout) rootView.findViewById(R.id.llProblems);
		llActionTAken = (LinearLayout) rootView.findViewById(R.id.llActionTaken);
		llSurgeryType = (LinearLayout) rootView.findViewById(R.id.llSurgeryType);
		llSurgeryComments = (LinearLayout) rootView.findViewById(R.id.llSurgeryComments);
		llGiveDetails = (LinearLayout) rootView.findViewById(R.id.llGiveDetails);
		llOtherSurgeryType = (LinearLayout) rootView.findViewById(R.id.llOtherSurgeryType);
		
		treatmentHideAbleViews = new ArrayList<View>();
		treatmentHideAbleViews.add(llCaster);
		treatmentHideAbleViews.add(llCastNumber);
		treatmentHideAbleViews.add(llDegreesAbduction);
		treatmentHideAbleViews.add(llDegreesDorsiflexion);
		treatmentHideAbleViews.add(llBraceCompilance);
		treatmentHideAbleViews.add(llProblems);
		treatmentHideAbleViews.add(llActionTAken);
		treatmentHideAbleViews.add(llSurgeryType);
		treatmentHideAbleViews.add(llSurgeryComments);
		treatmentHideAbleViews.add(llGiveDetails);
		treatmentHideAbleViews.add(llOtherSurgeryType);
		
		checkAndFillData();
		return rootView;
	}
	
	@Override
	public List<Answer> getAnswers(int formId) {
		answers = new ArrayList<Answer>();
		answers.add(new Answer(tools.getViewText(spVarus), AddVisit.QUESTION_RIGHT_VARUS, formId));
		answers.add(new Answer(tools.getViewText(spCavus), AddVisit.QUESTION_RIGHT_CAVUS, formId));
		answers.add(new Answer(tools.getViewText(spTreatment), AddVisit.QUESTION_RIGHT_TREATMENT, formId));
		answers.add(new Answer(tools.getViewText(spBraceCompilance), AddVisit.QUESTION_RIGHT_BRACE_COMPILANCE, formId));
		answers.add(new Answer(tools.getViewText(etAbductus), AddVisit.QUESTION_RIGHT_ABDUCTUS, formId));
		answers.add(new Answer(tools.getViewText(etEnquinus), AddVisit.QUESTION_RIGHT_ENQUINUS, formId));
		answers.add(new Answer(tools.getViewText(etCaster), AddVisit.QUESTION_RIGHT_CASTER, formId));
		answers.add(new Answer(tools.getViewText(etCastNumber), AddVisit.QUESTION_RIGHT_CAST_NUMBER, formId));
		answers.add(new Answer(tools.getViewText(etDegreesAbduction), AddVisit.QUESTION_RIGHT_DEGREES_ABDUCTION, formId));
		answers.add(new Answer(tools.getViewText(etDegreesDorsiflexion), AddVisit.QUESTION_RIGHT_DEGREES_DORSIFLEXION, formId));
		answers.add(new Answer(tools.getViewText(etProblems), AddVisit.QUESTION_RIGHT_PROBLEMS, formId));
		answers.add(new Answer(tools.getViewText(etActionTaken), AddVisit.QUESTION_RIGHT_ACTION_TAKEN, formId));
		answers.add(new Answer(tools.getViewText(etSurgeryComments), AddVisit.QUESTION_RIGHT_SURGERY_COMMENTS, formId));
		answers.add(new Answer(tools.getViewText(etGiveDetails), AddVisit.QUESTION_RIGHT_GIVE_DETAILS, formId));
		answers.add(new Answer(tools.getViewText(rgPosteriorCrease), AddVisit.QUESTION_RIGHT_POSTERIOR_CREASE, formId));
		answers.add(new Answer(tools.getViewText(rgEmptyHeel), AddVisit.QUESTION_RIGHT_EMPTY_HEEL, formId));
		answers.add(new Answer(tools.getViewText(rgRigidEnquinus), AddVisit.QUESTION_RIGHT_RIGID_ENQUINUS, formId));
		answers.add(new Answer(tools.getViewText(rgMedialCrease), AddVisit.QUESTION_RIGHT_MEDIAL_CREASE, formId));
		answers.add(new Answer(tools.getViewText(rgTalarHeadCoverage), AddVisit.QUESTION_RIGHT_TALAR_HEAD_COVERAGE, formId));
		answers.add(new Answer(tools.getViewText(rgCurvedLateralBorder), AddVisit.QUESTION_RIGHT_CURVED_LATERAL_BORDER, formId));
		answers.add(new Answer(tools.getViewText(etOtherSurgeryType), AddVisit.QUESTION_RIGHT_OTHER_SURGERY_TYPE, formId));
		
		addCheckBoxesAnswers(AddVisit.QUESTION_RIGHT_SURGERY_TYPE, formId, cbPosterioRelease, cbMedialRelease, cbSubatalarRelease,
				cbPlantarRelease, cbAchillesLengthening, cbTendonTransfers,
				cbOsteotomies, cbArthrodesis, cbTalectomy, cbOther);
		
		
		float ls, hfcs, mfcs;
		hfcs = tools.calculateFCS(tools.getViewText(rgPosteriorCrease), tools.getViewText(rgEmptyHeel), tools.getViewText(rgRigidEnquinus));
		mfcs = tools.calculateFCS(tools.getViewText(rgMedialCrease), tools.getViewText(rgTalarHeadCoverage), tools.getViewText(rgCurvedLateralBorder));
		ls = mfcs+hfcs;
		answers.add(new Answer(hfcs+"", AddVisit.QUESTION_RIGHT_HFCS, formId));
		answers.add(new Answer(mfcs+"", AddVisit.QUESTION_RIGHT_MFCS, formId));
		answers.add(new Answer(ls+"", AddVisit.QUESTION_RIGHT_TS, formId));
		
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
					if (questionId == AddVisit.QUESTION_RIGHT_VARUS) {
						List<String> array = Arrays.asList(getResources().getStringArray(R.array.varus)) ;
						spVarus.setSelection(array.indexOf(ans));

					} else if (questionId == AddVisit.QUESTION_RIGHT_CAVUS) {
						List<String> array = Arrays.asList(getResources().getStringArray(R.array.cavus)) ;
						spCavus.setSelection(array.indexOf(ans));

					} else if (questionId == AddVisit.QUESTION_RIGHT_ABDUCTUS) {
						etAbductus.setText(ans);

					} else if (questionId == AddVisit.QUESTION_RIGHT_ENQUINUS) {
						etEnquinus.setText(ans);

					} else if (questionId == AddVisit.QUESTION_RIGHT_POSTERIOR_CREASE) {
						if (ans.equals("1")) {
							getRadioButtonByText(rgPosteriorCrease, "1").setChecked(true);
	
						} else if (ans.equals("0.5")) {
							getRadioButtonByText(rgPosteriorCrease, "0.5").setChecked(true);
	
						} else if (ans.equals("0")) {
							getRadioButtonByText(rgPosteriorCrease,"0").setChecked(true);
	
						}

					} else if (questionId == AddVisit.QUESTION_RIGHT_EMPTY_HEEL) {
						if (ans.equals("1")) {
							getRadioButtonByText(rgEmptyHeel, "1").setChecked(true);
	
						} else if (ans.equals("0.5")) {
							getRadioButtonByText(rgEmptyHeel, "0.5").setChecked(true);
	
						} else if (ans.equals("0")) {
							getRadioButtonByText(rgEmptyHeel,"0").setChecked(true);
	
						}

					} else if (questionId == AddVisit.QUESTION_RIGHT_RIGID_ENQUINUS) {
						if (ans.equals("1")) {
							getRadioButtonByText(rgRigidEnquinus, "1").setChecked(true);
	
						} else if (ans.equals("0.5")) {
							getRadioButtonByText(rgRigidEnquinus, "0.5").setChecked(true);
	
						} else if (ans.equals("0")) {
							getRadioButtonByText(rgRigidEnquinus,"0").setChecked(true);
	
						}

					} else if (questionId == AddVisit.QUESTION_RIGHT_MEDIAL_CREASE) {
						if (ans.equals("1")) {
							getRadioButtonByText(rgMedialCrease, "1").setChecked(true);
	
						} else if (ans.equals("0.5")) {
							getRadioButtonByText(rgMedialCrease, "0.5").setChecked(true);
	
						} else if (ans.equals("0")) {
							getRadioButtonByText(rgMedialCrease,"0").setChecked(true);
	
						}

					} else if (questionId == AddVisit.QUESTION_RIGHT_TALAR_HEAD_COVERAGE) {
						if (ans.equals("1")) {
							getRadioButtonByText(rgTalarHeadCoverage, "1").setChecked(true);
	
						} else if (ans.equals("0.5")) {
							getRadioButtonByText(rgTalarHeadCoverage, "0.5").setChecked(true);
	
						} else if (ans.equals("0")) {
							getRadioButtonByText(rgTalarHeadCoverage,"0").setChecked(true);
	
						}

					} else if (questionId == AddVisit.QUESTION_RIGHT_CURVED_LATERAL_BORDER) {
						if (ans.equals("1")) {
							getRadioButtonByText(rgCurvedLateralBorder, "1").setChecked(true);
	
						} else if (ans.equals("0.5")) {
							getRadioButtonByText(rgCurvedLateralBorder, "0.5").setChecked(true);
	
						} else if (ans.equals("0")) {
							getRadioButtonByText(rgCurvedLateralBorder,"0").setChecked(true);
	
						}

					} else if (questionId == AddVisit.QUESTION_RIGHT_TREATMENT) {
						List<String> array = Arrays.asList(getResources().getStringArray(R.array.treatments)) ;
						spTreatment.setSelection(array.indexOf(ans));

					} else if (questionId == AddVisit.QUESTION_RIGHT_GIVE_DETAILS) {
						etGiveDetails.setText(ans);

					} else if (questionId == AddVisit.QUESTION_RIGHT_BRACE_COMPILANCE) {
						List<String> array = Arrays.asList(getResources().getStringArray(R.array.brace_compilance)) ;
						spBraceCompilance.setSelection(array.indexOf(ans));

					} else if (questionId == AddVisit.QUESTION_RIGHT_PROBLEMS) {
						etProblems.setText(ans);

					} else if (questionId == AddVisit.QUESTION_RIGHT_ACTION_TAKEN) {
						etActionTaken.setText(ans);

					} else if (questionId == AddVisit.QUESTION_RIGHT_SURGERY_TYPE) {
						if(cbAchillesLengthening.getText().toString().equals(ans)) {
							cbAchillesLengthening.setChecked(true);
						} else if(cbArthrodesis.getText().toString().equals(ans)) {
							cbArthrodesis.setChecked(true);
						} else if(cbMedialRelease.getText().toString().equals(ans)) {
							cbMedialRelease.setChecked(true);
						} else if(cbOsteotomies.getText().toString().equals(ans)) {
							cbOsteotomies.setChecked(true);
						} else if(cbOther.getText().toString().equals(ans)) {
							cbOther.setChecked(true);
						} else if(cbPlantarRelease.getText().toString().equals(ans)) {
							cbPlantarRelease.setChecked(true);
						} else if(cbPosterioRelease.getText().toString().equals(ans)) {
							cbPosterioRelease.setChecked(true);
						} else if(cbSubatalarRelease.getText().toString().equals(ans)) {
							cbSubatalarRelease.setChecked(true);
						} else if(cbTalectomy.getText().toString().equals(ans)) {
							cbTalectomy.setChecked(true);
						} else if(cbTendonTransfers.getText().toString().equals(ans)) {
							cbTendonTransfers.setChecked(true);
						}

					} else if (questionId == AddVisit.QUESTION_RIGHT_DEGREES_ABDUCTION) {
						etDegreesAbduction.setText(ans);

					} else if (questionId == AddVisit.QUESTION_RIGHT_DEGREES_DORSIFLEXION) {
						etDegreesDorsiflexion.setText(ans);

					} else if (questionId == AddVisit.QUESTION_RIGHT_CASTER) {
						etCaster.setText(ans);

					} else if (questionId == AddVisit.QUESTION_RIGHT_CAST_NUMBER) {
						etCastNumber.setText(ans);

					}
				}
			}
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		if(arg0 == spTreatment) {
			if("C - manipulation & Casting".equals(arg0.getItemAtPosition(arg2))) {
				tools.manageViewsVisibility(treatmentHideAbleViews, llCaster, llCastNumber);
			} else if("T - Tenotomy".equals(arg0.getItemAtPosition(arg2))) {
				tools.manageViewsVisibility(treatmentHideAbleViews, llDegreesAbduction, llDegreesDorsiflexion);
			} else if("B - Brace application".equals(arg0.getItemAtPosition(arg2))) {
				tools.manageViewsVisibility(treatmentHideAbleViews, llBraceCompilance, llProblems, llActionTAken);
			} else if("R - Refer".equals(arg0.getItemAtPosition(arg2))) {
				tools.manageViewsVisibility(treatmentHideAbleViews);
			} else if("S - Surgery".equals(arg0.getItemAtPosition(arg2))) {
				tools.manageViewsVisibility(treatmentHideAbleViews, llSurgeryType, llSurgeryComments);
			} else if("O - Other".equals(arg0.getItemAtPosition(arg2))) {
				tools.manageViewsVisibility(treatmentHideAbleViews, llGiveDetails);
			}
		}
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	
	@Override
	public ValidationFailureInformation validate() {

		ValidationFailureInformation validationInformation = null;
		if(!tools.validate(rgPosteriorCrease)){
			validationInformation = new ValidationFailureInformation(false, "Please select Posterior Crease for right foor");
		} else if(!tools.validate(rgEmptyHeel)) {
			validationInformation = new ValidationFailureInformation(false, "Please select Empty Heel for right foor");
		} else if(!tools.validate(rgRigidEnquinus)) {
			validationInformation = new ValidationFailureInformation(false, "Please select Rigid Enquinus for right foor");
		} else if(!tools.validate(spTreatment)) {
			validationInformation = new ValidationFailureInformation(false, "Please select a treatment for right foot");
		} else if(llSurgeryType.getVisibility() == View.VISIBLE) {
			if(!tools.validate(cbPosterioRelease, cbMedialRelease, cbSubatalarRelease,
					cbPlantarRelease, cbAchillesLengthening, cbTendonTransfers,
					cbOsteotomies, cbArthrodesis, cbTalectomy, cbOther)) {
				validationInformation = new ValidationFailureInformation(false, "Please select surgery type");
			}
		} else if(llOtherSurgeryType.getVisibility() == View.VISIBLE) {
			if(!tools.validate(etOtherSurgeryType)) {
				validationInformation = new ValidationFailureInformation(false, "Please select a treatment for right foot");
			}
		}
		
		return validationInformation;
	}
}
