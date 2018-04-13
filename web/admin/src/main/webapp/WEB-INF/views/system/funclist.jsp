<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="../common/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="../../css/jquery.treetable.css" />
<link type="text/css" rel="stylesheet" href="../../css/jquery.treetable.theme.default.css" />
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript" src="../../js/jquery.treetable.js"></script>

<script type="text/javascript">
$(document).ready(function(){

  	$("#func-list").treetable({ expandable: true });
	

});

function delfunc(funcId){
	if(confirm('所有子节点也将被删除,请确认是否删除！')){
			$.ajax({
				url:'/func/delete.do',
				type: 'POST',
				data:'funcId='+funcId,
				success: function(data){
					if(data.status == 1)
					{
						alert(data.msg);
						document.forms[0].submit();
					}else
					{
						alert(data.msg);
					}
				}
			});
	}
}

</script>


</head>


<body>

	<div class="place">
    <span>位置：</span>
    <app:nav/>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <app:auth url="/func/add.do"><a href="/func/add.do?fatherFuncId=-1"><li class="click"><span><img src="../../images/t01.png" /></span>添加</li></a></app:auth>
        
        </ul>
        
    
    </div>
    
    
	<form action="/func/list.do">
	<table class="tablelist" id="func-list">
	<thead>
	  <tr>
	  	
    	<th>功能名称</th>
        <th>URL</th>
        <th>状态</th>
        <th>操作</th>
        
	  </tr>
	  </thead>
	  	<tbody>
		<c:forEach items="${funcList}" var="func">
  			<tr data-tt-id="node-${func.funcId}"  <c:if test="${func.fatherFuncId!=-1}">data-tt-parent-id="node-${func.fatherFuncId}"</c:if> >
		  		<td>
		  			
		  			 ${func.funcName} 
		  		</td>
			
				<td>${func.url}</td>
				<td align="center">
					<c:if test="${func.status==0}">可用</c:if>
					<c:if test="${func.status==1}">禁用</c:if>
				</td>
				
				 <td align="center">
				 		<app:auth url="/func/add.do"><a href="/func/add.do?fatherFuncId=${func.funcId}">增加下级功能</a> |</app:auth>
				 		<app:auth url="/func/edit.do"><a href="/func/edit.do?funcId=${func.funcId}">编辑</a> |</app:auth>
		                <app:auth url="/func/delete.do"><a href="#" onClick="delfunc('${func.funcId}')" title="删除">删除</a> </app:auth>
		        </td>
  			</tr>
  			
  		</c:forEach>
  		</tbody>
	</table>
    </form>
    </div>
  
</body>

</html>
