package com.ihsinformatics.ponsetti.parser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;

public abstract class FormParser {

	protected List<Answer> answers;
	Form form;
	private String jsonData;
	public byte tag;
	public static final byte TAG_EXISTING = 0;
	public static final byte TAG_NEW = 1;

	public FormParser() {
		answers = new ArrayList<Answer>();
		
	}
	
	public FormParser(String jsonData, Form form, byte tag) {
		answers = new ArrayList<Answer>();
		this.form = form;
		this.tag = tag;
		this.jsonData = jsonData;
	}

	public FormParser parse() {
		try {
			parse(jsonData, form);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public abstract void parse(String jsonData, Form form) throws JSONException;

	public List<Answer> getAnswers() {
		
		return answers;
	}

	public Form getForm() {
		
		return form;
	}

	protected void addAnswer(int questionId, String text) {
		// if(text != null) {
		answers.add(new Answer(text, questionId, form.getFormId()));
		// }
	}
	
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public byte getTag() {
		
		return tag;
	}

	public void setTag(byte tag) {
		this.tag = tag;
	}
}
