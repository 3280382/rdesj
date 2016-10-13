function UpdateProcess(wrapper, toolbar) {
    this.base = Ajax;
    this.base();
    this.wrapper = wrapper;
    this.toolbar = toolbar;
}

UpdateProcess.prototype = new Ajax();

UpdateProcess.prototype.setButtonEnable = function (b) {
    if (this.toolbar) {
        this.toolbar.setButtonEnable(b);
    }
};
UpdateProcess.prototype.updateProcess = function () {
    var model = this.wrapper.getModel();
    var name = model.getName();
    if (!name) {
        this.name = null;
        infoDialog("Name is empty for this definition.");
        return false;
    }

    //
    this.setButtonEnable(false);
    
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
	if(endNodeCount==0){
	    infoDialog("One ENDNODE must be added.");
	    return false;
	}
	if(endNodeCount>1){
	    infoDialog("Only one ENDNODE can be added.");
	    return false;
	}

	var doc = JbpmFlowModelConverter.convertModelToXML(model);
    if (!doc) {
        infoDialog("Exception while parsing the graphic to xml");
        this.setButtonEnable(true);
        return false;
    }

    //
    var url = XiorkFlowWorkSpace.URL_UPDATE_PROCESS;
    var method = "POST";
    var params = "name=" + name;
    params += "&xml=" + doc.xml;
    infoDialog(doc.xml);
};

UpdateProcess.prototype.onReadyStateChange = function (httpRequest) {
    if (httpRequest.readyState == 4) {
        if (httpRequest.status == 200) {
            this.processXMLHttpRequest(httpRequest);
        } else {
            infoDialog("Exception while handle the request");
            this.setButtonEnable(true);
        }
    }
};
UpdateProcess.prototype.processXMLHttpRequest = function (httpRequest) {
    var doc = httpRequest.responseXML;
    if (!doc) {
        infoDialog("Operation end with unknew exception");
        this.setButtonEnable(true);
        return false;
    }

    var responseNode = doc.getElementsByTagName("Response")[0];
    var statusValue = eval(responseNode.getAttribute("status"));
    var alertStr = "";
    switch (statusValue) {
      case XiorkFlowWorkSpace.STATUS_SUCCESS:
        alertStr = "Update sucessfully";
        this.wrapper.getModel().setName(this.name);
        this.wrapper.setChanged(false);
        break;
      case XiorkFlowWorkSpace.STATUS_FAIL:
        alertStr = "Update failed";
        break;
      case XiorkFlowWorkSpace.STATUS_XML_PARSER_ERROR:
        alertStr = "Update faield with xml parsing error";
        break;
      case XiorkFlowWorkSpace.STATUS_FILE_NOT_FOUND:
        document.title = "New";
        alertStr = "Update failed with find not found exception. Swith to 'add' style automatically.";
        break;
      case XiorkFlowWorkSpace.STATUS_IO_ERROR:
        alertStr = "Update failed with IO error";
        break;
      default:
        alertStr = "Update failed with unknew exception.";
        break;
    }
    this.setButtonEnable(true);
    infoDialog(alertStr);
};

