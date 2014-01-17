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
<tr><td colspan="2" >账号信息</td></tr>
<c:choose>
<c:when test="${!empty player }">
<tr><td class="name">id</td><td class="value"><c:out value="${player.idx}"/></td></tr>
<tr><td>youaiId:</td><td ><c:out value="${player.youaiId}"/></td></tr>
<tr><td>userName:</td><td ><c:out value="${player.userName}"/></td></tr>
<tr><td>注册时间:</td><td ><c:out value="${player.registTime}"/></td></tr>
<tr><td>用户类型:</td><td ><c:out value="${player.type}"/></td></tr>
<tr><td>渠道:</td><td><c:out value="${player.channel}"/></td></tr>
<tr><td>deviceId:</td><td ><c:out value="${player.deviceId}"/></td></tr>
<tr><td>email:</td><td ><c:out value="${player.email}"/></td></tr>
<tr><td>guestBound:</td><td ><c:out value="${player.guestBound}"/></td></tr>
<tr><td>youaiCoin:</td><td ><c:out value="${player.youaiCoin}"/></td></tr>
</c:when>
<c:otherwise>
<tr><td>非自有账号用户，无法查询账号信息！</td></tr>
</c:otherwise>
</c:choose>
</table>
</div>
</div>
</body>
</html>
