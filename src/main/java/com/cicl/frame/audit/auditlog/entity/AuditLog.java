/** 
 * @(#)AuditLog.java 1.0.0 2011-4-22 下午04:46:32  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.audit.auditlog.entity;

import java.sql.Timestamp;

import com.cicl.frame.core.entity.AbstractEntity;

/**
 * Class AuditLog
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午04:46:32
 */
public class AuditLog extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;
	
	
	private String userId;
	
	private String username;
	
	private String loginName;
	
	private String userIp;
	
	private String targetId;
	
	private String targetEntityType;
	
	private String targetName;
	
	private String targetDesc;
	
	private String targetSnapshot;
	
	private String opType;
	
	private Timestamp opTime;
	
	private String opDesc;

	
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getLoginName() {
		return this.loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getUserIp() {
		return this.userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	public String getTargetId() {
		return this.targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	public String getTargetEntityType() {
		return this.targetEntityType;
	}
	public void setTargetEntityType(String targetEntityType) {
		this.targetEntityType = targetEntityType;
	}
	
	public String getTargetName() {
		return this.targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
	public String getTargetDesc() {
		return this.targetDesc;
	}
	public void setTargetDesc(String targetDesc) {
		this.targetDesc = targetDesc;
	}
	
	public String getTargetSnapshot() {
		return this.targetSnapshot;
	}
	public void setTargetSnapshot(String targetSnapshot) {
		this.targetSnapshot = targetSnapshot;
	}
	
	public String getOpType() {
		return this.opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	public Timestamp getOpTime() {
		return this.opTime;
	}
	public void setOpTime(Timestamp opTime) {
		this.opTime = opTime;
	}
	
	public String getOpDesc() {
		return this.opDesc;
	}
	public void setOpDesc(String opDesc) {
		this.opDesc = opDesc;
	}
	/* (non-Javadoc)
	 * @see com.cicl.frame.core.entity.AbstractEntity#getEntityType()
	 */
	@Override
	public String getEntityType() {
		return getClassName(true);
	}
}
