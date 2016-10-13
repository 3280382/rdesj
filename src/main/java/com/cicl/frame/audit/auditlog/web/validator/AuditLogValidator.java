/** 
 * @(#)AuditLogValidator.java 1.0.0 2011-3-3 11:02:45  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.audit.auditlog.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.cicl.frame.audit.auditlog.entity.AuditLog;
import com.cicl.frame.audit.auditlog.service.AuditLogService;
import com.cicl.frame.core.web.validator.AbstractMessageAwareValidator;

/**
 * Class AuditLogValidator 
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 上午08:16:09
 */
@Component
public class AuditLogValidator extends AbstractMessageAwareValidator {
	@Autowired
	private AuditLogService service;

	private static final String VALIDATION_TEXT_PREFIX = null;

	public AuditLogValidator() {
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
		return AuditLog.class.isAssignableFrom(clazz);
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
		AuditLog entity = (AuditLog) target;
		
		// Validation when adding an object
		// Validation when editing

		// Validation both when adding or editing an object
		String field = null;		
		field = "userId";
		if ( service.isPropertyExist(entity.getId(), field, entity.getUserId()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"userId exists");
		}
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
		field = "userIp";
		if ( service.isPropertyExist(entity.getId(), field, entity.getUserIp()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"userIp exists");
		}
		field = "targetId";
		if ( service.isPropertyExist(entity.getId(), field, entity.getTargetId()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"targetId exists");
		}
		field = "targetEntityType";
		if ( service.isPropertyExist(entity.getId(), field, entity.getTargetEntityType()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"targetEntityType exists");
		}
		field = "targetName";
		if ( service.isPropertyExist(entity.getId(), field, entity.getTargetName()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"targetName exists");
		}
		field = "targetDesc";
		if ( service.isPropertyExist(entity.getId(), field, entity.getTargetDesc()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"targetDesc exists");
		}
		field = "targetSnapshot";
		if ( service.isPropertyExist(entity.getId(), field, entity.getTargetSnapshot()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"targetSnapshot exists");
		}
		field = "opType";
		if ( service.isPropertyExist(entity.getId(), field, entity.getOpType()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"opType exists");
		}
		field = "opTime";
		if ( service.isPropertyExist(entity.getId(), field, entity.getOpTime()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"opTime exists");
		}
		field = "opDesc";
		if ( service.isPropertyExist(entity.getId(), field, entity.getOpDesc()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"opDesc exists");
		}
	}
}
