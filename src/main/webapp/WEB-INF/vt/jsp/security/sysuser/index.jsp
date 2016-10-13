<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
$(function(){
	$("#tabs").tabs();
});
</script>
</head>
<body>
<div class="tabDivOut">
	<div id="tabs" class="tabDiv">
		<div name="<fmt:message key="global.menu.security.sysuser" />" isrc="listDetail.do" class="content"></div>
		<div name="demo" isrc="listDemo.do" class="content"></div>
	</div>
</div>
</body>
</html>
