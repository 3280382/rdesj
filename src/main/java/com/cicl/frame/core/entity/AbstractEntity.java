/** 
 * @(#)AbstractEntity.java 1.0.0 2010-12-30 03:15:29  
 *  
 * Copyright 2010 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Class AbstractEntity 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2010-12-30 03:15:29
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer","entityType","modifiedDate","new" })
public abstract class AbstractEntity<PK extends Serializable> implements Persistable<PK> {
	private static final long serialVersionUID = 2660540904564363953L;
	private PK id;
	// @JsonSerialize(using=DateSerializer.class)
	private Date createdDate;

	private Date modifiedDate;

	protected AbstractEntity() {
		createdDate = new Date();
	}

	//@Override
	public Date getCreatedDate() {
		return this.createdDate;
	}

	//@Override
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	//@Override
	public PK getId() {
		return this.id;
	}

	//@Override
	public boolean isNew() {
		return getId() == null;
	}

	//@Override
	public void setCreatedDate(final Date date) {
		this.createdDate = date;
	}

	//@Override
	public void setModifiedDate(final Date date) {
		this.modifiedDate = date;
	}

	//@Override
	public void setId(final PK id) {
		this.id = id;
	}

	//@Override
	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);

	}

	//@Override
	@Override
	public boolean equals(Object other) {

		if (!(other instanceof AbstractEntity)) {
			return false;
		}

		final AbstractEntity castOther = (AbstractEntity) other;

		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	//@Override
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	//@Override
	public String getClassName(boolean includePackage) {
		String sClassName = getClass().getName();
		if (!includePackage) {
			int nPos = sClassName.lastIndexOf('.');
			if (nPos >= 0)
				sClassName = sClassName.substring(nPos + 1);
		}
		return sClassName;
	}

	public abstract String getEntityType();
}
