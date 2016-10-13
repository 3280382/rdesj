<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script>
var selOrganizationDialog = $("#selOrganizationDiv").dialog({
    title:"<fmt:message key="global.op.select" />",
    width: 480,
    height: 380,
    buttons:{
    	"<fmt:message key="global.op.select" />":function() {
    		selOrganizationDialog.dialogClose();                
    	},
    	"<fmt:message key="global.op.search" />":function() {
    		selOrganizationGrid.loadGridData($("#selOrganizationSearchBar").getParam());             
    	},
    	"<fmt:message key="global.op.close" />": function() {
    		selOrganizationDialog.dialogClose();                
    	}
    }
});//end

var selOrganizationGrid;
selOrganizationGrid = $("#selOrganizationGrid");
selOrganizationGrid.gridview({
	//caption : "<fmt:message key="role.pageTitle" />",
	url:"${ctx}/security/organization/search.json",
	colModel:[
			{name:"id",display:"id",width:120,hidden:true},
			{name:"name",display:"<fmt:message key="organization.name" />",width:120},
			{name:"code",display:"<fmt:message key="organization.code" />",width:120}
	],
	height:"235px",
	sortname:"sortOrder",
	sortorder:"desc",
	pager:"#selOrganizationPager",
	loadComplete:function(data){
		$("#searchBar").resultBinding(data);
	}
});
selOrganizationGrid.loadGridData({});
</script>
<div id="selOrganizationDiv" class="contentDiv">
	<ul style="width:450px;">		
		<li class="contentGrid">
			<table id="selOrganizationGrid"></table><div id="selOrganizationPager"></div>
		</li>
		<li id="selOrganizationSearchBar" class="searchBar">
			<fmt:message key="organization.name" />
			<input style="width: 100px;" type="text" name="organization.name" id="organization.name" />
			<fmt:message key="organization.code" />
			<input style="width: 100px;" type="text" name="organization.code" id="organization.code" />
		</li>
	</ul>
</div>

