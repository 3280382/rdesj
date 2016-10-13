function JoinNodeModel() {
    this.base = MetaNodeModel;
    this.base();

    //
    this.TOS_MAX = MetaNodeModel.NUM_NOT_LIMIT;
    this.setText("Join");
    //
    this.setSize(new Dimension(80, 30));
}
JoinNodeModel.prototype = new MetaNodeModel();

//
JoinNodeModel.prototype.toString = function () {
    return "[Join:" + this.getText() + "]";
};

//
JoinNodeModel.prototype.type = MetaNodeModel.TYPE_JOIN_NODE;
//Jbpm Node Type
JoinNodeModel.prototype.jbpmtype = MetaNodeModel.TYPE_JBPM_JOIN;

