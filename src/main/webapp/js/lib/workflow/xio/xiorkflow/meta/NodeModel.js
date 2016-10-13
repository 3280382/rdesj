function NodeModel() {
    this.base = MetaNodeModel;
    this.base();

    //
    this.setText("Node");

    //
    this.setSize(new Dimension(80, 30));
	//extended properties
	this.action = new Action();
	this.exceptionhandler = new Action();
	this.event = new NodeEvent()
}
NodeModel.prototype = new MetaNodeModel();

//
NodeModel.prototype.toString = function () {
    return "[Node:" + this.getText() + "]";
};
NodeModel.prototype.setAction = function (action) {
    this.action=action;
}
//get action
NodeModel.prototype.getAction = function () {
    return this.action;
}
// set exceptionhandler
NodeModel.prototype.setExceptionhandler = function (handler) {
    this.exceptionhandler=handler;
}
//get exceptionhandler
NodeModel.prototype.getExceptionhandler = function () {
    return this.exceptionhandler;
}
//
NodeModel.prototype.type = MetaNodeModel.TYPE_NODE;
//Jbpm Node Type
NodeModel.prototype.jbpmtype = MetaNodeModel.TYPE_JBPM_NODE;

//define action structure   by Ray

function Action(){
    this.actionClass = "";
	this.variables = new Array();
}

//define event structure
function NodeEvent(){
	this.nodeEnter = "";
	this.nodeLeave = "";
	this.beforeSignal = "";
	this.afterSignal = "";
	
	this.toString = function(){
		return "{node-enter:'"+this.nodeEnter+"',node-leave:'" + this.nodeLeave + "',before-signal:'" + this.beforeSignal +"',after-signal:'"+ this.afterSignal +"'}";
	}
}
