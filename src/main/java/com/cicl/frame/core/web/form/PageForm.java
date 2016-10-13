/** 
 * @(#)PageForm.java 1.0.0 2011-5-8 下午12:36:09  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.web.form;

/**
 * Class PageForm
 *  
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-8 下午12:36:09  
 */
public interface PageForm {
	public int getPageNumber();

	public void setPageNumber(int pageNumber);

	public int getResultsPerPage();

	public void setResultsPerPage(int resultsPerPage);

	public String getOrder();

	public void setOrder(String order);

	public String getOrderBy();

	public void setOrderBy(String orderBy);
}
