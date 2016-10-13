jQuery.fn.extend({
	listPair:function(p){
		if(typeof(p)=="string"){
			if(p=="update"){
				if(arguments.length == 1) return;
				var json = arguments[1];
				$(this).each(function(){
					this.update(json);
				});
			}
			if(p=="param"){
				var param;
				$(this).each(function(){
					param = this.param();
				});
				return param;
			}
			if(p=="paramList"){
				var param;
				$(this).each(function(){
					param = this.paramList();
				});
				return param;
			}
			if(p=="enable"){
				$(this).button().enable();
				$(this).find("select").removeAttr("disabled");
			}
			if(p=="disable"){
				$(this).button().disable();
				$(this).find("select").attr("disabled", true);
			}
			return;
		}
		p = $.extend(true,{
			json:null,
			jsonReader:{
				val:"",
				text:""
			},
			left:{
				name:"",
				prmName:"",
				title:"",
				legend:"",
				callback:null
			},
			right:{
				name:"",
				prmName:"",
				title:"",
				legend:"",
				callback:null
			}
		},p);
		if(!p.left.prmName&&p.left.name) p.left.prmName = p.left.name;
		if(!p.right.prmName&&p.right.name) p.right.prmName = p.right.name;
		var fieldsetH = $.browser.msie?270:260;
		var html = [];
		html.push('<div class="fL">');
		html.push('	<fieldset style="height:'+fieldsetH+'px">');  
		html.push('		<legend>'+p.left.legend+'</legend>'); 
		html.push('		<select multiple="multiple" id="'+p.left.prmName+'" name="'+p.left.prmName+'" title="'+p.left.title+'"></select>'); 
		html.push('	</fieldset>');  
		html.push('</div>');
		html.push('<div class="dropListBtn">');
		html.push('	<button name="<"></button>');
		html.push('	<button name=">"></button>');
		html.push('</div>');
		html.push('<div class="fL">');
		html.push('	<fieldset style="height:'+fieldsetH+'px">');    
		html.push('		<legend>'+p.right.legend+'</legend>'); 
		html.push('		<select multiple="multiple" name="'+p.right.prmName+'" title="'+p.right.title+'"></select>');
		html.push('	</fieldset>'); 
		html.push('</div>');
		var pair = $(html.join(""));
		var left = pair.find("select[name="+p.left.prmName+"]");
		var right = pair.find("select[name="+p.right.prmName+"]");
		$(right).each(function(){
			this.update = function(json){
				if(!json||!p.right.name) return;
				var newRight = $(this).clone(true).empty();
 				var name = p.right.name;
 				try{
					var data = eval("json."+name);
				}catch(e){
				}
				if(typeof(data)=="undefined"&&typeof(json[name])=="undefined"){
					return;
				}else if(typeof(data)=="undefined"){
					var data = typeof(json[name]);
				}
				newRight.get(0).update = this.update;
				append(newRight, data);
				$(this).replaceWith(newRight);
				right = newRight;
			}
		});
		$(left).each(function(){
			this.update = function(json){
				if(!json||!p.left.name) return;
				var newLeft = $(this).clone(true).empty();
				var name = p.left.name;
				try{
					var data = eval("json."+name);
				}catch(e){
				}
				if(typeof(data)=="undefined"&&typeof(json[name])=="undefined"){
					return;
				}else if(typeof(data)=="undefined"){
					var data = typeof(json[name]);
				}
				newLeft.get(0).update = this.update;
				newLeft.get(0).param = this.param;
				append(newLeft, data);
				$(this).replaceWith(newLeft);
				left = newLeft;
			};
			this.param = function(){
				var param = {};
				if(p.jsonReader.val&&p.jsonReader.text){
					$(this).find("option").each(function(i){
						param[p.left.name+"["+i+"]"+"."+p.jsonReader.val] = $(this).val();
						param[p.left.name+"["+i+"]"+"."+p.jsonReader.text] = $(this).text();
					});
				}else{
					$(this).find("option").each(function(i){
						param[p.left.name+"["+i+"]"] = $(this).val();
					});
				}
				return param;
			};
			this.paramList = function(){
				var param = [];
					$(this).find("option").each(function(i){
						param.push($(this).val());
					});
				return param;
			};
		});
		var append = function(select, json){
			if(!json) return;
			if(p.jsonReader.val&&p.jsonReader.text){
				$(json).each(function(i, data){				
					$(select).append("<option value='" + data[p.jsonReader.val] + "'>" + data[p.jsonReader.text] + "</option>");
				});
			}else{
				$(json).each(function(i, data){				
					$(select).append("<option value='" + i + "'>" + data + "</option>");
				});
			}
		};
		return $(this).each(function(){
			if(this.update) return;
			$(this).append(pair);
			this.update=function(json){
				$(left).each(function(){
					this.update(json);
				});
				$(right).each(function(){
					this.update(json);
				});
			};
			this.param=function(){
				return $(left).get(0).param();
			};
			this.paramList=function(){
				return $(left).get(0).paramList();
			};
			if(p.json) this.update(p.json);
			$(this).button("<").click(function(){
				if($(right).find("option:selected").length<=0) return;
				$(left).append($(right).find("option:selected"));
				if($.isFunction(p.left.callback)) p.left.callback();
				$(left).click();
			});
			$(this).button(">").click(function(){
				if($(left).find("option:selected").length<=0) return;
				$(right).append($(left).find("option:selected"));
				if($.isFunction(p.right.callback)) p.right.callback();
				$(left).click();
			});
		});
	}
});