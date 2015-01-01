package com.ihsinformatics.ponsetti.view.Fragments.create_patient_form;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.screens.CreatePatient;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;

public class PateientPhysicalInformationFragment extends MyFragment {

	CheckBox cbHead, cbHeartOrLungs, cbUrinaryORDigestive, cbSkin, cbSpine,
			cbHips, cbUpperExtremities, cbLowerExtremities, cbNeurological,
			cbArms, cbLegs, cbOtherPartsOfBody;
	
	
	public PateientPhysicalInformationFragment() {
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		cbHead = (CheckBox) rootView.findViewById(R.id.cbHead);
		cbHeartOrLungs = (CheckBox) rootView.findViewById(R.id.cbHeartLungs);
		cbUrinaryORDigestive = (CheckBox) rootView.findViewById(R.id.cbUrinaryDigestive);
		cbSkin = (CheckBox) rootView.findViewById(R.id.cbSkin);
		cbSpine = (CheckBox) rootView.findViewById(R.id.cbSpine);
		cbHips = (CheckBox) rootView.findViewById(R.id.cbHips);
		cbUpperExtremities = (CheckBox) rootView.findViewById(R.id.cbUpperExtremities);
		cbLowerExtremities = (CheckBox) rootView.findViewById(R.id.cbLowerExtremities);
		cbNeurological = (CheckBox) rootView.findViewById(R.id.cbNeurological);
		cbArms = (CheckBox) rootView.findViewById(R.id.cbArms);
		cbLegs = (CheckBox) rootView.findViewById(R.id.cbLegs);
		cbOtherPartsOfBody = (CheckBox) rootView.findViewById(R.id.cbOtherPartsOfBody);
		rlMain = (RelativeLayout) rootView.findViewById(R.id.rlMain);
		
		
		
		checkAndFillData();
		
		return rootView;
	}
	
	
	
	@Override
	public List<Answer> getAnswers(int formId) {
		answers = new ArrayList<Answer>();
		answers.add(new Answer(addCheckBoxesAnswers(cbHead, cbHeartOrLungs, cbUrinaryORDigestive, cbSkin, cbSpine, cbHips, cbUpperExtremities, cbLowerExtremities, cbNeurological), CreatePatient.QUESTION_ANY_ABNORMALITIES, formId));
		answers.add(new Answer(addCheckBoxesAnswers(cbArms, cbLegs, cbOtherPartsOfBody), CreatePatient.QUESTION_ANY_WEAKNESS, formId));
		
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
					 if (questionId == CreatePatient.QUESTION_ANY_ABNORMALITIES) {
						 List<String> selectedValues = tools.findSelectedCheckboxesValues(ans);
						 for(String val : selectedValues) {
							 
						 
								if(cbHead.getText().toString().equals(val)) {
									cbHead.setChecked(true);
								} else if(cbHeartOrLungs.getText().toString().equals(val)) {
									cbHeartOrLungs.setChecked(true);
								} else if(cbUrinaryORDigestive.getText().toString().equals(val)) {
									cbUrinaryORDigestive.setChecked(true);
								} else if(cbSkin.getText().toString().equals(val)) {
									cbSkin.setChecked(true);
								} else if(cbSpine.getText().toString().equals(val)) {
									cbSpine.setChecked(true);
								} else if(cbHips.getText().toString().equals(val)) {
									cbHips.setChecked(true);
								} else if(cbUpperExtremities.getText().toString().equals(val)) {
									cbUpperExtremities.setChecked(true);
								} else if(cbLowerExtremities.getText().toString().equals(val)) {
									cbLowerExtremities.setChecked(true);
								} else if(cbNeurological.getText().toString().equals(val)) {
									cbNeurological.setChecked(true);
								}
						 }	

						} else if (questionId == CreatePatient.QUESTION_ANY_WEAKNESS) {
							List<String> selectedValues = tools.findSelectedCheckboxesValues(ans);
							 for(String val : selectedValues) {
								if(cbArms.getText().toString().equals(val)) {
									cbArms.setChecked(true);
								} else if(cbLegs.getText().toString().equals(val)) {
									cbLegs.setChecked(true);
								} else if(cbOtherPartsOfBody.getText().toString().equals(val)) {
									cbOtherPartsOfBody.setChecked(true);
								}
							 }

						}
				}
			}
		}
	}

	@Override
	public ValidationFailureInformation validate() {
		// No field to validate
		return null;
	}

}
