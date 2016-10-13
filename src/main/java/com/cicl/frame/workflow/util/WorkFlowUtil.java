package com.cicl.frame.workflow.util;

import java.util.HashSet;
import java.util.Set;

import com.cicl.frame.workflow.dictionary.Constants;

public class WorkFlowUtil {
	public static final String getPooledActorIdDesc(String actorId,
			int actorType) {
		if (actorId == null || actorId.length() <= 0)
			return "";
		String pooledActorIdDesc = null;
		switch (actorType) {
		case Constants.ACTORTYPE_ROLE:
			pooledActorIdDesc = "%role('" + actorId + "')%";
			break;
		case Constants.ACTORTYPE_OU:
			pooledActorIdDesc = "%ou('" + actorId + "')%";
			break;
		default:
			break;
		}
		return pooledActorIdDesc;
	}

	public static final Set<String> getPooledActorIdDescs(Set<String> actorIds,
			int actorType) {
		if (actorIds == null)
			return new HashSet<String>();
		Set<String> pooledActorIdDescs = new HashSet<String>();
		for (String actorId : actorIds) {
			pooledActorIdDescs.add(getPooledActorIdDesc(actorId, actorType));
		}
		return pooledActorIdDescs;
	}
	
	private static final String SQL_FindTaskInstancesByPoolActorIds_Prefix =   "select distinct ti " +
	" from org.jbpm.taskmgmt.exe.PooledActor pooledActor " +
	" join pooledActor.taskInstances ti " +
	" where ti.actorId is null " + 
	" and ti.end is null " + 
	" and ti.isCancelled = false and ";
	private static final String SQL_PooledActorId_Like = "pooledActor.actorId like ";

	public static final String getPooledActorIdQueryString(Set<String> actorIds, String ou) {
		if (actorIds == null || actorIds.size() <= 0)
			return "";
		StringBuilder pooledActorIdDesc = new StringBuilder("");

		int i = 1;
		for (String actorId : actorIds) {
			pooledActorIdDesc.append(SQL_PooledActorId_Like).append(":actorId")
					.append(i).append(" or ");
			i++;
		}
		
		if(ou!=null){
			pooledActorIdDesc.append(SQL_PooledActorId_Like).append(":ou").append(" or ");
		}
		
		pooledActorIdDesc.delete(pooledActorIdDesc.length() - " or ".length(),
				pooledActorIdDesc.length());
		
		pooledActorIdDesc = new StringBuilder(SQL_FindTaskInstancesByPoolActorIds_Prefix).append("(").append(pooledActorIdDesc).append(")");

		return pooledActorIdDesc.toString();
	}

}
