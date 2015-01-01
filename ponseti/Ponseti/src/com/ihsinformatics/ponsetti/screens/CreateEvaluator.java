package com.ihsinformatics.ponsetti.screens;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.utils.Tools;

public class CreateEvaluator extends Activity {

	
	
	EditText etLastName, etFirstName, etMiddleName;
	Spinner spTitleOfEvaluator;
	Button btnSave;
	Tools tools;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_evaluator);
		
		tools = Tools.getInstance();
		etLastName = (EditText) findViewById(R.id.etSurName);
		etFirstName = (EditText) findViewById(R.id.etFirstName);
		etMiddleName = (EditText) findViewById(R.id.etMiddleName);
		
		spTitleOfEvaluator = (Spinner) findViewById(R.id.spTitleOfEvaluator);
		
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Form form = new Form(null, 0, FormsTypes.EVALUATOR_FORM);
				int i = (int) new FormDAO(CreateEvaluator.this).insert(form);
				List<Answer> answers = new ArrayList<Answer>();
				answers.add(new Answer(tools.getViewText(etLastName), QUESTION_LAST_NAME, i));
				answers.add(new Answer(tools.getViewText(etFirstName), QUESTION_FIRST_NAME, i));
				answers.add(new Answer(tools.getViewText(etMiddleName), QUESTION_MIDDLE_NAME, i));
				answers.add(new Answer(tools.getViewText(spTitleOfEvaluator), QUESTION_TITLE, i));
				new AnswerDAO(CreateEvaluator.this).insert(answers);
				finish();
			}
		});
	}

	
	
	public static int QUESTION_LAST_NAME = 105;
	public static int QUESTION_FIRST_NAME =106;
	public static int QUESTION_MIDDLE_NAME = 107;
	public static int QUESTION_TITLE = 108;
	public static int QUESTION_SEX = 112;

}
