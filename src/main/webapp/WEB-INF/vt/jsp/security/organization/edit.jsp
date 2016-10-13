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
		<th style="width: 90px;"><fmt:message key="organization.id" /></th>
		<td style="width: 200px;"><input disabled type="text" name="id" id="id"  value="${organization.id}" /></td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="organization.parentId" /></th>
		<td><input type="text" name="parentId" id="parentId" value="${organization.parentId}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="organization.name" /></th>
		<td><input type="text" name="name" id="name" value="${organization.name}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="organization.code" /></th>
		<td><input type="text" name="code" id="code" value="${organization.code}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="organization.sortOrder" /></th>
		<td><input type="text" name="sortOrder" id="sortOrder" value="${organization.sortOrder}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="organization.status" /></th>
		<td><input type="text" name="status" id="status" value="${organization.status}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="organization.datatype" /></th>
		<td><input type="text" name="datatype" id="datatype" value="${organization.datatype}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="organization.description" /></th>
		<td><input type="text" name="description" id="description" value="${organization.description}"  /></td>
 
	</table>
    </form>
</div>
