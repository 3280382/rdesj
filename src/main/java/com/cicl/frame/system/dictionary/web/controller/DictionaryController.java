/** 
 * @(#)DictionaryController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.system.dictionary.web.controller;

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
import com.cicl.frame.system.dictionary.dictionary.DictionaryConstant;
import com.cicl.frame.system.dictionary.dictionary.NodesTypeList;
import com.cicl.frame.system.dictionary.entity.Dictionary;
import com.cicl.frame.system.dictionary.service.DictionaryService;
import com.cicl.frame.system.dictionary.web.form.DictionarySearchForm;
import com.cicl.frame.system.dictionary.web.validator.DictionarySearchFormValidator;
import com.cicl.frame.system.dictionary.web.validator.DictionaryValidator;

/**
 * Class DictionaryController
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 09:05:03
 */
@Controller
@RequestMapping("/system/dictionary/*")
public class DictionaryController extends AbstractJsonController {
	@Autowired
	private DictionaryService service;
	@Autowired
	private DictionarySearchFormValidator searchFormValidator;
	@Autowired
	private DictionaryValidator entityValidator;
	@Autowired
	private NodesTypeList nodesTypeList;

	public DictionaryController() {
		super();
	}

	public DictionaryController(DictionaryService service) {
		super();
		this.service = service;
	}

	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
		model.addAttribute("nodesTypeList", nodesTypeList.getMap());
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
	public String listDemo(Model model) {
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
		List<Dictionary> resutl = service.searchByParentId(id);
		// Return json
		return jsonObject("tree",resutl);
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> search(@Valid DictionarySearchForm searchForm, BindingResult bindingResult) {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		Page<Dictionary> page = service.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, DictionarySearchForm searchForm,
			BindingResult bindingResult) throws Exception {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// TODO:How to render to original view with bindingResult?
			return;
		}

		// Invoke Business Service
		Page<Dictionary> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "dictionary.csv");
		String[] cols = { "parentId","nodesType","parentKey","key","alias","value","valuetype","validation","value1","valuetype1","validation1","sortOrder","enable","visualble","displayType","editable","description" };
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());

		// No return, write response directly
	}

	@RequestMapping
	public String show(String id, Model model) {
		// Invoke Business Service
		Dictionary dictionary = service.load(Long.parseLong(id));

		// prepare model, referenceData
		preModel(model);

		// Set Model, formBackingObject
		model.addAttribute(dictionary);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> load(String id) {
		// Invoke Business Service
		Dictionary dictionary;
		dictionary = service.load(Long.parseLong(id));

		// Return json
		return jsonObject(dictionary);
	}

	@RequestMapping
	public String edit(String id, Model model) {
		// Invoke Business Service
		Dictionary dictionary;
		dictionary = service.load(Long.parseLong(id));
		
		Dictionary parent = dictionary.getParent();
		String validation = ( parent==null ? "" : parent.getValidation() );
		// prepare model, referenceData
		preModel(model);
		// Set Model,formBackingObject)
		model.addAttribute(dictionary);
		model.addAttribute("validation",validation);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> update(@Valid Dictionary dictionary, BindingResult bindingResult) {
		// Validation by dictionaryValidator
		entityValidator.validate(dictionary, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.merge(dictionary, DictionaryConstant.OP_EDIT);

		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	public String add(String parentId, String parentKey, Model model) {
		// prepare model, referenceData
		preModel(model);
		
		Dictionary parent = service.load(Long.parseLong(parentId));
		String validation = ( parent==null ? "" : parent.getValidation() );
		
		model.addAttribute("parentId", parentId);
		model.addAttribute("parentKey", parentKey);
		model.addAttribute("validation",validation);
		
		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> save(@Valid Dictionary dictionary, BindingResult bindingResult) {
		// Validation by dictionaryValidator
		entityValidator.validate(dictionary, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		service.save(dictionary, DictionaryConstant.OP_ADD);
		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> remove(Long[] ids) {
		// Invoke Business Service
		service.remove(ids, DictionaryConstant.OP_REMOVE);

		// Return json, ok message
		return jsonOk();
	}
}
