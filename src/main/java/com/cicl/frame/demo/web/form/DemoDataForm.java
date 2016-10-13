/** 
 * @(#)DemoDataForm.java 1.0.0 2011-3-3 09:39:17  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.form;

import javax.validation.Valid;

import com.cicl.frame.core.web.form.AbstractForm;
import com.cicl.frame.demo.entity.DemoData;


/**
 * Class DemoDataForm
 *  
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-8 下午03:01:25  
 */
public class DemoDataForm extends AbstractForm {
	private static final long serialVersionUID = 5533747381412446275L;
	@Valid 
	private DemoData demoData;

	public DemoDataForm() {
	}


	public DemoData getDemoData() {
		return demoData;
	}

	public void setDemoData(DemoData demoData) {
		this.demoData = demoData;
	}
}
