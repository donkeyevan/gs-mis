<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@page import="com.youai.gamemis.model.MisPrivilege"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/default.css'/>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet"/>
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet" rel="stylesheet"/>
<script type="text/javascript">
	function pagechange(index) {
		var url = "<c:url value='/gameentity/query?'/>";//原始的地址
		url = url
				+ "mentity_id=<c:out value='${mentity_id}'/>&sort_field1=<c:out value='${sort_field1}'/>"
				+ "&sort_type1=<c:out value='${sort_type1}'/>&query_field=<c:out value='${query_field}'/>"
				+ "&query_value=<c:out value='${query_value}'/>";
		//使用正则达式，假定当前页参数在最后一个，且形如page=4
		var regExp = /(S*page=)(S*)/;
		var arr = regExp.exec(url);//使用正则表达式分析页面的地址，使用分析结果调整页码
		if (arr == null)
			url = url + "&pn=" + index;
		else
			url = arr[1] + index;
		//alert(url);
		window.location = url;
	}
</script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/unix-stamp-format.js'/>"></script>
<body class="main">
<div class="query-list">
	<table class="list" id="countdata">

		<tr class="title">
			<td>时间</td>
			<td>渠道</td>
			<td>总设备数</td>
			<td>新增设备</td>
			<td>充值总数</td>
			<td>充值用户数</td>
			<td>付费率</td>
			<td>arpu</td>
			<td>arppu</td>
			<td>dau</td>
			<td>一日留存</td>
			<td>二日留存</td>
			<td>三日留存</td>
			<td>七日留存</td>
			<td>十五日留存</td>
			<td>三十日留存</td>
		</tr>

		<c:forEach var="item" items="${dataList}" begin="0">
			<tr class="body">
				<td>${fn:substring(item.stamp, 0, 10)}</td>
				<td>
				<c:forEach items="${channels}" var="channels">
					<c:if test="${channels.idx == item.channel}">${channels.name }</c:if>
				</c:forEach>
				</td>
				<td>${item.deviceCount}</td>
				<td>${item.newDevice }</td>
				<td>${item.sumMoney }</td>
				<td>${item.rechargeUsers }</td>
				<td>${model eq 0 ? item.payRate : '---' }</td>
				<td>${model eq 0 ? item.arpu : '---' }</td>
				<td>${model eq 0 ? item.arppu : '---' }</td>
				<td>${model eq 0 ? item.dau : '---' }</td>
				<td>${model eq 0 ? item.stay1 : '---' }</td>
				<td>${model eq 0 ? item.stay2 : '---' }</td>
				<td>${model eq 0 ? item.stay3 : '---' }</td>
				<td>${model eq 0 ? item.stay7 : '---' }</td>
				<td>${model eq 0 ? item.stay15 : '---' }</td>
				<td>${model eq 0 ? item.stay30 : '---' }</td>
			</tr>
		</c:forEach>

	</table>
</div>
</body>