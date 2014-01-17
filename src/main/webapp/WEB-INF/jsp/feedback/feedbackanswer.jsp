<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/default.css'/>"/>
<script type="text/javascript" src="<c:url value='/resources/js/jquery_1.5.1.min.js'/>"></script>
<script type="text/javascript">
$(function() {
	$('#ready').load('ready');
	$("#feedback_select").change(function(){
		 $("#answer_content").val($(this).find("option:selected").val()  );
	});
	});
</script>
</head>
<body>
<div>
<select id="feedback_select" name="feedback_select">
<option  value=""></option>
<c:forEach var="item"  items="${requestScope.feedback_samples}" >
<option  value="${item.message}">${item.title}</option>
</c:forEach>
</select> 
</div>
<form id="form" method="post" action="<c:url value='/feedback/thread/answer?'/>thread_id=<%=request.getParameter("thread_id") %>">

<div class="answer-textarea">
回复内容：
<textarea id="answer_content" rows="5" cols="60" name="content"></textarea>
<input type="submit" value="确定"/>
</div>
</form>

</body>
</html>