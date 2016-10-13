/** 
 * @(#)SexType.java 1.0.0 2011-5-10 下午02:33:37  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package ${parentPackage}.${package}.${subpackage}.dictionary;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.cicl.frame.core.dictionary.AbstractMessageAwareDictionary;

/**
 * Class SexType
 *
 * @author ${author}
 * @version ${revision}, $Date: 2011-5-10 下午02:33:37  
 */
@Component
public class ListTempDictionary extends AbstractMessageAwareDictionary{
	public ListTempDictionary(){
		super();
	}
	public void init(){
		this.map = new HashMap<String, String>();
		this.map.put("1", "demoData.dict.sex.male");
		this.map.put("2", "demoData.dict.sex.female");
	}
}
