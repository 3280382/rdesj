/** 
 * @(#)HibernateDAOHelper.java 1.0.0 2011-1-13 04:33:47  
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.core.repository.hibernate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.cicl.frame.core.repository.query.QueryObject;
import com.cicl.frame.core.repository.query.QueryParam;
import com.cicl.frame.core.repository.query.QueryProjections;
import com.cicl.frame.core.util.RandomGenerator;

/**
 * Class HibernateDAOHelper 
 * 
 * @author Ray Li
 * @version $Revision:1.0.0, $Date: 2011-1-13 04:33:47
 */
public class HibernateDAOHelper {

	private static Log LOG = LogFactory.getLog(HibernateDAOHelper.class);

    private Map<String, String> aliasesMap;
    
    protected final static int DEFAULT_BATCH_SIZE = 30;
    
    protected final static String ALIAS_PREFIX = "alias_";

	public final static String COUNT_ALIAS = "c168";

	private SessionFactory sessionFactory;

	@SuppressWarnings("unused")
	private HibernateDAOHelper() {

	}

	public HibernateDAOHelper(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * ssemble the SQL which carry out the sum of results
	 * 
	 * @param sql
	 * @param paginationKey
	 * @param alias
	 * @return
	 */
	public static String getCountSql(String sql, String paginationKey,
			String alias) {
		String trueAlias = (alias == null) ? COUNT_ALIAS : alias;
		if (StringUtils.isEmpty(sql)) {
			return null;
		} else {
			// sql = StringUtils.lowerCase(sql);
		}
		return (new StringBuilder()).append("select count(").append(
				paginationKey).append(") as ").append(trueAlias).append(" ")
				.append(
						sql.substring(sql.toLowerCase().indexOf(" from "), sql
								.toLowerCase().indexOf("order by") == -1 ? sql
								.length() : sql.toLowerCase().indexOf(
								"order by"))).toString();
	}

	/**
	 * Check whether write operations are allowed on the given Session.
	 * <p>
	 * Default implementation throws an InvalidDataAccessApiUsageException in
	 * case of <code>FlushMode.NEVER/MANUAL</code>.
	 * 
	 * @param current
	 *            hibernate session
	 * @throws InvalidDataAccessApiUsageException
	 *             if write operations are not allowed
	 * @see #setCheckWriteOperations
	 * @see #getFlushMode()
	 * @see #FLUSH_EAGER
	 * @see org.hibernate.Session#getFlushMode()
	 * @see org.hibernate.FlushMode#NEVER
	 * @see org.hibernate.FlushMode#MANUAL
	 */
	public static void checkWriteOperationAllowed(Session session)
			throws InvalidDataAccessApiUsageException {
		if (session.getFlushMode().lessThan(FlushMode.COMMIT)) {
			throw new InvalidDataAccessApiUsageException(
					"Write operations are not allowed in read-only mode.");
		}
	}

	/**
	 * Assemble the Hibernate Criteria instance by QueryProjections which is in
	 * the QueryObject.
	 * 
	 * @param Hibernate
	 *            Criteria's instance
	 * @param QueryObject
	 *            's instance
	 * @return Assembled Hibernate Criteria's instance
	 */
	@SuppressWarnings("unchecked")
	public Criteria buildCriteria(Criteria criteria, QueryObject queryObject) {
		if (queryObject == null) {
			return criteria;
		}
		String aliasKey = RandomGenerator.randomString(12);
		if (queryObject.getQueryProjections() != null) {
			QueryProjections queryProjections = queryObject
					.getQueryProjections();
			ProjectionList projectionList = Projections.projectionList();
			if (queryProjections.isRowCount()) {
				if (queryProjections.isDistinctFlag()) {
					projectionList.add(Projections
							.countDistinct(getSessionFactory()
									.getClassMetadata(
											queryObject.getEntityClass())
									.getIdentifierPropertyName()));
				} else {
					projectionList.add(Projections.rowCount());
				}
			}

			if (queryProjections.isDistinctFlag()
					&& !queryProjections.isRowCount()) {
				/* filter the duplicate data when retrieving records */
				String identityPropertyName = getIdentityPropertyName(queryObject
						.getEntityClass());
				projectionList.add(Projections.distinct(Projections
						.property(identityPropertyName)), ALIAS_PREFIX
						+ identityPropertyName);
				projectionList.add(getClassProjectionList(queryObject
						.getEntityClass()));
				// criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				// has some pagination issues when using hibernate
			} else if (!queryProjections.isRowCount()) {
				if (queryProjections.getDistinct() != null) {
					for (String distinct : queryProjections.getDistinct()) {
						projectionList.add(Projections.distinct(Projections
								.property(generateAlias(criteria, distinct,
										aliasKey))));
					}
				}

				if (queryProjections.getProperty() != null) {
					for (String property : queryProjections.getProperty()) {
						projectionList.add(Projections.property(generateAlias(
								criteria, property, aliasKey)));
					}
				}

				if (queryProjections.getCountDistinct() != null) {
					for (String countDistinct : queryProjections
							.getCountDistinct()) {
						projectionList.add(Projections
								.countDistinct(generateAlias(criteria,
										countDistinct, aliasKey)));
					}
				}

				if (queryProjections.getMax() != null) {
					for (String max : queryProjections.getMax()) {
						projectionList.add(Projections.max(generateAlias(
								criteria, max, aliasKey)));
					}
				}

				if (queryProjections.getMin() != null) {
					for (String min : queryProjections.getMin()) {
						projectionList.add(Projections.min(generateAlias(
								criteria, min, aliasKey)));
					}
				}

				if (queryProjections.getCount() != null) {
					for (String count : queryProjections.getCount()) {
						projectionList.add(Projections.count(generateAlias(
								criteria, count, aliasKey)));
					}
				}

				if (queryProjections.getAvg() != null) {
					for (String avg : queryProjections.getAvg()) {
						projectionList.add(Projections.avg(generateAlias(
								criteria, avg, aliasKey)));
					}
				}

				if (queryProjections.getSum() != null) {
					for (String sum : queryProjections.getSum()) {
						projectionList.add(Projections.sum(generateAlias(
								criteria, sum, aliasKey)));
					}
				}
			}

			if (queryProjections.getGroupProperty() != null) {
				for (String groupProperty : queryProjections.getGroupProperty()) {
					projectionList.add(Projections.groupProperty(generateAlias(
							criteria, groupProperty, aliasKey)));
				}
			}

			if (queryProjections.getOrderProperty() != null) {
				String[] orderProperty = queryProjections.getOrderProperty();
				boolean[] descFlag = queryProjections.getDescFlag();
				boolean descFlagState = descFlag != null
						&& descFlag.length == orderProperty.length;
				for (int i = 0, max = orderProperty.length; i < max; i++) {
					if (descFlagState && descFlag[i]) {
						criteria.addOrder(Order.desc(generateAlias(criteria,
								orderProperty[i], aliasKey)));
					} else {
						criteria.addOrder(Order.asc(generateAlias(criteria,
								orderProperty[i], aliasKey)));
					}
				}
			}

			if (projectionList.getLength() > 0) {
				criteria.setProjection(projectionList);
				if (queryProjections.isDistinctFlag()
						&& !queryProjections.isRowCount()) {
					criteria
							.setResultTransformer(new PrefixAliasToBeanResultTransformer(
									queryObject.getEntityClass()));
				}
			}
		}
		QueryParam queryParam = queryObject.getQueryParam();
		if (queryParam != null) {
			criteria.add(processParameter(criteria, queryParam, aliasKey));
			clearAlias(aliasKey);
		}
		return criteria;
	}

	/**
	 * 
	 * @param criteria
	 * @param name
	 * @param aliasKey
	 * @return
	 */
	private String generateAlias(Criteria criteria, String name, String aliasKey) {
		int aliasPos = StringUtils.indexOf(name, ":");
		if (aliasPos != -1) {
			int joinType = name.charAt(aliasPos - 1) == '[' ? (name
					.charAt(aliasPos + 1) == ']' ? CriteriaSpecification.FULL_JOIN
					: CriteriaSpecification.LEFT_JOIN)
					: CriteriaSpecification.INNER_JOIN;
			String trueName = (joinType == CriteriaSpecification.FULL_JOIN) ? StringUtils
					.substring(name, aliasPos + 2)
					: StringUtils.substring(name, aliasPos + 1);
			if (StringUtils.indexOf(trueName, ".") == -1) {
				LOG
						.error("ERROR:"
								+ name
								+ " -- the parameter name with alias should contain a . char!");
				return null;
			}
			String alias = StringUtils.substringBefore(trueName, ".");
			if (aliasesMap == null) {
				aliasesMap = new HashMap<String, String>();
			}
			String aliases = aliasesMap.get(aliasKey);
			if (aliases == null)
				aliases = "";
			if (StringUtils.indexOf(aliases, alias + '|') == -1) {
				criteria = joinType == CriteriaSpecification.INNER_JOIN ? criteria
						.createAlias(StringUtils.substringBefore(name, ":"),
								alias, joinType)
						: criteria.createAlias(StringUtils.substringBefore(
								name, "[:"), alias, joinType);
				aliases = aliases + alias + '|';
				aliasesMap.put(aliasKey, aliases);
			}
			name = trueName;
			if (StringUtils.lastIndexOf(name, ":") != -1) {
				name = generateAlias(criteria, name, aliasKey);
			}
		}
		return name;
	}

	
	 /**
     * Transform queryParam to Criterion of Hibernate
     * 
     * @param criteria
     * @param queryParam
     * @param aliasKey
     * @return
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public Criterion processParameter(Criteria criteria, QueryParam queryParam, String aliasKey) {
        Criterion criterion = null;
        if (queryParam.getMode() == QueryParam.BASIC) {
            if (queryParam.getSql() != null) {
                criterion = Expression.sql(queryParam.getSql());
            } else if (queryParam.getName() != null) {
                boolean notFlag = false;
                boolean ansiNull = true;
                String operator = queryParam.getOperator();
                String name = generateAlias(criteria, queryParam.getName(), aliasKey);
                if (operator.startsWith("!")) {
                    notFlag = true;
                    if (StringUtils.equals(QueryParam.OPERATOR_NE_ANSINULL_OFF, operator)) {
                        ansiNull = false;
                        operator = "=";
                    } else {
                        operator = operator.substring(1);
                    }
                }
                if (queryParam.getValue() == null) {
                    criterion = Restrictions.isNull(name);
                } else if (queryParam.getType() != -1) {
                    criterion = Expression.sql((new StringBuffer()).append(name).append(' ').append(operator).append(
                            " ?").toString(), queryParam.getValue(), HibernateTypeConvertor.getTypeBySqlType(queryParam
                            .getType()));
                } else if (QueryParam.FETCH.equals(operator)) {
                	if (queryParam.getValue() != null && queryParam.getValue() instanceof FetchMode) {
                		criteria.setFetchMode(name, (FetchMode)queryParam.getValue());
                	} else {
                		LOG.error("error fetch mode for :" + name);
                	}
                	
                } else {
                    if (QueryParam.OPERATOR_EQ.equals(operator)) {
                        criterion = Restrictions.eq(name, queryParam.getValue());
                    } else if (QueryParam.OPERATOR_GE.equals(operator)) {
                        criterion = Restrictions.ge(name, queryParam.getValue());
                    } else if (QueryParam.OPERATOR_GT.equals(operator)) {
                        criterion = Restrictions.gt(name, queryParam.getValue());
                    } else if (QueryParam.OPERATOR_LE.equals(operator)) {
                        criterion = Restrictions.le(name, queryParam.getValue());
                    } else if (QueryParam.OPERATOR_LT.equals(operator)) {
                        criterion = Restrictions.lt(name, queryParam.getValue());
                    } else if (QueryParam.OPERATOR_LIKE.equals(operator)) {
                        if (queryParam.getValue() instanceof String) {
                            criterion = Restrictions.like(name, queryParam.getValue());
                        } else {
                            LOG.error("The value type -- " + queryParam.getValue().getClass().getName()
                                    + " not match the operator:like");
                        }
                    } else if (QueryParam.OPERATOR_INCLUDE.equals(operator)) {
                        if (queryParam.getValue() instanceof String) {
                            criterion = Restrictions.like(name, '%' + (String) queryParam.getValue() + '%');
                        } else {
                            LOG.error("The value type -- " + queryParam.getValue().getClass().getName()
                                    + " not match the operator:like");
                        }
                    } else if (QueryParam.OPERATOR_ILIKE.equals(operator)) {
                        if (queryParam.getValue() instanceof String) {
                            criterion = Restrictions.ilike(name, queryParam.getValue());
                        } else {
                            LOG.error("The value type -- " + queryParam.getValue().getClass().getName()
                                    + " not match the operator:like");
                        }
                    } else if (QueryParam.OPERATOR_IINCLUDE.equals(operator)) {
                        if (queryParam.getValue() instanceof String) {
                            criterion = Restrictions.ilike(name, '%' + (String) queryParam.getValue() + '%');
                        } else {
                            LOG.error("The value type -- " + queryParam.getValue().getClass().getName()
                                    + " not match the operator:like");
                        }
                    } else if (QueryParam.OPERATOR_IN.equals(operator) && queryParam.getValue() instanceof Collection) {
                    	Collection ins = (Collection) queryParam.getValue();
                    	if (ins.size() > 0) {
	                        criterion = Restrictions.in(name, ins);
                    	} else {
                    		criterion = Expression.sql("1 != 1");
                    	}
                    } else {
                        LOG.error("invalid operator!");
                    }
                }
                if (notFlag) {
                    if (!ansiNull) {
                        criterion = Restrictions.or(Restrictions.not(criterion), Restrictions.isNull(name));
                    } else {
                        criterion = Restrictions.not(criterion);
                    }
                }
            }
        } else {
            // and each
            List<QueryParam> andParams = queryParam.getAndParams();
            if (andParams != null && andParams.size() > 0) {
                Conjunction conjunction = Restrictions.conjunction();
                for (QueryParam innerQueryParam : andParams) {
                    conjunction.add(processParameter(criteria, innerQueryParam, aliasKey));
                }
                criterion = conjunction;
            }
            // or each
            List<QueryParam> orParams = queryParam.getOrParams();
            if (orParams != null && orParams.size() > 0) {
                for (QueryParam innerQueryParam : orParams) {
                    if (criterion == null) {
                        criterion = processParameter(criteria, innerQueryParam, aliasKey);
                    } else {
                        criterion = Restrictions.or(criterion, processParameter(criteria, innerQueryParam, aliasKey));
                    }
                }
            }
            // not each
            List<QueryParam> notParams = queryParam.getNotParams();
            if (notParams != null && notParams.size() > 0) {
                for (QueryParam innerQueryParam : notParams) {
                    if (criterion == null) {
                        criterion = Restrictions.not(processParameter(criteria, innerQueryParam, aliasKey));
                    } else {
                        criterion = Restrictions.and(criterion, Restrictions.not(processParameter(criteria,
                                innerQueryParam, aliasKey)));
                    }
                }
            }
        }
        return criterion == null ? Expression.sql("1 = 1") : criterion;
    }

	/**
	 * inner class for distinct
	 * 
	 */
	@SuppressWarnings("unchecked")
	class PrefixAliasToBeanResultTransformer implements ResultTransformer {
		
		private static final long serialVersionUID = 336710202869401931L;

		private final Class resultClass;

		private Setter[] setters;

		private PropertyAccessor propertyAccessor;

		public PrefixAliasToBeanResultTransformer(Class resultClass) {
			if (resultClass == null)
				throw new IllegalArgumentException("resultClass cannot be null");
			this.resultClass = resultClass;
			propertyAccessor = new ChainedPropertyAccessor(
					new PropertyAccessor[] {
							PropertyAccessorFactory.getPropertyAccessor(
									resultClass, null),
							PropertyAccessorFactory
									.getPropertyAccessor("field") });
		}

		public Object transformTuple(Object[] tuple, String[] aliases) {
			Object result;

			try {
				if (setters == null) {
					setters = new Setter[aliases.length];

					for (int i = 0; i < aliases.length; i++) {
						String alias = aliases[i];

						if (alias != null) {
							if (alias.startsWith(ALIAS_PREFIX)) {
								alias = alias.substring(ALIAS_PREFIX.length());
							}

							setters[i] = propertyAccessor.getSetter(
									resultClass, alias);
						}
					}
				}

				result = resultClass.newInstance();

				for (int i = 0; i < aliases.length; i++) {
					if (setters[i] != null) {
						setters[i].set(result, tuple[i], null);
					}
				}
			} catch (InstantiationException e) {
				throw new HibernateException(
						"Could not instantiate resultclass: "
								+ resultClass.getName());
			} catch (IllegalAccessException e) {
				throw new HibernateException(
						"Could not instantiate resultclass: "
								+ resultClass.getName());
			}

			return result;
		}

		public List transformList(List collection) {
			return collection;
		}

	}

	/**
	 * get primary key name for entity class
	 * 
	 * @param entityClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getIdentityPropertyName(Class entityClass) {
		ClassMetadata classMetadata = getSessionFactory().getClassMetadata(
				entityClass);
		return classMetadata.getIdentifierPropertyName();
	}

	/**
	 * 
	 * @param entityClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Projection getClassProjectionList(Class entityClass) {
		ClassMetadata classMetadata = getSessionFactory().getClassMetadata(
				entityClass);
		// String identityPropertyName =
		// classMetadata.getIdentifierPropertyName();
		String[] properties = classMetadata.getPropertyNames();
		ProjectionList list = Projections.projectionList();
		// list.add(Projections.property(identityPropertyName),
		// identityPropertyName);
		for (int i = 0; i < properties.length; ++i) {
			list.add(Projections.property(properties[i]), ALIAS_PREFIX
					+ properties[i]);
		}
		return list;
	}

	public void clearAlias(String alias) {
		if (aliasesMap != null) {
			aliasesMap.remove(alias);
		}
	}

}
