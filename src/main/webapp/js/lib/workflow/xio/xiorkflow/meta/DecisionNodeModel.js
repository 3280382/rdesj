function DecisionNodeModel() {
    this.base = MetaNodeModel;
    this.base();

    this.FROMS_MAX = MetaNodeModel.NUM_NOT_LIMIT;
    this.setText("DecisionNode");

    this.setSize(new Dimension(80, 30));
	this.condition = new Condition();
}
DecisionNodeModel.prototype = new MetaNodeModel();

DecisionNodeModel.prototype.toString = function () {
    return "[Decision:" + this.getText() + "]";
}

DecisionNodeModel.prototype.setCondition = function (condition) {
    this.condition=condition;
}

DecisionNodeModel.prototype.getCondition = function () {
    return this.condition;
}

//
DecisionNodeModel.prototype.type = MetaNodeModel.TYPE_DECISION_NODE;
//Jbpm Node Type
DecisionNodeModel.prototype.jbpmtype = MetaNodeModel.TYPE_JBPM_DECISION;

function Condition(){
    this.conditionExpression = "";
}
