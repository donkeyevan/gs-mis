<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@page import="com.youai.gamemis.model.MisPrivilege"%>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/default.css'/>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet"/>
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet" rel="stylesheet"/>
<script type="text/javascript"
	src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript">
	$(function() {
		$('#ready').load('ready');
		$("a#deleteLink").click(
						function(event) {
							if (confirm("确认删除该记录，确认提交？") == false)
								return false;
							$.getJSON("<c:url value='/gameentity/delete?'/>"
													+ "mentity_id=<c:out value='${mentity_id}'/>&gameentity_id="
													+ $(event.target).attr(
															"href"),

											function(data) {
												if (data
														.hasOwnProperty('result')
														&& data.result == 1) {
													alert("Delete successfully!");
													window.location.reload();

												} else {
													alert(data);
												}
											});

							return false;
						});
	});
	
	function pagechange(index) {
		//var url = $('#form1').attr("action");
		//alert(location.href);
		var url = location.href;
		/*var regExp = /(S*pn=)(S*)/;
        var arr = regExp.exec(url);//使用正则表达式分析页面的地址，使用分析结果调整页码
        console.log(arr);
        if(arr==null)url = url+"&pn="+index;
        else url = arr[1]+index;*/
        var pnIndex = url.indexOf('pn=');
        if(pnIndex > 0){
        	url = url.substring(0, pnIndex + 3) + index;
        }else{
        	url = url + "&pn=" + index;
        }
        //alert(url);
		location.href = url;
	}
	$().ready(function(){
		if(window.parent.xval != null){
			window.parent.xval.remove();
		}
	});
</script>
<body>
	<div class="row" style="text-align:right;margin-right:1.2%;margin-top:0px">
		<c:if
			test="${sessionScope.currentPriv != null and sessionScope.currentPriv.addPriv == 1}">
			<a class="btn btn-success" style="color:#fff;margin-bottom:6px"
				href="<c:url value='/gameentity/preadd?'/>&mentity_id=<c:out value='${mentity_id}'/>">新增${fn:substring("hello", 0, 3) }</a>
		</c:if>
	<div class="clear"></div>
	</div>
	<div class="query-list">
		<table class="list">

			<tr class="title">
				<td>${pqinfo.mentity.idField.nickname}</td>
				<c:forEach var="field" items="${pqinfo.mentity.fields}">
					<td>${field.nickname}</td>
				</c:forEach>
				<td>自定义操作</td>
				<td>操作</td>
			</tr>

			<c:forEach var="item" items="${pqinfo.mentities}" begin="0">
				<tr class="body">
					<td>${item.idField.value}</td>
					<c:forEach var="field" items="${item.fields}">
						<td>
						<c:choose>
								<c:when test="${field.valueUitype eq 1 || field.valueUitype eq 5 || field.valueUitype eq (5 + 10)}">
									<script type="text/javascript">
									<!--	
									var options = jQuery
												.parseJSON('${field.fieldRealValues}');
										$.each(options, function(i, option) {

											var optionkey = option.key;
											var itemkey = '${field.value}';
											if (itemkey == optionkey) {
												document.write(option.value);
											}

										});
									//-->	
									</script>
								</c:when>
								<c:when test="${field.valueUitype eq 4}">
									${field.showValue}
								</c:when>
								<c:when test="${field.valueUitype eq 6}">
									<script type="text/javascript">
									<!--
										var options = jQuery
												.parseJSON('${field.fieldRealValues}');
										if(options != null || options != "" ){
											var textHtml = "";
											var ids = '${field.value }';
											ids = ids.split(",");
											$.each(	options,function(i, option) {
												if(option.key != 0 && ids.toString().indexOf(option.key) > -1){
													  textHtml+= option.value + ",";
												}
											});
											document.write(textHtml.substring(0, textHtml.length - 1));
										}
									//-->
									</script>

								</c:when>
								<c:when test="${field.valueUitype eq 7}">
									<script type="text/javascript">
									<!--
										var unixStamp = '${field.value }';
										document.write(date('Y-m-d H:i:s', unixStamp));
									//-->
									</script>

								</c:when>
								<c:otherwise>
								 	${field.value}
								</c:otherwise>
							</c:choose>
							</td>
					</c:forEach>
					<td>
						<c:if test="${sessionScope.currentPriv != null and sessionScope.currentPriv.modifyPriv == 1}">
							<c:if test="${item.extendLinkType != null}">
								<c:forEach var="elink" items="${item.extendLinkType.extendLinks}"
									begin="0">
									<a class="btn btn-info action" href="${elink.link}">${elink.word}</a> &nbsp;
	 							</c:forEach>
							</c:if>
						</c:if>
					</td>
					<td>
						<c:if test="${sessionScope.currentPriv != null and sessionScope.currentPriv.modifyPriv == 1}">
								<a class="btn btn-mini btn-info" style="margin:3px; color: #fff"
									href="<c:url value='/gameentity/preedit?'/>mentity_id=<c:out value='${mentity_id}'/>&entity_id=${item.idField.value}">编辑</a>
						  &nbsp;&nbsp;
						 </c:if> 
						 <c:if	test="${sessionScope.currentPriv != null and sessionScope.currentPriv.deletePriv == 1}">
								<a class="btn btn-mini btn-info" style="margin:3px;color: #fff" id="deleteLink" href="${item.idField.value}">删除</a>
						 </c:if>
				   </td>
				</tr>
			</c:forEach>

		</table>
		<div class="navigation">
			<c:set var="page_num" scope="page" value="${pqinfo.pageSum}" />
			<c:set var="row_count" scope="page" value="${pqinfo.rowCount}" />
			<c:set var="pn" scope="page" value="${pqinfo.pn}" />
			<c:set var="rn" scope="page" value="${pqinfo.rn}" />
			<center>
				总记录数：${row_count}；页数：${page_num}，当前页：${pn} <a
					href="javascript:pagechange(1)" title="首页">首页</a>&nbsp;&nbsp;
				<c:if test="${pn > 1}">
					<a href="javascript:pagechange(${pn-1})" title="前一页">前一页</a>&nbsp;&nbsp;
    </c:if>
				<c:if test="${(pn+0) < (page_num)}">
					<a href="javascript:pagechange(${pn+1})" title="下一页">下一页</a>&nbsp;&nbsp;
    </c:if>
				<a href="javascript:pagechange(${page_num})" title="尾页">尾页</a>
			</center>

		</div>
	</div>
	<script>
	$(window.parent.document).find("#iframe_id").css({height:document.body.scrollHeight+"px"})
	</script>
</body>