/** 
 * @(#)DemoCountry.java 1.0.0 2011-5-11 上午09:50:24  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.entity;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.cicl.frame.core.entity.AbstractEntity;

/**
 * Class DemoCountry
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-11 上午09:50:24
 */
public class DemoCountry extends AbstractEntity<Long> {
	private static final long serialVersionUID = 8886908222850940227L;
	@NotEmpty
	@Length(max = 200)
	private String name;
	private Set<DemoProvince> provinces;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	@JsonIgnore
	public Set<DemoProvince> getProvinces() {
		return provinces;
	}
	public void setProvinces(Set<DemoProvince> provinces) {
		this.provinces = provinces;
	}

	/* (non-Javadoc)
	 * @see com.cicl.frame.core.entity.AbstractEntity#getEntityType()
	 */
	@Override
	public String getEntityType() {
		return getClassName(true);
	}
}
