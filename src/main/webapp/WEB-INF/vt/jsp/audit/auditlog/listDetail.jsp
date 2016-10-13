<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="${ctx}/js/com/jquery.treeview.js"></script>
<script>
var grid,toolBar;
$(function(){
	toolBar = $("#toolBar");
	toolBar.addbutton("searchBtn","<fmt:message key="global.op.search" />").click(function(){	
		var param = $("#searchBar").getParam();
		grid.loadGridData(param);
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
		caption : "<fmt:message key="auditLog.pageTitle" />",
		url:"search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="auditLog.id" />",width:50,hidden:true},
			{name:"userId",display:"<fmt:message key="auditLog.userId" />",width:90,hidden:true},
			{name:"loginName",display:"<fmt:message key="auditLog.loginName" />",width:80},
			{name:"targetEntityType",display:"<fmt:message key="auditLog.targetEntityType" />",width:90,formatter:"select",editoptions:{value:"<c:forEach items="${authorityList}" var="it">${it.code}:${it.name};</c:forEach>"}},
			{name:"opType",display:"<fmt:message key="auditLog.opType" />",width:70,formatter:"select",editoptions:{value:"<c:forEach items="${authorityList}" var="it">${it.code}:${it.name};</c:forEach>"}},
			{name:"opTime",display:"<fmt:message key="auditLog.opTime" />",width:140}
		],
		height:"235px",
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
	$("#auditLog\\.targetEntityType").treeSelect({key:"id"});
	$("#auditLog\\.opType").treeSelect({key:"id"});
	$("#auditLog\\.opTime").datepickerView();
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
		$("#fmDetailDiv").setFormValue(data.auditLog);
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
			if( !detail.resultBinding(data, {position:"bottom"}).hasError)
			{
				grid.trigger("reloadGrid");
			}  
		});
	});	
	detailToolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("add.do",{},true);		
	});
});
</script>
</head>
<body>
<table class="contain">
	<tr>
		<td style="width:360px;" class="leftBar">
		<ul>
			<li id="searchBar" class="searchBar">
			<table>				
				<tr>
					<th style="width:70px;"><fmt:message key="auditLog.loginName" /></th>
					<td style="width:110px;"><input style="width: 110px;" type="text" name="auditLog.loginName" id="auditLog.loginName" /></td>
					<th style="width:70px;"><fmt:message key="auditLog.username" /></th>
					<td style="width:110px;"><input style="width: 110px;" type="text" name="auditLog.username" id="auditLog.username" /></td>
				</tr>
				<tr>
					<th><fmt:message key="auditLog.userIp" /></th>
					<td><input style="width: 110px;" type="text" name="auditLog.userIp" id="auditLog.userIp" /></td>
					<th><fmt:message key="auditLog.opTime" /></th>
					<td><input style="width: 110px;" type="text" name="auditLog.opTime" id="auditLog.opTime" /></td>
				</tr>
				<tr>					
					<th><fmt:message key="auditLog.opType" /></th>
					<td colspan="3">
							<select name="auditLog.opType" id="auditLog.opType" >
								<option value=""></option>
							<c:forEach items="${authorityList}" var="it">
								<option parentId="${it.parentId}" id="${it.id}" value="${it.code}" >${it.name}</option>
							</c:forEach> 
						</select>
					</td>
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
		<td  style="height:98%;">
		<div id="tabsDetail" class="tabDiv">
		<div id="fmDetailDiv" name="fmDetailDiv" value="<fmt:message key="auditLog.pageTitle" />" class="content">
		<ul>
			<li id="detailToolBar" class="toolBar"></li>
			<li>
			<table class="editTB">
				<tr style="display:none;">
					<th><fmt:message key="auditLog.id" /></th>
					<td><input disabled type="text" name="id" id="id" /></td>
					<th>&nbsp;</th>
					<td>&nbsp;</td>
				</tr>
				 
				<tr>
					<th style="width: 90px;"><fmt:message key="auditLog.userId" /></th>
					<td style="width: 200px;"><input type="text" name="userId" id="userId" /></td>
  					<th style="width: 90px;"><fmt:message key="auditLog.userIp" /></th>
					<td style="width: 200px;"><input type="text" name="userIp" id="userIp" /></td>
				</tr>
				  
				<tr>
					<th><fmt:message key="auditLog.loginName" /></th>
					<td><input type="text" name="loginName" id="loginName" /></td>
  					<th><fmt:message key="auditLog.username" /></th>
					<td><input type="text" name="username" id="username" /></td>
				</tr>
				  
				<tr>
					<th><fmt:message key="auditLog.targetId" /></th>
					<td><input type="text" name="targetId" id="targetId" /></td>
					<th><fmt:message key="auditLog.targetName" /></th>
					<td><input type="text" name="targetName" id="targetName" /></td>
  					
				</tr>				
				
				<tr>
					<th><fmt:message key="auditLog.targetEntityType" /></th>
					<td>
						<select name="targetEntityType" id="targetEntityType" >
								<option value=""></option>
							<c:forEach items="${authorityList}" var="it">
								<option parentId="${it.parentId}" id="${it.id}" value="${it.code}" >${it.name}</option>
							</c:forEach> 
						</select>
					</td>
					<th><fmt:message key="auditLog.opType" /></th>
					<td>
						<select name="opType" id="opType" >
								<option value=""></option>
							<c:forEach items="${authorityList}" var="it">
								<option parentId="${it.parentId}" id="${it.id}" value="${it.code}" >${it.name}</option>
							</c:forEach> 
						</select>
					</td>  					
				</tr>
				  
				<tr>
					<th><fmt:message key="auditLog.opTime" /></th>
					<td><input type="text" name="opTime" id="opTime" /></td>
  					<th><fmt:message key="auditLog.opDesc" /></th>
					<td><input type="text" name="opDesc" id="opDesc" /></td>
				</tr>
				 <tr>
					<th style="vertical-align:top;"><fmt:message key="auditLog.targetSnapshot" /></th>
					<td colspan="3"><textarea style="width:450px;height:200px;" name="targetSnapshot" id="targetSnapshot"> </textarea></td>
				</tr>
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
