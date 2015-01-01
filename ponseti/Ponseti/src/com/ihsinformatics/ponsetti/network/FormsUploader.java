package com.ihsinformatics.ponsetti.network;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.data_access.PhotoDAO;
import com.ihsinformatics.ponsetti.database.pojos.Answer;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.database.pojos.Photo;
import com.ihsinformatics.ponsetti.database.pojos.Question;
import com.ihsinformatics.ponsetti.screens.AddVisit;
import com.ihsinformatics.ponsetti.screens.CreatePatient;
import com.ihsinformatics.ponsetti.utils.Global;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.utils.enums.OperationType;
import com.ihsinformatics.ponsetti.utils.interfaces.OnOperationFinishedListener;

public class FormsUploader implements ServerCommunicationAdapter {

	Context context;
	OnOperationFinishedListener operationFinishedListener;
	FormDAO formDAO;
	private int numberOfSubmitedForms;
	private int evaluatorFormsSize;
	private int patientFormsSize;
	private int numberOfCalls;
	
	public FormsUploader(Context context, OnOperationFinishedListener operationFinishedListener) {
		this.context = context;
		numberOfSubmitedForms = 0;
		this.operationFinishedListener = operationFinishedListener;
		formDAO = new FormDAO(context);
	}

	public void upload() {
		uploadFormsList(fetchEvaluatorForm());
	}
	
	private List<JSONObject> fetchEvaluatorForm() {
		List<JSONObject> jsonisedEvaluatorForms = new ArrayList<JSONObject>();
		List<Form> evaluatorForms = formDAO.getUploadableForms(FormsTypes.EVALUATOR_FORM);
		for(Form form:evaluatorForms) {
			jsonisedEvaluatorForms.add(form.getJsonizedFormData(context));
		}
		
		evaluatorFormsSize = jsonisedEvaluatorForms.size();
		return jsonisedEvaluatorForms;
	}
	
	private List<JSONObject> fetchPatientForm() {
		List<JSONObject> jsonisedPatientForms = new ArrayList<JSONObject>();
		List<Form> patientForms = formDAO.getUploadableForms(FormsTypes.PATIENT_FORM);
		for(Form form:patientForms) {
			jsonisedPatientForms.add(form.getJsonizedFormData(context));
		}
		patientFormsSize = jsonisedPatientForms.size();
		return jsonisedPatientForms;
	}
	
	private List<JSONObject> fetchVisitForm() {
		List<JSONObject> jsonisedVisitForms = new ArrayList<JSONObject>();
		List<Form> visitForms = formDAO.getUploadableForms(FormsTypes.VISIT_FORM);
		for(Form form:visitForms) {
			jsonisedVisitForms.add(form.getJsonizedFormData(context));
		}
		return jsonisedVisitForms;
	}
	
	@Override
	public synchronized void xmlrpcCallResponse(Object res, String requestId) {
		numberOfSubmitedForms++;
		Object[] response = (Object[]) res;
		String newIcrId = response[0].toString();
		String timeStamp = response[1].toString();
		
		String oldIcrId = requestId.substring(0, 11);
		Form form = formDAO.getForm(Form.COLUMN_ICR_ID +"= '"+oldIcrId+"'");
		form.setIcrId(newIcrId);
		form.setTimeStamp(Integer.parseInt(timeStamp));
		formDAO.update(form, oldIcrId);
		
		PhotoDAO photoDAO = new PhotoDAO(context);
		ArrayList<Photo> photos = photoDAO.getPhotos(Photo.COLUMN_PARENT_NODE+"='"+oldIcrId+"'");
		for(Photo photo:photos) {
			photo.setParentNode(newIcrId);
			photoDAO.update(photo, oldIcrId);
		}
		
		List<Answer> answers;
		AnswerDAO answerDAO = new AnswerDAO(context);
		// answers = answerDAO.getAnswers(Question.COLUMN_QUESTION_ID+"="+AddVisit.QUESTION_PARENT_NODE +"and"+Answer.COLUMN_TEXT+"='"+ oldIcrId+"'", null);
		answers = answerDAO.getAnswers(Answer.COLUMN_TEXT+"='"+ oldIcrId+"'", null);
		for(Answer answer : answers) {
			answer.setText(newIcrId);	
		}
		if(answers.size()>0) {
			answerDAO.update(answers);
		}
		
		
		
		
		if(numberOfSubmitedForms == evaluatorFormsSize) {
			uploadFormsList(fetchPatientForm());
		} else if (numberOfSubmitedForms == evaluatorFormsSize+patientFormsSize) {
			uploadFormsList(fetchVisitForm());
		} else {
			operationFinishedListener.onOperationFinished(OperationType.FORMS_UPLOAD);
		}
	}

	@Override
	public synchronized void xmlrpcCallResponse(Object res, int requestId) {
		// TODO Auto-generated method stub
	}
	
	private void uploadFormsList(List<JSONObject> jsonDataList) {
		numberOfCalls++;
		if(jsonDataList!=null && jsonDataList.size()>0) {
			for(JSONObject jsonData:jsonDataList) {
				String dataToSend = Tools.getInstance().deflateAndEncodeData(jsonData.toString());
				try {
					new ServerCommunicator(this, context, jsonData.getString("title"), Global.UPLOAD_JSON_FORM).execute(Global.CLIENT_KEY, dataToSend);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else {
			if(numberOfCalls == 1) {
				uploadFormsList(fetchPatientForm());
			} else if(numberOfCalls == 2) {
				uploadFormsList(fetchVisitForm());
			}  else {
				operationFinishedListener.onOperationFinished(OperationType.FORMS_UPLOAD);
			}
		}
	}
	
}
