<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
$(function(){
	$("#tabs").tabs();
} 
);
</script>
</head>
<body>
<div class="tabDivOut">
	<div id="tabs" class="tabDiv">
		<div name="Definition" isrc="list.do" class="content"></div>
	</div>
</div>
</body>
</html>
