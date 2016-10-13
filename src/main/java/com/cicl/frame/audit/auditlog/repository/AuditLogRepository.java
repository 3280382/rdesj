/** 
 * @(#)AuditLogRepository.java 1.0.0 2011-4-22 下午05:05:00  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.audit.auditlog.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cicl.frame.audit.auditlog.entity.AuditLog;
import com.cicl.frame.core.repository.hibernate.AbstractHibernateRepository;

/**
 * Class AuditLogRepositoryHibernate
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午05:05:00
 */
@Repository
public class AuditLogRepository extends AbstractHibernateRepository<AuditLog, Long> {
	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(AuditLogRepository.class);

	public AuditLogRepository() {
	}

	public AuditLogRepository(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
