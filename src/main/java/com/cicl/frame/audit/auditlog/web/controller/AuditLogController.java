/** 
 * @(#)AuditLogController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.audit.auditlog.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cicl.frame.audit.auditlog.entity.AuditLog;
import com.cicl.frame.audit.auditlog.service.AuditLogService;
import com.cicl.frame.audit.auditlog.web.form.AuditLogSearchForm;
import com.cicl.frame.audit.auditlog.web.validator.AuditLogSearchFormValidator;
import com.cicl.frame.audit.auditlog.web.validator.AuditLogValidator;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.util.CSVFileUtils;
import com.cicl.frame.core.web.controller.AbstractJsonController;
import com.cicl.frame.core.web.util.ResponseUtils;
import com.cicl.frame.security.authority.service.AuthorityService;


/**
 * Class AuditLogController
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 上午09:05:03
 */
@Controller
@RequestMapping("/audit/auditlog/*")
public class AuditLogController extends AbstractJsonController {
	@Autowired
	private AuditLogService service;
	@Autowired
	private AuditLogSearchFormValidator searchFormValidator;
	@Autowired
	private AuditLogValidator entityValidator;
	@Autowired
	private AuthorityService authorityService;

	public AuditLogController() {
		super();
	}

	public AuditLogController(AuditLogService service) {
		super();
		this.service = service;
	}

	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
		model.addAttribute("authorityList", authorityService.search(null));
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
	@ResponseBody
	public Map<String, Object> search(@Valid AuditLogSearchForm searchForm, BindingResult bindingResult) {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		Page<AuditLog> page = service.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, AuditLogSearchForm searchForm,
			BindingResult bindingResult) throws Exception {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// TODO:How to render to original view with bindingResult?
			return;
		}

		// Invoke Business Service
		Page<AuditLog> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "auditLog.csv");
		String[] cols = { "userId","username","loginName","userIp","targetId","targetEntityType","targetName","targetDesc","targetSnapshot","opType","opTime","opDesc" };
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());

		// No return, write response directly
	}

	@RequestMapping
	public String show(Long id, Model model) {
		// Invoke Business Service
		AuditLog auditLog = service.load(id);

		// prepare model, referenceData
		preModel(model);

		// Set Model, formBackingObject
		model.addAttribute(auditLog);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> load(Long id) {
		// Invoke Business Service
		AuditLog auditLog;
		auditLog = service.load(id);

		// Return json
		return jsonObject(auditLog);
	}

	@RequestMapping
	public String edit(Long id, Model model) {
		// Invoke Business Service
		AuditLog auditLog;
		auditLog = service.load(id);

		// prepare model, referenceData
		preModel(model);
		// Set Model,formBackingObject)
		model.addAttribute(auditLog);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> update(@Valid AuditLog entity, BindingResult bindingResult) {
		// Validation by auditLogValidator
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
	public String add(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> save(@Valid AuditLog entity, BindingResult bindingResult) {
		// Validation by auditLogValidator
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
