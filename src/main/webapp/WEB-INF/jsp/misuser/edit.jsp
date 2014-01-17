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
<form id="form1" action="<c:url value='/misuser/edit'/>" method="post">
<input type="hidden" name="user_id" value="${user.id}" />
<table class="list">
<tr><td>管理员ID:</td><td>${user.name}</td></tr>
<tr><td>所属群组:</td><td>
<c:forEach var="item" items="${groups}">
<input type="checkbox" id='checkbox' name="group_ids" value="${item.id}" <c:if test="${item.belongTo eq 1}">checked</c:if> />${item.name} &nbsp;&nbsp;
</c:forEach>
</td></tr>

<tr><td colspan="2"> <input type="submit" class="btn btn-success" value="修改"></input></td></tr>
</table>
</form>


</div>


</div>
</body>
</html>