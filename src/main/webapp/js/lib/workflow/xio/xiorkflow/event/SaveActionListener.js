function SaveActionListener(xiorkFlow) {
    this.xiorkFlow = xiorkFlow;
}

SaveActionListener.prototype.actionPerformed = function (obj) {
    var wrapper = this.xiorkFlow.getWrapper();
    var toolbar = this.xiorkFlow.getToolBar();
    var name = wrapper.getModel().getName();
	if(this.xiorkFlow.xiorkFlowWrapper.metaNodes.size()<1){
		infoDialog("Unable to add Process Definition.");
		return;
	}
	var duplicateNames = findDuplicateNames(wrapper.getModel());
	if (duplicateNames != "")
	{
		infoDialog
		  ("Cannot save the process definition; there are duplicate node names: "+duplicateNames
		   +". \n\nPlease correct the names and then choose Save again.");
    return;
	}
    if (!name) {
        saveProcessConsiderDupleName(this.xiorkFlow, false);
    } else {
		saveProcessIgnoreDupleName(this.xiorkFlow, name);
	}
};

function saveProcessConsiderDupleName(xiorkFlow, doClose){
	infoDialog("Please enter workflow name in process properties setting.");
    /*
	if (name != null && name != "") {
		saveProcessIgnoreDupleName(xiorkFlow, name, doClose);
    }
    */
}

function saveProcessIgnoreDupleName(xiorkFlow, name, doClose)
{
	var addProcess = new AddProcess(xiorkFlow.getWrapper(), xiorkFlow.getToolBar(),
			xiorkFlow.getProcessList());
    addProcess.addProcess(name);
    if(doClose){
		exitwindow(); 
    }
    
}

function SaveAsActionListener(xiorkFlow) {
    this.xiorkFlow = xiorkFlow;
}

SaveAsActionListener.prototype.actionPerformed = function (obj) {
    var wrapper = this.xiorkFlow.getWrapper();
    var toolbar = this.xiorkFlow.getToolBar();
    
  var duplicateNames = findDuplicateNames(wrapper.getModel());
  if (duplicateNames != "")
  {
    infoDialog
      ("Cannot save the process definition; there are duplicate node names: "+duplicateNames
       +". \n\nPlease correct the names and then choose Save As again.");
    return;
  }
    
    
    var name = wrapper.getModel().getName();
    saveProcessConsiderDupleName(this.xiorkFlow, false);
 
};

function findDuplicateNames(xiorkFlowModel)
{
	var names = {};
  var duplicateNames = "";
  var metaNodeModels = xiorkFlowModel.getMetaNodeModels();
  
  for (var i = 0; i < metaNodeModels.size(); i++) {
  	var name = metaNodeModels[i].getText();
  	if (names[name] == null) {
  		names[name] = name;
  	}        
  	else {
  		if (duplicateNames != "")
  		  duplicateNames += ", ";
  		duplicateNames += name;
  	}
  }
  
  return duplicateNames;
}

function CloseActionListener(xiorkFlow) {
    this.xiorkFlow = xiorkFlow;
}

CloseActionListener.prototype.actionPerformed = function (obj) {
    var wrapper = this.xiorkFlow.getWrapper(); 
    if(this.xiorkFlow.getWrapper().isChanged()){
       ShowWorkFlowCloseDialog(this.xiorkFlow);
    }
    else
    { 
        exitwindow();   
    } 
};

function IsChangeWorkFlow(xiorkFlowModel){
    return true;
}
