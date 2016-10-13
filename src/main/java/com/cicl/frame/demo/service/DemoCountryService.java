/** 
 * @(#)DemoCountryService.java 1.0.0 2011-01-12 10:40:38
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
import com.cicl.frame.demo.entity.DemoCountry;
import com.cicl.frame.demo.repository.DemoCountryRepository;
import com.cicl.frame.demo.web.form.DemoCountrySearchForm;

/**
 * Class DemoCountryService
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32
 */
@Transactional
@Service
public class DemoCountryService extends AbstractCrudService<DemoCountry, Long> {
	@Autowired
	private DemoCountryRepository repository;

	public DemoCountryService() {
		super();
	}

	public DemoCountryService(DemoCountryRepository repository) {
		super();
		this.repository = repository;
	}

	//@Override
	public IBaseCrudRepository<DemoCountry, Long> getIBaseCrudRepository() {
		return repository;
	}

	public Page<DemoCountry> searchForm(DemoCountrySearchForm demoCountrySearchForm) {
		DemoCountry entity = null;

		QueryObject<DemoCountry> queryObject = QueryObjectFactory.createQueryObject(DemoCountry.class);
		QueryParam queryParam = new QueryParam();

		if (demoCountrySearchForm != null) {
			entity = demoCountrySearchForm.getDemoCountry();
		}

		if (entity != null) {
			if (entity.getName() != null) {
				queryParam.and(new QueryParam("name", QueryParam.OPERATOR_LIKE, "%" + entity.getName() + "%"));
			}
		}

		queryObject.setQueryParam(queryParam);
		Page<DemoCountry> page = this.search(queryObject, true, demoCountrySearchForm);

		return page;
	}
}
