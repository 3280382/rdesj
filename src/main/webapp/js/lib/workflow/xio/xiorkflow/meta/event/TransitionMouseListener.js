function TransitionMouseListener(transitionModel, wrapper) {
    this.transitionModel = transitionModel;
    this.wrapper = wrapper;
}
TransitionMouseListener.prototype = new MouseListener();

TransitionMouseListener.prototype.onMouseDown = function (e) {
    this.moved = false;
    if (e.button != MouseEvent.BUTTON_LEFT) {
        return;
    }
    this.down = true;

    var state = this.wrapper.getStateMonitor().getState();
    switch (state) {
      case StateMonitor.SELECT:
        var selectedMetaNodeModels = this.wrapper.getModel().getSelectedMetaNodeModels();
        var selectedTransitionModels = this.wrapper.getModel().getSelectedTransitionModels();
        if ((selectedTransitionModels.size() <= 1) && (selectedMetaNodeModels.size() <= 1) && (!e.ctrlKey)) {
            this.wrapper.getModel().clearSelectedMetaNodeModels();
            this.wrapper.getModel().clearSelectedTransitionModels();
            this.wrapper.getModel().addSelectedTransitionModel(this.transitionModel);
            this.wrapper.setStatusInfo("Double click the transition to edit name");
        }
        break;
      case StateMonitor.TRANSITION:
        break;
    }
};
TransitionMouseListener.prototype.onMouseMove = function (e) {
    if (e.button != MouseEvent.BUTTON_LEFT) {
        return;
    }
    if (this.down) {
        this.moved = true;
    }
};
TransitionMouseListener.prototype.onMouseUp = function (e) {
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
            if (this.transitionModel.isSelected()) {
                this.wrapper.getModel().removeSelectedTransitionModel(this.transitionModel);
            } else {
                this.wrapper.getModel().addSelectedTransitionModel(this.transitionModel);
            }
        } else {
            this.wrapper.getModel().clearSelectedMetaNodeModels();
            this.wrapper.getModel().clearSelectedTransitionModels();
            this.wrapper.getModel().addSelectedTransitionModel(this.transitionModel);
            this.wrapper.setStatusInfo("Double click the transition to edit name");
        }
        break;
    }
};

