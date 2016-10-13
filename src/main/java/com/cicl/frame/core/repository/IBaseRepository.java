/** 
 * @(#)IBaseRepository.java 1.0.0 2011-1-12 11:00:52  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.cicl.frame.core.entity.Persistable;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.repository.query.QueryObject;

/**
 * Class IBaseRepository 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-1-12 11:00:52
 */
public interface IBaseRepository<T extends Persistable, PK extends Serializable>  extends IBaseCrudRepository<T, PK>{

	/**
	 * Return the persistent instance of the given entity class with the given
	 * identifier.
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	T get(Class<T> entityClass, PK id);

	/**
	 * Return the persistent instance of the given entity class with the given
	 * identifier, obtaining the specified lock mode, assuming the instance
	 * exists.
	 * 
	 * @param a
	 *            valid identifier of an existing persistent instance of the
	 *            class
	 * @return the persistent instance or proxy
	 */

	T load(Class<T> entityClass, PK id);
	T load(PK id);

	/**
	 * Persist the given transient instance.
	 * 
	 * @param a
	 *            transient instance of a persistent class
	 * @return the generated identifier
	 */
	PK save(T transientInstance);

	/**
	 * Update the persistent instance with the identifier of the given detached
	 * instance.
	 * 
	 * @param a
	 *            detached instance containing updated state
	 * @return identifier
	 */
	void update(T detachedInstance);

	/**
	 * Either <tt>save()</tt> or <tt>update()</tt> the given instance, depending
	 * upon the value of its identifier property. By default the instance is
	 * always saved. This behavior may be adjusted by specifying an
	 * <tt>unsaved-value</tt> attribute of the identifier property mapping. This
	 * operation cascades to associated instances if the association is mapped
	 * with <tt>cascade="save-update"</tt>.
	 * 
	 * @param a
	 *            transient or detached instance containing new or updated state
	 */
	void saveOrUpdate(T instance);

	/**
	 * Re-read the state of the given instance from the underlying database. It
	 * is inadvisable to use this to implement long-running sessions that span
	 * many business tasks. This method is, however, useful in certain special
	 * circumstances. For example
	 * <ul>
	 * <li>where a database trigger alters the object state upon insert or
	 * update
	 * <li>after executing direct SQL (eg. a mass update) in the same session
	 * <li>after inserting a <tt>Blob</tt> or <tt>Clob</tt>
	 * </ul>
	 * 
	 * @param a
	 *            persistent or detached instance
	 */
	void refresh(T instance);

	/**
	 * Make a transient instance persistent. This operation cascades to
	 * associated instances if the association is mapped with
	 * <tt>cascade="persist"</tt>.<br>
	 * <br>
	 * The semantics of this method are defined by JSR-220.
	 * 
	 * @param a
	 *            transient instance to be made persistent
	 */
	void persist(T transientInstance);

	/**
	 * Copy the state of the given object onto the persistent object with the
	 * same identifier. If there is no persistent instance currently associated
	 * with the session, it will be loaded. Return the persistent instance. If
	 * the given instance is unsaved, save a copy of and return it as a newly
	 * persistent instance. The given instance does not become associated with
	 * the session. This operation cascades to associated instances if the
	 * association is mapped with <tt>cascade="merge"</tt>.<br>
	 * <br>
	 * The semantics of this method are defined by JSR-220.
	 * 
	 * @param a
	 *            detached instance with state to be copied
	 * @return an updated persistent instance
	 */
	void merge(T detachedInstance);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving <tt>Session</tt> or a transient
	 * instance with an identifier associated with existing persistent state.
	 * This operation cascades to associated instances if the association is
	 * mapped with <tt>cascade="delete"</tt>.
	 * 
	 * @param the
	 *            instance to be removed
	 */
	void remove(T persistentInstance);
	void remove(PK id);

	/**
	 * Remove this instance from the session cache. Changes to the instance will
	 * not be synchronized with the database. This operation cascades to
	 * associated instances if the association is mapped with
	 * <tt>cascade="evict"</tt>.
	 * 
	 * @param a
	 *            persistent instance
	 */

	void evict(T persistentInstance);

	/**
	 * Force the <tt>Session</tt> to flush. Must be called at the end of a unit
	 * of work, before committing the transaction and closing the session (
	 * <tt>Transaction.commit()</tt> calls this method). <i>Flushing</i> is the
	 * process of synchronizing the underlying persistent store with persistable
	 * state held in memory.
	 */

	void flush();

	/**
	 * Completely clear the session. Evict all loaded instances and cancel all
	 * pending saves, updates and deletions. Do not close open iterators or
	 * instances of <tt>ScrollableResults</tt>.
	 */
	void clear();

	/**
	 * Persist some of given transient instances.
	 * 
	 * @param some
	 *            of transient instance of a persistent class
	 */
	void batchSave(final Collection<T> transientInstances);

	/**
	 * Update some of persistent instances with the identifier of each of the
	 * given detached instances.
	 * 
	 * @param some
	 *            detached instances containing updated state
	 */
	void batchUpdate(final Collection<T> detachedInstances);

	/**
	 * Either <tt>save()</tt> or <tt>update()</tt> some of given instances��
	 * 
	 * @param some
	 *            detached instances with state to be copied
	 */
	void batchSaveOrUpdate(final Collection<T> instances);

	/**
	 * Remove some persistent instances from the datastore.
	 * 
	 * @param some
	 *            instances to be removed
	 */
	void batchRemove(final Collection<T> persistentInstances);

	/**
	 * Return an instance of Page by specific conditions.
	 * 
	 * @param queryObject
	 * @param cacheable
	 * @param pageNumber
	 * @param resultsPerPage
	 * @param paginationSupport
	 * @return
	 */
	Page<T> findByQueryObject(final QueryObject<T> queryObject,
			final boolean cacheable, final int pageNumber,
			final int resultsPerPage, boolean paginationSupport);

	/**
	 * Return a list by specific conditions.
	 * 
	 * @param queryObject
	 * @param cacheable
	 * @param startIndex
	 * @param maxResultCount
	 * @return
	 */
	List<T> findByQueryObject(final QueryObject<T> queryObject,
			final boolean cacheable, final int startIndex,
			final int maxResultCount);

	/**
	 * Return an instance of Page by especial query language.
	 * 
	 * @param expql
	 * @param names
	 * @param values
	 * @param cacheable
	 * @param startIndex
	 * @param maxResultCount
	 * @param paginationKey
	 * @return
	 */
	Page<T> findByExpQL(final String expql, final String[] names,
			final Object[] values, final boolean cacheable,
			final int pageNumber, final int resultsPerPage, String paginationKey);

	/**
	 * Return a list by especial query language.
	 * 
	 * @param expql
	 * @param cacheable
	 * @param startIndex
	 * @param maxResultCount
	 * @return
	 */
	List<T> findByExpQL(final String expql, final boolean cacheable,
			final int startIndex, final int maxResultCount);

	/**
	 * Return a list by especial query language with specific conditions.
	 * 
	 * @param expql
	 * @param names
	 * @param values
	 * @param cacheable
	 * @param startIndex
	 * @param maxResultCount
	 * @return
	 */
	List<T> findByExpQL(final String expql, final String[] names,
			final Object[] values, final boolean cacheable,
			final int startIndex, final int maxResultCount);

	/**
	 * Return the number of the success which carried out by update or delete
	 * query language
	 * 
	 * @param expql
	 * @param cacheable
	 * @return
	 */
	int executeByExpQL(final String expql, final boolean cacheable);

	/**
	 * Return the number of the success which carried out by update or delete
	 * query language by specific conditions.
	 * 
	 * @param expql
	 * @param names
	 * @param values
	 * @param cacheable
	 * @return
	 */
	int executeByExpQL(final String expql, final String[] names,
			final Object[] values, final boolean cacheable);

	/**
	 * Return an instance which carried out by especial query language.
	 * 
	 * @param expql
	 * @param cacheable
	 * @return an instance
	 */
	Object getByExpQL(final String expql, final boolean cacheable);

	/**
	 * Return an instance which carried out by especial query language.
	 * 
	 * @param expql
	 * @param names
	 * @param values
	 * @param cacheable
	 * @return an instance
	 */
	Object getByExpQL(final String expql, final String[] names,
			final Object[] values, final boolean cacheable);

	/**
	 * Return an instance of Page by carried out standard SQL.
	 * 
	 * @param sql
	 * @param names
	 * @param values
	 * @param entityClass
	 * @param startIndex
	 * @param maxResultCount
	 * @param paginationKey
	 * @return
	 */
	Page<T> findBySQL(final String sql, final String alias,
			final String[] names, final Object[] values, Class<T> entityClass,
			boolean cacheable, int pageNumber, int resultsPerPage,
			String paginationKey);

	/**
	 * Return a list by carried out standard SQL.
	 * 
	 * @param sql
	 * @param entityClass
	 * @return result list
	 */
	List<T> findBySQL(final String sql, final String alias,
			Class<T> entityClass, boolean cacheable, final int startIndex,
			final int maxResultCount);

	/**
	 * Return a list by carried out standard SQL by specific conditions
	 * 
	 * @param sql
	 * @param names
	 * @param values
	 * @param entityClass
	 * @param startIndex
	 * @param maxResultCount
	 * @return
	 */
	List<T> findBySQL(final String sql, final String alias,
			final String[] names, final Object[] values, Class<T> entityClass,
			boolean cacheable, final int startIndex, final int maxResultCount);

	/**
	 * Return the number of the success which carried out by update or delete
	 * SQL
	 * 
	 * @param sql
	 * @return the number of the success
	 */
	int executeBySQL(final String sql);

	/**
	 * Return the number of the success which carried out by update or delete
	 * SQL by specific conditions.
	 * 
	 * @param sql
	 * @param names
	 * @param values
	 * @return
	 */
	int executeBySQL(final String sql, final String[] names,
			final Object[] values);

	/**
	 * Return the number of the result which carried out by SQL.
	 * 
	 * @param sql
	 * @return the number of the result
	 */
	int countBySQL(String sql);

	/**
	 * Return the number of the result which carried out by SQL with alias.
	 * 
	 * @param sql
	 * @param alias
	 * @return
	 */
	int countBySQL(String sql, final String alias);

	/**
	 * Return an instance which carried out by SQl.
	 * 
	 * @param sql
	 * @param entityClass
	 * @return
	 */
	Object findValueBySQL(final String sql, final String scalar,
			final Object type);

	/**
	 * Return an instance which carried out by SQl
	 * 
	 * @param sql
	 * @param names
	 * @param values
	 * @param entityClass
	 * @return
	 */
	Object findValueBySQL(final String sql, final String[] names,
			final Object[] values, final String scalar, final Object type);

	/**
	 * Return an object list which carried out by SQl with the data type
	 * declaration.
	 * 
	 * @param sql
	 * @param scalar
	 * @param type
	 * @param cacheable
	 * @return
	 */
	List<Object> findObjectsBySQL(final String sql, final String[] scalar,
			final Object[] type, boolean cacheable);

	/**
	 * Return an object list which carried out by SQl with the data type
	 * declaration.
	 * 
	 * @param sql
	 * @param names
	 * @param values
	 * @param scalar
	 * @param type
	 * @param cacheable
	 * @return
	 */
	List<Object> findObjectsBySQL(final String sql, final String[] names,
			final Object[] values, final String[] scalar, final Object[] type,
			boolean cacheable);

	/**
	 * Return an object list which carried out by SQl with the data type.
	 * 
	 * @param sql
	 * @param names
	 * @param values
	 * @param scalar
	 * @param type
	 * @param cacheable
	 * @param startIndex
	 * @param maxResultCount
	 * @return
	 */
	List<Object> findObjectsBySQL(final String sql, final String[] names,
			final Object[] values, final String[] scalar, final Object[] type,
			boolean cacheable, final int startIndex, final int maxResultCount);

	/**
	 * Execute a HSQL Named Query with the appropriate arguments
	 * 
	 * @param queryName
	 * @param names
	 * @param values
	 * @param cacheable
	 * @param startIndex
	 * @param maxResultCount
	 * @return
	 */
	public List<T> findByNamedQuery(final String queryName,
			final String[] names, final Object[] values,
			final boolean cacheable, final int startIndex,
			final int maxResultCount);

	/**
	 * Execute a HSQL Named Query with the appropriate arguments
	 * 
	 * @param queryName
	 * @param names
	 * @param values
	 * @param cacheable
	 * @param startIndex
	 * @param maxResultCount
	 * @param paginationKey
	 * @return
	 */
	public Page<T> findByNamedQuery(final String queryName,
			final String[] names, final Object[] values,
			final boolean cacheable, final int pageNumber,
			final int resultsPerPage, String paginationKey);

	/**
	 * Execute a HSQL Named Query with the appropriate arguments
	 * 
	 * @param queryName
	 * @param cacheable
	 * @param startIndex
	 * @param maxResultCount
	 * @return
	 */
	public List<T> findByNamedQuery(final String queryName,
			final boolean cacheable, final int startIndex,
			final int maxResultCount);

	/**
	 * Execute a page query with QueryObject method 
	 *
	 * @param queryObject
	 * @param cacheable
	 * @param pageForm
	 * @return
	 */
}
