<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
function search()
{
	$("#demoDataSearchForm").attr("action","list.do");
	$("#demoDataSearchForm").submit();
}
function list2csv()
{ 
	$("#demoDataSearchForm").attr("action","list2csv.do");
	$("#demoDataSearchForm").submit();
}
function add()
{
	$("#detail").attr("src","add.do");
}
function show(id)
{
	$("#detail").attr("src","show.do?id="+id);
}
function edit(id)
{
	$("#detail").attr("src","edit.do?id="+id);
}
function wizard()
{
	$("#detail").attr("src","../demowizard/first.do");
}
function remove(id)
{	
	$.getJSON("remove.json", {id:id}, function(data) {		 
		alert("<fmt:message key="global.op.tip.success" />");
		search();
	});
}

$(function()
{
	var totalCount = ${demoDataSearchResults.totalCount}.0;
	var resultsPerPage = ${demoDataSearchForm.resultsPerPage};
	var pageNumber = ${demoDataSearchForm.pageNumber}-1;
	$("#listpage").pagination
	(
		totalCount, 
		{
        callback: function(pageNumber){if(!this.pageInit){this.pageInit=true;return;}$("#pageNumber").val(pageNumber+1);search();},
        items_per_page: resultsPerPage,
        current_page: pageNumber
    	}
    );
}
);
</script>
</head>
<body>
<div class="contentDiv">
<ul>
	<form:form name="demoDataSearchForm" commandName="demoDataSearchForm">
	<li id="toolBar" class="toolBar">
	<form:hidden path="pageNumber" />
	<form:hidden path="method" />
	<form:hidden path="resultsPerPage" />
	<input name="searchBtn" class="inputBt" type="button" value="<fmt:message key="global.op.search" />" onclick="search();" /> 
	<input name="list2csvBtn" class="inputBt" type="button" value="<fmt:message key="global.op.download" /> CSV" onclick="list2csv();" /> 
	<input name="addBtn" class="inputBt" type="button" value="<fmt:message key="global.op.add" />" onclick="add();" />
	<input name="wizardBtn" class="inputBt" type="button" value="wizard" onclick="wizard();" />	
	</li>
	<li id="searchBar" class="searchBar">
	<fmt:message key="demoData.name" /> <form:input path="demoData.name" />
	</li>
	</form:form>
	<li><c:if test="${not empty demoDataSearchResults.results}">
		<table class="listTB">
			<tr>
				<th>&nbsp;</th>
				<th style="width:50px;"><fmt:message key="demoData.id" /></th>
				<th style="width:150px;"><fmt:message key="demoData.name" /></th>
				<th style="width:90px;"><fmt:message key="demoData.birthday" /></th>
				<th style="width:50px;"><fmt:message key="demoData.sex" /></th>
				<th style="width:90px;"><fmt:message key="demoData.salary" /></th>
				<th style="width:100px;"><fmt:message key="demoData.email" /></th>
				<th style="width:90px;"><fmt:message key="demoData.countryId" /></th>
				<th style="width:90px;"><fmt:message key="demoData.provinceId" /></th>
				<th style="width:90px;"><fmt:message key="demoData.createdDate" /></th>	
			</tr>
			<c:forEach items="${demoDataSearchResults.results}" var="result">
				<tr>
					<td class="aopt">
						<a href="javascript:edit('${result.id}');"><fmt:message key="global.op.edit" /></a> 
						<a href="javascript:remove('${result.id}');"><fmt:message key="global.op.remove" /></a>
					</td>
					<td>${result.id}</td>
					<td class="aopt"><a href="javascript:show('${result.id}');">${result.name}</a></td>
					<td><fmt:formatDate value="${result.birthday}" pattern ="${dateFormat}" /></td>
					<td>${sexList[result.sex]}</td>
					<td>${result.salary}</td>
					<td>${result.email}</td>
					<td>${result.country.name}</td>
					<td>${result.province.name}</td>
					<td><fmt:formatDate value="${result.createdDate}" pattern ="${dateFormat}" /></td>
				</tr>
			</c:forEach>
		</table>
	</c:if></li>
	<li>
	<div id="listpage"></div>
	</li>
	<li></li>
	<li class="content">
	<br><iframe src="" id="detail" name="detail" style="border:0px;width: 800px; height: 230px;" />
	</li>
</ul>
</div>
</body>
</html>
