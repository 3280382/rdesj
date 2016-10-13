/** 
 * @(#)DictionaryService.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.system.dictionary.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cicl.frame.core.util.spring.SpringContextHolder;
import com.cicl.frame.system.dictionary.service.DictionaryService;

/**
 * Class DictionaryService 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 04:46:32  
 */
@Component
public class DictionaryTree {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DictionaryTree.class);
	@Autowired
	private DictionaryService dictionaryService;	
	
	private static final String ROOT_KEY = "ROOT";
	private DictionaryTreeNode root = new DictionaryTreeNode(ROOT_KEY);
	
	public DictionaryTree() {		
	}
	
	private static DictionaryTree tree;
	public static DictionaryTree getInstance(){
		if(tree==null){
			tree = SpringContextHolder.getBean(DictionaryTree.class);
			tree.reloadTree();
		}
		return tree;
	}
	
	public static Map<String, DictionaryTreeNode> get(String key){
		return getInstance().getChildren(key);
	}
	
	public void reloadTree(){
		List<Dictionary> list = dictionaryService.search(null);
		Map<Long, DictionaryTreeNode> tempTree = new HashMap<Long, DictionaryTreeNode>();
		for(Dictionary dic : list){
			tempTree.put(dic.getId(), new DictionaryTreeNode(dic.getKey(), dic));
		}
		
		DictionaryTreeNode node;
		Long parentId;
		for(Long id : tempTree.keySet()){
			node = tempTree.get(id);
			parentId = node.getItem().getParentId();
			if( tempTree.containsKey( parentId ) ){
				tempTree.get( parentId ).getChildren().put(node.getKey(), node);
			}
			else{
				root.getChildren().put(node.getKey(), node);
			}
		}		
	}	
	
	public DictionaryTreeNode getNode(String key){
		if(key==null)return null;
		
		String[] keyTree = key.split("/");
		
		DictionaryTreeNode curNode = root;
		for(String keyLevel : keyTree){
			if(keyLevel.trim().equals(""))continue;
			
			curNode = curNode.getChildren().get(keyLevel);
		}
		
		if(curNode==null)return null;
		
		return curNode;
	}
	
	public Map<String, DictionaryTreeNode> getChildren(String key){	
		if(key==null)return null;
		
		DictionaryTreeNode node = getNode(key);
		
		if(node==null)return null;
		
		return node.getChildren();
	}
}
