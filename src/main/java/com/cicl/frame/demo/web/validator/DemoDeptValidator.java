/** 
 * @(#)DemoDeptValidator.java 1.0.0 2011-3-3 11:02:45  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.cicl.frame.core.web.validator.AbstractMessageAwareValidator;
import com.cicl.frame.demo.entity.DemoDept;
import com.cicl.frame.demo.service.DemoDeptService;

/**
 * Class DemoDataValidator 
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 上午08:16:09
 */
@Component
public class DemoDeptValidator extends AbstractMessageAwareValidator {
	@Autowired
	private DemoDeptService service;

	private static final String VALIDATION_TEXT_PREFIX = null;

	public DemoDeptValidator() {
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
		return DemoDept.class.isAssignableFrom(clazz);
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
		DemoDept entity = (DemoDept) target;
		// Validation when adding an object
		// Validation when editing

		// Validation both when adding or editing an object
		String field = "name";
		if ( service.isPropertyExist(entity.getId(), field, entity.getName()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"name exists");
		}
		
		field = "code";
		if ( service.isPropertyExist(entity.getId(), field, entity.getCode()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"code exists");
		}
	}
}
