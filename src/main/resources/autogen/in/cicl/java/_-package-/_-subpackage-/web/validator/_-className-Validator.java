/** 
 * @(#)${className}Validator.java 1.0.0 2011-3-3 11:02:45  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package ${parentPackage}.${package}.${subpackage}.web.validator;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.cicl.frame.core.web.validator.AbstractMessageAwareValidator;
import ${parentPackage}.${package}.${subpackage}.entity.${className};
import ${parentPackage}.${package}.${subpackage}.service.${className}Service;

/**
 * Class ${className}Validator 
 * 
 * @author ${author}
 * @version ${revision}, $Date: 2011-5-6 上午08:16:09
 */
@Component
public class ${className}Validator extends AbstractMessageAwareValidator {
	@Autowired
	private ${className}Service service;

	private static final String VALIDATION_TEXT_PREFIX = null;

	public ${className}Validator() {
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
		return ${className}.class.isAssignableFrom(clazz);
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
		${className} entity = (${className}) target;
		
		// Validation when adding an object
		// Validation when editing

		// Validation both when adding or editing an object
		String field = null;		
		<#list col as r>
		field = "${r.name}";
		if ( service.isPropertyExist(entity.getId(), field, entity.get${r.name?cap_first}()) ) {
			errors.rejectValue(field, ERRORS_FIELD_EXISTS, getMessageArray(getValidationTextPrefix(errors) + field),
					"${r.name} exists");
		}
		</#list>
	}
}
