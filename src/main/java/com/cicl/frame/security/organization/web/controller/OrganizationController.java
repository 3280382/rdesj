/** 
 * @(#)OrganizationController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.organization.web.controller;

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
import com.cicl.frame.security.organization.dictionary.OrganizationConstant;
import com.cicl.frame.security.organization.dictionary.OrganizationStatusList;
import com.cicl.frame.security.organization.entity.Organization;
import com.cicl.frame.security.organization.service.OrganizationService;
import com.cicl.frame.security.organization.web.form.OrganizationSearchForm;
import com.cicl.frame.security.organization.web.validator.OrganizationSearchFormValidator;
import com.cicl.frame.security.organization.web.validator.OrganizationValidator;


/**
 * Class OrganizationController
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 上午09:05:03
 */
@Controller
@RequestMapping("/security/organization/*")
public class OrganizationController extends AbstractJsonController {
	@Autowired
	private OrganizationService service;
	@Autowired
	private OrganizationSearchFormValidator searchFormValidator;
	@Autowired
	private OrganizationValidator entityValidator;
	@Autowired
	private OrganizationStatusList organizationStatusList;

	public OrganizationController() {
		super();
	}

	public OrganizationController(OrganizationService service) {
		super();
		this.service = service;
	}

	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
		model.addAttribute("organizationList", service.search(null));
		model.addAttribute("organizationStatusList", organizationStatusList.getMap());
	}
	
	@RequestMapping
	public String index() {
		// Return to view
		return null;
	}

	@RequestMapping
	public String list(Model model) {
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
	public String listTree(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}
	
	@RequestMapping
	public String listTreeDetail(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> search(@Valid OrganizationSearchForm searchForm, BindingResult bindingResult) {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		Page<Organization> page = service.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> searchTree(@Valid OrganizationSearchForm searchForm, BindingResult bindingResult) {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}
		
		searchForm.setPageNumber(-1);
		// Invoke Business Service
		Page<Organization> page = service.searchForm(searchForm);
		// Return json
		return jsonObject("tree",page.getList());
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, OrganizationSearchForm searchForm,
			BindingResult bindingResult) throws Exception {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// TODO:How to render to original view with bindingResult?
			return;
		}

		// Invoke Business Service
		Page<Organization> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "organization.csv");
		String[] cols = { "parentId","name","code","sortOrder","status","datatype","description" };
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());

		// No return, write response directly
	}

	@RequestMapping
	public String show(Long id, Model model) {
		// Invoke Business Service
		Organization organization = service.load(id);

		// prepare model, referenceData
		preModel(model);

		// Set Model, formBackingObject
		model.addAttribute(organization);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> load(Long id) {
		// Invoke Business Service
		Organization organization;
		organization = service.load(id);

		// Return json
		return jsonObject(organization);
	}
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> loadTree(Long id) {
		// Invoke Business Service
		List<Organization> resutl = service.searchByParentId(id);
		// Return json
		return jsonObject("tree",resutl);
	}

	@RequestMapping
	public String edit(Long id, Model model) {
		// Invoke Business Service
		Organization organization;
		organization = service.load(id);

		// prepare model, referenceData
		preModel(model);
		// Set Model,formBackingObject)
		model.addAttribute(organization);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> update(@Valid Organization organization, BindingResult bindingResult) {
		// Validation by organizationValidator
		entityValidator.validate(organization, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.merge(organization, OrganizationConstant.OP_EDIT);
		
		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	public String add(Long parentId, Model model) {
		// prepare model, referenceData
		preModel(model);
		model.addAttribute("parentId", parentId);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> save(@Valid Organization organization, BindingResult bindingResult) {
		// Validation by organizationValidator
		entityValidator.validate(organization, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.save(organization, OrganizationConstant.OP_ADD);

		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> remove(Long[] ids) {
		// Invoke Business Service
		service.remove(ids, OrganizationConstant.OP_REMOVE);
		
		// Return json, ok message
		return jsonOk();
	}
	
	@RequestMapping
	public String select(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}
	
	@RequestMapping
	public String selectTree(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}
}
