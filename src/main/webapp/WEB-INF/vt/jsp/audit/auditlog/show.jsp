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
		<th style="width: 90px;"><fmt:message key="auditLog.id" /></th>
		<td style="width: 200px;">${auditLog.id}</td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.userId" /></th>
		<td>${auditLog.userId}</td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.username" /></th>
		<td>${auditLog.username}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.loginName" /></th>
		<td>${auditLog.loginName}</td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.userIp" /></th>
		<td>${auditLog.userIp}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.targetId" /></th>
		<td>${auditLog.targetId}</td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.targetEntityType" /></th>
		<td>${auditLog.targetEntityType}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.targetName" /></th>
		<td>${auditLog.targetName}</td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.targetDesc" /></th>
		<td>${auditLog.targetDesc}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.targetSnapshot" /></th>
		<td>${auditLog.targetSnapshot}</td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.opType" /></th>
		<td>${auditLog.opType}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.opTime" /></th>
		<td>${auditLog.opTime}</td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.opDesc" /></th>
		<td>${auditLog.opDesc}</td>
	</tr>
	 
</table>
</div>