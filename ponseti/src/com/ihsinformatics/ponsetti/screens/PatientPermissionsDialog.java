package com.ihsinformatics.ponsetti.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ihsinformatics.ponsetti.R;

public class PatientPermissionsDialog extends Activity implements OnCheckedChangeListener {

	RadioGroup rgAreGuardianConsentIncluded, rgArePhotographConsentGiven;
	public static String GUARDIAN_CONSENT = "guardian";
	public static String PHOTO_CONSENT = "parent";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_permissions_dialog);
		
		rgAreGuardianConsentIncluded = (RadioGroup) findViewById(R.id.rgAreGuardianConsentIncluded);
		rgArePhotographConsentGiven = (RadioGroup) findViewById(R.id.rgArePhotographConsentGiven);
		
		rgAreGuardianConsentIncluded.setOnCheckedChangeListener(this);
		rgArePhotographConsentGiven.setOnCheckedChangeListener(this);
		
		setFinishOnTouchOutside(false);
	}

	
	
	@Override
	public void onBackPressed() {
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int guardian = rgAreGuardianConsentIncluded.getCheckedRadioButtonId();
		int photo = rgArePhotographConsentGiven.getCheckedRadioButtonId();
		if(guardian != -1 && ((RadioButton)findViewById(guardian)).getText().toString().equals(getString(R.string.no))) {
			finish();
		} else if(check(guardian, photo)) {
			Intent intent = new Intent(PatientPermissionsDialog.this, CreatePatient.class);
			intent.putExtra(GUARDIAN_CONSENT, ((RadioButton)findViewById(guardian)).getText().toString());
			intent.putExtra(PHOTO_CONSENT, ((RadioButton)findViewById(photo)).getText().toString());
			startActivity(intent);
			finish();
		}
		
	}
	
	private boolean check(int guardian, int photo) {
		
		if (guardian != -1 && photo != -1 ) {
			
			return true;
		}
		
		return false;
	}

}
