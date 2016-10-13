/** 
 * @(#)${className}Service.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package ${parentPackage}.${package}.${subpackage}.service;

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
import ${parentPackage}.${package}.${subpackage}.entity.${className};
import ${parentPackage}.${package}.${subpackage}.repository.${className}Repository;
import ${parentPackage}.${package}.${subpackage}.web.form.${className}SearchForm;

/**
 * Class ${className}Service 
 *
 * @author ${author}
 * @version ${revision}, $Date: 2011-4-22 下午04:46:32  
 */
@Transactional
@Service
public class ${className}Service extends AbstractCrudService<${className}, ${pk.type}> {
	@Autowired
	private ${className}Repository repository;
	
	public ${className}Service() {
		super();
	}
	public ${className}Service(${className}Repository repository) {
		super();
		this.repository = repository;
	}
	
	//@Override
	public IBaseCrudRepository<${className}, ${pk.type}> getIBaseCrudRepository(){
		return repository;
	}

	public Page<${className}> searchForm(${className}SearchForm ${objectName}SearchForm) {
		${className} ${objectName} = null;
		
		QueryObject<${className}> queryObject = QueryObjectFactory.createQueryObject(${className}.class);
		QueryParam queryParam = new QueryParam();
		
		if (${objectName}SearchForm != null) {
			${objectName} = ${objectName}SearchForm.get${className}();
		}
		
		if (${objectName} != null) {			
			<#list col as r>
			if(${objectName}.get${r.name?cap_first}()!=null){
				<#if r.type.java=="String">queryParam.and(new QueryParam("${r.name}", QueryParam.OPERATOR_LIKE, "%" + ${objectName}.get${r.name?cap_first}() + "%"));
				<#else>queryParam.and(new QueryParam("${r.name}", QueryParam.OPERATOR_EQ, ${objectName}.get${r.name?cap_first}()));</#if>
			}
			</#list>
		}
		
		queryObject.setQueryParam(queryParam);
		Page<${className}> page = this.search(queryObject, true, ${objectName}SearchForm);

		return page;
	}
	<#if display.type=="TREE">
	public List<${className}> searchByParentId(${pk.type} id) {
		QueryObject<${className}> queryObject = QueryObjectFactory.createQueryObject(${className}.class);
		QueryParam queryParam = new QueryParam();
		
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
	</#if>
}
