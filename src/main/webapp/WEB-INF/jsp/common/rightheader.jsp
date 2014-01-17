<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/default.css'/>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet" rel="stylesheet">
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript">
	function defaultServerChange(){
       	$.post($('#select_form').attr("action"), $('#select_form').serialize(),
			function(data) {
	            if(data==1){
					alert("设置成功");
					//window.parent.parent.parent.location.reload();
				}else if(data==0) {
					alert("清除成功");
					//window.parent.parent.parent.location.reload();
				}else{
					alert("操作失败");
				}
		});
		return false;
	}
</script>
<body class="main">
<div style="float: left; min-width: 100px; height: 15px">
	<form id="select_form" action="<c:url value='/server/select'/>"	method="post">
		<font>当前选择的默认服务器：</font>
		<select id="serverIdx" name="serverIdx" onchange="defaultServerChange()">
			<c:forEach var="server" items="${sessionScope.servers}">
				<option value="${server.idx}" <c:if test="${server.idx eq sessionScope.default_server_idx}">selected</c:if>>${server.name}</option>
			</c:forEach>
		</select>
	</form>
</div>
</body>
