package com.ihsinformatics.ponsetti.database.pojos;

import java.io.Serializable;

public class Answer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2670076779502035019L;
	public static String COLUMN_ANSWER_ID = "answer_id";
	public static String COLUMN_TEXT = "answer_text";
	public static String COLUMN_QUESTION_ID = Question.COLUMN_QUESTION_ID;
	public static String COLUMN_FORM_ID = "form_id";

	int answerId;
	String text;
	int questionId;
	int formId;

	public Answer(int answerId, String text, int question_id, int formId) {
		super();
		this.answerId = answerId;
		this.text = text;
		this.questionId = question_id;
		this.formId = formId;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public Answer(String text, int question_id, int formId) {
		super();
		this.text = text;
		this.questionId = question_id;
		this.formId = formId;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getQuestion_id() {
		return questionId;
	}

	public void setQuestion_id(int question_id) {
		this.questionId = question_id;
	}

}
