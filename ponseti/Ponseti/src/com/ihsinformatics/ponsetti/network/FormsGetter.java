package com.ihsinformatics.ponsetti.network;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ihsinformatics.ponsetti.database.data_access.AnswerDAO;
import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.data_access.PhotoDAO;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.database.pojos.Photo;
import com.ihsinformatics.ponsetti.parser.FormParser;
import com.ihsinformatics.ponsetti.parser.FormParserBuilder;
import com.ihsinformatics.ponsetti.screens.MainMenu;
import com.ihsinformatics.ponsetti.utils.Global;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.utils.exceptions.UnSupportedFormTypeException;
import com.ihsinformatics.ponsetti.utils.interfaces.OnDataParsedListener;

public class FormsGetter implements ServerCommunicationAdapter {
	
	private Tools tools;
	private List<FormParser> parsedForms;
	private List<Form> downloadableForms;
	private int requestNumber;
	private OnDataParsedListener formsReceivedListener;
	private Context context;
	private MainMenu mainMenu;
	private DecimalFormat decimalFormat;
	private boolean isPhotoDownloadStarted;
	private FormDAO formDAO;
	private PhotoDAO photoDAO;
	private AnswerDAO answerDAO;
	
	public FormsGetter(List<Form> downloadableForms, OnDataParsedListener formsReceivedListener) {
		this.downloadableForms = downloadableForms;
		tools = Tools.getInstance();
		this.formsReceivedListener = formsReceivedListener;
		decimalFormat = new DecimalFormat("#");
	}
	
	public void start(Context context) {
		this.context = context;
		formDAO = new FormDAO(context);
		answerDAO = new AnswerDAO(context);
		photoDAO = new PhotoDAO(context);
		this.mainMenu = (MainMenu)context;
		parsedForms = new ArrayList<FormParser>();
		requestNumber = 1;
		isPhotoDownloadStarted = false;
		
		downloadForms();
		
	}
	
	private void downloadForms() {
		if(downloadableForms.size() > 0) {
			Form tempForm;
			for(int i = 0; i<downloadableForms.size(); i++) {
				tempForm = downloadableForms.get(i);
				new ServerCommunicator(this, context, i, Global.DOWNLOAD_JSON_FORM).execute(Global.CLIENT_KEY, tempForm.getIcrId());
			}
		} else {
			formsReceivedListener.onDataParsed(downloadableForms, parsedForms, requestNumber);
		}
	}
	
	private void downloadPhotos() {
		Form tempForm;
		for(int i = 0; i<downloadableForms.size(); i++) {
			tempForm = downloadableForms.get(i);
			ArrayList<Photo> photosList = photoDAO.getPhotos(Photo.COLUMN_PARENT_NODE+"='"+tempForm.getIcrId()+"'");
			Tools tools = Tools.getInstance();
			String[] bitmapHashes = null;
			if(photosList!=null) {
				bitmapHashes = new String[photosList.size()];
				for(int j=0; j<photosList.size(); j++) {
					bitmapHashes[j] = tools.generateBitmapHash(tools.getBitmapByteArrayFromEncodedContent(photosList.get(j).getContent()));
				}
			}
			
			
			new ServerCommunicator(this, context, tempForm.getIcrId(), Global.DOWNLOAD_PHOTO).execute(Global.CLIENT_KEY, tempForm.getIcrId(), bitmapHashes);
		}
	}
	
	@Override
	public synchronized void xmlrpcCallResponse(Object res, int requestId) {
		try{
			// if(!isPhotoDownloadStarted) {
				float donePercent =  ((float)requestNumber/downloadableForms.size())*100f;
				String message = "Downloading forms...\n"+ decimalFormat.format(donePercent) + "% done";
				mainMenu.setDialogText(message);
				
				String jsonData = tools.decodedAndInflateData(res.toString());
				String formType = checkFormType(jsonData);
				Form tempForm = null;
				if(formType != null) {
					tempForm = downloadableForms.get(requestId);
					if(tempForm.isNew()) {
						tempForm.setTypeId(formType);
						formDAO.insert(tempForm);
						tempForm = formDAO.getForm(Form.COLUMN_ICR_ID+" = '"+tempForm.getIcrId()+"'");
						FormParser parsedForm = new FormParserBuilder(formType).buildFormParser(jsonData, tempForm, FormParser.TAG_NEW);
						answerDAO.insert(parsedForm.parse().getAnswers());
					} else {
						formDAO.update(tempForm, tempForm.getIcrId());
						FormParser parsedForm = new FormParserBuilder(formType).buildFormParser(jsonData, tempForm, FormParser.TAG_EXISTING);
						answerDAO.update(parsedForm.parse().getAnswers());
					}
				}
				
				if(requestNumber == downloadableForms.size()) {
					// isPhotoDownloadStarted = true;
					downloadPhotos();
					
				}
				
		} catch(UnSupportedFormTypeException e) {
			e.printStackTrace();
		} finally {
			requestNumber ++;
		}
	}
	
	private String checkFormType(String jsonData) throws UnSupportedFormTypeException {
		JSONObject user;
		try {
			user = new JSONObject(jsonData);
			String formType = user.getString("type");
			if(formType.equals(FormsTypes.PATIENT_FORM)) {
				return FormsTypes.PATIENT_FORM;
			} else if(formType.equals(FormsTypes.VISIT_FORM)) {
				return FormsTypes.VISIT_FORM;
			} else if(formType.equals(FormsTypes.EVALUATOR_FORM)) {
				return FormsTypes.EVALUATOR_FORM;
			} else {
				throw new UnSupportedFormTypeException("Form type "+formType+" is not supported by this application yet");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void xmlrpcCallResponse(Object res, String requestId) {
		try{
			float donePercent =  ((float)(requestNumber-downloadableForms.size())/downloadableForms.size())*100f;
			String message = "Downloading photos...\n"+ decimalFormat.format(donePercent) + "% done";
			mainMenu.setDialogText(message);
			/*if(res instanceof Object[]) {
				Object[] tempResp = (Object[])res;
			} else */if(res instanceof HashMap<?, ?>) {
				HashMap<String, String> resp = (HashMap<String, String>) res;
				Iterator<String> itr = resp.keySet().iterator();
				String parentNode;
				while ( itr.hasNext()) {
					parentNode = itr.next();
					String photoContent = resp.get(parentNode);
					
					Photo tempPhoto = new Photo(photoContent, 1, requestId);
					photoDAO.insert(tempPhoto);
				}
				
			}
			
			/*if(tempResp.length == 2) {
				photoDAO = new PhotoDAO(context);
				Photo tempPhoto = new Photo(tempResp[1].toString(), 1, tempResp[0].toString());
				photoDAO.insert(tempPhoto);
			}*/
		
			if(requestNumber == (downloadableForms.size()*2)) {
				formsReceivedListener.onDataParsed(downloadableForms, parsedForms, requestNumber);
		 	}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			requestNumber ++;
		}
	}
}
