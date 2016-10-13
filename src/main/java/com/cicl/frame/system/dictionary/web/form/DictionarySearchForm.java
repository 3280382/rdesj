/** 
 * @(#)DictionarySearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.system.dictionary.web.form;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import com.cicl.frame.system.dictionary.entity.Dictionary;

public class DictionarySearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = 1L;

	private Dictionary dictionary;

	public DictionarySearchForm() {
		super();
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
}
