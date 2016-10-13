/** 
 * @(#)DictionaryService.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.system.dictionary.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cicl.frame.core.repository.IBaseCrudRepository;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.repository.query.QueryObject;
import com.cicl.frame.core.repository.query.QueryObjectFactory;
import com.cicl.frame.core.repository.query.QueryParam;
import com.cicl.frame.core.repository.query.QueryProjections;
import com.cicl.frame.core.service.AbstractCrudService;
import com.cicl.frame.system.dictionary.entity.Dictionary;
import com.cicl.frame.system.dictionary.repository.DictionaryRepository;
import com.cicl.frame.system.dictionary.web.form.DictionarySearchForm;

/**
 * Class DictionaryService 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32  
 */
@Transactional
@Service
public class DictionaryService extends AbstractCrudService<Dictionary, Long> {
	@Autowired
	private DictionaryRepository repository;
	
	public DictionaryService() {
		super();
	}
	public DictionaryService(DictionaryRepository repository) {
		super();
		this.repository = repository;
	}
	
	//@Override
	public IBaseCrudRepository<Dictionary, Long> getIBaseCrudRepository(){
		return repository;
	}

	public Page<Dictionary> searchForm(DictionarySearchForm dictionarySearchForm) {
		Dictionary dictionary = null;
		
		QueryObject<Dictionary> queryObject = QueryObjectFactory.createQueryObject(Dictionary.class);
		QueryParam queryParam = new QueryParam();
		
		if (dictionarySearchForm != null) {
			dictionary = dictionarySearchForm.getDictionary();
		}
		
		if (dictionary != null) {			
			if(dictionary.getParentId()!=null){
				queryParam.and(new QueryParam("parentId", QueryParam.OPERATOR_EQ, dictionary.getParentId()));
			}
			if(dictionary.getNodesType()!=null){
				queryParam.and(new QueryParam("nodesType", QueryParam.OPERATOR_LIKE, "%" + dictionary.getNodesType() + "%"));
				
			}
			if(dictionary.getParentKey()!=null){
				queryParam.and(new QueryParam("parentKey", QueryParam.OPERATOR_LIKE, "%" + dictionary.getParentKey() + "%"));
				
			}
			if(dictionary.getKey()!=null){
				queryParam.and(new QueryParam("key", QueryParam.OPERATOR_LIKE, "%" + dictionary.getKey() + "%"));
				
			}
			if(dictionary.getAlias()!=null){
				queryParam.and(new QueryParam("alias", QueryParam.OPERATOR_LIKE, "%" + dictionary.getAlias() + "%"));
				
			}
			if(dictionary.getValue()!=null){
				queryParam.and(new QueryParam("value", QueryParam.OPERATOR_LIKE, "%" + dictionary.getValue() + "%"));
				
			}
			if(dictionary.getValuetype()!=null){
				queryParam.and(new QueryParam("valuetype", QueryParam.OPERATOR_LIKE, "%" + dictionary.getValuetype() + "%"));
				
			}
			if(dictionary.getValidation()!=null){
				queryParam.and(new QueryParam("validation", QueryParam.OPERATOR_LIKE, "%" + dictionary.getValidation() + "%"));
				
			}
			if(dictionary.getValue1()!=null){
				queryParam.and(new QueryParam("value1", QueryParam.OPERATOR_LIKE, "%" + dictionary.getValue1() + "%"));
				
			}
			if(dictionary.getValuetype1()!=null){
				queryParam.and(new QueryParam("valuetype1", QueryParam.OPERATOR_LIKE, "%" + dictionary.getValuetype1() + "%"));
				
			}
			if(dictionary.getValidation1()!=null){
				queryParam.and(new QueryParam("validation1", QueryParam.OPERATOR_LIKE, "%" + dictionary.getValidation1() + "%"));
				
			}
			if(dictionary.getSortOrder()!=null){
				queryParam.and(new QueryParam("sortOrder", QueryParam.OPERATOR_LIKE, "%" + dictionary.getSortOrder() + "%"));
				
			}
			if(dictionary.getEnable()!=null){
				queryParam.and(new QueryParam("enable", QueryParam.OPERATOR_EQ, dictionary.getEnable()));
			}
			if(dictionary.getVisualable()!=null){
				queryParam.and(new QueryParam("visualble", QueryParam.OPERATOR_EQ, dictionary.getVisualable()));
			}
			if(dictionary.getDisplayType()!=null){
				queryParam.and(new QueryParam("displayType", QueryParam.OPERATOR_LIKE, "%" + dictionary.getDisplayType() + "%"));
				
			}
			if(dictionary.getEditable()!=null){
				queryParam.and(new QueryParam("editable", QueryParam.OPERATOR_EQ, dictionary.getEditable()));
			}
			if(dictionary.getDescription()!=null){
				queryParam.and(new QueryParam("description", QueryParam.OPERATOR_LIKE, "%" + dictionary.getDescription() + "%"));
				
			}
		}
		
		queryObject.setQueryParam(queryParam);
		Page<Dictionary> page = this.search(queryObject, true, dictionarySearchForm);

		return page;
	}
	public List<Dictionary> searchByParentId(Long id) {
		QueryObject<Dictionary> queryObject = QueryObjectFactory.createQueryObject(Dictionary.class);
		QueryParam queryParam = new QueryParam();
		queryParam.and(new QueryParam("nodesType", QueryParam.OPERATOR_EQ, "CAT"));
		queryParam.and(new QueryParam("visualable", QueryParam.OPERATOR_EQ, 1L));
		queryObject.setQueryParam(queryParam);
		
		//set default sort by sortOrder
		QueryProjections queryProjections = new QueryProjections();		
		queryObject.setQueryProjections(queryProjections);
		
		queryProjections.setOrderProperty("sortOrder");
		queryProjections.setDescFlag(false);
		
		if(id!=null){			
			queryParam.and(new QueryParam("parentId", QueryParam.OPERATOR_EQ, id));
		}		
		
		queryObject.setQueryParam(queryParam);
		return this.search(queryObject);
	}
	
	public List<Dictionary> searchByParentKey(String key) {
		QueryObject<Dictionary> queryObject = QueryObjectFactory.createQueryObject(Dictionary.class);
		QueryParam queryParam = new QueryParam();
		//queryParam.and(new QueryParam("nodesType", QueryParam.OPERATOR_EQ, "VALUE"));
		queryObject.setQueryParam(queryParam);
		
		//set default sort by sortOrder
		QueryProjections queryProjections = new QueryProjections();		
		queryObject.setQueryProjections(queryProjections);
		
		queryProjections.setOrderProperty("sortOrder");
		queryProjections.setDescFlag(false);
		
		if(key!=null){			
			queryParam.and(new QueryParam("parentKey", QueryParam.OPERATOR_EQ, key));
		}		
		
		queryObject.setQueryParam(queryParam);
		return this.search(queryObject);
	}
	
	public Dictionary searchSingleByKey(String key){
		QueryObject<Dictionary> queryObject = QueryObjectFactory.createQueryObject(Dictionary.class);
		QueryParam queryParam = new QueryParam();
		queryParam.and(new QueryParam("key", QueryParam.OPERATOR_EQ, key));
		queryObject.setQueryParam(queryParam);
		List<Dictionary> result = this.search(queryObject);
		return result.size()>0 ? result.get(0) : null;
	}
	
	public List<Dictionary> searchByParentKey(String key, boolean descendant) {
		if(descendant){
			Dictionary parent = searchSingleByKey(key);
			if(parent!=null){
				String parentId = parent.getId()+"";
				parentId = parentId.substring(0, parentId.length()-1);
				
				String expql = "FROM " + Dictionary.class.getName() +" d ";
				expql += " WHERE d.id LIKE '" + parentId + "%'";
				expql += " AND d.id !=" + parent.getId();
				
				return repository.findByExpQL(expql, false, 0, -1);
			}else{
				return new ArrayList<Dictionary>();
			}
		}else{
			return searchByParentKey(key);
		}
	}
}
