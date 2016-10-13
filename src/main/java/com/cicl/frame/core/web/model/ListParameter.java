/** 
 * @(#)ListParameter.java 1.0.0 2011-5-11 下午08:08:40  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.web.model;

import java.util.ArrayList;

/**
 * Class ListParameter
 * TODO Purpose/description of class 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-11 下午08:08:40  
 */
public class ListParameter<T>{
	private ArrayList<T> list;

	public ArrayList<T> getList() {
		return list;
	}

	public void setList(ArrayList<T> list) {
		this.list = list;
	}
}
