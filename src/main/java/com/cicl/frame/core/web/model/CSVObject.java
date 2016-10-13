/** 
 * @(#)CSVObject.java 1.0.0 2011-2-23 23:40:55  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cicl.frame.core.entity.Persistable;

/**
 * Class CSVObject 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-2-23 23:40:55
 */
public class CSVObject<T extends Persistable> {

	private static final Log LOG = LogFactory.getLog(CSVObject.class);

	private String csvFileName;

	private String[] hearderNames;

	private String[] propertyNames;

	private List<T> data;

	public CSVObject(String[] propertyNames, List<T> data) {
		super();
		this.propertyNames = propertyNames;
		this.data = data;
		if (data != null && data.size() > 0)
			csvFileName = data.get(0).getClassName(false) + ".csv";
	}

	public CSVObject(String csvFileName, String[] hearderNames,
			String[] propertyNames, List<T> data) {
		super();
		this.csvFileName = csvFileName;
		this.hearderNames = hearderNames;
		this.propertyNames = propertyNames;
		this.data = data;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	//@Override
	public String toString() {
		StringBuffer csvContent = new StringBuffer();
		// s.append("medicationName,strengthValue,strengthUnit,route\n");

		if (propertyNames == null || propertyNames.length == 0 || data == null
				|| data.size() == 0)
			return null;

		if (hearderNames != null) {
			csvContent.append(formCsvLine(hearderNames));
		} else {
			csvContent.append(formCsvLine(propertyNames));
		}

		List<String> lineContent = null;
		new ArrayList<String>();

		try {
			for (T o : data) {
				lineContent = new ArrayList<String>();
				for (String name : propertyNames) {
					String value = (String) PropertyUtils.getSimpleProperty(o,
							name);
					lineContent.add(value);
				}
				csvContent.append(formCsvLine(lineContent
						.toArray(new String[lineContent.size()])));
			}
		} catch (Exception e) {
			LOG
					.error(
							"There is an error occurred when building CSV content from object",
							e);
			return null;
		}

		return csvContent.toString();
	}

	/**
	 * TODO Purpose/description of method
	 * 
	 * @param elements
	 * @return
	 */
	private String formCsvLine(String[] elements) {
		StringBuffer line = new StringBuffer();
		for (String el : elements) {
			line.append(String.format("\"%s\"", el));
			line.append(",");
		}
		line.deleteCharAt(line.lastIndexOf(","));
		line.append("\r\n");
		return line.toString();
	}

	/**
	 * @return the csvFileName
	 */
	public final String getCsvFileName() {
		return csvFileName;
	}

	/**
	 * @param csvFileName
	 *            the csvFileName to set
	 */
	public final void setCsvFileName(String csvFileName) {
		this.csvFileName = csvFileName;
	}

	/**
	 * @return the hearderNames
	 */
	public final String[] getHearderNames() {
		return hearderNames;
	}

	/**
	 * @param hearderNames
	 *            the hearderNames to set
	 */
	public final void setHearderNames(String[] hearderNames) {
		this.hearderNames = hearderNames;
	}

	/**
	 * @return the propertyNames
	 */
	public final String[] getPropertyNames() {
		return propertyNames;
	}

	/**
	 * @param propertyNames
	 *            the propertyNames to set
	 */
	public final void setPropertyNames(String[] propertyNames) {
		this.propertyNames = propertyNames;
	}

	/**
	 * @return the data
	 */
	public final List<T> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public final void setData(List<T> data) {
		this.data = data;
	}

}
