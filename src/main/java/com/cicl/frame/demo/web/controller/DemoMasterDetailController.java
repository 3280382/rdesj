/** 
 * @(#)DemoMasterDetailController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cicl.frame.core.web.controller.AbstractJsonController;

/**
 * Class DemoListPairController
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-11 上午10:35:08  
 */
@Controller
@RequestMapping("/demo/demomasterdetail/*")
public class DemoMasterDetailController extends AbstractJsonController{

	public DemoMasterDetailController() {
		super();
	}
	
	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
	}
	
	@RequestMapping
	public String list(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}
	
	@RequestMapping
	public String index() {
		// Return to view
		return null;
	}
	

}
