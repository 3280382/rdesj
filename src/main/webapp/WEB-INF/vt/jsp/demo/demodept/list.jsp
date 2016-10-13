<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/jquery.zTree-2.5/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/tree.css" />
<script type="text/javascript" src="${ctx}/js/lib/jquery.zTree-2.5/jquery-ztree-2.5.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.treeview.js"></script>

<script>
var grid,toolBar,tree;
$(function(){
	toolBar = $("#toolBar");
	toolBar.addbutton("searchBtn","<fmt:message key="global.op.search" />").click(function(){	
		var param = $("#searchBar").getParam();
		var curNode = tree.getSelectedNode();
		if(curNode){
			$.extend(param, {"demoDept.parentId":curNode[tree.getSetting().treeNodeKey]});
		}
		grid.loadGridData(param);
	});
	toolBar.addbutton("tocsvBtn","<fmt:message key="global.op.download" />CSV").click(function(){	
		var param = $("#searchBar").getParam();
		$.downloadFile(param,"list2csv.do");
	});
	toolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		var curNode = tree.getSelectedNode();
		if(curNode){			
			$.dialog("add.do",{parentId:curNode.id},true);		
		}
		else {
			alert("<fmt:message key="global.op.tip.choose_one" />");
		}		
	});
	toolBar.addbutton("showBtn","<fmt:message key="global.op.show" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("show.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
	});
	toolBar.addbutton("editBtn","<fmt:message key="global.op.edit" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("edit.do", {id:grid.getRowData(grid.attr("p").selrow).id},true);
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
				reloadTree();
			}  
		});
	});	

	grid = $("#listDetail");
	grid.gridview({
		caption : "<fmt:message key="demoDept.pageTitle" />",
		url:"search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="demoDept.id" />",width:50},
			{name:"parentId",hidden:true},
			{name:"name",display:"<fmt:message key="demoDept.name" />",width:150},
			{name:"code",display:"<fmt:message key="demoDept.code" />",width:120},
			{name:"deptType",display:"<fmt:message key="demoDept.deptType" />",width:80},
			{name:"sortOrder",display:"<fmt:message key="demoDept.sortOrder" />",width:120}		
		],
		height:"320px",
		sortname:"sortOrder",
		sortorder:"asc",
		pager:"#listPager",
		loadComplete:function(data){
			$("#searchBar").resultBinding(data);
		}
	});

	$("#spliter").splite();
	
	tree = $("#treeDiv").treeview({
		callback:{
			click: onTreeClick,
			asyncSuccess: onTreeAsyncSuccess
		}
	});	
}
);
function onTreeClick(event, treeId, treeNode){
	toolBar.button("searchBtn").click();
}
var curNode;
function onTreeAsyncSuccess(){
	if(curNode){
		tree.selectNode(tree.getNodeByParam(tree.getSetting().treeNodeKey,curNode.id));
	}
}
function reloadTree(){
	curNode = tree.getSelectedNode();

	if(curNode){
		tree.reAsyncChildNodes(null, "refresh");
	}
}
function reloadList(){
	grid.trigger("reloadGrid");			
	reloadTree();
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
<table><tr>
	<td style="width:200px;" class="leftBar" >
		<ul class="tree" id="treeDiv"></ul>
	</td>
	<td><a href="#" id="spliter"></a></td>
	<td style="width:600px;">
		<div class="contentDiv">
			<ul>
			<li id="toolBar" class="toolBar">
			</li>
			<li id="searchBar" class="searchBar">		
				<fmt:message key="demoDept.name" /><input style="width:100px;" type="text" name="demoDept.name" id="demoDept.name" />					
			</li>
			<li class="contentGrid"><table id="listDetail"></table><div id="listPager"></div></li>
			</ul>
		</div>
	</td>
</tr></table>
</body>
</html>
