<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="demoData.pageTitle" /></title>
</head>
<body>
	<form:hidden path="demoData.id" />
    <table class="editTB">
		<tr>
			<th style="width:80px;"><fmt:message key="demoData.id" /></th>
			<td style="width:300px;">${demoData.id}</td>
			<th style="width:80px;"><fmt:message key="demoData.name" /></th>
			<td style="width:300px;">${demoData.name}</td>
		</tr>
		<tr>
			<th><fmt:message key="demoData.sex" /></th>
			<td>${demoData.sex}</td>
			<th><fmt:message key="demoData.salary" /></th>
			<td>${demoData.salary}</td>
		</tr>
		<tr>
			<th><fmt:message key="demoData.birthday" /></th>
			<td><fmt:formatDate value="${demoData.birthday}" pattern ="${dateFormat}" /></td>
			<th><fmt:message key="demoData.email" /></th>
			<td>${demoData.email}</td>
		</tr>
		<tr>
			<th><fmt:message key="demoData.countryId" /></th>
			<td>${demoData.country.name}</td>
			<th><fmt:message key="demoData.provinceId" /></th>
			<td>${demoData.province.name}</td>
		</tr>
	</table>

</body>
</html>