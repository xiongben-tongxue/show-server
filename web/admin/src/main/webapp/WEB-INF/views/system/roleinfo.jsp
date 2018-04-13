<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="../common/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../../css/jquery.ui.all.css">
<script type="text/javascript" src="../../js/jquery.js"></script>

<script src="../../js/ui/jquery.ui.core.js"></script>
<script src="../../js/ui/jquery.ui.widget.js"></script>
<script src="../../js/ui/jquery.ui.mouse.js"></script>
<script src="../../js/ui/jquery.ui.draggable.js"></script>
<script src="../../js/ui/jquery.ui.position.js"></script>
<script src="../../js/ui/jquery.ui.resizable.js"></script>
<script src="../../js/ui/jquery.ui.dialog.js"></script>
<script src="../../js/ui/jquery.effects.core.js"></script>
<script src="../../js/ui/jquery.effects.blind.js"></script>
<script src="../../js/ui/jquery.effects.explode.js"></script>

	<script>
		$(document).ready(function () {
			$( "#tree" ).dialog({
				autoOpen: false,
				show: "blind",
				resizable:true,
				height:500
			});

		});
		
		
        function checkform()
        {
        	var result = true;
			if ($.trim($("#roleName").val())==''){
				alert('请输入名称');
				$("#roleName").focus();
				result = false;
			}else if ($.trim($("#roleDesc").val()).length > 120){
				alert('描述不能超过120个字');
				$("#roleDesc").focus();
				result = false;
			}
		
			if(result==true)
			{
				document.forms[0].submit();
			}
        }
        function showFuncTree(){
        	$( "#tree" ).dialog( "open" );
        
        }
        
        function setValue(code){
        	$("#funcStr").val(code);
        }
        
        function closeFuncTree(){
        	$( "#tree" ).dialog( "close" );
        }
	</script>


</head>

<body>

	<div class="place">
    <span>位置：</span>
    <app:nav/>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>角色信息</span></div>
    
    <form:form action="/role/${opt}.do" modelAttribute="role" method="post">
    
		
	<c:if test="${opt=='doEdit'}">
	<form:hidden path="roleId" />
	</c:if>
	
    <ul class="forminfo">
    
	<li><label>名称</label><form:input path="roleName" class="dfinput"/></li>
	
    
    <li><label>描述</label><form:input path="roleDesc" class="dfinput"/> </li>
    <c:if test="${opt=='doEdit'}">
    	<li><label>状态</label><cite><form:radiobutton path="status" value="0"/>可用&nbsp;&nbsp;&nbsp;&nbsp;<form:radiobutton path="status" value="1"/>禁用</cite></li>
    </c:if>
    <li><label>功能设置</label>
		<input type="text" name="funcStr" id="funcStr" value="${funcStr}" class="dfinput" readonly="true" onclick="showFuncTree();" />&nbsp;<i><a href="javascript:showFuncTree();" >选择</a></i>
	</li>
    
    <li><label>&nbsp;</label><input onclick="checkform();" name="" type="button" class="btn" value="确认保存"/></li>
    </ul>
    </form:form>
    
    </div>
    
  <div id="tree" title="功能树">
	<iframe  id="funcTree" width="100%" height="100%" frameborder="0" scrolling="yes" src="/func/tree.do?funcIdInTree=${funcStr}" ></iframe>
  </div>

</body>

</html>
