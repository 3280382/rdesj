/** 
 * @(#)AbstractMessageAwareValidator.java 1.0.0 2011-1-19 05:06:41  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/** 
 * Class AbstractMessageAwareValidator 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-1-19 05:06:41
 */
public abstract class AbstractMessageAwareValidator implements Validator,
		MessageSourceAware {
	@Autowired
	private MessageSource messageSource;
	public static final String MESSAGE_KEY_SEPARATOR = ".";
	public static final String ERRORS_REQUIRED = "errors.required";
	public static final String ERRORS_FIELD_EXISTS = "errors.field_exists";

	public static final String DEFAULT_REQUIRED_MSG = "Required field value missing";

	public final void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	protected final String getMessage(final String messageKey,
			final Object[] args) {
		return messageSource.getMessage(messageKey, args, LocaleContextHolder
				.getLocale());
	}

	protected final String[] getMessageArray(final String messageKey) {
		return new String[] { getMessage(messageKey, null) };
	}

	protected final void validateRequired(String[] requiredFields,
			Errors errors, String validationTextPrefix) {
		for (String field : requiredFields) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, field,
					ERRORS_REQUIRED, getMessageArray(validationTextPrefix
							+ field), DEFAULT_REQUIRED_MSG);
		}

	}

}
