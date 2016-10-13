<%@ page contentType="text/html;charset=UTF-8" language="java"%>
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
<script>
var LOGIN_URL = "${ctx}/security/sysuser/login.do";	
</script>

<!-- <script type="text/javascript" src="${ctx}/js/min/ciclMIN.js"></script> -->

<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.bgiframe.js"></script>

<script type="text/javascript" src="${ctx}/js/lib/jquery-ui-1.8.12.custom/development-bundle/ui/i18n/jquery.ui.datepicker-<%=org.springframework.web.servlet.support.RequestContextUtils.getLocale(request).getCountry().toLowerCase()%>.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery.jqGrid-4.0.0/src/i18n/grid.locale-<%=org.springframework.web.servlet.support.RequestContextUtils.getLocale(request).getCountry().toLowerCase()%>.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery-ui-1.8.12.custom/development-bundle/ui/jquery-ui-1.8.12.custom.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery.jqGrid-4.0.0/src/grid.base.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery.jqGrid-4.0.0/src/jquery.fmatter.js"></script>

<script type="text/javascript" src="${ctx}/js/com/ajaxSetting.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.extend.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.gridview.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.dialog.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.param.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.splite.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.tabs.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.button.js"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/css/forms.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/jquery.jqGrid-4.0.0/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/jquery.jqGrid-4.0.0/css/ui.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/com/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/jquery-com-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/jquery-ui-1.8.12.custom/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/jquery-ui-1.8.12.custom.css" />

<!--[if IE]>
<style type="text/css">
body { overflow:hidden; }
</style>
<![endif]-->
<decorator:head />
</head>
<body bgcolor="#FFFFFF">	
<decorator:body />
</body>
</html>