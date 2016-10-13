function ProcessEventListener(xiorkFlow) {
    this.xiorkFlow = xiorkFlow;
}

ProcessEventListener.prototype.actionPerformed = function (obj) {
    var wrapper = this.xiorkFlow.getWrapper();
    var toolbar = this.xiorkFlow.getToolBar();
    var name = wrapper.getModel().getName();
	
    var processEventDialog = new ProcessEventDialog(wrapper.getModel());
    processEventDialog.populate();
	processEventDialog.show();
};
