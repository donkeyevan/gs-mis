<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<script type="text/javascript">
        function pagechange(index){
            var url = "<c:url value='/feedback/thread/query?'/>";//原始的地址
			url = url+"sort_field1=<c:out value='${sort_field1}'/>"+
			"&sort_type1=<c:out value='${sort_type1}'/>&query_field=<c:out value='${query_field}'/>"+
			"&query_value=<c:out value='${query_value}'/>&feedback_type=<c:out value='${feedback_type}'/>"+
			"&feedback_status=<c:out value='${feedback_status}'/>&begin_created_at=<c:out value='${begin_created_at}'/>"+
			"&replied=<c:out value='${replied}'/>&last_op_admin=<c:out value='${last_op_admin}'/>&end_created_at=<c:out value='${end_created_at}'/>";
            //使用正则达式，假定当前页参数在最后一个，且形如page=4
            var regExp = /(S*page=)(S*)/;
            var arr = regExp.exec(url);//使用正则表达式分析页面的地址，使用分析结果调整页码
            if(arr==null)url = url+"&pn="+index;
            else url = arr[1]+index;
            //alert(url);
            window.location = url;
        }
        
</script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery.popupWindow.js'/>"></script>
<script type="text/javascript"> 
$(function() {
$('#ready').load('ready');
$('.feedback_answer').popupWindow({ 
	height:500, 
	width:800, 
	top:50, 
	left:50 
	});
 
$('.feedback_detail').popupWindow({ 
	height:500, 
	width:800, 
	top:50, 
	left:50 
	});

$('.feedback_player').popupWindow({ 
	height:600, 
	width:1024, 
	top:50, 
	left:50 
	});

$('.feedback_type').popupWindow({ 
	height:300, 
	width:300, 
	top:200, 
	left:200 
	}); 
$("a#change_status").click( function(event){ 
	$.getJSON(
			   "<c:url value='/feedback/thread/status?'/>"+"thread_id="+$(event.target).attr( "href" ),

	            function(data){
		            if( data.result == 1 ){
						alert( " Change feedback status successfully!" );
						window.location.reload();
			        }else{
						alert( data.desc );
					}		            
		        }
	 );
	 
     return false;		
    });


$("#batch_type").click( function(){
	$.post(
			 "<c:url value='/feedback/thread/batchtype'/>",
			 $('#form1').serialize(),
	            function(data){
				 	var data = jQuery.parseJSON( data);
				 	if( data.result == 1 ){
						alert( " Batch change feedbacks status successfully!" );
						//window.location.reload();
			        }else{
						alert( data.desc );
					}	
		        }
	 );
    return false;		
});
	
$("#batch_answer").click( function(){
	$.post(
			 "<c:url value='/feedback/thread/batchanswer'/>",
			 $('#form1').serialize(),
	            function(data){
				 	var data = jQuery.parseJSON( data);
				 	if( data.result == 1 ){
						alert( "Batch reply feedbacks  successfully!" );
						//window.location.reload();
			        }else{
						alert( data.desc );
					}	
		        }
	 );
    return false;		
});

$("#batch_status").click( function(){
	$.post(
			 "<c:url value='/feedback/thread/batchstatus'/>",
			 $('#form1').serialize(),
	            function(data){
				 	var data = jQuery.parseJSON( data);
				 	if( data.result == 1 ){
						alert( "Batch change status  successfully!" );
						//window.location.reload();
			        }else{
						alert( data.desc );
					}	
		        }
	 );
    return false;		
});


$("#sample_select").change(function(){
 	$("#answer_content").val($(this).find("option:selected").val()  );
});

$("#selectall").click( function(){
	$('.threadlist_checkbox').attr("checked",'true');
});

$('#cancel_selectall').click( function(){
	$('.threadlist_checkbox').removeAttr("checked");;
});


});

</script>
	


</head>
<body>
<div class="action-panel">
<div class="clear"></div>
</div>

<div class="query-list">
<form id="form1" method="post" >
<table class="list">
 
  <tr class="title">
  <td>复选</td>
  <td width="100">反馈ID</td>
  <td>反馈人</td>
  <td>已回复</td>
  <td>反馈内容</td>
  <td>反馈时间</td>
  <td>更新时间</td>
  <td>分类类型</td>
  <td>最后操作人</td>
  <td >操作</td>
  </tr>
  
<c:forEach var="item"  items="${pqinfo.rows}" begin="0"   >
 <tr class="body">
 <td><input type="checkbox" name="thread_ids" value="${item.thread.id}" class="threadlist_checkbox"/></td>
<td>${item.thread.id}</td>
<td><a class="feedback_player" href="<c:url value='/feedback/thread/query?'/>query_field=player_key&query_value=${item.thread.playerIdx}">
<c:choose>
<c:when test="${item.thread.vipLevel gt 0}">
<font color="red">${item.thread.playerName}</font>
</c:when>
<c:otherwise>
${item.thread.playerName}
</c:otherwise>
</c:choose>
</a></td>
<td>
<c:choose>
<c:when test="${item.thread.replied eq 1}">YES</c:when>
<c:when test="${item.thread.replied eq 0}"><font color="red">NO</font></c:when>
</c:choose>
</td>
<td>${item.origMsg.content}</td>
<td>
   <fmt:timeZone value="Etc/UTC">
   		<fmt:formatDate value="${item.thread.createdAt}" type="both" dateStyle="medium"  timeZone="GMT+8"                  
               timeStyle="medium" />
   </fmt:timeZone>
</td>
<td>
	<fmt:timeZone value="Etc/UTC">
   		<fmt:formatDate value="${item.thread.updatedAt}" type="both" dateStyle="medium"  timeZone="GMT+8"                  
               timeStyle="medium" />
    </fmt:timeZone>
</td>
<td>
${item.thread.feedbackTypeName}
</td>
<td>
${item.thread.lastOpAdmin}
</td>
<td>
<script type="text/javascript">  
var  link = "<c:url value='/feedback/msg/list?'/>"+
		"thread_id=${item.thread.id}&player_key=${item.thread.playerIdx}"+
		"&feedback_type=${item.thread.feedbackType}&feedback_status=${item.thread.ignored}&player_name=" +
		 encodeURI( "${item.thread.playerName}&puid=${item.thread.puid}" );
		
document.write( '<a class="feedback_detail" href='+link+'>回复</a>&nbsp;&nbsp;'); 
</script>
<!-- <a class="feedback_answer" href="<c:url value='/feedback/thread/preanswer?'/>thread_id=<c:out value='${item.thread.id}'/>">回复</a> -->
<a class="feedback_type" href="<c:url value='/feedback/thread/pretype'/>?thread_id=${item.thread.id}&thread_type=${item.thread.feedbackType}">分类</a>
&nbsp;&nbsp;
<c:if test="${feedback_type eq 0}">
<a class="action" id="change_status" href="<c:out value='${item.thread.id}&status=1'/>">忽略</a>
<a class="action" id="change_status" href="<c:out value='${item.thread.id}&status=2'/>">关闭</a>
</c:if>
</td>
 </tr>
</c:forEach>
</table>
<!-- follow is batch op table -->
<table><tr><td>
<input type="button" id="selectall" value="全选"/>
<input type="button" id="cancel_selectall" value="取消全选"/>
</td></tr></table>
<table>
<tr><td>批量改状态:
<select name="feedback_status">
<option value="0" >open</option>
<option value="1" >ignore</option>
<option value="2" >close</option>
<option value="3" >reopen</option>
</select>
<input type="button" value="状态修改提交" id="batch_status"></input> 
</td></tr>
<tr><td>
<tr><td>批量设置分类:
<select name="feedback_type">
<c:forEach var="item"  items="${feedback_types}"  >
<option value="${item.id}" >${item.title}</option>
</c:forEach>
</select>
<input type="button" value="分类修改提交" id="batch_type"></input> 
</td></tr>
<tr><td>
批量回复 样例： 
<select id="sample_select" name="sample_select">
<option  value=""></option>
<c:forEach var="item"  items="${feedback_samples}" >
<option  value="${item.message}">${item.title}</option>
</c:forEach>
</select> 
</td></tr>
<tr><td>
<textarea id="answer_content" rows="5" cols="60" name="content"></textarea>
<input type="button" value="批量回复提交" id="batch_answer"></input> 
</td></tr>
</table>
<!-- before is batch op table -->
</form>
<div class="navigation">
 <c:set var="page_num" scope="page" value="${pqinfo.pageSum}"/>
   <c:set var="row_count" scope="page" value="${pqinfo.rowCount}"/>
    <c:set var="pn" scope="page" value="${pqinfo.pn}"/>
     <c:set var="rn" scope="page" value="${pqinfo.rn}"/>
 <center>总记录数：${row_count}；页数：${page_num}，当前页：${pn}
    <a href="javascript:pagechange(1)" title="首页">首页</a>&nbsp;&nbsp;
    <c:if test="${pn > 1}">
    <a href="javascript:pagechange(${pn-1})" title="前一页">前一页</a>&nbsp;&nbsp;
    </c:if>
    <c:if test="${(pn+0) < (page_num)}">
    <a href="javascript:pagechange(${pn+1})" title="下一页">下一页</a>&nbsp;&nbsp;
    </c:if>
    <a href="javascript:pagechange(${page_num})" title="尾页">尾页</a></center>

</div>
</div>
</body>
</html>
