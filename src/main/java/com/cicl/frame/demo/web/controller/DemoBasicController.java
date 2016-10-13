/** 
 * @(#)DemoBasicController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.util.CSVFileUtils;
import com.cicl.frame.core.web.controller.AbstractAnnotationController;
import com.cicl.frame.core.web.util.ResponseUtils;
import com.cicl.frame.demo.dictionary.SexList;
import com.cicl.frame.demo.entity.DemoData;
import com.cicl.frame.demo.service.DemoCountryService;
import com.cicl.frame.demo.service.DemoDataService;
import com.cicl.frame.demo.service.DemoProvinceService;
import com.cicl.frame.demo.web.form.DemoDataForm;
import com.cicl.frame.demo.web.form.DemoDataSearchForm;
import com.cicl.frame.demo.web.validator.DemoDataFormValidator;
import com.cicl.frame.demo.web.validator.DemoDataSearchFormValidator;

@Controller
@RequestMapping("/demo/demobasic/*")
public class DemoBasicController extends AbstractAnnotationController{
	@Autowired
	private DemoDataService service;
	@Autowired
	private DemoDataSearchFormValidator searchFormValidator;
	@Autowired
	private DemoDataFormValidator entityFormValidator;
	
	@Autowired
	private DemoCountryService demoCountryService;
	@Autowired
	private DemoProvinceService demoProvinceService;
	@Autowired
	private SexList sexList;

	public DemoDataService getDemoDataService() {
		return service;
	}

	public DemoBasicController() {
		super();
	}

	public DemoBasicController(DemoDataService service) {
		super();
		this.service = service;
	}
	
	public static String getMethodMapping(String method) {
		return getMethodMapping("/demo",DemoBasicController.class, method);
	}
	
	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
		model.addAttribute("sexList", sexList.getMap());
		model.addAttribute("demoCountryList", demoCountryService.search(null));
		model.addAttribute("demoProvinceList", demoProvinceService.searchByCountryId(null));
	}
	
	@RequestMapping
	public String list(DemoDataSearchForm searchForm, BindingResult BindingResult, Model model) {
		// Validation
		searchFormValidator.validate(searchForm, BindingResult);
		if (BindingResult.hasErrors()) {
			return null;
		}
		
		// prepare model, referenceData
		preModel(model);

		// Invoke Business Service
		Page<DemoData> page = service.searchForm(searchForm);

		// Set Model(referenceData,formBackingObject)
		model.addAttribute(searchForm);
		model.addAttribute("demoDataSearchResults", page);

		// Return to view
		return null;
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, DemoDataSearchForm searchForm, BindingResult bindingResult,
			Model model) throws Exception {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// TODO:How to render to original view with error model?
			return;
		}

		// Invoke Business Service
		Page<DemoData> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "test.csv");
		String[] cols = {"id","name"};
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());
		// Response output direct
	}

	@RequestMapping
	public String show(Long id, Model model) {
		// Invoke Business Service
		DemoData demoData = service.load(id);
		
		// prepare model, referenceData
		preModel(model);

		// Set Model
		model.addAttribute(demoData);

		// Return to view
		return null;
	}

	@RequestMapping
	public String add(Model model) {
		// Invoke Business Service
		DemoData demoData = new DemoData();
		
		// prepare model, referenceData
		preModel(model);

		// Set Model(referenceData,formBackingObject)
		DemoDataForm demoDataForm = new DemoDataForm();
		demoDataForm.setDemoData(demoData);
		model.addAttribute(demoDataForm);

		// Return to view
		return null;
	}

	@RequestMapping
	public String save(@Valid DemoDataForm entityForm, BindingResult bindingResult, Model model) {
		// Validation by demoDataFormValidator
		entityFormValidator.validate(entityForm, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			return getMethodMapping(METHOD_ADD);
		}
		
		// Invoke Business Service
		service.save(entityForm.getDemoData());

		// Set Model(referenceData,formBackingObject)
		model.addAttribute(entityForm);

		// Return to view
		return getMethodMapping(METHOD_RELOAD);
	}

	@RequestMapping
	public String edit(Long id, Model model) {
		// Invoke Business Service
		DemoData demoData;
		demoData = service.load(id);
		
		// prepare model, referenceData
		preModel(model);

		// Set Model(referenceData,formBackingObject)
		DemoDataForm demoDataForm = new DemoDataForm();
		demoDataForm.setDemoData(demoData);
		model.addAttribute(demoDataForm);

		// Return to view
		return null;
	}
	
	@RequestMapping
	public String update(@Valid DemoDataForm entityForm, BindingResult bindingResult, Model model) {
		// Validation by demoDataFormValidator
		entityFormValidator.validate(entityForm, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			return getMethodMapping(METHOD_EDIT);
		}

		// Invoke Business Service
		service.update(entityForm.getDemoData());

		// Set Model(referenceData,formBackingObject)
		model.addAttribute(entityForm);

		// Return to view
		return getMethodMapping(METHOD_RELOAD);
	}

	@RequestMapping
	public String remove(Long id, Model model) {
		// Invoke Business Service
		service.remove(id);

		// Return to view
		return getMethodMapping(METHOD_RELOAD);
	}
}
