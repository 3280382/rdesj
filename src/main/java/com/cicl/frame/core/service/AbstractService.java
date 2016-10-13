/** 
 * @(#)AbstractCrudService.java 1.0.0 2011-5-8 04:25:47  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cicl.frame.core.entity.Persistable;
import com.cicl.frame.core.repository.IBaseCrudRepository;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.repository.query.QueryObject;
import com.cicl.frame.core.web.form.PageForm;

/**
 * Class AbstractCrudService
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-8 04:25:47
 */
@Transactional
public abstract class AbstractService<T extends Persistable, PK extends Serializable> implements
		IBaseCrudService<T, PK> {
	abstract public IBaseCrudRepository<T, PK> getIBaseCrudRepository();
	
	public T load(PK id) {
		return getIBaseCrudRepository().load(id);
	}

	public List<T> load(PK[] ids) {
		return getIBaseCrudRepository().load(ids);
	}

	public List<Persistable> loadPersistable(PK[] ids) {
		List<Persistable> listPersistable = new ArrayList<Persistable>();
		List<T> list = getIBaseCrudRepository().load(ids);
		for (T entity : list) {
			listPersistable.add(entity);
		}
		return listPersistable;
	}	

	public PK save(T instance) {	
		return getIBaseCrudRepository().save(instance);
	}

	public void update(T instance) {
		getIBaseCrudRepository().update(instance);
	}

	public void merge(T instance) {
		getIBaseCrudRepository().merge(instance);
	}

	public void remove(PK id) {
		getIBaseCrudRepository().remove(id);
	}

	public void remove(PK[] ids) {
		for (PK id : ids) {
			getIBaseCrudRepository().remove(id);
		}
	}

	public Page<T> search(QueryObject<T> queryObject, boolean cacheable, PageForm pageForm) {
		return getIBaseCrudRepository().search(queryObject, cacheable, pageForm);
	}

	public Page<T> search(QueryObject<T> queryObject, PageForm pageForm) {
		return getIBaseCrudRepository().search(queryObject, pageForm);
	}

	public List<T> search(QueryObject<T> queryObject, boolean cacheable) {
		return getIBaseCrudRepository().search(queryObject, cacheable);
	}

	public List<T> search(QueryObject<T> queryObject) {
		return getIBaseCrudRepository().search(queryObject);
	}

	public boolean isPropertyExist(PK id, String propertyName, Object newValue) {
		if (newValue == null)
			return false;
		return getIBaseCrudRepository().isPropertyExist(id, propertyName, newValue);
	}
}
