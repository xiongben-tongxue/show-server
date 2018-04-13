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
$(document).ready(function(){
	if ($('#userType').val()==1){
		$('#family').show();
		$('#owner').hide();
	}else{
		$('#owner').show();
		$('#family').hide();
	}
})
	function checkform()
	{
		
		var result = true;
		if($.trim($('#userName').val())=='')
		{
			alert('请输入登陆账号！');
			$("#userName").focus();
			result =  false;
		}else if($.trim($('#name').val())=='' && $('#userType').val()==0)
		{
			alert('请输入姓名！');
			$("#name").focus();
			result =  false;
		}
		
		
		
		if(result==true)
		{
			document.forms[0].submit();
		}
	}
	
	function typechange(){
		if ($('#userType').val()==1){
			$('#family').show();
			$('#owner').hide();
		}else{
			$('#owner').show();
			$('#family').hide();
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
    
    <div class="formtitle"><span>用户信息</span></div>
    
    <form:form action="/user/${opt}.do" modelAttribute="user" method="post">
    
		
	<c:if test="${opt=='doEdit'}">
	<form:hidden path="userName" />
	</c:if>
	
    <ul class="forminfo">
    
    <li><label>账号类型</label> 
    <form:select path="userType"  class="dfinput" onchange="typechange()" >
		<form:option value="0">公司内部</form:option>
		<form:option value="1">家族账号</form:option>
	</form:select>
    
    </li>
    
    <c:if test="${opt=='doAdd'}">
	 	 <li><label>登陆账号</label><form:input path="userName" class="dfinput"/><i>标题不能超过30个字符</i></li>
	</c:if>
    
    <div id="owner">
    <li><label>姓名</label><form:input path="name" class="dfinput"/> </li>
    </div>
    <div id="family" style="display: none">
    <li><label>家族</label> 
    
     <form:select path="fid"  class="dfinput" >
		<c:forEach items="${familyList}" var="item">
			<form:option value="${item.id}">${item.name}</form:option>
		</c:forEach>
	</form:select>
    </div>
    <c:if test="${opt=='doEdit'}">
    	<li><label>状态</label><cite><form:radiobutton path="status" value="0"/>可用&nbsp;&nbsp;&nbsp;&nbsp;<form:radiobutton path="status" value="1"/>禁用</cite></li>
    </c:if>
    <li><label>角色设置</label>
		
		<c:forEach items="${roleList}" var="role">
			<form:checkbox path="roles" value="${role.roleId}"/>${role.roleName}<br/>
		</c:forEach>
		
	</li>
    
    <li><label>&nbsp;</label><input onclick="checkform();" name="" type="button" class="btn" value="确认保存"/></li>
    </ul>
    </form:form>
    
    </div>

</body>

</html>
