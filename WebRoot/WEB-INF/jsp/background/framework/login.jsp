<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>管理系统</title>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	overflow:hidden;
}
-->
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.min.js"></script>

<script type="text/JavaScript">
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
</script>

<script type="text/javascript">
  
  function checkUserForm(){
    var userName = $("#textfield").val();
    var userPassword = $("#textfield2").val();
    if(userName=="" || userPassword==""){
      alert("用户名或密码不能为空");
      return false;
    }else{
      document.loginForm.submit();
   	  return true;
   	}
  }
      
    function reSetVl(){
	  $("#textfield").val("");
	  $("#textfield2").val("");  	
    }
     
      //按回车键
    document.onkeydown=function(event){ 
        var e = event ? event : (window.event ? window.event : null); 
        if(e.keyCode==13){ 
            checkUserForm();
        } 
    }
     
</script>
</head>

<body onload="MM_preloadImages('<%=request.getContextPath()%>/images/login_09_1.gif','<%=request.getContextPath()%>/images/login_10_1.gif')" >

<form name="loginForm" method="post" action="<%=request.getContextPath()%>/background/loginCheck.html">

<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td bgcolor="#02395f"><br></td>
  </tr>
  <tr>
    <td height="607" align="center" background="<%=request.getContextPath()%>/images/login_02.gif">
    <table width="974" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="331" background="<%=request.getContextPath()%>/images/login_01.jpg">&nbsp;</td>
      </tr>
      <tr>
        <td height="116"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="393" height="116" background="<%=request.getContextPath()%>/images/login_05.gif">&nbsp;</td>
            <td width="174"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="81" background="<%=request.getContextPath()%>/images/login_06.gif">
                	<div align="center">
		                <span style="color: yellow;font-size: 12px; text-align: center">${error}</span>
                	</div>
	                <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                  <tr>
	                    <td width="24%"><div align="center"><font style="height:1;font-size:9pt; color:#bfdbeb;filter:glow(color=#1070a3,strength=1)">用户</font></div></td>
	                    <td width="76%" height="25"><input type="text" id="textfield" name="username"  style="width:125px; height:20px; background:#32a2e3; font-size:12px; border:solid 1px #0468a7; color:#14649f;"></td>
	                  </tr>
	                  <tr>
	                    <td><div align="center"><font style="height:1;font-size:9pt; color:#bfdbeb;filter:glow(color=#1070a3,strength=1)">密码</font></div></td>
	                    <td height="25"><input type="password" name="password" id="textfield2" style="width:125px; height:20px; background:#32a2e3; font-size:12px; border:solid 1px #0468a7; color:#14649f;"></td>
	                  </tr>
	                </table>
                </td>
              </tr>
              <tr>
                <td height="35"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="50" height="35"><img src="<%=request.getContextPath()%>/images/login_08.gif" width="50" height="35"></td>
						
						  <!-- 登陆和重置 -->
                    <td width="46"><a href="javascript:void(0);"><img src="<%=request.getContextPath()%>/images/login_09.gif"  name="Image1" width="46" height="35" border="0" id="Image1" onmouseover="MM_swapImage('Image1','','<%=request.getContextPath()%>/images/login_09_1.gif',1)" onmouseout="MM_swapImgRestore()"  onclick="return checkUserForm()" /></a></td>
                    <td width="45"><a href="#">                  <img src="<%=request.getContextPath()%>/images/login_10.gif" name="Image2" width="45" height="35" border="0" id="Image2"  onmouseover="MM_swapImage('Image2','','<%=request.getContextPath()%>/images/login_10_1.gif',1)" onmouseout="MM_swapImgRestore()" onclick="reSetVl()" ></a></td>
                   
                    <td width="33"><img src="<%=request.getContextPath()%>/images/login_11.gif" width="33" height="35"></td>
                  </tr>
                </table></td>
              </tr>
            </table></td>
            <td width="407" background="<%=request.getContextPath()%>/images/login_07.gif">&nbsp;</td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="160" background="<%=request.getContextPath()%>/images/login_12.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td bgcolor="#02609c">&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>
