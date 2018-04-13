<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="../common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统配置</title>
<link href="../../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../js/jquery.js"></script>
<script>
	function checkform() {
		document.forms[0].submit();
	}
</script>
</head>

<body>

	<div class="place">
		<span>位置：</span>
		<app:nav />
	</div>
	<div class="rightinfo">
		<form name="configForm" action="/system/configList.do">
			<input type="hidden" name="currentPage" value="1" />

			<div class="tools">

				<ul class="toolbar">
					<app:auth url="/system/configAdd.do">
						<a href="/system/configAdd.do"><li class="click"><span><img
									src="../../images/t01.png" /></span>添加</li></a>
					</app:auth>

				</ul>


			</div>
			<table class="tablelist">
					<tr>
						<th>版本号</th>
						<th>视频码率</th>
						<th>视频帧数</th>
						<th>视频分辨率</th>
						
						<th>支付宝支付开关</th>
						<th>微信支付开关</th>
						<th>广告开关</th>
						<th>操作</th>
					</tr>

					<c:forEach items="${configList}" var="config">
						<tr>
						
							<td><c:if test="${config.version=='0'}">默认配置</c:if>
								<c:if test="${config.version!='0'}">${config.version}</c:if>
							</td>
							
							<td>${config.bitrate} kb</td>
							<td>${config.frame}</td>
							<td>${config.width}*${config.height}</td>
							
							<td><c:if test="${config.alipay==0}">支付宝支付关闭</c:if><c:if test="${config.alipay==1}">支付宝支付开启</c:if></td>
							<td><c:if test="${config.weixinpay==0}">微信支付关闭</c:if><c:if test="${config.weixinpay==1}">微信支付开启</c:if></td>
							<td><c:if test="${config.showAd==0}">广告关闭</c:if><c:if test="${config.showAd==1}">广告开启</c:if></td>
							
							<td><app:auth url="/system/configEdit.do">
									<a href="/system/configEdit.do?configId=${config.configId}" title="编辑">编辑</a> </app:auth>
								<app:auth url="/system/configDelete.do">
									<c:if test="${config.version!='0'}">| <a href="/system/configDelete.do?configId=${config.configId}" title="删除">删除</a></c:if>
								</app:auth></td>
						</tr>
					</c:forEach>

			</table>
			<div class="pagin">

				<app:pageController name="pageController" form="configForm" />
			</div>
		</form>
	</div>
</body>
</html>