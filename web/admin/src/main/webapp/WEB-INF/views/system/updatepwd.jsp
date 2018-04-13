<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="../common/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/jquery.js"></script>
<script>
		
	function checkform()
	{
		
		var result = true;
		if($.trim($('#password').val())=='')
		{
			alert('请输入旧密码！');
			$("#password").focus();
			result =  false;
		}else if($.trim($('#newPassword').val())=='')
		{
			alert('请输入新密码！');
			$("#newPassword").focus();
			result =  false;
		}else if($.trim($('#confirmNewPassword').val())=='')
		{
			alert('请输入确认新密码！');
			$("#confirmNewPassword").focus();
			result =  false;
		}else if($.trim($('#confirmNewPassword').val())!=$.trim($('#newPassword').val()))
		{
			alert('两次输入的新密码不一致！');
			result =  false;
		}
		
		
		
		if(result==true)
		{
			document.forms[0].submit();
		}
	}
</script>

</head>

<body>

	<div class="place">
    <span>位置：</span>
    <app:nav/>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>修改密码</span></div>
    
    <form action="/user/doUpdatePwd.do" method="post" >
    
		
	<input type="hidden" name="userName" value="${user.userView.userName}" />
	
    <ul class="forminfo">
    
	<li><label>账号</label><input name="name" id="name" class="dfinput" value="${user.userView.name}(${user.userView.userName})" disabled="disabled"/></li>
	<li><label>旧密码</label><input name="password" id="password" type="password" class="dfinput" /></li>
	<li><label>新密码</label><input name="newPassword" id="newPassword" type="password" class="dfinput" /></li>
	<li><label>确认新密码</label><input name="confirmNewPassword" id="confirmNewPassword" type="password" class="dfinput" /></li>
	
    <li><label>&nbsp;</label><input onclick="checkform();" name="" type="button" class="btn" value="确认保存"/></li>
    </ul>
    </form>
    
    </div>

</body>

</html>
