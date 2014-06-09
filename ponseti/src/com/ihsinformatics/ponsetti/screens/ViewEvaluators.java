package com.ihsinformatics.ponsetti.screens;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.view.EvaluatorView;
import com.ihsinformatics.ponsetti.view.NetworkProgressDialog;

public class ViewEvaluators extends Activity {

	NetworkProgressDialog networkProgressDialog;
	LinearLayout llMAin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_evaluators);
		
		llMAin = (LinearLayout) findViewById(R.id.llMain);
		AnswerDAO answerDAO = new AnswerDAO(this);
		List<Form> forms = new FormDAO(this).getForms(FormsTypes.EVALUATOR_FORM);
		for(Form form : forms) {
			llMAin.addView(new EvaluatorView(this, form, answerDAO.getEvaluatorName(form.getFormId())));
		}
		
	}

	
	
}
