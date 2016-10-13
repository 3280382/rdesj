function ForkNode(model, wrapper) {
    this.base = MetaNode;
    var imageUrl = XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/fork.gif";
    this.base(model, imageUrl, wrapper);
}
ForkNode.prototype = new MetaNode();

/**
 * extend function for editing properties for task node
 */
ForkNode.prototype.startEdit = function () {
	   var propertyDialog = new ForkNodePropertyDialog(this.getModel());
	   propertyDialog.titleInput = this.titleInput;
	   propertyDialog.titleTxtCell = this.titleTxtCell;
	   propertyDialog.titleInputCell = this.titleInputCell;
	      
	   propertyDialog.populate();
	   propertyDialog.show();
}