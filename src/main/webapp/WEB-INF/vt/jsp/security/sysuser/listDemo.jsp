<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="/WEB-INF/vt/jsp/includes/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="${ctx}/js/lib/jquery.zTree-2.5/css/<%=org.springframework.web.servlet.support.RequestContextUtils.getTheme(request).getName()%>/tree.css" />
<script type="text/javascript" src="${ctx}/js/lib/jquery.zTree-2.5/jquery-ztree-2.5.js"></script>
<script type="text/javascript" src="${ctx}/js/com/jquery.treeview.js"></script>
<script>
var grid,toolBar;
$(function(){
	toolBar = $("#toolBar");
	toolBar.addbutton("selBtn","<fmt:message key="global.op.select" />部门").click(function(){	
		$.dialog("${ctx}/security/organization/select.do",{},true);		
	});
	toolBar.addbutton("selTreeBtn","<fmt:message key="global.op.select" />部门2").click(function(){	
		$.dialog("${ctx}/security/organization/selectTree.do",{},true);		
	});
	toolBar.addbutton("selRoleBtn","<fmt:message key="global.op.select" />角色").click(function(){	
		$.dialog("${ctx}/security/role/select.do",{},true);		
	});
	toolBar.addbutton("selSysUserBtn","<fmt:message key="global.op.select" />用户").click(function(){	
		$.dialog("${ctx}/security/sysuser/select.do",{},true);		
	});
}
);
</script>

</head>
<body>
	<ul>
		<li id="toolBar" class="toolBar"></li>
	</ul>
</body>
</html>
