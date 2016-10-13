<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>

<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.edit" />",
        width: 650,
        height: 360,
        buttons:{
	    	"<fmt:message key="global.op.save" />":{
	        url:"update.json",
	        param:function() {
	            return $("#fmForm").getParam();
	        },
	        validate:function() {
	            return true;
	        },
	        dataType: "json",
	        success:function(data) {
				if( !$("#fmForm").resultBinding(data).hasError )
				{
					dialog.dialogClose();
					reloadList();
				}   
	        }
	    	},
	    	"<fmt:message key="global.op.close" />": function() {
                dialog.dialogClose();               
	    	}
        }
    });//end
</script>
<div id="fmDiv">
    <form id="fmForm">
	<br>
    <table class="editTB">					
	<tr>
		<th style="width: 90px;"><fmt:message key="auditLog.id" /></th>
		<td style="width: 200px;"><input disabled type="text" name="id" id="id"  value="${auditLog.id}" /></td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.userId" /></th>
		<td><input type="text" name="userId" id="userId" value="${auditLog.userId}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.username" /></th>
		<td><input type="text" name="username" id="username" value="${auditLog.username}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.loginName" /></th>
		<td><input type="text" name="loginName" id="loginName" value="${auditLog.loginName}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.userIp" /></th>
		<td><input type="text" name="userIp" id="userIp" value="${auditLog.userIp}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.targetId" /></th>
		<td><input type="text" name="targetId" id="targetId" value="${auditLog.targetId}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.targetEntityType" /></th>
		<td><input type="text" name="targetEntityType" id="targetEntityType" value="${auditLog.targetEntityType}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.targetName" /></th>
		<td><input type="text" name="targetName" id="targetName" value="${auditLog.targetName}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.targetDesc" /></th>
		<td><input type="text" name="targetDesc" id="targetDesc" value="${auditLog.targetDesc}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.targetSnapshot" /></th>
		<td><input type="text" name="targetSnapshot" id="targetSnapshot" value="${auditLog.targetSnapshot}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.opType" /></th>
		<td><input type="text" name="opType" id="opType" value="${auditLog.opType}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="auditLog.opTime" /></th>
		<td><input type="text" name="opTime" id="opTime" value="${auditLog.opTime}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="auditLog.opDesc" /></th>
		<td><input type="text" name="opDesc" id="opDesc" value="${auditLog.opDesc}"  /></td>
	</tr>
	 
	</table>
    </form>
</div>
