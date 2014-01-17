<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>

<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet"/>
<link href="<c:url value='/resources/css/beauty.css'/>" rel="stylesheet" rel="stylesheet"/>
<script type="text/javascript">
$(function() {
$('#ready').load('ready');
$("#delform").submit( function(){ 
	
	$.post(
			 $('#delform').attr( "action" ),
			 $('#delform').serialize(),
	            function(data){
	                alert( data );
		            }
	 );
     return false;		
    });
$("#addform").submit( function(){ 
	
	$.post(
			 $('#addform').attr( "action" ),
			 $('#addform').serialize(),
	            function(data){
	                alert( data );
		            }
	 );
     return false;		
    });

$("#delallform").submit( function(){ 
	
	$.post(
			 $('#delallform').attr( "action" ),
			 $('#delallform').serialize(),
	            function(data){
	                alert( data );
		            }
	 );
     return false;		
    });


$("#deleteSubmit").click( function(){
	  	 if(confirm("清空所有cache操作很危险，确认提交？")) return true;
	     return false;
			
	});

$("#queryform").submit( function(){ 
	
	$.post(
			 $('#queryform').attr( "action" ),
			 $('#queryform').serialize(),
	         function(data){
	             	$('#query_value').attr("value",  data );
		     }
	 );
     return false;		
    });

});
 </script>
</head>
<body class="main">
<div style="row">
<div style="clear:left;width:500px;float:left;">
<form id="delform" action="<c:url value='/mcache/delete/key'/>" method="post">
<table  class="list" style="min-width:100%">
<tr><td colspan="2">清除缓存</td></tr>
<tr><td>需要清除缓存的key:</td><td><input type="text" name="cache_key" class="memcache" class="input-medium focused fuck"></input></td>
<tr><td colspan="2"><input type="submit" class="btn btn-warning" value="删除"/></td></tr>
</table>
</form>

<form id="delallform" action="<c:url value='/mcache/delete/all'/>" method="post">
<div><input type="submit" value="清除所有缓存（危险）" class="btn btn-danger" id="deleteSubmit" ></input></div>
</form>

<form id="addform" action="<c:url value='/mcache/add/key'/>" method="post">
<table  class="list" style="min-width:100%">
<tr><td colspan="2">增加缓存:</td></tr>
<tr><td width="30%">key:</td><td><input type="text" name="cache_key" class="memcache" class="input-medium focused fuck"></input></td></tr>
<tr><td width="30%">value:</td><td><textarea rows="5" cols="50" name="cache_value" class="memcache"></textarea></td></tr>
<tr><td colspan="2"><input type="submit" value="新增" class="btn btn-primary"></input></td></tr>
</table>

</form>

<form id="queryform" action="<c:url value='/mcache/query/key'/>" method="post">
<table class="list" style="min-width:100%">
<tr><td colspan="2">查询缓存:</td></tr>
<tr><td width="30%">key:</td><td><input type="text" name="query_key"  class="memcache" class="input-medium focused fuck"></input></td></tr>
<tr><td >value:</td><td ><textarea rows="5" cols="50"  name="query_value" id="query_value" class="memcache"></textarea></td></tr>
<tr><td colspan="2"><input type="submit" value="查询" class="btn btn-success"></input></td></tr>
</table>
</form>
</div>

<div style="width:500px;float:right;background-color:#e0ecff">
<p style="font-style: normal; font-size:12px"><span style="font-weight:bold">缓存Key说明：<br/></span></p>
<p style="font-style: normal; font-size:12px">


<br/>
==mis相关==<br/>

mis权限：mis_privs_map_key<br/>

==反馈相关==<br/>
反馈类型：mis_feedback_type_key<br/>
反馈样板：mis_feedback_sample_key<br/>
<br/>
<!--  
反馈回复样例：mis_feedback_sample_key<br/>
反馈分类：mis_feedback_type_key<br/>
-->
</p>

</div>
</div>
</body>
</html>