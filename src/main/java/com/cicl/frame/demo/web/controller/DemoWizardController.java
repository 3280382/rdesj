/** 
 * @(#)DemoWizardController.java 1.0.0 2011-5-1 下午07:07:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cicl.frame.core.web.controller.AbstractAnnotationController;
import com.cicl.frame.demo.entity.DemoData;
import com.cicl.frame.demo.service.DemoDataService;
import com.cicl.frame.demo.web.form.DemoDataForm;
import com.cicl.frame.demo.web.validator.DemoDataFormValidator;

/**
 * Class DemoWizardController 
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-1 下午07:07:42
 */
@Controller
@SessionAttributes(DemoWizardController.CUR_MSG)
@RequestMapping("/demo/demowizard/*")
public class DemoWizardController  extends AbstractAnnotationController{
	public static final String CUR_MSG = "curMsg";
	public static final String METHOD_FIRST = "first";
	public static final String METHOD_SECOND = "second";
	public static final String METHOD_THIRD = "third";
	@Autowired
	private DemoDataService service;
	@Autowired
	private DemoDataFormValidator entityFormValidator;

	public static String getMethodMapping(String method) {
		return getMethodMapping("/demo",DemoWizardController.class, method);
	}
	
	@RequestMapping
	public String first(Model model) {
		// Invoke Business Service
		DemoData demoData = new DemoData();

		// Set Model(referenceData,formBackingObject)
		DemoDataForm demoDataForm = new DemoDataForm();
		demoDataForm.setDemoData(demoData);
		model.addAttribute(demoDataForm);
		model.addAttribute(CUR_MSG, "current message");

		// Return to view
		return null;
	}

	@RequestMapping
	public String second(@ModelAttribute(CUR_MSG) String curMessage, DemoDataForm entityForm, BindingResult bindingResult,
			Model model) {
		// Validation
		entityFormValidator.validate(entityForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return getMethodMapping(METHOD_FIRST);
		}

		// Invoke Business Service
		Long id = service.save(entityForm.getDemoData());

		// Set Model(referenceData,formBackingObject)
		entityForm.getDemoData().setId(id);
		model.addAttribute(entityForm);

		// Return to view
		return null;
	}

	@RequestMapping
	public String third(@ModelAttribute(CUR_MSG) String curMessage, DemoDataForm entityForm, BindingResult bindingResult,
			Model model) {
		// Validation
		entityFormValidator.validate(entityForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return getMethodMapping(METHOD_SECOND);
		}

		// Invoke Business Service
		service.update(entityForm.getDemoData());

		// Set Model(referenceData,formBackingObject)
		model.addAttribute(entityForm);

		// Return to view
		return null;
	}
	
	@RequestMapping
	public String done(@ModelAttribute(CUR_MSG) String curMessage, DemoDataForm entityForm, BindingResult bindingResult, Model model) {
		// Validation
		entityFormValidator.validate(entityForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return getMethodMapping(METHOD_THIRD);
		}

		// Invoke Business Service
		service.update(entityForm.getDemoData());
		
		return null;
	}
}
