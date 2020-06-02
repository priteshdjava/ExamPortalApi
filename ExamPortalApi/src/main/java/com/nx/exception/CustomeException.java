package com.nx.exception;

public class CustomeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public CustomeException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
