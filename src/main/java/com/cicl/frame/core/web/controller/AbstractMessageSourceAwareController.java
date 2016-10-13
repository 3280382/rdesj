/** 
 * @(#)AbstractMessageSourceAwareController.java 1.0.0 2011-5-10 上午10:35:36  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

/**
 * Class AbstractMessageSourceAwareController
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-10 上午10:35:36  
 */
public class AbstractMessageSourceAwareController implements MessageSourceAware{
	@Autowired
	private MessageSource messageSource;
	
	public MessageSource getMessageSource(){
		return messageSource;
	}
	
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
