/** 
 * @(#)DemoCountry.java 1.0.0 2011-5-11 上午09:50:24  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.cicl.frame.core.entity.AbstractEntity;

/**
 * Class DemoCountry
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-11 上午09:50:24
 */
public class DemoProvince extends AbstractEntity<Long> {
	private static final long serialVersionUID = -540521320079215825L;
	@NotNull
	private Long countryId;
	private DemoCountry country;
	@NotEmpty
	@Length(max = 200)
	private String name;

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public DemoCountry getCountry() {
		return country;
	}

	public void setCountry(DemoCountry country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see com.cicl.frame.core.entity.AbstractEntity#getEntityType()
	 */
	@Override
	public String getEntityType() {
		return getClassName(true);
	}
}
