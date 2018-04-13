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

function deluser(userName){
	if(confirm('请确认是否删除！')){
			$.ajax({
				url:'/user/delete.do',
				type: 'POST',
				data:'userName='+userName,
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
        <app:auth url="/user/add.do"><a href="/user/add.do"><li class="click"><span><img src="../../images/t01.png" /></span>添加</li></a></app:auth>
        
        </ul>
        
    
    </div>
    
    
	<form name="adminUserListForm" action="/user/list.do">
	<input type="hidden" name="currentPage" value="1"/>
	
	<table class="tablelist" >
	<thead>
	  <tr>
    	<th>用户名</th>
        <th>姓名</th>
        <th>状态</th>
        <th>账号类型</th>
        <th>创建时间</th>
        <th>操作</th>
	  </tr>

	  </thead>
	  	<tbody>
			<c:forEach items="${userList}" var="user">
			  <tr>
			   
			    <td>
			      ${user.userName}
			    </td>
			    <td>${user.name}</td>
				<td>
					<c:if test="${user.status=='0'}">可用</c:if>
					<c:if test="${user.status=='1'}">禁用</c:if>
					
				</td>
				<td>
					<c:if test="${user.userType=='0'}">公司内部</c:if>
					<c:if test="${user.userType=='1'}"><font color="blue">家族账号</font></c:if>
					
				</td>
				
				<td>${fns:intDate2String(user.createTime, "yyyy-MM-dd HH:mm:ss")}</td>
				<td>
				  <app:auth url="/user/edit.do"><a href="/user/edit.do?userName=${user.userName}" title="编辑">编辑</a> |</app:auth>
				  <app:auth url="/user/delete.do"><a href="#" onClick="deluser('${user.userName}')" title="删除">删除</a> </app:auth>
				  
				</td>
			  </tr>
			</c:forEach>

  		</tbody>
	</table>
	
	 <div class="pagin">
    			
		<app:pageController name="pageController"  form="adminUserListForm"/>
    </div>
	
    </form>
    </div>
  
</body>

</html>
