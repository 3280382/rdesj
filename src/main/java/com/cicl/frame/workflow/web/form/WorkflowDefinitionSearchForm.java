/** 
 * @(#)DemoDataForm.java 1.0.0 2011-3-3 09:39:17  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.workflow.web.form;

import javax.validation.Valid;

import com.cicl.frame.core.web.form.AbstractSearchForm;
import com.cicl.frame.workflow.entity.WorkflowDefinition;


/**
 * Class DemoDataForm
 *  
 *
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-5-8 下午03:01:25  
 */
public class WorkflowDefinitionSearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = 5533747381412446275L;
	@Valid 
	private WorkflowDefinition workflowDefinition;

	public WorkflowDefinitionSearchForm() {
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}

	public void setWorkflowDefinition(WorkflowDefinition workflowDefinition) {
		this.workflowDefinition = workflowDefinition;
	}
}
