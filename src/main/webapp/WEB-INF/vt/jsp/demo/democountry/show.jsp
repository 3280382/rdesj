<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.show" />",
        width: 380,
        height: 120,
        buttons:{
            "<fmt:message key="global.op.close" />": function() {
                dialog.dialogClose();
            }
        }
    });//end

</script>
<div id="fmDiv">
	<br>
    <table class="editTB">
    	<tr>
		<th style="width:80px;"><fmt:message key="demoCountry.id" /></th>
		<td style="width:300px;">${demoCountry.id}</td>
		</tr>
		<tr>
		<th><fmt:message key="demoCountry.name" /></th>
		<td><c:out value="${demoCountry.name}" /></td>
		</tr>
	</table>
</div>