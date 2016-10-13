<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
</script>
</head>
<body>
<div class="contentDiv">
	<ul>
		<li>
		User Type(parentKey SEC_USER_TYPE):
		<select name="userType" id="userType" >
			<c:forEach items="<%=DictionaryHolder.get("SEC_USER_TYPE")%>" var="it">
			<option value="${it.key}">${it.value}</option>
			</c:forEach> 
		</select>
		<br/>
		</li>
		<li>
		User Type(parentKey SEC_USER_TYPE):
		<c:forEach items="<%=DictionaryHolder.get("SEC_USER_TYPE")%>" var="it">
		<input type="checkbox" value="${it.key}" />${it.value}
		</c:forEach> 
		<br/>
		</li>
		<li>
		Organization Type(parentKey SEC_ORG_TYPE):
		<select name="orgType" id="orgType" >
			<c:forEach items="<%=DictionaryHolder.get("SEC_ORG_TYPE")%>" var="it">
			<option value="${it.key}">${it.value}</option>
			</c:forEach> 
		</select>
		<br/>
		</li>
	</ul>
</div>
</body>
</html>
