/** 
 * @(#)AbstractRepositoryTest.java 1.0.0 2010-12-30 04:34:16  
 *  
 * Copyright 2010 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.repository;

import java.io.InputStream;

import junit.framework.TestCase;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.HSQLDialect;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Class AbstractRepositoryTest 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2010-12-30 04:34:16
 */
public abstract class AbstractRepositoryTest extends TestCase {

	protected final Session getSession() {
		return sessionFactory().getCurrentSession();
	}

	protected final SessionFactory sessionFactory() {

		if (sessionFactory == null) {
			buildSessionFactory();
		}

		return sessionFactory;
	}

	protected final void bindTxResource() {
		TransactionSynchronizationManager.bindResource(sessionFactory(), new SessionHolder(getSession()));
		getSession().beginTransaction();
	}

	protected final void flushAndClearSessionCache() {
		final Session session = getSession();
		session.flush();
		session.clear();
	}

	protected String[] resources() {
		return new String[] { "com/ctit/cff/model/DemoData.hbm.xml"};
	}

	protected abstract String testData();

	protected final void buildSessionFactory() {

		final Configuration configuration = new Configuration();

		configuration.setProperty(Environment.DRIVER, JDBC_DRIVER);
		configuration.setProperty(Environment.URL, jdbcUrl());
		configuration.setProperty(Environment.USER, JDBC_USER);
		configuration.setProperty(Environment.DIALECT, HSQLDialect.class.getName());
		configuration.setProperty(Environment.HBM2DDL_AUTO, "create-drop");
		configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
		configuration.setProperty(Environment.MAX_FETCH_DEPTH, "1");

		configuration.setProperty(Environment.SHOW_SQL, "true");
		configuration.setProperty(Environment.FORMAT_SQL, "true");

		for (String resource : resources()) {
			InputStream resourceAsStream = getResourceAsStream(resource);
			configuration.addInputStream(resourceAsStream);
		}

		sessionFactory = configuration.buildSessionFactory();
	}

	protected String jdbcUrl() {
		return "jdbc:hsqldb:mem:" + getClass().getSimpleName();
	}

	//@Override
	@SuppressWarnings("deprecation")
	protected void setUp() {
		try {
			super.setUp();

			buildSessionFactory();

			databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, jdbcUrl(), JDBC_USER, JDBC_PASSWORD);

			if (testData() != null) {
				final InputStream inputStream = getResourceAsStream(testData());

				final ReplacementDataSet dataSet = new ReplacementDataSet(new FlatXmlDataSet(inputStream));
				dataSet.addReplacementObject("[NULL]", null);

				databaseTester.setDataSet(dataSet);
				databaseTester.onSetup();
			}

			bindTxResource();

		} catch (Exception e) {
			throw new RepositoryTestRuntimeException(e);
		}

	}

	//@Override
	protected void tearDown() {
		try {
			super.tearDown();

			SessionFactoryUtils.closeSession(getSession());
			sessionFactory.close();

			databaseTester.onTearDown();

		} catch (Exception e) {
			throw new RepositoryTestRuntimeException(e);
		}
	}

	private InputStream getResourceAsStream(String resource) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
	}

	private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";

	private static final String JDBC_USER = "sa";

	private static final String JDBC_PASSWORD = "";

	private IDatabaseTester databaseTester;

	private SessionFactory sessionFactory;

	class RepositoryTestRuntimeException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public RepositoryTestRuntimeException(final Throwable cause) {
			super(cause);
		}
	}

}
