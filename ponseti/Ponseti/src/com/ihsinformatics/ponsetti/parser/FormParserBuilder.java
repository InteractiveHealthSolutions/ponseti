package com.ihsinformatics.ponsetti.parser;

import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;

public class FormParserBuilder {

	FormParser formParser;

	public FormParserBuilder(String formType) {
		if (formType.equals(FormsTypes.VISIT_FORM)) {

			formParser = new VisitFormParser();
		} else if (formType.equals(FormsTypes.PATIENT_FORM)) {
			
			formParser = new PatientFormParser();
		} else if (formType.equals(FormsTypes.EVALUATOR_FORM)) {

			formParser = new EvaluatorFormParser();
		}
	}

	public FormParser buildFormParser(String jsonData, Form form, byte tag) {
		formParser.setJsonData(jsonData);
		formParser.setForm(form);
		formParser.setTag(tag);

		return formParser;
	}
}
