package com.ihsinformatics.ponsetti.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;

public class ExportActivity extends Activity {

	Spinner spType;
	Button btnExport;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_export);
		
		spType = (Spinner) findViewById(R.id.spType);
		btnExport = (Button) findViewById(R.id.btnExport);
		
		btnExport.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String exportType = spType.getSelectedItem().toString();
				List<String[]> data = null;
				if(exportType.equals("Visit Form")) {
					data = new AnswerDAO(ExportActivity.this).getAnswers(FormsTypes.VISIT_FORM);
					//data = new QuestionDAO().getAllQuestions(ExportActivity.this);
				} else if(exportType.equals("Patient Form")) {
					data = new AnswerDAO(ExportActivity.this).getAnswers(FormsTypes.PATIENT_FORM);
				} else if(exportType.equals("Evaluator Form")) {
					data = new AnswerDAO(ExportActivity.this).getAnswers(FormsTypes.EVALUATOR_FORM);
				}
				
				try {
					export(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	private void export(List<String[]> data) throws IOException {
		if(data!=null && data.size()>0) {
			File exportDir = new File(Environment.getExternalStorageDirectory(), "DCIM/Ponseti");
			if (!exportDir.exists()) {
				exportDir.mkdirs();
			}
		
			File file = new File(exportDir, spType.getSelectedItem().toString() + ".csv");
		
			file.createNewFile();
			CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
		
			csvWrite.writeAll(data);
		
			csvWrite.close();
			Toast.makeText(this, "data exported successfully to "+exportDir.getPath()+spType.getSelectedItem().toString()+".csv", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "No data found", Toast.LENGTH_LONG).show();
		}
		finish();

	}
}
