<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>

<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.edit" />",
        width: 350,
        height: 200,
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
    <input type="hidden" name="id" id="id"value="${dictionary.id}" />
	<input type="hidden" name="parentId" id="parentId" value="${dictionary.parentId}"/>
	<input type="hidden" name="nodesType" id="nodesType" value="${dictionary.nodesType}"/>
	<input type="hidden" name="parentKey" id="parentKey" value="${dictionary.parentKey}"/>
	<input type="hidden" name="valuetype" id="valuetype" value="${dictionary.valuetype}"/>
	<input type="hidden" name="validation" id="validation" value="${dictionary.validation}"/>
	<input type="hidden" name="value1" id="value1" value="${dictionary.value1}"/>
	<input type="hidden" name="valuetype1" id="valuetype1" value="${dictionary.valuetype1}"/>
	<input type="hidden" name="validation1" id="validation1" value="${dictionary.validation1}"/>
	<input type="hidden" name="enable" id="enable" value="${dictionary.enable}"/>
	<input type="hidden" name="visualable" id="visualable" value="${dictionary.visualable}"/>
	<input type="hidden" name="displayType" id="displayType" value="${dictionary.displayType}"/>
	<input type="hidden" name="editable" id="editable" value="${dictionary.editable}"/>
	<input type="hidden" name="description" id="description" value="${dictionary.description}"/>
	<br>
    <table class="editTB">		
    	<tr>
			<th style="width: 90px;"><span class="fRed">*</span><fmt:message key="dictionary.nodesType" /></th>
			<td style="width: 250px;">
				<select name="nodesType" id="nodesType">
				<c:forEach items="${nodesTypeList}" var="it">
					<option value="${it.key}" <c:if test="${it.key==dictionary.nodesType}">selected</c:if>>${it.value}</option>
				</c:forEach> 
				</select>
			</td>
		</tr>
		<tr>
	  		<th><span class="fRed">*</span><fmt:message key="dictionary.key" /></th>
			<td><input type="text" name="key" id="key" value="${dictionary.key}"  /></td>
		</tr>
		
		<tr>
	  		<th><span class="fRed">*</span><fmt:message key="dictionary.value" /></th>
			<td><input type="text" name="value" id="value" value="${dictionary.value}"  /></td>
		</tr>
		  
		<tr>
			<th><fmt:message key="dictionary.alias" /></th>
			<td><input type="text" name="alias" id="alias" value="${dictionary.alias}"  /></td>
		</tr>	  
		
		<tr>
			<th><fmt:message key="dictionary.sortOrder" /></th>
			<td><input type="text" name="sortOrder" id="sortOrder" value="${dictionary.sortOrder}"  /></td>
		</tr>	
	</table>
    </form>
</div>
