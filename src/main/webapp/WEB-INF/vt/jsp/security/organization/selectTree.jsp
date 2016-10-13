<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script>
var selOrganizationDialog = $("#selOrganizationDiv").dialog({
    title:"<fmt:message key="global.op.select" />",
    width: 480,
    height: 380,
    buttons:{
    	"<fmt:message key="global.op.select" />":function() {		
    		var ids = [];	    
	    	$("#selOrganization").find("option").each(function(index, item){
	    		ids.push(item.value);
	    	});		
	    	alert(ids.join(","));
    		selOrganizationDialog.dialogClose();                
    	},
    	"<fmt:message key="global.op.search" />":function() {
        	selOrganizationTree.setting.asyncParamOther = $("#selOrganizationSearchBar").getParam();
    		selOrganizationTree.reAsyncChildNodes(null, "refresh");   
    	},
    	"<fmt:message key="global.op.close" />": function() {
    		selOrganizationDialog.dialogClose();                
    	}
    }
});//end

var selOrganizationTree = $("#selOrganizationTreeDiv").treeview({
	asyncUrl: "${ctx}/security/organization/searchTree.do",
	callback:{
	asyncSuccess: function(){selOrganizationTree.expandAll();}
	}
});	

function organizationToLeft(){
	$("#selOrganization").find("option:selected").remove();
}

function organizationToRight(){
	var selNode = selOrganizationTree.getSelectedNode();
	var hasSelected = false;
	$("#selOrganization").find("option").each(function(index, item){
		if(item.value==selNode.id) hasSelected = true;
	});
	if(!hasSelected)$("#selOrganization").append("<option value='"+selNode.id+"'>"+selNode.name+"</option>");
}
//selOrganizationTree.reAsyncChildNodes(null, "refresh");
</script>
<div id="selOrganizationDiv" class="contentDiv">
	<table class="contain">
		<tr>
			<td style="width:220px;" class="leftBar" ><ul class="tree" id="selOrganizationTreeDiv"></ul></td>
			<td style="width:30px;text-align:center;">
				<div >
					<a class="btn1" href="javascript:organizationToLeft();"><span></span>&lt;</a><br>
					<a class="btn1" href="javascript:organizationToRight();"><span></span>&gt;</a>
	            </div>
			</td>
			<td style="width:220px;">
				<select id="selOrganization" name="selOrganization" style="height:100%;overflow:auto;width:100%;" multiple="multiple"></select>
			</td>
		</tr>
		<tr>
		<td colspan="3">
		<div id="selOrganizationSearchBar"><fmt:message key="organization.name" /> <input style="width: 100px;" type="text" name="organization.name" id="organization.name" /></div>
		</td>
		</tr>
	</table>
</div>

