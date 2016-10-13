<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.add" />",
        width: ${display.dialog.width},
        height: ${display.dialog.height},
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
	<#list col as r> <#if r_index%display.form.cols==0>
	<tr>
		</#if>
		<th<#if r_index<display.form.cols> style="width: ${display.form.colWidth[r_index*2]}px;"</#if>><span class="fRed">*</span><fmt:message key="${objectName}.${r.name}" /></th>
		<td<#if r_index<display.form.cols> style="width: ${display.form.colWidth[r_index*2+1]}px;"</#if>><#if display.type=="TREE"><input type="text" name="${r.name}" id="${r.name}" value="${'$'}{parentId}" /><#else><input type="text" name="${r.name}" id="${r.name}" /></#if></td>
		<#if r_index%display.form.cols==(display.form.cols-1)>
	</tr>
	</#if> </#list>
	</table>
    </form>
</div>
