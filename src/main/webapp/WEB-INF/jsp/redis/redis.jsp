<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>

<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
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

$("#queryformjson").submit( function(){ 
	
	$.post(
			 $('#queryformjson').attr( "action" ),
			 $('#queryformjson').serialize(),
	         function(data){
	             	$('#query_value_json').attr("value",  data );
		     }
	 );
     return false;		
    });

$("#commandform").submit( function(){ 
	
	$.post(
			 $('#commandform').attr( "action" ),
			 $('#commandform').serialize(),
	         function(data){
	             	$('#command_result').attr("value",  data );
		     }
	 );
     return false;		
    });

});



 </script>
</head>
<body>
<div style="width:100%;clear:none;">
<div style="clear:left;width:500px;float:left;background-color:#e0ecff">
 
<form id="delform" action="<c:url value='/redis/delete/key'/>" method="post">
<div>
<span>清除key</span>
<div>需要清除的key:<input type="text" name="key" class="memcache"></input></div>
<div><input type="submit" value="删除"/></div>
</div>
</form>
<!-- 
<form id="delallform" action="<c:url value='/mcache/delete/all'/>" method="post">
<div><input type="submit" value="清除所有缓存（危险）" id="deleteSubmit" ></input></div>
</form>

<form id="addform" action="<c:url value='/mcache/add/key'/>" method="post">
<table width="100%">
<tr><td colspan="2">增加缓存:</td></tr>
<tr><td width="30%">key:</td><td><input type="text" name="cache_key" class="memcache"></input></td></tr>
<tr><td width="30%">value:</td><td><textarea rows="5" cols="50" name="cache_value" class="memcache"></textarea></td></tr>
<tr><td colspan="2"><input type="submit" value="新增"></input></td></tr>
</table>

</form>
 -->

<form id="queryform" action="<c:url value='/redis/query/key'/>" method="post">
<table width="100%">
<tr><td colspan="2">查询key</td></tr>
<tr><td width="30%">key:</td><td><input type="text" name="query_key"  class="memcache"></input></td></tr>
<tr><td >value:</td><td ><textarea rows="5" cols="50"  name="query_value" id="query_value" class="memcache"></textarea></td></tr>
<tr><td colspan="2"><input type="submit" value="查询"></input></td></tr>
</table>
</form>

<form id="queryformjson" action="<c:url value='/redis/query/keyjson'/>" method="post">
<table width="100%">
<tr><td colspan="2">查询key(值通过jackson Json串行化)</td></tr>
<tr><td width="30%">key:</td><td><input type="text" name="query_key"  class="memcache"></input></td></tr>
<tr><td >value:</td><td ><textarea rows="5" cols="50"  name="query_value_json" id="query_value_json" class="memcache"></textarea></td></tr>
<tr><td colspan="2"><input type="submit" value="查询"></input></td></tr>
</table>
</form>

<form id="commandform" action="<c:url value='/redis/command'/>" method="post">
<table width="100%">
<tr><td colspan="2">执行命令(目前支持的命令:zscore, zrank, zrem, zrevrank , zadd, hset, lrange, llen)::</td></tr>
<tr><td width="30%">command:</td><td><input type="text" name="command"  class="memcache"></input></td></tr>
<tr><td >value:</td><td ><textarea rows="5" cols="50"  name="command_result" id="command_result" class="memcache"></textarea></td></tr>
<tr><td colspan="2"><input type="submit" value="查询"></input></td></tr>
</table>
</form>
</div>

<div style="width:500px;float:right;background-color:#e0ecff">
<p style="font-style: normal; font-size:12px"><span style="font-weight:bold">Redis Key说明：</br></span></p>
<p style="font-style: normal; font-size:12px">
广告推广激活=> ad_stat:activate_广告商 </br>
广告推广点击=> ad_stat:click_广告商 </br>

</p>

</div>
</div>
</body>
</html>