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
</script>
<div id="fmDiv">
    <form id="fmForm">
    <br>
    <table class="editTB">
	 
	<tr>
		<th style="width: 90px;"><span class="fRed">*</span><fmt:message key="role.name" /></th>
		<td style="width: 200px;"><input type="text" name="name" id="name" /></td>
  		<th style="width: 90px;"><span class="fRed">*</span><fmt:message key="role.code" /></th>
		<td style="width: 200px;"><input type="text" name="code" id="code" /></td>
	</tr>
	  
	<tr>
		<th><fmt:message key="role.sortOrder" /></th>
		<td><input type="text" name="sortOrder" id="sortOrder" /></td>
  		<th><fmt:message key="role.status" /></th>
		<td>
			<select name="status" id="status">
				<option value=""></option>
			<c:forEach items="${roleStatusList}" var="it">
				<option value="${it.key}">${it.value}</option>
			</c:forEach> 
			</select>
		</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="role.datatype" /></th>
		<td><input type="text" name="datatype" id="datatype" /></td>
  		<th><fmt:message key="role.description" /></th>
		<td><input type="text" name="description" id="description" /></td>
	</tr>
	 
	</table>
    </form>
</div>
