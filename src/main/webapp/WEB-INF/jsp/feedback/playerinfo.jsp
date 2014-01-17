<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
</head>
<body class="frame">
<div class="action-panel">
<div class="edit">
<table>
	
<tr><td colspan="2" >角色信息</td></tr>
<c:choose>
<c:when test="${!empty player }">
<tr><td class="name">id</td><td class="value"><c:out value="${player.idx}"/></td></tr>
<tr><td>puid:</td><td><c:out value="${player.youaiId}"/></td></tr>
<tr><td>gameId:</td><td><c:out value="${player.gameId}"/></td></tr>
<tr><td>platformId:</td><td><c:out value="${player.platformId}"/></td></tr>
<tr><td>serverId:</td><td><c:out value="${player.serverId}"/></td></tr>
<tr><td>playerId:</td><td><c:out value="${player.playerId}"/></td></tr>
<tr><td>角色名称:</td><td><c:out value="${player.playerName}"/></td></tr>
<tr><td>贝里:</td><td><c:out value="${player.gameCoin1}"/></td></tr>
<tr><td>钻石:</td><td><c:out value="${player.gameCoin2}"/></td></tr>
<tr><td>vip等级:</td><td><c:out value="${player.vipLvl}"/></td></tr>
<tr><td>用户等级:</td><td><c:out value="${player.playerLvl}"/></td></tr>
<tr><td>设备:</td><td><c:out value="${player.deviceId}"/></td></tr>
<tr><td>上次登录:</td><td><c:out value="${player.lastLoginTime}"/></td></tr>
<tr><td>注册时间:</td><td><c:out value="${player.registTime}"/></td></tr>
<tr><td>最后登录:</td><td><c:out value="${player.updateTime}"/></td></tr>
</c:when>
<c:otherwise>
<tr><td>无法查询此人角色信息，客户端push开关关闭，请联系管理员！</td></tr>
</c:otherwise>
</c:choose>
</table>
</div>
</div>
</body>
</html>
