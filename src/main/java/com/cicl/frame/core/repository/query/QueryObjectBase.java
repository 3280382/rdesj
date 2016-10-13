/** 
 * @(#)QueryObjectBase.java 1.0.0 2011-2-14 14:41:54  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.repository.query;

import com.cicl.frame.core.entity.Persistable;

/**
 * Class QueryObjectBase 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-2-14 14:41:54
 */
public class QueryObjectBase<T extends Persistable> implements QueryObject<T> {
	protected static final String CACHE_KEY = "QueryObject_cache";

	/**
	 * the instance of entity's class type
	 */
	private Class<T> entityClass;

	/**
	 * the instance of QueryProjections
	 */
	private QueryProjections queryProjections;

	/**
	 * the instance of QueryParam
	 */
	private QueryParam queryParam;

	/**
	 * Return the instance of entity's class type.
	 * 
	 * @return class type
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * Set the instance of entity's class type.
	 * 
	 * @param class type
	 */
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Return the instance of QueryProjections.
	 * 
	 * @return the instance of QueryProjections
	 */
	public QueryProjections getQueryProjections() {
		return queryProjections;
	}

	/**
	 * Set the instance of QueryProjections.
	 * 
	 * @param the
	 *            instance of QueryProjections
	 */
	public void setQueryProjections(QueryProjections queryProjections) {
		this.queryProjections = queryProjections;
	}

	/**
	 * Return the instance of QueryParam.
	 * 
	 * @return the instance of QueryParam
	 */
	public QueryParam getQueryParam() {
		return queryParam;
	}

	/**
	 * Set the instance of QueryParam.
	 * 
	 * @param the
	 *            instance of QueryParam
	 */
	public void setQueryParam(QueryParam queryParam) {
		this.queryParam = queryParam;
	}

}
