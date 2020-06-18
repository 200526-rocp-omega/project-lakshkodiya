package com.revature.exceptions;

public class IllegalParameterException extends AuthorizationException{

	public IllegalParameterException() {
		super();
		
	}

	public IllegalParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		}

	public IllegalParameterException(String message, Throwable cause) {
		super(message, cause);
		}

	public IllegalParameterException(String message) {
		super(message);
		}

	public IllegalParameterException(Throwable cause) {
		super(cause);
		}

}
