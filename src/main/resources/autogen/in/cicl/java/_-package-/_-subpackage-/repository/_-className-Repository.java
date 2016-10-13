/** 
 * @(#)${className}Repository.java 1.0.0 2011-4-22 下午05:05:00  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package ${parentPackage}.${package}.${subpackage}.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.cicl.frame.core.repository.hibernate.AbstractHibernateRepository;
import ${parentPackage}.${package}.${subpackage}.entity.${className};

/**
 * Class ${className}RepositoryHibernate
 * 
 * @author ${author}
 * @version ${revision}, $Date: 2011-4-22 下午05:05:00
 */
@Repository
public class ${className}Repository extends AbstractHibernateRepository<${className}, ${pk.type}> {
	private static final Log LOG = LogFactory.getLog(${className}Repository.class);

	public ${className}Repository() {
	}

	public ${className}Repository(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
