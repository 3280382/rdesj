/** 
 * @(#)AbstractDictionary.java 1.0.0 2011-5-10 上午09:44:23  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.dictionary;

import java.io.Serializable;
import java.util.Map;

/**
 * Class AbstractDictionary
 * TODO Purpose/description of class 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-10 上午09:44:23  
 */
public abstract class AbstractDictionary<ID extends Serializable, TEXT extends Serializable> implements Dictionary<ID, TEXT>{
	protected Map<ID,TEXT> map = null;
	
	public AbstractDictionary(){
		init();
	}
	
	abstract public void init();
	
	public void put(ID id, TEXT text){
		map.put(id, text);
	}
	
	public TEXT getText(ID id){
		return map.get(id);
	}
	
	public void setMap(Map<ID,TEXT> map){
		this.map = map;
	}
	
	public  Map<ID,TEXT> getMap(){
		return this.map;
	}
}
