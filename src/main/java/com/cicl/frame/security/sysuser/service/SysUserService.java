/** 
 * @(#)SysUserService.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.sysuser.service;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cicl.frame.core.repository.IBaseCrudRepository;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.repository.query.QueryObject;
import com.cicl.frame.core.repository.query.QueryObjectFactory;
import com.cicl.frame.core.repository.query.QueryParam;
import com.cicl.frame.core.service.AbstractCrudService;
import com.cicl.frame.security.organization.entity.Organization;
import com.cicl.frame.security.organization.service.OrganizationService;
import com.cicl.frame.security.sysuser.entity.SysUser;
import com.cicl.frame.security.sysuser.repository.SysUserRepository;
import com.cicl.frame.security.sysuser.web.form.SysUserSearchForm;

/**
 * Class SysUserService 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32  
 */
@Transactional
@Service
public class SysUserService extends AbstractCrudService<SysUser, Long> {
	@Autowired
	private SysUserRepository repository;
	@Autowired
	private OrganizationService organizationService;
	
	public SysUserService() {
		super();
	}
	public SysUserService(SysUserRepository repository) {
		super();
		this.repository = repository;
	}
	
	//@Override
	@Override
	public IBaseCrudRepository<SysUser, Long> getIBaseCrudRepository(){
		return repository;
	}

	public Page<SysUser> searchForm(SysUserSearchForm sysUserSearchForm) {
		SysUser sysUser = null;
		
		QueryObject<SysUser> queryObject = QueryObjectFactory.createQueryObject(SysUser.class);
		QueryParam queryParam = new QueryParam();
		
		if (sysUserSearchForm != null) {
			sysUser = sysUserSearchForm.getSysUser();
		}
		
		if (sysUser != null) {			
			if(sysUser.getUsername()!=null){
				queryParam.and(new QueryParam("username", QueryParam.OPERATOR_LIKE, "%" + sysUser.getUsername() + "%"));
			}
			if(sysUser.getLoginName()!=null){
				queryParam.and(new QueryParam("loginName", QueryParam.OPERATOR_LIKE, "%" + sysUser.getLoginName() + "%"));
			}
			if(sysUser.getEmail()!=null){
				queryParam.and(new QueryParam("email", QueryParam.OPERATOR_LIKE, "%" + sysUser.getEmail() + "%"));
			}
			if(sysUser.getStatus()!=null){
				queryParam.and(new QueryParam("status", QueryParam.OPERATOR_EQ,sysUser.getStatus()));
			}
			if(sysUser.getOrganizationId()!=null){
				queryParam.and(new QueryParam("organizationId", QueryParam.OPERATOR_EQ, sysUser.getOrganizationId()));
			}
			if(sysUser.getCode()!=null){
				queryParam.and(new QueryParam("code", QueryParam.OPERATOR_LIKE, "%" + sysUser.getCode() + "%"));
			}
			if(sysUser.getMobile()!=null){
				queryParam.and(new QueryParam("mobile", QueryParam.OPERATOR_LIKE, "%" + sysUser.getMobile() + "%"));
			}
			if(sysUser.getPhone()!=null){
				queryParam.and(new QueryParam("phone", QueryParam.OPERATOR_LIKE, "%" + sysUser.getPhone() + "%"));
			}
			if(sysUser.getAddress()!=null){
				queryParam.and(new QueryParam("address", QueryParam.OPERATOR_LIKE, "%" + sysUser.getAddress() + "%"));
			}
			if(sysUser.getDescription()!=null){
				queryParam.and(new QueryParam("description", QueryParam.OPERATOR_LIKE, "%" + sysUser.getDescription() + "%"));
			}
		}
		
		queryObject.setQueryParam(queryParam);
		Page<SysUser> page = this.search(queryObject, true, sysUserSearchForm);

		return page;
	}
	
	public SysUser loadByLoginName(String loginName){
		QueryObject<SysUser> queryObject = QueryObjectFactory.createQueryObject(SysUser.class);
		QueryParam queryParam = new QueryParam();
		queryParam.and(new QueryParam("loginName", QueryParam.OPERATOR_EQ, loginName));
		queryObject.setQueryParam(queryParam);
		
		List<SysUser> list = this.search(queryObject);
		if(list.size()<=0){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	/**
	 * search SysUser by role only;
	 * @param roleCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysUser> searchByRoleCode(String roleCode) {
		Criteria criteria = repository.createCriteria();
		criteria.createCriteria("roles").add(Restrictions.eq("code", roleCode));
		return criteria.list();
	}
	
	/**
	 * search SysUser by organizationId only;
	 * @param organizationId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysUser> searchByOrganizationId(Long organizationId) {
		Criteria criteria = repository.createCriteria();
		criteria.add(Restrictions.eq("organizationId", organizationId));
		return criteria.list();
	}
	
	/**
	 * search SysUser in a organization with special role(roleCode).
	 * @param organizationId
	 * @param roleCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysUser> searchByOrganizationIdAndRoleCodeSelf(Long organizationId, String roleCode) {
		Criteria criteria = repository.createCriteria();

		criteria.add(Restrictions.eq("organizationId", organizationId)).createCriteria("roles")
				.add(Restrictions.eq("code", roleCode));

		return criteria.list();
	}
	
	/**
	 * @param organizationId
	 * @param roleCode
	 * @param filterType
	 *            1 selfOnly, 2 selfAndParent,3 parentOnly,4 selfAndChildren,5
	 *            childrenOnly
	 * @return
	 */
	public List<SysUser> searchByOrganizationIdAndRoleCode(Long organizationId, String roleCode, int searchType) {
		List<SysUser> list;
		switch (searchType) {
		case 1:
			return searchByOrganizationIdAndRoleCodeSelf(organizationId, roleCode);
		case 2:
			list = searchByOrganizationIdAndRoleCodeSelf(organizationId, roleCode);
			if (list != null)
				return list;

			return searchByOrganizationIdAndRoleCodeParent(organizationId, roleCode);
		case 3:
			return searchByOrganizationIdAndRoleCodeParent(organizationId, roleCode);
		case 4:
			list = searchByOrganizationIdAndRoleCodeSelf(organizationId, roleCode);
			if (list != null)
				return list;

			return searchByOrganizationIdAndRoleCodeChildren(organizationId, roleCode);
		case 5:
			return searchByOrganizationIdAndRoleCodeChildren(organizationId, roleCode);
		default:
			return null;
		}
	}

	private List<SysUser> searchByOrganizationIdAndRoleCodeParent(Long organizationId, String roleCode) {
		Organization organization;
		List<SysUser> list;

		organization = organizationService.load(organizationId);
		if (organization == null)
			return null;
		organization = organization.getParent();
		if (organization == null)
			return null;

		while (organization.getId() != null) {
			list = searchByOrganizationIdAndRoleCodeSelf(organization.getId(), roleCode);
			if (list != null)
				return list;

			organization = organization.getParent();
			if (organization == null)
				return null;
		}
		return null;
	}

	private List<SysUser> searchByOrganizationIdAndRoleCodeChildren(Long organizationId, String roleCode) {
		Organization organization;
		Collection<Organization> children;
		List<SysUser> list;

		organization = organizationService.load(organizationId);
		children = organization.getChildren();
	
		for (Organization child : children) {
			list = searchByOrganizationIdAndRoleCodeSelf(child.getId(), roleCode);
			if (list != null)
				return list;
		}
		
		for (Organization child : children) {
			list = searchByOrganizationIdAndRoleCodeChildren(child.getId(), roleCode);
			if (list != null)
				return list;
		}
		return null;
	}
	
}
