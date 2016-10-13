/** 
 * @(#)SysUserController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.sysuser.web.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.util.CSVFileUtils;
import com.cicl.frame.core.web.controller.AbstractJsonController;
import com.cicl.frame.core.web.util.ResponseUtils;
import com.cicl.frame.security.organization.service.OrganizationService;
import com.cicl.frame.security.role.entity.Role;
import com.cicl.frame.security.role.service.RoleService;
import com.cicl.frame.security.sysuser.dictionary.SysUserConstant;
import com.cicl.frame.security.sysuser.dictionary.SysUserStatusList;
import com.cicl.frame.security.sysuser.entity.SysUser;
import com.cicl.frame.security.sysuser.service.SysUserDetailsService;
import com.cicl.frame.security.sysuser.service.SysUserService;
import com.cicl.frame.security.sysuser.web.form.SysUserSearchForm;
import com.cicl.frame.security.sysuser.web.validator.SysUserSearchFormValidator;
import com.cicl.frame.security.sysuser.web.validator.SysUserValidator;

/**
 * Class SysUserController
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-6 上午09:05:03
 */
@Controller
@RequestMapping("/security/sysuser/*")
public class SysUserController extends AbstractJsonController {
	@Autowired
	private SysUserService service;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SysUserSearchFormValidator searchFormValidator;
	@Autowired
	private SysUserValidator entityValidator;
	@Autowired
	private SysUserStatusList sysUserStatusList;

	public SysUserController() {
		super();
	}

	public SysUserController(SysUserService service) {
		super();
		this.service = service;
	}

	// prepare model for list,show,add,edit pages
	private void preModel(Model model) {
		model.addAttribute("sysUserStatusList", sysUserStatusList.getMap());
		model.addAttribute("organizationList", organizationService.search(null));
	}
	
	@RequestMapping
	public String login() {
		// Return to view
		return null;
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
	public String listDemo(Model model) {
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
	@ResponseBody
	public Map<String, Object> search(@Valid SysUserSearchForm searchForm, BindingResult bindingResult) {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}

		// Invoke Business Service
		Page<SysUser> page = service.searchForm(searchForm);
		// Return json
		return jsonPage(page);
	}

	@RequestMapping
	public void list2csv(HttpServletResponse response, SysUserSearchForm searchForm,
			BindingResult bindingResult) throws Exception {
		// Validation
		searchFormValidator.validate(searchForm, bindingResult);
		if (bindingResult.hasErrors()) {
			// TODO:How to render to original view with bindingResult?
			return;
		}

		// Invoke Business Service
		Page<SysUser> page = service.searchForm(searchForm);

		ResponseUtils.setCSVFileDownloadHeader(response, "sysUser.csv");
		String[] cols = { "username","loginName","password","email","status","accountNonExpired","credentialsNonExpired","enabled","accountNonLocked","organizationId","code","mobile","phone","address","description","recentPassword","isLogin","tryTimes","failLoginTimes" };
		CSVFileUtils.getCSVFile(page.getList(), cols, response.getWriter());

		// No return, write response directly
	}

	@RequestMapping
	public String show(Long id, Model model) {
		// Invoke Business Service
		SysUser sysUser = service.load(id);

		// prepare model, referenceData
		preModel(model);

		// Set Model, formBackingObject
		model.addAttribute(sysUser);

		// Return to view
		return null;
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> load(Long id) {
		// Invoke Business Service
		SysUser sysUser;
		sysUser = service.load(id);
		
		// Return json
		Map<String,Object> model = jsonObject(sysUser);
		jsonObject(model, "roles", sysUser.getRoles());
		return model;
	}

	@RequestMapping
	public String edit(Long id, Model model) {
		// Invoke Business Service
		SysUser sysUser;
		sysUser = service.load(id);

		// prepare model, referenceData
		preModel(model);
		// Set Model,formBackingObject)
		model.addAttribute(sysUser);

		// Return to view
		return null;
	}	

	@RequestMapping
	@ResponseBody
	public Map<String, Object> update(@Valid SysUser sysUser, BindingResult bindingResult) {
		// Validation by sysUserValidator
		entityValidator.validate(sysUser, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}
		
		// TODO:how to ignore unnecessarily data loading
		sysUser.setRoles(service.load(sysUser.getId()).getRoles());
		// Invoke Business Service
		service.merge(sysUser, SysUserConstant.OP_EDIT);

		// Return json, ok message
		return jsonOk();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping
	@ResponseBody
	public Map<String, Object> updateRoles(Long id, Long[] childrenIds) {		
		// Invoke Business Service
		SysUser sysUser;
		sysUser = service.load(id);
		List<Role> newRole = roleService.load(childrenIds);
		sysUser.setRoles(new HashSet(newRole));
		
		service.merge(sysUser, SysUserConstant.OP_EDIT);
		
		// Return json, ok message
		return jsonOk();
	}
	
	@RequestMapping
	public String editProfile(Model model) {		
		// Invoke Business Service
		SysUser sysUser;
		sysUser = service.load(SysUserDetailsService.getCurrentSysUserId());

		// prepare model, referenceData
		preModel(model);
		// Set Model,formBackingObject)
		model.addAttribute(sysUser);
		
		// Return to view
		return null;
	}
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> updateProfile(@Valid SysUser entity, BindingResult bindingResult) {
		// Check permission
		if( !SysUserDetailsService.isCurrentUser(entity.getId()) ){
			return jsonError("not current user");
		}
		
		// Validation by sysUserValidator
		entityValidator.validate(entity, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}
		
		// Just modify allow fields 
		SysUser sysUser = service.load(entity.getId());
		sysUser.setUsername(entity.getUsername());
		sysUser.setLoginName(entity.getLoginName());
		sysUser.setAddress(entity.getAddress());
		sysUser.setPhone(entity.getPhone());
		sysUser.setEmail(entity.getEmail());
		sysUser.setMobile(entity.getMobile());
		sysUser.setDescription(entity.getDescription());

		// Invoke Business Service
		service.merge(sysUser, SysUserConstant.OP_EDIT);

		// Return json, ok message
		return jsonOk();
	}
	
	@RequestMapping
	public String editPassword(Model model) {		
		// prepare model, referenceData
		preModel(model);

		// Return to view
		return null;
	}	
		
	@RequestMapping
	@ResponseBody
	public Map<String, Object> updatePassword(String oldPassword, String newPassword) {
		SysUser sysUser = service.load(SysUserDetailsService.getCurrentSysUserId());
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();		
		
		// verify password
		String oldPasswordMd5 = md5.encodePassword(oldPassword, null);
		if( !sysUser.getPassword().equals(oldPasswordMd5) ){
			return jsonError("wrong password");
		}		
		
		String newPasswordMd5 = md5.encodePassword(newPassword, null);		
		sysUser.setPassword(newPasswordMd5);
		
		// Invoke Business Service
		service.merge(sysUser, SysUserConstant.OP_EDIT);

		// Return json, ok message
		return jsonOk();
	}
	
	@RequestMapping
	public String editPasswordAdmin(Long id, Model model) {		
		// prepare model, referenceData
		preModel(model);
		model.addAttribute("id", id);

		// Return to view
		return null;
	}
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> updatePasswordAdmin(Long id, String newPassword) {
		SysUser sysUser = service.load(id);
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();		
		
		String newPasswordMd5 = md5.encodePassword(newPassword, null);		
		sysUser.setPassword(newPasswordMd5);
		
		// Invoke Business Service
		service.merge(sysUser, SysUserConstant.OP_RESETPWD);

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
	public Map<String, Object> save(@Valid SysUser sysUser, BindingResult bindingResult) {
		// Validation by sysUserValidator
		entityValidator.validate(sysUser, bindingResult);
		// Check binding, constraint validation and business validation result
		if (bindingResult.hasErrors()) {
			// Return json, validation error
			return jsonError(bindingResult);
		}
			
		// Invoke Business Service
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String md5pwd = md5.encodePassword(sysUser.getPassword(), null);
		sysUser.setPassword(md5pwd);
		
		service.save(sysUser, SysUserConstant.OP_ADD);

		// Return json, ok message
		return jsonOk();
	}

	@RequestMapping
	@ResponseBody
	public Map<String, Object> remove(Long[] ids) {
		// Invoke Business Service
		service.remove(ids, SysUserConstant.OP_REMOVE);

		// Return json, ok message
		return jsonOk();
	}
}
