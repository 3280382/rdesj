/** 
 * @(#)AuthorityRepository.java 1.0.0 2011-4-22 05:05:00  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.security.authority.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cicl.frame.core.repository.hibernate.AbstractHibernateRepository;
import com.cicl.frame.security.authority.entity.Authority;

/**
 * Class AuthorityRepositoryHibernate
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 05:05:00
 */
@Repository
public class AuthorityRepository extends AbstractHibernateRepository<Authority, Long> {
	@SuppressWarnings("unused")
	private static final Log LOG = LogFactory.getLog(AuthorityRepository.class);

	public AuthorityRepository() {
	}

	public AuthorityRepository(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}