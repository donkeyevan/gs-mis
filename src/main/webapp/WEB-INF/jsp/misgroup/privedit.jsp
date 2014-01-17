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
<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript"> 
$(function() {
	$("#selectall").click( function(){
		$('.priv_ids_checkbox').attr("checked",'true');
	});

	$('#cancel_selectall').click( function(){
		$('.priv_ids_checkbox').removeAttr("checked");;
	});


});

</script>
</head>
<body class="main">
<h2 class="title" style="margin-left: 5%;">为群组[<c:out value="${group.name}"/>]分配权限</h2>
<form action="<c:url value='/misgroup/privedit'/>" method="post" >
<input type="hidden" name="group_id" value="<c:out value='${group.id}'/>"/>
<table class="list1" style="width:90%;margin: 10px 5%;">
<tr class="title">
<td>是否分配</td>
<td>权限名称</td>
</tr>
<c:forEach var="item" items="${privs}" >
<tr class="body">
<td>
查询:<input type="checkbox" name="priv_ids" <c:if test="${item.queryPriv eq 1}">checked</c:if> value="${item.id}_query" class="priv_ids_checkbox"></input>
增加:<input type="checkbox" name="priv_ids" <c:if test="${item.addPriv eq 1}">checked</c:if> value="${item.id}_add" class="priv_ids_checkbox"></input>
删除:<input type="checkbox" name="priv_ids" <c:if test="${item.deletePriv eq 1}">checked</c:if> value="${item.id}_delete" class="priv_ids_checkbox"></input>
修改:<input type="checkbox" name="priv_ids" <c:if test="${item.modifyPriv eq 1}">checked</c:if> value="${item.id}_modify" class="priv_ids_checkbox"></input>
</td>
<td>${item.name}</td>
</tr>
</c:forEach>

<tr><td colspan="2">
<input type="button" class="btn btn-small btn-info" id="selectall" value="全选"/>
<input type="button" class="btn btn-small btn-warning" id="cancel_selectall" value="取消全选"/>
<input type="submit" class="btn btn-small btn-success" value="提交"/>
</td></tr>
</table>
</form>
</body>
</html>
