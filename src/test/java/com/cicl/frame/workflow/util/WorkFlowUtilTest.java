package com.cicl.frame.workflow.util;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.cicl.frame.workflow.dictionary.Constants;

public class WorkFlowUtilTest {
/*
	@Test
	public void testGetPooledActorIdDescStringInt() {
		String actorId = "admin";
		assertEquals("%role('admin')%",WorkFlowUtil.getPooledActorIdDesc(actorId, Constants.ACTORTYPE_ROLE));
		
	}
	
	private static final String SQL_FindTaskInstancesByPoolActorIds =   "select distinct ti " +
	" from org.jbpm.taskmgmt.exe.PooledActor pooledActor " +
	" join pooledActor.taskInstances ti " +
	" where ti.actorId is null " + 
	" and ti.end is null " + 
	" and ti.isCancelled = false and ";

	@Test
	public void testGetPooledActorIdDescSetOfStringInt() {
		Set<String> roles = new HashSet<String>();
		
		assertEquals("",WorkFlowUtil.getPooledActorIdQueryString(roles, null));
		
		roles.add("admin");
		roles.add("user");
		assertEquals(SQL_FindTaskInstancesByPoolActorIds+"(pooledActor.actorId like :actorId1 or pooledActor.actorId like :actorId2)",WorkFlowUtil.getPooledActorIdQueryString(roles, null));
	}
*/
}
