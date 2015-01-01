package com.ihsinformatics.ponsetti.database.pojos;

import java.io.Serializable;

public class Photo implements Serializable {

	
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 1395706624298656490L;
	
	public static String COLUMN_PHOTO_ID = "photo_id";
	public static String COLUMN_PARENT_NODE = "parent_node";
	public static String COLUMN_IS_UPLOADED = "is_uploaded";
	public static String COLUMN_CONTENT = "content";

	int photoId;
	String parentNode;
	String content;
	int isUploaded;

	public Photo(int photoId, String content, int isUploaded, String parentNode) {
		super();
		this.photoId = photoId;
		this.parentNode = parentNode;
		this.isUploaded = isUploaded; // 1 = true
		this.content = content;
	}
	
	public Photo(String content, int isUploaded, String parentNode) {
		super();
		this.parentNode = parentNode;
		this.isUploaded = isUploaded;
		this.content = content;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getParentNode() {
		return parentNode;
	}

	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getUploaded() {
		return isUploaded;
	}

	public void isUploaded(int isUploaded) {
		this.isUploaded = isUploaded;
	}

}
