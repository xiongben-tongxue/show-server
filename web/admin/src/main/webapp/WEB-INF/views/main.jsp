<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/highcharts.js"></script>

<script type="text/javascript">

$(function () {
    $('#container').highcharts({
        title: {
            text: '摘要统计',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: beike.tv',
            x: -20
        },
        xAxis: {
            categories: ${time}
        },
        yAxis: {
            title: {
                text: '数量'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '次'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '播放',
            data: ${play}
        },{
            name: 'PV',
            data: ${pv}
        },{
            name: '登录',
            data: ${login}
        },{
            name: '注册',
            data: ${register}
        },{
            name: '分享',
            data: ${share}
        },{
            name: '直播',
            data: ${videos}
        },{
            name: '峰值',
            data: ${liveMax}
        }]
        
    });
});
</script>


</head>


<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    </ul>
    </div>
    
    <div class="mainindex">
    
    
    <div class="welinfo">
    <span><img src="../images/sun.png" alt="天气" /></span>
    <b>欢迎使用ShowLive运营管理后台</b>(${user.userView.name})
    <a href="/user/updatePwd.do">修改密码</a>
    </div>
    
  
    <div class="xline"></div>
    <c:if test="${user.userView.userType==0}">
    		<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	</c:if>
</body>

</html>
