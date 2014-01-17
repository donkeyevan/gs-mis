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

$("#modify_button").click( function(){ 
	
	if($.trim($("#old_passwd").val()) == ""){
		alert("密码不可为空");
		return false;
	}

	if($.trim($("#passwd").val()) == ""){
		alert("新密码不可为空");
		return false;
	}

	if($.trim($("#passwd").val()) != $.trim($("#passwd_confirm").val())){
		alert("新密码两次输入请一致");
		return false;
	}
	
	$.post(
			 $('#modifyform').attr( "action" ),
			 $('#modifyform').serialize(),
	         function(data){
	             	alert(data);
		     }
	 );
     return false;		
    });

});
 </script>
</head>
<body class="main">
<div style="width:100%;margin: 18% 20%">
<div style="clear:left;width:800px;float:left;background-color:#e0ecff">
<form id="modifyform" action="<c:url value='/passwd/modify'/>" method="post">
<div>
<h3>修改密码</h3>
<div>老密码:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" name="oldPasswd" id="old_passwd"  class="input-medium focused fuck"></input></div>
<div>新密码:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" name="passwd" id="passwd"  class="input-medium focused fuck"></input></div>
<div>再次输入新密码:&nbsp;&nbsp;<input type="password" name="passwd" id="passwd_confirm"  class="input-medium focused fuck"></input></div>
<div><input type="button" id="modify_button" value="确认修改" class="btn btn-success"/></div>
</div>
</form>

</div>
</div>
</body>
</html>