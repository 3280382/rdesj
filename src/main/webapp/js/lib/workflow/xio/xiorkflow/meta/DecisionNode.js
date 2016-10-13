function DecisionNode(model, wrapper) {
    this.base = MetaNode;
    var imageUrl = XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/decision.gif";
    this.base(model, imageUrl, wrapper);
}
DecisionNode.prototype = new MetaNode();

DecisionNode.prototype.startEdit = function () {
	   var propertyDialog = new DecisionNodePropertyDialog(this.getModel());
	   propertyDialog.titleInput = this.titleInput;
	   propertyDialog.titleTxtCell = this.titleTxtCell;
	   propertyDialog.titleInputCell = this.titleInputCell;
	      
	   propertyDialog.populate();
	   propertyDialog.show();
}