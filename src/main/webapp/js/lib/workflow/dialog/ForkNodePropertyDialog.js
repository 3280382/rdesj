function ForkNodePropertyDialog(model) {
	this.model = model;
	var id = "PropertyForkNodeDialog";
	$(document.body)
			.append(
					'<div id="'+id+'">' 
					+ '<div class="hd">Properties</div>' 
					+ '<div class="bd">' 
					+ '<table border="1" width="600" id="table1" height="341">'
					+ '<tr>'
					+ '<td valign=top>'
					+ '<div><a href="#" onclick="changeForkNodeTab(\'ForkNodeGeneralTab\')">General</a></div>'
					+ '</td>'
				
					+ '<td bgcolor="#EBE9ED" valign=top>' 
					+ '<div id="ForkNodeGeneralTab">' 
					+ '<span><b>General properties setting for fork node.</b></span>'
					+ '<br><hr/>'
					+ '<table border="0" width="100%">' 
					+ '<tr>' 
					+ '<td width="20%">Name</td>' 
					+ '<td width="80%"><input name="forknode_name" id="ForkNodeName"></td>' 
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
			title : "Fork Node Properties",
			width : 640,
			height : 280,
			buttons : {
				"Apply" : function() {
					parent.model.setText($("#ForkNodeName").val());
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
		$("#ForkNodeName").val(model.getText());

		changeForkNodeTab('ForkNodeGeneralTab');
	};
}


function changeForkNodeTab(_tagId) {
	var tabShowed = document.getElementById(_tagId);
	if (tabShowed) {
		var tab1 = document.getElementById("ForkNodeGeneralTab");
		var tabs = new Array(tab1);
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