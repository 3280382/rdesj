function XiorkFlow(ui) {
    this.base = Frame;
    this.base(ui);
    this.ui.style.overflow = "auto";

    //
    this.stateMonitor = new StateMonitor();
    this.xiorkFlowToolBar = new XiorkFlowToolBar(this);
    this.add(this.xiorkFlowToolBar);
    this.stateMonitor.addObserver(this.xiorkFlowToolBar);

    //
    this.xiorkFlowViewer = new XiorkFlowViewer();
    this.xiorkFlowViewer.setWidth("100%");
    this.xiorkFlowViewer.setHeight("100%");
    this.viewerRow = this.add(this.xiorkFlowViewer);

    //
    this.tableViewer = new XiorkFlowTableViewer();
    this.tableViewer.setWidth("100%");
    this.tableViewer.setHeight("100%");
    this.tableViewerRow = this.add(this.tableViewer);
    this.tableViewer.setDisplay("none");

    //
    this.statusPanel = new StatusLabel();
    //
    this.statusPanel.setText("Workflow Designer");
    this.add(this.statusPanel);

	//
    this.xiorkFlowToolBar.getNodeButtonGroup().addObserver(this);
    this.xiorkFlowToolBar.getViewerPatternButtonGroup().addObserver(this);

    //
    this.xiorkFlowWrapper = new XiorkFlowWrapper(this.xiorkFlowViewer, null, this.stateMonitor, this.statusPanel);
    this.tableViewer.setModel(this.xiorkFlowWrapper.getModel());
}
XiorkFlow.prototype = new Frame();

//
XiorkFlow.prototype.getToolBar = function () {
    return this.xiorkFlowToolBar;
};
XiorkFlow.prototype.getStatusLabel = function () {
    return this.statusPanel;
};
XiorkFlow.prototype.getWrapper = function () {
    return this.xiorkFlowWrapper;
};
XiorkFlow.prototype.getTableViewer = function () {
    return this.tableViewer;
};
XiorkFlow.prototype.setProcessList = function (processList) {
    this.processList = processList;
};
XiorkFlow.prototype.getProcessList = function () {
    return this.processList;
};

//
XiorkFlow.prototype.update = function (observable, arg) {
    if (arg == ButtonGroup.PRESSED_CHANGED) {
        if (observable == this.getToolBar().getNodeButtonGroup()) {
            var pressedButtonModel = this.getToolBar().getNodeButtonGroup().getPressedButtonModel();
            var modelName = pressedButtonModel.name;
            switch (modelName) {
              case XiorkFlowToolBar.BUTTON_NAME_SELECT:
                this.stateMonitor.setState(StateMonitor.SELECT);
                this.statusPanel.setText("Select, For multiple select press Ctrl");
                break;
              case XiorkFlowToolBar.BUTTON_NAME_START_NODE:
                this.stateMonitor.setState(StateMonitor.START_NODE);
                this.statusPanel.setText("Creates a Start Node");
                break;
              case XiorkFlowToolBar.BUTTON_NAME_END_NODE:
                this.stateMonitor.setState(StateMonitor.END_NODE);
                this.statusPanel.setText("Creates a End Node");
                break;
              case XiorkFlowToolBar.BUTTON_NAME_FORK_NODE:
                this.stateMonitor.setState(StateMonitor.FORK_NODE);
                this.statusPanel.setText("Creates a Fork Node");
                break;
              case XiorkFlowToolBar.BUTTON_NAME_JOIN_NODE:
                this.stateMonitor.setState(StateMonitor.JOIN_NODE);
                this.statusPanel.setText("Creates a Join Node");
                break;
			  case XiorkFlowToolBar.BUTTON_NAME_DECISION_NODE:
                this.stateMonitor.setState(StateMonitor.DECISION_NODE);
                this.statusPanel.setText("Creates a Decision Node");
                break;
              case XiorkFlowToolBar.BUTTON_NAME_NODE:
                this.stateMonitor.setState(StateMonitor.NODE);
                this.statusPanel.setText("Creates a Task Node");
                break;
              case XiorkFlowToolBar.BUTTON_NAME_TASK_NODE:
                this.stateMonitor.setState(StateMonitor.TASK_NODE);
                this.statusPanel.setText("Creates a Node");
                break;
              case XiorkFlowToolBar.BUTTON_NAME_TRANSITION:
                this.stateMonitor.setState(StateMonitor.TRANSITION);
                this.statusPanel.setText("Creates a Transition");
                break;
              default:
                break;
            }
            return;
        }

        //
        if (observable == this.getToolBar().getViewerPatternButtonGroup()) {
            var pressedButtonModel = this.getToolBar().getViewerPatternButtonGroup().getPressedButtonModel();
            var modelName = pressedButtonModel.name;
            switch (modelName) {
              case XiorkFlowToolBar.BUTTON_NAME_DESIGN:
                this.xiorkFlowViewer.setDisplay("");
                this.viewerRow.style.display = "";
                this.xiorkFlowViewer.setHeight("100%");
                this.viewerRow.height = "100%";
                this.tableViewer.setDisplay("none");
                this.tableViewerRow.style.display = "none";
                this.getToolBar().setDesignButtonEnable(true);
                this.getStatusLabel().setText("Design Perspective");
                break;
              case XiorkFlowToolBar.BUTTON_NAME_TABLE:
                this.xiorkFlowViewer.setDisplay("none");
                this.viewerRow.style.display = "none";
                this.tableViewer.setDisplay("");
                this.tableViewerRow.style.display = "";
                this.tableViewer.setHeight("100%");
                this.tableViewerRow.height = "100%";
                this.getToolBar().setDesignButtonEnable(false);
                this.getStatusLabel().setText("Table Perspective");
                break;
              case XiorkFlowToolBar.BUTTON_NAME_MIX:
                this.xiorkFlowViewer.setDisplay("");
                this.viewerRow.style.display = "";
                this.xiorkFlowViewer.setHeight("100%");
                this.viewerRow.height = "100%";
                this.tableViewer.setDisplay("");
                this.tableViewerRow.style.display = "";
                this.tableViewer.setHeight("200px");
                this.tableViewerRow.height = "200px";
                this.getToolBar().setDesignButtonEnable(true);
                this.getStatusLabel().setText("Mix perspective");
                break;
              default:
                break;
            }
            return;
        }

        //
        return;
    }
};

