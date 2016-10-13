<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>error</title>
</head>
<body marginheight=0 marginwidth=0 class="scrollNo">
<script>
	alert("<fmt:message key="upload.error" />");
	self.close();
</script>

</body>