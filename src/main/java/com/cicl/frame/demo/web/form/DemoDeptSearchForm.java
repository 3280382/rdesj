/** 
 * @(#)DemoDeptSearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.form;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import com.cicl.frame.demo.entity.DemoDept;

public class DemoDeptSearchForm extends AbstractSearchForm {

	private static final long serialVersionUID = 3596488495921667147L;
	private DemoDept demoDept;

	public DemoDeptSearchForm() {
		super();
	}

	public DemoDept getDemoDept() {
		return demoDept;
	}

	public void setDemoDept(DemoDept demoDept) {
		this.demoDept = demoDept;
	}

}
