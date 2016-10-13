<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="max-age=0" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/jquery-ui-1.8.12.custom/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/jquery-ui-1.8.12.custom.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/com/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/jquery-com-ui.css" />

<!--[if IE]>
<style type="text/css">
body { overflow:hidden; }
</style>
<![endif]-->
<decorator:head />
</head>
<body>

<div><decorator:body /></div>

</body>
</html>