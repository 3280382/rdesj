/** 
 * @(#)RequestUtils.java 1.0.0 2010-12-30 04:52:01  
 *  
 * Copyright 2010 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

/**
 * Class RequestUtils 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2010-12-30 04:52:01
 */
public final class RequestUtils {

	public static final String MESSAGES_ATTRIBUTE = "messages";

	public static final String AUTH_COOKIE_NAME = "medivaultUser";

	public static final String CURRENT_USER = "currentUser";

	public static final String CURRENT_PROFILE = "currentProfile";

	public static final String PROFILE_LIST = "profileList";
	
	public static final String ALERT_MESSAGE_COUNT = "alertMessageCount";

	public static final String IS_ADMINISTRATOR = "isAdministrator";

	private RequestUtils() {
	}

	public static Long getLongParameter(final HttpServletRequest request,
			final String name, final boolean allowEmpty)
			throws ServletRequestBindingException {

		try {
			return ServletRequestUtils.getLongParameter(request, name);
		} catch (ServletRequestBindingException e) {
			if (!allowEmpty || !isParameterBlank(request, name)) {
				throw new ServletRequestBindingException(
						"No value provided for parameter " + name);
			}
		}

		return null;
	}

	public static Integer getIntParameter(final HttpServletRequest request,
			final String name, final boolean allowEmpty)
			throws ServletRequestBindingException {

		try {
			return ServletRequestUtils.getIntParameter(request, name);
		} catch (ServletRequestBindingException e) {
			if (!allowEmpty || !isParameterBlank(request, name)) {
				throw new ServletRequestBindingException(
						"No value provided for parameter " + name);
			}
		}

		return null;
	}

	public static Long[] getLongParameters(final HttpServletRequest request,
			final String name, final boolean allowEmpty)
			throws ServletRequestBindingException {

		final long[] params = ServletRequestUtils.getLongParameters(request,
				name);

		if ((params == null || params.length == 0) && !allowEmpty) {
			throw new ServletRequestBindingException(
					"Null or empty params in request for parameter " + name);
		}

		final Long[] _params = new Long[params.length];
		for (int i = 0; i < params.length; i++) {
			_params[i] = Long.valueOf(params[i]);
		}

		return _params;
	}

	public static Boolean getBooleanParameter(final HttpServletRequest request,
			final String name, final boolean allowEmpty)
			throws ServletRequestBindingException {

		try {
			return ServletRequestUtils.getBooleanParameter(request, name);
		} catch (ServletRequestBindingException e) {
			if (!allowEmpty || !isParameterBlank(request, name)) {
				throw new ServletRequestBindingException(
						"No value provided for parameter " + name);
			}
		}

		return null;
	}

	private static boolean isParameterBlank(final HttpServletRequest request,
			final String name) {
		return StringUtils.isBlank(request.getParameter(name));
	}
	
	public static Long[] getLongParametersFromString(final HttpServletRequest request,
			final String name, final boolean allowEmpty)
			throws ServletRequestBindingException {

		final String params = ServletRequestUtils.getStringParameter(request, name);

		if (allowEmpty && isParameterBlank(request, name)) {
			throw new ServletRequestBindingException(
					"Null or empty params in request for parameter " + name);
		}
		
		String[] paramArray = params.split(",");

		final Long[] _params = new Long[paramArray.length];
		for (int i = 0; i < paramArray.length; i++) {
			_params[i] = Long.valueOf(paramArray[i]);
		}

		return _params;
	}
}
