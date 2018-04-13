<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="../common/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户排行</title>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/jquery.js"></script>
<script language="javascript" src="../../js/My97DatePicker/WdatePicker.js"></script>


</head>


<body>

	<div class="place">
    <span>位置：</span>
    <app:nav/>
    </div>
    
    <div class="rightinfo">
    
   
    
    
	<form name="rankListForm" action="/rank/userList.do">
	<input type="hidden" name="currentPage" value="1"/>
	
	<ul class="forminfo">
    
	
    <li><label>分类</label> 
    <select name="type"  class="dfinput" >
    	<option value="0" <c:if test="${type==0}">selected="selected"</c:if>>消费</option>
		<option value="1" <c:if test="${type==1}">selected="selected"</c:if>>收礼</option>
		<option value="2" <c:if test="${type==2}">selected="selected"</c:if>>充值</option>
		<option value="3" <c:if test="${type==3}">selected="selected"</c:if>>点赞</option>
	</select>
	</li>
	<li>
	<label>日期</label> 
	<input type="text" name="beginTime" id="beginTime" class="dfinput"   onfocus="WdatePicker({el:'beginTime',dateFmt: 'yyyyMMdd'});"  value="${beginTime}"/>
	到
	<input type="text" name="endTime" id="endTime" class="dfinput"  onfocus="WdatePicker({el:'endTime',dateFmt: 'yyyyMMdd'});"  value="${endTime}"/>
	

	<label>&nbsp;</label><input  name="" type="submit" class="btn" value="查询"/>   
	</li>
    </ul>
	
    
	<table class="tablelist" >
	<thead>
	  <tr>
    	<th>uid</th>
        <th>昵称</th>
        <th>靓号</th>
        <c:if test="${type==0}"><th>消费ShowCoin数</th></c:if>
        <c:if test="${type==1}"><th>收到珍珠数</th></c:if>
        <c:if test="${type==2}"><th>充值人民币(元)</th></c:if>
        <c:if test="${type==3}"><th>收到点赞数</th></c:if>
	  </tr>

	  </thead>
	  	<tbody>
			<c:forEach items="${userRankList}" var="rank">
			  <tr>
			    
			    <td>${rank.uid}</td>
			    <td>${rank.nickName} <c:if test="${rank.isRobot==1}"><font color="red">【假人】</font></c:if></td>
			    <td>${rank.pid}</td>
			    <td>${rank.number}</td>
			  </tr>
			</c:forEach>

  		</tbody>
	</table>
	
	 <div class="pagin">
    			
		<app:pageController name="pageController"  form="rankListForm"/>
    </div>
	
    </form>
    </div>
  
</body>

</html>
