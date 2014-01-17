<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet"/>
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet" rel="stylesheet"/>
</head>
<body class="main">
<div class="row" style="text-align:right;">
<a class="btn btn-success" style="color:#fff;margin:6px" href="<c:url value='/misuser/preadd'/>">新增</a>
<div class="clear"></div>
</div>
<table class="list">
<tr class="title">
<td>管理员名字</td>
<td>创建人</td>
<td>创建日期</td>
<td>操作</td>
</tr>
<c:forEach var="item" items="${users}" >
<tr class="body">
<td>${item.name}</td>
<td>${item.createBy}</td>
<td>${item.createAt}</td>
<td>
<a class="action" href="<c:url value='/misuser/preedit?id=${item.id}'/>">编辑</a>
<a class="action" href="<c:url value='/misuser/delete?id=${item.id}'/>">删除</a>
</td>
</tr>
</c:forEach>
</table>
</body>
</html>