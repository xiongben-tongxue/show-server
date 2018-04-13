<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="../common/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/jquery.js"></script>

<script type="text/javascript">

function deluser(roleId){
	if(confirm('请确认是否删除！')){
			$.ajax({
				url:'/role/delete.do',
				type: 'POST',
				data:'roleId='+roleId,
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
        <app:auth url="/role/add.do"><a href="/role/add.do"><li class="click"><span><img src="../../images/t01.png" /></span>添加</li></a></app:auth>
        
        </ul>
        
    
    </div>
    
    
	<form name="adminRoleListForm" action="/role/list.do">
	<input type="hidden" name="currentPage" value="1"/>
	
	<table class="tablelist" >
	<thead>
	  <tr>
    	<th>角色名称</th>
        <th>描述</th>
        <th>状态</th>
        <th>创建时间</th>
        <th>操作</th>
	  </tr>

	  </thead>
	  	<tbody>
			<c:forEach items="${roleList}" var="role">
			  <tr>
			   
			    <td>${role.roleName}</td>
			    <td>${role.roleDesc}</td>
				<td>
					<c:if test="${role.status=='0'}">可用</c:if>
					<c:if test="${role.status=='1'}">禁用</c:if>
					
				</td>
				
				<td>${fns:intDate2String(role.createTime, "yyyy-MM-dd HH:mm:ss")}</td>
				<td>
				  <app:auth url="/role/edit.do"><a href="/role/edit.do?roleId=${role.roleId}" title="编辑">编辑</a> |</app:auth>
				  <app:auth url="/role/delete.do"><a href="#" onClick="deluser('${role.roleId}')" title="删除">删除</a> </app:auth>
				  
				</td>
			  </tr>
			</c:forEach>

  		</tbody>
	</table>
	
	 <div class="pagin">
    			
		<app:pageController name="pageController"  form="adminRoleListForm"/>
    </div>
	
    </form>
    </div>
  
</body>

</html>
