/** 
 * @(#)IntegerUtils.java 1.0.0 2011-2-15 12:29:37  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.util;

/**
 * Class IntegerUtils 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-2-15 12:29:37
 */
public class IntegerUtils {

	public static int createInteger(Object obj) {
		if (obj == null) {
			return 0;
		}
		if (obj instanceof Object[]) {
			Object[] temp = (Object[]) obj;
			if (temp != null && temp.length > 0) {
				for (Object inner : temp) {
					if (inner instanceof Integer) {
						return inner == null ? 0 : ((Integer) inner).intValue();
					}
				}
				return 0;
			} else {
				return 0;
			}
		}

		if (obj instanceof Integer) {
			return obj == null ? 0 : ((Integer) obj).intValue();
		} else {
			return 0;
		}

	}

}
