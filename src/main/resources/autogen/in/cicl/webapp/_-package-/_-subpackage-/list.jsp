<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<%@page import="${parentPackage}.${package}.${subpackage}.dictionary.${className}Constant"%>
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
	<sec:authorize ifAllGranted="<%=${className}Constant.OP_ADD%>">
	toolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("add.do",{},true);		
	});
	</sec:authorize>
	toolBar.addbutton("showBtn","<fmt:message key="global.op.show" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("show.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
	});
	<sec:authorize ifAllGranted="<%=${className}Constant.OP_EDIT%>">
	toolBar.addbutton("editBtn","<fmt:message key="global.op.edit" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("edit.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
	});
	</sec:authorize>
	<sec:authorize ifAllGranted="<%=${className}Constant.OP_REMOVE%>">
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
	</sec:authorize>

	grid = $("#listDetail");
	grid.gridview({
		caption : "<fmt:message key="${objectName}.pageTitle" />",
		url:"search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="${objectName}.id" />",width:50},
			<#list col as r>{name:"${r.name}",display:"<fmt:message key="${objectName}.${r.name}" />",width:90},
			</#list>
			{name:"createdDate",display:"<fmt:message key="${objectName}.createdDate" />",width:90}			
		],
		height:"235px",
		sortname:"id",
		sortorder:"desc",
		pager:"#listPager",
		loadComplete:function(data){
			$("#searchBar").resultBinding(data);
		}
	});
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
	<li id="searchBar" class="searchBar"><#list col as r><fmt:message key="${objectName}.${r.name}" /> <input style="width: 100px;"
		type="text" name="${objectName}.${r.name}" id="${objectName}.${r.name}" /></#list></li>
	<li class="contentGrid">
	<table id="listDetail"></table>
	<div id="listPager"></div>
	</li>
</ul>
</div>
</body>
</html>
