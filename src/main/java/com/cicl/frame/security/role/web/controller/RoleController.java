/** 
 * @(#)RoleController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.role.web.controller;

import java.util.HashSet;
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
import com.cicl.frame.security.authority.entity.Authority;
import com.cicl.frame.security.authority.service.AuthorityService;
import com.cicl.frame.security.role.dictionary.RoleConstant;
import com.cicl.frame.security.role.dictionary.RoleStatusList;
import com.cicl.frame.security.role.entity.Role;
import com.cicl.frame.security.role.service.RoleService;
import com.cicl.frame.security.role.web.form.RoleSearchForm;
import com.cicl.frame.security.role.web.validator.RoleSearchFormValidator;
import com.cicl.frame.security.role.web.validator.RoleValidator;

/**
 * Class RoleController
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 上午09:05:03
 */
@Controller
@RequestMapping("/security/role/*")
public class RoleController extends AbstractJsonController {
	@Autowired
	private RoleService service;
	@Autowired
	private AuthorityService authorityService;
	@Autowired
	private RoleSearchFormValidator searchFormValidator;
	@Autowired
	private RoleValidator entityValidator;
	@Autowired
	private RoleStatusList roleStatusList;
	
	public RoleController() {
		super();
	}

	public RoleController(RoleService service) {
		super();
		this.service = service;
	}

	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
		model.addAttribute("roleStatusList", roleStatusList.getMap());
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
	public String select(Model model) {
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
	public Map<String, Object> search(@Valid RoleSearchForm searchForm, BindingResult bindingResult) {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		Page<Role> page = service.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, RoleSearchForm searchForm,
			BindingResult bindingResult) throws Exception {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// TODO:How to render to original view with bindingResult?
			return;
		}

		// Invoke Business Service
		Page<Role> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "role.csv");
		String[] cols = { "name","code","sortOrder","status","datatype","description" };
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());

		// No return, write response directly
	}

	@RequestMapping
	public String show(Long id, Model model) {
		// Invoke Business Service
		Role role = service.load(id);

		// prepare model, referenceData
		preModel(model);

		// Set Model, formBackingObject
		model.addAttribute(role);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> load(Long id) {
		// Invoke Business Service
		Role role;
		role = service.load(id);

		// Return json
		Map<String,Object> model = jsonObject(role);
		jsonObject(model, "authorities", role.getAuthorities());
		return model;
	}

	@RequestMapping
	public String edit(Long id, Model model) {
		// Invoke Business Service
		Role role;
		role = service.load(id);

		// prepare model, referenceData
		preModel(model);
		// Set Model,formBackingObject)
		model.addAttribute(role);
		
		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> update(@Valid Role role, BindingResult bindingResult) {
		// Validation by roleValidator
		entityValidator.validate(role, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// TODO:how to ignore unnecessarily data loading
		role.setAuthorities(service.load(role.getId()).getAuthorities());
		// Invoke Business Service
		service.merge(role, RoleConstant.OP_EDIT);

		// Return json, ok message
		return jsonOk();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	@ResponseBody
	public Map<String, Object> updateAuthorities(Long id, Long[] childrenIds) {		
		// Invoke Business Service
		Role role;
		role = service.load(id);
		List<Authority> newAuthorities = authorityService.load(childrenIds);
		role.setAuthorities(new HashSet(newAuthorities));
		
		service.merge(role, RoleConstant.OP_EDIT);

		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	public String add(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> save(@Valid Role role, BindingResult bindingResult) {
		// Validation by roleValidator
		entityValidator.validate(role, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.save(role, RoleConstant.OP_ADD);

		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> remove(Long[] ids) {
		// Invoke Business Service
		service.remove(ids, RoleConstant.OP_REMOVE);

		// Return json, ok message
		return jsonOk();
	}
}
