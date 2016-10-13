/** 
 * @(#)AuditLogSearchFormValidator.java 1.0.0 2011-3-3 11:02:45  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.audit.auditlog.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.cicl.frame.audit.auditlog.web.form.AuditLogSearchForm;
import com.cicl.frame.core.web.validator.AbstractMessageAwareValidator;

@Component
public class AuditLogSearchFormValidator extends AbstractMessageAwareValidator {

	private static final String VALIDATION_TEXT_PREFIX = null;

	public AuditLogSearchFormValidator() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	//@Override
	public boolean supports(Class clazz) {
		return AuditLogSearchForm.class.isAssignableFrom(clazz);
	}
	
	public String getValidationTextPrefix(Errors errors){
		if(VALIDATION_TEXT_PREFIX==null) {
			return errors.getObjectName()+MESSAGE_KEY_SEPARATOR;
		}
		else {
			return VALIDATION_TEXT_PREFIX;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	//@Override
	public void validate(Object target, Errors errors) {
		validateRequired(requiredFields(), errors, getValidationTextPrefix(errors));
	}

	private String[] requiredFields() {
		return new String[] {};
	}
}
