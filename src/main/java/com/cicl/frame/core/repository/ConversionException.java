/** 
 * @(#)ConversionException.java 1.0.0 2011-4-13 16:31:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.repository;

import com.cicl.frame.core.AbstractBaseRuntimeException;


/**
 * Class ConversionException
 *  
 *
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-4-13 16:31:42  
 */
public class ConversionException extends AbstractBaseRuntimeException {
	private static final long serialVersionUID = 1L;

	public ConversionException(String message) {
		super(message);
	}

	public ConversionException(Throwable cause) {
		super(cause);
	}

	public ConversionException(String message, Throwable cause) {
		super(message, cause);
	}
}
