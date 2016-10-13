jQuery.extend({
	calculateByte:function(sTargetStr,encode){
		var sTmpStr, sTmpChar;
		var nOriginLen = 0;
		var nStrLength = 0;
		sTmpStr = new String(sTargetStr);
		nOriginLen = sTmpStr.length;
		for ( var i=0 ; i < nOriginLen ; i++ ) {
			sTmpChar = sTmpStr.charAt(i);
			if (escape(sTmpChar).length > 4) {
				if(encode!=null&&encode.toUpperCase()=="UTF-8"){
					nStrLength += 3;
				}else{
					nStrLength += 2;
				}
			} else if (sTmpChar!='\r') {
				nStrLength ++;
			}
		}
	return nStrLength;  
	}
});
$.validateOptions = {
	error : function(msg, input){
		$(input).parent().parent().before("<tr><th></th><td class=errorMsg>"+msg+"</td></tr>");
	},
	clear : function(input){
		var prev = $(input).parent().parent().prev();
		if($(prev).find(".errorMsg").length>0){
			$(prev).remove();
		}
	},
	alias : function(input){
		return $(input).parent().prev().text().replace("*","").replace(":","");
	}
};
jQuery.fn.extend(
	validateSetup = function(newOptions){
		if(arguments.length==1) return;
		var newOptions = arguments[1];
		$.extend($.validateOptions,newOptions);
	}
);
jQuery.fn.extend({
	validate:function(p){
		p = $.extend({},{
			error:null,
			clear:null,
			alias:null
		},$.validateOptions,p);
		if($.isFunction(p.clear)) p.clear(this);
		var $this = $(this);
		var tagName = $(this).attr("tagName").toUpperCase();
		if("INPUT"!=tagName&&"SELECT"!=tagName){
			var result = true;
			$(this).find("input,select,.validate").each(function(){
				if(!result) return;
				if($.isFunction(this.validate)){
					if(!this.validate()) result = false;
				}else{
					if(!$(this).validate(p)) result = false;
				}
			});
			return result;
		}
		
		var items = {
			alias:null,
			notNull:false,
			equals:null,
			lessThan:null,
			largeThan:null,
			notLessThan:null,
			notLargeThan:null,
			minLength:null,
			maxByteLength:null,
			number:false,
			english:false,
			email:false,
			mobile:false,
			maxSize:null,
			minSize:null,
			encode:"UTF-8",
			check:null
		}
		for(var i in items){
			if($(this).attr(i)) items[i] = $(this).attr(i);
		}
		$.each(["notNull","email","mobile","english","number"],function(i, item){
			if($this.attr(item)=="false") items[item] = false;
			if($this.attr(item)=="") items[item] = true;
		})
		if(items.lessThan||items.largeThan||items.notLessThan||items.notLargeThan) items.number = true;
		var val = $(this).val();
		if(typeof(val)!="string") return true;
		val = $.trim(val);
		var alias = items.alias||p.alias(this);
		//validate notNull
		if(items.notNull){
			if(val==null||val==""){
				p.error(alias+"不能为空", this);
				return false;
			}
		}
		//validate equals
		if(items.equals){
			if(val!=items.equals){
				p.error(alias+"值必须等于"+items.equals, this);
				return false;
			}
		}
		//validate number
		if(items.number){
			var re = new RegExp(/^\d+$/);
			if(val!=null&&val!=""&&(val.match(re) == null)){
				p.error(alias+"只能是数字", this);
				return false;
			}
		}
		//validate lessThan
		if(items.lessThan){
			val = val - 0;
			if(val>items.lessThan||val==items.lessThan){
				p.error(alias+"值必须小于"+items.lessThan, this);
				return false;
			}
		}
		//validate largeThan
		if(items.largeThan){
			val = val - 0;
			if(val<items.largeThan||val==items.largeThan){
				p.error(alias+"值必须大于"+items.largeThan, this);
				return false;
			}
		}
		//validate notLessThan
		if(items.notLessThan){
			val = val - 0;
			if(val<items.notLessThan){
				p.error(alias+"值不能小于"+items.notLessThan, this);
				return false;
			}
		}
		//validate notLargeThan
		if(items.notLargeThan){
			val = val - 0;
			if(val>items.notLargeThan){
				p.error(alias+"值不能大于"+items.notLargeThan, this);
				return false;
			}
		}
		//validate minLength
		if(items.minLength){
			if(val.length<items.minLength){
				p.error(alias+"最小长度不能小于"+items.maxLength, this);
				return false;
			}
		}
		//validate english
		if(items.english){
			if(val.length<$.calculateByte(val)){
				p.error(alias+"只能是英文字符", this);
				return false;
			}
		}
		//validate maxByteLength
		if(items.maxByteLength){
			if($.calculateByte(val,items.encode)>items.maxByteLength){
				if(items.encode=="UTF-8"){
					var chineseCharLength = items.maxByteLength/3;
				}else{
					var chineseCharLength = items.maxByteLength/2;
				}
				chineseCharLength = (chineseCharLength+"").replace(/.\d+/,"");
				p.error(alias+"最大长度不能大于"+chineseCharLength+"个中文字符或"+items.maxByteLength+"英文字符", this);
				return false;
			}
		}
		//validate email
		if(items.email){
			var re = new RegExp(/^[0-9a-zA-Z_\-\.]+@[0-9a-zA-Z_\-]+(\.[0-9a-zA-Z_\-]+)*$/);
			if(val!=null&&val!=""&&(val.match(re) == null)){
				p.error(alias+"不合法，请参照'yourname@domain.com'输入", this);
				return false;
			}
		}
		//validate mobile
		if(items.mobile){
			var re = new RegExp(/^(13|15|18)\d{9}$/);
			if(val!=null&&val!=""&&(val.match(re) == null)){
				p.error(alias+"不合法，请重新输入", this);
				return false;
			}
		}
		//validate check result
		var errorMsg = ""
		if(items.check){		
			errorMsg = eval(items.check);
		}
		if(errorMsg){
			p.error(errorMsg, this);
			return false;
		}
		return true;
	}
});