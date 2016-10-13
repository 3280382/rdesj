package com.cicl.frame.workflow.event;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Replace all variables in assignment field (actor_id and pooled-actors) with realtime value.
 * @author LU ZHULIANG
 *
 */
public class AssignmentExpressionExecutingHandler implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		TaskInstance ti = executionContext.getTaskInstance();
		String regex = "(^\\#\\{)(.*)(\\}$)"; //reg expression for variable reference 
		Pattern pattern = Pattern.compile(regex);
		
		String actorId = ti.getActorId();
		if(actorId!=null){
			Matcher matcher = pattern.matcher(actorId);
			if(matcher.find()){
				String varName = matcher.group(2);
				ti.setActorId(ti.getVariable(varName).toString());
			}
		}
		
		// replace START_OU in Pool.
		Set<PooledActor> set = ti.getPooledActors();
		while(set!=null && set.iterator().hasNext()){
			PooledActor pa = set.iterator().next();
			String pools = pa.getActorId();
			if(pools!=null){
				Matcher matcher = pattern.matcher(pools);
				if(matcher.find()){
					String varName = matcher.group(2);
					pa.setActorId(ti.getVariable(varName).toString());
				}
			}
		}
			
		ti.setPooledActors(set);
		
		executionContext.getJbpmContext().save(ti);
	}
}
