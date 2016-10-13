<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
var grid,toolBar;
$(function(){
	toolBar = $("#toolBar");
	toolBar.addbutton("searchBtn","<fmt:message key="global.op.search" />").click(function(){	
		var param = $("#searchBar").getParam();
		grid.loadGridData(param);
	});
	toolBar.addbutton("tocsvBtn","<fmt:message key="global.op.download" />CSV").click(function(){	
		var param = $("#searchBar").getParam();
		$.downloadFile(param,"list2csv.do");
	});
	toolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("add.do",{},true);		
	});
	toolBar.addbutton("showBtn","<fmt:message key="global.op.show" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("show.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
	});
	toolBar.addbutton("editBtn","<fmt:message key="global.op.edit" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("edit.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
	});
	toolBar.addbutton("removeBtn","<fmt:message key="global.op.remove" />").click(function(){	
		if(!confirmSelect())return;
		if(!confirm("<fmt:message key="global.op.tip.confirm_remove" />"))return;

		var selarrrow = grid.attr("p").selarrrow;
		var ids = [];
		for( var i=0; i<selarrrow.length; i++)
		{
			ids.push(grid.getRowData(selarrrow[i]).id);
		}
		
		$.getJSON("remove.json", {ids:ids.join(",")}, function(data){			
			if( !$("#fmForm").resultBinding(data).hasError )
			{
				grid.trigger("reloadGrid");
			}  
		});
	});	

	grid = $("#listDetail");
	grid.gridview({
		caption : "<fmt:message key="role.pageTitle" />",
		url:"search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="role.id" />",width:50},
			{name:"name",display:"<fmt:message key="role.name" />",width:90},
			{name:"code",display:"<fmt:message key="role.code" />",width:90},
			{name:"sortOrder",display:"<fmt:message key="role.sortOrder" />",width:90},
			{name:"status",display:"<fmt:message key="role.status" />",width:90},
			{name:"datatype",display:"<fmt:message key="role.datatype" />",width:90},
			{name:"description",display:"<fmt:message key="role.description" />",width:90},
			{name:"createdDate",display:"<fmt:message key="role.createdDate" />",width:90}			
		],
		height:"235px",
		sortname:"id",
		sortorder:"desc",
		pager:"#listPager",
		loadComplete:function(data){
			$("#searchBar").resultBinding(data);
		}
	});
	grid.loadGridData({});
});
function reloadList(){
	grid.trigger("reloadGrid");			
}
function confirmSelect()
{
	if(!grid.getRowData(grid.attr("p").selrow).id)
	{
		alert("<fmt:message key="global.op.tip.choose_one" />");
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div class="contentDiv">
<ul>
	<li id="toolBar" class="toolBar"></li>
	<li id="searchBar" class="searchBar"><fmt:message key="role.name" /> <input style="width: 100px;"
		type="text" name="role.name" id="role.name" /><fmt:message key="role.code" /> <input style="width: 100px;"
		type="text" name="role.code" id="role.code" /><fmt:message key="role.sortOrder" /> <input style="width: 100px;"
		type="text" name="role.sortOrder" id="role.sortOrder" /><fmt:message key="role.status" /> <input style="width: 100px;"
		type="text" name="role.status" id="role.status" /><fmt:message key="role.datatype" /> <input style="width: 100px;"
		type="text" name="role.datatype" id="role.datatype" /><fmt:message key="role.description" /> <input style="width: 100px;"
		type="text" name="role.description" id="role.description" /></li>
	<li class="contentGrid">
	<table id="listDetail"></table>
	<div id="listPager"></div>
	</li>
</ul>
</div>
</body>
</html>
