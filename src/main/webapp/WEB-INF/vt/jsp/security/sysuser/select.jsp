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
    		var param = $("#selSysUserSearchBar").getParam();
    		selSysUserGrid.loadGridData(param);                    
    	},
    	"<fmt:message key="global.op.close" />": function() {
            dialog.dialogClose();                
    	}
    }
});//end

var selSysUserGrid;
selSysUserGrid = $("#selSysUserGrid");
selSysUserGrid.gridview({
	//caption : "<fmt:message key="sysUser.pageTitle" />",
	url:"${ctx}/security/sysuser/search.json",
	colModel:[
		{name:"id",display:"<fmt:message key="sysUser.id" />",width:50,hidden:true},
		{name:"username",display:"<fmt:message key="sysUser.username" />",width:150},
		{name:"code",display:"<fmt:message key="sysUser.code" />",width:150},
		{name:"description",display:"<fmt:message key="sysUser.description" />",width:220}		
	],
	height:"250px",
	sortname:"username",
	sortorder:"desc",
	pager:"#selSysUserPager",
	loadComplete:function(data){
		$("#searchBar").resultBinding(data);
	}
});
selSysUserGrid.loadGridData({});
</script>
<div id="fmDiv" class="contentDiv">
	<ul style="width:650px;">		
		<li class="contentGrid">
			<table id="selSysUserGrid"></table><div id="selSysUserPager"></div>
		</li>
		<li id="selSysUserSearchBar" class="searchBar">
			<fmt:message key="sysUser.username" /> 
			<input style="width: 100px;" type="text" name="sysUser.username" id="sysUser.username" />
			<fmt:message key="sysUser.code" /> 
			<input style="width: 100px;" type="text" name="sysUser.code" id="sysUser.code" />
		</li>
	</ul>
</div>

