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
		<th style="width: 90px;"><fmt:message key="sysUser.id" /></th>
		<td style="width: 200px;"><input disabled type="text" name="id" id="id"  value="${sysUser.id}" /></td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.username" /></th>
		<td><input type="text" name="username" id="username" value="${sysUser.username}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.loginName" /></th>
		<td><input type="text" name="loginName" id="loginName" value="${sysUser.loginName}"  /></td>
	</tr>
	  
	<tr>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.email" /></th>
		<td><input type="text" name="email" id="email" value="${sysUser.email}"  /></td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="sysUser.status" /></th>
		<td><input type="text" name="status" id="status" value="${sysUser.status}"  /></td>
  		<th><fmt:message key="sysUser.accountNonExpired" /></th>
		<td><input type="text" name="accountNonExpired" id="accountNonExpired" value="${sysUser.accountNonExpired}"  /></td>
	</tr>
	  
	<tr>
		<th><fmt:message key="sysUser.credentialsNonExpired" /></th>
		<td><input type="text" name="credentialsNonExpired" id="credentialsNonExpired" value="${sysUser.credentialsNonExpired}"  /></td>
  		<th><fmt:message key="sysUser.enabled" /></th>
		<td><input type="text" name="enabled" id="enabled" value="${sysUser.enabled}"  /></td>
	</tr>
	  
	<tr>
		<th><fmt:message key="sysUser.accountNonLocked" /></th>
		<td><input type="text" name="accountNonLocked" id="accountNonLocked" value="${sysUser.accountNonLocked}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.organizationId" /></th>
		<td><input type="text" name="organizationId" id="organizationId" value="${sysUser.organizationId}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.code" /></th>
		<td><input type="text" name="code" id="code" value="${sysUser.code}"  /></td>
  		<th><fmt:message key="sysUser.mobile" /></th>
		<td><input type="text" name="mobile" id="mobile" value="${sysUser.mobile}"  /></td>
	</tr>
	  
	<tr>
		<th><fmt:message key="sysUser.phone" /></th>
		<td><input type="text" name="phone" id="phone" value="${sysUser.phone}"  /></td>
  		<th><fmt:message key="sysUser.address" /></th>
		<td><input type="text" name="address" id="address" value="${sysUser.address}"  /></td>
	</tr>
	  
	<tr>
		<th><fmt:message key="sysUser.description" /></th>
		<td><input type="text" name="description" id="description" value="${sysUser.description}"  /></td>
  		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="sysUser.isLogin" /></th>
		<td><input type="text" name="isLogin" id="isLogin" value="${sysUser.isLogin}"  /></td>
  		<th><fmt:message key="sysUser.tryTimes" /></th>
		<td><input type="text" name="tryTimes" id="tryTimes" value="${sysUser.tryTimes}"  /></td>
	</tr>
	  
	<tr>
		<th><fmt:message key="sysUser.failLoginTimes" /></th>
		<td><input type="text" name="failLoginTimes" id="failLoginTimes" value="${sysUser.failLoginTimes}"  /></td>
 		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</table>
    </form>
</div>
