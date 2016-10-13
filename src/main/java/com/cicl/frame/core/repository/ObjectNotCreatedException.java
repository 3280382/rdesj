package com.cicl.frame.core.repository;

import com.cicl.frame.core.AbstractBaseRuntimeException;

public class ObjectNotCreatedException extends AbstractBaseRuntimeException {
	private static final long serialVersionUID = 1L;

	public ObjectNotCreatedException(String message) {
		super(message);
	}

	public ObjectNotCreatedException(Throwable cause) {
		super(cause);
	}

	public ObjectNotCreatedException(String message, Throwable cause) {
		super(message, cause);
	}
}
