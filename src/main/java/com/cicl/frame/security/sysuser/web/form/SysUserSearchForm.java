/** 
 * @(#)SysUserSearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.sysuser.web.form;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import com.cicl.frame.security.sysuser.entity.SysUser;

public class SysUserSearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = 1L;

	private SysUser sysUser;

	public SysUserSearchForm() {
		super();
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
}
