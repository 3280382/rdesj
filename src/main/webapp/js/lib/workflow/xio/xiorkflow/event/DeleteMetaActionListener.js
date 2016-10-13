function DeleteMetaActionListener(xiorkFlow) {
    this.xiorkFlow = xiorkFlow;
}
DeleteMetaActionListener.prototype.actionPerformed = function (obj) {
    var xiorkFlowModel = this.xiorkFlow.getWrapper().getModel();
    xiorkFlowModel.deleteSelected();
};

