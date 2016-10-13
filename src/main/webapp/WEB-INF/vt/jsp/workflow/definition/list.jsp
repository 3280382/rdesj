<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
var grid,toolBar;
$(function(){
	toolBar = $("#toolBar");
	toolBar.addbutton("importBtn","Import").click(function(){	
		$.dialog("${ctx}/upload/showUpload.do",{action:"${ctx}/workflow/definition/upload.do"},true);	
	});
	
	toolBar.addbutton("startBtn","New Instance").click(function(){	
		if(!confirmSelect())return;
	
		var selarrrow = grid.attr("p").selarrrow;
		var ids = [];
		for( var i=0; i<selarrrow.length; i++){
			ids.push(grid.getRowData(selarrrow[i]).id);
		}

		// ajax call
		$.getJSON("startup.json", {ids:ids.join(",")}, function(data){			
			if( !$("#fmForm").resultBinding(data).hasError ){
				grid.trigger("reloadGrid");
			}  
		});		
	});

	toolBar.addbutton("showXMLBtn","Show Definition").click(function(){	
		if(!confirmSelect())return;
		$.dialog("show.do",{id:grid.getRowData(grid.attr("p").selrow).id},true);
	});

	toolBar.addbutton("designBtn","Design").click(function(){
		var id = grid.getRowData(grid.attr("p").selrow).id;
		if(id){
			id = id;
		}else{
			id = 0;
		}
		location.href = "design.do?id=" + id;
	});
	
	toolBar.addbutton("removeBtn","<fmt:message key="global.op.remove" />").click(function(){	
		if(!confirmSelect())return;
		if(!confirm("<fmt:message key="global.op.tip.confirm_remove" />"))return;

		var selarrrow = grid.attr("p").selarrrow;
		var ids = [];
		for( var i=0; i<selarrrow.length; i++){
			ids.push(grid.getRowData(selarrrow[i]).id);
		}
		
		$.getJSON("remove.json", {ids:ids.join(",")}, function(data){			
			if( !$("#fmForm").resultBinding(data).hasError ){
				grid.trigger("reloadGrid");
			}  
		});
	});	

	grid = $("#listDetail");
	grid.gridview({
		caption : "Workflow Definition",
		url:"search.json",
		colModel:[
			{name:"id",display:"", width:20},
			{name:"name",display:"Name",width:300},
			{name:"version",display:"Version",width:100},
			{name:"nodeNumber",display:"Nodes",width:100},
			{name:"instances",display:"Instances",width:100},
			{name:"createdDate",display:"Created Date",width:150}			
		],
		height:"250px",
		sortname:"name",
		sortorder:"asc",
		pager:"#listPager"
	});

    // show data on load
	grid.loadGridData({});
}
);

function confirmSelect(){
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
		 	<li id="toolBar" class="toolBar"></li>
			<li class="contentGrid">
				<table id="listDetail"></table><div id="listPager"></div>
			</li>
		</ul>
</div>
</body>
</html>
