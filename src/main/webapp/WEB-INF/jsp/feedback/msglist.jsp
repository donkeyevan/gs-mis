<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>

<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery.popupWindow.js'/>"></script>


<script type="text/javascript">
$(function() {
	$('#ready').load('ready');
	
	$("#feedback_select").change(function(){
	 	$("#answer_content").val($(this).find("option:selected").val()  );
	});
	$('#form2').submit(function() {
        window.open('', 'formpopup', 'width=600,height=400,resizeable,scrollbars');
        this.target = 'formpopup';
    });

	$("#form1").submit( function(){ 
		$.post(
				"<c:url value='/feedback/thread/status?'/>thread_id=<%=request.getParameter("thread_id") %>",
				 $('#form1').serialize(),
		            function(data){
						var result = jQuery.parseJSON( data);
						if( result.result == 1 ){
							alert("change status sucessfully!" );
						}else{
							alert( result.desc );
						}
				    }
		 );
	     return false;		
	 });
});

</script>
</head>
<body>
<div class="action-panel">
<div class="clear"></div>
</div>
<div class="query-list">
<table class="list">
 
  <tr class="title">
  <td>反馈/回复</td>
  <td>内容</td>
  <td>创建时间</td>
  <td>回复人</td>
  </tr>
  
<c:forEach var="item"  items="${msgs}" begin="0"   >
  <tr class="body">
<td>
<c:choose>
<c:when test="${item.answer eq 1}">回复</c:when>
<c:otherwise>
反馈
</c:otherwise>
</c:choose>

</td>
<td>${item.content}</td>
<td>
<fmt:timeZone value="Etc/UTC">
   		<fmt:formatDate value="${item.createdAt}" type="both" dateStyle="medium"  timeZone="GMT+8"                  
               timeStyle="medium" />
</fmt:timeZone>
</td>
<td>
${item.replyerName}
</td>
 </tr>
</c:forEach> 
</table>
</div>
<div>
<select id="feedback_select" name="feedback_select">
<option  value=""></option>
<c:forEach var="item"  items="${requestScope.feedback_samples}" >
<option  value="${item.message}">${item.title}</option>
</c:forEach>
</select> 
</div>

<form id="form" method="post" action="<c:url value='/feedback/thread/answer?'/>thread_id=<%=request.getParameter("thread_id") %>">
<table>
<tr><td colspan="2">
<div class="answer-textarea">
回复内容：
<textarea id="answer_content" rows="5" cols="60" name="content"></textarea>
</div>
</td></tr>
<tr><td>修改反馈状态:</td>
<td>
<select name="feedback_status">
<option value="0" <c:if test="{feedback_status eq 0}">selected</c:if>>open</option>
<option value="1" <c:if test="{feedback_status eq 1}">selected</c:if>>ignored</option>
<option value="2" <c:if test="{feedback_status eq 2}">selected</c:if>>close</option>
</select>
</td>
</tr>
<tr>
<td>反馈类型:</td>
<td>
<select name="feedback_type">
<c:forEach var="item"  items="${feedback_types}"  >
<option value="${item.id}" <c:if test="${item.selected eq 1}">selected</c:if>>${item.title}</option>
</c:forEach>
</select>
</td></tr>
<tr><td colspan="2">
<input type="submit" value="确定"/>
</td></tr>
</table>
</form>

查看反馈人<c:out value="${player_name}"/>的信息:
<form id="form2" method="post" action="<c:url value='/feedback/player/info?'/>player_key=<c:out value='${player_key}'/>&puid=<c:out value='${puid}'/>">
用户信息数据来源:
<select name="userinfo_db">
<option value="playercenter">账号信息</option>
<option value="gameserver">本角色信息</option>
</select>
<input type="submit" value="确定"/>
</form>
</body>
</html>
