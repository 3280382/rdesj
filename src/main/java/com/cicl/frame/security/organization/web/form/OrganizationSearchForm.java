/** 
 * @(#)OrganizationSearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.organization.web.form;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import com.cicl.frame.security.organization.entity.Organization;

public class OrganizationSearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = 1L;

	private Organization organization;

	public OrganizationSearchForm() {
		super();
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}
