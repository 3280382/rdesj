<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.show" />",
        width: 650,
        height: 360,
        buttons:{
            "<fmt:message key="global.op.close" />": function() {
                dialog.dialogClose();
            }
        }
    });//end

</script>
<div id="fmDiv"><br>
<table class="editTB">
	<tr>
		<th style="width: 90px;"><fmt:message key="dictionary.id" /></th>
		<td style="width: 200px;">${dictionary.id}</td>
		<th style="width: 90px;">&nbsp;</th>
		<td style="width: 200px;">&nbsp;</td>
	</tr>
	 
	<tr>
		<th><fmt:message key="dictionary.parentId" /></th>
		<td>${dictionary.parentId}</td>
  		<th><fmt:message key="dictionary.nodesType" /></th>
		<td>${dictionary.nodesType}</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="dictionary.parentKey" /></th>
		<td>${dictionary.parentKey}</td>
  		<th><fmt:message key="dictionary.key" /></th>
		<td>${dictionary.key}</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="dictionary.alias" /></th>
		<td>${dictionary.alias}</td>
  		<th><fmt:message key="dictionary.value" /></th>
		<td>${dictionary.value}</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="dictionary.valuetype" /></th>
		<td>${dictionary.valuetype}</td>
  		<th><fmt:message key="dictionary.validation" /></th>
		<td>${dictionary.validation}</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="dictionary.value1" /></th>
		<td>${dictionary.value1}</td>
  		<th><fmt:message key="dictionary.valuetype1" /></th>
		<td>${dictionary.valuetype1}</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="dictionary.validation1" /></th>
		<td>${dictionary.validation1}</td>
  		<th><fmt:message key="dictionary.sortOrder" /></th>
		<td>${dictionary.sortOrder}</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="dictionary.enable" /></th>
		<td>${dictionary.enable}</td>
  		<th><fmt:message key="dictionary.visualable" /></th>
		<td>${dictionary.visualable}</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="dictionary.displayType" /></th>
		<td>${dictionary.displayType}</td>
  		<th><fmt:message key="dictionary.editable" /></th>
		<td>${dictionary.editable}</td>
	</tr>
	  
	<tr>
		<th><fmt:message key="dictionary.description" /></th>
		<td>${dictionary.description}</td>
 
</table>
</div>