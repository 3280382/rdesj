<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<script type="text/javascript">
    var dialog = $("#fmDiv").dialog({
        title:"<fmt:message key="global.op.add" />",
        width: 380,
        height: 280,
        buttons:{
	    	"<fmt:message key="global.op.save" />":{
	        url:"save.json",
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
    $("#birthday",$("#fmDiv")).datepickerView();
</script>
<div id="fmDiv">
    <form id="fmForm">
    <br>
    <table class="editTB">
		<tr>
		<th style="width:80px;"><span class="fRed">*</span><fmt:message key="demoDept.name" /></th>
		<td style="width:300px;"><input type="text" name="name" id="name" /></td>
		</tr>
		<tr>
		<th style="width:80px;"><fmt:message key="demoDept.parentId" /></th>
		<td style="width:300px;">
			<select name="parentId" id="parentId" >
				<option value=""></option>
			<c:forEach items="${deptList}" var="it">
				<option parent="${it.parentId}" value="${it.id}" <c:if test="${it.id==parentId}">selected</c:if> >${it.name}</option>
			</c:forEach> 
			</select>
		</td>
		</tr>
		<tr>
		<th style="width:80px;"><fmt:message key="demoDept.code" /></th>
		<td style="width:300px;"><input type="text" name="code" id="code" /></td>
		</tr>
		<tr>
		<th style="width:80px;"><fmt:message key="demoDept.deptType" /></th>
		<td style="width:300px;"><input type="text" name="deptType" id="deptType" /></td>
		</tr>
		<tr>
		<th style="width:80px;"><fmt:message key="demoDept.sortOrder" /></th>
		<td style="width:300px;"><input type="text" name="sortOrder" id="sortOrder" /></td>
		</tr>
	</table>
    </form>
</div>
