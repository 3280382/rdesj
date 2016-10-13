/** 
 * @(#)DemoData.java 1.0.0 2011-4-22 下午04:46:32  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.cicl.frame.core.entity.AbstractEntity;

/**
 * Class DemoData
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32
 */
public class DemoData extends AbstractEntity<Long> {
	private static final long serialVersionUID = -5717823555032977114L;

	@NotEmpty
	@Length(max = 200)
	private String name;
	@Past
	private Date birthday;
	@NotEmpty
	private String sex;
	@Range(min = 0, max = 999999)
	private BigDecimal salary;
	@Email
	@Length(max = 200)
	private String email;
	@NotNull
	private Long countryId;
	private DemoCountry country;
	@NotNull
	private Long provinceId;
	private DemoProvince province;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public DemoProvince getProvince() {
		return province;
	}

	public void setProvince(DemoProvince province) {
		this.province = province;
	}
	/* (non-Javadoc)
	 * @see com.cicl.frame.core.entity.AbstractEntity#getEntityType()
	 */
	@Override
	public String getEntityType() {
		return getClassName(true);
	}
}
