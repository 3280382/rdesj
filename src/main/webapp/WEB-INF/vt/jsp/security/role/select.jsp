<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script>
var dialog = $("#fmDiv").dialog({
    title:"<fmt:message key="global.op.select" />",
    width: 680,
    height: 380,
    buttons:{
    	"<fmt:message key="global.op.select" />":function() {
            dialog.dialogClose();                
    	},
    	"<fmt:message key="global.op.search" />":function() {
    		var param = $("#selRoleSearchBar").getParam();
    		selRoleGrid.loadGridData(param);                    
    	},
    	"<fmt:message key="global.op.close" />": function() {
            dialog.dialogClose();                
    	}
    }
});//end

var selRoleGrid;
selRoleGrid = $("#selRoleGrid");
selRoleGrid.gridview({
	//caption : "<fmt:message key="role.pageTitle" />",
	url:"${ctx}/security/role/search.json",
	colModel:[
		{name:"id",display:"<fmt:message key="role.id" />",width:50,hidden:true},
		{name:"name",display:"<fmt:message key="role.name" />",width:150},
		{name:"code",display:"<fmt:message key="role.code" />",width:150},
		{name:"description",display:"<fmt:message key="role.description" />",width:220}		
	],
	height:"250px",
	sortname:"sortOrder",
	sortorder:"desc",
	pager:"#selRolePager",
	loadComplete:function(data){
		$("#searchBar").resultBinding(data);
	}
});
selRoleGrid.loadGridData({});
</script>
<div id="fmDiv" class="contentDiv">
	<ul style="width:650px;">		
		<li class="contentGrid">
			<table id="selRoleGrid"></table><div id="selRolePager"></div>
		</li>
		<li id="selRoleSearchBar" class="searchBar">
			<fmt:message key="role.name" /> 
			<input style="width: 100px;" type="text" name="role.name" id="role.name" />
			<fmt:message key="role.code" /> 
			<input style="width: 100px;" type="text" name="role.code" id="role.code" />
		</li>
	</ul>
</div>

