<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>

<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.main.top.edit_profile" />",
        width: 650,
        height: 280,
        buttons:{
	    	"<fmt:message key="global.op.save" />":{
	        url:"${ctx}/security/sysuser/updateProfile.json",
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
    <input type="hidden" name="code" id="code" value="${sysUser.code}"  />
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
		<td colspan="3"><textarea style="width:450px;height:100px;" name="description" id="description">${sysUser.description}</textarea></td>
	</tr>
	</table>
    </form>
</div>
