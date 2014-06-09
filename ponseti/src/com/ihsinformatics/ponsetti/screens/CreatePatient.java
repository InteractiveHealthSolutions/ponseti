package com.ihsinformatics.ponsetti.screens;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;
import com.ihsinformatics.ponsetti.view.Fragments.create_patient_form.PateientPhysicalInformationFragment;
import com.ihsinformatics.ponsetti.view.Fragments.create_patient_form.PatientDiagnosisFragment;
import com.ihsinformatics.ponsetti.view.Fragments.create_patient_form.PatientFamilyHistoryFragment;
import com.ihsinformatics.ponsetti.view.Fragments.create_patient_form.PatientGardianInfoFragment;
import com.ihsinformatics.ponsetti.view.Fragments.create_patient_form.PatientGeneralInfoFragment;
import com.ihsinformatics.ponsetti.view.Fragments.create_patient_form.PatientReferalInformationFragment;

public class CreatePatient extends FragmentActivity implements ActionBar.TabListener {

	Button btnSave;
	Tools tools;
	List<Answer> answers;
	Bundle bundle;
	Form form;
	String guardianConsent;
	String photoConsent;
	
	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	MyFragment generalInfoFragment, gardianInfoFragment, familyHistoryFragment,
				referalInformationFragment, diagnosisFragment, physicalInformationFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_patient);

		initFragments();
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(6);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		
		
		tools = Tools.getInstance();
		answers = new ArrayList<Answer>();
		
		
		retreiveaAndPopulateData();
		
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				boolean isUpdatable = true;
				ValidationFailureInformation informationgeneralInfoFragment = generalInfoFragment.validate();
				ValidationFailureInformation informationgardianInfoFragment = gardianInfoFragment.validate();
				ValidationFailureInformation informationfamilyHistoryFragment = familyHistoryFragment.validate();
				ValidationFailureInformation informationreferalInformationFragment = referalInformationFragment.validate();
				ValidationFailureInformation informationdiagnosisFragment = diagnosisFragment.validate();
				ValidationFailureInformation informationphysicalInformationFragment = physicalInformationFragment.validate();
				if(informationgeneralInfoFragment != null) {
					Toast.makeText(CreatePatient.this, informationgeneralInfoFragment.getErrorMessage(), Toast.LENGTH_LONG).show();
				} else if(informationgardianInfoFragment != null) {
					Toast.makeText(CreatePatient.this, informationgardianInfoFragment.getErrorMessage(), Toast.LENGTH_LONG).show();
				} else if(informationfamilyHistoryFragment != null) {
					Toast.makeText(CreatePatient.this, informationfamilyHistoryFragment.getErrorMessage(), Toast.LENGTH_LONG).show();
				} else if(informationreferalInformationFragment != null) {
					Toast.makeText(CreatePatient.this, informationreferalInformationFragment.getErrorMessage(), Toast.LENGTH_LONG).show();
				} else if(informationdiagnosisFragment != null) {
					Toast.makeText(CreatePatient.this, informationdiagnosisFragment.getErrorMessage(), Toast.LENGTH_LONG).show();
				} else if(informationphysicalInformationFragment != null) {
					Toast.makeText(CreatePatient.this, informationphysicalInformationFragment.getErrorMessage(), Toast.LENGTH_LONG).show();
				} else {
					if(form == null) {
						form = new Form(null, 0, FormsTypes.PATIENT_FORM);
						int i = (int) new FormDAO(CreatePatient.this).insert(form);
						form.setFormId(i);
						isUpdatable = false;
					}
					
					answers.add(new Answer(guardianConsent, CreatePatient.QUESTION_GUARDIAN_CONSENT, form.getFormId()));
					answers.add(new Answer(photoConsent, CreatePatient.QUESTION_PHOTO_CONSENT, form.getFormId()));
					
					answers.addAll(generalInfoFragment.getAnswers(form.getFormId()));
					answers.addAll(gardianInfoFragment.getAnswers(form.getFormId()));
					answers.addAll(familyHistoryFragment.getAnswers(form.getFormId()));
					answers.addAll(referalInformationFragment.getAnswers(form.getFormId()));
					answers.addAll(diagnosisFragment.getAnswers(form.getFormId()));
					answers.addAll(physicalInformationFragment.getAnswers(form.getFormId()));
					if(!isUpdatable) {
						new AnswerDAO(CreatePatient.this).insert(answers);
					} else {
						new AnswerDAO(CreatePatient.this).update(answers);
					}
					
					finish();
				}
			}
		});
		
		
		
	}

	

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return generalInfoFragment;
				
			case 1:
				return gardianInfoFragment;
				
			case 2:
				return familyHistoryFragment;
				
			case 3:
				return referalInformationFragment;
				
			case 4:
				return diagnosisFragment;
			case 5:
				return physicalInformationFragment;

			default:
				break;
			}
			
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "General Info";
			case 1:
				
				return "Patient/ Guardian Info";
			case 2:
				
				return "Family History";
			case 3:
				
				return "Referal Information";
			case 4:
				
				return "Diagnosis";
			case 5:
				
				return "Physical Examination";
			}
			
			return null;
		}
	}
	
	private void initFragments() {
		Bundle args1 = new Bundle();
		Bundle args2 = new Bundle();
		Bundle args3 = new Bundle();
		Bundle args4 = new Bundle();
		Bundle args5 = new Bundle();
		Bundle args6 = new Bundle();
		
		args1.putInt(MyFragment.LAYOUT_ID, R.layout.fragment_patient_general_info);
		args2.putInt(MyFragment.LAYOUT_ID, R.layout.fragment_patient_guardian_info);
		args3.putInt(MyFragment.LAYOUT_ID, R.layout.fragment_patient_family_history);
		args4.putInt(MyFragment.LAYOUT_ID, R.layout.fragment_patient_referal_information);
		args5.putInt(MyFragment.LAYOUT_ID, R.layout.fragment_patient_diagnosis);
		args6.putInt(MyFragment.LAYOUT_ID, R.layout.fragment_patient_physical_examination);
		
		generalInfoFragment = new PatientGeneralInfoFragment();
		gardianInfoFragment = new PatientGardianInfoFragment();
		familyHistoryFragment = new PatientFamilyHistoryFragment();
		referalInformationFragment = new PatientReferalInformationFragment();
		diagnosisFragment = new PatientDiagnosisFragment();
		physicalInformationFragment = new PateientPhysicalInformationFragment();
		
		generalInfoFragment.setArguments(args1);
		gardianInfoFragment.setArguments(args2);
		familyHistoryFragment.setArguments(args3);
		referalInformationFragment.setArguments(args4);
		diagnosisFragment.setArguments(args5);
		physicalInformationFragment.setArguments(args6);
		
	}

	private void retreiveaAndPopulateData() {
		bundle = getIntent().getExtras();
		form = (Form) bundle.getSerializable("form");
		
		if(form != null){
			List<Answer> answers = new AnswerDAO(this).getAnswers(form.getFormId());
			generalInfoFragment.loadData(answers);
			gardianInfoFragment.loadData(answers);
			familyHistoryFragment.loadData(answers);
			referalInformationFragment.loadData(answers);
			diagnosisFragment.loadData(answers);
			physicalInformationFragment.loadData(answers);
			// btnSave.setVisibility(View.GONE);
			
			for(Answer a: answers) {
				if(a.getQuestion_id() == CreatePatient.QUESTION_GUARDIAN_CONSENT) {
					guardianConsent = a.getText();
					
				} else if(a.getQuestion_id() == CreatePatient.QUESTION_PHOTO_CONSENT) {
					photoConsent = a.getText();
					
				}
			}
			
		} else {
			guardianConsent = bundle.getString(PatientPermissionsDialog.GUARDIAN_CONSENT);
			photoConsent = bundle.getString(PatientPermissionsDialog.PHOTO_CONSENT);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try {
			Bundle res = data.getExtras();
			String d = res.getString("date");
			if(requestCode == DATE_OF_BIRTH_REQUEST) {
				((PatientGeneralInfoFragment) generalInfoFragment).setEtDateOfBirth(d);
			} else if(requestCode == DATE_OF_EVALUATION_REQUEST) {
				((PatientDiagnosisFragment) diagnosisFragment).setEtDateOfEvaluation(d);
			}
		} catch (NullPointerException npe) {

		}
	}
	
	public static final int QUESTION_GUARDIAN_CONSENT = 109;
	public static final int QUESTION_PHOTO_CONSENT =110;
	public static final int QUESTION_HOSPITAL = 111;
	
	public static final int QUESTION_LAST_NAME = 0;
	public static final int QUESTION_FIRST_NAME =1;
	public static final int QUESTION_MIDDLE_NAME = 2;
	public static final int QUESTION_SEX = 3;
	public static final int QUESTION_RACE = 4;
	public static final int QUESTION_DATE_OF_BIRTH = 5;
	public static final int QUESTION_TRIBE = 6;
	public static final int QUESTION_ADDRESS_1 = 7;
	public static final int QUESTION_ADDRESS_2 = 8;
	public static final int QUESTION_VILLAGE = 9;
	public static final int QUESTION_STATE = 10;
	public static final int QUESTION_COUNTRY = 11;
	               
	public static final int QUESTION_PARENT_LAST_NAME = 12;
	public static final int QUESTION_PARENT_FIRST_NAME = 13;
	public static final int QUESTION_PARENT_MIDDLE_NAME = 14;
	public static final int QUESTION_PARENT_RELATON_TO_PATIENT = 15;
	public static final int QUESTION_PARENT_PHONE_NUMBER_1 = 16;
	public static final int QUESTION_PARENT_PHONE_NUMBER_2 = 17;
	public static final int QUESTION_SECONDARY_PARENT = 18;
	public static final int QUESTION_SECONDARY_PARENT_LAST_NAME = 19;
	public static final int QUESTION_SECONDARY_PARENT_FIRST_NAME = 20;
	public static final int QUESTION_SECONDARY_PARENT_MIDDLE_NAME = 21;
	public static final int QUESTION_SECONDARY_PARENT_RELATON_TO_PATIENT = 22;
	public static final int QUESTION_SECONDARY_PARENT_PHONE_NUMBER_1 = 23;
	public static final int QUESTION_SECONDARY_PARENT_PHONE_NUMBER_2 = 24;
	public static final int QUESTION_EMERGENCY_CONTACT = 25;
	public static final int QUESTION_EMERGENCY_PARENT_LAST_NAME = 26;
	public static final int QUESTION_EMERGENCY_PARENT_FIRST_NAME = 27;
	public static final int QUESTION_EMERGENCY_PARENT_MIDDLE_NAME = 28;
	public static final int QUESTION_EMERGENCY_PARENT_RELATON_TO_PATIENT = 29;
	public static final int QUESTION_EMERGENCY_PARENT_PHONE_NUMBER_1 = 30;
	public static final int QUESTION_EMERGENCY_PARENT_PHONE_NUMBER_2 = 31;
	               
	public static final int QUESTION_ANY_RELATIVES_WITH_CF = 32;
	public static final int QUESTION_HOW_MANY = 33;
	public static final int QUESTION_LENGHT_OF_PREGNANCY = 34;
	public static final int QUESTION_MOTHER_COMPLICATION = 35;
	public static final int QUESTION_WHAT_WERE_COMPLICATIONS = 36;
	public static final int QUESTION_MOTHER_ALCOHOL = 37;
	public static final int QUESTION_MOTHER_SMOKE = 38;
	public static final int QUESTION_BIRTH_COMPLICATIONS = 113;
	public static final int QUESTION_BIRTH_PLACE = 114;
	
	public static final int QUESTION_REFERAL_SOURCE = 39;
	public static final int QUESTION_HOSPITAL_OR_CLINIC_NAME = 40;
	public static final int QUESTION_DOCTOR_NAME = 41;
	public static final int QUESTION_PLEASE_SPECIFY = 42;
	               
	public static final int QUESTION_NAME_OF_EVALUATOR = 43;
	public static final int QUESTION_EVALUATION_DATE = 44;
	public static final int QUESTION_FEET_AFFECTED = 45;
	public static final int QUESTION_DIAGNOSIS = 46;
	public static final int QUESTION_DEFORMITY_PRESENT_AT_BIRTH = 47;
	public static final int QUESTION_ANY_PREVIOUS_TREATMENT = 48;
	public static final int QUESTION_HOW_MANY_PREVIOUS_TREATMENTS = 49;
	public static final int QUESTION_TYPE_OF_PREVIOUS_TREATMENTS = 50;
	public static final int QUESTION_DIAGNOSED_PARENTALLY = 51;
	public static final int QUESTION_AT_PREGNANCY_WEEK = 52;
	public static final int QUESTION_CONFIRMED_AT_BIRTH = 53;
	public static final int QUESTION_DIAGNOSIS_COMMENTS = 115;
	               
	public static final int QUESTION_ANY_ABNORMALITIES = 54;
	public static final int QUESTION_ANY_WEAKNESS = 55;
	
	

	public static final int DATE_OF_BIRTH_REQUEST = 0;
	public static final int DATE_OF_EVALUATION_REQUEST = 1;
	public static final int DATE_OF_VISIT = 2;
	public static final int DATE_OF_NEXT_VISIT = 3;

}
