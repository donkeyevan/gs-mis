<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
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
<title>Manage</title>
</head>
<c:if test="${empty sessionScope.is_login_key or sessionScope.is_login_key eq 0}">
<script>
	window.location.replace( "<c:url value='/jsp/user/login'/>");
</script>
</c:if>
<frameset rows="60,849,45" rows="*" frameborder="NO" border="0" >

<frame id="header" src="<c:url value='/jsp/common/header'/>" scrolling="no" name="header" noresize />

<frameset cols="180,*" cols="*" frameborder="NO" border="0">
	<frame id="menu" src="<c:url value='/jsp/common/left'/>" name="left" noresize />
	<frameset rows="40,*" cols="*" frameborder="NO" border="0">
		<frame id="rightheader" src="<c:url value='/jsp/common/rightheader'/>" name="rightheader" noresize />
		<frame id="body" src="<c:url value='/resources/html/index.html'/>" scrolling="auto" name="main" />
	</frameset>
</frameset>
<frame id="footer" src="<c:url value='/jsp/common/footer'/>" scrolling="no" name="footer" noresize />
</frameset>
<script src="<c:url value='/resources/bootstrap/docs/assets/js/jquery.js'/>"></script>
<script src="<c:url value='/resources/bootstrap/docs/assets/js/bootstrap-transition.js'/>"></script>
</html>