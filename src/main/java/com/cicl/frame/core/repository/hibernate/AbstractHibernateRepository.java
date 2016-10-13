/**
 * Class IBaseRepository 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-1-12 15:00:52
 */
package com.cicl.frame.core.repository.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;

import com.cicl.frame.core.entity.Persistable;
import com.cicl.frame.core.repository.IBaseRepository;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.repository.query.QueryObject;
import com.cicl.frame.core.repository.query.QueryObjectFactory;
import com.cicl.frame.core.repository.query.QueryParam;
import com.cicl.frame.core.repository.query.QueryProjections;
import com.cicl.frame.core.util.CollectionUtils;
import com.cicl.frame.core.util.IntegerUtils;
import com.cicl.frame.core.util.ReflectionUtils;
import com.cicl.frame.core.web.form.PageForm;


public abstract class AbstractHibernateRepository<T extends Persistable, PK extends Serializable> implements
		IBaseRepository<T, PK> {

	private static final Log LOG = LogFactory.getLog(AbstractHibernateRepository.class);
	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> entityClass;

	/**
	 * the name of the cache region for queries executed
	 */
	private String queryCacheRegion;
	
	public AbstractHibernateRepository() {
		this.entityClass = getEntityClass();
	}	
	
	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass(){
		if(this.entityClass==null){
			this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return this.entityClass;
	}

	protected AbstractHibernateRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected final Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Criteria createCriteria(){
		return getSession().createCriteria(getEntityClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#get(java.lang.Class,
	 * java.io.Serializable)
	 */
	//@Override
	@SuppressWarnings("unchecked")
	public T get(Class<T> entityClass, PK id) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Get by PK - " + id);
		}
		final Session session = getSession();
		return (T) session.get(entityClass, id);
	}
	
	public T get(PK id) {
		return get(getEntityClass(), id);
	}

	/**
	 * Return the name of the cache region for queries executed by this
	 * template.
	 */
	public final String getQueryCacheRegion() {
		return queryCacheRegion;
	}

	/**
	 * Set the name of the cache region for queries executed by this template.
	 * If this is specified, it will be applied to all Query and Criteria
	 * objects created by this template (including all queries through find
	 * methods).
	 * <p>
	 * The cache region will not take effect unless queries created by this
	 * template are configured to be cached via the "cacheQueries" property.
	 * 
	 * @see #setCacheQueries
	 * @see org.hibernate.Query#setCacheRegion
	 * @see org.hibernate.Criteria#setCacheRegion
	 */
	public final void setQueryCacheRegion(String queryCacheRegion) {
		this.queryCacheRegion = queryCacheRegion;
	}

	/**
	 * Return the instance of HibernateDAOHelper.
	 * 
	 * @return the instance of HibernateDAOHelper
	 */
	public HibernateDAOHelper getHelper() {
		return new HibernateDAOHelper(sessionFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#load(java.lang.Class ,
	 * java.io.Serializable)
	 */
	//@Override
	@SuppressWarnings("unchecked")
	public T load(Class<T> entityClass, PK id) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Load by PK - " + id);
		}
		final Session session = getSession();
		return (T) session.load(entityClass, id);
	}
	
	//@Override
	public T load(PK id) {
		return load(getEntityClass(), id);
	}
	
	public List<T> load(PK[] ids) {
		QueryObject<T> queryObject = QueryObjectFactory.createQueryObject(getEntityClass());
		Collection<PK> idsList = new ArrayList<PK>();
		CollectionUtils.addAll(idsList, ids);
		
		queryObject.setQueryParam(new QueryParam("id", QueryParam.OPERATOR_IN, idsList));
		return findByQueryObject(queryObject, false, -1, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#save(com.medivault
	 * .mynder.model.Persistable)
	 */
	//@Override
	@SuppressWarnings("unchecked")
	public PK save(T transientInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Saving " + transientInstance.getClassName(false));
		}
		final Session session = getSession();
		return (PK) session.save(transientInstance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#update(com.medivault
	 * .mynder.model.Persistable)
	 */
	//@Override
	public void update(T detachedInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Updating " + detachedInstance.getClassName(false));
		}
		final Session session = getSession();
		session.update(detachedInstance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#clear()
	 */
	//@Override
	public void clear() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Clearing the session");
		}
		final Session session = getSession();
		session.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#evict(com.medivault
	 * .mynder.model.Persistable)
	 */
	//@Override
	public void evict(T persistentInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Evicting " + persistentInstance.getClassName(false));
		}
		final Session session = getSession();
		session.evict(persistentInstance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#flush()
	 */
	//@Override
	public void flush() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Flushing the session");
		}
		final Session session = getSession();
		session.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#merge(com.medivault
	 * .mynder.model.Persistable)
	 */
	//@Override
	public void merge(T detachedInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Merging " + detachedInstance.getClassName(false));
		}
		final Session session = getSession();
		session.merge(detachedInstance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#persist(com.medivault
	 * .mynder.model.Persistable)
	 */
	//@Override
	public void persist(T transientInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Persisting " + transientInstance.getClassName(false));
		}
		final Session session = getSession();
		session.persist(transientInstance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#refresh(com.medivault
	 * .mynder.model.Persistable)
	 */
	//@Override
	public void refresh(T instance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Refreshing the session");
		}
		final Session session = getSession();
		session.persist(instance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#remove(com.medivault
	 * .mynder.model.Persistable)
	 */
	//@Override
	public void remove(T persistentInstance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Removing " + persistentInstance.getClassName(false));
		}
		final Session session = getSession();
		session.delete(persistentInstance);
	}

	//@Override
	public void remove(PK id) {
		T persistentInstance = load(id);
		if (LOG.isDebugEnabled()) {
			LOG.debug("Removing " + persistentInstance.getClassName(false));
		}
		final Session session = getSession();
		session.delete(persistentInstance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#saveOrUpdate(com
	 * .medivault.mynder.model.Persistable)
	 */
	//@Override
	public void saveOrUpdate(T instance) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("SaveOrUpdating " + instance.getClassName(false));
		}
		final Session session = getSession();
		session.saveOrUpdate(instance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#batchRemove(java
	 * .util.Collection)
	 */
	//@Override
	public void batchRemove(Collection<T> persistentInstances) {
		final Session session = getSession();
		HibernateDAOHelper.checkWriteOperationAllowed(session);
		if (persistentInstances != null && !persistentInstances.isEmpty()) {
			int max = org.apache.commons.collections.CollectionUtils.size(persistentInstances);
			int i = 0;
			for (T t : persistentInstances) {
				session.refresh(t);
				session.delete(t);
				if ((i != 0 && i % HibernateDAOHelper.DEFAULT_BATCH_SIZE == 0) || i == max - 1) {
					session.flush();
					session.clear();
				}
				i++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#batchSave(java.util
	 * .Collection)
	 */
	//@Override
	public void batchSave(Collection<T> transientInstances) {
		final Session session = getSession();
		HibernateDAOHelper.checkWriteOperationAllowed(session);
		if (transientInstances != null && !transientInstances.isEmpty()) {
			int max = org.apache.commons.collections.CollectionUtils.size(transientInstances);
			int i = 0;
			for (T t : transientInstances) {
				session.save(t);
				if ((i != 0 && i % HibernateDAOHelper.DEFAULT_BATCH_SIZE == 0) || i == max - 1) {
					session.flush();
					session.clear();
				}
				i++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#batchSaveOrUpdate
	 * (java.util.Collection)
	 */
	//@Override
	public void batchSaveOrUpdate(Collection<T> instances) {
		final Session session = getSession();
		HibernateDAOHelper.checkWriteOperationAllowed(session);
		if (instances != null && !instances.isEmpty()) {
			int max = org.apache.commons.collections.CollectionUtils.size(instances);
			int i = 0;
			for (T t : instances) {
				session.saveOrUpdate(t);
				if ((i != 0 && i % HibernateDAOHelper.DEFAULT_BATCH_SIZE == 0) || i == max - 1) {
					session.flush();
					session.clear();
				}
				i++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#batchUpdate(java
	 * .util.Collection)
	 */
	//@Override
	public void batchUpdate(Collection<T> detachedInstances) {
		final Session session = getSession();
		HibernateDAOHelper.checkWriteOperationAllowed(session);
		if (detachedInstances != null && !detachedInstances.isEmpty()) {
			int max = org.apache.commons.collections.CollectionUtils.size(detachedInstances);
			int i = 0;
			for (T t : detachedInstances) {
				session.saveOrUpdate(t);
				if ((i != 0 && i % HibernateDAOHelper.DEFAULT_BATCH_SIZE == 0) || i == max - 1) {
					session.flush();
					session.clear();
				}
				i++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findByQueryObject
	 * (com.cicl.frame.core.repository.query.QueryObject, boolean, int, int,
	 * boolean)
	 */
	@SuppressWarnings("unchecked")
	//@Override
	public Page<T> findByQueryObject(QueryObject<T> queryObject, boolean cacheable, int pageNumber, int resultsPerPage,
			boolean paginationSupport) {
		int totalCount = -1;
		int startIndex = (pageNumber - 1) * resultsPerPage;
		List<T> result = null;
		if (paginationSupport) {
			/*
			 * Get the number of result which carried out by hibernate's
			 * criteria query first when paginationSupport's value is true.
			 */
			QueryProjections backupProjections = null;
			if (queryObject.getQueryProjections() == null) {
				queryObject.setQueryProjections(new QueryProjections());
				backupProjections = queryObject.getQueryProjections();
			}
			if (backupProjections == null) {
				backupProjections = queryObject.getQueryProjections().clone();
			}
			QueryProjections rowCountProjections = new QueryProjections();
			rowCountProjections.setRowCount(true);
			rowCountProjections.setDistinct(backupProjections.getDistinct());
			rowCountProjections.setDistinctFlag(backupProjections.isDistinctFlag());
			rowCountProjections.setGroupProperty(backupProjections.getGroupProperty());
			queryObject.setQueryProjections(rowCountProjections);
			// TODO:May has performance problem here.
			List countResult = findByQueryObject(queryObject, cacheable, -1, -1);
			totalCount = IntegerUtils.createInteger(CollectionUtils.getFromUniqueCollection(countResult));
			if (totalCount > 0) {
				/*
				 * If record number is more than zero.
				 */
				queryObject.setQueryProjections(backupProjections);
				result = findByQueryObject(queryObject, cacheable, startIndex, resultsPerPage);
			}
		} else {
			result = findByQueryObject(queryObject, cacheable, startIndex, resultsPerPage);
		}
		return new Page<T>(startIndex, resultsPerPage, totalCount, result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#queryObject
	 * (com.cicl.frame.core.repository.query.QueryObject, boolean, PageForm)
	 */
	//@Override
	public Page<T> search(QueryObject<T> queryObject, boolean cacheable, PageForm pageForm) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("search by queryObject - " + queryObject + ",cacheable - " + cacheable + ", pageForm - " + pageForm);
		}
		if(queryObject==null){
			queryObject = QueryObjectFactory.createQueryObject(getEntityClass());
		}
		if (pageForm == null) {
			return findByQueryObject(queryObject, cacheable, 0, 0, false);
		} else {
			QueryProjections queryProjections = queryObject.getQueryProjections();
			if (queryProjections == null) {
				queryProjections = new QueryProjections();
				queryObject.setQueryProjections(queryProjections);
			}

			if (pageForm.getOrder() != null) {
				queryProjections.setOrderProperty(pageForm.getOrder());
			}
			if (pageForm.getOrderBy() != null) {
				queryProjections.setDescFlag(pageForm.getOrderBy().toUpperCase().equals("DESC"));
			}

			return findByQueryObject(queryObject, cacheable, pageForm.getPageNumber(), pageForm.getResultsPerPage(),
					true);
		}
	}
	//@Override
	public Page<T> search(QueryObject<T> queryObject,  PageForm pageForm) {
		return search(queryObject, false, pageForm);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#queryObject
	 * (com.cicl.frame.core.repository.query.QueryObject, boolean,)
	 */
	//@Override
	public List<T> search(QueryObject<T> queryObject, boolean cacheable) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("search by queryObject - " + queryObject + ",cacheable - " + cacheable);
		}
		if(queryObject==null){
			queryObject = QueryObjectFactory.createQueryObject(getEntityClass());
		}
		return findByQueryObject(queryObject, cacheable, 0, 0);
	}
	//@Override
	public List<T> search(QueryObject<T> queryObject) {
		return search(queryObject, false);
	}
	//@Override
	public boolean isPropertyExist(PK id, String propertyName, Object newValue) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("isPropertyExist by id - " + id + ",propertyName - " + propertyName+ ",newValue - " + newValue);
		}
		QueryObject<T> queryObject = QueryObjectFactory.createQueryObject(getEntityClass());
		QueryParam queryParam = new QueryParam();
		queryParam.and(new QueryParam(propertyName, QueryParam.OPERATOR_EQ, newValue));
		queryObject.setQueryParam(queryParam);
		
		//is new
		if(id==null){
			return search(queryObject, false).size()>=1;
		}
		//is update
		else{
			T oldEntity = get(id);
			Object oldValue = ReflectionUtils.getFieldValue(oldEntity, propertyName);
			if( oldValue==null ){
				return false;
			}
			if( oldValue.equals(newValue)){
				return false;
			}
			else{
				return search(queryObject, false).size()>=1;
			}			
		}		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findListByQueryObject
	 * (com.cicl.frame.core.repository.query.QueryObject, boolean, int, int)
	 */
	@SuppressWarnings("unchecked")
	//@Override
	public List<T> findByQueryObject(QueryObject<T> queryObject, boolean cacheable, int startIndex, int maxResultCount) {
		final Session session = getSession();
		Criteria criteria = session.createCriteria(queryObject.getEntityClass());
		if (cacheable) {
			criteria.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				criteria.setCacheRegion(getQueryCacheRegion());
			}
		}

		criteria = getHelper().buildCriteria(criteria, queryObject);
		if (maxResultCount > 0) {
			criteria.setMaxResults(maxResultCount);
		}
		if (startIndex > 0) {
			criteria.setFirstResult(startIndex);
		}

		return criteria.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findByExpQL(java
	 * .lang.String, boolean, int, int)
	 */
	//@Override
	public List<T> findByExpQL(String expql, boolean cacheable, int startIndex, int maxResultCount) {
		return findByExpQL(expql, null, null, cacheable, startIndex, maxResultCount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findByExpQL(java
	 * .lang.String, java.lang.String[], java.lang.Object[], boolean, int, int,
	 * int, java.lang.String)
	 */
	//@Override
	public Page<T> findByExpQL(String expql, String[] names, Object[] values, boolean cacheable, int pageNumber,
			int resultsPerPage, String paginationKey) {
		Page<T> result = new Page<T>();
		int startIndex = (pageNumber - 1) * resultsPerPage;
		result.setPageSize(resultsPerPage);
		result.setStartIndex(startIndex);

		if (paginationKey == null) {
			result.setResults(findByExpQL(expql, names, values, cacheable, startIndex, resultsPerPage));
		} else {
			int totalCount = ((Long) getByExpQL(HibernateDAOHelper.getCountSql(expql, paginationKey, null), names,
					values, cacheable)).intValue();
			result.setTotalCount(totalCount);
			if (totalCount > 0) {
				result.setResults(findByExpQL(expql, names, values, cacheable, startIndex, resultsPerPage));
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findByExpQL(java
	 * .lang.String, java.lang.String[], java.lang.Object[], boolean, int, int)
	 */

	@SuppressWarnings("unchecked")
	//@Override
	public List<T> findByExpQL(String expql, String[] names, Object[] values, boolean cacheable, int startIndex,
			int maxResultCount) {
		final Session session = getSession();
		final Query query = session.createQuery(expql);

		if (cacheable) {
			query.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				query.setCacheRegion(getQueryCacheRegion());
			}
		}
		if (names != null && values != null && names.length == values.length) {
			for (int i = 0, max = names.length; i < max; i++) {
				if (values[i] instanceof Object[]) {
					query.setParameterList(names[i], (Object[]) values[i]);
				} else {
					query.setParameter(names[i], values[i]);
				}
			}
		} else if (names == null && values != null) {
			for (int i = 0, max = values.length; i < max; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		if (maxResultCount != -1) {
			query.setMaxResults(maxResultCount);
		}
		if (startIndex != -1) {
			query.setFirstResult(startIndex);
		}

		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#getByExpQL(java.
	 * lang.String, boolean)
	 */
	//@Override
	public Object getByExpQL(String expql, boolean cacheable) {
		return getByExpQL(expql, null, null, cacheable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#getByExpQL(java.
	 * lang.String, java.lang.String[], java.lang.Object[], boolean)
	 */
	//@Override
	public Object getByExpQL(String expql, String[] names, Object[] values, boolean cacheable) {
		final Session session = getSession();
		final Query query = session.createQuery(expql);
		if (cacheable) {
			query.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				query.setCacheRegion(getQueryCacheRegion());
			}
		}
		if (names != null && values != null && names.length == values.length) {
			for (int i = 0, max = names.length; i < max; i++) {
				if (values[i] instanceof Object[]) {
					query.setParameterList(names[i], (Object[]) values[i]);
				} else {
					query.setParameter(names[i], values[i]);
				}
			}
		} else if (names == null && values != null) {
			for (int i = 0, max = values.length; i < max; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}

		return query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#executeByExpQL(java
	 * .lang.String, boolean)
	 */
	//@Override
	public int executeByExpQL(String expql, boolean cacheable) {
		return executeByExpQL(expql, null, null, cacheable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#executeByExpQL(java
	 * .lang.String, java.lang.String[], java.lang.Object[], boolean)
	 */
	//@Override
	public int executeByExpQL(String expql, String[] names, Object[] values, boolean cacheable) {
		final Session session = getSession();
		final Query query = session.createQuery(expql);
		if (cacheable) {
			query.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				query.setCacheRegion(getQueryCacheRegion());
			}
		}
		if (names != null && values != null && names.length == values.length) {
			for (int i = 0, max = names.length; i < max; i++) {
				query.setParameter(names[i], values[i]);
			}
		} else if (names == null && values != null) {
			for (int i = 0, max = values.length; i < max; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}

		return query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findBySQL(java.lang
	 * .String, java.lang.String, java.lang.Class, boolean, int, int)
	 */
	//@Override
	public List<T> findBySQL(String sql, String alias, Class<T> entityClass, boolean cacheable, int startIndex,
			int maxResultCount) {
		return findBySQL(sql, alias, null, null, entityClass, cacheable, startIndex, maxResultCount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findBySQL(java.lang
	 * .String, java.lang.String, java.lang.String[], java.lang.Object[],
	 * java.lang.Class, boolean, int, int, int, java.lang.String)
	 */
	//@Override
	public Page<T> findBySQL(String sql, String alias, String[] names, Object[] values, Class<T> entityClass,
			boolean cacheable, int pageNumber, int resultsPerPage, String paginationKey) {
		Page<T> result = new Page<T>();
		int startIndex = (pageNumber - 1) * resultsPerPage;
		result.setPageSize(resultsPerPage);
		result.setStartIndex(startIndex);

		if (paginationKey == null) {
			result.setResults(findBySQL(sql, alias, entityClass, cacheable, startIndex, resultsPerPage));
		} else {
			int totalCount = countBySQL(HibernateDAOHelper.getCountSql(sql, paginationKey, null),
					HibernateDAOHelper.COUNT_ALIAS);
			result.setTotalCount(totalCount);
			if (totalCount > 0) {
				result.setResults(findBySQL(sql, alias, entityClass, cacheable, startIndex, resultsPerPage));
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findBySQL(java.lang
	 * .String, java.lang.String, java.lang.String[], java.lang.Object[],
	 * java.lang.Class, boolean, int, int)
	 */
	@SuppressWarnings("unchecked")
	//@Override
	public List<T> findBySQL(String sql, String alias, String[] names, Object[] values, Class<T> entityClass,
			boolean cacheable, int startIndex, int maxResultCount) {
		final Session session = getSession();
		final Query query = (alias == null) ? session.createSQLQuery(sql).addEntity(entityClass) : session
				.createSQLQuery(sql).addEntity(alias, entityClass);
		if (cacheable) {
			query.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				query.setCacheRegion(getQueryCacheRegion());
			}
		}

		if (names != null && values != null && names.length == values.length) {
			for (int i = 0, max = names.length; i < max; i++) {
				query.setParameter(names[i], values[i]);
			}
		} else if (names == null && values != null) {
			for (int i = 0, max = values.length; i < max; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}

		if (maxResultCount != -1) {
			query.setMaxResults(maxResultCount);
		}
		if (startIndex != -1) {
			query.setFirstResult(startIndex);
		}

		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#countBySQL(java.
	 * lang.String, java.lang.String)
	 */
	//@Override
	public int countBySQL(String sql, String alias) {
		final Session session = getSession();
		final Query query = session.createSQLQuery(sql).addScalar(alias, Hibernate.INTEGER);
		Integer count = (Integer) query.uniqueResult();
		return count == null ? 0 : count.intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#countBySQL(java.
	 * lang.String)
	 */
	//@Override
	public int countBySQL(String sql) {
		return countBySQL(sql, HibernateDAOHelper.COUNT_ALIAS);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#executeBySQL(java
	 * .lang.String, java.lang.String[], java.lang.Object[])
	 */
	//@Override
	public int executeBySQL(String sql, String[] names, Object[] values) {
		final Session session = getSession();
		final Query query = session.createSQLQuery(sql);

		if (names != null && values != null && names.length == values.length) {
			for (int i = 0, max = names.length; i < max; i++) {
				query.setParameter(names[i], values[i]);
			}
		} else if (names == null && values != null) {
			for (int i = 0, max = values.length; i < max; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}

		return query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#executeBySQL(java
	 * .lang.String)
	 */
	//@Override
	public int executeBySQL(String sql) {
		return executeBySQL(sql, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findValueBySQL(java
	 * .lang.String, java.lang.String, java.lang.Object)
	 */
	//@Override
	public Object findValueBySQL(String sql, String scalar, Object type) {
		return findValueBySQL(sql, null, null, scalar, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findValueBySQL(java
	 * .lang.String, java.lang.String[], java.lang.Object[], java.lang.String,
	 * java.lang.Object)
	 */
	//@Override
	public Object findValueBySQL(String sql, String[] names, Object[] values, String scalar, Object type) {
		final Session session = getSession();
		final Query query = session.createSQLQuery(sql).addScalar(scalar, (Type) type);

		if (names != null && values != null && names.length == values.length) {
			for (int i = 0, max = names.length; i < max; i++) {
				query.setParameter(names[i], values[i]);
			}
		} else if (names == null && values != null) {
			for (int i = 0, max = values.length; i < max; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}

		return query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findObjectsBySQL
	 * (java.lang.String, java.lang.String[], java.lang.Object[], boolean)
	 */
	//@Override
	public List<Object> findObjectsBySQL(String sql, String[] scalar, Object[] type, boolean cacheable) {
		return findObjectsBySQL(sql, null, null, scalar, type, cacheable, -1, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findObjectsBySQL
	 * (java.lang.String, java.lang.String[], java.lang.Object[],
	 * java.lang.String[], java.lang.Object[], boolean, int, int)
	 */
	@SuppressWarnings("unchecked")
	//@Override
	public List<Object> findObjectsBySQL(String sql, String[] names, Object[] values, String[] scalar, Object[] type,
			boolean cacheable, int startIndex, int maxResultCount) {
		final Session session = getSession();
		final Query query = session.createSQLQuery(sql);

		for (int i = 0, max = scalar.length; i < max; i++) {
			((SQLQuery) query).addScalar(scalar[i], (Type) type[i]);
		}

		if (cacheable) {
			query.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				query.setCacheRegion(getQueryCacheRegion());
			}
		}

		if (names != null && values != null && names.length == values.length) {
			for (int i = 0, max = names.length; i < max; i++) {
				query.setParameter(names[i], values[i]);
			}
		} else if (names == null && values != null) {
			for (int i = 0, max = values.length; i < max; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}

		if (maxResultCount != -1) {
			query.setMaxResults(maxResultCount);
		}
		if (startIndex != -1) {
			query.setFirstResult(startIndex);
		}

		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findObjectsBySQL
	 * (java.lang.String, java.lang.String[], java.lang.Object[],
	 * java.lang.String[], java.lang.Object[], boolean)
	 */
	//@Override
	public List<Object> findObjectsBySQL(String sql, String[] names, Object[] values, String[] scalar, Object[] type,
			boolean cacheable) {
		return findObjectsBySQL(sql, names, values, scalar, type, cacheable, -1, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findByNamedQuery
	 * (java.lang.String, boolean, int, int)
	 */
	//@Override
	public List<T> findByNamedQuery(String queryName, boolean cacheable, int startIndex, int maxResultCount) {
		return findByNamedQuery(queryName, null, null, cacheable, startIndex, maxResultCount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findByNamedQuery
	 * (java.lang.String, java.lang.String[], java.lang.Object[], boolean, int,
	 * int, int, java.lang.String)
	 */
	//@Override
	public Page<T> findByNamedQuery(String queryName, String[] names, Object[] values, boolean cacheable,
			int pageNumber, int resultsPerPage, String paginationKey) {
		Page<T> result = new Page<T>();
		int startIndex = (pageNumber - 1) * resultsPerPage;
		result.setPageSize(resultsPerPage);
		result.setStartIndex(startIndex);

		final Session session = getSession();
		final Query query = session.getNamedQuery(queryName);

		if (paginationKey == null) {
			result.setResults(findByNamedQuery(queryName, names, values, cacheable, startIndex, resultsPerPage));
		} else {
			int totalCount = ((Long) getByExpQL(HibernateDAOHelper.getCountSql(query.getQueryString(), paginationKey,
					null), names, values, cacheable)).intValue();
			result.setTotalCount(totalCount);
			if (totalCount > 0) {
				result.setResults(findByNamedQuery(queryName, names, values, cacheable, startIndex, resultsPerPage));
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cicl.frame.core.repository.IBaseRepository#findByNamedQuery
	 * (java.lang.String, java.lang.String[], java.lang.Object[], boolean, int,
	 * int)
	 */
	@SuppressWarnings("unchecked")
	//@Override
	public List<T> findByNamedQuery(String queryName, String[] names, Object[] values, boolean cacheable,
			int startIndex, int maxResultCount) {
		final Session session = getSession();
		final Query query = session.getNamedQuery(queryName);

		if (cacheable) {
			query.setCacheable(true);
			if (getQueryCacheRegion() != null) {
				query.setCacheRegion(getQueryCacheRegion());
			}
		}
		if (names != null && values != null && names.length == values.length) {
			for (int i = 0, max = names.length; i < max; i++) {
				query.setParameter(names[i], values[i]);
			}
		} else if (names == null && values != null) {
			for (int i = 0, max = values.length; i < max; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		if (maxResultCount != -1) {
			query.setMaxResults(maxResultCount);
		}
		if (startIndex != -1) {
			query.setFirstResult(startIndex);
		}

		return query.list();
	}
}
