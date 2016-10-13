/** 
 * @(#)RandomGenerator.java 1.0.0 2011-2-14 15:49:31  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */ 
package com.cicl.frame.core.util;

/**
 * Class RandomGenerator
 *  
 *
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-2-14 03:49:31  
 */
public class RandomGenerator {
	   public static String randomPassword() {
	        return randomString(6);
	    }
	    
	    public static String randomString(int length) {
	        return randomString(length, true);
	    }
	    
	    public static String randomString(int length, boolean includeNumbers) {
	        StringBuffer b = new StringBuffer(length);
	        for (int i = 0; i < length; i++) {
	            if (includeNumbers) {
	                b.append(randomCharacter());
	            } else {
	                b.append(randomAlpha());
	            }
	        }
	        return b.toString();
	    }
	    
	    public static String randomNumber(int length) {
	        StringBuffer b = new StringBuffer(length);
	        for (int i = 0; i < length; i++) {
	            b.append(randomDigit());
	        }
	        return b.toString();
	    }
	    
	    public static char randomCharacter() {
	        int i = (int) (Math.random() * 3D);
	        if (i < 2) {
	            return randomAlpha();
	        } else {
	            return randomDigit();
	        }
	    }
	    
	    public static char randomAlpha() {
	        int i = (int) (Math.random() * 52D);
	        if(i > 25) {
	            return (char) ((97 + i) - 26);
	        } else {
	            return (char) (65 + i);
	        }
	    }
	    
	    public static char randomDigit() {
	        int i = (int) (Math.random() * 10D);
	        return (char) (48 + i);
	    }
}
