function NodePropertyDialog(model) {
	this.model = model;
	var id = "PropertyNodeDialog";
	$(document.body)
			.append(
					'<div id="'
							+ id
							+ '">'
							+ '<div class="hd">Properties</div>'
							+ '<div class="bd">'
							+ '<table border="1" width="600" id="table1" height="341">'
							+ '<tr>'
							+ '<td valign=top>'
							+ '<div><a href="#" onclick="changeNodeTab(\'NodeGeneralTab\')">General</a></div>'
							+ '<div><a href="#" onclick="changeNodeTab(\'NodeActionHandlerTab\')">Handler</a></div>'
							+ '<div><a href="#" onclick="changeNodeTab(\'NodeExceptionHandlerTab\')">Exception</a></div>'
							+ '</td>'

							+ '<td bgcolor="#EBE9ED" valign=top>'
							
							+ '<div id="NodeGeneralTab">'
							+ '<span><b>General properties setting for node.</b></span>'
							+ '<br><hr/>'
							+ '<table border="0" width="100%">'
							+ '<tr>'
							+ '<td width="20%">Name</td>'
							+ '<td width="80%" align=left><input name="NodeName" id="NodeName"></td>'
							+ '</table>'
							+ '</div>'
							
							+ '<div id="NodeActionHandlerTab" style="display:none">'
							+ '<span><b>Action Handler setting for node.</b></span>'
							+ '<br><hr/>'
							+ '<table border="0" width="100%">'
							+ '<tr>'
							+ '<td width="20%">Action Handler</td>'
							+ '<td width="80%" align=left>'
							+ '<select name="NodeActionClass" id="NodeActionClass" size="1" onchange="initVariables(this, variables)">'
							+ '<option value="" selected>Please select an action handler</option>'
							+ '</select>'
							+ '</td>'
							+ '</tr>'
							+ '</table>'
							+ '<table border="0" width="100%">'
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
							+ '<table border="0" width="100%">'
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
							
							+ '<div id="NodeExceptionHandlerTab" style="display:none">'
							+ '<span><b>Node Exception handler setting for node.</b></span>'
							+ '<br><hr/>'
							+ '<table border="0" width="100%">'
							+ '<tr>'
							+ '<td width="20%">Exception</td>'
							+ '<td width="80%" align=left>'
							+ '<select name="NodeExceptionClass" id="NodeExceptionClass" size="1" onchange="initVariables(this, exceptionvariables)">'
							+ '<option value="" selected>Please select an exception handler</option>'
							+ '</select></td>'
							+ '</tr>'
							+ '</table>'
							+ '<table border="0" width="100%">'
							+ '<tr>'
							+ '<td width="80%">Define the used variables:</td>'
							+ '<td></td>'
							+ '</tr>'
							+ '<tr>'
							+ '<td height="22">'
							+ '<table border="1" cellpadding=0 cellspacing=0 width="100%" id="ExceptionVariableTable" name=tb2 bgcolor="#FFFFFF" bordercolor="#C0C0C0">'
							+ '<tr>' + '<td width="46%">Name</td>'
							+ '<td width="46%">Value</td>'
							+ '<td></td>'
							+ '</tr>'
							+ '</table>' + '</td>'
							+ '<td height="22" valign=top>'
							+ '<table border="0" width="100%">'
							+ '<tr>'
							+ '<td><input type=button  value="   Add   " onclick="addExceptionVariable()"></td>'
							+ '</tr>'
							+ '<tr>'
							+ '<td><input type=button  value="Remove" onclick="removeExceptionVarible()"></td>'
							+ '</tr>'
							+ '</table>'
							+ '</td>' 
							+ '</tr>'
							+ '</table>' + '</div>' + '</td>' + '</tr>'
							+ '</table>' + '</div>' + '</div>');

	this.show = function() {
		var parent = this;
		var dialog = $("#" + id).dialog( {
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

		changeNodeTab('NodeGeneralTab');
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

function changeNodeTab(_tagId) {
	var tabShowed = document.getElementById(_tagId);
	if (tabShowed) {
		var tab1 = document.getElementById("NodeGeneralTab");
		var tab2 = document.getElementById("NodeActionHandlerTab");
		var tab3 = document.getElementById("NodeExceptionHandlerTab");
		var tabs = new Array(tab1, tab2, tab3);
		for ( var i = 0; i < tabs.length; i++) {
			var aTab = tabs[i];

			if (aTab.id == _tagId || aTab.id == "") {
				aTab.style.display = "block";
			} else {
				aTab.style.display = "none";
			}
		}
	}
}