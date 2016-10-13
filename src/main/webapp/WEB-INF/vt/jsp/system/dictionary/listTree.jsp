<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<%@page import="com.cicl.frame.system.dictionary.dictionary.DictionaryConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/lib/jquery.zTree-2.5/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/tree.css" />
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
			$.extend(param, {"dictionary.parentId":curNode[tree.getSetting().treeNodeKey]});
		}
		grid.loadGridData(param);
	});
	toolBar.addbutton("tocsvBtn","<fmt:message key="global.op.download" />CSV").click(function(){	
		var param = $("#searchBar").getParam();
		$.downloadFile(param,"list2csv.do");
	});
	<sec:authorize ifAllGranted="<%=DictionaryConstant.OP_ADD%>">
	toolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		var curNode = tree.getSelectedNode();
		if(curNode){			
			$.dialog("add.do",{parentId:curNode.id,parentKey:curNode.key},true);		
		}
		else {
			alert("<fmt:message key="global.op.tip.choose_one" />");
		}		
	});
	</sec:authorize>
	toolBar.addbutton("showBtn","<fmt:message key="global.op.show" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("show.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
	});
	<sec:authorize ifAllGranted="<%=DictionaryConstant.OP_EDIT%>">
	toolBar.addbutton("editBtn","<fmt:message key="global.op.edit" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("edit.do", {id:grid.getRowData(grid.attr("p").selrow).id},true);
	});
	</sec:authorize>
	<sec:authorize ifAllGranted="<%=DictionaryConstant.OP_REMOVE%>">
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
	</sec:authorize>
	grid = $("#listDetail");
	grid.gridview({
		caption : "<fmt:message key="dictionary.pageTitle" />",
		url:"search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="dictionary.id" />",width:50,hidden:true},
			{name:"parentId",display:"<fmt:message key="dictionary.parentId" />",width:90,hidden:true},
			{name:"nodesType",display:"<fmt:message key="dictionary.nodesType" />",width:90,hidden:true},
			{name:"parentKey",display:"<fmt:message key="dictionary.parentKey" />",width:90,hidden:true},
			{name:"key",display:"<fmt:message key="dictionary.key" />",width:100},			
			{name:"value",display:"<fmt:message key="dictionary.value" />",width:250},
			{name:"alias",display:"<fmt:message key="dictionary.alias" />",width:150},
			{name:"valuetype",display:"<fmt:message key="dictionary.valuetype" />",width:90,hidden:true},
			{name:"validation",display:"<fmt:message key="dictionary.validation" />",width:90,hidden:true},
			{name:"value1",display:"<fmt:message key="dictionary.value1" />",width:90,hidden:true},
			{name:"valuetype1",display:"<fmt:message key="dictionary.valuetype1" />",width:90,hidden:true},
			{name:"validation1",display:"<fmt:message key="dictionary.validation1" />",width:90,hidden:true},
			{name:"sortOrder",display:"<fmt:message key="dictionary.sortOrder" />",width:90,hidden:true},
			{name:"enable",display:"<fmt:message key="dictionary.enable" />",width:90,hidden:true},
			{name:"visualable",display:"<fmt:message key="dictionary.visualable" />",width:90,hidden:true},
			{name:"displayType",display:"<fmt:message key="dictionary.displayType" />",width:90,hidden:true},
			{name:"editable",display:"<fmt:message key="dictionary.editable" />",width:90,hidden:true},
			{name:"description",display:"<fmt:message key="dictionary.description" />",width:90,hidden:true},
			{name:"createdDate",display:"<fmt:message key="dictionary.createdDate" />",width:90,hidden:true}			
		],
		height:"300px",
		sortname:"sortOrder",
		sortorder:"asc",
		pager:"#listPager",
		loadComplete:function(data){
			$("#searchBar").resultBinding(data);
		}
	});

	$("#spliter").splite();
	
	tree = $("#treeDiv").treeview({
		nameCol:"value",
		callback:{
			click: onTreeClick,
			asyncSuccess: onTreeAsyncSuccess
		}
	});	
}
);
function onTreeClick(event, treeId, treeNode){
	if(treeNode.editable=="1"){
		toolBar.button("addBtn").enable();
		toolBar.button("editBtn").enable();
		toolBar.button("removeBtn").enable();
	}
	else{
		toolBar.button("addBtn").disable();
		toolBar.button("editBtn").enable();
		toolBar.button("removeBtn").enable();
	}
	
	toolBar.button("searchBtn").click();
}
var curNode;
function onTreeAsyncSuccess(){
	if(curNode){
		var selNode = tree.getNodeByParam(tree.getSetting().treeNodeKey,curNode.id);
		if(selNode){
			tree.selectNode(selNode);
		}
		else if(curNodeParent){
			tree.selectNode(tree.getNodeByParam(tree.getSetting().treeNodeKey,curNodeParent.id));
		}
	}	
	else{
		tree.expandAll();
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
<table>
	<tr>
		<td style="width: 200px;" class="leftBar">
		<ul style="width: 200px;" class="tree" id="treeDiv"></ul>
		</td>
		<td><a href="#" id="spliter"></a></td>
		<td style="height: 98%;">
		<div style="width: 600px;" class="contentDiv">
		<ul>
			<li id="toolBar" class="toolBar"></li>
			<li id="searchBar" class="searchBar">
				<fmt:message key="dictionary.key" /> 
				<input style="width: 100px;" type="text" name="dictionary.key" id="dictionary.key" />
				<fmt:message key="dictionary.value" /> 
				<input style="width: 100px;" type="text" name="dictionary.value" id="dictionary.value" />
				<fmt:message key="dictionary.alias" /> 
				<input style="width: 100px;" type="text" name="dictionary.alias" id="dictionary.alias" />				
			</li>
			<li class="contentGrid">
			<table id="listDetail"></table>
			<div id="listPager"></div>
			</li>
		</ul>
		</div>
		</td>
	</tr>
</table>
</body>
</html>
