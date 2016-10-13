/** 
 * @(#)${className}List.java 1.0.0 2011-5-10 02:33:37  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package ${parentPackage}.${package}.${subpackage}.dictionary;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.cicl.frame.core.dictionary.AbstractMessageAwareDictionaryList;

/**
 * Class ${className}List
 *
 * @author ${author}
 * @version ${revision}, $Date: 2011-5-10 02:33:37  
 */
@Component
public class ${className}List extends AbstractMessageAwareDictionaryList{
	public ${className}List(){
		super();
	}
	public void init(){
		this.map = new HashMap<String, String>();
		this.map.put("key1", "${objectName}.dict.key1");
		this.map.put("key2", "${objectName}.dict.key2");
	}
}
