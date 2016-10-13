function AlertDialog() {
	var dialogId = "GeneralDialog";
	var bodyId = "DialogBody123";
	$(document.body)
			.append(
					'<div id="'
							+ dialogId
							+ '" title="Message" style="display: none">'
							+ '<p>'
							+ '<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;" id="'
							+ bodyId
							+ '">These items will be permanently deleted and cannot be recovered. Are you sure?</span>'
							+ '</p>' + '</div>');

	this.info = function(message) {
		var parent = this;
		$("#" + bodyId).text(message);
		var dialog = $("#" + dialogId).dialog( {
			autoOpen : false,
			resizable : false,
			height : 100,
			modal : true,
			buttons : {
				"OK" : function() {
					dialog.dialogClose();
				}
			}
		});
	};
}

function infoDialog(message) {
	var dial = new AlertDialog();
	dial.info(message);
}