/** 
 * @(#)AuditLogSearchForm.java 1.0.0 2011-3-3 09:41:05  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.audit.auditlog.web.form;

import com.cicl.frame.audit.auditlog.entity.AuditLog;
import com.cicl.frame.core.web.form.AbstractSearchForm;

public class AuditLogSearchForm extends AbstractSearchForm {
	private static final long serialVersionUID = 1L;

	private AuditLog auditLog;

	public AuditLogSearchForm() {
		super();
	}

	public AuditLog getAuditLog() {
		return auditLog;
	}

	public void setAuditLog(AuditLog auditLog) {
		this.auditLog = auditLog;
	}
}
