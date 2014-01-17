<%@page pageEncoding="UTF-8" isELIgnored="false" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
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
		$("#form1").submit(
				function() {
					$.post("<c:url value='/mentity/edit'/>", $('#form1')
							.serialize(), function(data) {
						alert(data);
					});
					return false;
				});
		$(".type-select").change(function() {
			$(this).parent().next().css("display", "none");
			$(this).parent().next().next().css("display", "none");
			$(this).parent().next().next().next().css("display", "none");
      	    $(this).parent().next().next().next().next().css("display", "none");
			if ($(this).attr("value") == "1" || $(this).attr("value") == "4" || $(this).attr("value") == "6") {
				$(this).parent().next().css("display", "inline");
			} else if ($(this).attr("value") == "2") {
				$(this).parent().next().next().css("display", "inline");
			}else if( $(this).attr("value") == "5" ) {
       			var itemName = $(this).attr("name");
                itemName = itemName.substring(0, itemName.indexOf('_'));
                //alert("change test:" + itemName);
				
           		//将级联的input选项append进去
				/*var option_html = "";
       			$.each(data, function(i,option){
                   	 if(option.name != itemName){
                   		 option_html = option_html + "<option value='" + option.name + "'>" + option.name + "</option>";
                   	 }
           	 	});
           	 	//alert("option_text:" + option_html);
       			$("select[name='" + itemName + "_ajax_related_input" + "']").html("");
       			$("select[name='" + itemName + "_ajax_related_input" + "']").append(option_html);*/
           	 	
				$(this).parent().next().css("display", "inline");
				$(this).parent().next().next().next().css("display", "inline");
				$(this).parent().next().next().next().next().css("display", "inline");
           	}
		});
	});
</script>
</head>
<body class="main">
	<form id="form1" method="post">
		<input type="hidden" value="<c:out value='${mentity.id}'/>"
			name="entity_id" />
		<div class="item-info">
			<table class="list" style="min-width:730px;width:730px;">
				<tr>
					<td class="name">管理的实体名:</td>
					<td class="value"><input name="entityName" type="text" class="input-medium focused fuck"
						value="<c:out value='${mentity.entityName}'/>"></input></td>
				</tr>
				<tr>
					<td>实体的Model定义路径：</td>
					<td><input name="entityClass" type="text" class="input-medium focused fuck"
						value="<c:out value='${mentity.entityClass}'/>"></input></td>
				</tr>
				<tr>
					<td>实体描述信息</td>
					<td><input name="entityDesc" type="text" class="input-medium focused fuck"
						value="<c:out value='${mentity.comment}'/>"></input></td>
				</tr>
				<tr>
					<td>是否系统配置</td><td><input type="checkbox"
						name="isSystemConfig"
						<c:if test="${mentity.isSysconfig eq 1}">checked</c:if> /></td>
				</tr>
				<tr>
					<td>扩展链接</td>
					<td><input type="text" class="input-xxlarge focused fuck"
						name="extendLink" value="<c:out value='${mentity.extendLink}'/>"></td>
				</tr>
				<tr>
					<td colspan="2">父目录
					<select name="parentNavId">
						<option value="0" <c:if test="${mentity.parentNavId eq 0 }">selected</c:if>>无</option>
						<c:forEach var="item" items="${requestScope.parentNavs}">
 							<option value="${item.idx}" <c:if test="${mentity.parentNavId eq item.idx}">selected</c:if>>${item.name}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			</table>
		</div>
		<div class="item-table">
			<div id="model_table" class="mentity_add_div">
				<c:forEach var="item" items="${mentity.fields}">
					<div class="item">
						<div class="name">
							<span>${item.name}:</span><input type='text' name="${item.name}" class="input-medium focused fuck"
								value="${item.nickname}"></input> <input type="checkbox"
								name="${item.name}_unmodified"
								<c:if test="${item.unmodified eq 1}">checked</c:if>>不可修改</input>
							显示顺序:<input type="text" name="${item.name}_num" class="input-mini focused fuck"
								value="${item.num}"></input>
						</div>
						<div class="type">
							<span>类型：</span><select class="type-select"
								name="${item.name}_type">
								<option value='1'
									<c:if test="${item.valueUitype eq 1}">selected</c:if>>枚举</option>
								<option value='2'
									<c:if test="${item.valueUitype eq 2}">selected</c:if>>日期时间</option>
								<option value='3'
									<c:if test="${item.valueUitype eq 3}">selected</c:if>>多行文本</option>
								<option value='4'
									<c:if test="${item.valueUitype eq 4}">selected</c:if>>自定义枚举</option>
								<option value='5'
									<c:if test="${item.valueUitype eq 5}">selected</c:if>>ajax枚举</option>
								<option value='6'
									<c:if test="${item.valueUitype eq 6}">selected</c:if>>multi枚举</option>
								<option value='7'
									<c:if test="${item.valueUitype eq 7}">selected</c:if>>时间戳</option>
								<option value='0'
									<c:if test="${item.valueUitype eq 0}">selected</c:if>>其他</option>
							</select>
						</div>
						<div class="value"
							<c:if test="${item.valueUitype  != 1 and item.valueUitype != 4 and item.valueUitype != 5 and item.valueUitype != 6}">style="display:none"</c:if>>
							<span>值：</span><input class="wide-item-value"
								name="${item.name}_values" value="${item.fieldValues}" />
						</div>
						<div class="value"
							<c:if test="${item.valueUitype != 2}">style="display:none"</c:if>>
							<span>是否采用当前时间：</span> <input type="checkbox"
								class="wide-item-value" name="${item.name}_takecurtime"
								class="wide-item-value"
								<c:if test="${item.valueUitype == 2 and item.fieldValues eq 1}">checked</c:if> />
						</div>
						<div class="value"
							<c:if test="${item.valueUitype  != 5}">style="display:none"</c:if>>
							<span>ajax关联输入框：</span> 
							<select name="${item.name}_ajax_related_input">
								<c:forEach var="option" items="${mentity.fields}">
									<c:if test="${option.name != item.name }">
										<option value="${option.name }" <c:if test="${option.name==item.ajaxRelatedInput }">selected</c:if>>${option.name }</option>
									</c:if>
								</c:forEach>
							</select>
							<span>ajaxUrl：</span>
							<input name="${item.name}_ajax_url" value="${item.ajaxUrl }"/>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="clear"></div>
		</div>
		<div id="submit" class="clear">
			<input type="submit" class="btn btn-success" value="修改" id="submitButton" style="margin-left:17%;float:left"></input>
		</div>
	</form>
</body>
</html>