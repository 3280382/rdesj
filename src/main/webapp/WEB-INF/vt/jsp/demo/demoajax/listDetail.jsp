<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
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
		caption : "<fmt:message key="demoData.pageTitle" />",
		url:"search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="demoData.id" />",width:50,hidden:true},
			{name:"name",display:"<fmt:message key="demoData.name" />",width:90},
			{name:"createdDate",display:"<fmt:message key="demoData.createdDate" />",width:80}			
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

	$("#update_countryId").selectPair($("#update_provinceId"));

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
		$("#fmDetailDiv").setFormValue(data.demoData);
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

	$("#birthday",detail).datepickerView();	
});
</script>
</head>
<body>
<table class="contain"">
	<tr>
		<td style="width: 190px;" class="leftBar">
			<ul>
				<li id="searchBar" class="searchBar">
					<table>
						<tr>
							<th><fmt:message key="demoData.name" /></th>
							<td><input style="width: 150px;" type="text" name="demoData.name" id="demoData.name" /></td>
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
		<td  style="height:98%;">
			<div id="tabsDetail" class="tabDiv" style="height:100%;width:100%;" >
				<div id="fmDetailDiv" name="fmDetailDiv" value="<fmt:message key="demoData.pageTitle" />" class="content">
				<ul >
					<li id="detailToolBar" class="toolBar"></li>
					<li>
					<table class="editTB">
						<tr>
							<th style="width: 70px;"><fmt:message key="demoData.id" /></th>
							<td style="width: 140px;"><input disabled type="text" name="id" id="id" /></td>
							<th style="width: 70px;"><span class="fRed">*</span><fmt:message key="demoData.name" /></th>
							<td style="width: 140px;"><input type="text" name="name" id="name" /></td>
						</tr>
						<tr>
							<th><span class="fRed">*</span><fmt:message key="demoData.sex" /></th>
							<td><select name="sex" id="sex">
								<option value=""></option>
								<c:forEach items="${sexList}" var="it">
									<option value="${it.key}">${it.value}</option>
								</c:forEach>
							</select></td>
							<th><fmt:message key="demoData.salary" /></th>
							<td><input type="text" name="salary" " id="salary" /></td>
						</tr>
						<tr>
							<th><fmt:message key="demoData.birthday" /></th>
							<td><input type="text" name="birthday" id="birthday" /></td>
							<th><fmt:message key="demoData.email" /></th>
							<td><input type="text" name="email" id="email" /></td>
						</tr>
						<tr>
							<th><span class="fRed">*</span><fmt:message key="demoData.countryId" /></th>
							<td><select name="countryId" id="update_countryId">
								<option value=""></option>
								<c:forEach items="${demoCountryList}" var="it">
									<option value="${it.id}">${it.name}</option>
								</c:forEach>
							</select></td>
							<th><span class="fRed">*</span><fmt:message key="demoData.provinceId" /></th>
							<td><select name="provinceId" id="update_provinceId">
								<option value=""></option>
								<c:forEach items="${demoProvinceList}" var="it">
									<option parentId="${it.countryId}" value="${it.id}">${it.name}</option>
								</c:forEach>
							</select></td>
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
