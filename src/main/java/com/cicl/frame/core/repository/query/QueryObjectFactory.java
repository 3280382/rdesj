/** 
 * @(#)QueryObjectFactory.java 1.0.0 2011-2-15 15:05:51  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.repository.query;

import com.cicl.frame.core.entity.Persistable;

/**
 * Class QueryObjectFactory 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-2-15 15:05:51
 */
public class QueryObjectFactory {

	/**
	 * TODO Purpose/description of method
	 * 
	 * @param <T>
	 * @param entityClass
	 * @return
	 */
	public static <T extends Persistable> QueryObject<T> createQueryObject(
			Class<T> entityClass) {
		QueryObject<T> queryObject = new QueryObjectBase<T>();
		queryObject.setEntityClass(entityClass);
		return queryObject;

	}

	/**
	 * TODO Purpose/description of method
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param queryParam
	 * @return
	 */
	public static <T extends Persistable> QueryObject<T> createQueryObject(
			Class<T> entityClass, QueryParam queryParam) {
		QueryObject<T> queryObject = new QueryObjectBase<T>();
		queryObject.setEntityClass(entityClass);
		queryObject.setQueryParam(queryParam);
		return queryObject;

	}

	/**
	 * TODO Purpose/description of method
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param queryParam
	 * @param queryProjection
	 * @return
	 */
	public static <T extends Persistable> QueryObject<T> createQueryObject(
			Class<T> entityClass, QueryParam queryParam,
			QueryProjections queryProjection) {
		QueryObject<T> queryObject = new QueryObjectBase<T>();
		queryObject.setEntityClass(entityClass);
		queryObject.setQueryParam(queryParam);
		queryObject.setQueryProjections(queryProjection);
		return queryObject;

	}

}
