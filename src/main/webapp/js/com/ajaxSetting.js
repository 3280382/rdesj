$(function(){
	if($(top.document).find("#loading").length==0) {
		$(top.document.body).append('<div id="loading" style="display:none;width:100%;height:100%;position:absolute;top:0;left:0;z-index:2001;"></div>');
		$("#loading").append(
			"<table height='50' style='position:absolute;top:75px;right:0px'>" +
				"<tr>" +
					"<td width='60'  align='center' valign='middle'></td>" +
					"<td width='110' style='color:#00DB00;' align='left' valign='middle'><b>loading...</b></td>" +
				"</tr>" +
			"</table>"
		);
	}
	$(top.document).ajaxStart(function(){
	  	$(top.document).find("#loading").show();
	});
	$(top.document).ajaxStop(function(){
	  	$(top.document).find("#loading").hide();
	});
	$(top.document).ajaxComplete(function(event,request,settings){
	  	$(top.document).find("#loading").hide();
	});
	$(top.document).ajaxError(function(event,request,settings, exception){
		$(top.document).find("#loading").hide();
		
		//Authority error handle
		if(exception=="Forbidden")
		{
			alert("You have no authority!");
		}
	});


    $.ajaxSetup({
    	
		dataFilter:function(data, type)
		{
            type = type || "";
            var LOGIN_KW = "j_spring_security_check";	        
		    var NO_AUTH = "NO_AUTHENTICATION";
		    var SESSION_EXPIRED = "This session has been expired";   
		    var STATUS_EXCEPTION = "EXCEPTION";
		    var EXCEPTION_PAGE = "EXCEPTION_PAGE";
		    
		    var tempData;
		    
		    //Authority error handle
            if(type.toUpperCase() == "JSON") 
            {
                if (data && typeof data == 'object')
                {      
                	tempData = data;
                } 
                else if(data && typeof data == 'string')
                {
                    try 
                    {
                       tempData = eval("(" + data + ")");
                    } 
                    catch (e) 
                    {                    	
                        if (data.indexOf(LOGIN_KW) != -1)
                        {
                        	alert("This session has been timeout, please login again!");
							top.location.href = LOGIN_URL;
                            return {"SESSION_TIMEOUT": true};
                        } 
                        else if (data.indexOf(NO_AUTH) != -1) 
                        {
                        	alert("You have no authority!");
                            return {"NO_AUTH": true};
                        } 
                        else if (data.indexOf(SESSION_EXPIRED) != -1) 
                        {
                        	alert("This session has been expired (possibly due to multiple concurrent logins being attempted as the same user).");
                        	top.location.href = LOGIN_URL;
                        	return {"SESSION_EXPIRED": true};
                        }
                    }
                }
            } 
            else 
            {
            	tempData = data;
                if (data.indexOf(LOGIN_KW) != -1) 
                {
                	alert("This session has been timeout, please login again!");
                	top.location.href = LOGIN_URL;
                    return {"SESSION_TIMEOUT": true};
                } 
                else if (data.indexOf(NO_AUTH) != -1) 
                {
                	alert("You have no authority!");
                    return {"NO_AUTH": true};
                } 
                else if (data.indexOf(SESSION_EXPIRED) != -1) 
                {
                	alert("This session has been expired (possibly due to multiple concurrent logins being attempted as the same user).");
                	top.location.href = LOGIN_URL;
                    return {"SESSION_EXPIRED": true};
                }
                else if (data.indexOf(EXCEPTION_PAGE) != -1) 
                {
                	handleException(data);
                    return {"EXCEPTION": true};
                } 
            }
            
            // Exception handle
            if( tempData['status'] == STATUS_EXCEPTION || tempData['exception'] ) 
 		    {
            	handleException( tempData['errormsg'] || tempData['exception'] );
 		    }
            
			return data;
		}//end of dataFilter    
	});//end of setup
});

function handleException(exceptionMsg){
	if($(top.document).find("#exception").length==0) 
		{
			$(top.document.body).append('<div id="exception" class="fRed" style="display:none;z-index:2001;"><div id="exceptionMsg"></div></div>');		
		}
		$(top.document).find("#exceptionMsg").html( exceptionMsg );
		var exceptionDialog = $(top.document).find("#exception").dialog({
	        title:"System Internal Error",
	        width: 650,
	        height: 420,
	        buttons:
	        {
		    	"close": function() 
		    	{
					exceptionDialog.dialogClose();                
		    	}
	        }
	    });
}