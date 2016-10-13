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
</script>
<div id="fmDiv">
    <form id="fmForm">
	<br>
    <table class="editTB">
    	<tr>
		<th style="width:80px;"><fmt:message key="demoDept.id" /></th>
		<td style="width:300px;"><input disabled type="text" name="id" id="id" value="${demoDept.id}" /></td>
		</tr>
		<tr>
		<th><span class="fRed">*</span><fmt:message key="demoDept.name" /></th>
		<td><input type="text" name="name" id="name" value="<c:out value="${demoDept.name}" />" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoDept.parentId" /></th>
		<td>
			<select name="parentId" id="parentId" >
				<option value=""></option>
			<c:forEach items="${deptList}" var="it">
				<option parent="${it.parentId}" value="${it.id}" <c:if test="${it.id==demoDept.parentId}">selected</c:if> >${it.name}</option>
			</c:forEach> 
			</select>
		</td>
		</tr>
		<tr>
		<th><fmt:message key="demoDept.code" /></th>
		<td><input type="text" name="code" id="code" value="<c:out value="${demoDept.code}" />" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoDept.sortOrder" /></th>
		<td><input type="text" name="sortOrder" id="sortOrder" value="<c:out value="${demoDept.sortOrder}" />" /></td>
		</tr>
		<tr>
		<th><fmt:message key="demoDept.deptType" /></th>
		<td><input type="text" name="deptType" id="deptType" value="<c:out value="${demoDept.deptType}" />" /></td>
		</tr>
	</table>
    </form>
</div>
