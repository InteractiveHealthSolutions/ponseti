package com.ihsinformatics.ponsetti.utils;

import com.ihsinformatics.ponsetti.utils.interfaces.ValidationInterface;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class Validator {
	
	public static final byte LENGTH_TYPE_EQUAL = 0;
	public static final byte LENGTH_TYPE_MIN = 1;
	public static final byte LENGTH_TYPE_MAX = 2;
	
	private static Validator validator;

	private Validator() {}

	public static Validator getInstance() {
		if (validator == null) {
			validator = new Validator();
		}
		
		return validator;
	}
	
	public boolean validate(View view, View errorLabel, int fieldLength, int lenghtType, ValidationInterface validationInterface) {
		if(view.getVisibility() == View.VISIBLE) {
			if(view instanceof EditText) {
				String value = ((EditText)view).getText().toString();
				if(value != null && value.length() == fieldLength) {
					
					return true;
				}
			} else if(view instanceof RadioGroup) {
				if(((RadioGroup)view).getCheckedRadioButtonId() != -1) {
					
					return true;
				}
			}
		}
		
		validationInterface.onValidationFalse(view, errorLabel);
		return false;
	}
}
