<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="../common/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>文件上传</title>
	<link href="../../css/style.css" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="../../css/swf.css"  />
	<script type="text/javascript" src="../../js/jquery.js"></script>
	<script type="text/javascript" src="../../js/swfupload.js"></script>
	<script type="text/javascript" src="../../js/handlers.js"></script>
	<script type="text/javascript" src="../../js/fileprogress.js"></script>
	<script type="text/javascript" src="../../js/cptUploadImage.js"></script>
	<script>
	var swfu;
	$(document).ready(function(){
		var uploadImg = new uploadImage('#spanButtonPlaceHolder');
		
		$(uploadImg).on('onDone', function(e, obj){
			$('#url').append('<br/>文件 '+obj.msg+'的地址 ：'+obj.href);
			uploadImage.updateForm();
		});

		$(uploadImg).on('onFail', function(e, obj){
			alert('上传失败请重试');		
			uploadImage.updateForm();
		});
		
		/*
			swfu = new SWFUpload({
			//上传action
			upload_url: "updAct.do?m=upload",
			//设置了POST信息中上传文件的name值
			file_post_name: "Filedata",
			//上传文件上限
			file_size_limit : "100 MB",
			//允许上传文件的类型
			file_types : "*.*",	
			//设置文件选择对话框中显示给用户的文件描述
			file_types_description : "All Files",
			//设置SWFUpload实例允许上传的最多文件数量，同时也是设置对象中file_queue_limit属性的上限。
			file_upload_limit : 100,
			//设置文件上传队列中等待文件的最大数量限制。
			file_queue_limit : 0,
			
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,
			queue_complete_handler : queueComplete,	// Queue plugin event


			//上传按钮设置
			button_image_url: "images/TestImageNoText_65x29.png",
			button_width: "65",
			button_height: "29",
			button_placeholder_id: "spanButtonPlaceHolder",
			button_text: '<span class="theFont">选择文件</span>',
			button_text_style: ".theFont { font-size: 12; }",
			button_text_left_padding: 12,
			button_text_top_padding: 3,


			
			//flash设置
			flash_url : "flash/swfupload.swf",
			
			custom_settings : {
				progressTarget : "fsUploadProgress",
				cancelButtonId : "btnCancel"
			},
			debug: false

		});
		*/
		});
	/*
	function uploadSuccess(file, serverData) {
		try {
			var progress = new FileProgress(file, this.customSettings.progressTarget);
			progress.setComplete();
			progress.setStatus("Complete.");
			progress.toggleCancel(false);
			var obj = eval('('+serverData+')');
			
			if (obj.result){
				this.customSettings.upload_successful = true;
				document.getElementById("url").innerHTML = document.getElementById("url").innerHTML+'<br/>'+'文件 '+obj.fileName+' 的地址 ： '+obj.url;
			}else{
				this.customSettings.upload_successful = false;
			}

		} catch (ex) {
			this.debug(ex);
		}
	}*/
	
	</script>

</head>


<body>

	<div class="place">
		<span>位置：</span>
		<app:nav />
	</div>

<br/>
 
		
			<div id="divStatus"></div>
			<div>
				<span id="spanButtonPlaceHolder"></span>&nbsp;&nbsp;
				<!-- <input id="btnCancel" type="button" value="取消" onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 29px;" /> -->
			</div>
			<div id="fsUploadProgress"></div>
			<div  id="url" ></div>


</body>
</html>