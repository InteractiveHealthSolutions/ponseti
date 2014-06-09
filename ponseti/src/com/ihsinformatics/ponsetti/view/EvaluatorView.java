package com.ihsinformatics.ponsetti.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.pojos.Form;

public class EvaluatorView extends LinearLayout {

	ImageView ivIsSynced;
	TextView tvPatientName, tvPatientId;
 	
	public EvaluatorView(Context context) {
		super(context);
	}
	
	public EvaluatorView(Context context, Form form, String patientName) {
		this(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.layout_view_evaluator, this, false);
		addView(view);
		
		ivIsSynced = (ImageView) findViewById(R.id.ivIsSynched);
		
		
		tvPatientName = (TextView) findViewById(R.id.tvPatientName);
		tvPatientId = (TextView) findViewById(R.id.tvPatientId);
		tvPatientName.setText(patientName);
		
		if(form.getIcrId() != null) {
			tvPatientId.setText(form.getIcrId());
		} else {
			tvPatientId.setText(form.getFormId()+" (local id)");
			ivIsSynced.setImageResource(R.drawable.synchronize);
		}
		
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		
	}

}
