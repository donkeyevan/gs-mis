<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet" rel="stylesheet">
<style type="text/css">
body {
	height: 849px;
	background: gradient(linear, left top, left bottom, from(#666), to(#999));
   	background: -webkit-gradient(linear, left top, left bottom, from(#666), to(#999));
   	background: -moz-gradient(linear, left top, left bottom, from(#666), to(#999));
   	background: -o-gradient(linear, left top, left bottom, from(#666), to(#999));
}
</style>
</head>
<body>
<div class="span2">
	<div class="sidebarmenu">
	<c:if test="${sessionScope.login_game_admin.isSuperAdmin eq 1}">
		<a class="menuitem" href="<c:url value='/mentity/list'/>" target="main">实体管理</a>
		<a class="menuitem" href="<c:url value='/misgroup/list'/>" target="main">群组管理</a>
		<a class="menuitem" href="<c:url value='/misuser/list'/>" target="main">管理员管理</a>
		<a class="menuitem" href="<c:url value='/mispriv/list'/>" target="main">权限管理</a>
	</c:if>
	<c:forEach var="nav" items="${sessionScope.mis_admin_priv}">
		<c:choose>
			<c:when test="${nav.navType eq 1}">
				<a class="menuitem submenuheader">${nav.name}</a>
				<div class="submenu">
					<ul>
						<c:forEach var="priv" items="${nav.privs}" >
						<li><a href="${priv.realUrl}" target="main" onclick="setActive(this)">${priv.name}</a></li>
						</c:forEach>
					</ul>
				</div>
			</c:when>
			<c:otherwise>
				<a class="menuitem" href="${nav.realUrl}" target="main">${nav.name}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	</div>
</div>
<script src="<c:url value='/resources/bootstrap/docs/assets/js/jquery.js'/>"></script>
<script src="<c:url value='/resources/bootstrap/docs/assets/js/bootstrap-transition.js'/>"></script>
<script src="<c:url value='/resources/js/ddaccordion.js'/>"></script>
<script src="<c:url value='/resources/js/beauty.js'/>"></script>
</body>
</html>

