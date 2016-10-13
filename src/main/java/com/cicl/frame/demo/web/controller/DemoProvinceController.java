/** 
 * @(#)DemoProvinceController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.util.CSVFileUtils;
import com.cicl.frame.core.web.controller.AbstractJsonController;
import com.cicl.frame.core.web.util.ResponseUtils;
import com.cicl.frame.demo.entity.DemoProvince;
import com.cicl.frame.demo.service.DemoCountryService;
import com.cicl.frame.demo.service.DemoProvinceService;
import com.cicl.frame.demo.web.form.DemoProvinceSearchForm;
import com.cicl.frame.demo.web.validator.DemoProvinceValidator;

/**
 * Class DemoCountryController
 * 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-19 下午07:13:33  
 */
@Controller
@RequestMapping("/demo/demoprovince/*")
public class DemoProvinceController extends AbstractJsonController {
	@Autowired
	private DemoProvinceService service;
	@Autowired
	private DemoProvinceValidator entityValidator;
	@Autowired
	private DemoCountryService demoCountryService;

	public DemoProvinceController() {
		super();
	}

	public DemoProvinceController(DemoProvinceService service) {
		super();
		this.service = service;
	}

	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
		model.addAttribute("demoCountryList", demoCountryService.search(null));
	}

	@RequestMapping
	public String list(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> search(@Valid DemoProvinceSearchForm searchForm, BindingResult bindingResult) {
		// Validation


		// Invoke Business Service
		Page<DemoProvince> page = service.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, DemoProvinceSearchForm searchForm,
			BindingResult bindingResult) throws Exception {
		// Validation

		// Invoke Business Service
		Page<DemoProvince> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "demodata.csv");
		String[] cols = { "id", "name" };
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());

		// No return, write response directly
	}

	@RequestMapping
	public String show(Long id, Model model) {
		// Invoke Business Service
		DemoProvince demoData = service.load(id);

		// prepare model, referenceData
		preModel(model);

		// Set Model, formBackingObject
		model.addAttribute(demoData);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> load(Long id) {
		// Invoke Business Service
		DemoProvince demoData;
		demoData = service.load(id);

		// Return json
		return jsonObject(demoData);
	}
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> loadProvince(Long countryId) {		
		// Invoke Business Service
		List<DemoProvince> result = service.searchByCountryId(countryId);
		
		// Return json
		return jsonObject(result);
	}

	@RequestMapping
	public String edit(Long id, Model model) {
		// Invoke Business Service
		DemoProvince demoData;
		demoData = service.load(id);

		// prepare model, referenceData
		preModel(model);
		// Set Model,formBackingObject)
		model.addAttribute(demoData);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> update(@Valid DemoProvince entity, BindingResult bindingResult) {
		// Validation by demoDataValidator
		entityValidator.validate(entity, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.merge(entity);

		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	public String add(Long countryId, Model model) {
		// prepare model, referenceData
		preModel(model);
		
		// spring MVC will not put Long type into implicitModel, must put in explicitly way
		model.addAttribute("countryId", countryId);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> save(@Valid DemoProvince entity, BindingResult bindingResult) {
		// Validation by demoDataValidator
		entityValidator.validate(entity, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.save(entity);
		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> remove(Long[] ids) {
		// Invoke Business Service
		service.remove(ids);

		// Return json, ok message
		return jsonOk();
	}
}
