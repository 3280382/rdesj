function MetaNodeModel() {
    this.base = MetaModel;
    this.base();

    //
    this.FROMS_MAX = -1;
    this.TOS_MAX = -1;

    //
    this.setPosition(new Point(0, 0));
    this.setSize(new Dimension(80, 30));
    this.setText("MetaNode");


    //
    this.froms = new Array();
    this.tos = new Array();

    //
    this.setEditing(false);
}
MetaNodeModel.prototype = new MetaModel();

//
MetaNodeModel.prototype.toString = function () {
    return "[Meta Node:" + this.getText() + "]";
};

//
MetaNodeModel.prototype.setPosition = function (position) {
    if (this.isEditing()) {
        return;
    }
    if (position == null) {
        return;
    }
    if ((position.getX() < 0) || (position.getY() < 0)) {
        return;
    }
    if (this.isResizing()) {
        return;
    }
    this.position = position;
    this.notifyObservers(MetaNodeModel.POSITION_CHANGED);
};
MetaNodeModel.prototype.getPosition = function () {
    return this.position;
};

//
MetaNodeModel.prototype.setSize = function (size) {
    if (size == null) {
        return;
    }
    if (size.getWidth() < XiorkFlowWorkSpace.META_NODE_MIN_WIDTH) {
        size.setWidth(XiorkFlowWorkSpace.META_NODE_MIN_WIDTH);
    }
    if (size.getHeight() < XiorkFlowWorkSpace.META_NODE_MIN_HEIGHT) {
        size.setHeight(XiorkFlowWorkSpace.META_NODE_MIN_HEIGHT);
    }
    this.size = size;
    this.notifyObservers(MetaNodeModel.SIZE_CHANGED);
};
MetaNodeModel.prototype.getSize = function () {
    return this.size;
};

//
MetaNodeModel.prototype.isNewFromAvailable = function () {
    var size = this.froms.size();
    return (this.FROMS_MAX == MetaNodeModel.NUM_NOT_LIMIT) ? true : (this.FROMS_MAX > size);
};
MetaNodeModel.prototype.isNewToAvailable = function () {
    var size = this.tos.size();
    return (this.TOS_MAX == MetaNodeModel.NUM_NOT_LIMIT) ? true : (this.TOS_MAX > size);
};

//
MetaNodeModel.prototype.isFromValidity = function () {
    var size = this.froms.size();
    return (this.FROMS_MAX == MetaNodeModel.NUM_NOT_LIMIT) ? true : (this.FROMS_MAX >= size);
};
MetaNodeModel.prototype.isToValidity = function () {
    var size = this.tos.size();
    return (this.TOS_MAX == MetaNodeModel.NUM_NOT_LIMIT) ? true : (this.TOS_MAX >= size);
};

//
MetaNodeModel.prototype.setResizing = function (resizing) {
    this.resizing = resizing;
};
MetaNodeModel.prototype.isResizing = function () {
    return this.resizing;
};

//
MetaNodeModel.prototype.addFrom = function (transitionModel) {
    this.froms.add(transitionModel);
    this.notifyObservers(MetaNodeModel.FROM_CHANGED);
    this.addObserver(transitionModel);
};
MetaNodeModel.prototype.removeFrom = function (transitionModel) {
    this.froms.remove(transitionModel);
    this.notifyObservers(MetaNodeModel.FROM_CHANGED);
    this.removeObserver(transitionModel);
};
MetaNodeModel.prototype.getFroms = function () {
    return this.froms;
};
MetaNodeModel.prototype.addTo = function (transitionModel) {
    this.tos.add(transitionModel);
    this.notifyObservers(MetaNodeModel.TO_CHANGED);
    this.addObserver(transitionModel);
};
MetaNodeModel.prototype.removeTo = function (transitionModel) {
    this.tos.remove(transitionModel);
    this.notifyObservers(MetaNodeModel.TO_CHANGED);
    this.removeObserver(transitionModel);
};
MetaNodeModel.prototype.getTos = function () {
    return this.tos;
};

//
MetaNodeModel.NUM_NOT_LIMIT = -1;

//
MetaNodeModel.POSITION_CHANGED = "POSITION_CHANGED";
MetaNodeModel.SIZE_CHANGED = "SIZE_CHANGED";
MetaNodeModel.FROM_CHANGED = "FROM_CHANGED";
MetaNodeModel.TO_CHANGED = "TO_CHANGED";

//
MetaNodeModel.TYPE_META_NODE = "META_NODE";
MetaNodeModel.TYPE_START_NODE = "START_NODE";
MetaNodeModel.TYPE_END_NODE = "END_NODE";
MetaNodeModel.TYPE_NODE = "NODE";
MetaNodeModel.TYPE_TASK_NODE = "TASK_NODE";
MetaNodeModel.TYPE_FORK_NODE = "FORK_NODE";
MetaNodeModel.TYPE_JOIN_NODE = "JOIN_NODE";
MetaNodeModel.TYPE_DECISION_NODE = "DECISION_NODE";

//Jbpm Node Type
MetaNodeModel.TYPE_JBPM_META_NODE = "meta_node";
MetaNodeModel.TYPE_JBPM_START_STATE = "start-state";
MetaNodeModel.TYPE_JBPM_END_STATE = "end-state";
MetaNodeModel.TYPE_JBPM_NODE = "node";

MetaNodeModel.TYPE_JBPM_TASK_NODE = "task-node";
MetaNodeModel.TYPE_JBPM_FORK = "fork";
MetaNodeModel.TYPE_JBPM_JOIN = "join";
MetaNodeModel.TYPE_JBPM_DECISION = "decision";

MetaNodeModel.prototype.jbpmtype = MetaNodeModel.TYPE_JBPM_META_NODE;

MetaNodeModel.prototype.type = MetaNodeModel.TYPE_META_NODE;

