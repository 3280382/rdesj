<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery-1.5.1.min.js"></script>

<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/Workspace.js"></script>

<!-- name.xio.util -->
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

<!-- name.xio.xiorkflow.meta -->
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

<!-- task node -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/TaskNodeModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/meta/TaskNode.js"></script>
	
<!-- name.xio.xiorkflow.event -->
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
	
<!-- name.xio.xiorkflow.process -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/process/AddProcess.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/process/GetProcess.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/process/UpdateProcess.js"></script>

<!-- name.xio.xiorkflow -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowToolBar.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/StateMonitor.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/TransitionMonitor.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowViewer.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowTableViewer.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowXMLViewer.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/StatusLabel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowModel.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowModelConverter.js"></script>

<!-- load jbpm js -->
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/JbpmFlowModelConverter.js"></script>

<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlow.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowWrapper.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/workflow/xio/xiorkflow/XiorkFlowViewPattern.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/workflow/xio/ui/ui.css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/workflow/xio/xiorkflow/xiorkflow.css"></link>

<script>
function init() {
    Message.setOutter($("#message"));

    //
    var designerDiv = $("#designer");
    
    var xiorkFlow = new XiorkFlow(designerDiv);
    xiorkFlow.setProcessList(window.dialogArguments);

}

if(navigator.appVersion.indexOf("MSIE 6.0")!=-1){ 
    window.onload=init;
}else{ 
    $(document).ready(init);
} 


 /*
  * initialize the page header
  */ 
 function Init_header(){}
 
 /*
  * initialize the page dialog
  */ 
 
 function Init_dialog(){
	    //handle cancel button event
        var handleCancel = function() {
		    this.cancel();
	    }; 
	    
	    //Instantiate the node property dialog 
		propertiesNodeDialog = new YAHOO.widget.Dialog("PropertiesNodeDialog", 
				{ width : "700px",
				  fixedcenter : true,
				  visible : false,    
                  constraintoviewport : true,
                  modal : true,
				  buttons : [ { text:"OK", handler:send, isDefault:true },
						      { text:"Cancel", handler:handleCancel } ]
				 } );	
        propertiesNodeDialog.render();
        
        //Instantiate the task node property dialog 
    	propertiesTaskNodeDialog = new YAHOO.widget.Dialog("PropertiesTaskNodeDialog", 
				{ width : "700px",
				  fixedcenter : true,
				  visible : false,    
                  constraintoviewport : true,
                  modal : true,
				  buttons : [ { text:"OK", handler:taskSend, isDefault:true },
						      { text:"Cancel", handler:handleCancel } ]
				 } );	
        propertiesTaskNodeDialog.render();
        
        //Instantiate the event node property dialog 
    	processEventDialog = new YAHOO.widget.Dialog("ProcessEventDialog", 
				{ width : "700px",
				  fixedcenter : true,
				  visible : false,    
                  constraintoviewport : true,
                  modal : true,
				  buttons : [ { text:"OK", handler:taskSend, isDefault:true },
						      { text:"Cancel", handler:handleCancel } ]
				 } );	
        processEventDialog.render();
        
        //Instantiate the decision node property dialog
    	propertiesDecisionNodeDialog = new YAHOO.widget.Dialog("PropertiesDecisionNodeDialog", 
				{ width : "700px",
				  fixedcenter : true,
				  visible : false,    
                  constraintoviewport : true,
                  modal : true,
				  buttons : [ { text:"OK", handler:decisionSend, isDefault:true },
						      { text:"Cancel", handler:handleCancel } ]
				 } );	
        propertiesDecisionNodeDialog.render();
        
        //Instantiate the fork node property dialog
        propertiesForkNodeDialog = new YAHOO.widget.Dialog("PropertiesForkNodeDialog", 
                { width : "700px",
                  fixedcenter : true,
                  visible : false,    
                  constraintoviewport : true,
                  modal : true,
				  buttons : [ { text:"OK", handler:forkSend, isDefault:true },
						      { text:"Cancel", handler:handleCancel } ]
                 } ); 
        propertiesForkNodeDialog.render();


        propertiesJoinNodeDialog = new YAHOO.widget.Dialog("PropertiesJoinNodeDialog", 
                { width : "700px",
                  fixedcenter : true,
                  visible : false,    
                  constraintoviewport : true,
                  modal : true,
        		  buttons : [ { text:"OK", handler:joinSend, isDefault:true },
        					  { text:"Cancel", handler:handleCancel } ]
                 } ); 
        propertiesJoinNodeDialog.render();
 }
</script>
</head>
<body>
	<div id="designer" style="width: 100%;height: 90%;border: #e0e0e0 1px solid;"></div>
	<div id="alertdialog-panel" style="visibility:hidden"></div>
</body>
</html>

