function JbpmFlowModelConverter() {
}

//
JbpmFlowModelConverter.convertModelToXML = function(model) {
	var doc = XMLDocument.newDomcument();

	// root
	var workflowProcessNode = doc
			.createElement(JbpmFlowModelConverter.NODE_ROOT);
	workflowProcessNode.setAttribute(
			JbpmFlowModelConverter.ATTR_JBPM_PROCESS_NAME, model.getName());
	workflowProcessNode.setAttribute(
			JbpmFlowModelConverter.ATTR_JBPM_PROCESS_XMLNS,
			"urn:jbpm.org:jpdl-3.1");
	
	// event
	
	// swimlane
	for(var i=0; i<model.swimlanes.length; i++){
		var swimlane = model.swimlanes[i];
		var swimlaneNode = doc.createElement(JbpmFlowModelConverter.NODE_ROOT_SWIMLANE);
		swimlaneNode.setAttribute("name", swimlane.name);
		var assignmentNode = doc.createElement("assignment");
		assignmentNode.setAttribute("class", swimlane.assignment);
		swimlaneNode.appendChild(assignmentNode);
		workflowProcessNode.appendChild(swimlaneNode);
	}
	
	doc.documentElement = workflowProcessNode;

	JbpmFlowModelConverter.setProcessProperties(model, doc);

	// NODES

	var metaNodeModels = model.getMetaNodeModels();
	for ( var i = 0; i < metaNodeModels.size(); i++) {
		var metaNodeModel = metaNodeModels.get(i);
		var activitieNode = JbpmFlowModelConverter.convertMetaNodeModelToXML(
				metaNodeModel, doc);
		if (activitieNode) {
			workflowProcessNode.appendChild(activitieNode);
		}
	}
	
	
	//
	return doc;
};

JbpmFlowModelConverter.setProcessProperties = function(model, doc) {
	var propertyNode = null;
	var action = model.action;

	for (p in action) {
		if (action[p] != "") {
			propertyNode = doc
					.createElement(JbpmFlowModelConverter.NODE_JBPM_PROCESS_EVENT);
			propertyNode.setAttribute(
					JbpmFlowModelConverter.ATTR_JBPM_PROCESS_EVENT_TYPE, p
							.replace("_", "-"));
			var actionNode = doc
					.createElement(JbpmFlowModelConverter.NODE_JBPM_ACTION);
			actionNode.setAttribute(
					JbpmFlowModelConverter.ATTR_JBPM_ACTION_CLASS, action[p]);
			propertyNode.appendChild(actionNode);
			doc.documentElement.appendChild(propertyNode);
		}
	}
};

JbpmFlowModelConverter.convertMetaNodeModelToXML = function(metaNodeModel, doc) {

	if (metaNodeModel.jbpmtype == MetaNodeModel.TYPE_JBPM_TASK_NODE) {
		return JbpmFlowModelConverter.convertTaskNodeModelToXML(metaNodeModel,
				doc);
	}
	if (metaNodeModel.jbpmtype == MetaNodeModel.TYPE_JBPM_DECISION) {
		return JbpmFlowModelConverter.convertDecisionNodeModelToXML(
				metaNodeModel, doc);
	}
	if (metaNodeModel.jbpmtype == MetaNodeModel.TYPE_JBPM_START_STATE) {
		return JbpmFlowModelConverter.convertStartNodeModelToXML(metaNodeModel,
				doc);
	}

	if (metaNodeModel.jbpmtype == MetaNodeModel.TYPE_JBPM_END_STATE) {
		return JbpmFlowModelConverter.convertEndNodeModelToXML(metaNodeModel,
				doc);
	}
	if (metaNodeModel.jbpmtype == MetaNodeModel.TYPE_JBPM_NODE) {
		return JbpmFlowModelConverter.convertNodeModelToXML(metaNodeModel, doc);
	}
	return null;
};

/**
 * convert start node
 */
JbpmFlowModelConverter.convertStartNodeModelToXML = function(metaNodeModel, doc) {
	var startNode = doc.createElement(metaNodeModel.jbpmtype);
	startNode.setAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_NAME,
			metaNodeModel.getText());

	var action = JbpmFlowModelConverter.convertActionPropertyToXML(
			metaNodeModel, doc);
	if (action)
		startNode.appendChild(action);

	var exceptionHandler = JbpmFlowModelConverter
			.convertExceptionhandlerPropertyToXML(metaNodeModel, doc);
	if (exceptionHandler)
		startNode.appendChild(exceptionHandler);

	var transitionModels = metaNodeModel.getFroms();
	for ( var i = 0; i < transitionModels.size(); i++) {
		var transitionModel = transitionModels.get(i);
		var transitionNode = JbpmFlowModelConverter
				.convertTransitionModelToXML(transitionModel, doc);
		if (transitionNode)
			startNode.appendChild(transitionNode);
	}

	return startNode;
};

JbpmFlowModelConverter.convertEndNodeModelToXML = function(metaNodeModel, doc) {
	var endNode = doc.createElement(metaNodeModel.jbpmtype);
	endNode.setAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_NAME,
			metaNodeModel.getText());

	var action = JbpmFlowModelConverter.convertActionPropertyToXML(
			metaNodeModel, doc);
	if (action)
		endNode.appendChild(action);

	var exceptionHandler = JbpmFlowModelConverter
			.convertExceptionhandlerPropertyToXML(metaNodeModel, doc);
	if (exceptionHandler)
		endNode.appendChild(exceptionHandler);

	var transitionModels = metaNodeModel.getFroms();
	for ( var i = 0; i < transitionModels.size(); i++) {
		var transitionModel = transitionModels.get(i);
		var transitionNode = JbpmFlowModelConverter
				.convertTransitionModelToXML(transitionModel, doc);
		if (transitionNode)
			endNode.appendChild(transitionNode);
	}

	return endNode;
};

/**
 * convert decision node
 */
JbpmFlowModelConverter.convertDecisionNodeModelToXML = function(metaNodeModel,
		doc) {
	var decisionNode = doc.createElement(metaNodeModel.jbpmtype);
	decisionNode.setAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_NAME,
			metaNodeModel.getText());
	decisionNode.setAttribute(
			JbpmFlowModelConverter.ATTR_JBPM_DECISION_EXPRESSION, metaNodeModel
					.getCondition().conditionExpression);

	var transitionModels = metaNodeModel.getFroms();
	for ( var i = 0; i < transitionModels.size(); i++) {
		var transitionModel = transitionModels.get(i);
		var transitionNode = JbpmFlowModelConverter
				.convertTransitionModelToXML(transitionModel, doc);
		if (transitionNode)
			decisionNode.appendChild(transitionNode);
	}

	return decisionNode;
};

JbpmFlowModelConverter.convertTransitionModelToXML = function(transitionModel,
		doc) {
	var transitionNode = doc
			.createElement(JbpmFlowModelConverter.NODE_JBPM_TRANSITION);

	//
	transitionNode.setAttribute(
			JbpmFlowModelConverter.ATTR_JBPM_TRANSITION_NAME, transitionModel
					.getText());
	transitionNode.setAttribute(JbpmFlowModelConverter.ATTR_JBPM_TRANSITION_TO,
			transitionModel.getToMetaNodeModel().getText());

	//
	return transitionNode;
};

JbpmFlowModelConverter.convertActionPropertyToXML = function(metaNodeModel, doc) {
	var actionNode = null;
	if (metaNodeModel.jbpmtype == MetaNodeModel.TYPE_JBPM_NODE) {

		if (metaNodeModel.getAction().actionClass == "")
			return null;

		actionNode = doc.createElement(JbpmFlowModelConverter.NODE_JBPM_ACTION);

		//
		actionNode.setAttribute(JbpmFlowModelConverter.ATTR_JBPM_ACTION_CLASS,
				metaNodeModel.getAction().actionClass);
		var variables = metaNodeModel.getAction().variables;
		for (i = 0; i < variables.length; i++) {
			var variableNode = doc.createElement(variables[i][0]);
			if (variables[i][1].isList) {
				for ( var j = 0; j < variables[i][1].length; j++) {
					var elementNode = doc
							.createElement(JbpmFlowModelConverter.NODE_JBPM_ELEMENT);
					var cdataNode = doc.createCDATASection(variables[i][1][j]);
					elementNode.appendChild(cdataNode);
					variableNode.appendChild(elementNode);
				}
			} else {
				var cdataNode = doc.createCDATASection(variables[i][1]);
				variableNode.appendChild(cdataNode);
				// variableNode.text = variables[i][1];
			}
			actionNode.appendChild(variableNode);
		}

	}
	return actionNode;
};

JbpmFlowModelConverter.convertExceptionhandlerPropertyToXML = function(
		metaNodeModel, doc) {
	var actionNode = null;
	var exceptionhandlerNode = null;
	if (metaNodeModel.jbpmtype == MetaNodeModel.TYPE_JBPM_NODE) {

		if (metaNodeModel.getExceptionhandler().actionClass == "")
			return null;

		exceptionhandlerNode = doc
				.createElement(JbpmFlowModelConverter.NODE_JBPM_EXCEPTIONHANDLER);
		actionNode = doc.createElement(JbpmFlowModelConverter.NODE_JBPM_ACTION);

		//
		actionNode.setAttribute(JbpmFlowModelConverter.ATTR_JBPM_ACTION_CLASS,
				metaNodeModel.getExceptionhandler().actionClass);
		var variables = metaNodeModel.getExceptionhandler().variables;
		for (i = 0; i < variables.length; i++) {
			var variableNode = doc.createElement(variables[i][0]);
			variableNode.text = variables[i][1];
			actionNode.appendChild(variableNode);
		}
		exceptionhandlerNode.appendChild(actionNode);

	}
	return exceptionhandlerNode;
};

/**
 * convert task node model to xml
 */
JbpmFlowModelConverter.convertTaskNodeModelToXML = function(metaNodeModel, doc) {
	var taskNode = doc.createElement(metaNodeModel.jbpmtype);
	taskNode.setAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_NAME,
			metaNodeModel.getText());
	
	taskNode.setAttribute("signal", metaNodeModel.signal ? metaNodeModel.signal : "last");
	taskNode.setAttribute("create-tasks", metaNodeModel.createTasksFlag ? metaNodeModel.createTasksFlag : "true");
	taskNode.setAttribute("end-tasks", metaNodeModel.endTasksFlag ? metaNodeModel.endTasksFlag : "false");
	
	// add node event to task;
	if (metaNodeModel.getEvent().nodeEnter != "") {
		var eventNode = doc
				.createElement(JbpmFlowModelConverter.NODE_JBPM_EVENT);
		eventNode.setAttribute(JbpmFlowModelConverter.NODE_JBPM_EVENT_TYPE,
				"node-enter");
		var eventHandlerNode = doc
				.createElement(JbpmFlowModelConverter.NODE_JBPM_ACTION);
		eventHandlerNode.setAttribute(
				JbpmFlowModelConverter.ATTR_JBPM_ACTION_CLASS, metaNodeModel
						.getEvent().nodeEnter);
		eventNode.appendChild(eventHandlerNode);
		taskNode.appendChild(eventNode);
	}
	if (metaNodeModel.getEvent().nodeLeave != "") {
		var eventNode = doc
				.createElement(JbpmFlowModelConverter.NODE_JBPM_EVENT);
		eventNode.setAttribute(JbpmFlowModelConverter.NODE_JBPM_EVENT_TYPE,
				"node-leave");
		var eventHandlerNode = doc
				.createElement(JbpmFlowModelConverter.NODE_JBPM_ACTION);
		eventHandlerNode.setAttribute(
				JbpmFlowModelConverter.ATTR_JBPM_ACTION_CLASS, metaNodeModel
						.getEvent().nodeLeave);
		eventNode.appendChild(eventHandlerNode);
		taskNode.appendChild(eventNode);
	}
	if (metaNodeModel.getEvent().beforeSignal != "") {
		var eventNode = doc
				.createElement(JbpmFlowModelConverter.NODE_JBPM_EVENT);
		eventNode.setAttribute(JbpmFlowModelConverter.NODE_JBPM_EVENT_TYPE,
				"before-signal");
		var eventHandlerNode = doc
				.createElement(JbpmFlowModelConverter.NODE_JBPM_ACTION);
		eventHandlerNode.setAttribute(
				JbpmFlowModelConverter.ATTR_JBPM_ACTION_CLASS, metaNodeModel
						.getEvent().beforeSignal);
		eventNode.appendChild(eventHandlerNode);
		taskNode.appendChild(eventNode);
	}
	if (metaNodeModel.getEvent().afterSignal != "") {
		var eventNode = doc
				.createElement(JbpmFlowModelConverter.NODE_JBPM_EVENT);
		eventNode.setAttribute(JbpmFlowModelConverter.NODE_JBPM_EVENT_TYPE,
				"after-signal");
		var eventHandlerNode = doc
				.createElement(JbpmFlowModelConverter.NODE_JBPM_ACTION);
		eventHandlerNode.setAttribute(
				JbpmFlowModelConverter.ATTR_JBPM_ACTION_CLASS, metaNodeModel
						.getEvent().afterSignal);
		eventNode.appendChild(eventHandlerNode);
		taskNode.appendChild(eventNode);
	}

	for (i = 0; i < metaNodeModel.getTasks().length; i++) {
		var task = metaNodeModel.getTasks()[i];
		var taskEl = doc.createElement(JbpmFlowModelConverter.NODE_JBPM_TASK);
		taskEl.setAttribute(JbpmFlowModelConverter.ATTR_JBPM_TASK_NAME,
				task.taskName);

		// add description attribute
		taskEl.setAttribute(JbpmFlowModelConverter.ATTR_JBPM_TASK_BLOCKING,
				task.blocking ? task.blocking : "false");

		taskEl.setAttribute(JbpmFlowModelConverter.ATTR_JBPM_TASK_SIGNALLING,
				task.signalling ? task.signalling : "true");
		
		taskEl.setAttribute(JbpmFlowModelConverter.ATTR_JBPM_TASK_PRIORITY,
				task.priority ? task.priority : "normal");
		
		taskEl.setAttribute(JbpmFlowModelConverter.ATTR_JBPM_TASK_DESCRIPTION,
				task.description ? task.description : "");
		
		// add due-date attribute
		if (task.duedate) {
			taskEl.setAttribute(JbpmFlowModelConverter.ATTR_JBPM_TASK_DUEDATE,
					task.duedate);
		}

		// add swimlane
		if (task.swimlane) {
			taskEl.setAttribute(JbpmFlowModelConverter.ATTR_JBPM_TASK_SWIMLANE,
					task.swimlane);
		}
		
		// add event node
		var events = task.event;
		var eventArray = new Array(new Array("task-create", events.create),
				new Array("task-assign", events.assign), new Array(
						"task-start", events.start), new Array("task-end",
						events.end));
		for (j = 0; j < eventArray.length; j++) {
			if (eventArray[j][1] == null || eventArray[j][1] == "") {
				continue;
			}
			var eventNode = doc
					.createElement(JbpmFlowModelConverter.NODE_JBPM_EVENT);
			eventNode.setAttribute(JbpmFlowModelConverter.NODE_JBPM_EVENT_TYPE,
					eventArray[j][0]);
			var eventHandlerNode = doc
					.createElement(JbpmFlowModelConverter.NODE_JBPM_ACTION);
			eventHandlerNode.setAttribute(
					JbpmFlowModelConverter.ATTR_JBPM_ACTION_CLASS,
					eventArray[j][1]);
			eventNode.appendChild(eventHandlerNode);
			taskEl.appendChild(eventNode);
		}

		// add assignment node
		 var assignmentNode = doc.createElement(JbpmFlowModelConverter.NODE_JBPM_TASK_ASSIGNMENT);
		 var assignment = task.assignment; 
		 if(assignment.actorId != null && assignment.actorId != ""){
			 assignmentNode.setAttribute(JbpmFlowModelConverter.NODE_JBPM_TASK_ASSIGNMENT_ACTORID,assignment.actorId);
		 }
		 if(assignment.pooledActors != null && assignment.pooledActors != ""){
			 assignmentNode.setAttribute(JbpmFlowModelConverter.NODE_JBPM_TASK_ASSIGNMENT_POOLEDACTORS,assignment.pooledActors); 
		 }
		 
		 taskEl.appendChild(assignmentNode);
		 
		// add controller node
		var controllerNode = doc
				.createElement(JbpmFlowModelConverter.NODE_JBPM_TASK_CONTROLLER);
		var variables = task.controller.variables;

		for (j = 0; j < variables.length; j++) {
			var variable = variables[j];
			var variableNode = doc
					.createElement(JbpmFlowModelConverter.NODE_JBPM_TASK_CONTROLLER_VARIABLE);

			variableNode
					.setAttribute(
							JbpmFlowModelConverter.ATTR_JBPM_TASK_CONTROLLER_VARIABLE_NAME,
							variable.name);
			variableNode
					.setAttribute(
							JbpmFlowModelConverter.ATTR_JBPM_TASK_CONTROLLER_VARIABLE_MAPPED_NAME,
							variable.mappedName);

			var access = "";
			if (variable.read) {
				access += access.length > 0 ? ",read" : "read";
			}
			if (variable.write) {
				access += access.length > 0 ? ",write" : "write";
			}

			if (variable.required) {
				access += access.length > 0 ? ",required" : "required";
			}

			variableNode
					.setAttribute(
							JbpmFlowModelConverter.ATTR_JBPM_TASK_CONTROLLER_VARIABLE_ACCESS,
							access);
			controllerNode.appendChild(variableNode);
		}
		taskEl.appendChild(controllerNode);

		taskNode.appendChild(taskEl);
	}

	// add transition
	var transitionModels = metaNodeModel.getFroms();
	for ( var i = 0; i < transitionModels.size(); i++) {
		var transitionModel = transitionModels.get(i);
		var transitionNode = JbpmFlowModelConverter
				.convertTransitionModelToXML(transitionModel, doc);
		if (transitionNode)
			taskNode.appendChild(transitionNode);
	}
	return taskNode;

};

//
JbpmFlowModelConverter.convertXMLToModel = function(doc, initModel) {
	if (!doc) {
		return null;
	}
	var model = initModel;
	if (!model) {
		model = new XiorkFlowModel();
	}

	//
	var activitieNodes = doc.getElementsByTagName("Activity");
	if (activitieNodes.length == 0) {
		activitieNodes = doc.getElementsByTagName("Activitie");
	}
	for ( var i = 0; i < activitieNodes.length; i++) {
		var activitieNode = activitieNodes[i];
		var metaNodeModel = JbpmFlowModelConverter
				.convertXMLToMetaNodeModel(activitieNode);
		model.addMetaNodeModel(metaNodeModel);
	}

	//
	var transitionNodes = doc.getElementsByTagName("Transition");
	for ( var i = 0; i < transitionNodes.length; i++) {
		var transitionNode = transitionNodes[i];
		var transitionModel = JbpmFlowModelConverter
				.convertXMLToTransitionModel(transitionNode, model);
		model.addTransitionModel(transitionModel);
	}

	//
	return model;
};

JbpmFlowModelConverter.convertXMLToMetaNodeModel = function(node) {
	var metaNodeModel = null;
	//
	var type = node.getAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_TYPE);
	switch (type) {
	case MetaNodeModel.TYPE_NODE:
		metaNodeModel = new NodeModel();
		break;
	case MetaNodeModel.TYPE_START_NODE:
		metaNodeModel = new StartNodeModel();
		break;
	case MetaNodeModel.TYPE_END_NODE:
		metaNodeModel = new EndNodeModel();
		break;
	case MetaNodeModel.TYPE_FORK_NODE:
		metaNodeModel = new ForkNodeModel();
		break;
	case MetaNodeModel.TYPE_JOIN_NODE:
		metaNodeModel = new JoinNodeModel();
		break;
	case MetaNodeModel.TYPE_DECISION_NODE:
		metaNodeModel = new DecisionNodeModel();
		break;
	}
	if (!metaNodeModel) {
		return null;
	}

	//
	var id = eval(node.getAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_ID));
	metaNodeModel.setID(id);

	//
	var name = node.getAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_NAME);
	metaNodeModel.setText(name);

	//
	var xCoordinate = eval(node
			.getAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_X_COORD));
	var yCoordinate = eval(node
			.getAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_Y_COORD));
	metaNodeModel.setPosition(new Point(xCoordinate, yCoordinate));

	//
	var width = eval(node
			.getAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_WIDTH));
	var height = eval(node
			.getAttribute(JbpmFlowModelConverter.ATTR_ACTIVITIE_HEIGHT));
	metaNodeModel.setSize(new Dimension(width, height));

	//
	return metaNodeModel;
};

JbpmFlowModelConverter.convertXMLToTransitionModel = function(node, model) {
	var fromID = node.getAttribute(JbpmFlowModelConverter.ATTR_TRANSITION_FROM);
	fromMetaNodeModel = JbpmFlowModelConverter.getMetaNodeModel(model, fromID);
	var toID = node.getAttribute(JbpmFlowModelConverter.ATTR_TRANSITION_TO);
	toMetaNodeModel = JbpmFlowModelConverter.getMetaNodeModel(model, toID);

	//
	var id = eval(node.getAttribute(JbpmFlowModelConverter.ATTR_TRANSITION_ID));

	//
	var name = node.getAttribute(JbpmFlowModelConverter.ATTR_TRANSITION_NAME);
	name = name ? name : "";

	//
	var transitionModel = new TransitionModel(fromMetaNodeModel,
			toMetaNodeModel, id);

	//
	transitionModel.setText(name);

	//
	return transitionModel;
};

JbpmFlowModelConverter.getMetaNodeModel = function(model, id) {
	var metaNodeModels = model.getMetaNodeModels();
	for ( var i = 0; i < metaNodeModels.size(); i++) {
		var metaNodeModel = metaNodeModels.get(i);
		if (metaNodeModel.getID() == id) {
			return metaNodeModel;
		}
	}
};

// static
JbpmFlowModelConverter.NODE_ROOT = "process-definition";
JbpmFlowModelConverter.NODE_ROOT_SWIMLANE = "swimlane";
//
JbpmFlowModelConverter.NODE_ACTIVITIE = "Activity";
JbpmFlowModelConverter.ATTR_ACTIVITIE_ID = "id";
JbpmFlowModelConverter.ATTR_ACTIVITIE_TYPE = "type";
JbpmFlowModelConverter.ATTR_ACTIVITIE_NAME = "name";
JbpmFlowModelConverter.ATTR_ACTIVITIE_X_COORD = "xCoordinate";
JbpmFlowModelConverter.ATTR_ACTIVITIE_Y_COORD = "yCoordinate";
JbpmFlowModelConverter.ATTR_ACTIVITIE_WIDTH = "width";
JbpmFlowModelConverter.ATTR_ACTIVITIE_HEIGHT = "height";

//
JbpmFlowModelConverter.NODE_TRANSITIONS = "Transitions";
JbpmFlowModelConverter.NODE_TRANSITION = "Transition";
JbpmFlowModelConverter.ATTR_TRANSITION_ID = "id";
JbpmFlowModelConverter.ATTR_TRANSITION_NAME = "name";
JbpmFlowModelConverter.ATTR_TRANSITION_FROM = "from";
JbpmFlowModelConverter.ATTR_TRANSITION_TO = "to";

// Jbpm process-definition attribute
JbpmFlowModelConverter.ATTR_JBPM_PROCESS_NAME = "name";
JbpmFlowModelConverter.ATTR_JBPM_PROCESS_XMLNS = "xmlns";

JbpmFlowModelConverter.NODE_JBPM_TRANSITION = "transition";
JbpmFlowModelConverter.ATTR_JBPM_TRANSITION_NAME = "name";
JbpmFlowModelConverter.ATTR_JBPM_TRANSITION_TO = "to";

JbpmFlowModelConverter.NODE_JBPM_ACTION = "action";
JbpmFlowModelConverter.ATTR_JBPM_ACTION_CLASS = "class";
JbpmFlowModelConverter.NODE_JBPM_EXCEPTIONHANDLER = "exception-handler";

JbpmFlowModelConverter.NODE_JBPM_ELEMENT = "element";

JbpmFlowModelConverter.NODE_JBPM_TASK = "task";
JbpmFlowModelConverter.ATTR_JBPM_TASK_NAME = "name";
JbpmFlowModelConverter.ATTR_JBPM_TASK_DESCRIPTION = "description";
JbpmFlowModelConverter.ATTR_JBPM_TASK_DUEDATE = "duedate";
JbpmFlowModelConverter.ATTR_JBPM_TASK_BLOCKING = "blocking";
JbpmFlowModelConverter.ATTR_JBPM_TASK_SIGNALLING = "signalling";
JbpmFlowModelConverter.ATTR_JBPM_TASK_PRIORITY = "priority";
JbpmFlowModelConverter.ATTR_JBPM_TASK_SWIMLANE = "swimlane";


JbpmFlowModelConverter.NODE_JBPM_TASK_ASSIGNMENT = "assignment";
JbpmFlowModelConverter.NODE_JBPM_TASK_ASSIGNMENT_ACTORID = "actor-id";
JbpmFlowModelConverter.NODE_JBPM_TASK_ASSIGNMENT_POOLEDACTORS = "pooled-actors";
JbpmFlowModelConverter.NODE_JBPM_TASK_CONTROLLER = "controller";
JbpmFlowModelConverter.NODE_JBPM_TASK_CONTROLLER_VARIABLE = "variable";
JbpmFlowModelConverter.ATTR_JBPM_TASK_CONTROLLER_VARIABLE_NAME = "name";
JbpmFlowModelConverter.ATTR_JBPM_TASK_CONTROLLER_VARIABLE_MAPPED_NAME = "mapped-name";
JbpmFlowModelConverter.ATTR_JBPM_TASK_CONTROLLER_VARIABLE_ACCESS = "access";
JbpmFlowModelConverter.NODE_JBPM_EVENT = "event";
JbpmFlowModelConverter.NODE_JBPM_EVENT_TYPE = "type";

// should use the
JbpmFlowModelConverter.NODE_JBPM_EVENT = "event";
JbpmFlowModelConverter.ATTR_JBPM_EVENT_TYPE = "type";

JbpmFlowModelConverter.NODE_JBPM_PROCESS_EVENT = "event";
JbpmFlowModelConverter.ATTR_JBPM_PROCESS_EVENT_TYPE = "type";

JbpmFlowModelConverter.ATTR_JBPM_DECISION_EXPRESSION = "expression";
