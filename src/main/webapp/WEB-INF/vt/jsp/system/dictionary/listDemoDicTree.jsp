<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<%@page import="com.cicl.frame.system.dictionary.entity.DictionaryTree"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
$(function(){
	$("selTree1").treeSelect({parentKey:"parentId",key:"id"});
});
</script>
</head>
<body>
<div class="contentDiv">
	<ul>
		<li>
		Tree path:/IR_PRIO, select list<br/>
		<select name="selList1" id="selList1" >
			<option value="">--please chose--</option>
			<c:forEach items="<%=DictionaryTree.getInstance().getChildren("SEC_USER_TYPE")%>" var="it">
			<option value="${it.key}">${it.value}</option>
			</c:forEach> 
		</select><br/>
		Tree path:/IR_PRIO/Normal, select list<br/>
		<select name="selList2" id="selList2" >
			<option value="">--please chose--</option>
			<c:forEach items="<%=DictionaryTree.getInstance().getChildren("/IR_PRIO/Normal")%>" var="it">
			<option value="${it.key}">${it.value}</option>
			</c:forEach> 
		</select><br/>
		Tree path:/IR_PRIO, select tree<br/>
		<select name="selTree1" id="selTree1" >
			<option value="">--please chose--</option>
			<c:forEach items="<%=DictionaryTree.getInstance().getChildren("/IR_PRIO")%>" var="it">
			<option id="{it.value.item.id}" parentId="{it.value.item.parentId}" value="${it.key}">${it.value}</option>
			</c:forEach> 
		</select><br/>
		</li>
	</ul>
</div>
</body>
</html>
