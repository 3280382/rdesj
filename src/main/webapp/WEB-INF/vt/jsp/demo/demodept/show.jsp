<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.show" />",
        width: 380,
        height: 280,
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
		<th style="width:80px;"><fmt:message key="demoDept.id" /></th>
		<td style="width:300px;">${demoDept.id}</td>
		</tr>
		<tr>
		<th><fmt:message key="demoDept.name" /></th>
		<td><c:out value="${demoDept.name}" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoDept.parentId" /></th>
		<td><c:out value="${demoDept.parent.name}" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoDept.code" /></th>
		<td><c:out value="${demoDept.code}" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoDept.deptType" /></th>
		<td><c:out value="${demoDept.deptType}" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoDept.sortOrder" /></th>
		<td><c:out value="${demoDept.sortOrder}" /></td>
		</tr>
	</table>
</div>