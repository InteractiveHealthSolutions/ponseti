package com.ihsinformatics.ponsetti.view.Fragments.create_patient_form;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.screens.CreatePatient;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;

public class PatientReferalInformationFragment extends MyFragment implements OnItemSelectedListener {

	Spinner spReferalSource;
	LinearLayout llDoctorName, llClinicName, llPleaseSpecify;
	
	EditText etDoctorName, etClnicName, etPleaseSpecify;
	
	
	public PatientReferalInformationFragment() {
		super();

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		spReferalSource = (Spinner) rootView.findViewById(R.id.spReferalSource);
		
		etDoctorName = (EditText) rootView.findViewById(R.id.etDoctorName);
		etClnicName = (EditText) rootView.findViewById(R.id.etClinicName);
		etPleaseSpecify = (EditText) rootView.findViewById(R.id.etPleaseSpecify);
		
		llDoctorName = (LinearLayout) rootView.findViewById(R.id.llDoctorName);
		llClinicName = (LinearLayout) rootView.findViewById(R.id.llClinicName);
		llPleaseSpecify = (LinearLayout) rootView.findViewById(R.id.llPleaseSpecify);
		
		spReferalSource.setOnItemSelectedListener(this);
		
		checkAndFillData();
		
		return rootView;
	}
	
	@Override
	public List<Answer> getAnswers(int formId) {
		answers = new ArrayList<Answer>();
		answers.add(new Answer(tools.getViewText(spReferalSource), CreatePatient.QUESTION_REFERAL_SOURCE, formId));
		answers.add(new Answer(tools.getViewText(etDoctorName), CreatePatient.QUESTION_DOCTOR_NAME, formId));
		answers.add(new Answer(tools.getViewText(etClnicName), CreatePatient.QUESTION_HOSPITAL_OR_CLINIC_NAME, formId));
		answers.add(new Answer(tools.getViewText(etPleaseSpecify), CreatePatient.QUESTION_PLEASE_SPECIFY, formId));
		
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
					if (questionId == CreatePatient.QUESTION_REFERAL_SOURCE) {
						tools.setSelectedValueToSpinner(spReferalSource, getResources().getStringArray(R.array.referal_source), ans);
					} else if (questionId == CreatePatient.QUESTION_DOCTOR_NAME) {
						etDoctorName.setText(ans);
					} else if (questionId == CreatePatient.QUESTION_HOSPITAL_OR_CLINIC_NAME) {
						etClnicName.setText(ans);
					} else if (questionId == CreatePatient.QUESTION_PLEASE_SPECIFY) {
						etPleaseSpecify.setText(ans);
					}
				}
			}
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(arg0 == spReferalSource) {
			if("Hospital/ clinic".equals(arg0.getItemAtPosition(arg2))) {
				llClinicName.setVisibility(View.VISIBLE);
				llDoctorName.setVisibility(View.VISIBLE);
				llPleaseSpecify.setVisibility(View.GONE);
			} else if("Other".equals(arg0.getItemAtPosition(arg2))) {
				llClinicName.setVisibility(View.GONE);
				llDoctorName.setVisibility(View.GONE);
				llPleaseSpecify.setVisibility(View.VISIBLE);
			} else {
				llClinicName.setVisibility(View.GONE);
				llDoctorName.setVisibility(View.GONE);
				llPleaseSpecify.setVisibility(View.GONE);
			}
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		
	}

	@Override
	public ValidationFailureInformation validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
