/** 
 * @(#)DemoProvinceSearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.form;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import com.cicl.frame.demo.entity.DemoProvince;

public class DemoProvinceSearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = -431946868501639928L;
	private DemoProvince demoProvince;

	public DemoProvinceSearchForm() {
		super();
	}

	public DemoProvince getDemoProvince() {
		return demoProvince;
	}

	public void setDemoProvince(DemoProvince demoProvince) {
		this.demoProvince = demoProvince;
	}
}
