<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/beauty.css" rel="stylesheet'/>" rel="stylesheet">

<title>用户登陆</title>
</head>
<body class='login'>
<div class='container'>
	<form id="loginform"  method="post" class='form-signin'>
		<h3 class="form-signin-heading">游戏管理系统用户登陆</h3>
        <input type="text" class="input-block-level" placeholder="用户名" name="user_name"/>
        <input type="password" class="input-block-level" placeholder="密码" name="user_passwd"/>
        <button class="btn btn-large btn-primary" type="submit">登陆</button>
	</form>
</div>
<script src="<c:url value='/resources/bootstrap/docs/assets/js/jquery.js'/>"></script>
<script src="<c:url value='/resources/bootstrap/docs/assets/js/bootstrap-transition.js'/>"></script>
<script type="text/javascript">
$(function() {
    $('#ready').load('ready');
    $(window).load(function() {
    	try
    	{
    		if (self.parent.frames.length != 0)
    		self.parent.location=document.location;
    	}
    	catch (Exception) {}
  	});
    $("#loginform").submit( function(){
		$.post(
				"<c:url value='/userauth/login'/>",
				 $('#loginform').serialize(),
		            function(data){
		            
		                var result = jQuery.parseJSON( data );
		                if( result.result != 'AUTH_SUCCESS' ) {
							alert( result.failReason );
			            }else{
				     
			            	window.location.replace( "<c:url value='/index/show'/>");
				        }
		            }
	            );
        return false;		
    });
});
</script>
</body>
</html>
