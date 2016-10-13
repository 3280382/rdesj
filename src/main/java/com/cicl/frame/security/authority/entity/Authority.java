/** 
 * @(#)Authority.java 1.0.0 2011-4-22 下午04:46:32  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.authority.entity;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;

import com.cicl.frame.core.entity.AbstractEntity;
import com.cicl.frame.security.authority.dictionary.AuthorityConstant;

/**
 * Class Authority
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32
 */
public class Authority extends AbstractEntity<Long> implements GrantedAuthority{
	private static final long serialVersionUID = 1L;	
	
	private Long parentId;
	@NotEmpty @Length(max = 200)
	private String name;
	@NotEmpty @Length(max = 200)
	private String code;
	@Length(max = 200)
	private String sortOrder;
	
	private Long status;
	@Length(max = 2000)
	private String datatype;
	@Length(max = 2000)
	private String description;
	
	public Long getParentId() {
		return this.parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Long getStatus() {
		return this.status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	
	public String getDatatype() {
		return this.datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	private Authority parent;
	
	private Set<Authority> children;
	
	@JsonIgnore
	public Authority getParent() {
		return parent;
	}
	public String getParentName() {
		if(getParent()==null)return "";
		return getParent().getName();
	}
	
	public void setParent(Authority parent) {
		this.parent = parent;
	}
	@JsonIgnore
	public Set<Authority> getChildren() {
		return children;
	}
	public void setChildren(Set<Authority> children) {
		this.children = children;
	}

	public String getAuthority() {
		return this.code;
	}
	/* (non-Javadoc)
	 * @see com.cicl.frame.core.entity.AbstractEntity#getEntityType()
	 */
	@Override
	public String getEntityType() {
		return AuthorityConstant.ENTITY_TYPE;
	}
}
