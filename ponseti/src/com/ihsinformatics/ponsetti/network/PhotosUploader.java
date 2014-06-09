package com.ihsinformatics.ponsetti.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.data_access.PhotoDAO;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.FormsTypes;
import com.ihsinformatics.ponsetti.database.pojos.Photo;
import com.ihsinformatics.ponsetti.utils.Global;
import com.ihsinformatics.ponsetti.utils.enums.OperationType;
import com.ihsinformatics.ponsetti.utils.interfaces.OnOperationFinishedListener;

public class PhotosUploader implements ServerCommunicationAdapter {

	Context context;
	OnOperationFinishedListener operationFinishedListener;
	FormDAO formDAO;
	private int numberOfSubmitedForms;

	public PhotosUploader(Context context, OnOperationFinishedListener operationFinishedListener) {
		this.context = context;
		areAllrequestsSent = false;
		isAtleastOneRequestSent = false;
		numberOfSubmitedForms = 0;
		this.operationFinishedListener = operationFinishedListener;
		formDAO = new FormDAO(context);
	}

	public void upload() {
		uploadPhotosList(fetchUploadablePhotos());
	}
	
	private ArrayList<Photo> fetchUploadablePhotos() {
		ArrayList<Photo> photos = new PhotoDAO(context).getUploadablePhoto();
		
		return photos;
	}
	
	@Override
	public synchronized void xmlrpcCallResponse(Object res, String requestId) {
		numberOfSubmitedForms++;
		Object[] response = (Object[]) res;
		String icrId = response[0].toString();
		String timeStamp = response[1].toString();
		
		// String oldIcrId = requestId.substring(0, 11);
		Form form = formDAO.getForm(Form.COLUMN_ICR_ID +"= '"+icrId+"'");
		form.setTimeStamp(Integer.parseInt(timeStamp));
		formDAO.update(form, icrId);
		
		PhotoDAO photoDAO = new PhotoDAO(context);
		Photo photo = photoDAO.getPhoto(Photo.COLUMN_PARENT_NODE+"='"+icrId+"'");
		if(photo!=null) {
			photo.isUploaded(1); // 1 = true
			photoDAO.update(photo, icrId);
		}
		
		if(areAllrequestsSent && numberOfSubmitedForms == numberOfCalls) {
			operationFinishedListener.onOperationFinished(OperationType.PHOTOS_UPLOAD);
		}
	}

	@Override
	public synchronized void xmlrpcCallResponse(Object res, int requestId) {
		// TODO Auto-generated method stub
	}

	private int numberOfCalls;
	private boolean areAllrequestsSent;private boolean isAtleastOneRequestSent;
	
	private void uploadPhotosList(ArrayList<Photo> photos) {
		
		if(photos!=null && photos.size()>0) {
			for(Photo photo:photos) {
				if(!photo.getParentNode().startsWith("OFF")) {
					numberOfCalls++;
					new ServerCommunicator(this, context, photo.getParentNode(), Global.UPLOAD_PHOTO).execute(Global.CLIENT_KEY, photo.getContent(), photo.getParentNode()+photo.getPhotoId(), photo.getParentNode());
				}
			}
			if(isAtleastOneRequestSent) {
				areAllrequestsSent = true;
			} else {
				operationFinishedListener.onOperationFinished(OperationType.PHOTOS_UPLOAD);
			}
			
		} else {
			operationFinishedListener.onOperationFinished(OperationType.PHOTOS_UPLOAD);
		}
	}
}
