/*! mm | Date: Mon Oct 22 2012 12:01:02 GMT+0800 (CST) */

typeof _e=="undefined"&&(_e=function(){},window.console&&(_e=function(a){console.info(a)})),window.sohuHDApple={};if(typeof window.ActiveXObject=="function")try{var m=new ActiveXObject("SoHuVA.SoHuDector");m&&m.isSoHuVaReady()&&(sohuHDApple.hasSoHuVA=!0,m.StartSoHuVA())}catch(e){}var SWFObject=function(a,b,c,d,e,f,g,h,i,j,k){s=this;if(!document.createElement||!document.getElementById)return;s.movie=s.src=a,s.id=b?b:"",s.width=c?c:0,s.height=d?d:0,s.ver=e?e.replace(".",","):"7,0,19,0",s.ver=="9,0,145"&&(s.ver="9,0,115"),s.bgcolor=f?f:"",s.quality=h?h:"high",s.useExpressInstall=typeof g=="boolean"?g:!1,s.xir=i?i:window.location,s.redirectUrl=j?j:window.location,s.detectKey=typeof k=="boolean"?k:!0,s.pluginspage="http://www.macromedia.com/go/getflashplayer",s.type="application/x-shockwave-flash",s.classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000",s.codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version="+s.ver,s.objAttrs={},s.embedAttrs={},s.params={},s.flashVarsObj={},s._setAttribute("id",s.id),s.objAttrs.classid=s.classid,s._setAttribute("codebase",s.codebase),s._setAttribute("pluginspage",s.pluginspage),s._setAttribute("type",s.type),s._setAttribute("width",s.width),s._setAttribute("height",s.height),s._setAttribute("movie",s.movie),s._setAttribute("quality",s.quality),s._setAttribute("bgcolor",s.bgcolor)};SWFObject.prototype={getFlashHtml:function(a){var b=this,c=[],d=[];for(var a in b.flashVarsObj)d.push("&",a,"=",b.flashVarsObj[a]);if(document.all){c.push("<object ");for(var a in b.objAttrs)c.push(a,'="',b.objAttrs[a],'"'," ");c.push(">\n");for(var a in b.params)c.push('<param name="',a,'" value="',b.params[a],'" />\n');d.length&&c.push('<param name="flashvars" value="',d.join(""),'" />\n'),c.push("</object>")}else{c.push("<embed "),b.embedAttrs.FlashVars=d.join("");for(var a in b.embedAttrs)c.push(a,'="',b.embedAttrs[a],'"'," ");c.push("></embed>")}return c.join("")},_setAttribute:function(a,b){var c=this;if(typeof a=="undefined"||a==""||typeof b=="undefined"||b=="")return;var a=a.toLowerCase();switch(a){case"classid":break;case"pluginspage":c.embedAttrs[a]=b;break;case"src":case"movie":c.embedAttrs.src=b,c.params.movie=b;break;case"codebase":c.objAttrs[a]=b;break;case"onafterupdate":case"onbeforeupdate":case"onblur":case"oncellchange":case"onclick":case"ondblClick":case"ondrag":case"ondragend":case"ondragenter":case"ondragleave":case"ondragover":case"ondrop":case"onfinish":case"onfocus":case"onhelp":case"onmousedown":case"onmouseup":case"onmouseover":case"onmousemove":case"onmouseout":case"onkeypress":case"onkeydown":case"onkeyup":case"onload":case"onlosecapture":case"onpropertychange":case"onreadystatechange":case"onrowsdelete":case"onrowenter":case"onrowexit":case"onrowsinserted":case"onstart":case"onscroll":case"onbeforeeditfocus":case"onactivate":case"onbeforedeactivate":case"ondeactivate":case"width":case"height":case"align":case"vspace":case"hspace":case"class":case"title":case"accesskey":case"name":case"id":case"tabindex":case"type":c.objAttrs[a]=c.embedAttrs[a]=b;break;default:c.params[a]=c.embedAttrs[a]=b}},_getAttribute:function(a){var b=this;return a=a.toLowerCase(),typeof b.objAttrs[a]!="undefined"?b.objAttrs[a]:typeof getA.params[a]!="undefined"?b.params[i]:typeof getA.embedAttrs[a]!="undefined"?b.embedAttrs[a]:null},setAttribute:function(a,b){this._setAttribute(a,b)},getAttribute:function(a){return this._getAttribute(n)},addVariable:function(a,b){var c=this;typeof b!="undefined"&&b!=""&&(c.flashVarsObj[a]=b)},getVariable:function(a){var b=this;return b.flashVarsObj[a]},addParam:function(a,b){this._setAttribute(a,b)},getParam:function(a){return this._getAttribute(a)},write:function(a){var b=this;typeof a=="string"?document.getElementById(a).innerHTML=b.getFlashHtml(a):typeof a=="object"&&(a.innerHTML=b.getFlashHtml(a))},writeCode:function(a){var b=this;document.write(b.getFlashHtml())},playerVer:function(){if(document.all){var a=new ActiveXObject("ShockwaveFlash.ShockwaveFlash");a&&(flashVersion=parseInt(a.GetVariable("$version").split(" ")[1].split(",")[0]))}else if(navigator.plugins&&navigator.plugins.length>0){var a=navigator.plugins["Shockwave Flash"];if(a){var b=a.description.split(" ");for(var c=0;c<b.length;++c){if(isNaN(parseInt(b[c])))continue;flashVersion=parseInt(b[c])}}}return{v:flashVersion}}},String.prototype._shift_en=function(a){var b=a.length,c=0;return this.replace(/[0-9a-zA-Z]/g,function(d){var e=d.charCodeAt(0),f=65,g=26;e>=97?f=97:e<65&&(f=48,g=10);var h=e-f;return String.fromCharCode((h+a[c++%b])%g+f)})},function(a,b,c){var d=-1,e=!1;document.all&&(e=!0);var f=function(a){return document.getElementById(a||"")},g=(new Date).getTime(),h=function(){return g+=1,g},i=["qf1.hd.sohu.com.cn","qf2.hd.sohu.com.cn"];i=i[Math.round(Math.random()*10)%2],sohuHDApple.pingbackArr=[];var j=function(a){var b=new Image;b.src=a,sohuHDApple.pingbackArr.push(b)},k=function(a,b,c){if(typeof b=="undefined"){var d=(new RegExp("(?:^|; )"+a+"=([^;]*)")).exec(document.cookie);return d?d[1]||"":""}c=c||{},b===null&&(b="",c.expires=-1);var e="";if(c.expires&&(typeof c.expires=="number"||c.expires.toUTCString)){var f;typeof c.expires=="number"?(f=new Date,f.setTime(f.getTime()+c.expires*24*60*60*1e3)):f=c.expires,e="; expires="+f.toUTCString()}var g=c.path?"; path="+c.path:"",h=c.domain?"; domain="+c.domain:"",i=c.secure?"; secure":"";document.cookie=[a,"=",b,e,g,h,i].join("")},l=function(a,c){c=escape(unescape(c));var d=new Array,e=null;if(a.nodeName=="#document")b.location.search.search(c)>-1&&(e=b.location.search.substr(1,b.location.search.length).split("&"));else if(typeof a.src!="undefined"){var f=a.src;if(f.indexOf("?")>-1){var g=f.substr(f.indexOf("?")+1);e=g.split("&")}}else{if(typeof a.href=="undefined")return null;var f=a.href;if(f.indexOf("?")>-1){var g=f.substr(f.indexOf("?")+1);e=g.split("&")}}if(e==null)return null;for(var h=0;h<e.length;h++)escape(unescape(e[h].split("=")[0]))==c&&d.push(e[h].split("=")[1]);return d.length==0?null:d.length==1?d[0]:d},m=function(a,b,c,d){var e=document.head||document.getElementsByTagName("head")[0]||document.documentElement,f=document.createElement("script");f.src=a,f.charset=c||"GBK";var g=!1;d&&(f.onerror=d),f.onload=f.onreadystatechange=function(){!g&&(!this.readyState||this.readyState==="loaded"||this.readyState==="complete")&&(g=!0,b&&b(),f.onload=f.onreadystatechange=null,e&&f.parentNode&&e.removeChild(f))},e.insertBefore(f,e.firstChild)};a.prototype.qfVV=function(a){var b=this.flashVarsObj.sid,c=this.flashVarsObj.vid,d=this.flashVarsObj.nid,e=this.flashVarsObj.hotVrs;j(["http://",i,"/dov.do?method=stat&pt=4&seekto=0","&error=0&code=2&allno=0&vvmark=1","&sid=",b,"&vid=",c,"&nid=",d,"&totTime=",a,"&ref=",encodeURIComponent(location.href),"&dom=",encodeURIComponent(e),"&t=",h()].join(""))},a.prototype.qfAbort=function(){var a=this.flashVarsObj.sid,b=this.flashVarsObj.vid,c=this.flashVarsObj.nid,d=this.flashVarsObj.hotVrs;j(["http://",i,"/dov.do?method=stat&pt=4&seekto=0","&code=4&error=800&allno=1&drag=-1",,"&sid=",a,"&vid=",b,"&nid=",c,"&ref=",encodeURIComponent(location.href),"&dom=",encodeURIComponent(d),"&t=",h()].join(""))},a.prototype.qfError=function(){var a=this.flashVarsObj.sid,b=this.flashVarsObj.vid,c=this.flashVarsObj.nid,d=this.flashVarsObj.hotVrs;j(["http://",i,"/dov.do?method=stat&pt=4&seekto=0","&error=500&code=2&allno=1&vvmark=0",,"&sid=",a,"&vid=",b,"&nid=",c,"&ref=",encodeURIComponent(location.href),"&dom=",encodeURIComponent(d),"&t=",h()].join(""))},a.prototype.qfBuffer=function(a){if(a!=1&&a!=4)return;_e("buffer");var b=this.flashVarsObj.sid,c=this.flashVarsObj.vid,d=this.flashVarsObj.nid,e=this.flashVarsObj.hotVrs;j(["http://",i,"/dov.do?method=stat&pt=4&seekto=0","&code=5&bufno=1&allbufno=",a,"&sid=",b,"&vid=",c,"&nid=",d,"&ref=",encodeURIComponent(location.href),"&dom=",encodeURIComponent(e),"&t=",h()].join(""))},a.prototype.getJSONP=function(a,c){var d=this,e="jsonp"+h();a.indexOf("callback=?")>-1&&(a=a.replace("callback=?","callback="+e),b[e]=function(a){c(a);try{delete b[e]}catch(d){}},m(a+"&_="+h(),null,null,d.qfError))};var n=function(){if(this==b)throw new Error(0,"HttpRequest is unable to call as a function.");var a=this,c=!1,d=!1,e,f=function(){a.onreadystatechange&&a.onreadystatechange.call(e);if(e.readyState==4){if(Number(e.status)>=300){a.onerror&&a.onerror.call(e,new Error(0,"Http error:"+e.status+" "+e.statusText)),d?e.onreadystatechange=Function.prototype:e.onReadyStateChange=Function.prototype,e=null;return}a.status=e.status,a.statusText=e.statusText,a.responseText=e.responseText,a.responseBody=e.responseBody,a.responseXML=e.responseXML,a.readyState=e.readyState,d?e.onreadystatechange=Function.prototype:e.onReadyStateChange=Function.prototype,e=null,a.onfinish&&a.onfinish()}},g=function(){var a;try{e=new b.XMLHttpRequest,d=!0}catch(a){var c=["MSXML2.XMLHttp.6.0","MSXML2.XMLHttp.3.0","MSXML2.XMLHttp.5.0","MSXML2.XMLHttp.4.0","Msxml2.XMLHTTP","MSXML.XMLHttp","Microsoft.XMLHTTP"],f=function(){var a;for(var b=0;b<c.length;b++){try{var e=new ActiveXObject(c[b]);d=!1}catch(a){continue}return e}throw{message:"XMLHttp ActiveX Unsurported."}};try{e=new f,d=!1}catch(a){throw new Error(0,"XMLHttpRequest Unsurported.")}}};g(),this.abort=function(){e.abort()},this.getAllResponseHeaders=function(){e.getAllResponseHeaders()},this.getResponseHeader=function(a){e.getResponseHeader(bstrHeader)},this.open=function(b,d,f,g,h){c=f;try{e.open(b,d,f,g,h)}catch(i){if(a.onerror)a.onerror(i);else throw i}},this.send=function(b){try{d?e.onreadystatechange=f:e.onReadyStateChange=f,e.send(b),c||(this.status=e.status,this.statusText=e.statusText,this.responseText=e.responseText,this.responseBody=e.responseBody,this.responseXML=e.responseXML,this.readyState=e.readyState,d?e.onreadystatechange=Function.prototype:e.onReadyStateChange=Function.prototype,e=null)}catch(g){if(a.onerror)a.onerror(g);else throw g}},this.setRequestHeader=function(a,b){e.setRequestHeader(a,b)}},p=function(a,b){var c=new n;c.onfinish=function(){var a=(new Function("return "+this.responseText))();b(a)},c.open("get",a,!0),c.send(null)},q=function(a){a=a||{};var b=a.width||"100%",c=a.height||"100%",d=a.noControls,e=a.poster,f=a.preload||"auto",g=a.noLoop,i=a.autoplay,j={};j.unCheckFile=a.unCheckFile;var k=document.createElement("div");k.style.display="none";var l=document.body||document.documentElement;l.appendChild(k),k.innerHTML=['<video style="background:#000;" id="video_',h(),'"',' width="',b,'" height="',c,'"',d?"":" controls",i?" autoplay":"",' preload="',f,'"',">",'<p width="',b,'" height="',c,'">your device do not surpport video</p>',"</video>"].join(""),j.player=k.getElementsByTagName("video")[0];if(!j.player)j.tips=k.getElementsByTagName("p")[0],j.noplayer={},j.attr=function(a,b){if(b)j.noplayer[a]=b;else return j.noplayer[a]};else{j.attr=function(a,b){if(b)j.player[a]=b;else return j.player[a]};var m=["loadstart","progress","suspend","abort","error","stalled","play","pause","loadedmetadata","loadeddata","waiting","playing","canplay","canplaythrough","seeking","seeked","timeupdate","ended","ratechange","durationchange","volumechange"];j.eventsHandler={trigger:function(a,b){var c=this[a];if(c&&c.length)for(var d=0;d<c.length;++d)c[d].callback.call(j.player,b),c[d].once&&c.splice(d,1)}};var n=function(a){var b=this;return b.eventsHandler[a]=[],b.player.addEventListener?b.player.addEventListener(a,function(c){b.eventsHandler.trigger(a,c)}):b.player.attachEvent&&b.player.attachEvent(a,function(c){b.eventsHandler.trigger(a,c)}),b[a]=function(c,d){if(c){var e={};e.callback=c,d&&(d.namespace&&(e.namespace=d.namespace),e.once=d.once),b.eventsHandler[a].push(e)}else b.eventsHandler.trigger(a,"this is self event")},b};for(var o=0;o<m.length;++o){var p=m[o];n.call(j,p)}j.events=[],j.bind=function(a,b,c){a=a.split("."),j[a[0]](b,{namespace:a[1]||"",once:c.once})},j.unbind=function(a){a=a.split(".");var b=j.eventsHandler[a[0]];if(b&&b.length&&a[1])for(var c=0;c<b.length;++c)b[c].namespace==a[1]&&b.splice(c,1)},j.buffered=function(){var a={start:0,end:0},b=this.player;if(b.buffered.length)for(var c=0;c<b.buffered.length;c++)a.start=Math.round(b.buffered.start(c)),a.end=Math.round(b.buffered.end(c));return a},j.playVideo=function(){var a=this.attr("playlist");if(a instanceof Array)return a&&a.length&&(/mp4|ogg|m3u8/ig.test(a[0].url)||this.unCheckFile?(this.attr("src",a[0].url),this.player.innerHTML=['<a href="',a[0].url,'"',' style="width:',this.player.parentNode.clientWidth,"px;height:",this.player.parentNode.clientHeight,"px;",'display:block;background:url(http://tv.sohu.com/upload/touch/skin/images/play@2x.png) no-repeat center center;"></a>'].join("")):this.player.parentNode.innerHTML="\u5bf9\u4e0d\u8d77\uff0c\u6b64\u89c6\u9891\u6682\u4e0d\u652f\u6301\u60a8\u7684\u8bbe\u5907\u89c2\u770b!",a.splice(0,1),this.attr("playlist",a)),this;throw new Error("html5 video playlist must be array")}}return j};(function(a,b){var c=b.navigator.userAgent;a.prototype.isMacSafari=/Macintosh/ig.test(c)&&/Safari/ig.test(c)&&!/Chrome/ig.test(c),a.prototype.isIpad=/ipad/ig.test(c)||/lepad_hls/ig.test(c)||/SonyDTV/ig.test(c),a.prototype.isIphone=/\(i[^;]+;( U;)? CPU iphone.+Mac OS X/ig.test(c),a.prototype.isIpod=/\(i[^;]+;( U;)? CPU ipod.+Mac OS X/ig.test(c),a.prototype.isIOS=/iphone|ipod|ipad/ig.test(c),a.prototype.isIOSLow=!1,a.prototype.enforceFlash=!1,a.prototype.enforceMP4=!1,a.prototype.switchHtml="",a.prototype.isIOS||(a.prototype.isSBDevice=/MQQBrowser|GT-N7000|ucweb/ig.test(c),b.uc_browser=="1"&&(a.prototype.isSBDevice=!0));if(/iphone|ipod/ig.test(c)){var d=c.indexOf(" OS ")+4,e=parseFloat(c.substring(d,c.indexOf(" ",d)).replace(/_/g,"."));e<4.2&&(a.prototype.isIOSLow=!0)}a.prototype.isSogou=/SE \d+\.X/.test(c),a.prototype.isIEMobile=/IEMobile/.test(c),a.prototype.isAndroid=/android/ig.test(c),a.prototype.isAndroidLow=!1;if(a.prototype.isAndroid){var f=c.match(/Android (\d+\.\d+)/)||[0,"0.0"];a.prototype.androidVer=f,f=parseFloat(f[1]),f<2.3&&(a.prototype.isAndroidLow=!0)}})(a,b),a.prototype.eventObj={},a.prototype.bindEvents=function(a,b){a&&b&&(this.eventObj[a]=b)},a.prototype.getFlashHtml=function(c){var f=this,g=[],h=[],i={"#super":"21","#high":"1","#common":"2"};i=i[location.hash]||"",i&&(f.flashVarsObj.co=i);var j=location.href;j.indexOf("sohu.com")>-1&&(f.flashVarsObj.ua=j.split("sohu.com")[0]),f.flashVarsObj.api_key||(f.flashVarsObj.api_key=l(document,"api_key")),k("machtml5new")&&(f.isIpad=!0),f.isSBDevice=f.isSBDevice||!f.isIOS&&f.flashVarsObj.enforceMP4||f.isAndroidLow;var m=!f.checkFlash(w);if(!f.enforceFlash&&(f.isIpad||f.isIphone||f.isIpod||f.isIEMobile||f.isAndroid||f.isSBDevice))return f.isIOSLow&&alert("\u60a8\u7684iOS\u7248\u672c\u4f4e\u4e8e4.2,\u8bf7\u5347\u7ea7\u7cfb\u7edf\u7248\u672c\u4ee5\u83b7\u5f97\u66f4\u6d41\u7545\u89c2\u770b\u4f53\u9a8c."),f.getHTML5(c);f.isAndroid&&(f.flashVarsObj.skinNum="-1",f.flashVarsObj.os="android",f.flashVarsObj.oad="",f.flashVarsObj.ead="",f.flashVarsObj.co="2"),typeof f.flashVarsObj["tlogoad"]=="undefined"&&(f.flashVarsObj.tlogoad="http://tv.sohu.com/upload/swf/empty.swf|http://tv.sohu.com/upload/swf/time.swf");try{if(m){var n=c,p=null;typeof n=="string"?p=document.getElementById(n):typeof n=="object"&&(p=n);var q=["<a href='",e?"http://220.181.61.152/fp10_archive/10r18_2/install_flash_player_10_active_x.exe":"http://220.181.61.152/fp10_archive/10r18_2/install_flash_player.exe","' >\u641c\u72d0\u4e0b\u8f7d</a>"].join(""),r="<a href='http://get.adobe.com/flashplayer/' target='_blank' >\u5b98\u65b9\u4e0b\u8f7d</a>",s="\uff0c\u8bf7\u9009\u62e9\u4e0b\u9762\u5730\u5740\u8fdb\u884c\u5b89\u88c5\uff08\u5b89\u88c5\u540e\u9700\u8981\u91cd\u542f\u6d4f\u89c8\u5668\uff09\u5982\u679c\u60a8\u786e\u5b9a\u5df2\u7ecf\u5b89\u88c5\uff0c\u8bf7 <a href='#' rel='play'>\u70b9\u51fb\u8fd9\u91cc\u5c1d\u8bd5\u64ad\u653e</a>",t=["<style>#",n," div{color:#FFF;width:325px;height:118px;padding:10px;background:#262626;border:1px solid #313131;position:absolute;top:50%;left:50%;margin:-64px auto 0 -170px;line-height:1.5em;}","#",n," a{text-decoration:underline;color:#00A2FF;} ","#",n," a:hover{text-decoration:none;color:#00A2FF;}","#",n," p{margin:8px 0;border-top:1px dashed #3C3C3C;border-bottom:1px dashed #3C3C3C;height:34px;line-height:34px;}","</style>"];arHtml=["<div id='_noFlv'>"];var u=f.DetectFlashVer(6,0,65);return d===-1?arHtml.push(["\u60a8\u53ef\u80fd\u6ca1\u6709\u5b89\u88c5FLASH",s,"<p>",q,"\uff08\u63a8\u8350\uff09\u3000\u3000"].join("")):(arHtml.push(["\u60a8\u7684FLASH\u7248\u672c\u4f4e\u4e8e",self.ver.replace(/,/g,"."),s,"<p>"].join("")),u?arHtml.push(["<a rel='update' href='javascript: flvAutoUpdate();'>\u81ea\u52a8\u5347\u7ea7</a>\uff08\u63a8\u8350\uff09\u3000\u3000",q].join("")):arHtml.push([q,"\uff08\u63a8\u8350\uff09"].join(""))),arHtml.push(["\u3000\u3000",r,"</p>\u5b89\u88c5\u5931\u8d25\uff1f<a href='http://tv.sohu.com/upload/hdfeedback/index.jsp?12' target='_blank'>\u70b9\u51fb\u8fd9\u91cc\u67e5\u770b\u89e3\u51b3\u65b9\u6848</a></div>"].join("")),p.innerHTML=[arHtml.join(""),t.join("")].join(""),self.update=function(){flvUrl="http://tv.sohu.com/upload/20090903hd/new.swf";var c=document.all?"ActiveX":"PlugIn",d=b.location;document.title=document.title.slice(0,47)+" - Flash Player Installation";var e=document.title,f=new a(flvUrl,"player",so_width,so_height,"6,0,65");f.addParam("FlashVars","MMredirectURL="+d+"&MMplayerType="+c+"&MMdoctitle="+e),f.addParam("wmode","Opaque"),f.addParam("align","middle"),f.addParam("allowscriptaccess","always"),f.addParam("type","application/x-shockwave-flash"),f.addParam("pluginspage","http://www.adobe.com/go/getflashplayer"),document.getElementById(n).innerHTML=f.getFlashHtml()},self.play=function(){var a=new Date;a.setFullYear(a.getFullYear()+1),k("tryPlay","1",{path:"/",domain:"tv.sohu.com",expires:a}),document.getElementById(n).innerHTML=self.getFlashHtml()},document.getElementById("_noFlv").onclick=function(a){o=a?a:b.event,o=o.target?o.target:o.srcElement,o.tagName=="A"&&o.rel!=""&&(self[o.rel].call(self),a&&a.preventDefault?a.preventDefault():b.event.returnValue=!1)},""}}catch(v){}f.isAndroid||(f.flashVarsObj.topBarFull=1);for(var w in f.flashVarsObj)h.push("&",w,"=",f.flashVarsObj[w]);g.push(f.switchHtml);if(document.all){g.push("<object ");for(var w in f.objAttrs)g.push(w,'="',f.objAttrs[w],'"'," ");g.push(">");for(var w in f.params)g.push('<param name="',w,'" value="',f.params[w],'" />');h.length&&g.push('<param name="flashvars" value="',h.join(""),'" />'),g.push("</object>")}else{g.push("<embed "),f.embedAttrs.FlashVars=h.join("");for(var w in f.embedAttrs)g.push(w,'="',f.embedAttrs[w],'"'," ");g.push("></embed>")}return g.join("")},a.prototype.getHTML5=function(){var a=this,c=a.flashVarsObj,d=k("SUV"),e="videobox_"+h(),g=!1,i=!1,l=c.api_key,n=c.ltype,o=c.autoplay;c.id&&(g=!0);if(n!=""&&typeof n!="undefined"){i=!0;if(!a.isIOS)return a.enforceFlash=!0,a.getFlashHtml()}var r=c.vid||c.id,s=c.nid||"",t=c.sid||d,u=c.pid||c.playListId||b.PLAYLIST_ID;_e("mytvid:"+c.id),_e("vid:"+c.vid),_e("ltype:"+c.ltype);var v=a.width||"auto",w=a.height||"auto",x=v,y=w,z=/^-?\d+(?:px)?$/i;z.test(x)&&(x+="px"),z.test(y)&&(y+="px");var A=function(a){var c=this,d=a.totalTime;c.qfVV(d);var e=c.flashVarsObj.vid||c.flashVarsObj.id,f=c.flashVarsObj.nid,g=c.flashVarsObj.pid,i=c.flashVarsObj.sid,k=c.flashVarsObj.api_key,l=encodeURIComponent(c.videoInfo.company||""),m=c.videoInfo.cateid||"",n=c.videoInfo.catecode||"",p=c.videoInfo.systype||"",r=c.videoInfo.type,s=c.videoInfo.ltype||0,t=c.videoInfo.vtitle||"",u=encodeURIComponent(document.referrer),x=encodeURIComponent(location.href);j(["http://b.scorecardresearch.com/b?c1=1&c2=7395122&c3=&c4=&c5=&c6=&c11=",i].join("")),j(["http://count.vrs.sohu.com/count/stat.do?videoId=",e,"&apikey=",k,"&t=",h()].join("")),j(["http://pb.hd.sohu.com.cn/hdpb.gif?cts=isow&msg=playCount&isHD=0&time=0&ua=h5","&sid=",i,"&uid=",i,"&pid=",g,"&vid=",e,"&nid=",f,"&type=",r,"&msg=playCount&isp2p=0&ltype=",s,"&company=",l,"&url=",x,"&td=",d,"&cateid=",m,"&refer=",u,"&systype=",p,"&catcode=",n,"&apikey=",k,"&t=",h()].join(""));var y=a.box,z=a.playlist,A=q({width:v,height:w,autoplay:o,unCheckFile:a.unCheckFile});A.attr("playlist",z),A.attr("totalTime",d);var B=c.eventObj.onVideoReady;B&&B.call(c,A);if(c.isSBDevice||!A||!A.player){if(z.length>1){var C=["\u8bf7\u70b9\u51fb\u64ad\u653e"];for(var D=0;D<z.length;++D)C.push(' <a style="color:#fff;" href="',z[D],'">',D+1,"</a>");y.innerHTML=C.join("")}else y.innerHTML=['<a href="',z[0].url,'" style="display:block;height:100%;width:100%;"></a>'].join(""),y.style.backgroundImage="url(http://tv.sohu.com/upload/touch/skin/images/play@2x.png)",y.style.backgroundPosition="center center",y.style.backgroundRepeat="no-repeat";return null}var E=A.player;y.innerHTML="",y.appendChild(E),A.playVideo(),A.ended(function(){var a=A.attr("playlist");return a&&a.length?A.playVideo():swfGotoNewPage(),this}),A.abort(function(){c.qfAbort()}),E.bufferCount=-1,A.playing(function(){++this.bufferCount},{once:!0}),A.playing(function(){var a=this;a.playing=!0,setTimeout(function(){a.playing=!1},3e3)}),A.playing(function(){setInterval(function(){_e("heart"),j(["http://pb.hd.sohu.com.cn/stats.gif?isHD=0&isp2p=0","&url=",x,"&refer=",u,"&url=",x,"&systype=",p,"&vid=",e,"&pid=",g,"&nid=",f,"&catcode=",n,"&ua=h5&&uid=",i,"&tc=",F,"&type=vrs&cateid=",m,"&apikey=",k,"&userid=t=",h()].join(""))},12e4)},{once:!0}),A.ended(function(){setInterval(function(){_e("end"),j(["http://pb.hd.sohu.com.cn/stats.gif?isHD=0&isp2p=0&msg=videoEnds","&url=",x,"&refer=",u,"&url=",x,"&systype=",p,"&vid=",e,"&pid=",g,"&nid=",f,"&catcode=",n,"&ua=h5&&uid=",i,"&tc=",F,"&type=vrs&cateid=",m,"&apikey=",k,"&userid=t=",h()].join(""))},12e4)});if(a.type=="tv"){var F=parseInt(E.currentTime);if(b.PassportSC&&PassportSC.cookieHandle()){var G=function(){b.PassportSC&&PassportSC.cookieHandle()&&j(["http://his.tv.sohu.com/his/ping.do?c=21&vid=",e,"&sid=",b.PLAYLIST_ID?b.PLAYLIST_ID:b.VRS_ALBUM_ID,"&t=",F,"&_=",h()].join(""))};A.bind("playing.cloudHistory",function(){this.cloudHistory=setInterval(function(){G()},12e4),G()},{once:!0})}else{var H=function(){try{var a=getVrsPlayerHistory(e,g,parseInt(E.currentTime),d,t)}catch(b){}};A.bind("playing.history",function(){this.history=setInterval(H,3e4),H()},{once:!0})}}return A.waiting(function(){if(this.playing||this.bufferCount<0)return;c.qfBuffer(this.bufferCount),++this.bufferCount}),A};a.videoInfo={};if(g){var B="http://my.tv.sohu.com/videinfo.jhtml?m=viewtv&vid="+r;a.flashVarsObj.hotVrs=B,p(B,function(c){_e(c);var d=f(e);if(c&&c.data&&c.data.su){var g=[];b._videoInfo&&(a.videoInfo.cateid=_videoInfo.cateId),a.videoInfo.catecode=c.catcode,a.videoInfo.type="my";var h=c.data.su,i=c.allot,j=c.data.clipsDuration;if(a.isIpad||a.isIphone&&!a.isIOSLow)g.push({url:["http://my.tv.sohu.com/ipad/",r,".m3u8"].join(""),time:j[0]});else for(var k=0;k<h.length;++k)g.push({url:["http://",i,h[k],"?type=2"].join(""),time:j[k]});A.call(a,{type:"mytv",box:d,totalTime:c.data.totalDuration,playlist:g})}else a.qfError(),d.innerHTML=c.mytvmsg})}else if(i){var C=["http://live.tv.sohu.com/live/player_json.jhtml?callback=?&lid=",r,"&af=1&bw=524&type=",n,"&g=8&ipad=1"].join("");a.flashVarsObj.hotVrs=C,a.videoInfo.type="vrs",a.videoInfo.ltype=n,a.getJSONP(C,function(b){var c=f(e);if(b&&b.data&&b.data.clipsURL){var d=[],g=b.data.clipsURL[0]||"";/m3u8/ig.test(g)?d.push({url:["http://",g].join(""),time:b.data.totalDuration}):d.push({url:["http://",g,"&type=hls"].join(""),time:Infinity}),A.call(a,{type:"live",box:c,playlist:d,totalTime:Infinity,unCheckFile:!0})}else a.qfError(),c.innerHTML="\u5bf9\u4e0d\u8d77\uff0c\u6b64\u89c6\u9891\u6682\u4e0d\u652f\u6301\u60a8\u7684\u8bbe\u5907\u89c2\u770b!"})}else{var D="jsonp"+h(),B=["http://hot.vrs.sohu.com/vrs_flash.action?var=",D,"&gbk=true"];b.fkey&&B.push("&fkey=",fkey),B.push("&vid=",r,"&pid=",u),B=B.join(""),_e(B),a.flashVarsObj.hotVrs=B,m(B,function(){var c=b[D],d=f(e);if(c&&c.data&&c.data.su){var g=[];a.videoInfo.cateid=c.caid,a.videoInfo.catecode=c.catcode,a.videoInfo.systype=c.systype,a.videoInfo.type=c.vt=="1"?"vrs":"vms",a.videoInfo.company=c.company,a.videoInfo.vtitle=c.data.tvName;if(a.isIpad||a.isIphone&&!a.isIOSLow){var h=(new Date).getTime().toString();m3u8url=["http://hot.vrs.sohu.com/ipad",r,"_",h._shift_en([23,12,131,1321]),"_",r.toString()._shift_en([23,12,131,1321]),".m3u8"],b.fkey&&m3u8url.push("?fkey=",fkey),g=[{url:m3u8url.join(""),time:c.data.totalDuration}],A.call(a,{type:"tv",box:d,totalTime:c.data.totalDuration,playlist:g})}else{var i=["http://api.tv.sohu.com/video/playinfo/",r,".json?callback=?&encoding=gbk&api_key=f351515304020cad28c92f70f002261c&from=mweb"].join("");a.getJSONP(i,function(b){var d=f(e);if(b&&b.data&&b.data.downloadurl)A.call(a,{type:"tv",box:d,totalTime:b.data.totalDuration,playlist:[{url:b.data.downloadurl,time:b.data.totalDuration}]});else{var h=c.data.su,i=c.allot,j=c.data.clipsDuration,k=c.data.ck;if(h&&h.length&&h[0]){for(var l=0;l<h.length;++l)g.push({url:["http://",i,h[l],"?type=2&key=",k[l]].join(""),time:j[l]});A.call(a,{type:"tv",box:d,totalTime:c.data.totalDuration,playlist:g})}else d.innerHTML="\u5bf9\u4e0d\u8d77\uff0c\u6b64\u89c6\u9891\u6682\u4e0d\u652f\u6301\u60a8\u7684\u8bbe\u5907\u89c2\u770b!"}})}}else a.qfError(),d.innerHTML="\u5bf9\u4e0d\u8d77\uff0c\u6b64\u89c6\u9891\u6682\u4e0d\u652f\u6301\u60a8\u7684\u8bbe\u5907\u89c2\u770b!"})}return[a.switchHtml,'<div id="',e,'" ','style="background:#000;width:',x,";height:",y,";","line-height:",y,';color:#fff;text-align:center;">',"\u89c6\u9891\u52a0\u8f7d\u4e2d....<noscript>\u60a8\u7684\u6d4f\u89c8\u5668\u7981\u7528\u4e86JavaScript,\u8bf7\u624b\u52a8\u5f00\u542f.</noscript></div>"].join("")},a.prototype.DetectFlashVer=function(a,b,c){if(d==-1)return!1;if(d!=0){e?(tempArray=d.split(" "),tempString=tempArray[1],versionArray=tempString.split(",")):versionArray=d.split(".");var f=versionArray[0],g=versionArray[1],h=versionArray[2];if(f>parseFloat(a))return!0;if(f==parseFloat(a)){if(g>parseFloat(b))return!0;if(g==parseFloat(b)&&h>=parseFloat(c))return!0}return!1}},a.prototype.checkFlash=function(a){var b=k("tryPlay");if(b)return!0;var c=this,f=null;typeof a=="string"?f=document.getElementById(a):typeof a=="object"&&(f=a),document.all&&(e=!0);var g=function(){var a="",b="",c="";try{b=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7"),a=b.GetVariable("$version")}catch(c){}if(!a)try{b=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6"),a="WIN 6,0,21,0",b.AllowScriptAccess="always",a=b.GetVariable("$version")}catch(c){}if(!a)try{b=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.3"),a=b.GetVariable("$version")}catch(c){}if(!a)try{b=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.3"),a="WIN 3,0,18,0"}catch(c){}if(!a)try{b=new ActiveXObject("ShockwaveFlash.ShockwaveFlash"),a="WIN 2,0,0,11"}catch(c){a=-1}return a},h=function(){var a=-1;if(navigator.plugins!=null&&navigator.plugins.length>0){if(navigator.plugins["Shockwave Flash 2.0"]||navigator.plugins["Shockwave Flash"]){var b=navigator.plugins["Shockwave Flash 2.0"]?" 2.0":"",c=navigator.plugins["Shockwave Flash"+b].description,d=c.split(" "),f=d[2].split("."),h=f[0],i=f[1],j=d[3];j==""&&(j=d[4]),j[0]=="d"?j=j.substring(1):j[0]=="r"?(j=j.substring(1),j.indexOf("d")>0&&(j=j.substring(0,j.indexOf("d")))):j[0]=="b"&&(j=j.substring(1));var a=h+"."+i+"."+j}}else navigator.userAgent.toLowerCase().indexOf("webtv/2.6")!=-1?a=4:navigator.userAgent.toLowerCase().indexOf("webtv/2.5")!=-1?a=3:navigator.userAgent.toLowerCase().indexOf("webtv")!=-1?a=2:e&&(a=g());return a},i=c.ver.split(",");d=h();var j=c.DetectFlashVer(i[0],i[1],i[2]);return j?j:j}}(SWFObject,window);