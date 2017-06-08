package org.pawan.messenger.exception;

public class DataNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7390402985240277436L;
	
	public DataNotFoundException(String message){
		super(message);
	}

}
