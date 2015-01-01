package com.ihsinformatics.ponsetti.utils;

public class ValidationFailureInformation {
	private boolean isValid;
	private String errorMessage;
	private int viewId;

	public ValidationFailureInformation(boolean isValid) {
		super();
		this.isValid = isValid;
	}

	public ValidationFailureInformation(boolean isValid, String errorMessage) {
		super();
		this.isValid = isValid;
		this.errorMessage = errorMessage;
	}

	public ValidationFailureInformation(boolean isValid, String errorMessage, int viewId) {
		super();
		this.isValid = isValid;
		this.errorMessage = errorMessage;
		this.viewId = viewId;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getViewId() {
		return viewId;
	}

	public void setViewId(int viewId) {
		this.viewId = viewId;
	}

}
