<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/jquery-ui.css'/>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet"/>
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet" rel="stylesheet"/>
<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-ui-timepicker-addon.js'/>" ></script>
<script type="text/javascript">
           $(function() {
        	   $('.datebox').datetimepicker({
        		    currentText: 'Now',
        		    dateFormat: 'yy-mm-dd',
        				showSecond: true,
        			dateFormate: 'yyyy-MM-dd',
        			timeFormat: ' hh:mm:ss'
        		});
        	   
          });
</script> 
</head>
<body class="main">
<div class="action-panel">
<div class="query-table">
<form action="<c:url value='/feedback/thread/query'/>" method="get">
<table class="list">
<tr>
<td>应用名称:</td>
<td>
<select name="game_id">
	<option value=''>全部</option>
	<c:forEach var="game" items="${games}">
		<option value='${game.appKey }'>${game.appName }</option>
	</c:forEach>
</select>
</td>
</tr>
<tr>
<td>vip等级:</td><td>
<select name="vip_level">
	<option value='-1'>全部</option>
	<option value='0'>小R（vip1-3）</option>
	<option value='1'>中R（vip4-6）</option>
	<option value='2'>大R（vip7-9）</option>
	<option value='3'>超R（vip10-13）</option>
</select>
</td>
</tr>
<tr>
<td class="name">查找角色：</td>
<td class="value"><select name="query_field">
<option></option>
<option value="player_idx">角色idx</option>
<option value="player_name">角色名字</option>
</select>
</td>
</tr>
<tr>
<td>查询值:</td><td><input value="" name="query_value" class="input-medium focused fuck"></input></td>
</tr>
<tr><td>反馈开始时间:</td><td>从<input type="text" class="input-medium focused fuck datebox" name="begin_created_at" /></td></tr>
<tr><td>反馈结束时间:</td><td>至<input type="text" class="input-medium focused fuck datebox" name="end_created_at" /></td></tr>
<tr>
<td class="name">排序：</td>
<td class="value"><select name="sort_field1">
<option value="created_at">创建时间</option>
<option value="updated_at">更新时间</option>
</select>
&nbsp;
<select name="sort_type1"><option value="asc">升序</option><option value="desc" selected>降序</option></select>
排序
</td>
</tr>
<tr>
<td class="name">反馈状态：</td>
<td class="value">
<select name="feedback_status">
<option value="-1" >所有反馈</option>
<option value="0" selected>正常反馈</option>
<option value="1">已忽略反馈</option>
<option value="2">已关闭反馈</option>
<option value="3">重新打开反馈</option>
</select></td>
</tr>
<tr>
<td class="name">反馈类型：</td>
<td class="value">
<select name="feedback_type">
	<option value="0">全部</option>
	<c:forEach var="item"  items="${feedback_types}"   >
		<option value="${item.id}">${item.title}</option>
	</c:forEach>
	</select>
</td>
</tr>
<tr>
<td class="name">是否回复：</td>
<td class="value">
<select name="replied">
	<option value="-1">ALL</option>
	<option value="0">No</option>
	<option value="1">YES</option>
</select>
</td>
</tr>
<tr><td>最后操作人</td><td><input name="last_op_admin" class="input-medium focused fuck"></input></td></tr>
<tr>
<td colspan="2"> <input type="submit" class="btn btn-success"></input></td></tr>
</table>
</form>
</div>
</div>
</body>
</html>