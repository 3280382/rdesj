<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>

<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.edit" />",
        width: 380,
        height: 140,
        buttons:{
	    	"<fmt:message key="global.op.save" />":{
	        url:"${ctx}/demo/democountry/update.json",
	        param:function() {
	            return $("#fmForm").getParam();
	        },
	        validate:function() {
	            return true;
	        },
	        dataType: "json",
	        success:function(data) {
				if( !$("#fmForm").resultBinding(data,{position:"bottom"}).hasError )
				{
					dialog.dialogClose();
					grid.trigger("reloadGrid");
		        	//toolBar.button("searchBtn").click();
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
		<th style="width:80px;"><fmt:message key="demoCountry.id" /></th>
		<td style="width:300px;"><input disabled type="text" name="id" id="id" value="${demoCountry.id}" /></td>
		</tr>
		<tr>
		<th><span class="fRed">*</span><fmt:message key="demoCountry.name" /></th>
		<td><input type="text" name="name" id="name" value="<c:out value="${demoCountry.name}" />" /></td>
		</tr>
	</table>
    </form>
</div>
