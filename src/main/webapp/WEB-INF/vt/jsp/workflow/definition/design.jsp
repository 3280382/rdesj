<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>ELITES-Workflow Designer</title>
<!--  TO BE INTEGRATED TO WORKFLOW COMPONENT -->
<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.bgiframe.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery-ui-1.8.12.custom/development-bundle/ui/jquery-ui-1.8.12.custom.js"></script>

<script type="text/javascript" src="${ctx}/js/com/jquery.dialog.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.button.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/js/com/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/jquery-com-ui.css" />
<!-- END -->

<script type="text/javascript">
/*----------------------------global variables declaration-----------------------*/  
var propertiesNodeDialog = null;
var propertiesTaskNodeDialog = null;
var propertiesDecisionNodeDialog = null;
var propertiesForkNodeDialog = null;
var propertiesJoinNodeDialog = null;
var postLoadDialog = null;
var processEventDialog = null;
var metadataDefinitionDialog = null;
var nodeValuesDialog = null;
var workFlowCloseDialog = null;
var xiorkFlow_close = null;
var workFlowSaveDialog = null;
var xiorkFlow_save = null;
var workflowName_save = null;
var doClose_save = null;
var closeFieldDefinitionDialog = null;

var processDefinitionList;

function XiorkFlowWorkSpace() {}
XiorkFlowWorkSpace.BASE_PATH = "${ctx}";
XiorkFlowWorkSpace.XIORK_FLOW_PATH = XiorkFlowWorkSpace.BASE_PATH + "/js/lib/workflow/xio/";
XiorkFlowWorkSpace.DEFAULT_PROCESS_NAME = "default";
XiorkFlowWorkSpace.URL_ADD_PROCESS = XiorkFlowWorkSpace.BASE_PATH + "addprocess.xf";
XiorkFlowWorkSpace.URL_DELETE_PROCESS = XiorkFlowWorkSpace.BASE_PATH + "deleteprocess.xf";
XiorkFlowWorkSpace.URL_GET_PROCESS = XiorkFlowWorkSpace.BASE_PATH + "getprocess.xf";
XiorkFlowWorkSpace.URL_LIST_PROCESS = XiorkFlowWorkSpace.BASE_PATH + "listprocess.xf";
XiorkFlowWorkSpace.URL_UPDATE_PROCESS = XiorkFlowWorkSpace.BASE_PATH + "updateprocess.xf";
XiorkFlowWorkSpace.STATUS_NULL = -1;
XiorkFlowWorkSpace.STATUS_SUCCESS = 0;
XiorkFlowWorkSpace.STATUS_FAIL = 1;
XiorkFlowWorkSpace.STATUS_FILE_EXIST = 3;
XiorkFlowWorkSpace.STATUS_FILE_NOT_FOUND = 5;
XiorkFlowWorkSpace.STATUS_XML_PARSER_ERROR = 7;
XiorkFlowWorkSpace.STATUS_IO_ERROR = 9;
XiorkFlowWorkSpace.ID = 1;
XiorkFlowWorkSpace.META_NODE_MOVED_STEP_TIME = 100;
XiorkFlowWorkSpace.META_NODE_MOVED_STEP = 1;
XiorkFlowWorkSpace.META_NODE_MAX = 100;
XiorkFlowWorkSpace.META_NODE_MIN_WIDTH = 80;
XiorkFlowWorkSpace.META_NODE_MIN_HEIGHT = 30;

</script>
<!-- name.xio.util  -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/util/Message.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/util/Array.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/util/String.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/util/List.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/util/Observable.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/util/Observer.js"></script>

<!-- name.xio.geom -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/geom/Point.js"></script>

<!-- name.xio.html -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/html/Toolkit.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/html/Browser.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/html/Cursor.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/html/MouseEvent.js"></script>

<!-- name.xio.xml -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xml/XMLDocument.js"></script>

<!-- name.xio.ajax -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ajax/Ajax.js"></script>

<!-- name.xio.ui.event -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/event/KeyListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/event/MouseListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/event/MouseWheelListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/event/ContextMenuListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/event/ListenerProxy.js"></script>

<!-- name.xio.ui -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/Dimension.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/Component.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/Button.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/ButtonModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/ToggleButton.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/ToggleButtonModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/ButtonGroup.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/ToolBar.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/Panel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/ScrollPanel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/Label.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/ui/Frame.js"></script>

<!-- name.xio.geom.ui -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/geom/ui/GeometryCanvas.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/geom/ui/LineView.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/geom/ui/LineTextView.js"></script>

<!-- name.xio.xiorkflow.meta.event -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/event/MetaNodeMouseListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/event/MetaNodeTextMouseListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/event/MetaNodeTextKeyListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/event/TransitionMouseListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/event/MetaNodeResizeMouseListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/event/TransitionTextMouseListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/event/TransitionTextKeyListener.js"></script>

<!--  name.xio.xiorkflow.meta -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/DragablePanel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/MetaModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/MetaNodeModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/MetaNode.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/StartNodeModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/StartNode.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/EndNodeModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/EndNode.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/NodeModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/Node.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/ForkNodeModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/ForkNode.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/JoinNodeModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/JoinNode.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/DecisionNodeModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/DecisionNode.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/TransitionCompass.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/TransitionModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/Transition.js"></script>
<!--  task node -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/TaskNodeModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/TaskNode.js"></script>

<!--  name.xio.xiorkflow.event -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/event/WrapperMetaMouseListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/event/WrapperSelectMouseListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/event/MetaMoveMouseListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/event/MetaMoveKeyListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/event/WrapperTransitionMouseListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/event/DeleteMetaActionListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/event/SaveActionListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/event/HelpActionListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/event/NewActionListener.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/event/ProcessEventListener.js"></script>


<!--  name.xio.xiorkflow.process -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/process/AddProcess.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/process/GetProcess.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/process/UpdateProcess.js"></script>

<!--  name.xio.xiorkflow -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowToolBar.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/StateMonitor.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/TransitionMonitor.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowViewer.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowTableViewer.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowXMLViewer.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/StatusLabel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowModelConverter.js"></script>

<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/JbpmFlowModelConverter.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlow.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowWrapper.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowViewPattern.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/workflow/xio/xiorkflow/xiorkflow.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/workflow/xio/ui/ui.css" />


<script type="text/javascript" src="${ctx}/js/lib/workflow/dialog/DialogConstants.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/dialog/ProcessEventDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/dialog/TaskNodePropertyDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/dialog/NodePropertyDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/dialog/ForkNodePropertyDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/dialog/JoinNodePropertyDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/dialog/DecisionNodePropertyDialog.js"></script>

<script type="text/javascript" src="${ctx}/js/lib/workflow/dialog/AlertDialog.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/dialog/PropertyDivFactory.js"></script>

<script>

	jQuery.createXMLDocument = function(string){
		var browserName = navigator.appName;
		var doc;
		if (browserName == 'Microsoft Internet Explorer'){
			doc = new ActiveXObject('Microsoft.XMLDOM');
			doc.async = 'false'
			doc.loadXML(string);
		} else {
			doc = (new DOMParser()).parseFromString(string, 'text/xml');
		}
		return doc;
	}

	
	function Save_Click(pdxml,schemaxml,wrapper){
		$.post("deploy.json",{xml:pdxml, schema:schemaxml}, 
				function(data, status){
					if(data.msg){ 
						infoDialog("Save successfully.");
					}else{
						if(data.errorsMsg){ 
							infoDialog(data.errorsMsg);
						}else{
							infoDialog("Unknown result.");
						}
					}
				}, 
				"json"); 
	}
	
	// on document ready (jquery)
	$(function(){
		$.getJSON("fetchSchema.json",{id:${definitionID}}, function(data, status){
			var designerDiv = Toolkit.getElementByID("designer");
			var xiorkFlow = new XiorkFlow(designerDiv);
			xiorkFlow.getToolBar().setButtonEnable(false);
				
			if (data.xml) {
				 var doc = $.createXMLDocument(data.schema);
				 var jbpmDoc = $.createXMLDocument(data.xml);
				 
			     var getProcess = new GetProcess(xiorkFlow.getWrapper(), xiorkFlow.getTableViewer(), xiorkFlow.getToolBar(), doc, jbpmDoc);
			 } else {
			     document.title = "-Untitle-";
			     xiorkFlow.getToolBar().setButtonEnable(true);
			 }
			}); 
	});
</script>
</head>
<body onselectstart="return false;" style="margin: 0px;"> 
	<div id="designer" style="width: 100%; height: 90%; border: #e0e0e0 1px solid;"></div>
	<div id="alertdialog-panel" style="display:none">
	
		<!--  properties dialog index -->
				
		<!-- Properties Dialog for Decision Node -->
		

		<!-- Action handler Dialog -->
		<div id="ActionhandlerDialog">
			<div class="hd">Properties</div>
			<div class="bd">
				<table border="0" width="100%" id="table1" height="341">
					<tr>
						<td valign=top>
							<table border="0" width="100%">
								<tr>
									<td>Name</td>
									<td align=left><input type=text name="ah_name" id="ah_name"></td>
								</tr>
								<tr>
									<td width="10%">Type</td>
									<td width="90%" align=left>
										<select name="ah_type" id="ah_type" size="1">
											<option value="normal">Normal</option>
											<option value="exception">Exception</option>
										</select>
									</td>
								</tr>	
								<tr>
									<td>Class</td>
									<td align=left>
					                <input type=text size=43 name="ah_class" id="ah_class">
									</td>
								</tr>	
							</table>
							<table border="0" width="778">
								<tr>
									<td width="235">Define the used variables:</td>
									<td></td>
								</tr>
								<tr>
									<td height="22" width="235">
									<table border="1" cellpadding=0 cellspacing=0 width="100%" id="ah_variables" name=tb1 bgcolor="#FFFFFF" bordercolor="#C0C0C0">
								<tr>
									<td width="80%">Name</td>
									<td></td>
								</tr>
							</table>
						</td>
						<td height="22" valign=top>
							<table border="0" width="100%">
								<tr>
									<td><input type=button  value="   Add   " onclick="ah_addVariable()"></td>
								</tr>
								<tr>
									<td><input type=button  value="Remove" onclick="ah_removeVarible()"></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>					
			
				<div align="center">
					<input type=button value=" Save " onclick="SetActionhandler()"><font color="#FF0000"><label id="ah_message"></label></font>
				</div>
			</div>	
		</div>

		<!-- Node Value Dialog -->
		<div id="NodeValuesDialog">
		  	<div class="hd">Value List</div>
		 	<div class="bd">
				<table border="0" width="100%">
					<tr>
				    	<td width="235"><label id="valuelisttitle">Add a new value to the variable: </label></td>
					</tr>
					<tr>
				  		<td height="22" width="80%">
							<table border="1" cellpadding=0 cellspacing=0 width="100%" id="nodeValueList" name=tb2 bgcolor="#FFFFFF" bordercolor="#C0C0C0">
				        		<tr>
					      			<td width="80%">values</td>	
				        		</tr>
			         		</table>
					 		<input type=button value=" OK " onclick="setNodeValues()">
			      		</td>
					</tr>
				</table>
		  	</div>	
		</div>
	
	</div>
</body>
</html>
