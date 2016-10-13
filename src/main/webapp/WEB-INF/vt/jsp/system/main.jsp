<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<%@page import="com.cicl.frame.security.sysuser.util.SpringSecurityUtils"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>ELITES</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="max-age=0" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta name="application-name" content="RDESJ"/>
<meta name="description" content="Application for LB HKPF"/>
<style>
html{ height:100%}
body{background:url(${ctx}/images/bot_bg.gif) no-repeat left bottom; height:100%;overflow:hidden}
#header{HEIGHT: 96px; background:url(${ctx}/images/top_bg.jpg) no-repeat left;}
#header .logo{float:left; margin-top:8px; width:465px; height:70px; }
#header .top_r{float:right; margin-right:15px; margin-top:5px; color:#666; line-height:140%; font-size:11px}
#header .top_r p{padding-left:5px;}
#header .top_r ul{background:url(${ctx}/images/logos.gif) no-repeat; height:30px; width:250px; padding-top:6px;padding-left:18px}
#header .top_r ul li{ float:left; width:32px;}
.main_table{WIDTH: 100%; background :url(${ctx}/images/bg.gif) repeat-y left;}
#footer{background:url(${ctx}/images/bot_bg.jpg) no-repeat left top; padding-top:10px;height:10px;width:100%}
</style>
<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery/jquery.bgiframe.js"></script>
<script type="text/javascript" src="${ctx}/js/lib/jquery-ui-1.8.12.custom/js/jquery-ui-1.8.12.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/js/com/ajaxSetting.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.param.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.extend.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.splite.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.tabs.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.dialog.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.button.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/com/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/jquery-com-ui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/jquery-ui-1.8.12.custom/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/jquery-ui-1.8.12.custom.css" />
<script>
var LOGIN_URL = "${ctx}/security/sysuser/login.do";	
</script>
<script type="text/javascript">
function setCookie(name, value, expire, path) 
{
	  expire = expire || 30 * 24 * 60 * 60 * 1000;
	  path = path || '/';
	  var date = new Date();
	  date.setTime(date.getTime() + expire);
	  document.cookie = name + "=" + encodeURIComponent(value) + "; expires=" + date.toUTCString() + "; path=" + path;
};
function setLocale(local)
{
	setCookie('CFFLocale',local);
	location.reload();
}
function setStyle(style)
{
	setCookie('CFFTheme',style);
	location.reload();
}
</script>
<script type="text/javascript">
var spliter,accordion;
var ss;
$(function() {    
    accordion = $("#accordion").accordion({autoHeight:false,  active: 1});
    spliter = $("#spliter").splite();
    var h=document.documentElement.scrollHeight-125;//visiable area height
	ss=document.getElementById('main_div');
	ss.style.height=h+"px";
});
</script>
<script type="text/javascript">
window.onresize=function()
{
	var h=document.documentElement.scrollHeight-125;//visiable area height
	ss=document.getElementById('main_div');
}
</script>
</head>
<body style="overflow-y:hidden;">
<div id="header" style="width:100%;HEIGHT: 96px;">
	<div class="logo"><IMG src="${ctx}/images/logo.gif" width=242 height=35></IMG></div>	
	<div  class="top_r" >
		<p><fmt:message key="global.main.top.cur_user" />:<A class=aopt href="javascript:$.dialog('${ctx}/security/sysuser/editProfile.do',{});" name="Edit"><%=SpringSecurityUtils.getCurrentUserName()%></A></p>
		<ul>
		<li> <A class=aopt href="${ctx}/j_spring_security_logout" name="Logout"><img src="${ctx}/images/icon1.gif" title="Logout" /></A></li>
		<li> <A class=aopt href="javascript:$.dialog('${ctx}/security/sysuser/editProfile.do',{});" name="Edit"><img src="${ctx}/images/icon2.gif" title="Edit profile"/></A></li>
		<li> <A class=aopt href="javascript:$.dialog('${ctx}/security/sysuser/editPassword.do',{});" name="Logout"><img src="${ctx}/images/icon3.gif" title="Change password" border="0"/></A></li>
		<li> <A class=aopt><img src="${ctx}/images/icon4.gif" title="Print"/></A></li>
		<li> <A class=aopt><img src="${ctx}/images/icon5.gif" title="Help"/></A></li>
		<li> <A class=aopt><img src="${ctx}/images/icon6.gif" title="Telephone"/></A></li>
		<li> <A class=aopt><img src="${ctx}/images/icon7.gif" title="documents"/></A></li>	 
		</ul>
		<p>
			<fmt:message key="global.main.top.change_language" />:
			<a class="aopt" href="javascript:setLocale('zh_CN');">中文</a>
			<a class="aopt" href="javascript:setLocale('en_EN');">English</a>
		</p>
		<p>
			<fmt:message key="global.main.top.change_style" />:
			<a class="aopt" href="javascript:setStyle('redmond');"><fmt:message key="global.style_redmond" /></a>
			<a class="aopt" href="javascript:setStyle('ui-lightness');"><fmt:message key="global.style_lightness" /></a>
		</p>
	</div>
</div>
<div id="mainBody" style="width:100%;">
	<table style="width:100%;" class="main_table">
		<tr>
			<td style="width:180px;padding:0px 0px 0px 5px;text-align:left;vertical-align:top;">	
	<div id="accordion">
					<h3><a href="#"><fmt:message key="global.menu.ajax" /></a></h3>
					<div>
						<ul>
							<li><a style="color:#0033CC;" class="aopt" target="rightFrm" href="${ctx}/demo/demoajax/index.do"><fmt:message key="global.menu.ajax" /></a></li>
							<li><a style="color:#0033CC;" class="aopt" target="rightFrm" href="${ctx}/demo/demodept/index.do"><fmt:message key="global.menu.dept" /></a></li>
							<li><a style="color:#0033CC;" class="aopt" target="rightFrm" href="${ctx}/demo/demomasterdetail/index.do"><fmt:message key="global.menu.masterdetail" /></a></li>
							<li><a style="color:#0033CC;" class="aopt" target="rightFrm" href="${ctx}/demo/demobasic/list.do"><fmt:message key="global.menu.basic" /></a></li>
						</ul>
					</div>
					<h3><a href="#"><fmt:message key="global.menu.security" /></a></h3>
					<div>
						<ul>
							<li><a style="color:#0033CC;" class="aopt" target="rightFrm" href="${ctx}/security/sysuser/index.do"><fmt:message key="global.menu.security.sysuser" /></a></li>	
							<li><a style="color:#0033CC;" class="aopt" target="rightFrm" href="${ctx}/security/organization/index.do"><fmt:message key="global.menu.security.organization" /></a></li>
							<li><a style="color:#0033CC;" class="aopt" target="rightFrm" href="${ctx}/security/role/index.do"><fmt:message key="global.menu.security.role" /></a></li>
							<li><a style="color:#0033CC;" class="aopt" target="rightFrm" href="${ctx}/security/authority/index.do"><fmt:message key="global.menu.security.authority" /></a></li>
						</ul>
					</div>
					<h3><a href="#"><fmt:message key="global.menu.system" /></a></h3>
					<div>
						<ul>
							<li><a style="color:#0033CC;" class="aopt" target="rightFrm" href="${ctx}/system/dictionary/index.do"><fmt:message key="global.menu.system.dictionary" /></a></li>	
							<li><a style="color:#0033CC;" class="aopt" target="rightFrm" href="${ctx}/audit/auditlog/index.do"><fmt:message key="global.menu.audit.auditlog" /></a></li>
						</ul>
					</div>
					<h3><a href="#">Section 4</a></h3>
					<div>
					</div>
				</div>
			</td>
			<td class="inlineCM"><a href="#" id="spliter"></a></td>
			<td style="vertical-align:top;width:auto;">		
			<div id="main_div" >
				<iframe name="rightFrm" id="rightFrm" width="100%" height="100%" src="${ctx}/security/sysuser/index.do" frameborder="0" scrolling="auto" ></iframe>
			</div>
			</td>
		</tr>
	</table>
</div>
<div id="footer"> </div>
</body>
</html>