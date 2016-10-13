/** 
 * @(#)Persistable.java 1.0.0 2010-12-30 02:42:37  
 *  
 * Copyright 2010 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * Class Persistable
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-6-2 下午02:25:10  
 */
public interface Persistable<PK extends Serializable> extends Serializable{
	boolean isNew();

	PK getId();

	void setId(PK id);

	Date getCreatedDate();

	void setCreatedDate(Date date);

	Date getModifiedDate();

	void setModifiedDate(Date date);
	
	String getClassName(boolean includePackage);
	
	String getEntityType();
}
