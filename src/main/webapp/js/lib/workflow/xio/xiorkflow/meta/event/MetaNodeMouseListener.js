function MetaNodeMouseListener(metaNodeModel, wrapper) {
    this.metaNodeModel = metaNodeModel;
    this.wrapper = wrapper;
}
MetaNodeMouseListener.prototype = new MouseListener();
MetaNodeMouseListener.prototype.onMouseDown = function (e) {
    this.moved = false;
    if (e.button != MouseEvent.BUTTON_LEFT) {
        return;
    }
    this.down = true;

    //
    var state = this.wrapper.getStateMonitor().getState();
    switch (state) {
      case StateMonitor.SELECT:
        if (this.metaNodeModel.isEditing()) {
            return;
        }
        var selectedMetaNodeModels = this.wrapper.getModel().getSelectedMetaNodeModels();
        var selectedTransitionModels = this.wrapper.getModel().getSelectedTransitionModels();
        if ((selectedTransitionModels.size() <= 1) && (selectedTransitionModels.size() <= 1) && (!e.ctrlKey)) {
            this.wrapper.getModel().clearSelectedMetaNodeModels();
            this.wrapper.getModel().clearSelectedTransitionModels();
            this.wrapper.getModel().addSelectedMetaNodeModel(this.metaNodeModel);
            this.wrapper.setStatusInfo("Double click the node to edit properties; Move the cursor to the point of right bottom to resize the node");
        }
        break;
      case StateMonitor.TRANSITION:
        var transitionMonitor = this.wrapper.getTransitionMonitor();
        if (this.metaNodeModel.isNewFromAvailable()) {
            transitionMonitor.setFromMetaNodeModel(this.metaNodeModel);
            this.wrapper.setStatusInfo("Can build a transition from" + this.metaNodeModel);
        } else {
            this.wrapper.setStatusInfo("Can't build a transition from" + this.metaNodeModel);
        }
        break;
    }
};
MetaNodeMouseListener.prototype.onMouseMove = function (e) {
    if (e.button != MouseEvent.BUTTON_LEFT) {
        return;
    }
    if (this.down) {
        this.moved = true;
    }
};
MetaNodeMouseListener.prototype.onMouseOver = function (e) {
    var state = this.wrapper.getStateMonitor().getState();
    switch (state) {
      case StateMonitor.TRANSITION:
        var transitionMonitor = this.wrapper.getTransitionMonitor();
        var fromMetaNodeModel = transitionMonitor.getFromMetaNodeModel();
        if (fromMetaNodeModel) {
            if ((this.metaNodeModel.isNewToAvailable()) && (fromMetaNodeModel != this.metaNodeModel)) {
                transitionMonitor.setToMetaNodeModel(this.metaNodeModel);
                this.wrapper.setStatusInfo("Can build a transition to" + this.metaNodeModel);
            } else {
                this.wrapper.setStatusInfo("Can't build a transition to" + this.metaNodeModel);
            }
        }
        break;
    }
};
MetaNodeMouseListener.prototype.onMouseOut = function (e) {
    var state = this.wrapper.getStateMonitor().getState();
    switch (state) {
      case StateMonitor.TRANSITION:
        var transitionMonitor = this.wrapper.getTransitionMonitor();
        if (transitionMonitor.getFromMetaNodeModel() !== null) {
            transitionMonitor.setToMetaNodeModel(null);
            this.wrapper.setStatusInfo("Can't find a transition end node");
        }
        break;
    }
};
MetaNodeMouseListener.prototype.onMouseUp = function (e) {
    this.down = false;
    if (e.button != MouseEvent.BUTTON_LEFT) {
        return;
    }

    var state = this.wrapper.getStateMonitor().getState();
    switch (state) {
      case StateMonitor.SELECT:
        if (this.moved) {
            return;
        }
        if (e.ctrlKey) {
            if (this.metaNodeModel.isSelected()) {
                this.wrapper.getModel().removeSelectedMetaNodeModel(this.metaNodeModel);
            } else {
                this.wrapper.getModel().addSelectedMetaNodeModel(this.metaNodeModel);
            }
            this.wrapper.setStatusInfo("Multiple select");
        } else {
            this.wrapper.getModel().clearSelectedMetaNodeModels();
            this.wrapper.getModel().clearSelectedTransitionModels();
            this.wrapper.getModel().addSelectedMetaNodeModel(this.metaNodeModel);
            this.wrapper.setStatusInfo("Double click the node to edit properties; Move the cursor to the point of right bottom to resize the node");
        }
        break;
      case StateMonitor.TRANSITION:
        var transitionMonitor = this.wrapper.getTransitionMonitor();

    	//
        var fromMetaNodeModel = transitionMonitor.getFromMetaNodeModel();
        var toMetaNodeModel = this.metaNodeModel;
        if ((this.metaNodeModel.isNewToAvailable()) && (fromMetaNodeModel) && (fromMetaNodeModel != toMetaNodeModel)) {
            var transitionModel = new TransitionModel(fromMetaNodeModel, toMetaNodeModel);
            this.wrapper.getModel().addTransitionModel(transitionModel);
            this.wrapper.setStatusInfo("Successed build a transition from " + fromMetaNodeModel + " to " + toMetaNodeModel);
        } else {
            this.wrapper.setStatusInfo("Failed to build a transition");
        }

        transitionMonitor.reset();
        break;
    }
};

