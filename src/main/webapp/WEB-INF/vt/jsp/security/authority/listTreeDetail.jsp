<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.cicl.frame.security.authority.dictionary.AuthorityConstant"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/jquery.zTree-2.5/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/tree.css" />
<script type="text/javascript" src="${ctx}/js/lib/jquery.zTree-2.5/jquery-ztree-2.5.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.treeview.js"></script>

<script>
var tree;
$(function(){
	$("#spliter").splite();
	$("#tabsDetail").tabs();
	
	tree = $("#treeDiv").treeview({
		callback:{
			click: onTreeClick,
			asyncSuccess: onTreeAsyncSuccess
		}
	});	
}
);
function onTreeClick(event, treeId, treeNode){
	loadDetail();
}
var curNode, curNodeParent;
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
	curNodeParent = null;
	if(curNode) curNodeParent = curNode.parentNode;
	
	if(curNode){
		tree.reAsyncChildNodes(null, "refresh");
	}
}
function confirmSelect()
{
	curNode = tree.getSelectedNode();
	if(!curNode)
	{
		alert("<fmt:message key="global.op.tip.choose_one" />");
		return false;
	}
	return true;
}
function reloadList(){	
	reloadTree();
}
function loadDetail()
{
	var id = tree.getSelectedNode().id;
	$.getJSON("load.json",{id:id}, function(data){		
		$("#fmDetailDiv").setFormValue(data.authority);
	});	
}
</script>
<script>
var detail,detailToolBar;
$(function(){
	detail = $("#fmDetailDiv");
	detailToolBar = $("#detailToolBar");

	<sec:authorize ifAllGranted="<%=AuthorityConstant.OP_EDIT%>">
	detailToolBar.addbutton("saveBtn","<fmt:message key="global.op.save" />").click(function(){	
		$.getJSON("update.json", detail.getParam(), function(data){			
			if( !detail.resultBinding(data, {position:"right"}).hasError)
			{
				reloadTree();
			}  
		});
	});	
	</sec:authorize>

	<sec:authorize ifAllGranted="<%=AuthorityConstant.OP_ADD%>">
	detailToolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("add.do",{parentId:tree.getSelectedNode().id},true);		
	});
	</sec:authorize>

	<sec:authorize ifAllGranted="<%=AuthorityConstant.OP_REMOVE%>">
	detailToolBar.addbutton("removeBtn","<fmt:message key="global.op.remove" />").click(function(){	
		if(!confirmSelect())return;
		if(!confirm("<fmt:message key="global.op.tip.confirm_remove" />"))return;
				
		$.getJSON("remove.json", {ids:tree.getSelectedNode().id}, function(data){			
			if( !$("#fmForm").resultBinding(data).hasError )
			{
				reloadTree();
			}  
		});
	});	
	</sec:authorize>

	$("#parentId",$("#fmDetailDiv")).treeSelect({});
});
</script>
</head>
<body>
<table class="contain">
	<tr>
	<td style="width:200px;" class="leftBar" >
		<ul class="tree" id="treeDiv"></ul>
	</td>
	<td><a href="#" id="spliter"></a></td>
	<td style="height:98%;">
		<div id="tabsDetail" class="tabDiv">
			<div id="fmDetailDiv" name="fmDetailDiv" value="<fmt:message key="authority.pageTitle" />" class="content">
			<ul>
				<li id="detailToolBar" class="toolBar"></li>
				<li>
					<table class="editTB">
					<tr>
						<th style="width: 90px;"><fmt:message key="authority.id" /></th>
						<td style="width: 200px;"><input disabled type="text" name="id" id="id" /></td>
						<th style="width: 90px;">&nbsp;</th>
						<td style="width: 200px;">&nbsp;</td>
					</tr>
					 
					<tr>
						<th><span class="fRed">*</span><fmt:message key="authority.parentId" /></th>
						<td>
							<select name="parentId" id="parentId" >
								<option value=""></option>
							<c:forEach items="${authorityList}" var="it">
								<option parentId="${it.parentId}" value="${it.id}" >${it.name}</option>
							</c:forEach> 
							</select>
						</td>
  						<th><span class="fRed">*</span><fmt:message key="authority.name" /></th>
						<td><input type="text" name="name" id="name" /></td>
					</tr>
					  
					<tr>
						<th><fmt:message key="authority.code" /></th>
						<td><input type="text" name="code" id="code" /></td>
  						<th><fmt:message key="authority.sortOrder" /></th>
						<td><input type="text" name="sortOrder" id="sortOrder" /></td>
					</tr>
					  
					<tr>
						<th><fmt:message key="authority.status" /></th>
						<td>
							<select name="status" id="status">
								<option value=""></option>
							<c:forEach items="${authorityStatusList}" var="it">
								<option value="${it.key}">${it.value}</option>
							</c:forEach> 
							</select>
						</td>
  						<th><fmt:message key="authority.datatype" /></th>
						<td><input type="text" name="datatype" id="datatype" /></td>
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
		</div>
	</td>
</tr></table>
</body>
</html>
