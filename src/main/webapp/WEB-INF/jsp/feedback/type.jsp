<%@ page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript">
</script>
</head>
<body>
<form id="form" method="post" action="<c:url value='/feedback/thread/type'/>?thread_id=<c:out value='${thread_id}'/>">
<table width="300">
<tr><td colspan="2" >为反馈ID为：<c:out value="${thread_id}"/>的反馈分类</td></tr>
<tr>
	<td width="100">反馈分类</td>
	<td >
	<select name="type_id">
	<c:forEach var="item"  items="${feedback_types}"   >
		<option value="${item.id}">${item.title}</option>
	</c:forEach>
	</select>
</td>
</tr>
<tr><td colspan="2"><input type="submit" value="提交"></input></td></tr>
</table>
</form>

</body>
</html>