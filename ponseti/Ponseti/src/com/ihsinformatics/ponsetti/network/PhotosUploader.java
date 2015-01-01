package com.ihsinformatics.ponsetti.network;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.ihsinformatics.ponsetti.database.data_access.FormDAO;
import com.ihsinformatics.ponsetti.database.data_access.PhotoDAO;
import com.ihsinformatics.ponsetti.database.pojos.Form;
import com.ihsinformatics.ponsetti.database.pojos.Photo;
import com.ihsinformatics.ponsetti.utils.Global;
import com.ihsinformatics.ponsetti.utils.enums.OperationType;
import com.ihsinformatics.ponsetti.utils.interfaces.OnOperationFinishedListener;

public class PhotosUploader implements ServerCommunicationAdapter {

	Context context;
	OnOperationFinishedListener operationFinishedListener;
	FormDAO formDAO;
	private int numberOfSubmitedForms;
	private int numberOfUploadablePhotos;

	public PhotosUploader(Context context, OnOperationFinishedListener operationFinishedListener) {
		this.context = context;
		numberOfSubmitedForms = 0;
		this.operationFinishedListener = operationFinishedListener;
		formDAO = new FormDAO(context);
	}

	public void upload() {
		uploadPhotosList(fetchUploadablePhotos());
	}
	
	private ArrayList<Photo> fetchUploadablePhotos() {
		ArrayList<Photo> photos = new PhotoDAO(context).getReadyToUploadPhotos();
		numberOfUploadablePhotos = photos.size();
		
		return photos;
	}
	
	@Override
	public synchronized void xmlrpcCallResponse(Object res, String requestId) {

		// TODO Auto-generated method stub
	}

	@Override
	public synchronized void xmlrpcCallResponse(Object res, int requestId) {
		numberOfSubmitedForms++;
		Object[] response = (Object[]) res;
		String icrId = response[0].toString();
		String timeStamp = response[1].toString();
		
		// String oldIcrId = requestId.substring(0, 11);
		Form form = formDAO.getForm(Form.COLUMN_ICR_ID +"= '"+icrId+"'");
		form.setTimeStamp(Integer.parseInt(timeStamp));
		formDAO.update(form, icrId);
		
		PhotoDAO photoDAO = new PhotoDAO(context);
		ArrayList<Photo> photos = photoDAO.getPhotos(Photo.COLUMN_PHOTO_ID+"='"+requestId+"'");
		if(photos.size()>0) {
			Photo photo = photos.get(0);
			photo.isUploaded(1); // 1 = true
			photoDAO.update(photo, icrId);
		}
		
		if(numberOfSubmitedForms == numberOfUploadablePhotos) {
			operationFinishedListener.onOperationFinished(OperationType.PHOTOS_UPLOAD);
		}
	}

	
	
	private void uploadPhotosList(ArrayList<Photo> photos) {
		
		if(photos!=null && photos.size()>0) {
			for(Photo photo:photos) {
				if(!photo.getParentNode().startsWith("OFF")) {
					new ServerCommunicator(this, context, photo.getPhotoId(), Global.UPLOAD_PHOTO).execute(Global.CLIENT_KEY, photo.getContent(), photo.getParentNode()+"_"+new Date(), photo.getParentNode());
				}
			}
		} else {
			operationFinishedListener.onOperationFinished(OperationType.PHOTOS_UPLOAD);
		}
	}
}
