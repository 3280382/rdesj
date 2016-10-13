function XiorkFlowModelConverter() {
}

//
XiorkFlowModelConverter.convertModelToXML = function(model) {
	var doc = XMLDocument.newDomcument();

	// root
	var workflowProcessNode = doc
			.createElement(XiorkFlowModelConverter.NODE_ROOT);
	doc.documentElement = workflowProcessNode;

	//
	var activitiesNode = doc
			.createElement(XiorkFlowModelConverter.NODE_ACTIVITIES);
	workflowProcessNode.appendChild(activitiesNode);

	// metaNodes
	var metaNodeModels = model.getMetaNodeModels();
	for ( var i = 0; i < metaNodeModels.size(); i++) {
		var metaNodeModel = metaNodeModels.get(i);
		var activitieNode = XiorkFlowModelConverter.convertMetaNodeModelToXML(
				metaNodeModel, doc);
		activitiesNode.appendChild(activitieNode);
	}

	//
	var transitionsNode = doc
			.createElement(XiorkFlowModelConverter.NODE_TRANSITIONS);
	workflowProcessNode.appendChild(transitionsNode);

	//
	var transitionModels = model.getTransitionModels();
	for ( var i = 0; i < transitionModels.size(); i++) {
		var transitionModel = transitionModels.get(i);
		var transitionNode = XiorkFlowModelConverter
				.convertTransitionModelToXML(transitionModel, doc);
		transitionsNode.appendChild(transitionNode);
	}

	//
	return doc;
};
XiorkFlowModelConverter.convertMetaNodeModelToXML = function(metaNodeModel, doc) {
	var activitieNode = doc
			.createElement(XiorkFlowModelConverter.NODE_ACTIVITIE);

	//
	activitieNode.setAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_ID,
			metaNodeModel.getID());
	activitieNode.setAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_TYPE,
			metaNodeModel.type);
	activitieNode.setAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_NAME,
			metaNodeModel.getText());
	activitieNode.setAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_X_COORD,
			metaNodeModel.getPosition().getX());
	activitieNode.setAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_Y_COORD,
			metaNodeModel.getPosition().getY());
	activitieNode.setAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_WIDTH,
			metaNodeModel.getSize().getWidth());
	activitieNode.setAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_HEIGHT,
			metaNodeModel.getSize().getHeight());

	//
	return activitieNode;
};
XiorkFlowModelConverter.convertTransitionModelToXML = function(transitionModel,
		doc) {
	var transitionNode = doc
			.createElement(XiorkFlowModelConverter.NODE_TRANSITION);

	//
	transitionNode.setAttribute(XiorkFlowModelConverter.ATTR_TRANSITION_ID,
			transitionModel.getID());
	transitionNode.setAttribute(XiorkFlowModelConverter.ATTR_TRANSITION_NAME,
			transitionModel.getText());
	transitionNode.setAttribute(XiorkFlowModelConverter.ATTR_TRANSITION_FROM,
			transitionModel.getFromMetaNodeModel().getID());
	transitionNode.setAttribute(XiorkFlowModelConverter.ATTR_TRANSITION_TO,
			transitionModel.getToMetaNodeModel().getID());

	//
	return transitionNode;
};

//
// modified by ray li
XiorkFlowModelConverter.convertXMLToModel = function(doc, jbpmDoc, initModel) {
	if (!doc) {
		return null;
	}
	var model = initModel;
	if (!model) {
		model = new XiorkFlowModel();
	}

	// swimlane
	var swimlaneNodes = jbpmDoc.getElementsByTagName("swimlane");
	for(var i=0; i<swimlaneNodes.length; i++){
		var node = swimlaneNodes[i];
		var swimlane = new Swimlane();
		swimlane.name = node.getAttribute("name");
		var assNode = node.selectSingleNode("./assignment");
		if(assNode) swimlane.assignment = assNode.getAttribute("class");
		
		model.addSwimlane(swimlane);
	}
	
	//
	// var activitieNodes = doc.getElementsByTagName("Activitie");
	var activitieNodes = doc.getElementsByTagName("Activity");
	if (activitieNodes.length == 0) {
		activitieNodes = doc.getElementsByTagName("Activitie");
	}

	for ( var i = 0; i < activitieNodes.length; i++) {
		var activitieNode = activitieNodes[i];
		var metaNodeModel = XiorkFlowModelConverter.convertXMLToMetaNodeModel(
				activitieNode, XiorkFlowModelConverter
						.getJbpmNodeByActivitieNode(jbpmDoc, activitieNode));
		model.addMetaNodeModel(metaNodeModel);
	}

	//
	var transitionNodes = doc.getElementsByTagName("Transition");
	for ( var i = 0; i < transitionNodes.length; i++) {
		var transitionNode = transitionNodes[i];
		var transitionModel = XiorkFlowModelConverter
				.convertXMLToTransitionModel(transitionNode, model);
		model.addTransitionModel(transitionModel);
	}

	//
	return model;
};
// modified by Ray li
XiorkFlowModelConverter.convertXMLToMetaNodeModel = function(node, jbpmNode) {
	var metaNodeModel = null;

	//
	var type = node.getAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_TYPE);
	switch (type) {
	case MetaNodeModel.TYPE_NODE:
		metaNodeModel = new NodeModel();
		// add nodemodel properties by Ray li
		// add action
		var actionNode = jbpmNode.selectSingleNode("./action");
		if (actionNode) {
			var action = new Action();
			action.actionClass = actionNode.getAttribute("class");
			var variableNodes = actionNode.childNodes;
			for ( var i = 0; i < variableNodes.length; i++) {
				var variable = new Array();
				variable[0] = variableNodes[i].baseName;
				var valueNodes = variableNodes[i].selectNodes("./element");
				if (valueNodes.length > 0) {
					var valueArray = new Array();
					valueArray.isList = true;
					for ( var k = 0; k < valueNodes.length; k++) {
						valueArray[k] = valueNodes[k].text;
					}
					variable[1] = valueArray;
				} else {
					variable[1] = variableNodes[i].text;
				}
				action.variables.add(variable);
			}
			metaNodeModel.setAction(action);
		}
		// add exception handler
		var exceptionhandlerNode = jbpmNode
				.selectSingleNode(".//exception-handler");
		if (exceptionhandlerNode) {
			var action = new Action();
			action.actionClass = exceptionhandlerNode.childNodes[0]
					.getAttribute("class");
			var variableNodes = exceptionhandlerNode.childNodes[0].childNodes;
			for ( var i = 0; i < variableNodes.length; i++) {
				var variable = new Array();
				variable[0] = variableNodes[i].baseName;
				variable[1] = variableNodes[i].text;
				action.variables.add(variable);
			}
			metaNodeModel.setExceptionhandler(action);
		}
		break;
	case MetaNodeModel.TYPE_TASK_NODE:
		metaNodeModel = new TaskNodeModel();
		metaNodeModel.name = jbpmNode.getAttribute("name");
		metaNodeModel.signal = jbpmNode.getAttribute("signal");
		metaNodeModel.createTasksFlag = jbpmNode.getAttribute("create-tasks");
		metaNodeModel.endTasksFlag = jbpmNode.getAttribute("end-tasks");
			
		var taskNodes = jbpmNode.selectNodes(".//task");
		for(i=0; i<taskNodes.length; i++){
			var taskNode = taskNodes[i];
			var task = new Task();
			task.taskName = taskNode.getAttribute("name");
			// description
			task.description = taskNode.getAttribute("description");
			
			task.blocking = taskNode.getAttribute("blocking");
			task.signalling = taskNode.getAttribute("signalling");
			task.priority = taskNode.getAttribute("priority");
			task.swimlane = taskNode.getAttribute("swimlane");

			// due date
			task.duedate = taskNode.getAttribute("duedate") ? taskNode.getAttribute("duedate") : "";

					
			// task event
			var events = taskNode.selectNodes("event");
			for ( j = 0; j < events.length; j++) {
				var eventNode = events[j];
				var type = eventNode.getAttribute("type");
				if (type == "task-create") {
					task.event.create = eventNode.selectSingleNode("action").getAttribute("class");
				} else if (type == "task-assign") {
					task.event.assign = eventNode.selectSingleNode("action").getAttribute("class");
	
				} else if (type == "task-start") {
					task.event.start =eventNode.selectSingleNode("action").getAttribute("class");
	
				} else if (type == "task-end") {
					task.event.end = eventNode.selectSingleNode("action").getAttribute("class");
				}
			}
			
			
			var assignment = taskNode.selectNodes("assignment");
			if (assignment.length > 0) {
				var actors = assignment[0].getAttribute("pooled-actors");
				task.assignment.pooledActors = actors;
				
				var actors = assignment[0].getAttribute("actor-id");
				task.assignment.actorId = actors;
			}
			
			// controller
			var variableNodes = taskNode.selectSingleNode(".//controller").childNodes;
			for ( j = 0; j < variableNodes.length; j++) {
				var varNode = variableNodes[j];
				var variable = new Variable();
				variable.name = varNode.getAttribute("name");
				variable.mappedNmae = varNode.getAttribute("mapped-name");
				
				var access = varNode.getAttribute("access");
				variable.read = access.contains("read");
				variable.write = access.contains("write");
				variable.required = access.contains("required");
				
				task.controller.addVariable(variable);
			}
			metaNodeModel.addTask(task);
		}
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
		var condition = new Condition();
		condition.conditionExpression = jbpmNode.getAttribute("expression");
		metaNodeModel.setCondition(condition);
		break;
	}
	if (!metaNodeModel) {
		return null;
	}

	//
	var id = eval(node.getAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_ID));
	metaNodeModel.setID(id);

	//
	var name = node.getAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_NAME);
	metaNodeModel.setText(name);

	//
	var xCoordinate = eval(node
			.getAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_X_COORD));
	var yCoordinate = eval(node
			.getAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_Y_COORD));
	metaNodeModel.setPosition(new Point(xCoordinate, yCoordinate));

	//
	var width = eval(node
			.getAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_WIDTH));
	var height = eval(node
			.getAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_HEIGHT));
	metaNodeModel.setSize(new Dimension(width, height));

	//
	return metaNodeModel;
};
XiorkFlowModelConverter.convertXMLToTransitionModel = function(node, model) {
	var fromID = node
			.getAttribute(XiorkFlowModelConverter.ATTR_TRANSITION_FROM);
	fromMetaNodeModel = XiorkFlowModelConverter.getMetaNodeModel(model, fromID);
	var toID = node.getAttribute(XiorkFlowModelConverter.ATTR_TRANSITION_TO);
	toMetaNodeModel = XiorkFlowModelConverter.getMetaNodeModel(model, toID);

	//
	var id = eval(node.getAttribute(XiorkFlowModelConverter.ATTR_TRANSITION_ID));

	//
	var name = node.getAttribute(XiorkFlowModelConverter.ATTR_TRANSITION_NAME);
	name = name ? name : "";

	//
	var transitionModel = new TransitionModel(fromMetaNodeModel,
			toMetaNodeModel, id);

	//
	transitionModel.setText(name);

	//
	return transitionModel;
};
XiorkFlowModelConverter.getMetaNodeModel = function(model, id) {
	var metaNodeModels = model.getMetaNodeModels();
	for ( var i = 0; i < metaNodeModels.size(); i++) {
		var metaNodeModel = metaNodeModels.get(i);
		if (metaNodeModel.getID() == id) {
			return metaNodeModel;
		}
	}
};

// get JbpmNode by activitie node by Ray li
XiorkFlowModelConverter.getJbpmNodeByActivitieNode = function(jbpmDoc,
		activitieNode) {
	var nodeName = activitieNode
			.getAttribute(XiorkFlowModelConverter.ATTR_ACTIVITIE_NAME);
	var jbpmNode = jbpmDoc.selectSingleNode("//process-definition/*[@name='"
			+ nodeName + "']");
	return jbpmNode;
};

// static
XiorkFlowModelConverter.NODE_ROOT = "WorkflowProcess";

//
XiorkFlowModelConverter.NODE_ACTIVITIES = "Activities";
XiorkFlowModelConverter.NODE_ACTIVITIE = "Activity";
XiorkFlowModelConverter.ATTR_ACTIVITIE_ID = "id";
XiorkFlowModelConverter.ATTR_ACTIVITIE_TYPE = "type";
XiorkFlowModelConverter.ATTR_ACTIVITIE_NAME = "name";
XiorkFlowModelConverter.ATTR_ACTIVITIE_X_COORD = "xCoordinate";
XiorkFlowModelConverter.ATTR_ACTIVITIE_Y_COORD = "yCoordinate";
XiorkFlowModelConverter.ATTR_ACTIVITIE_WIDTH = "width";
XiorkFlowModelConverter.ATTR_ACTIVITIE_HEIGHT = "height";

//
XiorkFlowModelConverter.NODE_TRANSITIONS = "Transitions";
XiorkFlowModelConverter.NODE_TRANSITION = "Transition";
XiorkFlowModelConverter.ATTR_TRANSITION_ID = "id";
XiorkFlowModelConverter.ATTR_TRANSITION_NAME = "name";
XiorkFlowModelConverter.ATTR_TRANSITION_FROM = "from";
XiorkFlowModelConverter.ATTR_TRANSITION_TO = "to";
