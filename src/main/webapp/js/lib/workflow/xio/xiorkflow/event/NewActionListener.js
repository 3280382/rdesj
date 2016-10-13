function NewActionListener(xiorkFlow) {
    this.xiorkFlow = xiorkFlow;
}

NewActionListener.prototype.actionPerformed = function (obj) {
    Message.setOutter(Toolkit.getElementByID("message"));

    //
    var designerDiv = Toolkit.getElementByID("designer");
	Toolkit.clearElement(designerDiv);
    var xiorkFlow = new XiorkFlow(designerDiv);
    xiorkFlow.setProcessList(window.dialogArguments);
};