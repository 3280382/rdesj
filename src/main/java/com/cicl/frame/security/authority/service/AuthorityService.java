/** 
 * @(#)AuthorityService.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.authority.service;

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
import com.cicl.frame.security.authority.entity.Authority;
import com.cicl.frame.security.authority.repository.AuthorityRepository;
import com.cicl.frame.security.authority.web.form.AuthoritySearchForm;

/**
 * Class AuthorityService 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32  
 */
@Transactional
@Service
public class AuthorityService extends AbstractCrudService<Authority, Long> {
	@Autowired
	private AuthorityRepository repository;
	
	public AuthorityService() {
		super();
	}
	public AuthorityService(AuthorityRepository repository) {
		super();
		this.repository = repository;
	}
	
	//@Override
	public IBaseCrudRepository<Authority, Long> getIBaseCrudRepository(){
		return repository;
	}

	public Page<Authority> searchForm(AuthoritySearchForm authoritySearchForm) {
		Authority authority = null;
		
		QueryObject<Authority> queryObject = QueryObjectFactory.createQueryObject(Authority.class);
		QueryParam queryParam = new QueryParam();
		
		if (authoritySearchForm != null) {
			authority = authoritySearchForm.getAuthority();
		}
		
		if (authority != null) {			
			if(authority.getParentId()!=null){
				queryParam.and(new QueryParam("parentId", QueryParam.OPERATOR_EQ, authority.getParentId()));
			}
			if(authority.getName()!=null){
				queryParam.and(new QueryParam("name", QueryParam.OPERATOR_LIKE, "%" + authority.getName() + "%"));
				
			}
			if(authority.getCode()!=null){
				queryParam.and(new QueryParam("code", QueryParam.OPERATOR_LIKE, "%" + authority.getCode() + "%"));
				
			}
			if(authority.getSortOrder()!=null){
				queryParam.and(new QueryParam("sortOrder", QueryParam.OPERATOR_LIKE, "%" + authority.getSortOrder() + "%"));
				
			}
			if(authority.getStatus()!=null){
				queryParam.and(new QueryParam("status", QueryParam.OPERATOR_EQ, authority.getStatus()));
			}
			if(authority.getDatatype()!=null){
				queryParam.and(new QueryParam("datatype", QueryParam.OPERATOR_LIKE, "%" + authority.getDatatype() + "%"));
				
			}
			if(authority.getDescription()!=null){
				queryParam.and(new QueryParam("description", QueryParam.OPERATOR_LIKE, "%" + authority.getDescription() + "%"));
				
			}
		}
		
		queryObject.setQueryParam(queryParam);
		Page<Authority> page = this.search(queryObject, true, authoritySearchForm);

		return page;
	}
	public List<Authority> searchByParentId(Long id) {
		QueryObject<Authority> queryObject = QueryObjectFactory.createQueryObject(Authority.class);
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
