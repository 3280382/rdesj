/** 
 * @(#)ListenerThreadExecutor.java 1.0.0 2011-3-17 20:14:57  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.util;

import java.lang.reflect.Method;

/**
 * Class ListenerThreadExecutor 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-3-17 20:14:57
 */
public class ListenerThreadExecutor extends Thread {

	private Object targetListener;

	private String method;

	private Object[] args;

	public ListenerThreadExecutor(Object targetListener, String method,
			Object... args) {
		super();
		this.targetListener = targetListener;
		this.method = method;
		this.args = args;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	//@Override
	public void run() {

		// get the type of every parameter
		Class<?> argTypes[] = getParameterTypes(args);

		try {
			// invoke the target method
			Method targetMethod = targetListener.getClass().getMethod(method,
					argTypes);
			targetMethod.invoke(targetListener, args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * TODO Purpose/description of method
	 * 
	 * @param args
	 * @return
	 * @throws RuntimeException
	 */
	private Class<?>[] getParameterTypes(Object... args)
			throws RuntimeException {
		if (args != null) {
			Class<?> parameterTypes[] = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				parameterTypes[i] = args[i].getClass();
			}
			return parameterTypes;
		} else {
			return null;
		}
	}

}
