/** 
 * @(#)${className}SearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package ${parentPackage}.${package}.${subpackage}.web.form;

import java.util.Date;

import javax.validation.constraints.Past;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import ${parentPackage}.${package}.${subpackage}.entity.${className};

public class ${className}SearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = 1L;

	private ${className} ${objectName};

	public ${className}SearchForm() {
		super();
	}

	public ${className} get${className}() {
		return ${objectName};
	}

	public void set${className}(${className} ${objectName}) {
		this.${objectName} = ${objectName};
	}
}
