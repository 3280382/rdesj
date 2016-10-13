/** 
 * @(#)DemoProvinceService.java 1.0.0 2011-01-12 10:40:38
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
import com.cicl.frame.core.service.AbstractCrudService;
import com.cicl.frame.demo.entity.DemoProvince;
import com.cicl.frame.demo.repository.DemoProvinceRepository;
import com.cicl.frame.demo.web.form.DemoProvinceSearchForm;

/**
 * Class DemoProvinceService 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32  
 */
@Transactional
@Service
public class DemoProvinceService extends AbstractCrudService<DemoProvince, Long> {
	@Autowired
	private DemoProvinceRepository repository;
	
	public DemoProvinceService() {
		super();
	}
	public DemoProvinceService(DemoProvinceRepository repository) {
		super();
		this.repository = repository;
	}
	
	//@Override
	public IBaseCrudRepository<DemoProvince, Long> getIBaseCrudRepository(){
		return repository;
	}
	/* (non-Javadoc)
	 * @see com.cicl.frame.demo.service.DemoProvinceService#search(java.lang.String)
	 */
	
	public List<DemoProvince> searchByCountryId(Long countryId) {
		QueryObject<DemoProvince> queryObject = QueryObjectFactory.createQueryObject(DemoProvince.class);
		if(countryId!=null)
			queryObject.setQueryParam(new QueryParam("countryId", QueryParam.OPERATOR_EQ, countryId) );
		
		List<DemoProvince> result = super.search(queryObject, false);
		return result;
	}
	
	public Page<DemoProvince> searchForm(DemoProvinceSearchForm demoDataSearchForm) {
		DemoProvince entity = null;
		
		QueryObject<DemoProvince> queryObject = QueryObjectFactory.createQueryObject(DemoProvince.class);
		QueryParam queryParam = new QueryParam();
		
		if (demoDataSearchForm != null) {
			entity = demoDataSearchForm.getDemoProvince();
		}
		
		if (entity != null) {
			if(entity.getName()!=null){
				queryParam.and(new QueryParam("name", QueryParam.OPERATOR_LIKE, "%" + entity.getName() + "%"));
			}	
			if(entity.getCountryId()!=null){
				queryParam.and(new QueryParam("countryId", QueryParam.OPERATOR_EQ,  entity.getCountryId()));
			}	
		}
		
		queryObject.setQueryParam(queryParam);
		Page<DemoProvince> page = this.search(queryObject, true, demoDataSearchForm);

		return page;
	}
}
