function StartNodeModel() {
    this.base = MetaNodeModel;
    this.base();

    //
    this.TOS_MAX = 0;
    this.setText("StartNode");

    //
    this.setSize(new Dimension(80, 30));
}
StartNodeModel.prototype = new MetaNodeModel();

//
StartNodeModel.prototype.toString = function () {
    return "[Start:" + this.getText() + "]";
};

//
StartNodeModel.prototype.type = MetaNodeModel.TYPE_START_NODE;
//Jbpm Node Type
StartNodeModel.prototype.jbpmtype = MetaNodeModel.TYPE_JBPM_START_STATE;

