/** 
 * @(#)AbstractMynderForm.java 1.0.0 2011-1-19 03:52:01  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.web.form;

/**
 * Class AbstractMynderForm
 *  
 *
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-1-19 03:52:01  
 */
public abstract class AbstractSearchForm extends AbstractForm implements PageForm{	
	private static final long serialVersionUID = 6971382203422172627L;
	
	public static Integer DEFAULT_RESULTS_PER_PAGE = Integer.valueOf(10);
	public static Integer DEFAULT_PAGE_NUMBER = Integer.valueOf(1);
	private Integer pageNumber;
	private Integer resultsPerPage;
	public String order;
	public String orderBy;
	
	public AbstractSearchForm(){
		super();
		this.setPageNumber(DEFAULT_PAGE_NUMBER);
		this.setResultsPerPage(DEFAULT_RESULTS_PER_PAGE);
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber
	 *            the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the resultsPerPage
	 */
	public int getResultsPerPage() {
		return resultsPerPage;
	}

	/**
	 * @param resultsPerPage
	 *            the resultsPerPage to set
	 */
	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
