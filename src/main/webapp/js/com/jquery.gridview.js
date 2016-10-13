jQuery.fn.extend({
	gridview:function(p){
		if(!p) p = {};
		//if(!p.root) p.root = "pageResult";
		if(!p.pagination) p.pagination = p.root||"pagination";
		
		if(!p.prmNames) {
			p.prmNames  = $.extend({
				page:"pageNumber",//p.pagination+".pageNum",
				rows:"resultsPerPage",//p.pagination+".pageSize",
				sort:"order",//p.pagination+".sort",
				order:"orderBy"//p.pagination+".order"
				},p.prmNames);
		}
		p.jsonReader = $.extend(true,{
				page: p.root?p.root+".page":"page",
				total: p.root?p.root+".totalPageCount":"totalPageCount",
				records: p.root?p.root+".totalCount":"totalCount",
				cell: "",
				id: "",
				repeatitems: false,
				root: p.root?p.root+".results":"results"
			},p.jsonReader);
		p = $.extend(true,{
			hidegrid:false,
			datatype : "local",
			rowNum : 10,
			autowidth : true,
			shrinkToFit : true,
			multiselect : true,
			multiboxonly : true,
			rownumbers : true,
			prmNames : {page:"pageNumber",rows:"resultsPerPage"},
			jsonReader : {repeatitems: false}
		},p);
		if(!p.colNames) {
			p.colNames = [];
		}
		$.each(p.colModel,function(i, m){
			if(typeof(m)=="string"){
				var model = {name:m,sortable:true,resizable:true,align:"left"};
			}else{
				var model = $.extend({sortable:true,resizable:true,align:"left"},m);
			}
			if(!p.colNames[i])p.colNames[i] = m.display || m.name;
			p.colModel[i]=model;
			if(!p.sortname&&model.sortable) p.sortname = model.name;
		});
		//if(!p.viewsortcols) p.viewsortcols = [false,false,false];
		p.autowidth = (p.autowidth!==false);
		p.viewrecords = (p.viewrecords!==false);
		p.count = (p.count!==false);

		var jqGrid = $(this).jqGrid(p);
		if($.browser.msie) $(p.pager).find("input:text").height(18);
		if(!p.count) $(p.pager).find(".ui-icon-seek-end").remove();
		return jqGrid;
	},
	showGridData:function(data){
		$(this).clearGridData();
		if(typeof(data)=="undefined"||!data) return;
		for(var i=0;i<=data.length;i++) {
			$(this).addRowData(i+1,data[i]); 
		}
	},
	showEmptyGridData:function(len){
		$(this).clearGridData();
		if(!len)len=10;
		var ts = $(this).get(0);
		ts.p.page = 1;
		ts.p.lastpage= 1;
		ts.p.records= len;

		for(var i=1;i<=len;i++) {
			$(this).addRowData(i+1,{}); 
		}
	},
	loadGridData:function(param){
		param = param||{};
		var prmNames = $(this).getGridParam("prmNames");
		var rows = prmNames.rows;
		var count = rows.substring(0,rows.indexOf("."))+".count";
		param[count] = $(this).getGridParam("count");
		$(this).get(0).p.postData = param;
		$(this).setGridParam({page:1,datatype:"json"});
		$(this).clearGridData();
		$(this).trigger("reloadGrid");
		//delete param[count];
		$(this).attr("lastsel",false);
		$(this).setGridParam("postData",param);
	},
	loadGridDataNoPage:function(param){
		param = param||{};
		var prmNames = $(this).getGridParam("prmNames");
		var rows = prmNames.rows;
		var count = rows.substring(0,rows.indexOf("."))+".count";
		param[count] = $(this).getGridParam("count");
		$(this).get(0).p.postData = param;
		$(this).setGridParam({page:1,datatype:"json"});
		$(this).clearGridData();
		$(this).trigger("reloadGrid");
		delete param[count];
		$(this).setGridParam("postData",param);
	}
});