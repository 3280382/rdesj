<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<%@page import="${parentPackage}.${package}.${subpackage}.dictionary.${className}Constant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${'$'}{ctx}/js/lib/jquery.zTree-2.5/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/tree.css" />
<script type="text/javascript" src="${'$'}{ctx}/js/lib/jquery.zTree-2.5/jquery-ztree-2.5.js"></script>
<script type="text/javascript" src="${'$'}{ctx}/js/com/jquery.treeview.js"></script>

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
		$("#fmDetailDiv").setFormValue(data.${objectName});
	});	
}
</script>
<script>
var detail,detailToolBar;
$(function(){
	detail = $("#fmDetailDiv");
	detailToolBar = $("#detailToolBar");
	<sec:authorize ifAllGranted="<%=${className}Constant.OP_EDIT%>">
	detailToolBar.addbutton("saveBtn","<fmt:message key="global.op.save" />").click(function(){	
		$.getJSON("update.json", detail.getParam(), function(data){			
			if( !detail.resultBinding(data, {position:"right"}).hasError)
			{
				reloadTree();
			}  
		});
	});	
	</sec:authorize>
	<sec:authorize ifAllGranted="<%=${className}Constant.OP_ADD%>">
	detailToolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("add.do",{parentId:tree.getSelectedNode().id},true);		
	});
	</sec:authorize>
	<sec:authorize ifAllGranted="<%=${className}Constant.OP_REMOVE%>">
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
});
</script>
</head>
<body>
<table class="contain"><tr>
	<td style="width:200px;" class="leftBar" >
		<ul class="tree" id="treeDiv"></ul>
	</td>
	<td><a href="#" id="spliter"></a></td>
	<td  style="height:98%;">
		<div id="tabsDetail" class="tabDiv">
			<div id="fmDetailDiv" name="fmDetailDiv" value="<fmt:message key="${objectName}.pageTitle" />" class="content">
			<ul>
				<li id="detailToolBar" class="toolBar"></li>
				<li>
					<table class="editTB">
					<tr>
						<th style="width: ${display.form.colWidth[0]}px;"><fmt:message key="${objectName}.id" /></th>
						<td style="width: ${display.form.colWidth[1]}px;"><input disabled type="text" name="id" id="id" /></td>
						<#list 1..(display.form.cols-1) as index>
						<th style="width: ${display.form.colWidth[index*2]}px;">&nbsp;</th>
						<td style="width: ${display.form.colWidth[index*2+1]}px;">&nbsp;</td>
						</#list>
					</tr>
					<#list col as r> <#if r_index%display.form.cols==0>
					<tr>
						</#if>
						<th><span class="fRed">*</span><fmt:message key="${objectName}.${r.name}" /></th>
						<td><input type="text" name="${r.name}" id="${r.name}" /></td>
						<#if r_index%display.form.cols==(display.form.cols-1)>
					</tr>
					</#if> </#list>
					</table>
				</li>
			</ul>
			</div>
		</div>
	</td>
</tr></table>
</body>
</html>
