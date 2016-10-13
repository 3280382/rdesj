/** 
 * @(#)HibernateTypeConvertor.java 1.0.0 2011-2-14 03:39:13  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.repository.hibernate;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.type.Type;

/**
 * Class HibernateTypeConvertor 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-2-14 15:39:13
 */
public class HibernateTypeConvertor {

	/*
	 * can't be instanced publicly
	 */
	private HibernateTypeConvertor() {
	}

	/**
	 * get corresponding Hibernate type according to the SQL type
	 * 
	 * @param sqlType int
	 * @return Type
	 * @throws HibernateException
	 */
	public static Type getTypeBySqlType(int sqlType) throws HibernateException {
		Type type = null;

		switch (sqlType) {
		case Types.BIGINT:
			type = Hibernate.LONG;
			break;
		case Types.BOOLEAN:
			type = Hibernate.BOOLEAN;
			break;
		case Types.CHAR:
			type = Hibernate.CHARACTER;
			break;
		case Types.DATE:
			type = Hibernate.DATE;
			break;
		case Types.DOUBLE:
			type = Hibernate.DOUBLE;
			break;
		case Types.INTEGER:
			type = Hibernate.INTEGER;
			break;
		case Types.JAVA_OBJECT:
			type = Hibernate.OBJECT;
			break;
		case Types.LONGVARCHAR:
			type = Hibernate.TEXT;
			break;
		case Types.TIME:
			type = Hibernate.TIME;
			break;
		case Types.TIMESTAMP:
			type = Hibernate.TIMESTAMP;
			break;
		case Types.VARCHAR:
			type = Hibernate.STRING;
			break;
		default:
			throw new HibernateException("Unsupported hibernate sql type: "
					+ sqlType);
		}
		return type;
	}
}
