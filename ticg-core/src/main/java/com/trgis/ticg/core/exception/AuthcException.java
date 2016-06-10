package com.trgis.ticg.core.exception;

public class AuthcException extends Exception {
	private static final long serialVersionUID = 1L;

	public AuthcException() {
		super();
		
	}

	public AuthcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public AuthcException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public AuthcException(String message) {
		super(message);
		
	}

	public AuthcException(Throwable cause) {
		super(cause);
		
	}
}
