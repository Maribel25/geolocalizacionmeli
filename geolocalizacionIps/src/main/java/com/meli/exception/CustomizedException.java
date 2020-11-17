package com.meli.exception;

import com.meli.model.DTOApplicationErrorData;


public class CustomizedException extends RuntimeException {
	
	private static final long serialVersionUID = 3414634370641558044L;
	private DTOApplicationErrorData applicationErrorData;

	public CustomizedException(int statusCode, String message){
		setApplicationErrorData(new DTOApplicationErrorData());
		getApplicationErrorData().setMessage(message);
		getApplicationErrorData().setStatusCode(statusCode);
	}
	
	public CustomizedException(String message){
		setApplicationErrorData(new DTOApplicationErrorData());
		getApplicationErrorData().setMessage(message);
	}
	
	
	public DTOApplicationErrorData getApplicationErrorData() {
		return applicationErrorData;
	}

	public void setApplicationErrorData(DTOApplicationErrorData applicationErrorData) {
		this.applicationErrorData = applicationErrorData;
	}
}
