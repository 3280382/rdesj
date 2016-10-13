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
		caption : "<fmt:message key="demoCountry.pageTitle" />",
		url:"search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="demoCountry.id" />",width:50},
			{name:"name",display:"<fmt:message key="demoCountry.name" />",width:250},
			{name:"createdDate",display:"<fmt:message key="demoCountry.createdDate" />",width:90}			
		],
		height:"250px",
		sortname:"id",
		sortorder:"desc",
		pager:"#listPager",
		onSelectRow:function(rowid)	{
			loadDetail(rowid);
		},
		loadComplete:function(data){
			$("#searchBar").resultBinding(data);
		}
	});
}
);
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
		$("#fmDetailDiv").setFormValue(data.demoCountry);
	});	
}
</script>
</head>
<body>
<div class="contentDiv">
		 <ul>
		 <li id="toolBar" class="toolBar">
		</li>
		<li id="searchBar" class="searchBar">		
				<fmt:message key="demoCountry.name" /> <input style="width:100px;" type="text" name="demoCountry.name" id="demoCountry.name" />			
		</li>
		<li class="contentGrid"><table id="listDetail"></table><div id="listPager"></div></li>
		<li class="contentGrid">
		<br/>
		<div id="fmDetailDiv">
		    <table class="editTB">
		    	<tr>
				<th style="width:70px;"><fmt:message key="demoCountry.id" /></th>
				<td style="width:140px;"><input type="text" name="id" id="id" /></td>
				<th style="width:70px;"><fmt:message key="demoCountry.name" /></th>
				</tr>
			</table>
		</div>
		</li>
		</ul>
</div>
</body>
</html>
