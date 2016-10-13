/** 
 * @(#)MedivaultForm.java 1.0.0 2010-12-30 05:47:37  
 *  
 * Copyright 2010 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.web.form;

import java.io.Serializable;

/**
 * Class MedivaultForm 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2010-12-30 05:47:37
 */
public interface Form extends Serializable{
	String getMethod();

	void setMethod(String method);

	void clean();
}
