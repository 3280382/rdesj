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
			{name:"id",display:"<fmt:message key="${objectName}.id" />",width:50,hidden:true},
			<#list col as r>
			<#if r_index==1>/*</#if>{name:"${r.name}",display:"<fmt:message key="${objectName}.${r.name}" />",width:90},<#if !r_has_next>*/</#if>
			</#list>
			{name:"createdDate",display:"<fmt:message key="${objectName}.createdDate" />",width:80}			
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
			if( !detail.resultBinding(data, {position:"bottom"}).hasError)
			{
				grid.trigger("reloadGrid");
			}  
		});
	});	
	</sec:authorize>
	<sec:authorize ifAllGranted="<%=${className}Constant.OP_ADD%>">
	detailToolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("add.do",{},true);		
	});
	</sec:authorize>
});
</script>
</head>
<body>
<table class="contain">
	<tr>
		<td style="width:190px;" class="leftBar">
		<ul>
			<li id="searchBar" class="searchBar">
			<table>				
					<#list col as r>
				<#if r_index==1><!--</#if><tr>
					<th><fmt:message key="${objectName}.${r.name}" /></th>
					<td><input style="width: 150px;" type="text" name="${objectName}.${r.name}" id="${objectName}.${r.name}" /></td>
				</tr><#if !r_has_next>--></#if>
					</#list>				
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
	</tr>
</table>
</body>
</html>
