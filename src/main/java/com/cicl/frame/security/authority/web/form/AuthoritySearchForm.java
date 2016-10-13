/** 
 * @(#)AuthoritySearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.authority.web.form;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import com.cicl.frame.security.authority.entity.Authority;

public class AuthoritySearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = 1L;

	private Authority authority;

	public AuthoritySearchForm() {
		super();
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
}
