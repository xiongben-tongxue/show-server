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
		var result = true;
		
		var opt = '${opt}';
		var oldVer = '${config.version}';
		var version = $('#version').val();
		if(opt=="configDoEdit" && oldVer == "0"){
			document.forms[0].submit();
		}else{
			if (version == '0' || $.trim(version)=='') {
				alert("请填写版本号！");
				result =  false;
				return;
			}

			if (oldVer != version) {
				$.ajax({
					type : 'POST',
					url : '/system/checkSys.do',
					data : 'version='+version,
					dataType : 'json',
					async: false,
					success : function(data) {
						if (data.status == "1") {
							alert("版本号重复，请重新填写！");
							result =  false;
						}
					},
					error : function(data) {
						alert("系统错误！");
						result =  false;
					}
				});
			}
			
			if(result==true)
			{
				document.forms[0].submit();
			}
		}
	}
</script>
</head>

<body>

	<div class="place">
		<span>位置：</span>
		<app:nav />
	</div>
	<div class="rightinfo">
		<form action="/system/${opt}.do" method="post" name="configForm">
			<c:if test="${opt=='configDoEdit'}">
			<input type="hidden" name="configId" value="${configId}" />
			</c:if>
			<table class="imgtable">
				<thead>
					<tr>
						<th>配置项</th>
						<th>更改配置</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>版本号</td>
						<td><cite><input id="version" name="version"
								type="text" value="${config.version}" /></cite></td>
					</tr>
					
					<tr>
						<td>视频码率</td>
						<td><cite><input name="bitrate" type="text"
								value="${config.bitrate}" size=8/></cite> kb</td>
					</tr>
					<tr>
						<td>视频帧率</td>
						<td><cite><input name="frame" type="text"
								value="${config.frame}" size=8/></cite></td>
					</tr>
					<tr>
						<td>视频分辨率</td>
						<td><cite><input name="width" type="text" value="${config.width}" size=8/>*<input name="height" type="text"value="${config.height}" size=8/></cite></td>
					</tr>
					
					<tr>
						<td>新浪微博分享文案</td>
						<td><cite><input name="shareSina" type="text"
								value="${config.shareSina}" size=70/></cite></td>
					</tr>
					<tr>
						<td>QQ分享文案</td>
						<td><cite><input name="shareQq" type="text"
								value="${config.shareQq}" size=70/></cite></td>
					</tr>
					<tr>
						<td>QQ空间分享文案</td>
						<td><cite><input name="shareQqzone" type="text"
								value="${config.shareQqzone}" size=70/></cite></td>
					</tr>
					<tr>
						<td>微信分享文案</td>
						<td><cite><input name="shareWx" type="text"
								value="${config.shareWx}" size=70/></cite></td>
					</tr>
					<tr>
						<td>微信朋友圈分享文案</td>
						<td><cite><input name="shareWxPyq" type="text"
								value="${config.shareWxPyq}" size=70/></cite></td>
					</tr>
					
						
					<tr>
						<td>支付宝支付开关</td>
						<td><cite><input name="alipay" type="radio" value="0"
								<c:if test="${config.alipay==0}">checked="checked" </c:if> />支付宝支付关闭&nbsp;&nbsp;&nbsp;&nbsp;<input
								name="alipay" type="radio" value="1"
								<c:if test="${config.alipay==1}">checked="checked" </c:if> />支付宝支付开启</cite></td>
					</tr>
					<tr>
						<td>微信支付开关</td>
						<td><cite><input name="weixinpay" type="radio"
								value="0"
								<c:if test="${config.weixinpay==0}">checked="checked" </c:if> />微信支付关闭&nbsp;&nbsp;&nbsp;&nbsp;<input
								name="weixinpay" type="radio" value="1"
								<c:if test="${config.weixinpay==1}">checked="checked" </c:if> />微信支付开启</cite></td>
					</tr>
					
					<tr>
						<td>苹果支付开关</td>
						<td><cite><input name="applepay" type="radio" value="0"
								<c:if test="${config.applepay==0}">checked="checked" </c:if> />苹果支付关闭&nbsp;&nbsp;&nbsp;&nbsp;<input
								name="applepay" type="radio" value="1"
								<c:if test="${config.applepay==1}">checked="checked" </c:if> />苹果支付开启</cite></td>
					</tr>
					
					<tr>
						<td>广告开关</td>
						<td><cite><input name="showAd" type="radio"
								value="0"
								<c:if test="${config.showAd==0}">checked="checked" </c:if> />广告关闭&nbsp;&nbsp;&nbsp;&nbsp;<input
								name="showAd" type="radio" value="1"
								<c:if test="${config.showAd==1}">checked="checked" </c:if> />广告开启</cite></td>
					</tr>
					
					<tr>
						<td>QQ登陆开关</td>
						<td><cite><input name="showQQLogin" type="radio"
								value="0"
								<c:if test="${config.showQQLogin==0}">checked="checked" </c:if> />QQ登陆关闭&nbsp;&nbsp;&nbsp;&nbsp;<input
								name="showQQLogin" type="radio" value="1"
								<c:if test="${config.showQQLogin==1}">checked="checked" </c:if> />QQ登陆开启</cite></td>
					</tr>
					
					<tr>
						<td>兑换按钮开关</td>
						<td><cite><input name="showExchange" type="radio"
								value="0"
								<c:if test="${config.showExchange==0}">checked="checked" </c:if> />兑换按钮关闭&nbsp;&nbsp;&nbsp;&nbsp;<input
								name="showExchange" type="radio" value="1"
								<c:if test="${config.showExchange==1}">checked="checked" </c:if> />兑换按钮开启</cite></td>
					</tr>
					
				</tbody>
			</table>

			<ul class="forminfo">
				<li><label>&nbsp;</label><input onclick="checkform();" name=""
					type="button" class="btn" value="确认保存" /></li>
			</ul>
		</form>
	</div>
</body>
</html>