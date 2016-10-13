/** 
 * @(#)${className}.java 1.0.0 2011-4-22 04:46:32  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package ${parentPackage}.${package}.${subpackage}.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.codehaus.jackson.annotate.JsonIgnore;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.cicl.frame.core.entity.AbstractEntity;
import ${parentPackage}.${package}.${subpackage}.dictionary.${className}Constant;

/**
 * Class ${className}
 * 
 * @author ${author}
 * @version ${revision}, $Date: 2011-4-22 04:46:32
 */
public class ${className} extends AbstractEntity<${pk.type}> {
	private static final long serialVersionUID = 1L;
	
	<#list col as r>
	${r.annotation.field}
	private ${r.type.java} ${r.name};
	</#list>

	<#list col as r>
	${r.annotation.getter}
	public ${r.type.java} <#if r.type.java=='boolean'>is<#else>get</#if>${r.name?cap_first}() {
		return this.${r.name};
	}
	public void set${r.name?cap_first}(${r.type.java} ${r.name}) {
		this.${r.name} = ${r.name};
	}
	</#list>
	
	<#if display.type=="TREE">
	private ${className} parent;
	private Set<${className}> children;
	public ${className} getParent() {
		return parent;
	}
	public void setParent(${className} parent) {
		this.parent = parent;
	}
	@JsonIgnore
	public Set<${className}> getChildren() {
		return children;
	}
	public void setChildren(Set<${className}> children) {
		this.children = children;
	}
	</#if>

	//@Override
	public String getEntityType() {
		return ${className}Constant.ENTITY_TYPE;
	}
}
