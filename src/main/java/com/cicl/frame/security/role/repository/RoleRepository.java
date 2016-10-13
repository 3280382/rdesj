/** 
 * @(#)RoleRepository.java 1.0.0 2011-4-22 下午05:05:00  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.role.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cicl.frame.core.repository.hibernate.AbstractHibernateRepository;
import com.cicl.frame.security.role.entity.Role;

/**
 * Class RoleRepositoryHibernate
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 下午05:05:00
 */
@Repository
public class RoleRepository extends AbstractHibernateRepository<Role, Long> {
	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(RoleRepository.class);

	public RoleRepository() {
	}

	public RoleRepository(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
