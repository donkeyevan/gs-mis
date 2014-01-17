<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/jquery-ui.css'/>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet"/>
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet" rel="stylesheet"/>
<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript">

</script>
</head>
<body class="main">
<div class="action-panel">
<div class="query-table">
<form id="form1" action="<c:url value='/misuser/add'/>" method="post">
<table id="info" class="list">
<tr><td colspan="2"></td></tr>
<tr><td>用户ID:</td><td><input type="text"  name="name" class="input-medium focused fuck"/></td></tr>
<tr><td>所属群组:</td><td>
<c:forEach var="item" items="${groups}">
<input type="checkbox" name="group_ids" value="${item.id}" id ="groupids"/>${item.name} &nbsp;&nbsp;
</c:forEach>
</td></tr>
<tr id="show"></tr>
<tr><td colspan="2"> <input type="submit" class="btn btn-success" value="提交"></input></td></tr>
</table>
</form>
</div>


</div>
</body>
</html>