/** 
 * @(#)PostinghistoryId.java 1.0.0 2011-4-22 04:46:32  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Class AbstractId
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-6-17 下午02:36:40  
 * @param <PK>
 */
public class AbstractId implements Serializable {
	private static final long serialVersionUID = 2245563696919351568L;
	
	//@Override
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
