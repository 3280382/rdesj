/** 
 * @(#)IBaseCRUDService.java 1.0.0 2011-5-8 下午01:29:29  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.repository;

import java.io.Serializable;
import java.util.List;

import com.cicl.frame.core.entity.Persistable;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.repository.query.QueryObject;
import com.cicl.frame.core.web.form.PageForm;

/**
 * Class IBaseCRUDService
 *  
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-8 下午01:29:29  
 */
public interface IBaseCrudRepository <T extends Persistable, PK extends Serializable> {
	
	//API for CRUD
	T load(PK id);
	
	List<T> load(PK[] ids);

	PK save(T instance);
	
	void remove(PK id);

	void update(T instance);
	
	void merge(T detachedInstance);

	Page<T> search(QueryObject<T> queryObject, boolean cacheable, PageForm pageForm);
	
	Page<T> search(QueryObject<T> queryObject, PageForm pageForm);
	
	List<T> search(QueryObject<T> queryObject, boolean cacheable);
	
	List<T> search(QueryObject<T> queryObject);
	
	//API for validation
	boolean isPropertyExist(PK id, String propertyName, Object newValue);
}
