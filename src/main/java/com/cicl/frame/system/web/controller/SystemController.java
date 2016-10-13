/** 
 * @(#)SystemController.java 1.0.0 2011-5-3 上午10:35:33  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.system.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class SystemController
 *  
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-3 上午10:35:33  
 */
@Controller
public class SystemController {
	@RequestMapping
	public String main(){
		return null;
	}
}
