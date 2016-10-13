/** 
 * @(#)SysUserValidator.java 1.0.0 2011-3-3 11:02:45  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.sysuser.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.cicl.frame.core.web.validator.AbstractMessageAwareValidator;
import com.cicl.frame.security.sysuser.entity.SysUser;
import com.cicl.frame.security.sysuser.service.SysUserService;

/**
 * Class SysUserValidator 
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 上午08:16:09
 */
@Component
public class SysUserValidator extends AbstractMessageAwareValidator {
	@Autowired
	private SysUserService service;

	private static final String VALIDATION_TEXT_PREFIX = null;

	public SysUserValidator() {
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
		return SysUser.class.isAssignableFrom(clazz);
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
		SysUser entity = (SysUser) target;
		
		// Validation when adding an object
		// Validation when editing

		// Validation both when adding or editing an object
		String field = null;		
		field = "username";
		if ( service.isPropertyExist(entity.getId(), field, entity.getUsername()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"username exists");
		}
		field = "loginName";
		if ( service.isPropertyExist(entity.getId(), field, entity.getLoginName()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"loginName exists");
		}
		field = "email";
		if ( service.isPropertyExist(entity.getId(), field, entity.getEmail()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"email exists");
		}
		field = "code";
		if ( service.isPropertyExist(entity.getId(), field, entity.getCode()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"code exists");
		}
		field = "mobile";
		if ( service.isPropertyExist(entity.getId(), field, entity.getMobile()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"mobile exists");
		}
		field = "phone";
		if ( service.isPropertyExist(entity.getId(), field, entity.getPhone()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"phone exists");
		}
	}
}
