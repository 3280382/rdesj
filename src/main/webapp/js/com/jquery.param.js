jQuery.fn.extend({
	getParam:function(){
		var param = {};
		var tagName = $(this).attr("tagName")?$(this).attr("tagName").toUpperCase():"";
		var $this = $(this);
		var getParamFrom = function(elem){
			var $e = $(elem);
			var prmName = $e.attr("name");
			if(!prmName) return;
					
			var prmValue = $e.val();
			if(typeof(prmValue)=="string") prmValue = $.trim(prmValue);
			if(prmValue&&prmValue!=""){
				param[prmName] = prmValue;
			}
			$(elem).each(function(){
				if($.isFunction(this.param)){
					delete param[prmName];
					$.extend(param, this.param());
				}
			});			
		}

		if("INPUT"!=tagName&&"SELECT"!=tagName){
			if(arguments.length==0){
				$(this).find("input:text,input:hidden,input:password,input:checkbox:checked,input:radio:checked,select,textarea").each(function(i) {
					getParamFrom(this);
				});
			}else{
				$.each(arguments, function(i, name){
					$this.find("[name="+name+"]").each(function(i) {
						getParamFrom(this);	
					});
				});
			}
		}else{
			getParamFrom(this);
		}		
		
		return param;
	},
	getSearchParam:function(otherParam){
		var param = this.getParam();

		var newParam = {};
		for( o in param)
		{
			newParam["conditions."+o] = param[o];
		}
		
		if(otherParam)
		{
			for( o in otherParam)
			{
				newParam["conditions."+o] = otherParam[o];
			}
		}
		
		param = newParam;
		return newParam
	},
	getObjsParam:function(otherParam){
		var param = this.getParam();

		var newParam = {};
		for( o in param)
		{
			if(param[o]==null)param[o]="";
			newParam["objs[0]."+o] = param[o];
		}
		
		if(otherParam)
		{
			for( o in otherParam)
			{
				if(otherParam[o]==null)otherParam[o]="";
				newParam["objs[0]."+o] = otherParam[o];
			}
		}
		
		param = newParam;
		return newParam
	},
	getAccessor : function(obj, expr) {
		var ret,p,prm = [], i;
		if( typeof expr === 'function') { return expr(obj); }
		ret = obj[expr];
		if(ret===undefined) {
			try {
				if ( typeof expr === 'string' ) {
					prm = expr.split('.');
				}
				i = prm.length;
				if( i ) {
					ret = obj;
				    while (ret && i--) {
						p = prm.shift();
						ret = ret[p];
					}
				}
			} catch (e) {}
		}
		return ret;
	},
	setFormValue:function(data){
		//clear error binding
		this.resultBinding({});
		//set form value
		this.find("input:text,input:hidden,input:password,input:radio,input:checkbox,select,textarea").each(function(idx) 
		{		
			var $this = $(this);
			var target = this.name || this.id ;
			var targetValue = $this.getAccessor(data, target);			
			var tagName = $this.attr("type") || $this.attr("tagName");
			tagName = tagName.toUpperCase();
		
			switch(tagName)
			{
				case "RADIO" :
					$this.attr("checked", $this.val()== (targetValue+"") );
					break;
				default:
					$this.val(targetValue);
			}		
		});
	}
});		