<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/jquery-ui.css'/>" />
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
<body class="frame">
<div class="action-panel">
<div class="query-table">
<form action="<c:url value='/stat/player'/>" method="get">
<table>
<tr><td colspan="2">用户统计查询</td></tr>
<tr><td>统计开始时间:</td><td><input type="text" class="datebox" name="begin_date" /></td></tr>
<tr><td>统计结束时间:</td><td><input type="text" class="datebox" name="end_date" /></td></tr>
<tr>
<td colspan="2"> <input type="submit" ></input></td></tr>
</table>
</form>
</div>
<div class="query-table">
<form action="<c:url value='/stat/payment'/>" method="get">
<table>
<tr><td colspan="2">玩家付费统计总表查询</td></tr>
<tr><td>统计类别:</td><td><select name="stat_type">
<option value="all" selected>综合付费查询</option>
<option value="payuser">付费人数查询</option>
</select></td></tr>
<tr><td>统计开始时间:</td><td><input type="text" class="datebox" name="begin_date" /></td></tr>
<tr><td>统计结束时间:</td><td><input type="text" class="datebox" name="end_date" /></td></tr>
<tr>
<td colspan="2"> <input type="submit" ></input></td></tr>
</table>
</form>

</div>


</div>
</body>
</html>