package com.ihsinformatics.ponsetti.network;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.utils.exceptions.UnSupportedFormTypeException;
import com.ihsinformatics.ponsetti.parser.FormParser;
import com.ihsinformatics.ponsetti.parser.FormParserBuilder;
import com.ihsinformatics.ponsetti.screens.MainMenu;
import com.ihsinformatics.ponsetti.utils.Global;
import com.ihsinformatics.ponsetti.utils.Tools;
import com.ihsinformatics.ponsetti.utils.interfaces.OnDataParsedListener;

public class PhotosGetter implements ServerCommunicationAdapter {
	
	HashMap<String, String> formsList;
	Tools tools;
	List<FormParser> parsedForms;
	List<Form> forms;
	Set<Integer> newFormsIndexes;
	int requestNumber;
	private OnDataParsedListener formsReceivedListener;
	Context context;
	MainMenu mainMenu;
	DecimalFormat decimalFormat;
	FormDAO formDAO;
	
	public PhotosGetter(HashMap<String, String> formsList, OnDataParsedListener formsReceivedListener) {
		this.formsList = formsList;
		tools = Tools.getInstance();
		this.formsReceivedListener = formsReceivedListener;
		decimalFormat = new DecimalFormat("#");
		
	}
	
	public void start(Context context) {
		formDAO = new FormDAO(context);
		this.mainMenu = (MainMenu)context;
		forms = new ArrayList<Form>();
		parsedForms = new ArrayList<FormParser>();
		newFormsIndexes = new HashSet<Integer>();
		requestNumber = 1;
		
		Iterator<String> itr = formsList.keySet().iterator();
		String temp;
		
		while(itr.hasNext()) {
			temp = itr.next();
			if(!temp.startsWith("CCW")) {
				
				Form form = formDAO.getForm(Form.COLUMN_ICR_ID+" = '"+temp+"' and "+Form.COLUMN_TIME_STAMP +"< "+Integer.parseInt(formsList.get(temp)));			
				if(form != null) {
					form.setTimeStamp(Integer.parseInt(formsList.get(temp)));
					forms.add(form);
					
				} else {
					form = formDAO.getForm(Form.COLUMN_ICR_ID+" = '"+temp+"'");
					if(form == null) {
						forms.add(new Form(temp, Integer.parseInt(formsList.get(temp)), null));
						newFormsIndexes.add(forms.size()-1);
					}
					
				}
			}
		}
		
		if(forms.size() > 0) {
			for(int i = 0; i<forms.size(); i++) {
				new ServerCommunicator(this, context, i, Global.DOWNLOAD_JSON_FORM).execute(Global.CLIENT_KEY, forms.get(i).getIcrId());
			}
		} else {
			//formsReceivedListener.onDataParsed(parsedForms, requestNumber);
		}
		
	}
	
	@Override
	public synchronized void xmlrpcCallResponse(Object res, int requestId) {
		try{ 
			float donePercent =  ((float)requestNumber/forms.size())*100f;
			String message = "Downloading forms...\n"+ decimalFormat.format(donePercent) + "% done";
			mainMenu.setDialogText(message);
			String jsonData = tools.decodedAndInflateData(res.toString());
			String formType = checkFormType(jsonData);
			Form tempForm = null;
			if(formType != null) {
				tempForm = forms.get(requestId);
				
				if(newFormsIndexes.contains(requestId)) {
					tempForm.setTypeId(formType);
					formDAO.insert(tempForm);
					tempForm = formDAO.getForm(Form.COLUMN_ICR_ID+" = '"+tempForm.getIcrId()+"'");
					
					parsedForms.add(new FormParserBuilder(formType).buildFormParser(jsonData, tempForm, FormParser.TAG_NEW));
				} else {
					formDAO.update(tempForm, tempForm.getIcrId());
					parsedForms.add(new FormParserBuilder(formType).buildFormParser(jsonData, forms.get(requestId), FormParser.TAG_EXISTING));
				}
			}
			
			if(requestNumber == forms.size()) {
				//formsReceivedListener.onDataParsed(parsedForms, requestNumber);
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
		
	}
}
