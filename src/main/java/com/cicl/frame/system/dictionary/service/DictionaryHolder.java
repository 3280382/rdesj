/** 
 * @(#)DictionaryService.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.system.dictionary.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cicl.frame.core.util.spring.SpringContextHolder;
import com.cicl.frame.system.dictionary.entity.Dictionary;

/**
 * Class DictionaryService 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 04:46:32  
 */
@Component
public class DictionaryHolder {
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(DictionaryHolder.class);
	@Autowired
	private DictionaryService dictionaryService;	
	
	private static DictionaryHolder holder;
	public static DictionaryHolder getInstance(){
		if(holder==null){
			holder = SpringContextHolder.getBean(DictionaryHolder.class);
		}
		return holder;
	}
	
	public static List<Dictionary> get(String key){
		return getInstance().dictionaryService.searchByParentKey(key);
	}
	
}
