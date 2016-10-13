<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.cicl.frame.security.role.dictionary.RoleConstant"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/jquery.zTree-2.5/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/tree.css" />
<script type="text/javascript" src="${ctx}/js/lib/jquery.zTree-2.5/jquery-ztree-2.5.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.treeview.js"></script>

<script>
var grid,toolBar;
$(function(){
	toolBar = $("#toolBar");
	toolBar.addbutton("searchBtn","<fmt:message key="global.op.search" />").click(function(){	
		var param = $("#searchBar").getParam();
		grid.loadGridData(param);
	});
	<sec:authorize ifAllGranted="<%=RoleConstant.OP_REMOVE%>">
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
		caption : "<fmt:message key="role.pageTitle" />",
		url:"search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="role.id" />",width:50,hidden:true},
			{name:"name",display:"<fmt:message key="role.name" />",width:170}
			/*{name:"code",display:"<fmt:message key="role.code" />",width:90},
			{name:"sortOrder",display:"<fmt:message key="role.sortOrder" />",width:90},
			{name:"status",display:"<fmt:message key="role.status" />",width:90},
			{name:"datatype",display:"<fmt:message key="role.datatype" />",width:90},
			{name:"description",display:"<fmt:message key="role.description" />",width:90}*/	
		],
		height:"325px",
		rownumbers:false,
		sortname:"id",
		sortorder:"desc",
		pager:"#listPager",
		viewrecords:false,
		onSelectRow:function(rowid)	{
			loadDetail(rowid);
		},
		loadComplete:function(data){
			$("#searchBar").resultBinding(data);
		}
	});

	$("#tabsDetail").tabs();
	$("#spliter").splite();	
	grid.loadGridData({});
}
);
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
function loadDetail(rowid)
{
	var id = grid.getRowData(rowid).id;
	$.getJSON("load.json",{id:id}, function(data){		
		$("#fmDetailDiv").setFormValue(data.role);
		//var children = data.role.authorities || [];
		var children = data.authorities || [];		
		checkTree(children);
	});	
}
</script>
<script>
var detail,detailToolBar;
$(function(){
	detail = $("#fmDetailDiv");
	detailToolBar = $("#detailToolBar");

	<sec:authorize ifAllGranted="<%=RoleConstant.OP_EDIT%>">
	detailToolBar.addbutton("saveBtn","<fmt:message key="global.op.save" />").click(function(){	
		$.getJSON("update.json", detail.getParam(), function(data){			
			if( !detail.resultBinding(data, {position:"bottom"}).hasError)
			{
				grid.trigger("reloadGrid");
			}  
		});
	});	
	</sec:authorize>
	
	<sec:authorize ifAllGranted="<%=RoleConstant.OP_ADD%>">
	detailToolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("add.do",{},true);		
	});
	</sec:authorize>
});
</script>
<script>
var detailTree,detailTreeToolBar,tree;
$(function(){
	detailTree = $("#fmDetailTreeDiv");
	detailTreeToolBar = $("#detailTreeToolBar");

	<sec:authorize ifAllGranted="<%=RoleConstant.OP_EDIT%>">
	detailTreeToolBar.addbutton("saveBtn","<fmt:message key="global.op.save" />").click(function(){
		if(!confirmSelect())return;
		var checkedNodes = tree.getCheckedNodes();
		var ids = [];
		for (var i=0; i<checkedNodes.length; i++) {
			ids.push(checkedNodes[i].id);
		}		
			
		$.getJSON("updateAuthorities.json", {id:grid.getRowData(grid.attr("p").selrow).id,childrenIds:ids.join(",")}, function(data){			
			if( !detailTree.resultBinding(data, {position:"right"}).hasError)
			{
				grid.trigger("reloadGrid");
			}  
		});
	});	
	</sec:authorize>

	tree = $("#treeDiv").treeview({
		checkable: true,
		checkType : {"Y":"ps", "N":"ps"},
		asyncUrl: "${ctx}/security/authority/loadTree.json",
		callback:{
			asyncSuccess: onTreeAsyncSuccess
		}
	});	
});

function onTreeAsyncSuccess(){
	tree.expandAll();
}
function checkTree(children){
	tree.checkAllNodes(false);
	var selNode;
	for(var i=0; i<children.length; i++) {
		selNode = tree.getNodeByParam(tree.getSetting().treeNodeKey,children[i].id);
		selNode[tree.getSetting().checkedCol] = true;
	}
	tree.refresh();	
}
</script>
</head>
<body>
<table class="contain">
	<tr>
		<td style="width:190px;" class="leftBar">
		<ul>
			<li id="searchBar" class="searchBar">
			<table>				
				<tr>
					<th><fmt:message key="role.name" /></th>
					<td><input style="width: 150px;" type="text" name="role.name" id="role.name" /></td>
				</tr>
			</table>
			</li>
			<li id="toolBar" class="toolBar"></li>
			<li>
			<table id="listDetail"></table>
			<div id="listPager"></div>
			</li>
		</ul>
		</td>
		<td><a href="#" id="spliter"></a></td>
		<td style="height:98%;">
		<div id="tabsDetail" class="tabDiv">
			<div id="fmDetailDiv" name="fmDetailDiv" value="<fmt:message key="role.pageTitle" />" class="content">
				<ul>
					<li id="detailToolBar" class="toolBar"></li>
					<li>
					<table class="editTB">
						<tr>
							<th style="width: 90px;"><fmt:message key="role.id" /></th>
							<td style="width: 200px;"><input disabled type="text" name="id" id="id" /></td>
							<th style="width: 90px;">&nbsp;</th>
							<td style="width: 200px;">&nbsp;</td>
						</tr>
						 
						<tr>
							<th><span class="fRed">*</span><fmt:message key="role.name" /></th>
							<td><input type="text" name="name" id="name" /></td>
		  					<th><span class="fRed">*</span><fmt:message key="role.code" /></th>
							<td><input type="text" name="code" id="code" /></td>
						</tr>
						  
						<tr>
							<th><fmt:message key="role.sortOrder" /></th>
							<td><input type="text" name="sortOrder" id="sortOrder" /></td>
		  					<th><fmt:message key="role.status" /></th>
							<td>
								<select name="status" id="status">
									<option value=""></option>
								<c:forEach items="${roleStatusList}" var="it">
									<option value="${it.key}">${it.value}</option>
								</c:forEach> 
								</select>
							</td>
						</tr>
						  
						<tr>
							<th><fmt:message key="role.datatype" /></th>
							<td><input type="text" name="datatype" id="datatype" /></td>
		  					<th>&nbsp;</th>
							<td>&nbsp;</td>
						</tr>
						 <tr>
						<th><fmt:message key="organization.description" /></th>
							<td colspan="3">
							<textarea style="width:450px;height:100px;" name="description" id="description"> </textarea>
							</td> 
						</tr>
					</table>
					</li>
				</ul>
			</div>
			<div id="fmDetailTreeDiv" name="fmDetailTreeDiv" value="<fmt:message key="authority.pageTitle" />" class="content" >
				<ul >
					<li id="detailTreeToolBar" class="toolBar"></li>
					<li>
						<ul class="tree content" id="treeDiv"></ul>
					</li>
				</ul>
			</div>
		</div>
		</td>
	</tr>
</table>
</body>
</html>
