package com.ihsinformatics.ponsetti.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ihsinformatics.ponsetti.ImageCapturer;
import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.screens.AddVisit;
import com.ihsinformatics.ponsetti.screens.CreatePatient;
import com.ihsinformatics.ponsetti.utils.Tools;

public class PatientView extends LinearLayout implements OnClickListener {

	private ImageView ivIsSynced, ivPlusMinus, ivCamera, ivPatient;
	private TextView tvPatientName, tvPatientId;
	private LinearLayout llPlusMinus, llPatient;
	private Context context;
	private Form form;
	public static final String ICR_ID = "icr_id";
	
	public PatientView(Context context) {
		super(context);
		
	}
	
	public PatientView(final Context context, Form form, String patientName) {
		this(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.layout_view_patient, this, false);
		addView(view);
		this.context = context;
		this.form = form;
		
		ivIsSynced = (ImageView) findViewById(R.id.ivIsSynched);
		ivPlusMinus = (ImageView) findViewById(R.id.ivPlusMinus);
		ivCamera = (ImageView) findViewById(R.id.ivCamera);
		ivPatient = (ImageView) findViewById(R.id.ivPatient);
		
		tvPatientName = (TextView) findViewById(R.id.tvPatientName);
		tvPatientId = (TextView) findViewById(R.id.tvPatientId);
		tvPatientName.setText(patientName);
		
			tvPatientId.setText(form.getIcrId());
		
		
		llPlusMinus = (LinearLayout) findViewById(R.id.LLPlusMinus);
		llPatient = (LinearLayout) findViewById(R.id.llPatient);
		
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		ivPlusMinus.setOnClickListener(this);
		llPatient.setOnClickListener(this);
		ivPatient.setOnClickListener(this);
		ivCamera.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v == ivPlusMinus) {
			if(llPlusMinus.getVisibility() == View.VISIBLE) {
				llPlusMinus.setVisibility(View.GONE);
				ivPlusMinus.setImageResource(R.drawable.plus_circle);
			} else {
				llPlusMinus.setVisibility(View.VISIBLE);
				ivPlusMinus.setImageResource(R.drawable.minus_circle);
			}
		} else if(v == llPatient) {
			Activity parent = ((Activity)context);
			Intent intent = new Intent(parent, CreatePatient.class);
			Bundle b = new Bundle();
			b.putSerializable("form", form);
			intent.putExtras(b);
			parent.startActivity(intent);
		} else if(v == ivPatient) {
			Activity parent = ((Activity)context);
			Intent intent = new Intent(parent, AddVisit.class);
			Bundle b = new Bundle();
			b.putString(ICR_ID, tvPatientId.getText().toString());
			intent.putExtras(b);
			parent.startActivity(intent);
		} else if(v== ivCamera) {
			Activity parent = ((Activity)context);
			Tools.getInstance().dispatchTakePictureIntent(parent, tvPatientId.getText().toString());
		}
	}
	
		
}
