/** 
 * @(#)DemoDeptService.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cicl.frame.core.repository.IBaseCrudRepository;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.repository.query.QueryObject;
import com.cicl.frame.core.repository.query.QueryObjectFactory;
import com.cicl.frame.core.repository.query.QueryParam;
import com.cicl.frame.core.repository.query.QueryProjections;
import com.cicl.frame.core.service.AbstractCrudService;
import com.cicl.frame.demo.entity.DemoDept;
import com.cicl.frame.demo.repository.DemoDeptRepository;
import com.cicl.frame.demo.web.form.DemoDeptSearchForm;

/**
 * Class DemoDeptService 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32  
 */

@Service
@Transactional
public class DemoDeptService extends AbstractCrudService<DemoDept, Long> {
	@Autowired
	private DemoDeptRepository repository;
	
	public DemoDeptService() {
		super();
	}
	public DemoDeptService(DemoDeptRepository repository) {
		super();
		this.repository = repository;
	}
	
	//@Override
	public IBaseCrudRepository<DemoDept, Long> getIBaseCrudRepository(){
		return repository;
	}

	public Page<DemoDept> searchForm(DemoDeptSearchForm demoDeptSearchForm) {
		DemoDept demoDept = null;
		
		QueryObject<DemoDept> queryObject = QueryObjectFactory.createQueryObject(DemoDept.class);
		QueryParam queryParam = new QueryParam();
		
		if (demoDeptSearchForm != null) {
			demoDept = demoDeptSearchForm.getDemoDept();
		}
		
		if (demoDept != null) {
			if(demoDept.getName()!=null){
				queryParam.and(new QueryParam("name", QueryParam.OPERATOR_LIKE, "%" + demoDept.getName() + "%"));
			}
			if(demoDept.getParentId()!=null){
				queryParam.and(new QueryParam("parentId", QueryParam.OPERATOR_EQ, demoDept.getParentId()));
			}
		}
		
		queryObject.setQueryParam(queryParam);
		Page<DemoDept> page = this.search(queryObject, true, demoDeptSearchForm);

		return page;
	}
	
	public List<DemoDept> searchByParentId(Long id) {
		QueryObject<DemoDept> queryObject = QueryObjectFactory.createQueryObject(DemoDept.class);
		QueryParam queryParam = new QueryParam();
		
		//set default sort by sortOrder
		QueryProjections queryProjections = new QueryProjections();
		queryObject.setQueryProjections(queryProjections);
		
		queryProjections.setOrderProperty("sortOrder");
		queryProjections.setDescFlag(false);
		
		if(id!=null){			
			queryParam.and(new QueryParam("parentId", QueryParam.OPERATOR_EQ, id));
		}		
		
		queryObject.setQueryParam(queryParam);
		return this.search(queryObject);
	}
}
