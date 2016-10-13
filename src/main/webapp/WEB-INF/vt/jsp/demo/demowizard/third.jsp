<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="demoDataForm.pageTitle" /></title>
<script type="text/javascript" language="JavaScript">
</script>
</head>
<body id="ltbxBg">
Current message: ${curMsg},third step
<form:form name="demoDataForm" commandName="demoDataForm" action="done.do" >
	<spring:hasBindErrors name="demoDataForm">
		<div class="errors errorsSummary"><form:errors path="*" /> <br />
		<br />
		</div>
	</spring:hasBindErrors>
	<form:hidden path="demoData.id" />
	<form:hidden path="method" />
	<p class="title"><span> <form:label path="demoData.name" cssErrorClass="fieldError">
		<fmt:message key="demoData.name" />
	</form:label> </span></p>
	<p class="input"><form:input path="demoData.name" /></p>
	<!--buttons-->
	<input name="submit" type=submit value="next" />
	<!--end buttons-->
	</div>
</form:form>
</body>
</html>