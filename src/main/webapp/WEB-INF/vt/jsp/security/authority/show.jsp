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
		<th style="width: 90px;"><fmt:message key="authority.id" /></th>
		<td style="width: 200px;">${authority.id}</td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="authority.parentId" /></th>
		<td>${authority.parentId}</td>
  		<th><span class="fRed">*</span><fmt:message key="authority.name" /></th>
		<td>${authority.name}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="authority.code" /></th>
		<td>${authority.code}</td>
  		<th><span class="fRed">*</span><fmt:message key="authority.sortOrder" /></th>
		<td>${authority.sortOrder}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="authority.status" /></th>
		<td>${authority.status}</td>
  		<th><span class="fRed">*</span><fmt:message key="authority.datatype" /></th>
		<td>${authority.datatype}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="authority.description" /></th>
		<td>${authority.description}</td>
 
</table>
</div>