package com.cicl.frame.workflow.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cicl.frame.core.repository.IBaseCrudRepository;
import com.cicl.frame.core.repository.page.Page;
import com.cicl.frame.core.service.AbstractCrudService;
import com.cicl.frame.workflow.Workflowable;
import com.cicl.frame.workflow.dictionary.Constants;
import com.cicl.frame.workflow.entity.TodoItem;
import com.cicl.frame.workflow.entity.WorkflowDefinition;
import com.cicl.frame.workflow.repository.WorkflowRepository;
import com.cicl.frame.workflow.util.WorkFlowUtil;
import com.cicl.frame.workflow.web.form.WorkflowDefinitionSearchForm;

@Transactional
@Service
public class WorkflowService extends AbstractCrudService<WorkflowDefinition, Long> implements Workflowable{
	
	@Autowired
	private WorkflowRepository workflowRepository;
	@Autowired
	private org.springmodules.workflow.jbpm31.JbpmTemplate jbpmTemplate;
	
	private static Log log = LogFactory.getLog(WorkflowService.class);

	public void deployWorkflowDefintion(InputStream defintion, InputStream schema) throws Exception{
		WorkflowDefinition wfd = null;
		JbpmContext ctx = null;
		
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			ProcessDefinition procDef = ProcessDefinition.parseXmlInputStream(defintion);
			ctx.deployProcessDefinition(procDef);
			
			wfd = new WorkflowDefinition();
			wfd.setName(procDef.getName());
			wfd.setVersion(procDef.getVersion());
			defintion.reset();
			wfd.setDefinitionXml(IOUtils.toString(defintion));
			if(schema!=null){
				wfd.setDefinitionSchema(IOUtils.toString(schema));
			}else{
				wfd.setDefinitionSchema(wfd.getDefinitionXml());
			}
			
			wfd.setNodeNumber(procDef.getNodes().size());
			wfd.setInstances(0);
			wfd.setCreatedDate(new Date());
			save(wfd);
		}catch(Exception ex){
			throw ex;
		} finally {
			ctx.close();
		}
	}
	
	public void deployWorkflowDefintion(String definitionXml, String definitionSchema) throws Exception{
		InputStream defenition = IOUtils.toInputStream(definitionXml);
		InputStream schema = IOUtils.toInputStream(definitionSchema);
		deployWorkflowDefintion(defenition, schema);
	}
	
	public Page<WorkflowDefinition> searchForm(WorkflowDefinitionSearchForm searchForm) {
		return workflowRepository.findLastestWorkflowDefinition();
	}
	
	public void startup(Long wdID) throws Exception{
		createInstance(wdID, null);
	}
	
	/**
	 * create a jbpm process with init the businessObject and starter value
	 * @param wfName
	 * @param bussinessObject
	 * @param starter
	 * @throws Exception
	 */
	public void startup(String wfName, String bussinessObject, String ou, String starter, Map<String, Object> customizedVars) throws Exception{
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put(VAR_NAME_BUSINESS_OBJECT, bussinessObject);
		vars.put(VAR_NAME_PROCESS_STARTER, starter);
		vars.put(VAR_NAME_START_OU, ou);
		vars.putAll(customizedVars);
		createInstance(wfName, vars);
	}
	
	public void startup(Long wdID,Map<String, Object> variables) throws Exception{
		createInstance(wdID, variables);
	}
	
	public WorkflowDefinition createInstance(Long wdID,Map<String,Object> variables) throws Exception{
		WorkflowDefinition wfd = load(wdID);
		if(wfd==null) throw new Exception("Workflow Definition with ID[" + wdID + "] does not exists.");
		JbpmContext ctx = null;
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			ProcessInstance pi = ctx.newProcessInstance(wfd.getName());
			
			pi.getContextInstance().addVariables(variables);
			pi.signal();
			
			ctx.save(pi);

			return wfd;
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}
	}
	
	public List<TodoItem> fetchPersonalTodoList(String actotId) throws Exception{
		List<TodoItem> todoItemList = new ArrayList<TodoItem>();
		List<TaskInstance> taskInstances = null;
		JbpmContext ctx = null;
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			taskInstances = ctx.getTaskMgmtSession().findTaskInstances(actotId);
			Token token = null;
			for (TaskInstance ti : taskInstances){
				token = ti.getToken();
				
				TodoItem todoItem = new TodoItem();
				
				todoItem.setId(ti.getId());
				todoItem.setTaskName(ti.getName());
				todoItem.setNodeName(ti.getTask().getTaskNode().getName());
				todoItem.setCreatedDate(ti.getCreate());
				todoItem.setDueDate(ti.getDueDate());
				//todoItem.setFromPerson();
				
				todoItem.setSignalling(ti.isSignalling());
				Token ptoken = token.getParent();
				if(ptoken!=null && ptoken.getNode()!=null){
					todoItem.setFromNode(ptoken.getNode().getName());
				}
				
				List<Transition> transitions = ti.getAvailableTransitions();
				for(Transition t : transitions){
					todoItem.addTransition(t.getName());
				}
				
				todoItem.setVaribles(ti.getVariables());
				todoItem.setActorId(ti.getActorId());
				Set<PooledActor> actors = ti.getPooledActors();
				if(actors.size()>0){
					/** we tread pooledActors as a string which contains all the role and ou **/
					todoItem.setPooledActors(actors.iterator().next().getActorId());
					todoItem.setType(TodoItem.TYPE_MIXED);
				}else{
					todoItem.setType(TodoItem.TYPE_PERSONAL);
				}
				
				todoItemList.add(todoItem);
			}
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}		
		
		return todoItemList;
	}
	

	
	public List<TodoItem> fetchTeamTodoList(Set<String> roles, String ou) throws Exception{
		
		List<TodoItem> todoItemList = new ArrayList<TodoItem>();	
		List<TaskInstance> taskInstances = null;		
		Node node = null;
		Node parentNode = null;
		Token token = null;
		Token parentToken = null;
		JbpmContext ctx = null;
		Session session = null;
		Query query = null;
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			session = ctx.getSession();
			Set<String> mix = new HashSet<String>();
			mix.addAll(roles);
			if(ou!=null && roles!=null){
				for(String role : roles){
					mix.add(ou + "&" + role);
				}
			}
			query = session.createQuery(WorkFlowUtil.getPooledActorIdQueryString(mix, ou));
			
			if (ou!=null){			   
			    query.setString("ou", WorkFlowUtil.getPooledActorIdDesc(ou, Constants.ACTORTYPE_OU));
		    }
			
		    int i = 1;
		    if (mix!=null && mix.size()>0){			   
			    for (String role : mix){
			    	query.setString("actorId"+i, WorkFlowUtil.getPooledActorIdDesc(role, Constants.ACTORTYPE_ROLE));
			    	i ++;
			    }		    					
		    }
		    
		    taskInstances = query.list();
		
			for (TaskInstance ti : taskInstances){
				token = ti.getToken();
//				node = ctx.getToken(token.getId()).getNode();
				node = token.getNode();
				parentToken = token.getParent();
				if (parentToken!=null)
					parentNode = parentToken.getNode();			
				
				TodoItem todoItem = new TodoItem();
				todoItem.setId(ti.getId());
				todoItem.setTaskName(ti.getName());
				todoItem.setNodeName(node.getName());
				todoItem.setCreatedDate(ti.getCreate());
				todoItem.setDueDate(ti.getDueDate());
				todoItem.setSignalling(ti.isSignalling());
				
				if (parentNode!=null)
					todoItem.setFromNode(parentNode.getName());
				//todoItem.setFromPerson(parentToken.getActiveChildren());
				todoItem.setVaribles(ti.getVariables());
				todoItem.setType(TodoItem.TYPE_TEAM);
				
				List<Transition> transitions = ti.getAvailableTransitions();
				for(Transition t : transitions){
					todoItem.addTransition(t.getName());
				}
				
				todoItemList.add(todoItem);
			}
			
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}
		return todoItemList;
	}
	
	@Override
	public IBaseCrudRepository<WorkflowDefinition, Long> getIBaseCrudRepository() {
		// TODO Auto-generated method stub
		return workflowRepository;
	}
	
	public TaskInstance loadTaskInstance(long taskInstanceId) throws Exception{
		JbpmContext ctx = null;
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			TaskInstance ti = ctx.loadTaskInstance(taskInstanceId);
			
			return ctx.loadTaskInstance(taskInstanceId);
		}catch(Exception ex){
			throw ex;
		} finally {
			ctx.close();
		}
	}
	
	public WorkflowDefinition getWorkflowDefinitionByName(String wdName) throws Exception{
		Page<WorkflowDefinition> page = workflowRepository.findLastestWorkflowDefinitionByName(wdName);
		
		List<WorkflowDefinition> list = page.getList();
		WorkflowDefinition wd = null;
		if (list == null || list.size()<=0){
			throw new Exception("The WorkflowDefinition isn't exist.");
		}
		wd = list.get(0);
		return wd;
	}
	
	public Long getWorkflowDefinitionIdByName(String wdName)throws Exception{
		return getWorkflowDefinitionByName(wdName).getId();
	}
	
	public void createInstance(String wdName, Map<String, Object> variables) throws Exception{
		WorkflowDefinition wd = getWorkflowDefinitionByName(wdName);
		createInstance(wd.getId(),variables);
	}
		
	public List<TaskInstance> findTaskInstancesByActorId(String actorId) throws Exception{
		List<TaskInstance> list = null;
	
		JbpmContext ctx = null;
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			list = ctx.getTaskMgmtSession().findTaskInstances(actorId);
			
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}
		return list;
	}
	
	public void submitNext(Long taskInstanceId) throws Exception{
		JbpmContext ctx = null;
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			ctx.getTaskInstance(taskInstanceId).getToken().signal();
			
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}
	}
	
	public void signal(Long taskInstanceId,String leavingTransitionName ) throws Exception{
		JbpmContext ctx = null;
		TaskInstance taskInstance = null;
		try{			
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			taskInstance =  ctx.getTaskInstance(taskInstanceId);			
			taskInstance.end(leavingTransitionName);
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}
	}
	
	public List<String> getLeavingTransitionNamesByTaskInstanceId(Long taskInstanceId) throws Exception{
		JbpmContext ctx = null;
		TaskInstance taskInstance = null;
		List<Transition> leavingTransitions = null;
		List<String> leavingTransitionNames = new ArrayList();
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			taskInstance = ctx.getTaskInstance(taskInstanceId);			
			leavingTransitions = taskInstance.getToken().getNode().getLeavingTransitions();
			if (leavingTransitions!=null){
				for (Transition transition : leavingTransitions){
					leavingTransitionNames.add(transition.getName());
				}
			}
			
		}catch(Exception ex){
			throw ex;
		}
		finally{
			ctx.close();
		}
		
		return leavingTransitionNames;
	}
	

	public Map<String,Object> getVariablesByTaskInstance(TaskInstance ti) throws Exception{
		Map<String,Object> map = null;
		JbpmContext ctx = null;
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			map = ctx.getTaskInstance(ti.getId()).getVariables();
			
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}
		return map;
	}
	
	public boolean containVariables(Long taskInstanceId, String... variables){
		JbpmContext ctx = null;
		boolean result = false;
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			TaskInstance ti = ctx.getTaskInstance(taskInstanceId);
			
			if(ti!=null){
				Set<String> keys = ti.getVariables().keySet();
				for(String key : keys){
					for(String var : variables){
						if(key.equals(var)){
							result = true;
							break;
						}
					}
					if(result) break;
				}
			}
			
		}catch(Exception ex){
			result = false;
		} finally {			
			ctx.close();
		}
		return result;
	}
	
	
	public Map<String, Boolean> checkVariables(Long taskInstanceId, String... variables) {
		JbpmContext ctx = null;
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			TaskInstance ti = ctx.getTaskInstance(taskInstanceId);
			
			if(ti!=null){
				Set<String> keys = ti.getVariables().keySet();
				for(String var : variables) {
					result.put(var, keys.contains(var));
				}
			}
			
		}catch(Exception ex){
			for(String var : variables) {
				result.put(var, false);
			}
		} finally {			
			ctx.close();
		}
		return result;
	}
	
	public void taskTask(String actorId,Long taskInstanceId) throws Exception{
		JbpmContext ctx = null;
	
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			TaskInstance ti = ctx.getTaskInstance(taskInstanceId);
			if (ti == null) 
				throw new Exception("TaskInstance is null.");
			if (ti.getActorId() != null && ti.getActorId().length()>0)
				throw new Exception("ActorId is not empty. It means that someone has taken this task.");
			ti.setActorId(actorId);
			ctx.save(ti);
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}
	}
	
	public void releaseTask(Long taskInstanceId) throws Exception{
		JbpmContext ctx = null;
		
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			TaskInstance ti = ctx.getTaskInstance(taskInstanceId);
			if (ti == null) 
				throw new Exception("TaskInstance is null.");
			if (ti.getActorId() == null || ti.getActorId().length()<=0)
				throw new Exception("ActorId is empty.");
			ti.setActorId(null);
			ctx.save(ti);		
			
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}
	
	}
	
	public void delegateTask(Long taskInstanceId, String targetActorId) throws Exception{
		JbpmContext ctx = null;
		
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			TaskInstance ti = ctx.getTaskInstance(taskInstanceId);
			String orginal = ti.getActorId();
			ti.setActorId(targetActorId);
			ctx.save(ti);		
			
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}
	
	}
	
	public TodoItem fetchTodoItemById(Long taskInstanceId) throws Exception{
		JbpmContext ctx = null;
		try{
			ctx = jbpmTemplate.getJbpmConfiguration().createJbpmContext();
			TaskInstance ti = ctx.getTaskMgmtSession().getTaskInstance(taskInstanceId);
						
			if(ti==null || ti.hasEnded()){
				return null;
			}else{
				Token token = ti.getToken();
				TodoItem todoItem = new TodoItem();
				
				todoItem.setId(ti.getId());
				todoItem.setTaskName(ti.getName());
				todoItem.setNodeName(ti.getTask().getTaskNode().getName());
				todoItem.setCreatedDate(ti.getCreate());
				todoItem.setDueDate(ti.getDueDate());
				todoItem.setSignalling(ti.isSignalling());
				
				Token ptoken = token.getParent();
				if(ptoken!=null && ptoken.getNode()!=null){
					todoItem.setFromNode(ptoken.getNode().getName());
				}
				
				List<Transition> transitions = ti.getAvailableTransitions();
				for(Transition t : transitions){
					todoItem.addTransition(t.getName());
				}
				
				todoItem.setVaribles(ti.getVariables());
				todoItem.setActorId(ti.getActorId());
				Set<PooledActor> actors = ti.getPooledActors();
				
				if(ti.getActorId()!=null){
					if(actors.size()>0){
						/** we treat pooledActors as a string which contains all the role and ou **/
						todoItem.setPooledActors(actors.iterator().next().getActorId());
						todoItem.setType(TodoItem.TYPE_MIXED);
					}else{
						todoItem.setType(TodoItem.TYPE_PERSONAL);
					}
				}else{
					todoItem.setType(TodoItem.TYPE_TEAM);
				}
				
				return todoItem;
			}
		}catch(Exception ex){
			throw ex;
		} finally {			
			ctx.close();
		}
	}
}
