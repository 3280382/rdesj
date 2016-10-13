jQuery.fn.extend({
	dialog:function(p){
		p = $.extend({},{
			title:"",
			buttons:{},
			open:true,
			width:400,
			height:null,
			overlay:true,
			closeBtn:true,
			draggable:true,
			resizable:false,
			removeOnClose:true
		},p);
		this.parent = p.parent;
		var $this = $(this);
		var id = $(this).attr("id")?$(this).attr("id"):"Dialog"+new Date().getTime();
		var content = $(this).html();
		$(this).remove();
		$(document.body).append(
			'<div id='+id+' class="alertW awokeTips"><div class="alertWT" ><div class="alertW_L"></div><div class="alertW_M"><div class="alertW_MCT"><span>'+p.title+'</span></div></div>'
				+'<div class="alertW_R"></div></div><div class="alertWC"><div class="alertWCT"><div class="alertW_MCB"><div name="content" style="text-align:center;"><table style="margin:auto;height:100%;"><tr><td style="vertical-align:top;">'+content
				+'</td></tr></table></div><div class="alertBtnDiv" style="TEXT-ALIGN: center;"><table style="margin:0 auto;"><tr><td></td></tr></table></div></div></div></div></div>');
		var d =  $(document.body).find("#"+id);
		$(d).width(p.width);
		$(d).find(".alertW_M").width(p.width-10);
		$(d).find(".alertW_MCB").css({"width":p.width-14,"height":p.height});
		$(d).find("div[name='content']").css({"height":p.height-40,"overflow":"auto"});
		
		if(p.closeBtn){
			$(d).find(".alertW_MCT").append('<b class="aW_Btn" onmouseover="this.className = \'aW_BtnOver\'"  onmouseout="this.className = \'aW_Btn\'" title="close"></b>');
			$(d).find(".aW_Btn").click(function(){
				$(d).each(function(){this.close()});
			});
		}
		for(var i in p.buttons){
			$(d).find(".alertBtnDiv:last").find("td").append("<button name="+i+"></button>");
			if($.isFunction(p.buttons[i])){
				$(d).button(i,i).click(p.buttons[i])	
			}else{
				$(d).button(i,i).ajax(p.buttons[i])
			}
		}
		if($(d).find(".alertBtnDiv:last").find("a").length==0){
			$(d).find(".alertBtnDiv:last").remove();
		}
		var page = 	document.body;
		var zIndex = 1000 + $("div.dialog-overlay").length;
		$(d).css({"z-index":zIndex+1,"position":"absolute","top":(page.clientHeight-$(d).height())/2,"left":(page.clientWidth-$(d).width())/2});		
		$('<div id="'+id+'-dialog-overlay" class="dialog-overlay" style="position:absolute;top:0;left:0;z-index:'+zIndex+';"/>').appendTo(document.body).css({"width":"100%","height":"100%"}).show().bgiframe();
		if(p.draggable){
			$(d).find(".alertWT").mouseover(function(){
				$(this).css("cursor","move");
			}).mouseout(function(){
				$(this).css("cursor","default");
			});
			$(d).draggable({handle:".alertWT"});
		}
		if(p.resizable){
			$(d).resizable({alsoResize:'#'+id+' .alertW_MCB'});
			$(d).resize(function(){
				$(d).find(".alertW_M").width($(d).width()-10);
			});
		}
		return $(d).each(function(){
			this.close = function(){
				$("#"+id+"-dialog-overlay").remove();
				if(p.removeOnClose){
					$(d).remove();
				}else{					
					$(d).hide();
				}
			}
			this.open = function(){
				if(p.overlay){
					$(document).find(".dialog-overlay").show();
				} 
				$(this).show();
			}
			this.scrolltop = function(top){
				$(this).find("div[name=content]").scrollTop(top);
			}
		})
	},
	dialogClose:function(){
		$(this).each(function(){this.close()})
	},
	dialogOpen:function(){
		$(this).each(function(){this.open()})
	}
});	
jQuery.extend({
	dialog:function(url, param, isCurDocument){
		$.post(url,param,function(data){
            if(isCurDocument)
            {
            	$(document.body).append(data);
            }
            else
            {
				with(top){
					$(document.body).append(data);
				}
            }
		});
	}
});