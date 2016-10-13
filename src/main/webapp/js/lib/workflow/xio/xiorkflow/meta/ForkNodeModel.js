function ForkNodeModel() {
    this.base = MetaNodeModel;
    this.base();

    //
    this.FROMS_MAX = MetaNodeModel.NUM_NOT_LIMIT;
    this.setText("Fork");
    //
    this.setSize(new Dimension(80, 30));
}
ForkNodeModel.prototype = new MetaNodeModel();

//
ForkNodeModel.prototype.toString = function () {
    return "[Fork:" + this.getText() + "]";
};

//
ForkNodeModel.prototype.type = MetaNodeModel.TYPE_FORK_NODE;
//Jbpm Node Type
ForkNodeModel.prototype.jbpmtype = MetaNodeModel.TYPE_JBPM_FORK;

