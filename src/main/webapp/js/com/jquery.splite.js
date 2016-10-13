jQuery.fn.extend({
	splite:function(open){
		if(open===false){
			$(this).html('<b class="icoCloseL"></b>');
		}else{
			$(this).html('<b class="icoOpenL"></b>');
		}
		$(this).parent().width("12");
		$(this).click(
			function () { 
				if($(this).find("b").hasClass("icoCloseL")){
					$(this).find("b").addClass("icoOpenL"); 
				    $(this).find("b").removeClass("icoCloseL"); 
				}else{
					$(this).find("b").removeClass("icoOpenL"); 
				    $(this).find("b").addClass("icoCloseL"); 
				}				
				$(this).parent().prev().toggle();
			}
		);
	}
});

jQuery.fn.extend({
	selectPair:function( childSel, p){
		p = $.extend({parentId:"parentId"}, p);
		
		parentSel = this;
		var parentChange = function()
		{
			var selectedValue = $(this).val();
		
			childSel.children("span").each(function()
			{
				$(this).children().clone().replaceAll($(this));
			});
		
			if(selectedValue != "")
			{
				childSel.children("option["+p.parentId+"!='" + selectedValue + "'][value!='']").each(function()
				{
					$(this).wrap("<span style='display:none;'></span>"); 
				});
			}
		};
		var childChange = function()
		{
			parentSel.val($(this).children("option:selected").attr("parentid"));
		};
			parentSel.change(parentChange);
			childSel.change(childChange);
	}
});