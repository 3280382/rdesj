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
		<th style="width: 90px;"><fmt:message key="role.id" /></th>
		<td style="width: 200px;"><input disabled type="text" name="id" id="id"  value="${role.id}" /></td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="role.name" /></th>
		<td><input type="text" name="name" id="name" value="${role.name}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="role.code" /></th>
		<td><input type="text" name="code" id="code" value="${role.code}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="role.sortOrder" /></th>
		<td><input type="text" name="sortOrder" id="sortOrder" value="${role.sortOrder}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="role.status" /></th>
		<td><input type="text" name="status" id="status" value="${role.status}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="role.datatype" /></th>
		<td><input type="text" name="datatype" id="datatype" value="${role.datatype}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="role.description" /></th>
		<td><input type="text" name="description" id="description" value="${role.description}"  /></td>
	</tr>
	 
	</table>
    </form>
</div>
