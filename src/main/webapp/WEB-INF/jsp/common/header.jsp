<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="/css/default.css"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<c:url value='/resources/bootstrap/docs/assets/css/bootstrap.css'/>" rel="stylesheet">
<link href="<c:url value='/resources/css/beauty.css" rel="stylesheet'/>" rel="stylesheet">
<style type="text/css">
body {
        padding-top: 60px;
        padding-bottom: 40px;
        background:#d5e9ec;
      }
</style>
<%  Object user_name = request.getSession().getAttribute("login_user_name");
%>
<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <div class="nav-collapse collapse">
            <ul class="nav pull-right">
            	<li class="active"><a>YOUAI游戏MIS系统</a></li>
            </ul>
            <ul class="nav pull-left">
            	<li class="navbar-text navbar-link">欢迎：<%=user_name %></li>
            	<li class="divider-vertical"></li>
            	<li><a href="<c:url value='/userauth/logout'/>">退出登陆</a></li>
          	</ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
</div>