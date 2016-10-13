function getNodeEventPropertyDiv() {
	var divHTML = '<div>'
			+ '<span><b>Event setting.</b></span>'
			+ '<br><hr/>'
			+ '<table border="0" width="100%">'
			+ '<tr>'
			+ '<td width="20%">node-enter</td>'
			+ '<td width="80%">'
			+ '<select name="Nodeenter" id="'
			+ FID_EVENT_NODE_ENTER
			+ '" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.delivery.action.NodeEnterEventHandler">NodeLeaveEnterHandler</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">before-signal</td>'
			+ '<td width="80%">'
			+ '<select name="Beforesignal" id="'
			+ FID_EVENT_BEFORE_SIGNAL
			+ '" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.delivery.action.BeforeSignalEventHandler">BeforeSignalEnterHandler</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">after-signal</td>'
			+ '<td width="80%">'
			+ '<select name="Aftersignal" id="'
			+ FID_EVENT_AFTER_SIGNAL
			+ '" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.delivery.action.AfterSignalEventHandler">AfterSignalEnterHandler</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">node-leave</td>'
			+ '<td width="80%">'
			+ '<select name="Nodeleave" id="'
			+ FID_EVENT_NODE_LEAVE
			+ '" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.delivery.action.NodeLeaveEventHandler">NodeLeaveEventHandler</option>'
			+ '</select>' + '</td>' + '</tr>' + '</table>' + '</div>';

	return divHTML;
}

function getTaskEventPropertyDiv() {
	var divHTML = '<div>'
			+ '<span><b>Event setting.</b></span>'
			+ '<br><hr/>'
			+ '<table border="0" width="100%">'
			+ '<tr>'
			+ '<td width="20%">node-enter</td>'
			+ '<td width="80%">'
			+ '<select name="Nodeenter" id="'
			+ FID_EVENT_NODE_ENTER
			+ '" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.delivery.action.NodeEnterEventHandler">NodeLeaveEnterHandler</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">before-signal</td>'
			+ '<td width="80%">'
			+ '<select name="Beforesignal" id="'
			+ FID_EVENT_BEFORE_SIGNAL
			+ '" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.delivery.action.BeforeSignalEventHandler">BeforeSignalEnterHandler</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">after-signal</td>'
			+ '<td width="80%">'
			+ '<select name="Aftersignal" id="'
			+ FID_EVENT_AFTER_SIGNAL
			+ '" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.delivery.action.AfterSignalEventHandler">AfterSignalEnterHandler</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">node-leave</td>'
			+ '<td width="80%">'
			+ '<select name="Nodeleave" id="'
			+ FID_EVENT_NODE_LEAVE
			+ '" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.delivery.action.NodeLeaveEventHandler">NodeLeaveEventHandler</option>'
			+ '</select>' + '</td>' + '</tr>' + '</table>' + '</div>';

	return divHTML;
}
/**
 * 
 */

function getTaskPropertyDiv() {
	var divHTML = '<DIV>'
			+ '<span><b>Task setting.</b></span>'
			+ '<br><hr/>'
			
			+ '<table border="0" width="100%">'
			+ '<tr>'
			+ '<td rowspan="2" width="20%" valign="top">'
			+ '<select size="13" style="width:100px" name="TaskNameList" id="TaskNameList" onchange="changeTask(this);">'
			+ '</select>'
			+ '</td>'
			+ '<td width="80%" valign="top">'
			
			+ getHorizontalTagCSS()
			
			+ '<Div class="ui-tabs-horizontal">'
			+ '<UL class="ui-tabs-h-nav">'
			+ '<LI><A href="javascript:changeTaskTab(\'TaskGeneralTab\');">General</A></LI>'
			+ '<LI><A href="javascript:changeTaskTab(\'TaskAssignmentTab\');">Assignment</A></LI>'
			+ '<LI><A href="javascript:changeTaskTab(\'TaskControllerTab\');">Controller</A></LI>'
			+ '<LI><A href="javascript:changeTaskTab(\'TaskEventTab\');">Event</A></LI>'
			+ '</UL>'
			
			// ** task general tab**//
			+ '<div id="TaskGeneralTab" class="ui-tabs-h-panel" style="display:none">'
			+ getTaskGeneralPropertyContent()
			+ '</div>'

			// ** task assignment tab**//
			+ '<div id="TaskAssignmentTab" class="ui-tabs-h-panel" style="display:none">'
			+ getTaskAssignmentPropertyContent()
			+ '</div>'

			// ** task controller tab**//
			+ '<div id="TaskControllerTab" class="ui-tabs-h-panel" style="display:none">'
			+ getTaskControllerPropertyContent()
			+ '</div>'

			// ** task event tab**//
			+ '<div id="TaskEventTab" class="ui-tabs-h-panel" style="display:none">'
			+ getTaskEventPropertyContent()
			+ '</div>' 
			
			+ '</Div>' //end with task tab
			
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td>'
			+ '<hr/>'
			+ '<input type="button" name="Save Task" value="Save Task" onclick="saveTask()"/>'
			+ '<input type="button" name="Remove Task" value="Remove Task" onclick="removeTask()"/>'
			+ '</td>'
			+ '</tr>'
			+ '</table>'
			+ '</DIV>';
	
	return divHTML;
}

//** task general tab**//
function getTaskGeneralPropertyContent() {
	var contentHTML = '<table border="0" width="100%">'
			+ '<tr>'
			+ '<td width="20%">name</td>'
			+ '<td width="80%"><input name="TaskName" id="TaskName"></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">blocking</td>'
			+ '<td width="80%"><select name="blocking" id="TaskBlocking"><option value="true">true</option><option value="false">false</option></select></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">signalling</td>'
			+ '<td width="80%"><select name="signalling" id="TaskSignalling"><option value="true">true</option><option value="false">false</option></select></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">description</td>'
			+ '<td width="80%"><input name="description" id="TaskDescription"></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">duedate</td>'
			+ '<td width="80%"><input name="duedate" id="TaskDuedate"></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">priority</td>'
			+ '<td width="80%"><select name="priority" id="TaskPriority"><option value="highest">highest</option><option value="high">high</option><option value="normal">normal</option><option value="low">low</option><option value="lowest">lowest</option></select></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">swimlane</td>'
			+ '<td width="80%"><input name="swimlane" id="TaskSwimlane"></td>'
			+ '</tr>'
			+ '</table>';
	return contentHTML;
}

function getTaskAssignmentPropertyContent() {
	var contentHTML = '<table border="0" width="100%">'
			+ '<tr>'
			+ '<td width="20%" valign="top">actor_id</td>'
			+ '<td width="80%" align="left"><input name="TaskAssignmentActorID" id="TaskAssignmentActorID" size="35"></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td width="20%">pooled-actors</td>'
			+ '<td width="80%" align="left"><input name="TaskAssignmentPooledActors" id="TaskAssignmentPooledActors" size="35"></td>'
			+ '</tr>'
			+ '</table>';

	return contentHTML;
}

function getTaskControllerPropertyContent() {
	var contentHTML = '<table border="0" width="100%">'
			+ '<tr>'
			+ '<td height="22" width="80%" valign="top">'
			+ '<table border="1" cellpadding=0 cellspacing=0 width="100%" id="TaskNodeVariableTable" name=tb1 bgcolor="#FFFFFF" bordercolor="#C0C0C0">'
			+ '<tr>'
			+ '<td>Name</td>'
			+ '<td>Read</td>'
			+ '<td>Write</td>'
			+ '<td>Required</td>'
			//+ '<td>MappedName</td>'
			+ '<td></td>'
			+ '</tr>'
			+ '</table>'
			+ '</td>'
			+ '<td height="22" valign=top>'
			+ '<table border="0" width="100%">'
			+ '<tr>'
			+ '<td><input type=button  value=" Add " onclick="addTaskVariable()"></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td><input type=button  value="Remove" onclick="removeTaskVarible()"></td>'
			+ '</tr>' + '</table>' + '</td>' + '</tr>' + '</table>';
	
	return contentHTML;
}

// ** task event tab**//
function getTaskEventPropertyContent() {
	var contentHTML = '<table border="0" width="100%">'
			+ '<tr>'
			+ '<td width="20%">task-create</td>'
			+ '<td width="80%">'
			+ '<select name="TaskCreate" id="TaskCreateEvent" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.event.AssignmentExpressionExecutingHandler">Assgnment expression executinng Handler</option>'
			+ '<option value="com.cicl.project.elites.workflow.event.RecordApproverAction">Record "Approver" ActionHandler</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td width="20%">task-assign</td>'
			+ '<td width="80%">'
			+ '<select name="TaskAssign" id="TaskAssignEvent" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.event.AssignmentExpressionExecutingHandler">Assgnment expression executinng Handler</option>'
			+ '<option value="com.cicl.project.elites.workflow.event.RecordApproverAction">Record "Approver" ActionHandler</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td width="20%">task-start</td>'
			+ '<td width="80%">'
			+ '<select name="TaskStart" id="TaskStartEvent" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.event.AssignmentExpressionExecutingHandler">Assgnment expression executinng Handler</option>'
			+ '<option value="com.cicl.project.elites.workflow.event.RecordApproverAction">Record "Approver" ActionHandler</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			
			+ '<tr>'
			+ '<td width="20%">task-end</td>'
			+ '<td width="80%">'
			+ '<select name="TaskEnd" id="TaskEndEvent" size="1">'
			+ '<option value="" selected>Please select an event handler</option>'
			+ '<option value="com.cicl.frame.workflow.event.AssignmentExpressionExecutingHandler">Assgnment expression executinng Handler</option>'
			+ '<option value="com.cicl.project.elites.workflow.event.RecordApproverAction">Record "Approver" ActionHandler</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			+ '</table>';
	
	return contentHTML;
}

/** Vertical Tabs * */
function getVerticalTagCSS() {
	var cssHTML = '<style type="text/css">'
			+ '.ui-tabs-vertical { width: 55em; }'
			+ '.ui-tabs-vertical .ui-tabs-v-nav { PADDING-BOTTOM: 0.2em; PADDING-LEFT: 0em; WIDTH: 8em; PADDING-RIGHT: 0.1em; FLOAT: left; padding-top: 0.2em; padding-left:0px;margin-left:0px; }'
			+ '.ui-tabs-vertical .ui-tabs-v-nav li { BORDER-RIGHT-WIDTH: 0px !important; MARGIN: 0px -1px -1px 0px; WIDTH: 100%; BORDER-BOTTOM-WIDTH: 1px !important; CLEAR: left; text-align:center;_MARGIN: 0px -1px -1px 0px;_float:left;padding-left:0px;margin-left:0px; }'
			+ '.ui-tabs-vertical .ui-tabs-v-nav li a { DISPLAY: block;  border:1px solid #DDD; font-weight:bold; color:#666666; text-decoration:none;padding:2px 5px; }'
			+ '.ui-tabs-vertical .ui-tabs-v-nav li.ui-tabs-v-selected { PADDING-BOTTOM: 0px; BORDER-RIGHT-WIDTH: 1px; PADDING-RIGHT: 0.1em; }'
			+ '.ui-tabs-vertical .ui-tabs-v-panel { PADDING-BOTTOM: 1em; PADDING-LEFT: 1em; WIDTH: 46em; PADDING-RIGHT: 1em; FLOAT: right; PADDING-TOP: 1em; }'
			+ '.ui-tabs-vertical .ui-tabs-v-hide { display: none !important; }'
			+ '</style>';

	return cssHTML;
}

/** horizontal Tab **/
function getHorizontalTagCSS(){
	var cssHTML = '<STYLE type=text/css>' 
		+ '.ui-tabs-horizontal {WIDTH: 100%;}' 
		+ '.ui-tabs-horizontal .ui-tabs-h-nav {PADDING-BOTTOM: 0em; PADDING-LEFT: 0.2em; WIDTH: 100%; PADDING-RIGHT: 0.1em; FLOAT: left; PADDING-TOP: 0.2em; border-bottom:1px solid #DDD ; list-style:none ;margin-left:0px; padding-left:0px;}' 
		+ '.ui-tabs-horizontal .ui-tabs-h-nav LI { MARGIN: 0px -1px -1px 0px;   float:left; list-style:none ;padding-left:0px;}' 
		+ '.ui-tabs-horizontal .ui-tabs-h-nav LI A {DISPLAY: block;   font-weight:bold; color:#666666; text-decoration:none;border:1px solid #DDD;padding:2px 10px;}' 
		+ '.ui-tabs-horizontal .ui-tabs-h-nav LI.ui-tabs-h-selected {PADDING-BOTTOM: 0px; BORDER-RIGHT-WIDTH: 1px; PADDING-RIGHT: 0.1em; background:red;}' 
		+ '.ui-tabs-horizontal .ui-tabs-h-panel {PADDING-BOTTOM: 1em; PADDING-LEFT: 0em; WIDTH: 40em; PADDING-RIGHT: 1em; FLOAT:left; PADDING-TOP: 1em;}' 
		+ '.ui-tabs-horizontal .ui-tabs-h-hide {DISPLAY: none !important}' 
		+ '</STYLE>';
	return cssHTML;
}