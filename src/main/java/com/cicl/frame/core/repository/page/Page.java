/** 
 * @(#)Page.java 1.0.0 2011-1-13 03:41:04  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.repository.page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cicl.frame.core.entity.Persistable;

/**
 * Class Page 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-1-13 03:41:04
 */
public class Page<T extends Persistable> implements PageResult<T>{

	protected final static int DEFAULT_SHOW_PAGE_NUMBER_COUNT = 9;

	private int startIndex;
	private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;
	private int totalCount;

	private Collection<T> results;
	private int[] showPageNumbers;
	private InnerPage[] pages;

	@SuppressWarnings("unchecked")
	public Page() {
		this.startIndex = -1;
		this.pageSize = PageConstants.DEFAULT_PAGE_SIZE;
		this.totalCount = 0;
		this.results = new ArrayList();
	}

	public Page(int startIndex, int pageSize, int totalCount,
			Collection<T> results) {
		this.startIndex = startIndex;
		this.pageSize = pageSize <= 0 ? PageConstants.DEFAULT_PAGE_SIZE
				: pageSize;
		this.totalCount = totalCount;
		this.results = results;
	}

	public int[] getShowPageNumbers() {
		return getShowPageNumbers(DEFAULT_SHOW_PAGE_NUMBER_COUNT);
	}

	public int[] getShowPageNumbers(int showCount) {
		int currentPage = startIndex / pageSize;
		if (showPageNumbers == null) {
			int totalPageCount = getTotalPageCount();
			;
			showPageNumbers = new int[totalPageCount > showCount ? showCount
					: totalPageCount];
			if (totalPageCount > showCount) {
				int firstShowPage = currentPage - showCount / 2;
				int endShowPage = currentPage + showCount / 2;
				// System.out.println(showCount%2);
				/*
				 * if((showCount%2) == 0){ firstShowPage = currentPage -
				 * showCount / 2; endShowPage = currentPage + showCount / 2;
				 * }else{ firstShowPage = currentPage - showCount / 2;
				 * endShowPage = currentPage + showCount / 2; }
				 */
				if (firstShowPage > 0 && endShowPage < totalPageCount) {
					for (int i = 0, max = showPageNumbers.length; i < max; i++) {
						showPageNumbers[i] = firstShowPage + i;
					}
				} else if (firstShowPage > 0) {
					for (int i = 0, max = showPageNumbers.length; i < max; i++) {
						showPageNumbers[i] = totalPageCount - showCount + i;
					}
				} else {
					for (int i = 0, max = showPageNumbers.length; i < max; i++) {
						showPageNumbers[i] = i;
					}
				}
			} else {
				for (int i = 0, max = showPageNumbers.length; i < max; i++) {
					showPageNumbers[i] = i;
				}
			}
		}
		return showPageNumbers;
	}

	public int getTotalPageCount() {
		return (totalCount % pageSize == 0) ? totalCount / pageSize
				: totalCount / pageSize + 1;
	}

	public int getNextIndex() {
		return startIndex + pageSize;
	}

	public int getPreviousIndex() {
		int previousIndex = startIndex - pageSize;
		return previousIndex >= 0 ? previousIndex : 0;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Collection<T> getResults() {
		return results;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getList() {
		return results == null ? null
				: results instanceof List ? (List<T>) results
						: new ArrayList<T>(results);
	}
	
	@SuppressWarnings("unchecked")
	public Set<T> getSet() {
		return results == null ? null
				: results instanceof Set ? (Set<T>) results : new HashSet<T>(
						results);
	}

	@SuppressWarnings("unchecked")
	public List<T> getSortList() {
		List list = getList();
		if (list != null) {
			Collections.sort(list);
		}
		return list;
	}

	public void setResults(Collection<T> results) {
		this.results = results;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return totalCount > 0 ? (totalCount - 1) / pageSize + 1 : 0;
	}

	public int getPage() {
		return startIndex >= 0 ? startIndex / pageSize + 1 : 0;
	}

	/* another page style */

	/**
	 * A class which holds the page data if using the pagination feature.
	 * 
	 * @author Ray Li
	 * 
	 */
	public static class InnerPage {
		private int from;

		private int to;

		private int size;

		private boolean selected;

		/**
		 * Returns the hit number the page starts from.
		 */
		public int getFrom() {
			return from;
		}

		/**
		 * Sets the hit number the page starts from.
		 */
		public void setFrom(int from) {
			this.from = from;
		}

		/**
		 * Returns <code>true</code> if the page is selected, i.e. the results
		 * that are shown are part of the page.
		 */
		public boolean isSelected() {
			return selected;
		}

		/**
		 * Sets if the page is selected or not.
		 */
		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		/**
		 * Returns the size of the hits in the page.
		 */
		public int getSize() {
			return size;
		}

		/**
		 * Sets the size of the hits in the page.
		 */
		public void setSize(int size) {
			this.size = size;
		}

		/**
		 * Returns the hit number that the page ends at.
		 */
		public int getTo() {
			return to;
		}

		/**
		 * Sets the hit number that the page ends at.
		 */
		public void setTo(int to) {
			this.to = to;
		}
	}

	public InnerPage[] getPages() {
		return pages == null ? pages = generatePages() : pages;
	}

	private InnerPage[] generatePages() {
		int page = getPage() - 1;
		int from = page * pageSize;
		if (from > totalCount) {
			from = totalCount - pageSize;
			if (from < 0) {
				from = 0;
			}
		}
		int numberOfPages = (int) Math.ceil((float) totalCount / pageSize);
		InnerPage[] pages = new InnerPage[numberOfPages];
		for (int i = 0; i < pages.length; i++) {
			pages[i] = new InnerPage();
			pages[i].setFrom(i * pageSize + 1);
			pages[i].setSize(pageSize);
			pages[i].setTo((i + 1) * pageSize);
			if (from >= (pages[i].getFrom() - 1) && from < pages[i].getTo()) {
				pages[i].setSelected(true);
			} else {
				pages[i].setSelected(false);
			}
		}
		if (numberOfPages > 0) {
			InnerPage lastPage = pages[numberOfPages - 1];
			if (lastPage.getTo() > totalCount) {
				lastPage.setSize(totalCount - lastPage.getFrom());
				lastPage.setTo(totalCount);
			}
		}
		return pages;
	}

}
