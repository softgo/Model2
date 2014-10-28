<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="<%=request.getContextPath()%>/js/Clock.js" type=text/javascript></script>
<title>欢迎页面</title>


</head>

<body bgcolor="#353c44">

<div>
<center style="color: white;font-weight: 800; height:100;  font-size: 30px;" >
<br>
尊贵的：<span style="color: yellow;font-weight: 800;">${userSession.userName}</span>　
<br>
<br>　

您好! 欢迎登陆管理系统

<dir style="color: white;font-size: 20px;height:300;">
<br>

你的身份是: 
  <span style="color: yellow;">
      <c:if test="${userSession.level eq '0'}">超级管理员</c:if> 
      <c:if test="${userSession.level ne '0'}"> 普通用户</c:if>
  </span>
  
　　现在的时间是:<span class="STYLE7">
<b>
	<SPAN id="clock" style="color: yellow;"></SPAN>
<script type=text/javascript>
    var clock = new Clock();
    clock.display(document.getElementById("clock"));
</script>

</b>

</span>

</dir>　

</center>

</body>
</html>
