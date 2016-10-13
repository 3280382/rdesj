/** 
 * @(#)DemoDeptController.java 1.0.0 2011-2-18 18:19:42  
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
import com.cicl.frame.demo.entity.DemoDept;
import com.cicl.frame.demo.service.DemoDeptService;
import com.cicl.frame.demo.web.form.DemoDeptSearchForm;
import com.cicl.frame.demo.web.validator.DemoDeptValidator;

@Controller
@RequestMapping("/demo/demodept/*")
public class DemoDeptController extends AbstractJsonController {
	@Autowired
	private DemoDeptService service;
	@Autowired
	private DemoDeptValidator entityValidator;

	public DemoDeptController() {
		super();
	}

	public DemoDeptController(DemoDeptService service) {
		super();
		this.service = service;
	}

	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
		model.addAttribute("deptList", service.search(null));
	}

	@RequestMapping
	public String list(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}
	
	@RequestMapping
	public String listXml(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}
	
	@RequestMapping
	public String listDetail(Model model) {
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
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> search(DemoDeptSearchForm searchForm, BindingResult bindingResult) {
		// Validation

		// Invoke Business Service
		Page<DemoDept> page = service.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> loadTree(Long id) {
		// Invoke Business Service
		List<DemoDept> resutl = service.searchByParentId(id);
		// Return json
		return jsonObject("tree",resutl);
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, DemoDeptSearchForm searchForm,
			BindingResult bindingResult) throws Exception {
		// Validation

		// Invoke Business Service
		Page<DemoDept> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "demo.csv");
		String[] cols = { "id", "name" };
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());

		// No return, write response directly
	}

	@RequestMapping
	public String show(Long id, Model model) {
		// Invoke Business Service
		DemoDept entity = service.load(id);

		// prepare model, referenceData
		preModel(model);

		// Set Model, formBackingObject
		model.addAttribute( entity);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> load(Long id) {
		// Invoke Business Service
		DemoDept demoDept;
		demoDept = service.load(id);

		// Return json
		return jsonObject( demoDept);
	}

	@RequestMapping
	public String edit(Long id, Model model) {
		// Invoke Business Service
		DemoDept entity;
		entity = service.load(id);

		// prepare model, referenceData
		preModel(model);
		// Set Model,formBackingObject)
		model.addAttribute( entity);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> update(@Valid DemoDept entity, BindingResult bindingResult) {
		// Validation by demoDeptValidator
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
	public String add(Long parentId, Model model) {
		// prepare model, referenceData
		preModel(model);
		
		// spring MVC will not put Long type into implicitModel, must put in explicitly way
		model.addAttribute("parentId", parentId);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> save(@Valid DemoDept entity, BindingResult bindingResult) {
		// Validation by demoDeptValidator
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
