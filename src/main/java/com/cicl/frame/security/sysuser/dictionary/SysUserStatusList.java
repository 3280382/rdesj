/** 
 * @(#)RoleStatusList.java 1.0.0 2011-5-10 下午02:33:37  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.security.sysuser.dictionary;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.cicl.frame.core.dictionary.AbstractMessageAwareDictionaryList;

/**
 * Class RoleStatusList
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-20 下午02:33:37  
 */
@Component
public class SysUserStatusList extends AbstractMessageAwareDictionaryList{
	public SysUserStatusList(){
		super();
	}
	public void init(){
		this.map = new HashMap<String, String>();
		this.map.put("1", "sysUser.dict.sysUserStatusList.enable");
		this.map.put("0", "sysUser.dict.sysUserStatusList.disable");
	}
}
