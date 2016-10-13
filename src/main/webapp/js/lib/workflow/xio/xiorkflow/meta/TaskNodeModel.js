function TaskNodeModel() {
    this.base = MetaNodeModel;
    this.base();

    //
    this.setText("TaskNode");

    this.signal = "last";
    this.createTasksFlag = "false";
    this.endTasksFlag = "true";
    //
    this.setSize(new Dimension(80, 30));
	//extended properties
	this.tasks = new Array();
	this.event = new NodeEvent();
}

TaskNodeModel.prototype = new MetaNodeModel();

//
TaskNodeModel.prototype.toString = function () {
	//task node
    return "[TaskNode:" + this.getText() + "]";
};
//extended function for node  by Ray
// set task
TaskNodeModel.prototype.addTask = function (task) {
	var index = this.indexOfTask(task);
	if(index>=0){
		this.tasks[index] = task;
	}else{
		this.tasks[this.tasks.length] = task;
	}
}

TaskNodeModel.prototype.removeTask = function (task) {
	var index = this.indexOfTask(task);
	if(index>=0){
		var ttasks = this.tasks;
		ttasks.splice(index, 1);
		this.tasks = ttasks;
	}
}

TaskNodeModel.prototype.containsTask = function(task){
	for(i=0; i<this.tasks.length; i++){
		var tsk = this.tasks[i];
		if(tsk.taskName == task.taskName){
			return true;
		}
	}
	return false;
};

TaskNodeModel.prototype.indexOfTask = function(task){
	for(i=0; i<this.tasks.length; i++){
		var tsk = this.tasks[i];
		if(tsk.taskName == task.taskName){
			return i;
		}
	}
	return -1;
};

//get tasks
TaskNodeModel.prototype.getTasks = function () {
    return this.tasks;
}

//get tasks
TaskNodeModel.prototype.setTasks = function (tasks) {
    this.tasks = tasks;
}

//get tasks by name
TaskNodeModel.prototype.getTask = function (taskName) {
	for(i=0; i<this.tasks.length; i++){
		var tsk = this.tasks[i];
		if(tsk.taskName == taskName){
			return tsk;
		}
	}
	return null;
}

// set event
TaskNodeModel.prototype.setEvent = function(event){
	this.event = event;
}

//get event
TaskNodeModel.prototype.getEvent = function(){
	return this.event;
}
//
TaskNodeModel.prototype.type = MetaNodeModel.TYPE_TASK_NODE;
//Jbpm Node Type
TaskNodeModel.prototype.jbpmtype = MetaNodeModel.TYPE_JBPM_TASK_NODE;

//define task structure   by Ray
function Task(){
    this.taskName = "task1";
	this.description = "";
	this.duedate = "";
	
	this.blocking = "false";
	this.signalling = "true";
	this.priority = "normal";
	
	this.swimlane = "";
	
	this.controller = new Controller();
	this.assignment = new Assignment();
	this.event = new TaskEvent();
}

//define controller structure 
function Controller(){
	this.variables = new Array();
	
	this.addVariable = function(variable){
		var index = this.index(variable);
		if(index>=0){
			this.variables[index] = variable;
		}else{
			this.variables[this.variables.length] = variable;
		}
	};
	
	this.index = function(variable){
		for(i=0; i<this.variables.length; i++){
			var v = this.variables[i];
			if(v.name == variable.taskName){
				return i;
			}
		}
		return -1;
	};
}

function Variable(){
	this.name = "";
	this.read = true;
	this.write = true;
	this.required = false;
	this.mappedName = "";
}

//define assignment structure 
function Assignment(){
	this.actorId = "";
	this.pooledActors = "";
}

function TaskEvent(){
	this.create = "";
	this.assign = "";
	this.start = "";
	this.end = "";
}