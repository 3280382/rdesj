<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>

<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.edit" />",
        width: ${display.dialog.width},
        height: ${display.dialog.height},
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
		<th style="width: ${display.form.colWidth[0]}px;"><fmt:message key="${objectName}.id" /></th>
		<td style="width: ${display.form.colWidth[1]}px;"><input disabled type="text" name="id" id="id"  value="${'$'}{${objectName}.id}" /></td>
		<#list 1..(display.form.cols-1) as index>
		<th style="width: ${display.form.colWidth[index*2]}px;">&nbsp;</th>
		<td style="width: ${display.form.colWidth[index*2+1]}px;">&nbsp;</td>
		</#list>
	</tr>
	<#list col as r> <#if r_index%display.form.cols==0>
	<tr>
		</#if>
		<th><span class="fRed">*</span><fmt:message key="${objectName}.${r.name}" /></th>
		<td><input type="text" name="${r.name}" id="${r.name}" value="${'$'}{${objectName}.${r.name}}"  /></td>
		<#if r_index%display.form.cols==(display.form.cols-1)>
	</tr>
	</#if> </#list>
	</table>
    </form>
</div>
