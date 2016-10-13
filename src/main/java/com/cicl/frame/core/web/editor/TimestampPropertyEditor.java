/** 
 * @(#)DatePropertyEditor.java 1.0.0 2011-1-21 01:42:56  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.editor;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.PropertyEditorRegistry;

import com.cicl.frame.core.util.date.DateConstant;

/**
 * Class DateSerializer 
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-2 06:20:02
 */
public class TimestampPropertyEditor extends PropertyEditorSupport {
	private final DateFormat dateFormat;

	private final boolean allowEmpty;

	public TimestampPropertyEditor(final String datePattern, final boolean allowEmpty) {
		super();
		this.dateFormat = new SimpleDateFormat(datePattern);
		this.allowEmpty = allowEmpty;
	}
	
	public static void registerCustomEditor(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(Timestamp.class, new TimestampPropertyEditor(DateConstant.DATE_TIME_FORMAT, true)); 
	}

	//@Override
	public void setAsText(final String text) {

		if (this.allowEmpty && StringUtils.isBlank(text)) {
			setValue(null);
		} else {

			try {
				setValue(dateFormat.parse(text));
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: "
						+ ex.getMessage(), ex);
			}

		}
	}

	/**
	 * Format the Calendar as String, using the specified DateFormat.
	 */
	//@Override
	public String getAsText() {

		final Timestamp value = (Timestamp) getValue();

		String retVal = "";
		if (value != null) {
			retVal = this.dateFormat.format(value);
		}

		return retVal;
	}
}
