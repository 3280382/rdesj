/** 
 * @(#)AbstractMynderForm.java 1.0.0 2011-1-19 03:52:01  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.web.form;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Class AbstractMynderForm
 *  
 *
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-1-19 03:52:01  
 */
@Component
public abstract class AbstractForm implements Form {	
	private static final long serialVersionUID = 6971382203422172627L;

	private String method;
	@Autowired
	private MessageSource messageSource;

	public AbstractForm(){
		this.setMethod("search");
	}

	private boolean formSubmission;

	//@Override
	public void clean() {
		// default do nothing
	}

	//@Override
	public String getMethod() {
		 return method;
	}

	//@Override
	public void setMethod(String method) {
		 this.method = method;
	}
	

	/**
	 * @return the formSubmission
	 */
	public final boolean isFormSubmission() {
		return formSubmission;
	}

	/**
	 * @param formSubmission
	 *            the formSubmission to set
	 */
	public final void setFormSubmission(boolean formSubmission) {
		this.formSubmission = formSubmission;
	}
	
    /**
     * TODO Purpose/description of method 
     *
     * @param code
     * @return
     */
	public final String getMessage(String code) { 
            return getMessageSource().getMessage(code, null, null);
    }
    
    /**
     * TODO Purpose/description of method 
     *
     * @return
     */
    public MessageSource getMessageSource() {
        return messageSource;
    }
}
