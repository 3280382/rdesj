/** 
 * @(#)${className}Controller.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package ${parentPackage}.${package}.${subpackage}.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.util.CSVFileUtils;
import com.cicl.frame.core.web.controller.AbstractJsonController;
import com.cicl.frame.core.web.util.ResponseUtils;

import ${parentPackage}.${package}.${subpackage}.entity.${className};
import ${parentPackage}.${package}.${subpackage}.service.${className}Service;
import ${parentPackage}.${package}.${subpackage}.web.form.${className}SearchForm;
import ${parentPackage}.${package}.${subpackage}.web.validator.${className}SearchFormValidator;
import ${parentPackage}.${package}.${subpackage}.web.validator.${className}Validator;
import ${parentPackage}.${package}.${subpackage}.dictionary.${className}Constant;

/**
 * Class ${className}Controller
 * @author ${author}
 * @version ${revision}, $Date: 2011-5-6 09:05:03
 */
@Controller
@RequestMapping("/${package}/${className?lower_case}/*")
public class ${className}Controller extends AbstractJsonController {
	@Autowired
	private ${className}Service service;
	@Autowired
	private ${className}SearchFormValidator searchFormValidator;
	@Autowired
	private ${className}Validator entityValidator;

	public ${className}Controller() {
		super();
	}

	public ${className}Controller(${className}Service service) {
		super();
		this.service = service;
	}

	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
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
	<#if display.type=="TREE">
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
	public Map<String, Object> loadTree(${pk.type} id) {
		// Invoke Business Service
		List<${className}> resutl = service.searchByParentId(id);
		// Return json
		return jsonObject("tree",resutl);
	}
	</#if>

	@RequestMapping
	@ResponseBody
	public Map<String, Object> search(@Valid ${className}SearchForm searchForm, BindingResult bindingResult) {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		Page<${className}> page = service.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, ${className}SearchForm searchForm,
			BindingResult bindingResult) throws Exception {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// TODO:How to render to original view with bindingResult?
			return;
		}

		// Invoke Business Service
		Page<${className}> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "${objectName}.csv");
		String[] cols = { <#list col as r>"${r.name}"<#if r_has_next>,</#if></#list> };
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());

		// No return, write response directly
	}

	@RequestMapping
	public String show(${pk.type} id, Model model) {
		// Invoke Business Service
		${className} ${objectName} = service.load(id);

		// prepare model, referenceData
		preModel(model);

		// Set Model, formBackingObject
		model.addAttribute(${objectName});

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> load(${pk.type} id) {
		// Invoke Business Service
		${className} ${objectName};
		${objectName} = service.load(id);

		// Return json
		return jsonObject(${objectName});
	}

	@RequestMapping
	public String edit(${pk.type} id, Model model) {
		// Invoke Business Service
		${className} ${objectName};
		${objectName} = service.load(id);

		// prepare model, referenceData
		preModel(model);
		// Set Model,formBackingObject)
		model.addAttribute(${objectName});

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> update(@Valid ${className} ${objectName}, BindingResult bindingResult) {
		// Validation by ${objectName}Validator
		entityValidator.validate(${objectName}, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.merge(${objectName}, ${className}Constant.OP_EDIT);

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
	public Map<String, Object> save(@Valid ${className} ${objectName}, BindingResult bindingResult) {
		// Validation by ${objectName}Validator
		entityValidator.validate(${objectName}, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.save(${objectName}, ${className}Constant.OP_ADD);
		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> remove(${pk.type}[] ids) {
		// Invoke Business Service
		service.remove(ids, ${className}Constant.OP_REMOVE);

		// Return json, ok message
		return jsonOk();
	}
}
