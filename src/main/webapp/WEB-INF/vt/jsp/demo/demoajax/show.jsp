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
		<th style="width:80px;"><fmt:message key="demoData.id" /></th>
		<td style="width:300px;">${demoData.id}</td>
		</tr>
		<tr>
		<th><fmt:message key="demoData.name" /></th>
		<td><c:out value="${demoData.name}" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoData.birthday" /></th>
		<td><fmt:formatDate value="${demoData.birthday}" pattern ="${dateFormat}" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoData.sex" /></th>
		<td><c:out value="${sexList[demoData.sex]}" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoData.salary" /></th>
		<td><c:out value="${demoData.salary}" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoData.email" /></th>
		<td><c:out value="${demoData.email}" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoData.countryId" /></th>
		<td><c:out value="${demoData.country.name}" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoData.provinceId" /></th>
		<td><c:out value="${demoData.province.name}" /></td>
		</tr>
	</table>
</div>