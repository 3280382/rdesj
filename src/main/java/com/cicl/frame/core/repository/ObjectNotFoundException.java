/** 
 * @(#)ObjectNotFoundException.java 1.0.0 2010-12-30 04:23:16  
 *  
 * Copyright 2010 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.repository;

import com.cicl.frame.core.AbstractBaseRuntimeException;

/**
 * Class ObjectNotFoundException 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2010-12-30 04:23:16
 */
public class ObjectNotFoundException extends AbstractBaseRuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String message) {
		super(message);
	}

	public ObjectNotFoundException(Throwable cause) {
		super(cause);
	}

	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
