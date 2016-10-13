<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="upload.title" />",
        width: 600,
        height: 120,
        buttons:{
	    	"<fmt:message key="global.op.ok" />":function() {
                $("#uploadForm").submit();  
                dialog.dialogClose();              
	    	},
	    	"<fmt:message key="global.op.close" />": function() {
                dialog.dialogClose();                
	    	}
        }
    });//end
</script>
<div id="fmDiv">
     <form id="uploadForm" method="post" target="_blank" action="${action}" enctype="multipart/form-data">
		<table class="editTB">					
			<tr>
				<th style="width: 100px;"><fmt:message key="upload.fileName" /></th>
				<td style="width: 150px;"><input type="text" name="names" id="names" /></td>
				<th style="width: 100px;"><fmt:message key="upload.filePath" /></th>
				<td style="width: 150px;"><input type="file" name="file" id="file" /></td>
			</tr>
			<tr>
				<th style="width: 100px;"><fmt:message key="upload.fileName" /></th>
				<td style="width: 150px;"><input type="text" name="names" id="names" /></td>
				<th style="width: 100px;"><fmt:message key="upload.filePath" /></th>
				<td style="width: 150px;"><input type="file" name="file" id="file" /></td>
			</tr>
		</table>
     </form>
</div>
 