<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.show" />",
        width: ${display.dialog.width},
        height: ${display.dialog.height},
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
		<th style="width: ${display.form.colWidth[0]}px;"><fmt:message key="${objectName}.id" /></th>
		<td style="width: ${display.form.colWidth[1]}px;">${'$'}{${objectName}.id}</td>
		<#list 1..(display.form.cols-1) as index>
		<th style="width: ${display.form.colWidth[index*2]}px;">&nbsp;</th>
		<td style="width: ${display.form.colWidth[index*2+1]}px;">&nbsp;</td>
		</#list>
	</tr>
	<#list col as r> <#if r_index%display.form.cols==0>
	<tr>
		</#if>
		<th><span class="fRed">*</span><fmt:message key="${objectName}.${r.name}" /></th>
		<td>${'$'}{${objectName}.${r.name}}</td>
		<#if r_index%display.form.cols==(display.form.cols-1)>
	</tr>
	</#if> </#list>
</table>
</div>