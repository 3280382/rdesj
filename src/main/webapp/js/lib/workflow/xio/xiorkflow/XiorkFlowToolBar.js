function XiorkFlowToolBar(xiorkFlow) {
    this.base = ToolBar;
    this.base();

    //
    this.xiorkFlow = xiorkFlow;

    //
    this.addSeparator();

    //
    this.saveButton = new Button(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/save.gif", "Save");
    
    //Save
    this.saveButton.setToolTipText("Save");
    this.saveButton.addActionListener(new SaveActionListener(this.xiorkFlow));
    this.add(this.saveButton);

    //
    this.addSeparator();

    //
    this.saveAsButton = new Button(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/saveas.gif", "Save As");
    //Save As
    this.saveAsButton.setToolTipText("Save As ...");
    this.saveAsButton.addActionListener(new SaveAsActionListener(this.xiorkFlow));
    this.add(this.saveAsButton);
	//
    this.addSeparator();
        //
    this.closeButton = new Button(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/close.gif", "Close");
    //Save As
    this.closeButton.setToolTipText("close");
    this.closeButton.addActionListener(new CloseActionListener(this.xiorkFlow));
    this.add(this.closeButton);
    this.addSeparator();
    //
    this.newButton = new Button(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/new.gif", "New");
    //Save As
    this.newButton.setToolTipText("New");
    this.newButton.addActionListener(new NewActionListener(this.xiorkFlow));
    this.add(this.newButton);

    //
    this.nodeButtonGroup = new ButtonGroup();

    //
    this.addSeparator();

    //
    this.selectButton = new ToggleButton(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/select.gif", "", true);
    //Select
    this.selectButton.setToolTipText("Selected");
    this.add(this.selectButton);
    this.nodeButtonGroup.add(this.selectButton);
    this.selectButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_SELECT;

    //
    this.addSeparator();

    //
    this.startButton = new ToggleButton(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/start.gif");
    //Start State
    this.startButton.setToolTipText("Start Node");
    this.add(this.startButton);
    this.nodeButtonGroup.add(this.startButton);
    this.startButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_START_NODE;

    //
    this.endButton = new ToggleButton(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/end.gif", "");
    //End State
    this.endButton.setToolTipText("End Node");
    this.add(this.endButton);
    this.nodeButtonGroup.add(this.endButton);
    this.endButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_END_NODE;

    //
    this.addSeparator();

    //
    this.nodeButton = new ToggleButton(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/autonode.gif");
    //Node
    this.nodeButton.setToolTipText("Auto Node");
    this.add(this.nodeButton);
    this.nodeButtonGroup.add(this.nodeButton);
    this.nodeButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_NODE;

    //
    this.tasknodeButton = new ToggleButton(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/tasknode.gif");
    //task node   Ray li
    this.tasknodeButton.setToolTipText("Task Node");
    this.add(this.tasknodeButton);
    this.nodeButtonGroup.add(this.tasknodeButton);
    this.tasknodeButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_TASK_NODE;

    //
    this.forkButton = new ToggleButton(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/fork.gif");
    //Fork
    this.forkButton.setToolTipText("Fork Node");
    this.add(this.forkButton);
    this.nodeButtonGroup.add(this.forkButton);
    this.forkButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_FORK_NODE;

    //
    this.joinButton = new ToggleButton(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/join.gif");
    //Join
    this.joinButton.setToolTipText("Join Node");
    this.add(this.joinButton);
    this.nodeButtonGroup.add(this.joinButton);
    this.joinButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_JOIN_NODE;

	//
    this.decisionButton = new ToggleButton(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/decision.gif");
    //Decision
    this.decisionButton.setToolTipText("Descision");
    this.add(this.decisionButton);
    this.nodeButtonGroup.add(this.decisionButton);
    this.decisionButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_DECISION_NODE;

    //
    this.addSeparator();

    //
    this.transitionButton = new ToggleButton(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/transition.gif");
    //Transition
    this.transitionButton.setToolTipText("Transition");
    this.add(this.transitionButton);
    this.nodeButtonGroup.add(this.transitionButton);
    this.transitionButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_TRANSITION;

    //
    this.addSeparator();

    //
    this.deleteButton = new Button(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/delete.gif");
    //Delete
    this.deleteButton.setToolTipText("Delete");
    this.deleteButton.addActionListener(new DeleteMetaActionListener(this.xiorkFlow));
    this.add(this.deleteButton);

    //
    this.addSeparator();

	//
    this.processEventButton = new Button("", "Properties");
    //Process Event
    this.processEventButton.setToolTipText("Properties");
    this.processEventButton.addActionListener(new ProcessEventListener(this.xiorkFlow));
    this.add(this.processEventButton);
	//
    this.addSeparator();

    //
    this.viewerPatternButtonGroup = new ButtonGroup();

    //design
    var designButton = new ToggleButton("", "Design", true);
    //Design Perspective
    designButton.setToolTipText("Design View");
    this.add(designButton);
    this.viewerPatternButtonGroup.add(designButton);
    designButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_DESIGN;

    //table
    var tableButton = new ToggleButton("", "List", true);
    //Table Perspective
    tableButton.setToolTipText("List View");
    this.add(tableButton);
    this.viewerPatternButtonGroup.add(tableButton);
    tableButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_TABLE;

    var mixButton = new ToggleButton("", "Design/List", true);
    mixButton.setToolTipText("Design/List View");
    this.add(mixButton);
    this.viewerPatternButtonGroup.add(mixButton);
    mixButton.getModel().name = XiorkFlowToolBar.BUTTON_NAME_MIX;

    //
    this.addSeparator();

    //
    var helpButton = new Button(XiorkFlowWorkSpace.XIORK_FLOW_PATH + "images/xiorkflow/help.gif", "\u5e2e\u52a9");
    helpButton.addActionListener(new HelpActionListener());
    //help
    helpButton.setToolTipText("Help");
    //this.add(helpButton);
}
XiorkFlowToolBar.prototype = new ToolBar();
XiorkFlowToolBar.prototype.getNodeButtonGroup = function () {
    return this.nodeButtonGroup;
};
XiorkFlowToolBar.prototype.setDesignButtonEnable = function (b) {
    var buttons = this.nodeButtonGroup.getButtons();
    for (var i = 0; i < buttons.size(); i++) {
        buttons.get(i).getModel().setEnabled(b);
    }
    this.deleteButton.getModel().setEnabled(b);
};
XiorkFlowToolBar.prototype.setButtonEnable = function (b) {
    var buttons = this.nodeButtonGroup.getButtons();
    for (var i = 0; i < buttons.size(); i++) {
        buttons.get(i).getModel().setEnabled(b);
    }
    var viewPatternbuttons = this.viewerPatternButtonGroup.getButtons();
    for (var i = 0; i < viewPatternbuttons.size(); i++) {
        viewPatternbuttons.get(i).getModel().setEnabled(b);
    }
    this.deleteButton.getModel().setEnabled(b);
    this.saveButton.getModel().setEnabled(b);
    this.saveAsButton.getModel().setEnabled(b);
	this.newButton.getModel().setEnabled(b);
};

XiorkFlowToolBar.prototype.getViewerPatternButtonGroup = function () {
    return this.viewerPatternButtonGroup;
};

//
XiorkFlowToolBar.prototype.update = function (observable, arg) {
    if (arg instanceof Array) {
        if (arg.size() == 2) {
            var property = arg[0];
            var state = arg[1];
            if (property == StateMonitor.OPERATION_STATE_RESET) {
                switch (state) {
                  case StateMonitor.SELECT:
                    this.selectButton.getModel().setPressed(true);
                    break;
                  case StateMonitor.NODE:
                    this.nodeButton.getModel().setPressed(true);
                    break;
                  case StateMonitor.FORK_NODE:
                    this.forkNodeButton.getModel().setPressed(true);
                    break;
                  case StateMonitor.JOIN_NODE:
                    this.joinNode.getModel().setPressed(true);
                    break;
				  case StateMonitor.DECISION_NODE:
                    this.decisionNode.getModel().setPressed(true);
                    break;
                  case StateMonitor.START_NODE:
                    this.startNodeButton.getModel().setPressed(true);
                    break;
                  case StateMonitor.END_NODE:
                    this.endNodeButton.getModel().setPressed(true);
                    break;
                  case StateMonitor.TRANSITION:
                    this.transitionButton.getModel().setPressed(true);
                    break;
                  default:
                    break;
                }
            }
        }
    }
};

//
XiorkFlowToolBar.BUTTON_NAME_SELECT = "BUTTON_NAME_SELECT";
XiorkFlowToolBar.BUTTON_NAME_START_NODE = "BUTTON_NAME_START_NODE";
XiorkFlowToolBar.BUTTON_NAME_END_NODE = "BUTTON_NAME_END_NODE";
XiorkFlowToolBar.BUTTON_NAME_FORK_NODE = "BUTTON_NAME_FORK_NODE";
XiorkFlowToolBar.BUTTON_NAME_JOIN_NODE = "BUTTON_NAME_JOIN_NODE";
XiorkFlowToolBar.BUTTON_NAME_DECISION_NODE = "BUTTON_NAME_DECISION_NODE";
XiorkFlowToolBar.BUTTON_NAME_NODE = "BUTTON_NAME_NODE";
XiorkFlowToolBar.BUTTON_NAME_TRANSITION = "BUTTON_NAME_TRANSITION";
//task node button
XiorkFlowToolBar.BUTTON_NAME_TASK_NODE = "BUTTON_NAME_TASK_NODE";

//
XiorkFlowToolBar.BUTTON_NAME_DESIGN = "BUTTON_NAME_DESIGN";
XiorkFlowToolBar.BUTTON_NAME_TABLE = "BUTTON_NAME_TABLE";
XiorkFlowToolBar.BUTTON_NAME_MIX = "BUTTON_NAME_MIX";

