/** 
 * @(#)viewHistorylogShortListController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.workflow.web.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.cicl.frame.core.web.controller.AbstractJsonController;
import com.cicl.frame.workflow.entity.WorkflowDefinition;
import com.cicl.frame.workflow.service.WorkflowService;
import com.cicl.frame.workflow.web.form.WorkflowDefinitionSearchForm;

/**
 * Class WorkflowController
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 上午09:05:03
 */

@Controller
@RequestMapping("/workflow/definition/*")
public class WorkflowDefinitionController extends AbstractJsonController {
	private static final Log LOG = LogFactory
			.getLog(WorkflowDefinitionController.class);

	@Autowired
	private WorkflowService workflowService;
	
	public WorkflowDefinitionController() {
		super();
	}

	@RequestMapping
	public String index() {
		// Return to view
		return null;
	}

	@RequestMapping
	public String list(Model model) {
		
		return null;
	}

	@RequestMapping
	public String upload(@RequestParam("file") MultipartFile file, String name)
			throws Exception {
		if (!file.isEmpty()) {
			// just demo get bytes
			byte[] bytes = file.getBytes();
			LOG.info("user input file name:" + name);
			LOG.info(file.getName() + ", len:" + bytes.length);
			
			workflowService.deployWorkflowDefintion(file.getInputStream(), null);
			// store the bytes somewhere
			// Return ok page
			return "/upload/uploadOk";
		} else {
			return "/upload/uploadError";
		}
	}
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> startup(Long[] ids) {
		for(Long id : ids){
			try{
				workflowService.startup(id);
			}catch(Exception ex){
				// 
			}
		}
		// Return json, ok message
		return jsonOk();
	}
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> search(@Valid WorkflowDefinitionSearchForm searchForm, BindingResult bindingResult) {
		// Invoke Business Service
		Page<WorkflowDefinition> page = workflowService.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}
	
	@RequestMapping
	public String show(Long id, Model model) {
		// Invoke Business Service
		WorkflowDefinition wd = workflowService.load(id);
		
		String definitionXML = wd.getDefinitionXml();
		model.addAttribute("definitionXML",definitionXML);
		model.addAttribute("workflowName", wd.getName());
		// Return to view
		return null;
	}
	
	@RequestMapping
	public String design(Long id, Model model) {
		model.addAttribute("definitionID", id);
		return null;
	}
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> fetchSchema(Long id, Model model) {
		WorkflowDefinition wd = id==null || id < 1 ? null : workflowService.load(id);
		String xml = null;
		String schema = null;
		if(wd!=null){
			xml = wd.getDefinitionXml();
			schema = wd.getDefinitionSchema();
			schema = schema==null ? xml : schema;
		}
		xml = xml == null ? "" : xml;
		schema = schema == null ? "" : schema;
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("xml", xml);
		result.put("schema", schema);
		return result;
	}
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> deploy(String xml, String schema) {
		try{
			workflowService.deployWorkflowDefintion(xml, schema);
			return jsonOk();
		}catch(Exception ex){
			return jsonError(ex.getMessage());
		}
	}
}
