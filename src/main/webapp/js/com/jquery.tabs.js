jQuery.fn.extend({
	tabs:function(curtab,isonly,tabs){
		var $this = $(this);
		tabs = tabs?tabs:{};
		$.each(tabs,function(i, tab){
			tab = $.extend({
					content : null,
					onclick : null,
					onclickable : true
				},tab);
			tabs[i] = tab;
		});
		if($(this).find(".current").length==0 && !curtab) 
		{
			$(this).children(".content:first").addClass("current");
			curtab = 0;
		}
		var tabTable = $('<table class="tabTable"><tr><td class="tabTab"></td></tr><tr><td class="tabContent"></td></tr></table>').insertAfter($(this));
		var tabHeaders = $('<div class="tabTabDiv"></div>').appendTo($(tabTable).find(".tabTab"));
		var tabContents = $(tabTable).find(".tabContent");
		$.each($this.children(".content"),function(i, content){
			if(isonly && curtab!=i) return;
			var name = $(content).attr("name");
			var value = $(content).attr("value")||name;
			$(content).hide().appendTo(tabContents);
			var tabHeader = $('<a class="tabBg"><b class="tabL"></b><span class="btnF">'+value+'</span></a>')
				.appendTo(tabHeaders).click(function(){
					if(tabs[name]&&tabs[name].onclickable&&$.isFunction(tabs[name].onclick)) tabs[name].onclick();
					tabHeaders.find(".tabBg_checked").removeClass("tabBg_checked").addClass("tabBg");
					$(this).removeClass("tabBg").addClass("tabBg_checked").removeClass("tabBg_hover");
					tabContents.children(".content").hide();
					
					if($(content).attr("isrc") && $(content).attr("isrc")!="hasload")
					{
						$('<iframe src="'+$(content).attr("isrc")+'"  marginwidth=0 marginheight=0 frameborder="0" width="100%" height="100%" scrolling="auto" ></iframe>').appendTo($(content));
						$(content).attr("isrc","hasload");
					}
					$(content).show();
				});
			if($(content).hasClass("current") || curtab==i ){	
				if($(content).attr("isrc") && $(content).attr("isrc")!="hasload")
				{
					$('<iframe src="'+$(content).attr("isrc")+'"  marginwidth=0 marginheight=0 frameborder="0" width="100%" height="100%"  scrolling="auto" ></iframe>').appendTo($(content));
					$(content).attr("isrc","hasload");
				}
				$(content).show().removeClass("current");
				$(tabHeader).removeClass("tabBg").addClass("tabBg_checked");
			}
			$(tabHeader).mouseover(function(){
				if($(this).hasClass("tabBg_checked")) return;
				$(this).addClass("tabBg_hover");
			}).mouseout(function(){
				$(this).removeClass("tabBg_hover");
			});
			tabs[name] = tabs[name]?tabs[name]:{};
			tabs[name].select = function(){tabHeader.click()};
			tabs[name].content = $(content);
			tabs[name].isCurrent = function(){
				return $(tabHeader).hasClass("tabBg_checked");
			}
		})
		$this.remove();
		return tabs;
	}
});		