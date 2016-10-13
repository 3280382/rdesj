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
    $("#parentId",$("#fmDiv")).treeSelect({});
</script>
<div id="fmDiv">
    <form id="fmForm">
    <br>
    <table class="editTB">
	 
	<tr>
		<th style="width: 90px;"><span class="fRed">*</span><fmt:message key="organization.parentId" /></th>
		<td style="width: 200px;">
			<select name="parentId" id="parentId" >
				<option value=""></option>
			<c:forEach items="${organizationList}" var="it">
				<option parentId="${it.parentId}" value="${it.id}" <c:if test="${it.id==parentId}">selected</c:if> >${it.name}</option>
			</c:forEach> 
			</select>
		</td>
  		<th style="width: 90px;"><span class="fRed">*</span><fmt:message key="organization.name" /></th>
		<td style="width: 200px;"><input type="text" name="name" id="name" /></td>
	</tr>
	  
	<tr>
		<th><span class="fRed">*</span><fmt:message key="organization.code" /></th>
		<td><input type="text" name="code" id="code" /></td>
  		<th><fmt:message key="organization.sortOrder" /></th>
		<td><input type="text" name="sortOrder" id="sortOrder" /></td>
	</tr>
	  
	<tr>
		<th><fmt:message key="organization.status" /></th>
		<td>
			<select name="status" id="status">
				<option value=""></option>
			<c:forEach items="${organizationStatusList}" var="it">
				<option value="${it.key}">${it.value}</option>
			</c:forEach> 
			</select>
		</td>
  		<th><fmt:message key="organization.datatype" /></th>
		<td><input type="text" name="datatype" id="datatype" /></td>
	</tr>
	  
	<tr>
		<th><fmt:message key="organization.description" /></th>
		<td><input type="text" name="description" id="description" /></td>
 
	</table>
    </form>
</div>
