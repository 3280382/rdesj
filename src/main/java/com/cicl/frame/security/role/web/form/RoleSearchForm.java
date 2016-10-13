/** 
 * @(#)RoleSearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.role.web.form;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import com.cicl.frame.security.role.entity.Role;

public class RoleSearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = 1L;

	private Role role;

	public RoleSearchForm() {
		super();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
