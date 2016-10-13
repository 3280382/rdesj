/** 
 * @(#)DemoData.java 1.0.0 2011-4-22 下午04:46:32  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.entity;

import java.util.Set;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.cicl.frame.core.entity.AbstractEntity;

/**
 * Class DemoUser
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32
 */

public class DemoDept extends AbstractEntity<Long> {
	private static final long serialVersionUID = -3547303776731715493L;
	@NotNull
	private Long parentId;
	private DemoDept parent;
	private Set<DemoDept> children;
	@NotEmpty
	@Length(max = 200)
	private String name;
	@NotEmpty
	@Length(max = 200)
	private String code;
	@NotEmpty
	@Length(max = 200)
	private String sortOrder;
	@NotEmpty
	@Length(max = 200)
	private String deptType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public DemoDept getParent() {
		return parent;
	}

	public void setParent(DemoDept parent) {
		this.parent = parent;
	}
	@JsonIgnore
	public Set<DemoDept> getChildren() {
		return children;
	}

	public void setChildren(Set<DemoDept> children) {
		this.children = children;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	/* (non-Javadoc)
	 * @see com.cicl.frame.core.entity.AbstractEntity#getEntityType()
	 */
	@Override
	public String getEntityType() {
		return getClassName(true);
	}
}
