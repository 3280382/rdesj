/** 
 * @(#)PageForm.java 1.0.0 2011-5-8 下午12:36:09  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.repository.page;

import java.util.Collection;

import com.cicl.frame.core.entity.Persistable;

/**
 * Class PageForm
 *  
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-8 下午12:36:09  
 */

public interface PageResult<T extends Persistable> {
	public int getPage();

	public int getTotalPageCount();
	
	public int getTotalCount();

	public void setTotalCount(int totalCount);
	
	public int getPageSize();

	public void setPageSize(int pageSize);

	public void setResults(Collection<T> results);
	
	public Collection<T> getResults();
}
