package com.cicl.frame.workflow.entity;

import java.util.Date;

import com.cicl.frame.core.entity.AbstractEntity;

public class WorkflowDefinition extends AbstractEntity<Long>{
	private String name;
	private Integer version;
	private Integer instances;
	private Integer nodeNumber;
	
	private String definitionXml;
	private String definitionSchema;
	
	private Date createdDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getInstances() {
		return instances;
	}
	public void setInstances(Integer instances) {
		this.instances = instances;
	}

	public Integer getNodeNumber() {
		return nodeNumber;
	}
	public void setNodeNumber(Integer nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	public String getDefinitionXml() {
		return definitionXml;
	}
	public void setDefinitionXml(String definitionXml) {
		this.definitionXml = definitionXml;
	}
	public String getDefinitionSchema() {
		return definitionSchema;
	}
	public void setDefinitionSchema(String definitionSchema) {
		this.definitionSchema = definitionSchema;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Override
	public String getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
