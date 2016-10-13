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
		<th style="width: 90px;"><fmt:message key="organization.id" /></th>
		<td style="width: 200px;">${organization.id}</td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="organization.parentId" /></th>
		<td>${organization.parentId}</td>
  		<th><span class="fRed">*</span><fmt:message key="organization.name" /></th>
		<td>${organization.name}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="organization.code" /></th>
		<td>${organization.code}</td>
  		<th><span class="fRed">*</span><fmt:message key="organization.sortOrder" /></th>
		<td>${organization.sortOrder}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="organization.status" /></th>
		<td>${organization.status}</td>
  		<th><span class="fRed">*</span><fmt:message key="organization.datatype" /></th>
		<td>${organization.datatype}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="organization.description" /></th>
		<td>${organization.description}</td>
 
</table>
</div>