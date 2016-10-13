<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.show" />",
        width: 380,
        height: 180,
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
		<th style="width:80px;"><fmt:message key="demoProvince.id" /></th>
		<td style="width:300px;">${demoProvince.id}</td>
		</tr>
		<tr>
		<th style="width:80px;"><fmt:message key="demoCountry.name" /></th>
		<td style="width:300px;">${demoProvince.country.name}</td>
		</tr>
		<tr>
		<th><fmt:message key="demoProvince.name" /></th>
		<td><c:out value="${demoProvince.name}" /></td>
		</tr>
	</table>
</div>