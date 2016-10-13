jQuery.fn.extend({
	list:function(p){
		if(typeof(p)=="string"){
			if(p=="update"){
				$(this).get(0).update(arguments[1]);
			}
			return;
		}
		p = $.extend({
			json:null,
			jsonReader:null,//格式为{root:"", val:"", text:""}
			include:null,
			exclude:null,
			selectAllText:"-- all --",
			select:null,
			onchange:null
		},p);
		var $this = $(this);
		var tagName = $(this).attr("tagName")?$(this).attr("tagName").toUpperCase():"";
		if(tagName != "SELECT") return;
		$(this).empty();
		if(p.selectAllText){
			$(this).append("<option value=''>"+p.selectAllText+"</option>");
		}
		$.each((p.json), function(i, data){
			if(!p.jsonReader||!p.jsonReader.val||!p.jsonReader.text){
				if(( p.include && $.inArray(i, p.include) < 0 ) || ( p.exclude && $.inArray(i, p.exclude) >= 0 )) return;
				var op = $("<option value="+i+" title="+data+">"+data+"</option>").appendTo($this);
				if(p.select == i) setTimeout(function(){op.attr("selected", true);});
			}else{
				var val = data[p.jsonReader.val],text = data[p.jsonReader.text];
				if(( p.include && $.inArray(val, p.include) < 0 ) || ( p.exclude && $.inArray(val, p.exclude) >= 0 )) return;
				var op = $("<option value="+val+" title="+text+">"+text+"</option>").appendTo($this);
				if(p.select == val) setTimeout(function(){op.attr("selected", true);});
			}
		});
		$(this).attr("title", $(this).find("option:selected").text());
		return $(this).each(function(){
			if(this.listparam) return;
			this.listparam = p;
			$(this).change(function(){
				$(this).attr("title", $(this).find("option:selected").text());
				if($.isFunction(p.onchange)){
					$(this).get(0)._onchange = p.onchange;
					$(this).get(0)._onchange($(this).getParam());
				}
			});
			this.update = function(data){
				if(p.jsonReader&&p.jsonReader.root){
					var root = p.jsonReader.root;
					try{
						var json = eval("data."+root);
					}catch(e){}
					if(typeof(json)=="undefined") var json = data[root];
					if(typeof(json)!="object") return;
					data = json;
				}
				this.listparam.json = data;
				var newList = $(this).clone(true).empty();
				newList.get(0).listparam = this.listparam;
				newList.get(0).update = this.update;
				if(this._onchange) newList.get(0)._onchange = this._onchange;
				$(newList).list(newList.get(0).listparam);
				$(this).replaceWith(newList);
			}
		});
	}
});