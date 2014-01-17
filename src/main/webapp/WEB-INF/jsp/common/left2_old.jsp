<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>

<table class="menu">
<tr><td><a name="menu-item" class="selected" href="<c:url value='/resources/html/index.html'/>" target="main">首页</a></td></tr>
<c:if test="${sessionScope.login_game_admin.isSuperAdmin eq 1}">
<tr><td><a name="menu-item" class="normal" href="<c:url value='/mentity/list'/>" target="main">实体管理</a></td></tr>
<tr><td><a name="menu-item" class="normal" href="<c:url value='/misgroup/list'/>" target="main">群组管理</a></td></tr>
<tr><td><a name="menu-item" class="normal" href="<c:url value='/misuser/list'/>" target="main">管理员管理</a></td></tr>
<tr><td><a name="menu-item" class="normal" href="<c:url value='/mispriv/list'/>" target="main">权限管理</a></td></tr>
</c:if>
<c:forEach var="priv" items="${sessionScope.mis_shard_admin_priv}">
<tr><td><a name="menu-item" class="normal" href="${priv.realUrl}" target="main">${priv.name}</a></td></tr>
</c:forEach>
<c:forEach var="priv" items="${sessionScope.mis_config_admin_priv}">
<tr><td><a name="menu-item" class="normal" href="${priv.realUrl}" target="main">${priv.name}</a></td></tr>
</c:forEach>
</table>
<script type="text/javascript"><!--
	$("a[name='menu-item']").each(function() {
		$(this).click(function() {
			$("a[name='menu-item']").each(function(){$(this).attr("class", "normal");});
			$(this).attr("class", "selected");
		});
	});
--></script>


