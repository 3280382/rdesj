package com.cicl.frame.workflow.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import com.cicl.frame.core.repository.hibernate.AbstractHibernateRepository;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.workflow.entity.WorkflowDefinition;

@Repository
public class WorkflowRepository extends AbstractHibernateRepository<WorkflowDefinition, Long> {
	public Page<WorkflowDefinition> searchAll() {
		List<WorkflowDefinition> wdList = search(null);

		for(WorkflowDefinition wd : wdList){
			String sql = "select count(1) as amount from jbpm_processinstance pi, jbpm_processdefinition pd where pi.processdefinition_ = pd.id_ and pd.name_='" + wd.getName() +"' and pd.version_ = " + wd.getVersion();
			Integer instanceAmount = (Integer)findValueBySQL(sql, "amount", Hibernate.INTEGER);
			wd.setInstances(instanceAmount.intValue());
		}
		
		return new Page<WorkflowDefinition>(0,0,wdList.size(), wdList);
	}
	
	public Page<WorkflowDefinition> findLastestWorkflowDefinition() {
		String expql = "FROM " + WorkflowDefinition.class.getName() + " t ORDER BY t.version DESC";
		List<WorkflowDefinition> wdList = findByExpQL(expql, false, 0, -1);
		
		Map<String, WorkflowDefinition> lastest = new HashMap<String, WorkflowDefinition>();
		for(WorkflowDefinition wd : wdList){
			String name = wd.getName();
			WorkflowDefinition previous = lastest.get(name);
			if(previous==null || previous.getVersion() < wd.getVersion()){
				String sql = "select count(1) as amount from jbpm_processinstance pi, jbpm_processdefinition pd where pi.processdefinition_ = pd.id_ and pd.name_='" + wd.getName() +"' and pd.version_ = " + wd.getVersion();
				Integer instanceAmount = (Integer)findValueBySQL(sql, "amount", Hibernate.INTEGER);
				wd.setInstances(instanceAmount.intValue());
				lastest.put(name, wd);
			}
		}
		
		wdList = new ArrayList<WorkflowDefinition>(lastest.values());
		return new Page<WorkflowDefinition>(0,0,wdList.size(), wdList);
	}
	
	public Page<WorkflowDefinition> findLastestWorkflowDefinitionByName(String wdName) {	
		String expql = "FROM " + WorkflowDefinition.class.getName() + " t WHERE t.name = '"+wdName+"' ORDER BY t.version DESC";
		List<WorkflowDefinition> wdList = findByExpQL(expql, false, 0, -1);
		
		Map<String, WorkflowDefinition> lastest = new HashMap<String, WorkflowDefinition>();
		for(WorkflowDefinition wd : wdList){
			String name = wd.getName();
			WorkflowDefinition previous = lastest.get(name);
			if(previous==null || previous.getVersion() < wd.getVersion()){
				String sql = "select count(1) as amount from jbpm_processinstance pi, jbpm_processdefinition pd where pi.processdefinition_ = pd.id_ and pd.name_='" + wd.getName() +"' and pd.version_ = " + wd.getVersion();
				Integer instanceAmount = (Integer)findValueBySQL(sql, "amount", Hibernate.INTEGER);
				wd.setInstances(instanceAmount.intValue());
				lastest.put(name, wd);
			}
		}
		
		wdList = new ArrayList<WorkflowDefinition>(lastest.values());
		return new Page<WorkflowDefinition>(0,0,wdList.size(), wdList);
	}
}
