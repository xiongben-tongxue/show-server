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
			if ($.trim($("#funcName").val())==''){
				alert('请输入名称');
				$("#funcName").focus();
				result = false;
			}else if ($.trim($("#url").val())==''){
				alert('请输入URL');
				$("#url").focus();
				result = false;
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
    
    <div class="formtitle"><span>父节点 ：${fatherFunc.funcName}</span></div>
    
    <form:form action="/func/${opt}.do" modelAttribute="func" method="post">
   	<input name="fatherFuncId" type="hidden" value="${fatherFunc.funcId}"/>
	<c:if test="${opt=='doEdit'}">
	<form:hidden path="funcId" />
	</c:if>
    <ul class="forminfo">
    <li><label>功能名称</label> <form:input path="funcName" class="dfinput"/> <i>标题不能超过30个字符</i></li>
    <li><label>URL</label><form:input path="url" class="dfinput"/> <i>多个URL用,隔开</i></li>
    <c:if test="${opt=='doEdit'}">
    	<li><label>状态</label><cite><form:radiobutton path="status" value="0"/>可用&nbsp;&nbsp;&nbsp;&nbsp;<form:radiobutton path="status" value="1"/>禁用</cite></li>
    </c:if>
    
    <li><label>&nbsp;</label><input onclick="checkform();" name="" type="button" class="btn" value="确认保存"/></li>
    </ul>
    </form:form>
    
    </div>

</body>

</html>
