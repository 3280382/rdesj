<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
var grid,toolBar;
$(function(){
	toolBar = $("#toolBar");
	toolBar.addbutton("uploadBtn","<fmt:message key="global.op.upload" />").click(function(){	
		$.dialog("${ctx}/upload/showUpload.do",{action:"${ctx}/upload/upload.do"},true);	
	});
	toolBar.addbutton("searchBtn","<fmt:message key="global.op.search" />").click(function(){	
		var param = $("#searchBar").getParam();
		grid.loadGridData(param);
	});
	toolBar.addbutton("tocsvBtn","<fmt:message key="global.op.download" />CSV").click(function(){	
		var param = $("#searchBar").getParam();
		$.downloadFile(param,"list2csv.do");
	});
	toolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("add.do",{},true);		
	});
	toolBar.addbutton("showBtn","<fmt:message key="global.op.show" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("show.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
	});
	toolBar.addbutton("editBtn","<fmt:message key="global.op.edit" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("edit.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
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
			{name:"id",display:"<fmt:message key="demoData.id" />",width:50},
			{name:"name",display:"<fmt:message key="demoData.name" />",width:120},
			{name:"birthday",display:"<fmt:message key="demoData.birthday" />",width:90},
			{name:"sex",display:"<fmt:message key="demoData.sex" />",width:40,align:"center",formatter:"select",editoptions:{value:"<c:forEach items="${sexList}" var="it">${it.key}:${it.value};</c:forEach>"}},
			{name:"salary",display:"<fmt:message key="demoData.salary" />",width:100,align:"right",formatter:"currency"},
			{name:"email",display:"<fmt:message key="demoData.email" />",width:150},
			{name:"country.name",display:"<fmt:message key="demoData.countryId" />",width:90},
			{name:"province.name",display:"<fmt:message key="demoData.provinceId" />",width:90},
			{name:"createdDate",display:"<fmt:message key="demoData.createdDate" />",width:90}			
		],
		height:"235px",
		sortname:"id",
		sortorder:"desc",
		pager:"#listPager",
		loadComplete:function(data){
			$("#searchBar").resultBinding(data);
		}
	});
	
	$("#demoData\\.countryId").selectPair($("#demoData\\.provinceId"));
	$("#birthdayStart",$("#searchBar")).datepickerView();
	$("#birthdayEnd",$("#searchBar")).datepickerView();
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
</script>
</head>
<body>
<div class="contentDiv">
		 <ul>
		 <li id="toolBar" class="toolBar">
		</li>
		<li id="searchBar" class="searchBar">		
				<fmt:message key="demoData.name" /> <input style="width:100px;" type="text" name="demoData.name" id="demoData.name" />
				<fmt:message key="demoData.email" /> <input style="width:100px;" type="text" name="demoData.email" id="demoData.email" />
				<fmt:message key="demoData.search.birthdayStart" /> <input style="width:80px;" type="text" name="birthdayStart" id="birthdayStart" />
				<fmt:message key="demoData.search.birthdayEnd" /> <input style="width:80px;" type="text" name="birthdayEnd" id="birthdayEnd" />
				<fmt:message key="demoData.sex" /><select name="demoData.sex" id="demoData.sex">
					<option value=""></option>
				<c:forEach items="${sexList}" var="it">
					<option value="${it.key}">${it.value}</option>
				</c:forEach> 
				</select>	
				<fmt:message key="demoData.countryId" /><select name="demoData.countryId" id="demoData.countryId" >
					<option value=""></option>
				<c:forEach items="${demoCountryList}" var="it">
					<option value="${it.id}">${it.name}</option>
				</c:forEach> 
				</select>	
				<fmt:message key="demoData.provinceId" /><select name="demoData.provinceId" id="demoData.provinceId">
					<option value=""></option>
				<c:forEach items="${demoProvinceList}" var="it">
					<option parentId="${it.countryId}" value="${it.id}">${it.name}</option>
				</c:forEach> 
				</select>							
		</li>
		<li class="contentGrid"><table id="listDetail"></table><div id="listPager"></div></li>
		</ul>
</div>
</body>
</html>
