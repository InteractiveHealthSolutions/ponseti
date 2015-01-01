package com.ihsinformatics.ponsetti.view.Fragments.manage_photos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ihsinformatics.ponsetti.R;
import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.utils.interfaces.OnFormSelectedListener;

public class PhotosFilterFragment extends Fragment {
	
	
	AutoCompleteTextView actvInputIcrId;
	ListView lvVisits;
	TextView tvPreTreatmentPhotos, tvIcrId;
	LinearLayout llVisits;
	private OnFormSelectedListener onFormSelectedListener;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_photos_filter, container, false);
		actvInputIcrId = (AutoCompleteTextView) rootView.findViewById(R.id.actvEnterIcrId);
		llVisits = (LinearLayout) rootView.findViewById(R.id.llVisits);
		lvVisits = (ListView) rootView.findViewById(R.id.lvVisits);
		tvIcrId = (TextView) rootView.findViewById(R.id.tvIcrId);
		tvPreTreatmentPhotos = (TextView) rootView.findViewById(R.id.tvPreTreatmentPhotos);
		onFormSelectedListener = (OnFormSelectedListener) getActivity();
		FormDAO formDAO = new FormDAO(getActivity());
		AnswerDAO answerDAO = new AnswerDAO(getActivity());
		final List<Form> formsList = formDAO.getForms(Form.COLUMN_TYPE_ID+"= '"+FormsTypes.PATIENT_FORM+"'");
		final List<String> data = answerDAO.getNames(formsList);
		final List<String> icrIds = new ArrayList<String>();
		for(Form f:formsList) {
			icrIds.add(f.getIcrId());
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(),android.R.layout.simple_list_item_1, data);
		tvPreTreatmentPhotos.setVisibility(View.GONE);
		llVisits.setVisibility(View.GONE);
		actvInputIcrId.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			// .toArray(new String[formsList.size()])
			@Override
			public void afterTextChanged(Editable arg0) {
				int temp = data.indexOf(arg0.toString());
				tvIcrId.setText("");
				if(temp>-1) {
					tvIcrId.setText(icrIds.get(temp));
					displayVisits(icrIds.get(temp));
				}
			}
		});
		
		tools = Tools.getInstance();
        actvInputIcrId.setThreshold(1);//will start working from first character  
        actvInputIcrId.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView  
        // actvInputIcrId.setTextColor(Color.BLUE);  
        
        tvPreTreatmentPhotos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FormDAO formDAO = new FormDAO(getActivity());
				String icrId = tvIcrId.getText().toString();
				if(tools.isICRID(icrId)) {
					Form form = formDAO.getForm(Form.COLUMN_ICR_ID+"='"+icrId+"'");
					onFormSelectedListener.onFormSelected(form);
					tvPreTreatmentPhotos.setBackgroundColor(getResources().getColor(R.color.aqua_dark_D0E7FA));
					for(int i=0; i<lvVisits.getChildCount(); i++) {
							lvVisits.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent));
					}
				}
			}
		});
		
		return rootView;
	}
	Tools tools;
	HashMap<String, Form> formsMap;
	private void displayVisits(String parentNode) {
		FormDAO formDAO = new FormDAO(getActivity());
		Form patientForm = formDAO.getForm(Form.COLUMN_ICR_ID+"='"+parentNode+"'");
		onFormSelectedListener.onFormSelected(patientForm);
		if(patientForm!=null) {
			tvPreTreatmentPhotos.setVisibility(View.VISIBLE);
			tvPreTreatmentPhotos.setBackgroundColor(getResources().getColor(R.color.aqua_dark_D0E7FA));
			List<Answer> answers;
			formsMap = new HashMap<String, Form>();
			// List<Form> forms = new ArrayList<Form>();
			AnswerDAO answerDAO = new AnswerDAO(getActivity());
			// answers = answerDAO.getAnswers(Question.COLUMN_QUESTION_ID+"="+AddVisit.QUESTION_PARENT_NODE +"and"+Answer.COLUMN_TEXT+"='"+ oldIcrId+"'", null);
			answers = answerDAO.getAnswers(Answer.COLUMN_TEXT+"='"+ parentNode+"'", null);
			
			List<String> visitFormsDates = new ArrayList<String>();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.US);
			for(Answer answer:answers) {
				Form form = formDAO.getForm(Form.COLUMN_FORM_ID+"="+answer.getFormId());
				String tempDate = dateFormat.format(tools.getDateFromEpochTime(form.getTimeStamp()));
				formsMap.put(tempDate, form);
				visitFormsDates.add(tempDate);
			}
			lvVisits.setAdapter(new  ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, visitFormsDates));
			lvVisits.setOnItemClickListener(new OnItemClickListener() {
	
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Form f = formsMap.get(((TextView)arg1).getText().toString());
					arg1.setBackgroundColor(getResources().getColor(R.color.aqua_dark_D0E7FA));
					
					for(int i=0; i<arg0.getChildCount(); i++) {
						if(i!=arg2) {
							arg0.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent));
						}
					}
					tvPreTreatmentPhotos.setBackgroundColor(getResources().getColor(android.R.color.transparent));
					onFormSelectedListener.onFormSelected(f);
					
				}
			});
			
			
			if(visitFormsDates.size()>0) {
				llVisits.setVisibility(View.VISIBLE);
			} else {
				llVisits.setVisibility(View.GONE);
			}
		} else {
			tvPreTreatmentPhotos.setVisibility(View.GONE);
			llVisits.setVisibility(View.GONE);
			onFormSelectedListener.onFormSelected(null);
		}
	}
}
