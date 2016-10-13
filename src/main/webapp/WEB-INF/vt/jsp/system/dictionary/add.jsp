<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.add" />",
        width: 350,
        height: 200,
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
    <input type="hidden" name="parentId" id="parentId" value="${parentId}" />
    <input type="hidden" name="parentKey" id="parentKey" value="${parentKey}" />
    <input type="hidden" name="valuetype" id="valuetype" /> 
	<input type="hidden" name="validation" id="validation" /> 
	<input type="hidden" name="value1" id="value1" /> 
	<input type="hidden" name="valuetype1" id="valuetype1" /> 
	<input type="hidden" name="validation1" id="validation1" /> 
	<input type="hidden" name="enable" id="enable" /> 
	<input type="hidden" name="visualable" id="visualable" /> 
	<input type="hidden" name="displayType" id="displayType" /> 
	<input type="hidden" name="editable" id="editable" /> 
	<input type="hidden" name="description" id="description" /> 
    <br>
    <table class="editTB">    
	<tr>
		<th style="width: 90px;"><span class="fRed">*</span><fmt:message key="dictionary.nodesType" /></th>
		<td style="width: 250px;">
			<select name="nodesType" id="nodesType">
			<c:forEach items="${nodesTypeList}" var="it">
				<option value="${it.key}">${it.value}</option>
			</c:forEach> 
			</select>
		</td>
	</tr>
	<tr>
		<th style="width: 90px;"><span class="fRed">*</span><fmt:message key="dictionary.key" /></th>
		<td style="width: 250px;"><input type="text" name="key" id="key" /></td>
	</tr>
	<tr>
  		<th><span class="fRed">*</span><fmt:message key="dictionary.value" /></th>
		<td><input type="text" name="value" id="value" /></td>
	</tr>
	<tr>
		<th><fmt:message key="dictionary.alias" /></th>
		<td><input type="text" name="alias" id="alias" /></td>
	</tr>
	<tr>
		<th><fmt:message key="dictionary.sortOrder" /></th>
		<td><input type="text" name="sortOrder" id="sortOrder" /></td>
	</tr>

	</table>
    </form>
</div>
