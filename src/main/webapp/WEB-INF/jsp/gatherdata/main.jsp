<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/default.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/jquery-ui.css'/>" />

<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet" rel="stylesheet">

<script type="text/javascript"
	src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-ui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-ui-timepicker-addon.js'/>"></script>

<script src="<c:url value='/resources/js/My97DatePicker/WdatePicker.js'/>"></script>
<script src="<c:url value='/resources/js/cvi_busy_lib.js'/>"></script>
<script type="text/javascript">
	function submitPost(url) {
		$("#iframe_id").attr("src", url + "&" + $('#form1').serialize());
		/*$.post(url, $('#form1').serialize(), function(data) {
			$("#data_list").html("");
			$("#data_list").html(data);
		});*/
		//location.href = url + "&" + $('#form1').serialize();
		return false; //防止重复提交
	}

	function pagechange(index) {
		var url = $('#form1').attr("action");
		url += "?pn=" + index;
		submitPost(url);
	}
	
	$().ready(function() {
		$("#form1").submit(function() {
			var url = $('#form1').attr("action");
			block_viewport();
			$("#iframe_id").attr("src", url + "?" + $('#form1').serialize());
			return false; //防止重复提交
		});
		$("#form1").submit();
	});
</script>
</head>
<body class="main">
	<div class="query-table" style="text-align: center;margin:10px 0;padding:0">
		<form id="form1" class="form-inline" action="<c:url value='/gatherdata/query'/>" style="height:37px;margin:0">
			<span class="inlinetitle">开始时间：</span>
			<input id="start" class="input-medium focused" name="start_date" value="${start_date}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'end\')}'});" readonly/> 
			<span class="inlinetitle">结束时间：</span>
			<input id="end" class="input-medium focused" name="end_date" value="${end_date}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'start\')}',maxDate:'%y-%M-%d-%H-%m-%s'});" readonly/>
			<c:if test="${empty priv }">
			<span class="inlinetitle">渠道：</span>
			<select name="channel">
				<option value="-1">----所有----</option>
				<c:forEach items="${channels}" var="item">
				<option <c:if test="${item.idx eq channel }">selected</c:if> value="${item.idx }" >${item.name }</option>
	            </c:forEach>
			</select>
			</c:if>
			<input type="submit" class="btn btn-primary" value="查询"/>
			<input type="hidden" name="mentity_id" value="${mentityId }"/>
		</form>
	</div>
	
	<div id="data_list" style="width: 100%;flaot:left">
	<iframe id="iframe_id" style='width:100%;' scrolling="atuo" src="" height="100%" width="100%" frameborder=0 scrolling="auto"></iframe>
		<%--@ include file="/WEB-INF/jsp/gatherdata/list.jsp"--%>
	</div>
	
</body>
</html>