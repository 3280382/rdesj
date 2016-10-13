<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.cicl.frame.security.sysuser.dictionary.SysUserConstant"%>
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
	
	<sec:authorize ifAllGranted="<%=SysUserConstant.OP_REMOVE%>">
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
		caption : "<fmt:message key="sysUser.pageTitle" />",
		url:"search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="sysUser.id" />",width:50,hidden:true},
			{name:"username",display:"<fmt:message key="sysUser.username" />",width:170}		
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
		//var children = data.sysUser.roles || [];
		var children = data.roles || [];	
		checkRole(children);
			
		$("#fmDetailDiv").setFormValue(data.sysUser);		
	});		
}
</script>
<script>
var detail,detailToolBar;
$(function(){
	detail = $("#fmDetailDiv");
	detailToolBar = $("#detailToolBar");
	
	<sec:authorize ifAllGranted="<%=SysUserConstant.OP_EDIT%>">
	detailToolBar.addbutton("saveBtn","<fmt:message key="global.op.save" />").click(function(){	
		$.getJSON("update.json", detail.getParam(), function(data){			
			if( !detail.resultBinding(data, {position:"right"}).hasError)
			{
				grid.trigger("reloadGrid");
			}  
		});
	});	
	</sec:authorize>
	
	<sec:authorize ifAllGranted="<%=SysUserConstant.OP_ADD%>">
	detailToolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("add.do",{},true);		
	});
	</sec:authorize>

	<sec:authorize ifAllGranted="<%=SysUserConstant.OP_RESETPWD%>">
	detailToolBar.addbutton("resetBtn","<fmt:message key="sysUser.op.change_password_admin" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("editPasswordAdmin.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);		
	});
	</sec:authorize>

	$("#organizationId",$("#fmDetailDiv")).treeSelect({});
});	
</script>
<script>
var gridRole,toolBarRole;
$(function(){
	toolBarRole = $("#toolBarRole");
	
	<sec:authorize ifAllGranted="<%=SysUserConstant.OP_ADD%>">
	toolBarRole.addbutton("saveBtn","<fmt:message key="global.op.save" />").click(function(){	
		var selarrrow = gridRole.attr("p").selarrrow;
		var ids = [];
		for( var i=0; i<selarrrow.length; i++)
		{
			ids.push(gridRole.getRowData(selarrrow[i]).id);
		}
		$.getJSON("updateRoles.json", {id:grid.getRowData(grid.attr("p").selrow).id,childrenIds:ids.join(",")}, function(data){			
			if( !detail.resultBinding(data, {position:"right"}).hasError)
			{
				grid.trigger("reloadGrid");
			}  
		});
	});	
	</sec:authorize>
	
	gridRole = $("#listRole");
	gridRole.gridview({
		caption : "<fmt:message key="role.pageTitle" />",
		url:"${ctx}/security/role/search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="role.id" />",width:50,hidden:true},
			{name:"name",display:"<fmt:message key="role.name" />",width:150},
			{name:"description",display:"<fmt:message key="role.description" />",width:250},
			{name:"createdDate",display:"<fmt:message key="role.createdDate" />",width:150}			
		],
		height:"305px",
		rownumbers:false,
		rowNum:-1,
		sortname:"id",
		sortorder:"desc",
		pager:"#listRolePager"
	});

	gridRole.loadGridData({});  
}
);
function checkRole(children){
	gridRole.resetSelection();
	
	var idsMap = {};
	for(var i=0; i<children.length; i++)
	{
		idsMap[children[i].id] = true;
	}

	var ids = gridRole.getDataIDs();
	for( var i=0; i<ids.length; i++)
	{
		if( idsMap[gridRole.getRowData(ids[i]).id] )
		{
			gridRole.setSelection(ids[i],true);
		}
	}
}
</script>
</head>
<body>
<table class="contain">
	<tr>
		<td style="width: 190px;" class="leftBar">
		<ul>
			<li id="searchBar" class="searchBar">
			<table>				
				<tr>
					<th><fmt:message key="sysUser.username" /></th>
					<td><input style="width: 150px;" type="text" name="sysUser.username" id="sysUser.username" /></td>
				</tr>
			</table>
			</li>
			<li id="toolBar" class="toolBar"></li>
			<li>
			<table id="listDetail"></table><div id="listPager"></div>
			</li>
		</ul>
		</td>
		<td><a href="#" id="spliter"></a></td>
		<td style="height:98%;">
		<div id="tabsDetail" class="tabDiv">
			<div id="fmDetailDiv" name="fmDetailDiv" value="<fmt:message key="sysUser.pageTitle" />" class="content">
			<ul>
				<li id="detailToolBar" class="toolBar"></li>
				<li>
				<table class="editTB" class="content">
					<tr>
						<th style="width: 90px;"><fmt:message key="sysUser.id" /></th>
						<td style="width: 200px;"><input disabled type="text" name="id" id="id" /></td>
						<th style="width: 90px;">&nbsp;</th>
						<td style="width: 200px;">&nbsp;</td>
					</tr>
					 
					<tr>
						<th><span class="fRed">*</span><fmt:message key="sysUser.username" /></th>
						<td><input type="text" name="username" id="username" /></td>
	  					<th><span class="fRed">*</span><fmt:message key="sysUser.loginName" /></th>
						<td><input type="text" name="loginName" id="loginName" /></td>
					</tr>
					  
					<tr>
	  					<th><fmt:message key="sysUser.email" /></th>
						<td><input type="text" name="email" id="email" /></td>
						<th>&nbsp;</th>
						<td><input type="hidden" name="password" id="password" /></td>
					</tr>
					  
					<tr>
						<th><fmt:message key="sysUser.status" /></th>
						<td>
							<select name="status" id="status">
								<option value=""></option>
							<c:forEach items="${sysUserStatusList}" var="it">
								<option value="${it.key}">${it.value}</option>
							</c:forEach> 
							</select>
						</td>
	  					<th><fmt:message key="sysUser.accountNonExpired" /></th>
						<td>
						<input type="radio" name="accountNonExpired" id="accountNonExpired" value=false />yse
						<input type="radio" name="accountNonExpired" id="accountNonExpired" value=true />no
						</td>
					</tr>
					  
					<tr>
						<th><fmt:message key="sysUser.credentialsNonExpired" /></th>
						<td>
						<input type="radio" name="credentialsNonExpired" id="credentialsNonExpired" value=false />yse
						<input type="radio" name="credentialsNonExpired" id="credentialsNonExpired" value=true />no
						</td>
	  					<th><fmt:message key="sysUser.enabled" /></th>
						<td>
						<input type="radio" name="enabled" id="enabled" value=false />yse
						<input type="radio" name="enabled" id="enabled" value=true />no
						</td>
					</tr>
					  
					<tr>
						<th><fmt:message key="sysUser.accountNonLocked" /></th>
						<td class="editTBCkb">
						<input type="radio" name="accountNonLocked" id="accountNonLocked" value=false />yse
						<input type="radio" name="accountNonLocked" id="accountNonLocked" value=true />no
						</td>
	  					<th><fmt:message key="sysUser.organizationId" /></th>
						<td>
							<select name="organizationId" id="organizationId" >
								<option value=""></option>
							<c:forEach items="${organizationList}" var="it">
								<option parentId="${it.parentId}" value="${it.id}" >${it.name}</option>
							</c:forEach> 
							</select>
						</td>
					</tr>
					  
					<tr>
						<th><span class="fRed">*</span><fmt:message key="sysUser.code" /></th>
						<td><input type="text" name="code" id="code" /></td>
	  					<th><span class="fRed">*</span><fmt:message key="sysUser.mobile" /></th>
						<td><input type="text" name="mobile" id="mobile" /></td>
					</tr>
					  
					<tr>
						<th><fmt:message key="sysUser.phone" /></th>
						<td><input type="text" name="phone" id="phone" /></td>
	  					<th><fmt:message key="sysUser.address" /></th>
						<td><input type="text" name="address" id="address" /></td>
					</tr>					
					  
					<tr>
						<th><fmt:message key="sysUser.isLogin" /></th>
						<td>
						<input type="radio" name="isLogin" id="isLogin" value=false />yse
						<input type="radio" name="isLogin" id="isLogin" value=true />no
						</td>
	  					<th><fmt:message key="sysUser.tryTimes" /></th>
						<td><input type="text" name="tryTimes" id="tryTimes" /></td>
					</tr>
					  
					<tr>
						<th><fmt:message key="sysUser.failLoginTimes" /></th>
						<td><input type="text" name="failLoginTimes" id="failLoginTimes" /></td>
	 					<th>&nbsp;</th>
						<td><input type="hidden" name="recentPassword" id="recentPassword" /></td>
					</tr>
					
					<tr>
						<th style="vertical-align:top;"><fmt:message key="sysUser.description" /></th>
						<td colspan="3"><textarea style="width:450px;height:100px;" name="description" id="description"> </textarea></td>
					</tr>
				</table>
				</li>
			</ul>
			</div>
			
			<div id="fmDetailRoleDiv" name="fmDetailRoleDiv" value="<fmt:message key="role.pageTitle" />" class="content">
			<ul>
				<li id="toolBarRole" class="toolBar"></li>
				<li>
					<table  id="listRole"></table><div id="listRolePager"></div>
				</li>
			</ul>
			</div>
		</div>
		</td>
	</tr>
</table>
</body>
</html>
