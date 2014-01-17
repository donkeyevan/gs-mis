<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="/css/default.css"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/beauty.css" rel="stylesheet'/>" rel="stylesheet">
<body>
<div class="sidebarmenu">
	<a class="menuitem" href="<c:url value='/resources/html/index.html'/>" target="main">首页</a>
	<a class="menuitem" href="<c:url value='/mentity/list'/>" target="main">实体管理</a>
	<a class="menuitem" href="<c:url value='/misgroup/list'/>" target="main">群组管理</a>
	<a class="menuitem" href="<c:url value='/misuser/list'/>" target="main">管理员管理</a>
	<a class="menuitem" href="<c:url value='/mispriv/list'/>" target="main">权限管理</a>
	<a class="menuitem" href="<c:url value='/feedback/thread/prequery'/>" target="main">反馈管理</a>
	<a class="menuitem" href="<c:url value='/jsp/message/send'/>" target="main">给用户发消息</a>
	<a class="menuitem" href="<c:url value='/jsp/mcache/mcache'/>" target="main">缓存管理</a>
	<a class="menuitem" href="<c:url value='/jsp/stat/stat'/>" target="main">统计查询</a>
	<a class="menuitem" href="<c:url value='/resources/html/awstats/awstats.flighttycoon.html'/>" target="_blank">awstats统计</a>
	<c:forEach var="mentity" items="${mentities}">
		<c:choose>
		<c:when test="${mentity.isSysconfig eq 1}">
			<a class="menuitem" href="<c:url value='/gameentity/sysentity/edit?mentity_id=${mentity.id}'/>" target="main">${mentity.entityName}</a>
		</c:when>
		<c:otherwise>
			<a class="menuitem" href="<c:url value='/gameentity/prequery?mentity_id=${mentity.id}'/>" target="main">${mentity.entityName}</a>
		</c:otherwise>
		</c:choose>
	</c:forEach>
</div>
</body>
<script src="<c:url value='/resources/bootstrap/docs/assets/js/jquery.js'/>"></script>
<script src="<c:url value='/resources/bootstrap/docs/assets/js/bootstrap-transition.js'/>"></script>
<script src="<c:url value='/resources/js/ddaccordion.js'/>"></script>
<script src="<c:url value='/resources/js/beauty.js'/>"></script>