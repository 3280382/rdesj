var sequenceNum = 1; 
 
function WrapperMetaMouseListener(wrapper) {
    this.wrapper = wrapper;
}
WrapperMetaMouseListener.prototype = new MouseListener();
WrapperMetaMouseListener.prototype.onClick = function (e) {
    var state = this.wrapper.getStateMonitor().getState();
    if (state == StateMonitor.SELECT) {
        return;
    }

	//
    var viewer = this.wrapper.getViewer();
    var xiorkFlowModel = this.wrapper.getModel();
    var point = Toolkit.getContainerCoord(e, viewer);

    //
    var metaNodeModel = null;
    switch (state) {
      case StateMonitor.NODE:
        metaNodeModel = new NodeModel();
        break;
      case StateMonitor.FORK_NODE:
        metaNodeModel = new ForkNodeModel();
        break;
      case StateMonitor.JOIN_NODE:
        metaNodeModel = new JoinNodeModel();
        break;
	  case StateMonitor.DECISION_NODE:
        metaNodeModel = new DecisionNodeModel();
        break;
      case StateMonitor.START_NODE:
        metaNodeModel = new StartNodeModel();
        break;
      case StateMonitor.END_NODE:
        metaNodeModel = new EndNodeModel();
        break;
      case StateMonitor.TASK_NODE:
        metaNodeModel = new TaskNodeModel();
        break;
      default:
        break;
    }
    if (metaNodeModel !== null) {
        if (xiorkFlowModel.getMetaNodeModels().size() >= XiorkFlowWorkSpace.META_NODE_MAX) {
            infoDialog("The number of nodes can't exceed:" + XiorkFlowWorkSpace.META_NODE_MAX);
            return;
        }
        
        generateUniqueName(xiorkFlowModel, metaNodeModel);
        
        
        metaNodeModel.setPosition(point);
        xiorkFlowModel.addMetaNodeModel(metaNodeModel);
        this.wrapper.getStateMonitor().resetState(StateMonitor.SELECT);
        this.wrapper.setStatusInfo("Double click the node to edit properties");
    }
};

function generateUniqueName(xiorkFlowModel, newMetaNodeModel)
{
	var newName = newMetaNodeModel.getText();
    
  var metaNodeModels = xiorkFlowModel.getMetaNodeModels();
    
  var duplicateFound = 1;
  while (duplicateFound) {
  	duplicateFound = 0;
    for (var i = 0; i < metaNodeModels.size(); i++) {        
      if (newName == metaNodeModels.get(i).getText()) {
        duplicateFound = 1;
        break;
      }
    }
    
    if (duplicateFound) {
      newName =  newMetaNodeModel.getText()+sequenceNum;
      sequenceNum++;
    }
        
  }
    
	newMetaNodeModel.setText(newName);
	
}
