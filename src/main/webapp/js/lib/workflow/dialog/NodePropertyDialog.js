function NodePropertyDialog(model) {
	this.model = model;
	var did = "PropertyNodeDialog";
	var tid = did + "_tabs";
	$(document.body).append(getVerticalTagCSS());
	$(document.body)
			.append(  '<div id="' + did + '">'
					+ '<div id="' + tid + '" class="ui-tabs-vertical">'
					+ '<ul class="ui-tabs-v-nav">' 
					+ '<li><a href="javascript:changeNodeTab(\'NodeGeneralTab\');">General</a></li>' 
					+ '<li><a href="javascript:changeNodeTab(\'NodeActionTab\');">Action</a></li>' 
					+ '<li><a href="javascript:changeNodeTab(\'NodeExceptionTab\');">Exception</a></li>' 
					+ '</ul>' 
					
					// general tab for node
					+ '<div id="NodeGeneralTab" class="ui-tabs-v-panel">' 
					+ '<span><b>General properties setting for node.</b></span>' 
					+ '<br><hr/>' 
					+ '<table border="0" width="90%">' 
					+ '<tr>' 
					+ '<td width="20%">Name</td>' 
					+ '<td width="80%" align=left><input name="NodeName" id="NodeName"></td>' 
					+ '</tr>' 
					+ '</table>' 
					+ '</div>' 
					
					// action tab for node
					+ '<div id="NodeActionTab" class="ui-tabs-v-panel">' 
					+ '<span><b>Action Handler setting for node.</b></span>' 
					+ '<br><hr/>' 
					+ '<table border="0" width="90%">' 
					+ '<tr>' 
					+ '<td width="20%">Action Handler</td>' 
					+ '<td width="80%" align=left>' 
					+ '<select name="NodeActionClass" id="NodeActionClass" size="1" onchange="initVariables(this, variables)">' 
					+ '<option value="" selected>Please select an action handler</option>' 
					+ '</select>' 
					+ '</td>' 
					+ '</tr>' 
					+ '</table>' 
					+ '<table border="0" width="90%">' 
					+ '<tr>' 
					+ '<td width="80%">Define the used variables:</td>' 
					+ '<td width="20%"></td>' 
					+ '</tr>' 
					+ '<tr>' 
					+ '<td height="22" width="20%">' 
					+ '<table border="1" cellpadding=0 cellspacing=0 width="100%" id="ActionVariableTable" name=tb1 bgcolor="#FFFFFF" bordercolor="#C0C0C0">' 
					+ '<tr>' 
					+ '<td width="46%">Name</td>' 
					+ '<td width="46%">Value</td>' 
					+ '<td></td>' 
					+ '</tr>' 
					+ '</table>' 
					+ '</td>' 
					+ '<td height="22" valign=top>' 
					+ '<table border="0" width="90%">' 
					+ '<tr>' 
					+ '<td><input type=button  value="   Add   " onclick="addActionVariable()"></td>' 
					+ '</tr>' 
					+ '<tr>' 
					+ '<td><input type=button  value="Remove" onclick="removeActionVarible()"></td>' 
					+ '</tr>' 
					+ '</table>' 
					+ '</td>' 
					+ '</tr>' 
					+ '</table>' 
					+ '</div>' 
					
					// exception tab for node
					+ '<div id="NodeExceptionTab" class="ui-tabs-v-panel">' 
					+ '<span><b>Node Exception handler setting for node.</b></span>' 
					+ '<br><hr/>' 
					+ '<table border="0" width="90%">' 
					+ '<tr>' 
					+ '<td width="20%">Exception</td>' 
					+ '<td width="80%" align=left>' 
					+ '<select name="NodeExceptionClass" id="NodeExceptionClass" size="1" onchange="initVariables(this, exceptionvariables)">' 
					+ '<option value="" selected>Please select an exception handler</option>' 
					+ '</select></td>' 
					+ '</tr>' 
					+ '</table>' 
					+ '<table border="0" width="90%">' 
					+ '<tr>' 
					+ '<td width="80%">Define the used variables:</td>' 
					+ '<td></td>' 
					+ '</tr>' 
					+ '<tr>' 
					+ '<td height="22">' 
					+ '<table border="1" cellpadding=0 cellspacing=0 width="100%" id="ExceptionVariableTable" name=tb2 bgcolor="#FFFFFF" bordercolor="#C0C0C0">' 
					+ '<tr>' 
					+ '<td width="46%">Name</td>' 
					+ '<td width="46%">Value</td>' 
					+ '<td></td>' 
					+ '</tr>' 
					+ '</table>' 
					+ '</td>' 
					+ '<td height="22" valign=top>' 
					+ '<table border="0" width="90%">' 
					+ '<tr>' 
					+ '<td><input type=button  value="   Add   " onclick="addExceptionVariable()"></td>' 
					+ '</tr>' 
					+ '<tr>' 
					+ '<td><input type=button  value="Remove" onclick="removeExceptionVarible()"></td>' 
					+ '</tr>' 
					+ '</table>' 
					+ '</td> ' 
					+ '</tr>' 
					+ '</table> ' 
					+ '</div>' // tab
					+ '</div>' // tabs
					+ '</div>'); //dialog

	this.show = function() {
		
		//$("#" + tid).tabs().addClass("ui-tabs-vertical ui-helper-clearfix");
		//$("#" + tid +" li").removeClass("ui-corner-top").addClass("ui-corner-left");
		
		var parent = this;
		var dialog = $("#" + did).dialog( {
			title : "Node Properties",
			width : 640,
			height : 280,
			buttons : {
				"Apply" : function() {
					parent.model.setText($("#NodeName").val());
					parent.model.setAction(parent.getAction());
					parent.model.setExceptionhandler(parent.getExceptionHandler());
					parent.model.setEditing(false);
					dialog.dialogClose();
				},
				"Close" : function() {
					dialog.dialogClose();
				}
			}
		});
	};

	this.populate = function() {
		
		$("#NodeName").val(model.getText());

		// action
		$("#NodeActionClass").val(model.getAction().actionClass);
		$("#NodeExceptionClass").val(model.getExceptionhandler().actionClass);

		var actionVariables = model.getAction().variables;
		for (i = 0; i < actionVariables.length; i++) {
			addActionVariable(actionVariables[i][0], actionVariables[i][1]);
		}

		var exceptionVariables = model.getExceptionhandler().variables;
		for (i = 0; i < exceptionVariables.length; i++) {
			addExceptionVariable(exceptionVariables[i][0], exceptionVariables[i][1]);
		}
	};


	this.getAction = function() {
		var action = new Action();
		action.actionClass = $("#NodeActionClass").val();

		var objs_name = document.getElementsByName("action_variable_name");
		var objs_value = document.getElementsByName("action_variable_value");
		for(var i=0;i<objs_name.length;i++){
			if (objs_name[i].value != "") {
				var a = new Array();
				a[0] = objs_name[i].value;
				if(objs_value.length>i){
					a[1] = objs_value[i].value;
				}else{
					a[1] = "";
				}
				action.variables[action.variables.length] = a;
			}
		}
		
		return action;
	};
	
	this.getExceptionHandler = function() {
		var action = new Action();
		action.actionClass = $("#NodeExceptionClass").val();

		var objs_name = document.getElementsByName("exception_variable_name");
		var objs_value = document.getElementsByName("exception_variable_value");
		for(var i=0;i<objs_name.length;i++){
			if (objs_name[i].value != "") {
				var a = new Array();
				a[0] = objs_name[i].value;
				if(objs_value.length>i){
					a[1] = objs_value[i].value;
				}else{
					a[1] = "";
				}
				action.variables[action.variables.length] = a;
			}
		}
		
		return action;
	};
}

function addActionVariable(name, value) {
	var varName = name ? name : "";
	var varValue = value ? value : "";
	var variableTable = document.getElementById("ActionVariableTable");

	var row = variableTable.insertRow(-1);

	var cell_name = row.insertCell(-1);
	var cell_value = row.insertCell(-1);
	var cell_chk = row.insertCell(-1);
	
	var ipt_name = $("<input type='text' name='action_variable_name' value='" + varName+ "'/>");
	var ipt_value = $("<input type='text' name='action_variable_value' value='" + varValue + "'/>");
	var ipt_chk = $("<input type='checkbox' name='action_variable_chk' id='action_variable_chk'/>");
	
	$(ipt_name).appendTo(cell_name);
	$(ipt_value).appendTo(cell_value);
	$(ipt_chk).appendTo(cell_chk);
}

// remove variables
function removeActionVarible() {
	$("[name='action_variable_chk'][checked]").parent().parent().remove();
}

function addExceptionVariable(name, value) {
	var variableTable = document.getElementById("ExceptionVariableTable");

	var varName = name ? name : "";
	var varValue = value ? value : "";
	
	var row = variableTable.insertRow(-1);

	var cell_name = row.insertCell(-1);
	var cell_value = row.insertCell(-1);
	var cell_chk = row.insertCell(-1);
	
	var ipt_name = $("<input type='text' name='exception_variable_name' value='" + varName+ "'/>");
	var ipt_value = $("<input type='text' name='exception_variable_value' value='" + varValue+ "'/>");
	var ipt_chk = $("<input type='checkbox' name='exception_variable_chk' id='action_variable_chk'/>");
	
	$(ipt_name).appendTo(cell_name);
	$(ipt_value).appendTo(cell_value);
	$(ipt_chk).appendTo(cell_chk);
}

function removeExceptionVarible() {
	$("[name='exception_variable_chk'][checked]").parent().parent().remove();
}


function changeNodeTab(_tabId) {
	var tabShowed = document.getElementById(_tabId);

	if (tabShowed) {
		
		var tabs = new Array();
		tabs[tabs.length] = document.getElementById("NodeGeneralTab");
		tabs[tabs.length] = document.getElementById("NodeActionTab");
		tabs[tabs.length] = document.getElementById("NodeExceptionTab");
		
		for ( var i = 0; i < tabs.length; i++) {
			var aTab = tabs[i];
			if (aTab.id == _tabId) {
				aTab.style.display = "block";
			} else {
				aTab.style.display = "none";
			}
		}
	}
}