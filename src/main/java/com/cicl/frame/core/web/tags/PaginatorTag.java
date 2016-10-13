/** 
 * @(#)PaginatorTag.java 1.0.0 2010-12-30 05:22:54  
 *  
 * Copyright 2010 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

/**
 * Class PaginatorTag Generates a set of links in the page context for paging
 * search results. Must be deployed with correct mynder-tags.tld definition and
 * the .tld uri needs to be decleared in the page as well
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2010-12-30 05:22:54
 */
public class PaginatorTag extends RequestContextAwareTag {

	private static final Log LOG = LogFactory.getLog(PaginatorTag.class);

	private static final int DEFAULT_VISIBLE_LINKS = 5;

	private static final long serialVersionUID = 1L;

	private Integer totalResults;
	private Integer resultsPerPage;
	private Integer pageNumber;

	/**
	 * How many links to generate either side of the current page.
	 */
	private int visibleLinks = DEFAULT_VISIBLE_LINKS;

	/**
	 * Class to assign to the paginator div. If null not assigned.
	 */
	@SuppressWarnings("unused")
	private String divClass;

	/**
	 * Class to assign to the paginator links. If null not assigned.
	 */
	@SuppressWarnings("unused")
	private String linkClass;

	/**
	 * Id to assign to the paginator div. If null not assigned.
	 */
	private String divId;

	private RequestContext requestContextLocal;

	//@Override
	protected int doStartTagInternal() throws Exception {

		requestContextLocal = this.getRequestContext();
		final StringBuilder out = new StringBuilder();

		dumpParametersToLog();

		appendMainDivStartTag(out);

		// Only add links if there are more results that a single page can
		// handle.
		//if (totalResults > resultsPerPage) {

			// Add Page: text.
			// out.append(requestContextLocal.getMessage("paginator.page.txt"));

			// Add Prev: text.
			appendArrowLink(out, pageNumber, "prev");

			/*
			 * // Add the first link if the first page link is not going to be
			 * // visible. if (pageNumber - visibleLinks > 1) { appendLink(out,
			 * 1, requestContextLocal .getMessage("paginator.first.txt"));
			 * out.append(requestContextLocal
			 * .getMessage("paginator.dotdotdot.txt")); }
			 */

			// Calculate the number of links to generate and add them.
			for (int pageNumber : getDirectPageNumbers()) {
				appendLink(out, pageNumber, String.valueOf(pageNumber));
			}

			// If we are not displaying the last page then add a ....
			if (pageNumber + visibleLinks < getLastPageNbr()) {
				out.append(" ").append(
						requestContextLocal
								.getMessage("paginator.dotdotdot.txt"));
			}

			// Add Next: text.
			appendArrowLink(out, pageNumber, "next");


		// close main div.
		out.append("</ul>");

		try {
			this.pageContext.getOut().print(out.toString());
		} catch (IOException e) {
			throw new JspException(e.getMessage(), e);
		}

		return Tag.SKIP_BODY;
	}

	private void dumpParametersToLog() {
		if (LOG.isDebugEnabled()) {

			LOG.debug("Records found   : " + totalResults);
			LOG.debug("Display page    : " + pageNumber);
			LOG.debug("Results per page: " + resultsPerPage);
		}
	}

	/**
	 * Adds a link to the output.
	 * 
	 * @param baseUrl
	 */
	private void appendLink(final StringBuilder out, final int currentPage,
			final String linkText) {
		
		String[] messageArgs = null;
		String pageLinkHtml = null;

		if (currentPage == pageNumber) {
			LOG.debug("Adding placeholder for page " + currentPage);
			messageArgs = new String[] { "current",
					String.valueOf(currentPage), linkText };
		} else {
			LOG.debug("Adding link to page " + currentPage + ", link text "
					+ linkText);

			// paginator.pageNumber.htmlLink=<a class="{0}"
			// href="javascript:setPaginationAndSubmit({1});" >{2}</a>
			messageArgs = new String[] { "",
					String.valueOf(currentPage), linkText };
			
		}
		
		pageLinkHtml = requestContextLocal.getMessage(
				"paginator.pageNumber.a.link", messageArgs);
		
		out.append(" ").append(pageLinkHtml);
	}

	/**
	 * Adds a link to the output.
	 * 
	 * @param baseUrl
	 */
	private void appendArrowLink(final StringBuilder out,
			final int currentPage, final String arrowStyle) {

		LOG.debug("Adding arrow for page " + arrowStyle);

		String arrowClass = arrowStyle;
		String pageLinkHtml = null;
		int pageParam = 0;
		int lastPage = getLastPageNbr();

		if (("prev".equals(arrowStyle) && currentPage == 1)
				|| ("next".equals(arrowStyle) && currentPage == lastPage)) {

			arrowClass = arrowClass + " disabled";
			pageLinkHtml = requestContextLocal.getMessage(
					"paginator.arrow.txt", new String[] { arrowClass });

		} else {
			if ("prev".equals(arrowStyle)) {
				pageParam = currentPage - 1;
			} else if ("next".equals(arrowStyle)) {
				pageParam = currentPage + 1;
			}
			pageLinkHtml = requestContextLocal.getMessage(
					"paginator.arrow.clickable.txt", new String[] {
							String.valueOf(pageParam), arrowClass });

		}

		out.append(" ").append(pageLinkHtml);
	}

	/**
	 * Adds the main paginator div.
	 */
	private void appendMainDivStartTag(final StringBuilder out) {

		// paginator.divTag.start=<div class="{0}" id="{1}" >{2} Jobs,
		String[] messageArgs = new String[] { divId,
				String.valueOf(totalResults) };

		out.append(requestContextLocal.getMessage("paginator.divTag.start",
				messageArgs));
	}

	/**
	 * Returns a list of page numbers to be added.
	 * 
	 * @return
	 */
	public int[] getDirectPageNumbers() {
		int startPage = pageNumber - visibleLinks;
		if (startPage < 1) {
			startPage = 1;
		}
		int endPage = pageNumber + visibleLinks;
		final int endPageNbr = getLastPageNbr();
		if (endPage > endPageNbr) {
			endPage = endPageNbr;
		}
		final int[] pages = new int[endPage - startPage + 1];
		LOG.debug("Setting up pages " + startPage + " to " + endPage);
		for (int idx = 0, page = startPage; page <= endPage; idx++, page++) {
			pages[idx] = page;
		}
		return pages;
	}

	/**
	 * Calculates the last possible page number based on the number of records
	 * returned from the search and the number of records to be on each page.
	 * 
	 * @return
	 */
	public int getLastPageNbr() {
		int pageNbr = totalResults / resultsPerPage;
		if (totalResults % resultsPerPage > 0) {
			pageNbr++;
		}
		LOG.debug("Last page = " + pageNbr);
		return pageNbr;
	}

	public void setVisibleLinks(final int visibleLinks) {
		this.visibleLinks = visibleLinks;
	}

	public void setDivClass(final String divClass) {
		this.divClass = divClass;
	}

	public void setLinkClass(final String linkClass) {
		this.linkClass = linkClass;
	}

	public void setDivId(final String divId) {
		this.divId = divId;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public void setResultsPerPage(Integer resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

}
