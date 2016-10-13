/** 
 * @(#)AbstractBaseRuntimeException.java 1.0.0 2010-12-30 03:57:32  
 *  
 * Copyright 2010 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core;

/**
 * Class AbstractBaseRuntimeException
 *  
 *
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2010-12-30 03:57:32  
 */
public abstract class AbstractBaseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AbstractBaseRuntimeException(final String message) {
    	super(message);
    }

    public AbstractBaseRuntimeException(final Throwable cause) {
    	super(cause);
    }

    public AbstractBaseRuntimeException(final String message, final Throwable cause) {
    	super(message, cause);
    }
}
