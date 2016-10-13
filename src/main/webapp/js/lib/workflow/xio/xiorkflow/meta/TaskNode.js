function TaskNode(model, wrapper) {
    this.base = MetaNode;
    var imageUrl = XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/tasknode.gif";
    this.base(model, imageUrl, wrapper);
}
TaskNode.prototype = new MetaNode();

/**
 * extend function for editing properties for task node
 */
TaskNode.prototype.startEdit = function () {
	   var propertyDialog = new TaskNodePropertyDialog(this.getModel());
	   propertyDialog.titleInput = this.titleInput;
	   propertyDialog.titleTxtCell = this.titleTxtCell;
	   propertyDialog.titleInputCell = this.titleInputCell;
	      
	   propertyDialog.populate();
	   propertyDialog.show();
}