/** 
 * @(#)RoleService.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cicl.frame.core.repository.IBaseCrudRepository;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.repository.query.QueryObject;
import com.cicl.frame.core.repository.query.QueryObjectFactory;
import com.cicl.frame.core.repository.query.QueryParam;
import com.cicl.frame.core.service.AbstractCrudService;
import com.cicl.frame.security.role.entity.Role;
import com.cicl.frame.security.role.repository.RoleRepository;
import com.cicl.frame.security.role.web.form.RoleSearchForm;

/**
 * Class RoleService 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32  
 */
@Transactional
@Service
public class RoleService extends AbstractCrudService<Role, Long> {
	@Autowired
	private RoleRepository repository;
	
	public RoleService() {
		super();
	}
	public RoleService(RoleRepository repository) {
		super();
		this.repository = repository;
	}
	
	//@Override
	public IBaseCrudRepository<Role, Long> getIBaseCrudRepository(){
		return repository;
	}

	public Page<Role> searchForm(RoleSearchForm roleSearchForm) {
		Role role = null;
		
		QueryObject<Role> queryObject = QueryObjectFactory.createQueryObject(Role.class);
		QueryParam queryParam = new QueryParam();
		
		if (roleSearchForm != null) {
			role = roleSearchForm.getRole();
		}
		
		if (role != null) {			
			if(role.getName()!=null){
				queryParam.and(new QueryParam("name", QueryParam.OPERATOR_LIKE, "%" + role.getName() + "%"));
			}
			if(role.getCode()!=null){
				queryParam.and(new QueryParam("code", QueryParam.OPERATOR_LIKE, "%" + role.getCode() + "%"));
			}
			if(role.getSortOrder()!=null){
				queryParam.and(new QueryParam("sortOrder", QueryParam.OPERATOR_LIKE, "%" + role.getSortOrder() + "%"));
			}
		}
		
		queryObject.setQueryParam(queryParam);
		Page<Role> page = this.search(queryObject, true, roleSearchForm);

		return page;
	}
}
