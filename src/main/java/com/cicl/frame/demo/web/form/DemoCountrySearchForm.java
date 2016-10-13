/** 
 * @(#)DemoCountrySearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.form;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import com.cicl.frame.demo.entity.DemoCountry;

public class DemoCountrySearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = 3841979795087041764L;
	
	private DemoCountry demoCountry;

	public DemoCountrySearchForm() {
		super();
	}

	public DemoCountry getDemoCountry() {
		return demoCountry;
	}

	public void setDemoCountry(DemoCountry demoCountry) {
		this.demoCountry = demoCountry;
	}
}
