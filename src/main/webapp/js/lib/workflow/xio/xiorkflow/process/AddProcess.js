function AddProcess(wrapper, toolbar, processList) {
    //this.base = Ajax;
    //this.base();
    this.wrapper = wrapper;
    this.toolbar = toolbar;
    this.processList = processList;
}

//AddProcess.prototype = new Ajax();
AddProcess.prototype.setButtonEnable = function (b) {
    if (this.toolbar) {
        this.toolbar.setButtonEnable(b);
    }
};
AddProcess.prototype.addProcess = function (name) {
    if (!name) {
        this.name = null;
        infoDialog("Name for this workflow definiton must NOT be empty.");
        return false;
    }
    this.name = name;

    var model = this.wrapper.getModel();
	model.setName(name);
	var startNodeCount=0;
	var endNodeCount=0;
	var metaNodeModels = model.getMetaNodeModels();
    for (var i = 0; i < metaNodeModels.size(); i++) {
        var metaNodeModel = metaNodeModels.get(i);
        if(metaNodeModel.type=="START_NODE"){
          startNodeCount=startNodeCount+1;
        }
        if(metaNodeModel.type=="END_NODE"){
          endNodeCount=endNodeCount+1;
        }
    }
	if(startNodeCount==0){
	    infoDialog("One STARTNODE must be added.");
	    return false;
	}
	if(startNodeCount>1){
	    infoDialog("Only one STARTNODE can be added.");
	    return false;
	}
	if(endNodeCount==0){alert(3);
	    infoDialog("One ENDNODE must be added.");
	    return false;
	}
	if(endNodeCount>1){
	    infoDialog("Only one ENDNODE can be added.");
	    return false;
	}
	
	var doc = JbpmFlowModelConverter.convertModelToXML(model);
	var schema = XiorkFlowModelConverter.convertModelToXML(model);
    
	//infoDialog(doc.xml);
	//infoDialog(schema.xml);
	
    if (!doc) {
        infoDialog("An error occurred converting to XML.");
        this.setButtonEnable(true);
        return false;
    }

    //
    //var url = XiorkFlowWorkSpace.URL_ADD_PROCESS;
    //var method = "POST";
    //var params = "name=" + name;
    //params += "&xml=" + doc.xml;
	Save_Click(doc.xml,schema.xml,this.wrapper);

    //
    //this.loadXMLHttpRequest(url, method, params);
};
AddProcess.prototype.onReadyStateChange = function (httpRequest) {
    if (httpRequest.readyState == 4) {
        if (httpRequest.status == 200) {
            this.processXMLHttpRequest(httpRequest);
        } else {
            infoDialog("An error occurred processing request.");
            this.setButtonEnable(true);
        }
    }
};

AddProcess.prototype.processXMLHttpRequest = function (httpRequest) {
    var doc = httpRequest.responseXML;
    if (!doc) {
        infoDialog("Unknown error occurred.");
        this.setButtonEnable(true);
        return false;
    }

    //
    var responseNode = doc.getElementsByTagName("Response")[0];
    var statusValue = eval(responseNode.getAttribute("status"));
    var alertStr = "";
    switch (statusValue) {
      case XiorkFlowWorkSpace.STATUS_SUCCESS:
        alertStr = "Save sucessed";
        this.wrapper.getModel().setName(this.name);
        if (this.processList) {
            this.processList.refreshProcessList();
        }
        this.wrapper.setChanged(false);
        break;
      case XiorkFlowWorkSpace.STATUS_FAIL:
        alertStr = "Save failed";
        break;
      case XiorkFlowWorkSpace.STATUS_FILE_EXIST:
        alertStr = "Save failed. File with same name exists.";
        break;
      case XiorkFlowWorkSpace.STATUS_XML_PARSER_ERROR:
        alertStr = "Save failed. XML parsing error";
        break;
      case XiorkFlowWorkSpace.STATUS_IO_ERROR:
        alertStr = "Save failed. IO erro";
        break;
      default:
        alertStr = "Save failed. Unknew";
        break;
    }
    this.setButtonEnable(true);
    infoDialog(alertStr);
};

