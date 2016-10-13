<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>

<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="sysUser.op.change_password_admin" />",
        width: 300,
        height: 130,
        buttons:{
	    	"<fmt:message key="global.op.save" />":{
	        url:"${ctx}/security/sysuser/updatePasswordAdmin.json",
	        param:function() {
	            return $("#fmForm").getParam();
	        },
	        validate:function() {
		        if( $("#newPassword").val()!=$("#passwordConfirm").val() )
		        {
			        alert("<fmt:message key="sysUser.newPassword" /> not equal to <fmt:message key="sysUser.passwordConfirm" />");
			        return false;
		        }
		        
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
    <input type="hidden" name="id" id="id"  value="${id}" />
	<br>
    <table class="editTB">	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.newPassword" /></th>
		<td><input type="password" name="newPassword" id="newPassword" /></td>
	</tr>
	<tr>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.passwordConfirm" /></th>
		<td><input type="passwordpassword" name="passwordConfirm" id="passwordConfirm" /></td>
	</tr>

	</table>
    </form>
</div>
