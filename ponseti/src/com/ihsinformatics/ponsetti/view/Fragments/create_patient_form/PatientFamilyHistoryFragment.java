package com.ihsinformatics.ponsetti.view.Fragments.create_patient_form;

import java.util.ArrayList;
import java.util.Arrays;
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
import android.widget.Spinner;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.screens.CreatePatient;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;


public class PatientFamilyHistoryFragment extends MyFragment implements OnCheckedChangeListener {

	EditText etLengthOfPregnancy, etHowMany, etWhatWereComplications;
	RadioGroup rgDidMotherHadAnyComplicationAtPregnancy, 
	rgDidMotherConsumeAlcoholDuringPregnancy, rgDidMotherSmokeDuringPregnancy, rgAnyRelativeWithClubFoot,
	rgBirthComplications; 
	Spinner spBirthPlace;
	LinearLayout llHowMany, llWhatWereComplications;
	
	public PatientFamilyHistoryFragment() {
		super();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		rgAnyRelativeWithClubFoot = (RadioGroup) rootView.findViewById(R.id.rgAnyRelativeWithClubFoot);
		etLengthOfPregnancy = (EditText) rootView.findViewById(R.id.etLengthOfPregnancy);
		etHowMany = (EditText) rootView.findViewById(R.id.etHowMany);
		etWhatWereComplications = (EditText) rootView.findViewById(R.id.etWhatWereComplications);
		rgDidMotherHadAnyComplicationAtPregnancy = (RadioGroup) rootView.findViewById(R.id.rgDidMotherHadAnyComplicationAtPregnancy);
		rgDidMotherConsumeAlcoholDuringPregnancy = (RadioGroup) rootView.findViewById(R.id.rgDidMotherConsumeAlcoholDuringPregnancy);
		rgDidMotherSmokeDuringPregnancy = (RadioGroup) rootView.findViewById(R.id.rgDidMotherSmokeDuringPregnancy);
		rgBirthComplications = (RadioGroup) rootView.findViewById(R.id.rgBirthComplications);
		spBirthPlace = (Spinner) rootView.findViewById(R.id.spBirthPlace);
		
		llHowMany = (LinearLayout) rootView.findViewById(R.id.llHowMany);
		llWhatWereComplications = (LinearLayout) rootView.findViewById(R.id.llWhatWereComplications);
		rgAnyRelativeWithClubFoot.setOnCheckedChangeListener(this);
		rgDidMotherHadAnyComplicationAtPregnancy.setOnCheckedChangeListener(this);
		checkAndFillData();
		
		return rootView;
	}
	
	@Override
	public List<Answer> getAnswers(int formId) {
		answers = new ArrayList<Answer>();
		answers.add(new Answer(tools.getViewText(rgAnyRelativeWithClubFoot), CreatePatient.QUESTION_ANY_RELATIVES_WITH_CF, formId));
		answers.add(new Answer(tools.getViewText(etHowMany), CreatePatient.QUESTION_HOW_MANY, formId));
		answers.add(new Answer(tools.getViewText(etLengthOfPregnancy), CreatePatient.QUESTION_LENGHT_OF_PREGNANCY, formId));
		answers.add(new Answer(tools.getViewText(rgDidMotherHadAnyComplicationAtPregnancy), CreatePatient.QUESTION_MOTHER_COMPLICATION, formId));
		answers.add(new Answer(tools.getViewText(etWhatWereComplications), CreatePatient.QUESTION_WHAT_WERE_COMPLICATIONS, formId));
		answers.add(new Answer(tools.getViewText(rgDidMotherConsumeAlcoholDuringPregnancy), CreatePatient.QUESTION_MOTHER_ALCOHOL, formId));
		answers.add(new Answer(tools.getViewText(rgDidMotherSmokeDuringPregnancy), CreatePatient.QUESTION_MOTHER_SMOKE, formId));
		answers.add(new Answer(tools.getViewText(rgBirthComplications), CreatePatient.QUESTION_BIRTH_COMPLICATIONS, formId));
		answers.add(new Answer(tools.getViewText(spBirthPlace), CreatePatient.QUESTION_BIRTH_PLACE, formId));
		
		return answers;
	}
	
	@Override
	protected void checkAndFillData() {
		if(dataLoadRequest!= null && dataLoadRequest) {
			String ans;
			int questionId;
			for(Answer a: answers) {
				ans = a.getText();
				questionId = a.getQuestion_id();
				if (ans != null && !"null".equals(ans)) {
					if(questionId == CreatePatient.QUESTION_ANY_RELATIVES_WITH_CF) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgAnyRelativeWithClubFoot, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgAnyRelativeWithClubFoot, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgBirthComplications, getString(R.string.unspecified)).setChecked(true);
	
						}
						
					} else if(questionId == CreatePatient.QUESTION_LENGHT_OF_PREGNANCY) {
						etLengthOfPregnancy.setText(a.getText());
						
					} else if(questionId == CreatePatient.QUESTION_MOTHER_COMPLICATION) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgDidMotherHadAnyComplicationAtPregnancy, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgDidMotherHadAnyComplicationAtPregnancy, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgBirthComplications, getString(R.string.unspecified)).setChecked(true);
	
						}
						
					} else if(questionId == CreatePatient.QUESTION_MOTHER_ALCOHOL) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgDidMotherConsumeAlcoholDuringPregnancy, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgDidMotherConsumeAlcoholDuringPregnancy, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgBirthComplications, getString(R.string.unspecified)).setChecked(true);
	
						}
						
					} else if(questionId == CreatePatient.QUESTION_MOTHER_SMOKE) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgDidMotherSmokeDuringPregnancy, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgDidMotherSmokeDuringPregnancy, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgBirthComplications, getString(R.string.unspecified)).setChecked(true);
	
						}
						
					} else if(questionId == CreatePatient.QUESTION_BIRTH_COMPLICATIONS) {
						if (ans.equals(getString(R.string.yes))) {
							getRadioButtonByText(rgBirthComplications, getString(R.string.yes)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.no))) {
							getRadioButtonByText(rgBirthComplications, getString(R.string.no)).setChecked(true);
	
						} else if (ans.equals(getString(R.string.unspecified))) {
							getRadioButtonByText(rgBirthComplications, getString(R.string.unspecified)).setChecked(true);
	
						}
						
					} else if(questionId == CreatePatient.QUESTION_BIRTH_PLACE) {
						List<String> array = Arrays.asList(getResources().getStringArray(R.array.yes_no_unspecified)) ;
						spBirthPlace.setSelection(array.indexOf(a.getText()));
						
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

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		RadioButton rb = ((RadioButton)rootView.findViewById(arg0.getCheckedRadioButtonId()));
		if(arg0 == rgAnyRelativeWithClubFoot) {
			if(getString(R.string.yes).equals(rb.getText())) {
				llHowMany.setVisibility(View.VISIBLE);
			} else {
				llHowMany.setVisibility(View.GONE);
			}
		} else if(arg0 == rgDidMotherHadAnyComplicationAtPregnancy) {
			if(getString(R.string.yes).equals(rb.getText())) {
				llWhatWereComplications.setVisibility(View.VISIBLE);
			} else {
				llWhatWereComplications.setVisibility(View.GONE);
			}
		}
		
	}
}
