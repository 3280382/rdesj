/** 
 * @(#)AuthorityController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.authority.web.controller;

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
import com.cicl.frame.security.authority.dictionary.AuthorityConstant;
import com.cicl.frame.security.authority.dictionary.AuthorityStatusList;
import com.cicl.frame.security.authority.entity.Authority;
import com.cicl.frame.security.authority.service.AuthorityService;
import com.cicl.frame.security.authority.web.form.AuthoritySearchForm;
import com.cicl.frame.security.authority.web.validator.AuthoritySearchFormValidator;
import com.cicl.frame.security.authority.web.validator.AuthorityValidator;

/**
 * Class AuthorityController
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 09:05:03
 */
@Controller
@RequestMapping("/security/authority/*")
public class AuthorityController extends AbstractJsonController {
	@Autowired
	private AuthorityService service;
	@Autowired
	private AuthoritySearchFormValidator searchFormValidator;
	@Autowired
	private AuthorityValidator entityValidator;
	@Autowired
	private AuthorityStatusList authorityStatusList;

	public AuthorityController() {
		super();
	}

	public AuthorityController(AuthorityService service) {
		super();
		this.service = service;
	}

	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
		model.addAttribute("authorityStatusList", authorityStatusList.getMap());
		model.addAttribute("authorityList", service.search(null));
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
	public Map<String, Object> loadTree(Long id) {
		// Invoke Business Service
		List<Authority> resutl = service.searchByParentId(id);
		// Return json
		return jsonObject("tree",resutl);
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> search(@Valid AuthoritySearchForm searchForm, BindingResult bindingResult) {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		Page<Authority> page = service.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, AuthoritySearchForm searchForm,
			BindingResult bindingResult) throws Exception {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// TODO:How to render to original view with bindingResult?
			return;
		}

		// Invoke Business Service
		Page<Authority> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "authority.csv");
		String[] cols = { "parentId","name","code","sortOrder","status","datatype","description" };
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());

		// No return, write response directly
	}

	@RequestMapping
	public String show(Long id, Model model) {
		// Invoke Business Service
		Authority authority = service.load(id);

		// prepare model, referenceData
		preModel(model);

		// Set Model, formBackingObject
		model.addAttribute(authority);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> load(Long id) {
		// Invoke Business Service
		Authority authority;
		authority = service.load(id);

		// Return json
		return jsonObject(authority);
	}

	@RequestMapping
	public String edit(Long id, Model model) {
		// Invoke Business Service
		Authority authority;
		authority = service.load(id);

		// prepare model, referenceData
		preModel(model);
		// Set Model,formBackingObject)
		model.addAttribute(authority);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> update(@Valid Authority authority, BindingResult bindingResult) {
		// Validation by authorityValidator
		entityValidator.validate(authority, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.merge(authority, AuthorityConstant.OP_EDIT);
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
	public Map<String, Object> save(@Valid Authority authority, BindingResult bindingResult) {
		// Validation by authorityValidator
		entityValidator.validate(authority, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.save(authority,  AuthorityConstant.OP_ADD);
		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> remove(Long[] ids) {
		// Invoke Business Service
		service.remove(ids, AuthorityConstant.OP_REMOVE);

		// Return json, ok message
		return jsonOk();
	}
}
