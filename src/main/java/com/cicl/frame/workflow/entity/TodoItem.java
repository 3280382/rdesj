package com.cicl.frame.workflow.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TodoItem {
	public static final int TYPE_PERSONAL = 1;
	public static final int TYPE_TEAM = 2;
	public static final int TYPE_MIXED = 3;
	
	private Long id;	
	private String nodeName;
	private String taskName;
	private String fromNode;
	private String fromPerson;
	private Date createdDate;
	private Date dueDate;
	private int type;
	
	private boolean signalling; // if true, which mean can only execute task but can not submit the node.
	private String actorId;
	private String pooledActors;
	
	private Map<String, Object> varibles;
	private List<String> availableTransitions = new ArrayList<String>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getFromNode() {
		return fromNode;
	}
	public void setFromNode(String fromNode) {
		this.fromNode = fromNode;
	}
	public String getFromPerson() {
		return fromPerson;
	}
	public void setFromPerson(String fromPerson) {
		this.fromPerson = fromPerson;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Map<String, Object> getVaribles() {
		return varibles;
	}
	public void setVaribles(Map<String, Object> varibles) {
		this.varibles = varibles;
	}
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public String getPooledActors() {
		return pooledActors;
	}
	public void setPooledActors(String pooledActors) {
		this.pooledActors = pooledActors;
	}
	public List<String> getAvailableTransitions() {
		return availableTransitions;
	}
	public void setAvailableTransitions(List<String> availableTransitions) {
		this.availableTransitions = availableTransitions;
	}
	
	public void addTransition(String name){
		this.availableTransitions.add(name);
	}
	public boolean isSignalling() {
		return signalling;
	}
	public void setSignalling(boolean signalling) {
		this.signalling = signalling;
	}
}
