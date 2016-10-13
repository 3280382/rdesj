<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
		checkable: true,
		checkType : {"Y":"ps", "N":"ps"},
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
		$("#fmDetailDiv").setFormValue(data.demoDept);
	});	
}
</script>
<script>
var detail,detailToolBar;
$(function(){
	detail = $("#fmDetailDiv");
	detailToolBar = $("#detailToolBar");
	detailToolBar.addbutton("saveBtn","<fmt:message key="global.op.save" />").click(function(){	
		$.getJSON("update.json", detail.getParam(), function(data){			
			if( !detail.resultBinding(data, {position:"right"}).hasError)
			{
				reloadTree();
			}  
		});
	});	
	detailToolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("add.do",{parentId:tree.getSelectedNode().id},true);		
	});
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
	detailToolBar.addbutton("getTreeCheckedBtn","get checked").click(function(){	
		var checkedNodes = tree.getCheckedNodes();
		var ids = [];
		for (var i=0; i<checkedNodes.length; i++) {
			ids.push(checkedNodes[i].id);
		}		
		alert(ids.join(","));	
	});
	detailToolBar.addbutton("setTreeCheckedBtn","set checked").click(function(){	
		var ids = "10040,10020,10101";	
		ids = ids.split(",");

		tree.checkAllNodes(false);
		var selNode;
		for(var i=0; i<ids.length; i++) {
			selNode = tree.getNodeByParam(tree.getSetting().treeNodeKey,ids[i]);
			selNode[tree.getSetting().checkedCol] = true;
		}
		tree.refresh();
	});
});
</script>
</head>
<body>
<table class="contain"><tr>
	<td style="width:200px;height:460px;" class="leftBar" >
		<ul class="tree" id="treeDiv"></ul>
	</td>
	<td><a href="#" id="spliter"></a></td>
	<td  style="height:98%;">
		<div id="tabsDetail" class="tabDiv">
			<div id="fmDetailDiv" name="fmDetailDiv" value="<fmt:message key="demoData.pageTitle" />" class="content">
			<ul>
				<li id="detailToolBar" class="toolBar"></li>
				<li>
				<table class="editTB">
			    	<tr>
						<th style="width:80px;"><fmt:message key="demoDept.id" /></th>
						<td style="width:400px;"><input disabled type="text" name="id" id="id" value="${demoDept.id}" /></td>
					</tr>
					<tr>
						<th><span class="fRed">*</span><fmt:message key="demoDept.name" /></th>
						<td><input type="text" name="name" id="name" value="<c:out value="${demoDept.name}" />" /></td>
					</tr>
					<tr>
						<th><fmt:message key="demoDept.parentId" /></th>
						<td>
							<select name="parentId" id="parentId" >
								<option value=""></option>
							<c:forEach items="${deptList}" var="it">
								<option parent="${it.parentId}" value="${it.id}" <c:if test="${it.id==demoDept.parentId}">selected</c:if> >${it.name}</option>
							</c:forEach> 
							</select>
						</td>
					</tr>
					<tr>
						<th><fmt:message key="demoDept.code" /></th>
						<td><input type="text" name="code" id="code" value="<c:out value="${demoDept.code}" />" /></td>
						</tr>
						<tr>
						<th><fmt:message key="demoDept.sortOrder" /></th>
						<td><input type="text" name="sortOrder" id="sortOrder" value="<c:out value="${demoDept.sortOrder}" />" /></td>
					</tr>
					<tr>
						<th><fmt:message key="demoDept.deptType" /></th>
						<td><input type="text" name="deptType" id="deptType" value="<c:out value="${demoDept.deptType}" />" /></td>
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
