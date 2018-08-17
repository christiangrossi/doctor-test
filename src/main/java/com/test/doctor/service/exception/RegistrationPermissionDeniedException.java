package com.test.doctor.service.exception;

public class RegistrationPermissionDeniedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RegistrationPermissionDeniedException(String message) {
		 super(message);
	}
	
	public RegistrationPermissionDeniedException(String message, Throwable cause) {
		super(message, cause);
	}
	

}
