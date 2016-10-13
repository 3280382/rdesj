/** 
 * @(#)OrganizationService.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.organization.service;

import java.util.ArrayList;
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
import com.cicl.frame.security.organization.entity.Organization;
import com.cicl.frame.security.organization.repository.OrganizationRepository;
import com.cicl.frame.security.organization.web.form.OrganizationSearchForm;

/**
 * Class OrganizationService 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32  
 */
@Transactional
@Service
public class OrganizationService extends AbstractCrudService<Organization, Long> {
	@Autowired
	private OrganizationRepository repository;
	
	public OrganizationService() {
		super();
	}
	public OrganizationService(OrganizationRepository repository) {
		super();
		this.repository = repository;
	}
	
	//@Override
	public IBaseCrudRepository<Organization, Long> getIBaseCrudRepository(){
		return repository;
	}
	
	/*** 
	 * @param id
	 * @return only return first level of child organizations
	 */
	public List<Organization> searchByParentId(Long id) {
		QueryObject<Organization> queryObject = QueryObjectFactory.createQueryObject(Organization.class);
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
	
	/**
	 *
	 * @param code
	 * @return only return first level of child organizations
	 */
	public List<Organization> searchByCode(String code){
		QueryObject<Organization> queryObject = QueryObjectFactory.createQueryObject(Organization.class);
		QueryParam queryParam = new QueryParam();
		queryParam.and(new QueryParam("code", QueryParam.OPERATOR_EQ, code));
		queryObject.setQueryParam(queryParam);
		
		//set default sort by sortOrder
		QueryProjections queryProjections = new QueryProjections();
		queryObject.setQueryProjections(queryProjections);
		
		queryProjections.setOrderProperty("sortOrder");
		queryProjections.setDescFlag(false);
		
		return this.search(queryObject);
	}
	
	/**
	 *
	 * @param id
	 * @return return all child organizations
	 */
	public List<Organization> searchTreeByParentId(Long id){
		List<Organization> list = new ArrayList<Organization>();
		Organization organizaton = this.load(id);
		
		addAllChildren(list, organizaton);	
		
		return list;
	}
	
	private void addAllChildren(List<Organization> list,Organization organizaton){
		list.add(organizaton);	
		for(Organization child : organizaton.getChildren()){
			addAllChildren(list, child);
		}	
	}	

	public Page<Organization> searchForm(OrganizationSearchForm organizationSearchForm) {
		Organization organization = null;
		
		QueryObject<Organization> queryObject = QueryObjectFactory.createQueryObject(Organization.class);
		QueryParam queryParam = new QueryParam();
		
		if (organizationSearchForm != null) {
			organization = organizationSearchForm.getOrganization();
		}
		
		if (organization != null) {			
			if(organization.getName()!=null){
				queryParam.and(new QueryParam("name", QueryParam.OPERATOR_LIKE, "%" + organization.getName() + "%"));
			}
			if(organization.getCode()!=null){
				queryParam.and(new QueryParam("code", QueryParam.OPERATOR_LIKE, "%" + organization.getCode() + "%"));
			}
			if(organization.getSortOrder()!=null){
				queryParam.and(new QueryParam("sortOrder", QueryParam.OPERATOR_LIKE, "%" + organization.getSortOrder() + "%"));
			}
			if(organization.getDescription()!=null){
				queryParam.and(new QueryParam("description", QueryParam.OPERATOR_LIKE, "%" + organization.getDescription() + "%"));
			}
		}
		
		queryObject.setQueryParam(queryParam);
		Page<Organization> page = this.search(queryObject, true, organizationSearchForm);

		return page;
	}
}
