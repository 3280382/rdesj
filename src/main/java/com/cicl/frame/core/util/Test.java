/** 
 * @(#)Test.java 1.0.0 2011-6-14 上午09:23:51  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.util;

import com.cicl.frame.core.entity.Persistable;
import com.cicl.frame.security.sysuser.entity.SysUser;

/**
 * Class Test
 * TODO Purpose/description of class 
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-6-14 上午09:23:51  
 */
public class Test {

	/**
	 * TODO Purpose/description of method 
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Long l = new Long(1);
		System.out.println(l.toString());
		
		SysUser u = new SysUser();
		u.setId(l);
		
		System.out.println(u.getId().toString());
		printPersistable(u);

	}
	
	public static void printPersistable(Persistable target){
		System.out.println(target.getId().toString());
	}

}
