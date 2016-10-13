<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.show" />",
        width: 650,
        height: 360,
        buttons:{
            "<fmt:message key="global.op.close" />": function() {
                dialog.dialogClose();
            }
        }
    });//end

</script>
<div id="fmDiv"><br>
<table class="editTB">
	<tr>
		<th style="width: 90px;"><fmt:message key="role.id" /></th>
		<td style="width: 200px;">${role.id}</td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="role.name" /></th>
		<td>${role.name}</td>
  		<th><span class="fRed">*</span><fmt:message key="role.code" /></th>
		<td>${role.code}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="role.sortOrder" /></th>
		<td>${role.sortOrder}</td>
  		<th><span class="fRed">*</span><fmt:message key="role.status" /></th>
		<td>${role.status}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="role.datatype" /></th>
		<td>${role.datatype}</td>
  		<th><span class="fRed">*</span><fmt:message key="role.description" /></th>
		<td>${role.description}</td>
	</tr>
	 
</table>
</div>