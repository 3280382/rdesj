function DecisionNodePropertyDialog(model) {
	this.model = model;
	var id = "PropertyDecisionNodeDialog";
	
	$(document.body).append(
			'<div id="'
					+ id
					+ '">'
					+ '<div class="hd">Properties</div>'
					+ '<div class="bd">'
					+ '<table border="1" width="600" id="table1" height="341">'
					+ '<tr>'
					+ '<td valign=top>'
					+ '<div><a href="#" onclick="changeDecisionNodeTab(\'DecisionNodeGeneralTab\')">General</a></div>'
					+ '<div><a href="#" onclick="changeDecisionNodeTab(\'DecisionNodeConditionTab\')">Handler</a></div>'
					+ '</td>'

					+ '<td bgcolor="#EBE9ED" valign=top>'
					
					+ '<div id="DecisionNodeGeneralTab">'
					+ '<span><b>General properties setting for node.</b></span>'
					+ '<br><hr/>'
					+ '<table border="0" width="100%">'
					+ '<tr>'
					+ '<td width="20%">Name</td>'
					+ '<td width="80%" align=left><input name="DecisionNodeName" id="DecisionNodeName"></td>'
					+ '</table>'
					+ '</div>'
					
					+ '<div id="DecisionNodeConditionTab" style="display:none">' 
					+ '<span><b>Conditon expression for decision node.</b></span>'
					+ '<br><hr/>'
					+ '<table border="0" width="100%">' 
					+ '<tr>' 
					+ '<td width="20%">Condition</td>' 
					+ '<td width="80%"><input name="ConditionExpression" id="DecisionNodeConditionExpression" size="30"></td>' 
					+ '</tr>' 
					+ '</table>' 
					+ '</div>' 
					
					+ '</td>' 
					+ '</tr>' 
					+ '</table>' 
					+ '</div>' 
					+ '</div>');

	this.show = function() {
		var parent = this;
		var dialog = $("#" + id).dialog( {
			title : "Decision Node Properties",
			width : 640,
			height : 280,
			buttons : {
				"Apply" : function() {
					parent.model.setText($("#DecisionNodeName").val());
					var condition = new Condition();
					condition.conditionExpression = $("#DecisionNodeConditionExpression").val();
					parent.model.setCondition(condition);
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
		$("#DecisionNodeName").val(model.getText());
		$("#DecisionNodeConditionExpression").val(model.getCondition().conditionExpression);
		changeDecisionNodeTab('DecisionNodeGeneralTab');
	};
}


function changeDecisionNodeTab(_tagId) {
	var tabShowed = document.getElementById(_tagId);
	if (tabShowed) {
		var tab1 = document.getElementById("DecisionNodeGeneralTab");
		var tab2 = document.getElementById("DecisionNodeConditionTab");
		var tabs = new Array(tab1, tab2);
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