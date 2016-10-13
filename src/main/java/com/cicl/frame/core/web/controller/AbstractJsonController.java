/** 
 * @(#)viewHistorylogShortListController.java 1.0.0 2011-2-18 18:19:42  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Conventions;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.cicl.frame.core.repository.page.PageResult;

/**
 * Class AbstractAjaxController
 * json support
 * 1) return json map object, serialize to special data structure
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-3 下午03:30:56  
 * @param <T>
 */
public class AbstractJsonController extends AbstractAnnotationController{

	public AbstractJsonController() {
		super();
	}
	
	/**
	 * return object json string
	 *
	 * @param model
	 * @param key
	 * @param object
	 * @return
	 */
	public Map<String,Object> jsonObject(Map<String,Object> model, String key, Object object){
		model.put(key, object);
		return model;
	}
	
	/**
	 * return common object json string
	 *
	 * @param key
	 * @param object
	 * @return
	 */
	public Map<String,Object> jsonObject(String key, Object object){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put(key, object);
		return model;
	}	
	/**
	 * return object json string, 
	 * Determine the conventional variable name for the supplied
	 * <code>Object</code> based on its concrete type.
	 * <code>com.myapp.Product</code> becomes <code>product</code>;
	 * <code>com.myapp.MyProduct</code> becomes <code>myProduct</code>;
	 * @param object
	 * @return
	 */
	public Map<String,Object> jsonObject(Object object){
		return jsonObject(Conventions.getVariableName(object), object);
	}
	/**
	 * return data grid json string
	 * set data to Model instance from Page<T> instance, including 
	 * result list and page data, render to json stringl, such as:
	 * {
	 * 		results:[{id:1, name:'userName1'}, {id:2, name:'userName2'}],
	 * 		page:2,
	 * 		totalPageCount:10,
	 * 		totalCount:98,
	 * 		pageSize:10
	 * }
	 * Client browser can use this json string to render table by javascript.
	 * @param model
	 * @param page
	 */
	public Map<String,Object> jsonPage(PageResult<?> pageResult){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("results", pageResult.getResults());
		model.put("page", pageResult.getPage()<=0?1:pageResult.getPage());
		model.put("totalPageCount", pageResult.getTotalPageCount());
		model.put("totalCount", pageResult.getTotalCount());
		model.put("pageSize", pageResult.getPageSize());
		return model;
	}
	
	public Map<String,Object> jsonPage(Collection<?> results, int page, int totalPageCount, int totalCount, int pageSize){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("results", results);
		model.put("page", page<=0?1:page);
		model.put("totalPageCount", totalPageCount);
		model.put("totalCount", totalCount);
		model.put("pageSize", pageSize);
		return model;
	}
	
	/**
	 * return validation error by json string.
	 * Client browser can use this json string to binding error message with html input.
	 *
	 * @param errors
	 * @param request
	 */	
	public Map<String,Object> jsonError(Errors errors){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("status","VALID_ERROR");
		//model.put("error_debug",errors);
		
		List<Map<String,Object>> errorsMsg = new ArrayList<Map<String,Object>>();
		Map<String,Object> errorMsg;

		for(FieldError error : errors.getFieldErrors()){			
			errorMsg = new HashMap<String,Object>();
			errorMsg.put("field",error.getField() );
			errorMsg.put("error",getMessageSource().getMessage(error, LocaleContextHolder.getLocale()));
			errorsMsg.add(errorMsg);
		}	
		model.put("errorsMsg",errorsMsg);
		//TODO: add global error message
		
		return model;
	}
	/**
	 *  return exception error by json string
	 *
	 * @param model
	 * @param errors
	 */
	public Map<String,Object> jsonError(Throwable e){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("status","EXCEPTION");
		model.put("exception", e.getMessage());
		return model;
	}
	
	/**
	 *  return business error by json string
	 *
	 * @param errors
	 */
	public Map<String,Object> jsonError(String errorsMsg){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("status", "BUSINESS_ERROR");
		model.put("errorsMsg",errorsMsg);
		return model;
	}

	/**
	 *  return success message by json string
	 *
	 * @param model
	 * @param msg
	 */
	public Map<String,Object> jsonMsg(String msg){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("msg", msg);
		return model;
	}
	public Map<String,Object> jsonOk(){
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("msg", "ok");
		return model;
	}	
}
