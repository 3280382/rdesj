/** 
 * @(#)AbstractMessageAwareDictionary.java 1.0.0 2011-5-10 上午09:44:23  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.dictionary;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Class AbstractDictionary 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-10 上午09:44:23  
 */
public abstract class AbstractMessageAwareDictionaryList extends AbstractDictionaryList<String, String> implements MessageSourceAware{
	@Autowired
	private MessageSource messageSource;
	
	private Map<String, Map<String,String>> localMap = new HashMap<String, Map<String,String>>();
	
	public MessageSource getMessageSource(){
		return messageSource;
	}
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public AbstractMessageAwareDictionaryList(){
		init();
	}
	
	//@Override
	abstract public void init();
	
	//@Override
	public String getText(String id){
		return messageSource.getMessage(map.get(id), null, LocaleContextHolder.getLocale());
	}
	
	//@Override
	public  Map<String,String> getMap(){
		String localKey = LocaleContextHolder.getLocale().getDisplayName();
		if(this.localMap.containsKey(localKey)){
			return this.localMap.get(localKey);
		}
		else{
			Map<String,String> newLocalMap = new HashMap<String, String>();
			for(String key : this.map.keySet()){
				newLocalMap.put(key, getText(key));
			}
			
			this.localMap.put(localKey, newLocalMap);
			return newLocalMap;
		}
	}
	
}
