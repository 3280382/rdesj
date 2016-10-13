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
		<th style="width: 90px;"><fmt:message key="sysUser.id" /></th>
		<td style="width: 200px;">${sysUser.id}</td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.username" /></th>
		<td>${sysUser.username}</td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.loginName" /></th>
		<td>${sysUser.loginName}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.password" /></th>
		<td>${sysUser.password}</td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.email" /></th>
		<td>${sysUser.email}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.status" /></th>
		<td>${sysUser.status}</td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.accountNonExpired" /></th>
		<td>${sysUser.accountNonExpired}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.credentialsNonExpired" /></th>
		<td>${sysUser.credentialsNonExpired}</td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.enabled" /></th>
		<td>${sysUser.enabled}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.accountNonLocked" /></th>
		<td>${sysUser.accountNonLocked}</td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.organizationId" /></th>
		<td>${sysUser.organizationId}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.code" /></th>
		<td>${sysUser.code}</td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.mobile" /></th>
		<td>${sysUser.mobile}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.phone" /></th>
		<td>${sysUser.phone}</td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.address" /></th>
		<td>${sysUser.address}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.description" /></th>
		<td>${sysUser.description}</td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.recentPassword" /></th>
		<td>${sysUser.recentPassword}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.isLogin" /></th>
		<td>${sysUser.isLogin}</td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.tryTimes" /></th>
		<td>${sysUser.tryTimes}</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.failLoginTimes" /></th>
		<td>${sysUser.failLoginTimes}</td>
 
</table>
</div>