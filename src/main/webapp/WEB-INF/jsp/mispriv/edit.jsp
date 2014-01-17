<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
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
<form id="form1" action="<c:url value='/mispriv/edit'/>" method="post">
<input type="hidden" value="${priv.id}" name="id" />
<table class="list">
<tr class="title"><td colspan="2">修改权限</td></tr>
<tr><td>权限名称:</td><td><input type="text"  name="name" value="${priv.name}" class="input-medium focused fuck"/></td></tr>
<tr><td>权限URL:</td><td><input type="text" name="url" value="${priv.url}" class="input-medium focused fuck"></input></td></tr>
<tr><td>权限所在位置:</td><td><input type="text" name="position" value="${priv.position}" class="input-small focused fuck"></input></td></tr>
<tr><td>对应管理实体ID:</td><td><input type="text" name="mentity_id" value="${priv.mentityId}" class="input-medium focused fuck"></input></td></tr>
<tr>
			<td>父目录</td>
			<td>
			<select name="parentNavId">
				<option value="0" <c:if test="${priv.parentNavId eq 0 }">selected</c:if>>无</option>
				<c:forEach var="item" items="${sessionScope.parentNavs}">
						<option value="${item.idx}" <c:if test="${priv.parentNavId eq item.idx}">selected</c:if>>${item.name}</option>
				</c:forEach>
			</select>
			</td>
</tr>
<tr><td colspan="2"> <input type="submit" class="btn btn-success" value="提交"></input></td></tr>
</table>
</form>


</div>


</div>
</body>
</html>