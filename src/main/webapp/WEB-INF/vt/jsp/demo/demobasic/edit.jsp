<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><fmt:message key="demoDataForm.pageTitle" /></title>
</head>
<body>
<form:form name="demoDataForm" commandName="demoDataForm" action="update.do" >
	<spring:hasBindErrors name="demoDataForm">
		<div class="errors errorsSummary"><form:errors path="*global*" /> <br />
		<br />
		</div>
	</spring:hasBindErrors>
	<form:hidden path="method" />
    <table class="editTB">
		<tr>
			<th style="width:80px;"><fmt:message key="demoData.id" /></th>
			<td style="width:300px;"><form:input path="demoData.id" /><form:errors path="demoData.id"></form:errors></td>
			<th style="width:80px;"><span class="fRed">*</span><fmt:message key="demoData.name" /></th>
			<td style="width:300px;"><form:input path="demoData.name" /><form:errors path="demoData.name"></form:errors></td>
		</tr>
		<tr>
			<th><fmt:message key="demoData.sex" /></th>
			<td>
				<form:select path="demoData.sex">
	   				<form:option value="" label=""/>
	   				<form:options items="${sexList}"/>
	   			</form:select><form:errors  path="demoData.sex"></form:errors>
	   		</td>
			<th><fmt:message key="demoData.salary" /></th>
			<td><form:input path="demoData.salary" /><form:errors  path="demoData.salary"></form:errors></td>
		</tr>
		<tr>
			<th><fmt:message key="demoData.birthday" /></th>
			<td><form:input path="demoData.birthday" /><form:errors  path="demoData.birthday"></form:errors></td>
			<th><fmt:message key="demoData.email" /></th>
			<td><form:input path="demoData.email" /><form:errors  path="demoData.email"></form:errors></td>
		</tr>
		<tr>
			<th><fmt:message key="demoData.countryId" /></th>
			<td>
				<form:select path="demoData.countryId">
	   				<form:option value="" label=""/>
	   				<form:options items="${demoCountryList}" itemValue="id" itemLabel="name"/>
	   			</form:select><form:errors  path="demoData.countryId"></form:errors>
   			</td>
			<th><fmt:message key="demoData.provinceId" /></th>
			<td>
				<form:select path="demoData.provinceId">
	   				<form:option value="" label=""/>
	   				<form:options items="${demoProvinceList}" itemValue="id" itemLabel="name"/>
	   			</form:select><form:errors  path="demoData.provinceId"></form:errors>
   			</td>
		</tr>
		<tr>
		<td colspan="4" style="text-align:center;">
			<input class="inputBt" name="submit" type="submit" value="<fmt:message key="global.op.save" />" />
		</td>
		</tr>
	</table>

	

</form:form>
</body>
</html>