<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>

<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.edit" />",
        width: 380,
        height: 280,
        buttons:{
	    	"<fmt:message key="global.op.save" />":{
	        url:"update.json",
	        param:function() {
	            return $("#fmForm").getParam();
	        },
	        validate:function() {
	            return true;
	        },
	        dataType: "json",
	        success:function(data) {
				if( !$("#fmForm").resultBinding(data).hasError )
				{
					dialog.dialogClose();
					reloadList();
				}   
	        }
	    	},
	    	"<fmt:message key="global.op.close" />": function() {
                dialog.dialogClose();               
	    	}
        }
    });//end

    $("#countryId").selectPair($("#provinceId"));
    $("#birthday",$("#fmDiv")).datepickerView();
</script>
<div id="fmDiv">
    <form id="fmForm">
	<br>
    <table class="editTB">
    	<tr>
		<th style="width:80px;"><fmt:message key="demoData.id" /></th>
		<td style="width:300px;"><input disabled type="text" name="id" id="id" value="${demoData.id}" /></td>
		</tr>
		<tr>
		<th><span class="fRed">*</span><fmt:message key="demoData.name" /></th>
		<td><input type="text" name="name" id="name" value="<c:out value="${demoData.name}" />" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoData.birthday" /></th>
		<td><input type="text" name="birthday" id="birthday" value="<fmt:formatDate value="${demoData.birthday}" pattern ="${dateFormat}" />" /></td>
		</tr>
		<tr>
		<th><span class="fRed">*</span><fmt:message key="demoData.sex" /></th>
		<td><select name="sex" id="sex">
					<option value=""></option>
				<c:forEach items="${sexList}" var="it">
					<option value="${it.key}" <c:if test="${it.key==demoData.sex}">selected</c:if> >${it.value}</option>
				</c:forEach> 
				</select></td>
		</tr>
		<tr>
		<th><fmt:message key="demoData.salary" /></th>
		<td><input type="text" name="salary" id="salary" value="<c:out value="${demoData.salary}" />" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoData.email" /></th>
		<td><input type="text" name="email" id="email" value="<c:out value="${demoData.email}" />" /></td>
		</tr>
		<tr>
		<th><span class="fRed">*</span><fmt:message key="demoData.countryId" /></th>
		<td>
			<select name="countryId" id="countryId" >
				<option value=""></option>
			<c:forEach items="${demoCountryList}" var="it">
				<option value="${it.id}" <c:if test="${it.id==demoData.countryId}">selected</c:if> >${it.name}</option>
			</c:forEach> 
			</select>
		</td>
		</tr>
		<tr>
		<th><span class="fRed">*</span><fmt:message key="demoData.provinceId" /></th>
		<td>
			<select name="provinceId" id="provinceId">
				<option value=""></option>
			<c:forEach items="${demoProvinceList}" var="it">
				<option parentId="${it.countryId}" value="${it.id}" <c:if test="${it.id==demoData.provinceId}">selected</c:if> >${it.name}</option>
			</c:forEach> 
			</select>	
		</td>
		</tr>
	</table>
    </form>
</div>
