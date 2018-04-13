
	var uploadImage = function(element, op){
		opt = {
		};
		
		this.opt = $.extend(opt,op);

		this.element = $(element);

		this._init();
	};

	uploadImage.prototype = {
		_init : function(){
			this._insertFrom();
			this._event();

			this.element.addClass('formbody');
		},

		_event : function(){
			var that = this,
				opt = that.opt;

			//选择完图片时
			$('body').on('change', '.dfinput', function(){
				if($(this).val() == ''){
					$(that).trigger('onNone');
					return;
				}

				$('#uploadImageForm').submit();
			});

			//IFRAME提交后LOAD完以后
			$('#uploadImageIframe').load(function(){
				var str = $(this).contents().find('body').text();
				if($.trim(str) == ''){
					return;
				}
				var json = $.parseJSON($(this).contents().find('body').text());

				if(json.status == 1){
					$(that).trigger('onDone', json);
				}else{
					$(that).trigger('onFail', json);
				}
			});
		},

		//插入FORM
		_insertFrom : function(){
			var that = this;
				html = that._getFormHtml();

			that.element.append(html);
		},

		//重置form
		updateForm : function(){
			var that = this;

			that.element.find('#uploadImageForm').remove();
			that._insertFrom();
		},

		//获取Form
		_getFormHtml : function(){
			var randomNum = Math.random();

			return '<form id="uploadImageForm" target="uploadImageIframe"'+
					'method="post" enctype="multipart/form-data" '+
					'action="/upload/doUpload.do?n='+randomNum+'" autocomplete="off">'+
					'<input title="点击上传" exclude="true" class="dfinput" type="file" value="" name="fileData" /></form>'+
					'<iframe style="display:none;" id="uploadImageIframe" name="uploadImageIframe"></iframe>';
		}
	};
