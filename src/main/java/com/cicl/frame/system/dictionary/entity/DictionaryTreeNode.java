/** 
 * @(#)DictionaryTreeNode.java 1.0.0 2011-6-4 下午03:57:13  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.system.dictionary.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Class DictionaryTreeNode
 * TODO Purpose/description of class 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-6-4 下午03:57:13  
 */
public class DictionaryTreeNode {
	private String key;
	private Dictionary item;
	private Map<String, DictionaryTreeNode> children = new HashMap<String, DictionaryTreeNode>();


	@Override
	public String toString(){
		if(item==null)return null;
		return item.getValue();
	}
	
	public DictionaryTreeNode() {
		super();
	}
	public DictionaryTreeNode(String key) {
		super();
		this.key = key;
	}
	public DictionaryTreeNode(String key, Dictionary item) {
		super();
		this.key = key;
		this.item = item;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Dictionary getItem() {
		return item;
	}
	public void setItem(Dictionary item) {
		this.item = item;
	}
	public Map<String, DictionaryTreeNode> getChildren() {
		return children;
	}
	public void setChildren(Map<String, DictionaryTreeNode> children) {
		this.children = children;
	}	
	
}
