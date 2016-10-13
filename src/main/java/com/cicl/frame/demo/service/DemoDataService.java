/** 
 * @(#)DemoDataService.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cicl.frame.core.repository.IBaseCrudRepository;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.repository.query.QueryObject;
import com.cicl.frame.core.repository.query.QueryObjectFactory;
import com.cicl.frame.core.repository.query.QueryParam;
import com.cicl.frame.core.service.AbstractCrudService;
import com.cicl.frame.demo.entity.DemoData;
import com.cicl.frame.demo.repository.DemoDataRepository;
import com.cicl.frame.demo.web.form.DemoDataSearchForm;

/**
 * Class DemoDataService 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32  
 */
@Transactional
@Service
public class DemoDataService extends AbstractCrudService<DemoData, Long> {
	@Autowired
	private DemoDataRepository repository;
	
	public DemoDataService() {
		super();
	}
	public DemoDataService(DemoDataRepository repository) {
		super();
		this.repository = repository;
	}
	
	//@Override
	public IBaseCrudRepository<DemoData, Long> getIBaseCrudRepository(){
		return repository;
	}

	public Page<DemoData> searchForm(DemoDataSearchForm demoDataSearchForm) {
		DemoData demoData = null;
		
		QueryObject<DemoData> queryObject = QueryObjectFactory.createQueryObject(DemoData.class);
		QueryParam queryParam = new QueryParam();
		
		if (demoDataSearchForm != null) {
			demoData = demoDataSearchForm.getDemoData();
			
			if(demoDataSearchForm.getBirthdayStart()!=null){
				queryParam.and(new QueryParam("birthday", QueryParam.OPERATOR_GE, demoDataSearchForm.getBirthdayStart()));
			}
			if(demoDataSearchForm.getBirthdayEnd()!=null){
				queryParam.and(new QueryParam("birthday", QueryParam.OPERATOR_LE, demoDataSearchForm.getBirthdayEnd()));
			}
		}
		
		if (demoData != null) {
			if(demoData.getName()!=null){
				queryParam.and(new QueryParam("name", QueryParam.OPERATOR_LIKE, "%" + demoData.getName() + "%"));
			}
			if(demoData.getEmail()!=null){
				queryParam.and(new QueryParam("email", QueryParam.OPERATOR_LIKE, "%" + demoData.getEmail() + "%"));
			}
			if(demoData.getSex()!=null){
				queryParam.and(new QueryParam("sex", QueryParam.OPERATOR_EQ, demoData.getSex()));
			}
			if(demoData.getCountryId()!=null){
				queryParam.and(new QueryParam("countryId", QueryParam.OPERATOR_EQ, demoData.getCountryId()));
			}
			if(demoData.getProvinceId()!=null){
				queryParam.and(new QueryParam("provinceId", QueryParam.OPERATOR_EQ, demoData.getProvinceId()));
			}
		}
		
		queryObject.setQueryParam(queryParam);
		Page<DemoData> page = this.search(queryObject, true, demoDataSearchForm);

		return page;
	}
}
