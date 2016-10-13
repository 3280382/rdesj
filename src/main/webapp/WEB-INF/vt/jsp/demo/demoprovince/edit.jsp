<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>

<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.edit" />",
        width: 380,
        height: 180,
        buttons:{
	    	"<fmt:message key="global.op.save" />":{
	        url:"${ctx}/demo/demoprovince/update.json",
	        param:function() {
	            return $("#fmForm").getParam();
	        },
	        validate:function() {
	            return true;
	        },
	        dataType: "json",
	        success:function(data) {
				if( !$("#fmForm").resultBinding(data,{position:"BOTTOM"}).hasError )
				{
					dialog.dialogClose();
					loadProvince();
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
		<th style="width:80px;"><fmt:message key="demoProvince.id" /></th>
		<td style="width:300px;"><input disabled type="text" name="id" id="id" value="${demoProvince.id}" /></td>
		</tr>
		<tr>
		<th><span class="fRed">*</span><fmt:message key="demoCountry.name" /></th>
		<td>
			<select name="countryId" id="countryId" >
				<option value=""></option>
			<c:forEach items="${demoCountryList}" var="it">
				<option value="${it.id}" <c:if test="${it.id==demoProvince.countryId}">selected</c:if> >${it.name}</option>
			</c:forEach> 
			</select>
		</td>
		</tr>
		<tr>
		<th><span class="fRed">*</span><fmt:message key="demoProvince.name" /></th>
		<td><input type="text" name="name" id="name" value="<c:out value="${demoProvince.name}" />" /></td>
		</tr>
	</table>
    </form>
</div>
