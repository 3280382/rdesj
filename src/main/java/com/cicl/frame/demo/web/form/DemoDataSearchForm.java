/** 
 * @(#)DemoDataSearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.form;

import java.util.Date;

import javax.validation.constraints.Past;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import com.cicl.frame.demo.entity.DemoData;

public class DemoDataSearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = -8223162067544381656L;

	private DemoData demoData;
	@Past
	private Date  birthdayStart;
	@Past
	private Date  birthdayEnd;

	public DemoDataSearchForm() {
		super();
	}

	public DemoData getDemoData() {
		return demoData;
	}

	public void setDemoData(DemoData demoData) {
		this.demoData = demoData;
	}

	public Date getBirthdayStart() {
		return birthdayStart;
	}

	public void setBirthdayStart(Date birthdayStart) {
		this.birthdayStart = birthdayStart;
	}

	public Date getBirthdayEnd() {
		return birthdayEnd;
	}

	public void setBirthdayEnd(Date birthdayEnd) {
		this.birthdayEnd = birthdayEnd;
	}

}
