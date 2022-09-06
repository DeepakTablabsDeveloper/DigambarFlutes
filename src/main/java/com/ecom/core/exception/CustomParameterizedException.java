package com.ecom.core.exception;

import java.util.HashMap;
import java.util.Map;

public class CustomParameterizedException{

	String errorMessage;
	String errorCode;
	
	public CustomParameterizedException(String errorMessage){
	}

	
	
	
	public CustomParameterizedException(String errorMessage, String errorCode) {
		//super(errorMessage);
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}




	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}




	@Override
	public String toString() {
		return "CustomParameterizedException [errorMessage=" + errorMessage + ", errorCode=" + errorCode + "]";
	}
	
	
	

}