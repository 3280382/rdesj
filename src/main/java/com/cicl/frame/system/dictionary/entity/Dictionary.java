/** 
 * @(#)Dictionary.java 1.0.0 2011-4-22 04:46:32  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.system.dictionary.entity;

import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.cicl.frame.core.entity.AbstractEntity;
import com.cicl.frame.system.dictionary.dictionary.DictionaryConstant;

/**
 * Class Dictionary
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 04:46:32
 */
public class Dictionary extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;	
	
	private Long parentId;
	@NotEmpty @Length(max = 200)
	private String nodesType;
	
	private String parentKey;
	@NotEmpty @Length(max = 200)
	private String key;
	@Length(max = 200)
	private String alias;
	@NotEmpty @Length(max = 200)
	private String value;
	@Length(max = 200)
	private String valuetype;
	@Length(max = 1000)
	private String validation;
	@Length(max = 200)
	private String value1;
	@Length(max = 200)
	private String valuetype1;
	@Length(max = 1000)
	private String validation1;
	@Length(max = 200)
	private String sortOrder;
	
	private Long enable;
	
	private Long visualable;
	
	private String displayType;
	
	private Long editable;
	@Length(max = 2000)
	private String description;
	
	public Long getParentId() {
		return this.parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public String getNodesType() {
		return this.nodesType;
	}
	public void setNodesType(String nodesType) {
		this.nodesType = nodesType;
	}
	
	public String getParentKey() {
		return this.parentKey;
	}
	public void setParentKey(String parentKey) {
		this.parentKey = parentKey;
	}
	
	public String getKey() {
		return this.key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getAlias() {
		return this.alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValuetype() {
		return this.valuetype;
	}
	public void setValuetype(String valuetype) {
		this.valuetype = valuetype;
	}
	
	public String getValidation() {
		return this.validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}
	
	public String getValue1() {
		return this.value1;
	}
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	
	public String getValuetype1() {
		return this.valuetype1;
	}
	public void setValuetype1(String valuetype1) {
		this.valuetype1 = valuetype1;
	}
	
	public String getValidation1() {
		return this.validation1;
	}
	public void setValidation1(String validation1) {
		this.validation1 = validation1;
	}
	
	public String getSortOrder() {
		return this.sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Long getEnable() {
		return this.enable;
	}
	public void setEnable(Long enable) {
		this.enable = enable;
	}
	
	public Long getVisualable() {
		return this.visualable;
	}
	public void setVisualable(Long visualable) {
		this.visualable = visualable;
	}
	
	public String getDisplayType() {
		return this.displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	
	public Long getEditable() {
		return this.editable;
	}
	public void setEditable(Long editable) {
		this.editable = editable;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	private Dictionary parent;
	private Set<Dictionary> children;
	public Dictionary getParent() {
		return parent;
	}
	public void setParent(Dictionary parent) {
		this.parent = parent;
	}
	@JsonIgnore
	public Set<Dictionary> getChildren() {
		return children;
	}
	public void setChildren(Set<Dictionary> children) {
		this.children = children;
	}

	//@Override
	@Override
	public String getEntityType() {
		return DictionaryConstant.ENTITY_TYPE;
	}
}
