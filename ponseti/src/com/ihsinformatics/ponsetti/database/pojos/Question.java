package com.ihsinformatics.ponsetti.database.pojos;

import java.io.Serializable;

public class Question implements Serializable {

	/**
	 * Generated serial version uid
	 */
	private static final long serialVersionUID = -6300198294014151485L;
	
	public static String COLUMN_QUESTION_ID = "question_id";
	public static String COLUMN_TEXT = "question_text";
	public static String COLUMN_TYPE_ID = "type_id";
	public static String COLUMN_FIELD_NAME = "field_name";
	
	private String typeId;
	private String text;
	private String fieldName;
	private int questionId;

	public Question(String typeId, String text) {
		super();
		this.typeId = typeId;
		this.text = text;
	}

	public Question(int questionId, String typeId, String text, String fieldName) {
		super();
		this.questionId = questionId;
		this.typeId = typeId;
		this.text = text;
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

}
