var taskNodeViewModel;

function setTaskNodeViewModel(model){
	taskNodeViewModel = model;
}

function TaskNodePropertyDialog(model) {
	this.model = model;
	setTaskNodeViewModel(model);
	
	var did = "PropertyTaskNodeDialog";
	var tid = did + "_tabs";
	$(document.body).append(getVerticalTagCSS());

	$(document.body)
			.append(
					'<div id="'
							+ did
							+ '">'
							+ '<div id="'
							+ tid
							+ '" class="ui-tabs-vertical">'

							+ '<ul class="ui-tabs-v-nav">'
							+ '<li><a href="javascript:changeTaskNodeTab(\'TaskNodeGeneralTab\');">General</a></li>'
							+ '<li><a href="javascript:changeTaskNodeTab(\'TaskNodeEventTab\');">Events</a></li>'
							+ '<li><a href="javascript:changeTaskNodeTab(\'TaskNodeTaskTab\');">Tasks</a></li>'
							+ '</ul>'

							// Task Nod General Tab
							+ '<div id="TaskNodeGeneralTab" class="ui-tabs-v-panel">'
							+ '<span><b>General properties setting for task node.</b></span>'
							+ '<br><hr/>'
							+ '<table border="0" width="100%">'
							+ '<tr>'
							+ '<td width="15%">Name</td>'
							+ '<td width="85%"><input name="Name" id="NodeName"/>*</td>'
							+ '</tr>'

							+ '<tr>'
							+ '<td width="20%"><span title="Signal specifies the effect of task completion on the process execution continuation.">Signal</span></td>'
							+ '<td width="80%">'
							+ '<select name="TaskNodeSignal" id="TaskNodeSignal">'
							+ '<option value="last" selected>last</option>'
							+ '<option value="last-wait">last-wait</option>'
							+ '<option value="first">first</option>'
							+ '<option value="first-wait">first-wait</option>'
							+ '<option value="first-wait">first-wait</option>'
							+ '<option value="never">never</option>'
							+ '<option value="unsynchronized">unsynchronized</option>'
							+ '</select>'
							+ '</td>'
							+ '</tr>'

							+ '<tr>'
							+ '<td width="20%"><span title="Can be set to false when a runtime calculation has to determine which of the tasks have to be created. In that case, add an action on node-enter, create the tasks in the action, and set create-tasks to false.">create-tasks</span></td>'
							+ '<td width="80%">'
							+ '<select name="TaskNodeCreateTasksFlag" id="TaskNodeCreateTasksFlag">'
							+ '<option value="true" selected>true</option>'
							+ '<option value="false">false</option>'
							+ '</select>'
							+ '</td>'
							+ '</tr>'

							+ '<tr>'
							+ '<td width="20%"><span title="Can be set to true, in which all the tasks that are still open are ended on the node-leave event.">end-tasks</span></td>'
							+ '<td width="80%">'
							+ '<select name="TaskNodeEndTasksFlag" id="TaskNodeEndTasksFlag">'
							+ '<option value="true">true</option>'
							+ '<option value="false" selected>false</option>'
							+ '</select>'
							+ '</td>'
							+ '</tr>'
							+ '</table>'
							+ '</div>'

							// node event tab for task node
							+ '<div id="TaskNodeEventTab" class="ui-tabs-v-panel">'
							+ getNodeEventPropertyDiv() // function defined in ProertyDivFactory.js
							+ '</div>'

							// **=== TaskNodeTaskTab ===**//
							+ '<div id="TaskNodeTaskTab" class="ui-tabs-v-panel">'
							+ getTaskPropertyDiv()
							+ '</div>' // end of task tab
							
							+ '</div>' // end of tabs div
							+ '</div>'); // end of dialog div

	this.show = function() {
		var parent = this;
		var dialog = $("#" + did).dialog( {
			title : "Task Node Properties",
			width : 680,
			height : 325,
			buttons : {
				"Apply" : function() {
					parent.model.setText($("#NodeName").val());

					parent.model.signal = $("#TaskNodeSignal").val();
				    parent.model.createTasksFlag = $("#TaskNodeCreateTasksFlag").val();
				    parent.model.endTasksFlag = $("#TaskNodeEndTasksFlag").val();
				    
					parent.model.setTasks(taskNodeViewModel.getTasks());
					parent.model.setEvent(getTaskNodeEventFromView());
					parent.model.setEditing(false);
					dialog.dialogClose();
				},
				"Close" : function() {
					dialog.dialogClose();
				}
			}
		});
	};

	this.populate = function() {
		changeTaskNodeTab('TaskNodeGeneralTab');
		
		// node general
		$("#NodeName").val(model.getText());
		$("#TaskNodeSignal").val(model.signal);
		$("#TaskNodeCreateTasksFlag").val(model.createTasksFlag);
	    $("#TaskNodeEndTasksFlag").val(model.endTasksFlag);
	    
		// node events
		$("#" + FID_EVENT_NODE_ENTER).val(model.getEvent().nodeEnter);
		$("#" + FID_EVENT_NODE_LEAVE).val(model.getEvent().nodeLeave);
		$("#" + FID_EVENT_BEFORE_SIGNAL).val(model.getEvent().beforeSignal);
		$("#" + FID_EVENT_AFTER_SIGNAL).val(model.getEvent().afterSignal);

		
		// node tasks
		var tasks = model.getTasks();
		for(var i=0; i<tasks.length; i++){
			var task = tasks[i];
			if(i==0){
				$("<option selected value='"+task.taskName+"'>" + task.taskName +"</option>").appendTo("#TaskNameList")
				// init the first task property
				populateTaskToView(task);
			}else{
				$("<option value='"+task.taskName+"'>" + task.taskName +"</option>").appendTo("#TaskNameList");
			}
		}		
		changeTaskNodeTab('TaskNodeGeneralTab');
	};

	this.getEvent = function() {
		var nodeEvent = new NodeEvent();
		nodeEvent.nodeEnter = $("#" + FID_EVENT_NODE_ENTER).val();
		nodeEvent.nodeLeave = $("#" + FID_EVENT_NODE_LEAVE).val();
		nodeEvent.beforeSignal = $("#" + FID_EVENT_BEFORE_SIGNAL).val();
		nodeEvent.afterSignal = $("#" + FID_EVENT_AFTER_SIGNAL).val();
		return nodeEvent;
	}

}

/**
 * should be more integrative with the dialog
 * 
 * @param name
 * @param isRead
 * @param isWrite
 * @param isRequired
 * @return
 */
function addTaskVariable(name, read, write, required, mappedName) {
	var taskVariableTable = document.getElementById("TaskNodeVariableTable");
	var row = taskVariableTable.insertRow();
	var cell_name = row.insertCell();
	var cell_chk_read = row.insertCell();
	var cell_chk_write = row.insertCell();
	var cell_chk_required = row.insertCell();
	//var cell_mapped_name = row.insertCell();
	var cell_chk = row.insertCell();

	var ipt_name = document
			.createElement("<input type='text' name='task_variable_name' size='20'>");
	var ipt_chk_read = document
			.createElement("<input type='checkbox' name='task_variable_read'>");
	var ipt_chk_write = document
			.createElement("<input type='checkbox' name='task_variable_write'>");
	var ipt_chk_required = document
			.createElement("<input type='checkbox' name='task_variable_required'>");
	/*
	var ipt_mapped_name = document
			.createElement("<input type='text' name='task_variable_mapped_name' size='5'>");
	*/
	var ipt_chk = document
			.createElement("<input type='checkbox' name='task_variable_taskchk'>");
	
	
	if (name)
		ipt_name.value = name;

	if(mappedName)
		ipt_mapped_name.value = mappedName;
	
	cell_name.appendChild(ipt_name);
	cell_chk_read.appendChild(ipt_chk_read);
	cell_chk_write.appendChild(ipt_chk_write);
	cell_chk_required.appendChild(ipt_chk_required);
	//cell_mapped_name.appendChild(ipt_mapped_name);
	
	cell_chk.appendChild(ipt_chk);
	
	ipt_chk_read.checked = read;
	ipt_chk_write.checked = write;
	ipt_chk_required.checked = required;

	// set event handler
	ipt_chk_write.relativeProp = ipt_chk_read;
	ipt_chk_write.onclick = function() {
		if (this.checked) {
			this.relativeProp.checked = true;
			this.relativeProp.disabled = true;
		} else {
			this.relativeProp.disabled = false;
		}
	}
	
	if (write)
		ipt_chk_read.disabled = true;
}

// remove variables
function removeTaskVarible() {
	var taskVariableTable = document.getElementById("TaskNodeVariableTable");
	var objs = document.getElementsByName("task_variable_taskchk");
	var index = 0;
	for (i = 0; i < objs.length; i++) {
		if (objs[i].checked) {
			index = objs[i].parentElement.parentElement.rowIndex;
			taskVariableTable.deleteRow(index);
			i--;
		}
	}
}

function changeTaskNodeTab(_tabId) {
	var tabShowed = document.getElementById(_tabId);

	if (tabShowed) {
		var tabs = new Array();
		var tab = document.getElementById("TaskNodeGeneralTab");
		if(tab) tabs[tabs.length] = tab;
		tab = document.getElementById("TaskNodeEventTab");
		if(tab) tabs[tabs.length] = tab;
		tab = document.getElementById("TaskNodeTaskTab");
		if(tab) tabs[tabs.length] = tab;
		
		for ( var i = 0; i < tabs.length; i++) {
			var aTab = tabs[i];
			if (aTab.id == _tabId) {
				aTab.style.display = "block";
			} else {
				aTab.style.display = "none";
			}
		}
	}
}

function changeTaskTab(_tabId) {
	var tabShowed = document.getElementById(_tabId);

	if (tabShowed) {
		var tabs = new Array();
		tabs[tabs.length] = document.getElementById("TaskGeneralTab");
		tabs[tabs.length] = document.getElementById("TaskAssignmentTab");
		tabs[tabs.length] = document.getElementById("TaskControllerTab");
		tabs[tabs.length] = document.getElementById("TaskEventTab");

		for ( var i = 0; i < tabs.length; i++) {
			var aTab = tabs[i];
			if (aTab.id == _tabId) {
				aTab.style.display = "block";
			} else {
				aTab.style.display = "none";
			}
		}
	}
}

/**
 * save the task to view model and update the view
 * @return
 */
function saveTask(){
	var task = getTaskFromView();
		
	if(!task.taskName){
		alert("Please input name of the new task!");
		return;
	}
	
	if(!taskNodeViewModel.containsTask(task)){
		$("<option selected value='"+task.taskName+"'>" + task.taskName +"</option>").appendTo("#TaskNameList")
	}
	taskNodeViewModel.addTask(task);
}

function removeTask(){
	var task = getTaskFromView();
		
	if(!task.taskName){
		alert("Please select one of the task!");
		return;
	}
	
	if(taskNodeViewModel.containsTask(task)){
		$("#TaskNameList option:selected").remove();
		taskNodeViewModel.removeTask(task);
	}
}

function getTaskFromView(){
	var task = new Task();
	//general
	task.taskName = $("#TaskName").val();
	task.description = $("#TaskDescription").val();
	task.blocking = $("#TaskBlocking").val();
	task.signalling = $("#TaskSignalling").val();
	task.duedate = $("#TaskDuedate").val();
	task.priority = $("#TaskPriority").val();
	task.swimlane = $("#TaskSwimlane").val();
	
	//assignment
	task.assignment.actorId = $("#TaskAssignmentActorID").val();
	task.assignment.pooledActors = $("#TaskAssignmentPooledActors").val();
	
	//controller
	task.controller = getControllerFromView();
	
	//events
	task.event = getTaskEventFromView();
	return task;
}

function getTaskEventFromView(){
	var tskEvent = new TaskEvent();
	tskEvent.create = $("#TaskCreateEvent").val();
	tskEvent.assign = $("#TaskAssignEvent").val();
	tskEvent.start = $("#TaskStartEvent").val();
	tskEvent.end = $("#TaskEndEvent").val();
	return tskEvent;
}

function getControllerFromView(){
	var controller = new Controller();
	var objs_name = document.getElementsByName("task_variable_name");
	var objs_read = document.getElementsByName("task_variable_read");
	var objs_write = document.getElementsByName("task_variable_write");
	var objs_required = document.getElementsByName("task_variable_required");
	//var objs_mapped_name = document.getElementsByName("task_variable_mapped_name");
	
	for (i = 0; i < objs_name.length; i++) {
		if (objs_name[i].value != "") {
			var variable = new Variable();
			variable.name = objs_name[i].value;
			variable.read = objs_read[i].checked;
			variable.write = objs_write[i].checked; 
			variable.required = objs_required[i].checked;
			//variable.mappedName = objs_mapped_name[i].value;
			controller.addVariable(variable);
		}
	}
	return controller;
}

function getTaskNodeEventFromView(){
	var event = new NodeEvent();
	event.nodeEnter = $("#" + FID_EVENT_NODE_ENTER).val();
	event.nodeLeave = $("#" + FID_EVENT_NODE_LEAVE).val();
	event.beforeSignal = $("#" + FID_EVENT_BEFORE_SIGNAL).val();
	event.afterSignal = $("#" + FID_EVENT_AFTER_SIGNAL).val();
	return event;
}

function populateTaskToView(task){
	 $("#TaskName").val(task.taskName);
	 $("#TaskDescription").val(task.description);
	 $("#TaskBlocking").val(task.blocking);
	 $("#TaskSignalling").val(task.signalling);
	 $("#TaskDuedate").val(task.duedate);
	 $("#TaskPriority").val(task.priority);
	 $("#TaskSwimlane").val(task.swimlane);
	
	//assignment
	 $("#TaskAssignmentActorID").val(task.assignment.actorId);
	 $("#TaskAssignmentPooledActors").val(task.assignment.pooledActors);
	 
	//control
	var vars = task.controller.variables;
	for(var i=0; i<vars.length; i++){
		var v = vars[i];
		addTaskVariable(v.name, v.read, v.write, v.required, v.mappedName);
	}

	//events
	$("#TaskCreateEvent").val(task.event.create);
	$("#TaskAssignEvent").val(task.event.assign);
	$("#TaskStartEvent").val(task.event.start);
	$("#TaskEndEvent").val(task.event.end);
}

function changeTask(self){
	$("#TaskGeneralTab").html(getTaskGeneralPropertyContent());	
	$("#TaskAssignmentTab").html(getTaskAssignmentPropertyContent());
	$("#TaskControllerTab").html(getTaskControllerPropertyContent());
	$("#TaskEventTab").html(getTaskEventPropertyContent());
	var taskName = self.value;
	var task = taskNodeViewModel.getTask(taskName);
	if(task){
		populateTaskToView(task);
	}
}

function changeTaskEventTab(_tabId) {
	var tabShowed = document.getElementById(_tabId);

	if (tabShowed) {
		var tabs = new Array();
		tabs[tabs.length] = document.getElementById("TaskCreateEventTab");
		tabs[tabs.length] = document.getElementById("TaskAssignEventTab");
		tabs[tabs.length] = document.getElementById("TaskStartEventTab");
		tabs[tabs.length] = document.getElementById("TaskEndEventTab");

		for ( var i = 0; i < tabs.length; i++) {
			var aTab = tabs[i];
			if (aTab.id == _tabId) {
				aTab.style.display = "block";
			} else {
				aTab.style.display = "none";
			}
		}
	}
}