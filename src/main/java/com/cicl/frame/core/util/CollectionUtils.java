/** 
 * @(#)CollectionUtils.java 1.0.0 2011-2-15 12:27:54  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.util;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Class CollectionUtils 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-2-15 12:27:54
 */
public class CollectionUtils extends
		org.apache.commons.collections.CollectionUtils {

	@SuppressWarnings("unchecked")
	public static <T> T getFromUniqueCollection(Collection<T> c) {
		if (c == null || c.size() == 0) {
			return null;
		}
		if (isUniqueCollection(c)) {
			return (T) get(c, 0);
		} else {
			throw new RuntimeException(
					"found more than one object in this collection, this collection size is: "
							+ size(c));
		}
	}

	@SuppressWarnings("unchecked")
	public static boolean isUniqueCollection(Collection c) {
		return (size(c) > 1) ? false : true;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Collection<T> c) {
		if (c == null) {
			return null;
		} else {
			T[] result = (T[]) Array.newInstance(c.getClass()
					.getComponentType(), c.size());
			int i = 0;
			for (T r : c) {
				result[i] = r;
				i++;
			}
			return result;
		}
	}

}
