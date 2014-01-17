<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/jquery-ui.css'/>" />
<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript">
$().ready(function() {
	$("#form1").submit(
			function() {
				var url = $('#form1').attr("action");
				$.post(url, $('#form1').serialize(),
						function(data) {
							if(data==1){
								alert("设置成功");
								window.parent.parent.parent.location.reload();
							}else if(data==0) {
								alert("清除成功");
								window.parent.parent.parent.location.reload();
							}else{
								alert("操作失败");
							}
						});
				return false; //防止重复提交
			});
});
          
          
</script> 
</head>
<body class="frame">
<div class="action-panel">
<div class="query-table">
<form id="form1" action="<c:url value='/server/select'/>" method="post">
<table>
<tr><td colspan="2"></td></tr>
<tr><td>选择默认操作服务器:</td><td><select name="serverIdx">
<option value="0">无</option>
<c:forEach var="server" items="${sessionScope.servers}">
	<option value="${server.serverIdx}" <c:if test="${server.serverIdx eq sessionScope.default_server_idx}">selected</c:if>>${server.name}</option>
</c:forEach>
</select></td></tr>

<tr><td colspan="2"> <input type="submit" value="提交"></input></td></tr>
</table>
</form>


</div>


</div>
</body>
</html>