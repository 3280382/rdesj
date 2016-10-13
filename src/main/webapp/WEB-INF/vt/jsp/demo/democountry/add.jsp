<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.add" />",
        width: 380,
        height: 140,
        buttons:{
	    	"<fmt:message key="global.op.save" />":{
	        url:"${ctx}/demo/democountry/save.json",
	        param:function() {
	            return $("#fmForm").getParam();
	        },
	        validate:function() {		        
	            return true;
	        },
	        dataType: "json",
	        success:function(data) {
				if( !$("#fmForm").resultBinding(data,{position:"left"}).hasError )
				{
					dialog.dialogClose();					
		        	toolBar.button("searchBtn").click();
				}            
	        }
	    	},
	    	"<fmt:message key="global.op.close" />": function() {
                dialog.dialogClose();                
	    	}
        }
    });//end
</script>
<div id="fmDiv">
    <form id="fmForm">
    <br>
    <table class="editTB">
		<tr>
		<th style="width:80px;"><span class="fRed">*</span><fmt:message key="demoCountry.name" /></th>
		<td style="width:300px;"><input type="text" name="name" id="name" /></td>
		</tr>
	</table>
    </form>
</div>
