<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page import="org.springframework.security.web.WebAttributes"%>

<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
function initPage() {
    if (self != top) {
        top.location.href = "#";
    }
}
function refreshCaptcha() {
	document.getElementById("captchaImg").src = "${ctx}/security/jcaptcha.jpg?" + Math.floor(Math.random()*100);
}
</script>
</head>

<body onload="initPage();document.loginForm.j_username.focus();">

<div id="bd">
<div id="yui-main">
<%if ("1".equals(request.getParameter("error"))) {%>
<div class="error"><fmt:message key="global.login.error1" /></div>
<%
			}
			if ("2".equals(request.getParameter("error"))) {
		%>
<div class="error"><fmt:message key="global.login.error2" /></div>
<%
			}
			if ("3".equals(request.getParameter("error"))) {
		%>
<div class="error"><fmt:message key="global.login.error3" /></div>
<%}%>

<form id="loginForm" name="loginForm" action="${ctx}/j_spring_security_check?do" method="post">
<table class="noborder">
	<tr>
		<td><fmt:message key="global.login.user_name" />:</td>
		<td><input type='text' name='j_username' size='10' value='admin'
			<c:if test="not empty param.error">
							value='<%=session.getAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY)%>'</c:if> />
		</td>
		<td rowspan="3"><img id="captchaImg" src="${ctx}/security/jcaptcha.jpg" /></td>

	</tr>
	<tr>
		<td><fmt:message key="global.login.password" />:</td>
		<td><input type='password' size='10' name='j_password' value='admin' /></td>
	</tr>
	<tr>
		<td><fmt:message key="global.login.verify_code" />:</td>
		<td><input type='text' name='j_captcha' size='5' /></td>
	</tr>
	<tr>
		<td colspan='3'><input type="checkbox" name="_spring_security_remember_me" /><fmt:message key="global.login.remember_me" /> <span style="margin-left: 25px"><a
			href="javascript:refreshCaptcha()"><fmt:message key="global.login.change_img" /></a></span></td>
	</tr>
	<tr>
		<td colspan='3'><input value="<fmt:message key="global.login.login" />" type="submit" /></td>
	</tr>
</table>
</form>
<span><b>admin/admin</b> ,<b>user/admin</b></span></div>
</div>
</div>


</body>
</html>

