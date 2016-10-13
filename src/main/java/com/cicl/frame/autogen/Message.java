/** 
 * @(#)Message.java 1.0.0 2011-7-26 上午08:54:25  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.autogen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import com.cicl.frame.core.util.StringUtils;


/**
 * Class Message
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-7-26 上午08:54:25
 */
public class Message {

	public static void info(String msg) {
		System.out.println("[INFO] " + msg);
	}

	public static void infoHeader(String msg) {
		System.out.println("+=====================================================================+");
		System.out.println("+  " + msg);
		System.out.println("+=====================================================================+");
	}

	public static void error(String msg) {
		System.out.println("[ERROR]" + msg);
	}

	public static void warn(String msg) {
		System.out.println("[WARN] " + msg);
	}

	public static void error(Throwable e) {
		System.out.println("[ERROR]" + e.getMessage());
		e.printStackTrace();
	}

	public static void error(String msg, Throwable e) {
		System.out.println("[ERROR]" + msg);
		e.printStackTrace();
	}

	public static void infoInput(String msg, String selectList) {
		System.out.print("[INPUT]" + msg + "(" + selectList + "):");
	}

	public static char readChar(String msg, String selectList) {
		infoInput(msg, selectList);
		
		HashSet<String> list = StringUtils.splitToSet(selectList, ",");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			char input = (char)in.read();
			
			//System.out.println(input);
			
			if( list.contains( String.valueOf(input) ) ){
				return input;
			}
			else{
				error("输入错误，请重新输入");
				readChar(msg, selectList);
			}
			
		} catch (IOException e) {
			error(e);
		}
		return ' ';
	}
}
