/*jquery extend*/
jQuery.extend({
	getJSON:function(url,data,callback){
		if(jQuery.isFunction(data)){
			callback = data;
			data = null;
		}
		$.ajax({
			cache:false,
			dataType:"json",
			type:"POST",
			url:url,
			data:data,
			success:function(res){
				try{
					var obj;
					if(typeof res === 'string')
						obj = eval("("+res+")");
					else
						obj = res;
					
					callback(obj);
				}catch(e){
					alert(e);
				}
			},
            error: function(xmlHttp, text) {
				//此前应该过滤页面找不到、权限错误，并显示用户
                //alert("extend系统内部错误，请重新尝试！");
            }
		});
	},
	paramToForm:function(data, formDivId, url, target)
	{
		formDiv = $("#"+formDivId);
	
		var html = "";
		$.each(data, function(i,m){
			html += '<input type="text" name="'+i+'" value="'+m+'" /><br>';
		});	
	
		if(!target) target = "_self";
		html = "<form id='downloadForm' target='"+target+"' action='"+url+"' method='POST'>"+html+"</form>";
		if(formDiv.attr("id"))
		{
			formDiv.html(html);
		}
		else
		{
			html = "<div id='"+formDivId+"' style='display:none'>"+html+"</div>";
			$(document.body).append(html);
		}
		$("#downloadForm").submit();		
	},
	downloadFile:function(data,url)
	{
		return $.paramToForm(data,"downloadDiv",url);
	}
});

var AJAX_RESULT = 
{
	STATUS_EXCEPTION : 'EXCEPTION',
	STATUS_VALID_ERROR : 'VALID_ERROR',
	STATUS_BUSINESS_ERROR : 'BUSINESS_ERROR'
};

jQuery.fn.extend({
	resultBinding:function(data,p)
	{
		if(!data) return;
		
		var $this = $(this);
		if(!p) p = {};
		p = $.extend(
			{position : "right"
			//right,left,bottom
			},p);
		
		$this.hasError = false;
		
		//clear early error message
		$("errormsg", $this).remove();
		//exception handle
		
		//validation error handle
		if( data['status'] == AJAX_RESULT.STATUS_VALID_ERROR )
	    {
	    	$.each(data['errorsMsg'], function(key, val)
	    	{	
	    		//TODO: bug when field id has a dot, like "auditLog.opTime"
	        	//var element = $this.find("[name="+val.field+"]");
	    		var element = $("#"+val.field, $this);

	        	var label = $("<errormsg/>")
				.attr({"for":  val.field, generated: true})
				.addClass("errorMsg");
	        	switch(p.position)
	        	{	        		
	        		case "bottom":
	        			label.html( ("<BR>"+val.error || "") + "&nbsp;");
	        			label.insertAfter(element);
	        		break;
	        		case "left":
	        			label.html( (val.error || "") + "&nbsp;");
	        			label.insertBefore(element);
	        		break;
	        		case "right":
	        		default:
	        			label.html( (val.error || "") + "&nbsp;");
	        			label.insertAfter(element);
	        		break;
	        	}        	
	        	
	    	});
	    	$this.status = AJAX_RESULT.STATUS_VALID_ERROR;
	    	$this.hasError = true;
	    }      
		//business error handle
		else if( data['status'] == AJAX_RESULT.STATUS_BUSINESS_ERROR )
	    {
	    	alert(data['errorsMsg']);
	    	$this.status = AJAX_RESULT.STATUS_BUSINESS_ERROR;
	    	$this.hasError = true;
	    }  
	    else
	    {		        	
	    	$this.hasError = false;
	    } 
		return $this;
	}
});	
jQuery.fn.extend({
	datepickerView:function(p){
		if(!p) p = {};
		p = $.extend({dateFormat:"yy-mm-dd"},p);
		return $(this).datepicker(p);
	}
});