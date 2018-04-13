<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录ShowLive后台管理系统</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="../js/jquery.js"></script>
<script src="../js/cloud.js" type="text/javascript"></script>

<script language="javascript">
$(document).ready(function() {
	if (window.self != window.top) {
      window.open("<%=request.getContextPath()%>/login.do", "_top");
	}
});
	$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	$(window).resize(function(){  
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    })  
});  
	
	 function checkform()
     {
		 var result = true;
			if ($.trim($("#username").val())==''){
				alert('请输入用户名　');
				$("#username").focus();
				result = false;
			}else if ($.trim($("#password").val())==''){
				alert('请输入密码');
				$("#password").focus();
				result = false;
			}
		
			if(result==true)
			{
				document.forms[0].submit();
			}
     }

</script> 

</head>

<body style="background-color:#1c77ac; background-image:url(../images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">



    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>  


<div class="logintop">    
    <span>欢迎登录ShowLive后台管理系统</span>    
    <ul>
    <li><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    </ul>    
    </div>
    
    <div class="loginbody">
    
    
     
    <div class="loginbox">
    <form action="/doLogin.do" method="post"> 
    <ul>
    <li><input name="username" id="username" type="text" class="loginuser"  onclick="JavaScript:this.value=''"/></li>
    <li><input name="password" id="password" type="password" class="loginpwd"  onclick="JavaScript:this.value=''"/></li>
    <li><input name="" type="button" class="loginbtn" value="登录"  onclick="checkform()"  />
    <label>${loginError}</label></li>
    </ul>
    </form>
    
    </div>
    
    
    </div>
    
    
    
    <div class="loginbm">版权所有  2016  <a href="http://www.xiubi.com">xiubi.com</a></div>
	
    
</body>

</html>
