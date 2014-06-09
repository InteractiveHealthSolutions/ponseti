package com.ihsinformatics.ponsetti.view.Fragments;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.utils.ValidationFailureInformation;


public abstract class MyFragment extends Fragment {
	
	public static final String LAYOUT_ID = "layout_id";
	protected Tools tools;
	protected List<Answer> answers;
	protected View rootView;
	protected Boolean dataLoadRequest;
	
	public MyFragment() {
		tools = Tools.getInstance();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(getArguments().getInt(LAYOUT_ID), container, false);
		/*TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
		dummyTextView.setText(Integer.toString(getArguments().getInt(LAYOUT_ID)));*/
		this.rootView = rootView;
		return rootView;
	}
	
	public void loadData(List<Answer> answers) {
		dataLoadRequest = true;
		this.answers = answers;
	}
	
	public abstract List<Answer> getAnswers(int formId);
	public abstract ValidationFailureInformation validate();
	protected abstract void checkAndFillData();
	
	public View getRootView() {
		return rootView;
	}
	
	protected RadioButton getRadioButtonByText(RadioGroup radioGroup, String text) {
		int count = radioGroup.getChildCount();
		RadioButton radioButton;
	    // ArrayList<RadioButton> listOfRadioButtons = new ArrayList<RadioButton>();
	    for (int i=0;i<count;i++) {
	        View o = radioGroup.getChildAt(i);
	        if (o instanceof RadioButton) {
	            radioButton = (RadioButton) o;
	        	if(((RadioButton) o).getText().equals(text)){
	            	return radioButton;
	            }
	        }
	    }
	    
	    return null;
	}
	
	protected void addCheckBoxesAnswers(int questionId, int formId, CheckBox... checkBoxes) {
		String value;
		boolean hasValue = false;
		for(int i=0; i<checkBoxes.length; i++) {
			if(checkBoxes[i].isChecked()) {
				value = tools.getViewText(checkBoxes[i]);	
				answers.add(new Answer(value, questionId, formId));
				hasValue = true;
			}
		}
		if(!hasValue) {
			answers.add(new Answer("null", questionId, formId));
		}
	}
	
}
