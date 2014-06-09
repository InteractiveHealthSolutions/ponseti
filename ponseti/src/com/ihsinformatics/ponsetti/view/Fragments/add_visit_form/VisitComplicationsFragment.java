package com.ihsinformatics.ponsetti.view.Fragments.add_visit_form;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.screens.AddVisit;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;

public class VisitComplicationsFragment extends MyFragment implements OnCheckedChangeListener {

	
	EditText etDescriptions, etTreatments, etTreatmentResults, etGeneralComments;
	RadioGroup rgComplications;
	LinearLayout llComplicationsDetails;
	
	public VisitComplicationsFragment() {
		super();
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		llComplicationsDetails = (LinearLayout) rootView.findViewById(R.id.llComplicationsDetails); 
		rgComplications = (RadioGroup) rootView.findViewById(R.id.rgComplications);
		rgComplications.setOnCheckedChangeListener(this);
		etDescriptions = (EditText) rootView.findViewById(R.id.etDescription);
		etTreatments = (EditText) rootView.findViewById(R.id.etTreatment);
		etTreatmentResults = (EditText) rootView.findViewById(R.id.etTreatmentResults);
		etGeneralComments = (EditText) rootView.findViewById(R.id.etGeneralComments);
		
		checkAndFillData();
		return rootView;
	}
	
	@Override
	public List<Answer> getAnswers(int formId) {
		List<Answer> answers = new ArrayList<Answer>();
		answers.add(new Answer(tools.getViewText(rgComplications), AddVisit.QUESTION_COMPLICATIONS, formId));
		answers.add(new Answer(tools.getViewText(etDescriptions), AddVisit.QUESTION_DESCRIPION_OF_COMPLICATIONS, formId));
		answers.add(new Answer(tools.getViewText(etTreatments), AddVisit.QUESTION_TREATMENT_OF_COMPLICATIONS, formId));
		answers.add(new Answer(tools.getViewText(etTreatmentResults), AddVisit.QUESTION_RESULTS_AFTER_TREATMENT, formId));
		answers.add(new Answer(tools.getViewText(etGeneralComments), AddVisit.QUESTION_GENERAL_COMMENTS, formId));
		
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
				
				if (questionId == AddVisit.QUESTION_COMPLICATIONS) {
					if (ans.equals(getString(R.string.yes))) {
						getRadioButtonByText(rgComplications, getString(R.string.yes)).setChecked(true);
		
					} else if (ans.equals(getString(R.string.no))) {
						getRadioButtonByText(rgComplications, getString(R.string.no)).setChecked(true);
		
					} else if (ans.equals(getString(R.string.unspecified))) {
						getRadioButtonByText(rgComplications, getString(R.string.unspecified)).setChecked(true);
		
					}
				} else if (questionId == AddVisit.QUESTION_DESCRIPION_OF_COMPLICATIONS) {
					etDescriptions.setText(ans);

				} else if (questionId == AddVisit.QUESTION_TREATMENT_OF_COMPLICATIONS) {
					etTreatments.setText(ans);

				} else if (questionId == AddVisit.QUESTION_RESULTS_AFTER_TREATMENT) {
					etTreatmentResults.setText(ans);
					
				} else if (questionId == AddVisit.QUESTION_GENERAL_COMMENTS) {
					etGeneralComments.setText(ans);
					
				}
			}
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(group == rgComplications) {
			if("Yes".equals(((RadioButton)rootView.findViewById(group.getCheckedRadioButtonId())).getText().toString())) {
				llComplicationsDetails.setVisibility(View.VISIBLE);
				
			} else {
				llComplicationsDetails.setVisibility(View.GONE);
				
			}
		}
		
	}

	@Override
	public ValidationFailureInformation validate() {
		ValidationFailureInformation validationInformation = null;
		if(llComplicationsDetails.getVisibility() == View.VISIBLE) {
			if(!tools.validate(etDescriptions)){	
				validationInformation = new ValidationFailureInformation(false, "Please give complication description");
			} else if(!tools.validate(etTreatments)) {
				validationInformation = new ValidationFailureInformation(false, "Please specify treatments of the complications");
			}
		}
		
		return validationInformation;
	}
}
