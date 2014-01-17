<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/default.css'/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/jquery-ui.css'/>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link
	href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet"
	rel="stylesheet" />
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery-ui.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/cvi_busy_lib.js'/>"></script>
<script>
	$(function() {
		function Dynamic(name, mentityId, funcOrUrl, isReplace, text) {
			this.name = name;
			this.mentityId = mentityId;
			this.funcOrUrl = funcOrUrl;
			this.isReplace = isReplace;
			this.text = text;
		}

		var dynamics = new Array(new Dynamic("dataSource",
				"playercenter.model.GameServerInfo",
				'javascript:refreshDataSource()', 'false', '刷新数据源信息'),
				new Dynamic("deviceBlacklist",
						"com.youai.gs.dzm.model.DeviceBlacklist",
						"javascript:flushCache('device_blacklist')", 'false',
						'清除缓存'));
		//alert('size:' + dynamics.length);

		var entityClass = $("#entity_class").val();

		for ( var i = 0; i < dynamics.length; i++) {
			if (entityClass == null || entityClass == "") {
				return;
			}
			if (entityClass == dynamics[i].mentityId) {
				var text = dynamics[i].text;
				var funcOrUrl = dynamics[i].funcOrUrl;
				if (dynamics[i].isReplace == 'true') {
					$("#add_oper").attr("href", funcOrUrl).html(text);
				} else {
					var element = "<a class='btn btn-danger' style='float:left;margin-left:20px;color: #fff' href=" + funcOrUrl + " >"
							+ text + "</a>";
					$("#add_oper").after(element);
				}
				//return;
			}
		}

	});
	
	
	/**
	 * 刷新服务器列表
	 */
	function refreshDataSource() {
		block_viewport();
		$.post("<c:url value='/debris/refreshdatasource'/>", function(data) {
			xval.remove();
			alert(data);
		});
	}

	function flushCache(cacheKey) {

		$.post("<c:url value='/mcache/delete/key'/>", {
			"cache_key" : cacheKey
		}, function(data) {
			alert(data);
		});
		return false;
	}
</script>
</head>
<body class="main">

	<c:if
		test="${sessionScope.currentPriv != null and sessionScope.currentPriv.addPriv == 1}">
		<div class="action-panel">
			<a id="add_oper" class="btn btn-primary"
				style="float: left; color: #fff"
				href="<c:url value='/gameentity/preadd?mentity_id=${mentity_id}'/>">增加新实体</a>
		</div>
	</c:if>
	<div class="query-table">
		<form action="<c:url value='/gameentity/query'/>" method="get">
			<input type="hidden" value="<c:out value='${mentity_id}' />"
				name="mentity_id" id="mentity_id" /> <input type="hidden"
				value="<c:out value='${mentity.entityClass}' />" name="entity_class"
				id="entity_class" />
			<table class="list">
				<tr>
					<td class="name">选择查询条件1：</td>
					<td class="value"><select name="query_field">
							<option></option>
							<option value="<c:out value='${mentity.idField.name}'/>"><c:out
									value='${mentity.idField.nickname}' /></option>
							<c:forEach var="field" items="${requestScope.mentity.fields}">
								<c:if test="${field.name!='serverIdx'}">
									<option value="<c:out value='${field.name}'/>">
										<c:out value='${field.nickname}' /></option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>


				<tr>

					<td>查询值:</td>
					<td><input value="" name="query_value"></input></td>
				</tr>

				<tr>
					<td class="name">选择查询条件2：</td>
					<td class="value"><select name="query_field2">
							<option></option>
							<option value="<c:out value='${mentity.idField.name}'/>"><c:out
									value='${mentity.idField.nickname}' /></option>
							<c:forEach var="field2" items="${requestScope.mentity.fields}">
								<c:if test="${field2.name!='serverIdx'}">
									<option value="<c:out value='${field2.name}'/>">
										<c:out value='${field2.nickname}' /></option>
								</c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>查询值:</td>
					<td><input value="" name="query_value2"></input></td>
				</tr>

				<tr></tr>
				<td class="name">选择比较条件1：</td>
				<td class="value"><select name="compare_field1">
						<option></option>
						<option value="<c:out value='${mentity.idField.name}'/>"><c:out
								value='${mentity.idField.nickname}' /></option>
						<c:forEach var="field1" items="${requestScope.mentity.fields}">
							<c:if test="${field1.name!='serverIdx'}">
								<option value="<c:out value='${field1.name}'/>">
									<c:out value='${field1.nickname}' /></option>
							</c:if>
						</c:forEach>
				</select></td>
				</tr>
				<tr>
					<td></td>
					<td><select name="compare_type1"><option value="0">大于</option>
							<option value="1">大于等于</option>
							<option value="2">小于</option>
							<option value="3">小于等于</option></select></td>
				</tr>
				<tr>
					<td>比较值:</td>
					<td><input value="" name="compare_value1"></input></td>
				</tr>

				<td class="name">选择比较条件2：</td>
				<td class="value"><select name="compare_field2">
						<option></option>
						<option value="<c:out value='${mentity.idField.name}'/>"><c:out
								value='${mentity.idField.nickname}' /></option>
						<c:forEach var="field2" items="${requestScope.mentity.fields}">
							<c:if test="${field2.name!='serverIdx'}">
								<option value="<c:out value='${field2.name}'/>">
									<c:out value='${field2.nickname}' /></option>
							</c:if>
						</c:forEach>
				</select></td>
				</tr>
				<tr>
					<td></td>
					<td><select name="compare_type2"><option value="0">大于</option>
							<option value="1">大于等于</option>
							<option value="2">小于</option>
							<option value="3">小于等于</option></select></td>
				</tr>
				<tr>
					<td>比较值:</td>
					<td><input value="" name="compare_value2"></input></td>
				</tr>

				<tr></tr>
				<tr>
					<td class="name">排序：</td>
					<td class="value"><select name="sort_field1">
							<option value="<c:out value='${mentity.idField.name}'/>"><c:out
									value='${mentity.idField.nickname}' /></option>
							<c:forEach var="field" items="${requestScope.mentity.fields}">
								<option value="<c:out value='${field.name}'/>">
									<c:out value='${field.nickname}' /></option>
							</c:forEach>
					</select> &nbsp; <select name="sort_type1"><option value="0">升序</option>
							<option value="1">降序</option></select> 排序</td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" class="btn btn-success"></input></td>
				</tr>
			</table>
		</form>
	</div>
	<div style="visibility: hidden;">
		<!-- 区域表单信息 -->
		<div id="dialog-form"
			class="ui-widget ui-widget-content ui-corner-all" title="创建验证码">
			<p id="login_tip"></p>
			<form id="login-form" action="<c:url value='/debris/createcode'/>">
				<table>
					<tr>
						<td>创建数目:<input type="text" name="amount" id="name"
							class="text ui-widget-content ui-corner-all" />
						</td>
					</tr>
					<tr>
						<td>期数:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"
							name="season" id="password" value=""
							class="text ui-widget-content ui-corner-all" />
						</td>
					</tr>
					<tr>
						<td>清除往期记录&nbsp;&nbsp;<input type="checkbox" name="clean"
							value="1" class="text ui-widget-content ui-corner-all" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>