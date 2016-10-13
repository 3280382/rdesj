function Node(model, wrapper) {
    this.base = MetaNode;
    var imageUrl = XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/autonode.gif";
    this.base(model, imageUrl, wrapper);
}
Node.prototype = new MetaNode();

Node.prototype.startEdit = function () {
	   var propertyDialog = new NodePropertyDialog(this.getModel());
	   propertyDialog.titleInput = this.titleInput;
	   propertyDialog.titleTxtCell = this.titleTxtCell;
	   propertyDialog.titleInputCell = this.titleInputCell;
	      
	   propertyDialog.populate();
	   propertyDialog.show();
}