jQuery.fn.extend({
	addbutton:function(name,displayName,able){
		var $this = $(this);
		$this.append("<button name='"+name+"'></button>");
		return $this.button(name,displayName,able);
	},
	button:function(name,displayName,able){
		if(arguments.length==0){
			var $this = $(this);
			var $buttons = $(this).find("button").add("a.btnBg").add("a.btnBg_gray");
			return {
				click:function(onclick){
					$buttons.each(function(){
						$this.button(this.name).click(onclick);
					});
				},
				enable:function(){
					$buttons.each(function(){
						$this.button(this.name).enable();
					});
				},
				disable:function(){
					$buttons.each(function(){
						$this.button(this.name).disable();
					});
				},
				ajax:function(ajaxOps){
					$buttons.each(function(){
						$this.button(this.name).ajax(ajaxOps);
					});
				}
			};
		}
		able = !(able===false);
		var button = null;
		var $button = $(this).find("[name="+name+"]");
		$button.each(function(){
			if(this.button)	button = this.button;
		});
		if(button){
			if(able) button.enable();
			else button.disable();
			return button;
		}
		button = {
			name:name,
			displayName:displayName,
			able:able,
			click:function(onclick){
				if(!onclick&&$.isFunction(this.onclick)) this.onclick();
				else if($.isFunction(onclick)) this.onclick = onclick;
			},
			enable:function(){
				this.element.removeClass("btnBg_gray").addClass("btnBg").css("cursor","pointer");
				this.able = true;
			},
			disable:function(){
				this.element.addClass("btnBg_gray").removeClass("btnBg").removeClass("btnBg_hover").css("cursor","default");
				this.able = false;
			},
            rename: function(name,displayName) {
                this.element.attr("name", name).find("span.btnF").text(displayName);
            },
			ajax:function(ajaxOps){
				var complete = ajaxOps.complete;
				var error = ajaxOps.error;
				ajaxOps.complete = complete?
					function(){
						complete();
						button.enable();
					}:function(){
						button.enable();
					}
				ajaxOps.error = error?
					function(){
						error();
						button.enable();
					}:function(){
						button.enable();
					}
				ajaxOps.type = ajaxOps.type?ajaxOps.type:"POST";
 				ajaxOps.cache = !(ajaxOps.cache===false);
				this.ajaxOps = ajaxOps;
			}
		};
		if($button.find("span").length==0){
			$button.replaceWith('<a name="'+name+'" class="btnBg"><b class="btnL"/><span class="btnF">'+displayName+'</span></a>');
			$button = $(this).find("[name="+name+"]");	
		}
		button.element = $button;
		if(!able) button.disable();
		$button.click(function(){
			if(!button.able) return;
			button.disable();
			button.click();
			if(typeof(button.ajaxOps)=="object"){
				var ajaxOps = button.ajaxOps
				var validate = ajaxOps.validate;
				var param = ajaxOps.param;
				if(validate){
					if(($.isFunction(validate)&&!validate())
						||(!$.isFunction(validate)&&!$(validate).valid())) {
						button.enable();
						return;
					}	
				}
				if(param){
					ajaxOps.data = $.isFunction(param)?param():$(param).getParam();
				}
				$.ajax(ajaxOps);
			}else{
				button.enable();
			}
		});
		$button.mouseover(function(){
			if(!button.able) return;
			$(this).addClass("btnBg_hover");
		});
		$button.mouseout(function(){
			$(this).removeClass("btnBg_hover");
		});
		$button.each(function(){
			this.button = button;
		});
		return button;
	}
});		