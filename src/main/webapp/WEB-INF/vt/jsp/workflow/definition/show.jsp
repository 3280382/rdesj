<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"Workflow Definition Content - ${workflowName}",
        width: 760,
        height: 410,
        buttons:{
            "<fmt:message key="global.op.close" />": function() {
                dialog.dialogClose();
            }
        }
    });//end

</script>
<div id="fmDiv">
	<textarea rows="80" cols="120">${definitionXML}</textarea>
</div>