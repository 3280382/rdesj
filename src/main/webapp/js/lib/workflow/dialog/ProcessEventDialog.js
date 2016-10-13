var processViewModel;

function setProcessViewModel(model){
	processViewModel = model;
}

function ProcessEventDialog(model) {
	this.model = model;
	setProcessViewModel(model);
	var did = "ProcessEventDialog";
	var tid = did + "_tabs";
	$(document.body).append(getVerticalTagCSS());
	$(document.body)
			.append(
					'<div id="'
							+ did
							+ '">'
							+ '<div id="'
							+ tid
							+ '" class="ui-tabs-vertical">'

							+ '<ul class="ui-tabs-v-nav">'
							+ '<li><a href="javascript:changeProcessTab(\'ProcessGeneralTab\');">General</a></li>'
							+ '<li><a href="javascript:changeProcessTab(\'ProcessEventTab\');">Events</a></li>'
							+ '<li><a href="javascript:changeProcessTab(\'ProcessSwimlaneTab\');">Swimlane</a></li>'
							+ '</ul>'
							
							+ '<div id="ProcessGeneralTab" class="ui-tabs-v-panel">' 
							+ '<span><b>General properties setting for Process.</b></span>'
							+ '<br><hr/>'
							+ '<table border="0" width="100%">' 
							+ '<tr>' 
							+ '<td width="20%">Process Name</td>' 
							+ '<td width="80%" align=left><input name="process_name" id="ProcessName"></td>' 
							+ '</table>' 
							+ '</div>'
							
							+ '<div id="ProcessEventTab" class="ui-tabs-v-panel">' 
							+ '<span><b>Process Event setting.</b></span>'
							+ '<br><hr/>'
							+ '<table border="0" width="100%">' 
							+ '<tr>' 
							+ '<td width="20%">Before Signal</td>' 
							+ '<td width="80%">'
							+ '<select name="Beforesignal" id="ProcessEventBeforeSignal" size="1">' 
							+ '<option value="" selected>Please select an event handler</option>' 
							+ '<option value="com.cicl.frame.workflow.delivery.action.BeforeSignalEventHandler">BeforeSignalEnterHandler</option>' 
							+ '</select>' 
							+ '</td>' 
							+ '</tr>' 
							+ '<tr>' 
							+ '<td width="20%">After Signal</td>' 
							+ '<td width="80%">'
							+ '<select name="Aftersignal" id="ProcessEventAfterSignal" size="1">' 
							+ '<option value="" selected>Please select an event handler</option>' 
							+ '<option value="com.cicl.frame.workflow.delivery.action.AfterSignalEventHandler">AfterSignalEnterHandler</option>' 
							+ '</select>' 
							+ '</td>' 
							+ '</tr>' 
							+ '<tr>' 
							+ '<td width="20%">Node Enter</td>'  
							+ '<td width="80%">'
							+ '<select name="Nodeenter" id="ProcessEventNodeEnter" size="1">' 
							+ '<option value="" selected>Please select an event handler</option>' 
							+ '<option value="com.cicl.frame.workflow.delivery.action.NodeEnterEventHandler">NodeLeaveEnterHandler</option>' 
							+ '</select>' 
							+ '</td>' 
							+ '</tr>' 
							+ '<tr>' 
							+ '<td width="20%">Node Leave</td>' 
							+ '<td width="80%">'
							+ '<select name="Nodeleave" id="ProcessEventNodeLeave" size="1">' 
							+ '<option value="" selected>Please select an event handler</option>' 
							+ '<option value="com.cicl.frame.workflow.delivery.action.NodeLeaveEventHandler">NodeLeaveEventHandler</option>' 
							+ '</select>' 
							+ '</td>' 
							+ '</tr>' 
							+ '<tr>' 
							+ '<td width="20%">Process Start</td>' 
							+ '<td width="80%">'
							+ '<select name="Processstart" id="ProcessEventProcessStart" size="1">' 
							+ '<option value="" selected>Please select an event handler</option>' 
							+ '<option value="com.cicl.frame.workflow.delivery.action.ProcessStartEventHandler">ProcessStartEventHandle</option>' 
							+ '</select>' 
							+ '</td>' 
							+ '</tr>' 
							+ '<tr>' 
							+ '<td width="20%">Process End</td>' 
							+ '<td width="80%">' 
							+ '<select name="Processend" id="ProcessEventProcessEnd" size="1">' 
							+ '<option value="" selected>Please select an event handler</option>' 
							+ '<option value="com.cicl.frame.workflow.delivery.action.ProcessEndEventHandler">ProcessEndEventHandle</option>' 
							+ '</select>' 
							+ '</td>' 
							+ '</tr>' 
							+ '</table>' 
							+ '</div>' 
							
							+ '<div id="ProcessSwimlaneTab" class="ui-tabs-v-panel">' 
							+ '<span><b>Swimlane(process role).</b></span>'
							+ '<br><hr/>'
							+ '<table border="0" width="100%">'
							+ '<tr>'
							+ '<td width="80%" valign="top">'
							+ '<table border="1" width="100%" id="ProcessSwimlaneTable">' 
							+ '<tr>' 
							+ '<td width="25%">Name</td>' 
							+ '<td width="70%">Class</td>'
							+ '<td width="5%">...</td>'
							+ '</table>' 
							+ '</td>'
							+ '<td>'
							+ '<input type="button"  value=" Add " onclick="addSwimlane();">'
							+ '<input type="button"  value="Remove" onclick="removeSwimlane();">'
							+ '</td>'
							+ '</tr>'
							+ '</table>'
							+ '</div>'
							
							+ '</div>' // end of tabs div
							+ '</div>'); // end of dialog div
	
	this.show = function() {
		var parent = this;
		var dialog = $("#" + did).dialog( {
			title : "Process Event Properties",
			width : 680,
			height : 300,
			buttons : {
				"Apply" : function() {
					parent.model.action.before_signal = $("#ProcessEventBeforeSignal").val();
					parent.model.action.after_signal = $("#ProcessEventAfterSignal").val();
					parent.model.action.node_enter = $("#ProcessEventNodeEnter").val();
					parent.model.action.node_leave = $("#ProcessEventNodeLeave").val();
					parent.model.action.process_start = $("#ProcessEventProcessStart").val();
					parent.model.action.process_end = $("#ProcessEventProcessEnd").val();
					
					parent.model.setName($("#ProcessName").val());
					
					parent.model.swimlanes = getSwimlanesFromView();
					
					dialog.dialogClose();
				},
				"Close" : function() {
					dialog.dialogClose();
				}
			}
		});
	};

	this.populate = function() {
		$("#ProcessName").val(model.getName());
		$("#ProcessEventBeforeSignal").val(model.action.before_signal);
		$("#ProcessEventAfterSignal").val(model.action.after_signal);
		$("#ProcessEventNodeEnter").val(model.action.node_enter);
		$("#ProcessEventNodeLeave").val(model.action.node_leave);
		$("#ProcessEventProcessStart").val(model.action.process_start);
		$("#ProcessEventProcessEnd").val(model.action.process_end);
		
		for(var i=0; i<model.swimlanes.length; i++){
			var swm = model.swimlanes[i];
			addSwimlane(swm.name, swm.assignment);
		}
		
		changeProcessTab('ProcessGeneralTab');
	};
}

function changeProcessTab(_tabId){
	var tabShowed = document.getElementById(_tabId);
	if(tabShowed){
		var tabs = new Array();
		var tab = document.getElementById("ProcessGeneralTab");
		if(tab) tabs[tabs.length] = tab;
		tab = document.getElementById("ProcessEventTab");
		if(tab) tabs[tabs.length] = tab;
		tab = document.getElementById("ProcessSwimlaneTab");
		if(tab) tabs[tabs.length] = tab;
		
		for(var i=0;i<tabs.length;i++){
			var aTab = tabs[i];
			if(aTab.id==_tabId){
				aTab.style.display = "block";
			}else{
				aTab.style.display = "none";
			}
		}
	}
}

function addSwimlane(name, assignment) {
	var processSwimlaneTable = document.getElementById("ProcessSwimlaneTable");
	var row = processSwimlaneTable.insertRow();
	var cell_name = row.insertCell();
	var cell_assignment = row.insertCell();
	var cell_chk = row.insertCell();
	

	var ipt_name = document
				.createElement("<input type='text' name='process_swimlane_name' size='8'>");
	var ipt_assignment = document
				.createElement("<input type='text' name='swimlane_assignment_class' size='40'>");
	var ipt_chk = document
				.createElement("<input type='checkbox' name='process_swimlane_chk'>");
		
	if (name)
		ipt_name.value = name;
	if(assignment)
		ipt_assignment.value = assignment;
		
	cell_name.appendChild(ipt_name);
	cell_assignment.appendChild(ipt_assignment);
	
	cell_chk.appendChild(ipt_chk);
}

function removeSwimlane() {
	var processSwimlaneTable = document.getElementById("ProcessSwimlaneTable");
	var objs = document.getElementsByName("process_swimlane_chk");
	var index = 0;
	for (i = 0; i < objs.length; i++) {
		if (objs[i].checked) {
			index = objs[i].parentElement.parentElement.rowIndex;
			processSwimlaneTable.deleteRow(index);
			i--;
		}
	}
}

function getSwimlanesFromView(){
	var objs_name = document.getElementsByName("process_swimlane_name");
	var objs_assignment = document.getElementsByName("swimlane_assignment_class");
	
	for (i = 0; i < objs_name.length; i++) {
		if (objs_name[i].value != "") {
			var swimlane = new Swimlane();
			swimlane.name = objs_name[i].value;
			swimlane.assignment = objs_assignment[i].value;
			processViewModel.addSwimlane(swimlane);
		}
	}
	
	return processViewModel.swimlanes;
}