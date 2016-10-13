
function GetProcess(wrapper, tableViewer, toolbar, doc, jbpmDoc) {
    this.wrapper = wrapper;
    this.toolbar = toolbar;
    this.tableViewer = tableViewer;
    
	//add by Ray li
	this.doc = doc;
	this.jbpmDoc= jbpmDoc;
	
	// get the process name
	var pd = GetElementByTagName(this.jbpmDoc,"process-definition");
	var name = pd.getAttribute("name");

    var editable = this.wrapper.getModel().isEditable();
    var model = XiorkFlowModelConverter.convertXMLToModel(doc, jbpmDoc);
    model.setName(name);
    model.setEditable(editable);
    this.wrapper.setModel(model);
    this.wrapper.setChanged(false);
    if (this.tableViewer) {
        this.tableViewer.setModel(model);
    }
    this.setButtonEnable(true);
}

GetProcess.prototype.setButtonEnable = function (b) {
    if (this.toolbar) {
        this.toolbar.setButtonEnable(b);
    }
};


GetProcess.prototype.processXMLHttpRequest = function (httpRequest) {
    if (!doc) {
        infoDialog("Operation end. Unknew result from server.");
        this.setButtonEnable(true);
        return false;
    }

    //
    var responseNodes = doc.getElementsByTagName("Response");
    if ((responseNodes != null) && (responseNodes.length > 0)) {
        var responseNode = responseNodes[0];
        var statusValue = eval(responseNode.getAttribute("status"));
        var alertStr = "";
        switch (statusValue) {
          case XiorkFlowWorkSpace.STATUS_FAIL:
            alertStr = "Failed to fetch the process defintion";
            break;
          case XiorkFlowWorkSpace.STATUS_XML_PARSER_ERROR:
            alertStr = "Faield to pase the xml.";
            break;
          case XiorkFlowWorkSpace.STATUS_IO_ERROR:
            alertStr = "Failed to fetch the result for IO error.";
            break;
          case XiorkFlowWorkSpace.STATUS_FILE_NOT_FOUND:
            alertStr = "Faield to fetch result for file not found.";
            break;
          default:
            alertStr = "Faield to fetch result for unknew error.";
            break;
        }
        this.setButtonEnable(true);
        infoDialog(alertStr);
    } else {
		var jbpmDoc = new ActiveXObject("Msxml2.DOMDocument");
        jbpmDoc.loadXML("<process-definition name=\"dd\" xmlns=\"urn:jbpm.org:jpdl-3.1\"><start-state name=\"StartNode\"><transition name=\"\" to=\"Node\"/></start-state><end-state name=\"EndNode\"/><node name=\"Node\"><action class=\"com.ctit.test.class\"><nodevar1>nodevar1value1</nodevar1><nodevar2>nodevar1value2</nodevar2></action><transition name=\"\" to=\"TaskNode\"/></node><task-node name=\"TaskNode\"><task name=\"task_test\"><controller><variable name=\"tasknodevar1\" access=\",,\"/><variable name=\"tasknodevar2\" access=\",write,\"/><variable name=\"tasknodevar3\" access=\"read,,required\"/></controller></task><transition name=\"\" to=\"EndNode\"/></task-node></process-definition>");
        var editable = this.wrapper.getModel().isEditable();
        var model = XiorkFlowModelConverter.convertXMLToModel(doc, jbpmDoc);
        model.setName(this.name);
        model.setEditable(editable);
        this.wrapper.setModel(model);
        this.wrapper.setChanged(false);
        if (this.tableViewer) {
            this.tableViewer.setModel(model);
        }
        this.setButtonEnable(true);
    }
};

function GetElementByTagName(node, name)
{
	if(name.indexOf(":")<0||window.ActiveXObject)
	{
		var elems = node.getElementsByTagName(name);
		if(elems&&elems.length>0)
			return elems[0];	
	}
	else
	{
		var elems = node.getElementsByTagNameNS("http://www.reallysi.com",name.substring(name.indexOf(":")+1,name.length));
		if(elems&&elems.length>0)
			return elems[0];	
	}
}