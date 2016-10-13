<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
var grid,toolBar, provinceGrid, provinceToolBar;
$(function(){
	toolBar = $("#toolBar");
	toolBar.addbutton("searchBtn","<fmt:message key="global.op.search" />").click(function(){	
		var param = $("#searchBar").getParam();
		grid.loadGridData(param);
	});
	toolBar.addbutton("addBtn","<fmt:message key="global.op.add" />").click(function(){	
		$.dialog("${ctx}/demo/democountry/add.do",{},true);		
	});
	toolBar.addbutton("showBtn","<fmt:message key="global.op.show" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("${ctx}/demo/democountry/show.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
	});
	toolBar.addbutton("editBtn","<fmt:message key="global.op.edit" />").click(function(){	
		if(!confirmSelect())return;
		$.dialog("${ctx}/demo/democountry/edit.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
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
		
		$.getJSON("${ctx}/demo/democountry/remove.json", {ids:ids.join(",")}, function(data){			
			if( !$("#fmForm").resultBinding(data).hasError )
			{
				grid.trigger("reloadGrid");
			}  
		});
	});	

	grid = $("#listDetail");
	grid.gridview({
		caption : "<fmt:message key="demoCountry.pageTitle" />",
		url:"${ctx}/demo/democountry/search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="demoCountry.id" />",width:50},
			{name:"name",display:"<fmt:message key="demoCountry.name" />",width:160}	
		],
		height:"320px",
		sortname:"id",
		sortorder:"desc",
		pager:"#listPager",
		onSelectRow:function(rowid)	{
			loadProvince(rowid);
		},
		loadComplete:function(data){
			$("#searchBar").resultBinding(data);
		}
	});

	provinceToolBar = $("#provinceToolBar");
	provinceToolBar.addbutton("psearchBtn","<fmt:message key="global.op.search" />").click(function(){	
		var param = $("#provinceSearchBar").getParam();
		provinceGrid.loadGridData(param);
	});
	provinceToolBar.addbutton("paddBtn","<fmt:message key="global.op.add" />").click(function(){				
		//if(!confirmSelect())return;		
		$.dialog("${ctx}/demo/demoprovince/add.do",{countryId:grid.getRowData(grid.attr("p").selrow).id},true);	
	});
	provinceToolBar.addbutton("pshowBtn","<fmt:message key="global.op.show" />").click(function(){	
		if(!provinceConfirmSelect())return;
		$.dialog("${ctx}/demo/demoprovince/show.do",{id:provinceGrid.getRowData(provinceGrid.attr("p").selrow).id},true);
	});
	provinceToolBar.addbutton("peditBtn","<fmt:message key="global.op.edit" />").click(function(){	
		if(!provinceConfirmSelect())return;
		$.dialog("${ctx}/demo/demoprovince/edit.do",{id:provinceGrid.getRowData(provinceGrid.attr("p").selrow).id},true);
	});
	provinceToolBar.addbutton("premoveBtn","<fmt:message key="global.op.remove" />").click(function(){	
		if(!provinceConfirmSelect())return;
		if(!confirm("<fmt:message key="global.op.tip.confirm_remove" />"))return;

		var selarrrow = provinceGrid.attr("p").selarrrow;
		var ids = [];
		for( var i=0; i<selarrrow.length; i++)
		{
			ids.push(provinceGrid.getRowData(selarrrow[i]).id);
		}
		
		$.getJSON("${ctx}/demo/demoprovince/remove.json", {ids:ids.join(",")}, function(data){			
			if( !$("#fmForm").resultBinding(data).hasError )
			{
				provinceGrid.trigger("reloadGrid");
			}  
		});
	});	

	provinceGrid = $("#provinceListDetail");
	provinceGrid.gridview({
		caption : "<fmt:message key="demoProvince.pageTitle" />",
		url:"${ctx}/demo/demoprovince/search.json",
		colModel:[
			{name:"id",display:"<fmt:message key="demoProvince.id" />",width:50},
			{name:"name",display:"<fmt:message key="demoProvince.name" />",width:160}		
		],
		height:"320px",
		sortname:"id",
		sortorder:"desc",
		pager:"#provinceListPager",
		loadComplete:function(data){
			$("#provinceSearchBar").resultBinding(data);
		}
	});
}
);

function loadProvince()
{
	var id = grid.getRowData(grid.attr("p").selrow).id
	provinceGrid.loadGridData({"demoProvince.countryId":id});
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
function provinceConfirmSelect()
{
	if(!provinceGrid.getRowData(provinceGrid.attr("p").selrow).id)
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
<table>
	<tr>
		<td style="width:400px;"><div id="toolBar" class="toolBar"></div></td>
		<td style="width:400px;"><div id="provinceToolBar" class="toolBar"></div></td>
	</tr>
	<tr>
		<td> 
			<div id="searchBar" class="searchBar">
			<fmt:message key="demoCountry.name" /> <input style="width:100px;" type="text" name="demoCountry.name" id="demoCountry.name" />
			</div>
		</td>
		<td>
			<div id="provinceSearchBar" class="searchBar">
			<fmt:message key="demoProvince.name" /> <input style="width:100px;" type="text" name="demoProvince.name" id="demoProvince.name" />
			</div>
		</td>
	</tr>	
	<tr>
		<td> 
			<div class="contentGrid">
				<table id="listDetail"></table><div id="listPager"></div>
			</div>
		</td>
		<td>
			<div class="contentGrid">
				<table id="provinceListDetail"></table><div id="provinceListPager"></div>
			</div>
		</td>
	</tr>
</table>
</div>
</body>
</html>
