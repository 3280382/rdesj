<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.add" />",
        width: 650,
        height: 360,
        buttons:{
	    	"<fmt:message key="global.op.save" />":{
	        url:"save.json",
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

    $("#organizationId",$("#fmDiv")).treeSelect({});
</script>
<div id="fmDiv">
    <form id="fmForm">
    <br>
    <table class="editTB">
	 
	<tr>
		<th style="width: 90px;"><span class="fRed">*</span><fmt:message key="sysUser.username" /></th>
		<td style="width: 200px;"><input type="text" name="username" id="username" /></td>
  		<th style="width: 90px;"><span class="fRed">*</span><fmt:message key="sysUser.loginName" /></th>
		<td style="width: 200px;"><input type="text" name="loginName" id="loginName" /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.password" /></th>
		<td><input type="text" name="password" id="password" /></td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.email" /></th>
		<td><input type="text" name="email" id="email" /></td>
	</tr>
	  
	<tr>
  		<th><fmt:message key="sysUser.organizationId" /></th>
		<td>
			<select name="organizationId" id="organizationId" >
				<option value=""></option>
			<c:forEach items="${organizationList}" var="it">
				<option parentId="${it.parentId}" value="${it.id}" >${it.name}</option>
			</c:forEach> 
			</select>
		</td>
		<th>&nbsp;</th>
		<td>&nbsp;</td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="sysUser.code" /></th>
		<td><input type="text" name="code" id="code" /></td>
  		<th><span class="fRed">*</span><fmt:message key="sysUser.mobile" /></th>
		<td><input type="text" name="mobile" id="mobile" /></td>
	</tr>
	  
	<tr>
		<th><fmt:message key="sysUser.phone" /></th>
		<td><input type="text" name="phone" id="phone" /></td>
  		<th><fmt:message key="sysUser.address" /></th>
		<td><input type="text" name="address" id="address" /></td>
	</tr>
	  
	<tr>
		<th><fmt:message key="sysUser.description" /></th>
		<td colspan="3"><textarea  style="width:430px;height:100px;" name="description" id="description"></textarea></td>
	</tr>
	
	</table>
    </form>
</div>
