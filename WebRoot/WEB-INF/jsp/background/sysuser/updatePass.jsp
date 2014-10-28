<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
  <head>
		<%@include file="../../common/taglib.jsp"%>
		<%@include file="../../common/common-css.jsp"%>
    <style type="text/css">
      input{font-size: 12px}
    </style>
  </head>
  
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.4.min.js"></script>

<script type="text/javascript">
	//提交
	var result = false;
	function savePass(){
		if(result == true){
			return false;
		}else{
			var pass0 = $("#userPassword0").val();
			var pass1 = $("#userPassword1").val();
			var pass2 = $("#userPassword2").val();
			//judge.
			if(judge(pass1,pass2)){
				if(pass1==pass2){
					if(pass1==pass0 ){
						alert("与原密码一致,请重新输入或放弃修改!");
						$("[name='userPassword1']").val("");
				        $("[name='userPassword2']").val("");
						return false;
					}else{
						//submit
						$("#userPassword").val(pass1);
						$("#changePassFrom").attr("action","<%=request.getContextPath()%>/background/sysuser/updatePass.html");
						$("#changePassFrom").submit();
					}
				}else{
					alert("两次密码输入不一样,请重新输入!");
					$("[name='userPassword2']").val("");
					return false;			
				}
			}else{
				alert("请输入新密码和确认密码!");
				return false;
			}
			result = true;
			return true;
		}
	}
	
	//判断.
	function judge(pass1,pass2){
		if(pass1==null || pass1=='' || pass2==null || pass2=='' ){
			return false;
		}else{
			return true;		
		}
	}

</script>
	  
 <body>
<br/>
<br/>
  
<form method="post" id="changePassFrom">
		
		<input type="hidden" name="userPassword" id="userPassword" >
		<input type="hidden" name="userId" id="userId" value="${ user.userId }" >
		
		<table class="ttab" height="100" width="70%" border="0" cellpadding="0" cellspacing="1" align="center">
			<tr>
				<td height="30" colspan="2">
					<div align="center">
					<font color="blue" size="4" ><b>修改密码</b></font>
					</div>
				</td>
			</tr>
	
			<tr>	
					<td height="30" width="40%" >
						<div align="right" class="STYLE1" >
								用户名称：
						</div>
					</td>
					<td >
						<div align="left" class="STYLE1"  style="padding-left:10px;">
						<input style="height: 20px;width: 200px" name="name" value="${user.roleName }"/>
						</div>
					</td>
				</tr>
				<tr>	
					<td height="30" width="40%" >
						<div align="right" class="STYLE1" >
								原密码：
						</div>
					</td>
					<td >
						<div align="left" class="STYLE1"  style="padding-left:10px;">
						<input style="height: 20px;width: 200px" name="userPassword0" id="userPassword0" type="password" value="${user.userPassword }" />
						</div>
					</td>
				</tr>
				<tr>	
					<td height="30" width="40%" >
						<div align="right" class="STYLE1">
								新密码：
						</div>
					</td>
					<td>
						<div align="left" class="STYLE1"  style="padding-left:10px;">
						<input style="height: 20px;width: 200px" name="userPassword1" id="userPassword1"  type="password" />
						</div>
					</td>
				</tr>
				
				<tr>	
					<td height="30" width="40%" >
						<div align="right" class="STYLE1">
								确认密码：
						</div>
					</td>
					<td>
						<div align="left" class="STYLE1"  style="padding-left:10px;">
						<input style="height: 20px;width: 200px" name="userPassword2" id="userPassword2" type="password" />
						</div>
					</td>
				</tr>
				
				<tr>
					<td colspan="2" style="padding: 10px">
						<div align="center">
			 				<input type="button" value="　保　存　" class="input_btn_style1" onclick="return savePass()" />　　　　
			 				<input id="backBt" type="button" value="　返　回　" class="input_btn_style1" onclick="javascript:window.location.href='javascript:history.go(-1)'"/>
		 				</div>
					</td>
				</tr>
		</table>
</form>
  </body>
</html>
