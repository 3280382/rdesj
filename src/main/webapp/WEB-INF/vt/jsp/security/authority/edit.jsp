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
		<th style="width: 90px;"><fmt:message key="authority.id" /></th>
		<td style="width: 200px;"><input disabled type="text" name="id" id="id"  value="${authority.id}" /></td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><span class="fRed">*</span><fmt:message key="authority.parentId" /></th>
		<td><input type="text" name="parentId" id="parentId" value="${authority.parentId}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="authority.name" /></th>
		<td><input type="text" name="name" id="name" value="${authority.name}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="authority.code" /></th>
		<td><input type="text" name="code" id="code" value="${authority.code}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="authority.sortOrder" /></th>
		<td><input type="text" name="sortOrder" id="sortOrder" value="${authority.sortOrder}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="authority.status" /></th>
		<td><input type="text" name="status" id="status" value="${authority.status}"  /></td>
  		<th><span class="fRed">*</span><fmt:message key="authority.datatype" /></th>
		<td><input type="text" name="datatype" id="datatype" value="${authority.datatype}"  /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="authority.description" /></th>
		<td><input type="text" name="description" id="description" value="${authority.description}"  /></td>
 
	</table>
    </form>
</div>
