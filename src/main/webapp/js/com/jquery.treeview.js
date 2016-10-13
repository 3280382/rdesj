jQuery.fn.extend({
	treeview:function(p){
		var setting = {
			async: true,
			asyncUrl: p.url||"./loadTree.json",
			asyncParam: p.param||[],
			isSimpleData: true,
			treeNodeKey: "id",
			treeNodeParentKey: "parentId",
			nameCol: "name",
			expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast",
			rootID:"tree",
			root:{ 
				isRoot:true,
				nodes:[]
			}
		};
		$.extend(setting, p);

		return $(this).zTree(setting);
	},
	reloadTree:function(refreshType) {
		var treeNode = $(this).getSelectedNode();
		if (!treeNode) {
			alert("Please choose one node");
			return;
		}
		//refresh add
		$(this).reAsyncChildNodes( treeNode, refreshType || "refresh" );
	}
});

jQuery.fn.extend({
	treeSelect:function(p){
		p = $.extend({parentKey:"parentId",key:"value",nodesCol:"nodesCol"}, p
		);
		
		var keyMap = {};
		var treeMap = [];
		var treeSel = this;
		
	    function selectTagToTree() {		
			$("option",treeSel).each(function(i,option){
				option = $(option);	
				keyMap[option.attr(p.key)] = option;		
			});
			
			$.each(keyMap, function(i,option){
				if (keyMap[option.attr(p.parentKey)]) {				
					if (!keyMap[option.attr(p.parentKey)][p.nodesCol])
						keyMap[option.attr(p.parentKey)][p.nodesCol] = [];

					keyMap[option.attr(p.parentKey)][p.nodesCol].push(option);
				} else {
					treeMap.push(option);
				}
			});
		}
	
		function showSelectTree(options)
		{
			$(options).each(function(i, option){
				if (keyMap[option.attr(p.parentKey)]) {				
					option.attr("treeText",keyMap[option.attr(p.parentKey)].attr("treeText")+"─");
					//option.attr("treeText",keyMap[option.attr(p.parentKey)].attr("treeText").replace("├","-")+"├");
					option.attr("text", option.attr("treeText")+option.attr("text"));
				} else {
					option.attr("treeText","├");
					if(option.attr("text")!="")
						option.attr("text", option.attr("treeText")+option.attr("text"));
				}
				
				treeSel.append(option);
				//alert(node.toSource());
				showSelectTree(option["nodesCol"]);
			});
		}	
	    
	    selectTagToTree();

		var selValue = treeSel.val();
		showSelectTree(treeMap);
		treeSel.val(selValue);
		
		return treeSel;
	}
});
