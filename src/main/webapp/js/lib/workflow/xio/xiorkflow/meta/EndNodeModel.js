function EndNodeModel() {
    this.base = MetaNodeModel;
    this.base();

    //
    this.FROMS_MAX = 0;
    this.setText("EndNode");

    //
    this.setSize(new Dimension(80, 30));
}
EndNodeModel.prototype = new MetaNodeModel();

//
EndNodeModel.prototype.toString = function () {
    return "[End:" + this.getText() + "]";
};

//
EndNodeModel.prototype.type = MetaNodeModel.TYPE_END_NODE;
//Jbpm Node Type
EndNodeModel.prototype.jbpmtype = MetaNodeModel.TYPE_JBPM_END_STATE;

