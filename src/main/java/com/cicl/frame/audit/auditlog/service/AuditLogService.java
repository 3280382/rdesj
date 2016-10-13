/** 
 * @(#)AuditLogService.java 1.0.0 2011-01-12 10:40:38
 *  
 * Copyright 2011 CTIT Co.,Ltd.  All rights reserved. 
 * CTIT PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.   
 */
package com.cicl.frame.audit.auditlog.service;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cicl.frame.audit.auditlog.entity.AuditLog;
import com.cicl.frame.audit.auditlog.repository.AuditLogRepository;
import com.cicl.frame.audit.auditlog.web.form.AuditLogSearchForm;
import com.cicl.frame.core.entity.Persistable;
import com.cicl.frame.core.repository.IBaseCrudRepository;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.repository.query.QueryObject;
import com.cicl.frame.core.repository.query.QueryObjectFactory;
import com.cicl.frame.core.repository.query.QueryParam;
import com.cicl.frame.core.service.AbstractService;
import com.cicl.frame.core.web.view.json.serializer.CustomObjectMapper;
import com.cicl.frame.security.sysuser.util.SpringSecurityUtils;

/**
 * Class AuditLogService
 * 
 * @author cdzerg@gmail.com
 * @version $Revision:1.0.0, $Date: 2011-4-22 04:46:32
 */

// TODO Define a new transaction Manage for AuditLogService? when source method
// rollback, but methods in this class will commit?
@Transactional
@Service
public class AuditLogService extends AbstractService<AuditLog, Long> {
	@Autowired
	private AuditLogRepository repository;
	@Autowired
	private CustomObjectMapper mapper;

	public AuditLogService() {
		super();
	}

	public AuditLogService(AuditLogRepository repository) {
		super();
		this.repository = repository;
	}

	// @Override
	@Override
	public IBaseCrudRepository<AuditLog, Long> getIBaseCrudRepository() {
		return repository;
	}

	public void saveAudit(Persistable target, String operation) {
		AuditLog auditLog = new AuditLog();
		// operation user info
		UserDetails user = SpringSecurityUtils.getCurrentUserDetails();
		if (user == null) {
			//auditLog.setUserId(null);
		} else {
			//if(sysUser.getId()!=null){
			//	auditLog.setUserId(sysUser.getId().toString());
			//}
			//auditLog.setLoginName(user.getLoginName());
			auditLog.setUsername(user.getUsername());
		}
		auditLog.setUserIp(SpringSecurityUtils.getCurrentUserIp());

		if (target == null) {
			// operation target info
			auditLog.setTargetId(null);
		} else {
			// operation target info
			if (target.getId() != null) {
				auditLog.setTargetId(target.getId().toString());
			}
			auditLog.setTargetEntityType(target.getEntityType());

			// operation info
			auditLog.setOpType(operation);

			// snapshot render by json string
			// auditLog.setTargetSnapshot(getSnapshot(target));
		}

		repository.save(auditLog);
	}

	public void saveAudit(List<Persistable> targets, String operation) {
		// TODO: to prevent repeat loading User info ...
		for (Persistable target : targets) {
			saveAudit(target, operation);
		}
	}

	@SuppressWarnings("unused")
	private String getSnapshot(Persistable target) {
		try {
			int maxLen = 4000;
			String jsonString = mapper.writeValueAsString(target);

			if (jsonString.getBytes().length > maxLen)
				jsonString = jsonString.substring(0, maxLen);

			return jsonString;
		} catch (JsonGenerationException e) {
			return "JsonGenerationException";
		} catch (JsonMappingException e) {
			return "JsonMappingException";
		} catch (IOException e) {
			return "IOException";
		}
	}

	public Page<AuditLog> searchForm(AuditLogSearchForm auditLogSearchForm) {
		AuditLog auditLog = null;

		QueryObject<AuditLog> queryObject = QueryObjectFactory.createQueryObject(AuditLog.class);
		QueryParam queryParam = new QueryParam();

		if (auditLogSearchForm != null) {
			auditLog = auditLogSearchForm.getAuditLog();
		}

		if (auditLog != null) {
			if (auditLog.getUserId() != null) {
				queryParam.and(new QueryParam("userId", QueryParam.OPERATOR_EQ, auditLog.getUserId()));
			}
			if (auditLog.getUsername() != null) {
				queryParam
						.and(new QueryParam("username", QueryParam.OPERATOR_LIKE, "%" + auditLog.getUsername() + "%"));

			}
			if (auditLog.getLoginName() != null) {
				queryParam.and(new QueryParam("loginName", QueryParam.OPERATOR_LIKE, "%" + auditLog.getLoginName()
						+ "%"));

			}
			if (auditLog.getUserIp() != null) {
				queryParam.and(new QueryParam("userIp", QueryParam.OPERATOR_LIKE, "%" + auditLog.getUserIp() + "%"));

			}
			if (auditLog.getTargetId() != null) {
				queryParam.and(new QueryParam("targetId", QueryParam.OPERATOR_EQ, auditLog.getTargetId()));
			}
			if (auditLog.getTargetEntityType() != null) {
				queryParam.and(new QueryParam("targetEntityType", QueryParam.OPERATOR_EQ, auditLog
						.getTargetEntityType()));
			}
			if (auditLog.getTargetName() != null) {
				queryParam.and(new QueryParam("targetName", QueryParam.OPERATOR_LIKE, "%" + auditLog.getTargetName()
						+ "%"));

			}
			if (auditLog.getTargetDesc() != null) {
				queryParam.and(new QueryParam("targetDesc", QueryParam.OPERATOR_LIKE, "%" + auditLog.getTargetDesc()
						+ "%"));

			}
			if (auditLog.getTargetSnapshot() != null) {
				queryParam.and(new QueryParam("targetSnapshot", QueryParam.OPERATOR_LIKE, "%"
						+ auditLog.getTargetSnapshot() + "%"));

			}
			if (auditLog.getOpType() != null) {
				queryParam.and(new QueryParam("opType", QueryParam.OPERATOR_LIKE, "%" + auditLog.getOpType() + "%"));

			}
			if (auditLog.getOpTime() != null) {
				queryParam.and(new QueryParam("opTime", QueryParam.OPERATOR_EQ, auditLog.getOpTime()));
			}
			if (auditLog.getOpDesc() != null) {
				queryParam.and(new QueryParam("opDesc", QueryParam.OPERATOR_LIKE, "%" + auditLog.getOpDesc() + "%"));

			}
		}

		queryObject.setQueryParam(queryParam);
		Page<AuditLog> page = this.search(queryObject, true, auditLogSearchForm);

		return page;
	}
}
