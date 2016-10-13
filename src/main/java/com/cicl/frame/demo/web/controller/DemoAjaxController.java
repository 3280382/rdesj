/** 
 * @(#DemoAjaxController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.demo.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.util.CSVFileUtils;
import com.cicl.frame.core.web.controller.AbstractJsonController;
import com.cicl.frame.core.web.util.ResponseUtils;
import com.cicl.frame.demo.dictionary.SexList;
import com.cicl.frame.demo.entity.DemoData;
import com.cicl.frame.demo.service.DemoCountryService;
import com.cicl.frame.demo.service.DemoDataService;
import com.cicl.frame.demo.service.DemoProvinceService;
import com.cicl.frame.demo.web.form.DemoDataSearchForm;
import com.cicl.frame.demo.web.validator.DemoDataSearchFormValidator;
import com.cicl.frame.demo.web.validator.DemoDataValidator;
import com.cicl.frame.system.web.controller.UploadController;

/**
 * Class DemoAjaxController
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 上午09:05:03
 */

@Controller
@RequestMapping("/demo/demoajax/*")
public class DemoAjaxController extends AbstractJsonController {
	private static final Log LOG = LogFactory.getLog(DemoAjaxController.class);
	@Autowired
	private DemoDataService service;
	@Autowired
	private DemoDataSearchFormValidator searchFormValidator;
	@Autowired
	private DemoDataValidator entityValidator;
	
	@Autowired
	private DemoCountryService demoCountryService;
	@Autowired
	private DemoProvinceService demoProvinceService;
	@Autowired
	private SexList sexList;

	public DemoAjaxController() {
		super();
	}

	public DemoAjaxController(DemoDataService service) {
		super();
		this.service = service;
	}

	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
		model.addAttribute("sexList", sexList.getMap());
		model.addAttribute("demoCountryList", demoCountryService.search(null));
		model.addAttribute("demoProvinceList", demoProvinceService.searchByCountryId(null));
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
	public Map<String, Object> search(@Valid DemoDataSearchForm searchForm, BindingResult bindingResult) {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		Page<DemoData> page = service.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, DemoDataSearchForm searchForm,
			BindingResult bindingResult) throws Exception {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// TODO:How to render to original view with bindingResult?
			return;
		}

		// Invoke Business Service
		Page<DemoData> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "demodata.csv");
		String[] cols = { "id", "name" };
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());

		// No return, write response directly
	}

	@RequestMapping
	public String show(Long id, Model model) {
		// Invoke Business Service
		DemoData demoData = service.load(id);

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
		DemoData demoData;
		demoData = service.load(id);

		// Return json
		return jsonObject(demoData);
	}

	@RequestMapping
	public String edit(Long id, Model model) {
		// Invoke Business Service
		DemoData demoData;
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
	public Map<String, Object> update(@Valid DemoData entity, BindingResult bindingResult) {
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
	public String add(Model model) {
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> save(@Valid DemoData entity, BindingResult bindingResult) {
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

	@RequestMapping
	public String upload(@RequestParam("file") MultipartFile file, String name) throws Exception{
		if (!file.isEmpty()) {     
			// just demo get bytes
			byte[] bytes = file.getBytes();
			 LOG.info("user input file name:" + name);
            LOG.info(file.getName() + ", len:" + bytes.length);
            
          // store the bytes somewhere	
         // Return ok page
    		return UploadController.UPLOAD_OK;
       } else {
    	   return UploadController.UPLOAD_ERROR;
       }		
	}
}
