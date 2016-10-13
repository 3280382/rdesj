function JoinNode(model, wrapper) {
    this.base = MetaNode;
    var imageUrl = XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/join.gif";
    this.base(model, imageUrl, wrapper);
}
JoinNode.prototype = new MetaNode();

/**
 * extend function for editing properties for  node
 */
JoinNode.prototype.startEdit = function () {
	   var propertyDialog = new JoinNodePropertyDialog(this.getModel());
	   propertyDialog.titleInput = this.titleInput;
	   propertyDialog.titleTxtCell = this.titleTxtCell;
	   propertyDialog.titleInputCell = this.titleInputCell;
	      
	   propertyDialog.populate();
	   propertyDialog.show();
}