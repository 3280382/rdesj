/** 
 * @(#)QueryObject.java 1.0.0 2011-2-14 14:29:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.repository.query;

import com.cicl.frame.core.entity.Persistable;

/**
 * Class QueryObject 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-2-14 14:29:05
 */
public interface QueryObject<T extends Persistable> {
	/**
	 * Return the instance of entity's class type.
	 * 
	 * @return class type
	 */
	public Class<T> getEntityClass();

	/**
	 * Set the instance of entity's class type.
	 * 
	 * @param class type
	 */
	public void setEntityClass(Class<T> entityClass);

	/**
	 * Return the instance of QueryProjections.
	 * 
	 * @return the instance of QueryProjections
	 */
	public QueryProjections getQueryProjections();

	/**
	 * Set the instance of QueryProjections.
	 * 
	 * @param the
	 *            instance of QueryProjections
	 */
	public void setQueryProjections(QueryProjections queryProjection);

	/**
	 * Return the instance of QueryParam.
	 * 
	 * @return the instance of QueryParam
	 */
	public QueryParam getQueryParam();

	/**
	 * Set the instance of QueryParam.
	 * 
	 * @param the
	 *            instance of QueryParam
	 */
	public void setQueryParam(QueryParam queryParam);

}
