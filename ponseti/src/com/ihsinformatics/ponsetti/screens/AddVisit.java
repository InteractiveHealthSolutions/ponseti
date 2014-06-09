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
import com.ihsinformatics.ponsetti.view.PatientView;
import com.ihsinformatics.ponsetti.view.Fragments.MyFragment;
import com.ihsinformatics.ponsetti.view.Fragments.add_visit_form.VisitComplicationsFragment;
import com.ihsinformatics.ponsetti.view.Fragments.add_visit_form.VisitGeneralInfoFragment;
import com.ihsinformatics.ponsetti.view.Fragments.add_visit_form.VisitLeftFootFragment;
import com.ihsinformatics.ponsetti.view.Fragments.add_visit_form.VisitRightFootFragment;

public class AddVisit extends FragmentActivity implements ActionBar.TabListener {

	Button btnSave;
	Tools tools;
	List<Answer> answers;
	Bundle bundle;
	Form form;
	private String parentNode;
	
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	MyFragment generalInfoFragment, leftFootFragment, rightFootFragment, complicationsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_visit);
		
		initFragments();
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setOffscreenPageLimit(3);
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
				ValidationFailureInformation informationGeneralInfoFragment = generalInfoFragment.validate();
				ValidationFailureInformation informationLeftFootFragment = leftFootFragment.validate();
				ValidationFailureInformation informationRightFootFragment = rightFootFragment.validate();
				ValidationFailureInformation informationTreatmentFragment = complicationsFragment.validate();
				if(informationGeneralInfoFragment != null) {
					Toast.makeText(AddVisit.this, informationGeneralInfoFragment.getErrorMessage(), Toast.LENGTH_LONG).show();
				} else if(informationLeftFootFragment != null) {
					Toast.makeText(AddVisit.this, informationLeftFootFragment.getErrorMessage(), Toast.LENGTH_LONG).show();
				} else if(informationRightFootFragment != null) {
					Toast.makeText(AddVisit.this, informationRightFootFragment.getErrorMessage(), Toast.LENGTH_LONG).show();
				} else if(informationTreatmentFragment != null) {
					Toast.makeText(AddVisit.this, informationTreatmentFragment.getErrorMessage(), Toast.LENGTH_LONG).show();
				} else {
					if(form == null) {
						form = new Form(null, 0, FormsTypes.VISIT_FORM);
						int i = (int) new FormDAO(AddVisit.this).insert(form);
						form.setFormId(i);
						isUpdatable = false;
					}
					
					answers.add(new Answer(parentNode, AddVisit.QUESTION_PARENT_NODE, form.getFormId()));
					
					answers.addAll(generalInfoFragment.getAnswers(form.getFormId()));
					answers.addAll(leftFootFragment.getAnswers(form.getFormId()));
					answers.addAll(rightFootFragment.getAnswers(form.getFormId()));
					answers.addAll(complicationsFragment.getAnswers(form.getFormId()));
					
					if(!isUpdatable) {
						new AnswerDAO(AddVisit.this).insert(answers);
					} else {
						new AnswerDAO(AddVisit.this).update(answers);
					}
					
					finish();
				}
			}
		});
	}

	

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
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
	private class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			
			switch (position) {
			case 0:
				return generalInfoFragment;
				
			case 1:
				return leftFootFragment;
				
			case 2:
				return rightFootFragment;
			case 3:
				return complicationsFragment;

			default:
				break;
			}
			
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			
			switch (position) {
			case 0:
				return "General Info";
			case 1:
				return "Left Foot";
			case 2:
				return "Right Foot";
			case 3:
				return "Complications";
			}
			return null;
		}
	}

	private void initFragments() {
		Bundle args1 = new Bundle();
		Bundle args2 = new Bundle();
		Bundle args3 = new Bundle();
		Bundle args4 = new Bundle();
		
		args1.putInt(MyFragment.LAYOUT_ID, R.layout.fragment_visit_general_info);
		args2.putInt(MyFragment.LAYOUT_ID, R.layout.fragment_visit_left_foot);
		args3.putInt(MyFragment.LAYOUT_ID, R.layout.fragment_visit_right_foot);
		args4.putInt(MyFragment.LAYOUT_ID, R.layout.fragment_visit_complications);
		
		generalInfoFragment = new VisitGeneralInfoFragment();
		leftFootFragment = new VisitLeftFootFragment();
		rightFootFragment = new VisitRightFootFragment();
		complicationsFragment = new VisitComplicationsFragment();
		
		generalInfoFragment.setArguments(args1);
		leftFootFragment.setArguments(args2);
		rightFootFragment.setArguments(args3);
		complicationsFragment.setArguments(args4);
	}
	
	private void retreiveaAndPopulateData() {
		bundle = getIntent().getExtras();
		form = (Form) bundle.getSerializable("form");
		
		if(form != null){
			List<Answer> answers = new AnswerDAO(this).getAnswers(form.getFormId());
			generalInfoFragment.loadData(answers);
			leftFootFragment.loadData(answers);
			rightFootFragment.loadData(answers);
			complicationsFragment.loadData(answers);
			// btnSave.setVisibility(View.GONE);	
			for(Answer a: answers) {
				if(a.getQuestion_id() == AddVisit.QUESTION_PARENT_NODE) {
					parentNode = a.getText();
					
				}
			}
			
		} else {
			parentNode = bundle.getString(PatientView.ICR_ID);
			
		}
		
			
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		try {
			Bundle res = arg2.getExtras();
			String d = res.getString("date");
			if(arg0 == DATE_OF_VISIT) {
				((VisitGeneralInfoFragment) generalInfoFragment).setEtDateOfVisit(d);;
			} else if(arg0 == DATE_OF_NEXT_VISIT) {
				((VisitGeneralInfoFragment) generalInfoFragment).setEtDateOfNextVisit(d);
			}
		} catch (NullPointerException npe) {

		}
		super.onActivityResult(arg0, arg1, arg2);
	}
	
	public static final int QUESTION_HOSPITAL_OR_CLINIC = 56;
	public static final int QUESTION_NAME_OF_EVALUATOR = 57;
	public static final int QUESTION_DATE_OF_VISIT = 58;
	public static final int QUESTION_DATE_OF_NEXT_VISIT = 116;
	public static final int QUESTION_IS_FINAL_VISIT = 59;
	public static final int QUESTION_DID_EXPERIENCE_RELAPSE = 60;
	
	public static final int QUESTION_LEFT_VARUS = 61;
	public static final int QUESTION_LEFT_CAVUS = 62;
	public static final int QUESTION_LEFT_ABDUCTUS = 63;
	public static final int QUESTION_LEFT_ENQUINUS = 64;
	public static final int QUESTION_LEFT_POSTERIOR_CREASE = 65;
	public static final int QUESTION_LEFT_EMPTY_HEEL = 66;
	public static final int QUESTION_LEFT_RIGID_ENQUINUS = 67;
	public static final int QUESTION_LEFT_MEDIAL_CREASE = 68;
	public static final int QUESTION_LEFT_TALAR_HEAD_COVERAGE = 69;
	public static final int QUESTION_LEFT_CURVED_LATERAL_BORDER = 70;
	public static final int QUESTION_LEFT_TREATMENT = 71;
	public static final int QUESTION_LEFT_CASTER = 72;
	public static final int QUESTION_LEFT_CAST_NUMBER = 73;
	public static final int QUESTION_LEFT_DEGREES_ABDUCTION = 74;
	public static final int QUESTION_LEFT_DEGREES_DORSIFLEXION = 75;
	public static final int QUESTION_LEFT_BRACE_COMPILANCE = 76;
	public static final int QUESTION_LEFT_PROBLEMS = 77;
	public static final int QUESTION_LEFT_ACTION_TAKEN = 78;
	public static final int QUESTION_LEFT_SURGERY_TYPE = 79;
	public static final int QUESTION_LEFT_SURGERY_COMMENTS = 117;
	public static final int QUESTION_LEFT_GIVE_DETAILS = 80;
	public static final int QUESTION_COMPLICATIONS = 81;
	
	public static final int QUESTION_RIGHT_VARUS = 82;
	public static final int QUESTION_RIGHT_CAVUS = 83;
	public static final int QUESTION_RIGHT_ABDUCTUS = 84;
	public static final int QUESTION_RIGHT_ENQUINUS = 85;
	public static final int QUESTION_RIGHT_POSTERIOR_CREASE = 86;
	public static final int QUESTION_RIGHT_EMPTY_HEEL = 87;
	public static final int QUESTION_RIGHT_RIGID_ENQUINUS = 88;
	public static final int QUESTION_RIGHT_MEDIAL_CREASE = 89;
	public static final int QUESTION_RIGHT_TALAR_HEAD_COVERAGE = 90;
	public static final int QUESTION_RIGHT_CURVED_LATERAL_BORDER = 91;
	public static final int QUESTION_RIGHT_TREATMENT = 92;
	public static final int QUESTION_RIGHT_CASTER = 93;
	public static final int QUESTION_RIGHT_CAST_NUMBER = 94;
	public static final int QUESTION_RIGHT_DEGREES_ABDUCTION = 95;
	public static final int QUESTION_RIGHT_DEGREES_DORSIFLEXION = 96;
	public static final int QUESTION_RIGHT_BRACE_COMPILANCE = 97;
	public static final int QUESTION_RIGHT_PROBLEMS = 98;
	public static final int QUESTION_RIGHT_ACTION_TAKEN = 99;
	public static final int QUESTION_RIGHT_SURGERY_TYPE = 100;
	public static final int QUESTION_RIGHT_SURGERY_COMMENTS = 118;
	public static final int QUESTION_RIGHT_GIVE_DETAILS = 101;
	public static final int QUESTION_DESCRIPION_OF_COMPLICATIONS = 102;
	public static final int QUESTION_TREATMENT_OF_COMPLICATIONS = 103;
	public static final int QUESTION_RESULTS_AFTER_TREATMENT = 104;
	
	public static final int QUESTION_PARENT_NODE = 119;
	public static final int QUESTION_LEFT_HFCS = 120;
	public static final int QUESTION_RIGHT_HFCS = 121;
	public static final int QUESTION_LEFT_MFCS = 122;
	public static final int QUESTION_RIGHT_MFCS = 123;
	public static final int QUESTION_LEFT_TS = 124;
	public static final int QUESTION_RIGHT_TS = 125;
	public static final int QUESTION_RIGHT_OTHER_SURGERY_TYPE = 126;
	public static final int QUESTION_LEFT_OTHER_SURGERY_TYPE = 127;
	public static final int QUESTION_GENERAL_COMMENTS = 128;
	
	
	public static final int DATE_OF_VISIT = 0;
	public static final int DATE_OF_NEXT_VISIT = 1;
}






















