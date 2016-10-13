/** 
 * @(#)Dictionary.java 1.0.0 2011-5-10 上午10:20:12  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.dictionary;

import java.io.Serializable;
import java.util.Map;

/**
 * Class Dictionary
 * TODO Purpose/description of class 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-10 上午10:20:12  
 */
public interface Dictionary<ID extends Serializable, TEXT extends Serializable> {
	public void init();
	
	public void put(ID id, TEXT text);
	
	public TEXT getText(ID id);
	
	public Map<ID,TEXT> getMap();
}
