package one.show.common.cdn;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import one.show.common.Constant;
import one.show.common.JacksonUtil;
import one.show.common.ShellUtil;
import one.show.common.TimeUtil;
import one.show.common.TypeUtil;
import one.show.common.Constant.UPLOAD_USE_TYPE;
import one.show.common.exception.ServiceException;
import one.show.common.id.ObjectId;
import one.show.common.util.FileUtil;
import one.show.common.util.URLRequestUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinanetcenter.api.entity.FileListObject;
import com.chinanetcenter.api.entity.FileMessageObject;
import com.chinanetcenter.api.entity.FmgrParam;
import com.chinanetcenter.api.entity.HttpClientResult;
import com.chinanetcenter.api.exception.WsClientException;
import com.chinanetcenter.api.http.HttpClientUtil;
import com.chinanetcenter.api.util.Config;
import com.chinanetcenter.api.util.DateUtil;
import com.chinanetcenter.api.util.EncodeUtils;
import com.chinanetcenter.api.util.EncryptUtil;
import com.chinanetcenter.api.util.StringUtil;
import com.chinanetcenter.api.wsbox.OperationManager;
import com.cloopen.rest.sdk.utils.LoggerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WCSSDK {

	private static final Logger log = LoggerFactory
			.getLogger(WCSSDK.class);
	
	public final static  String AK = "6469b2d7500f1eec52d17c7618854c9ab009a227";
	
	public final static String SK = "952af9e547d9d2ea683f6112a60438d2c3be735c";
	
	public final static String UPLOAD_DOMAIN = "upload.showonelive.cn";
	
	public final static String DOWNLOAD_DOMAIN_VIDEO = "play.xiubi.come";
	public final static String DOWNLOAD_DOMAIN_IMG = "images.xiubi.come";
	
	public final static String MGR_DOMAIN = "51renzhen.mgr5.v1.wcsapi.com";
	
	public final static String BUCKET_VIDEO = "wuli-video";
	
	public final static String BUCKET_IMG =	"showone-images";
	
	public final static String CONCAT_NOTIFYURL = "http://"+Constant.DOMAIN+"/callback/cdn1/concat_notify";
	
	public final static String WATER_FILE_KEY = "591ab9f7306ddcc2b1ef2286.png";

	public static final String WATER_FILE_KEY_ANDR = "591d6a21ec9cdcc20b308aa7.png";
	
	public static final String WATER_FILE_KEY_IOS = "591bcd316f41dcc24306551a.png";
	
	public static final String NOTIFY_URL_PROFILEIMG = "http://"+Constant.DOMAIN+"/callback/cdn1/upload_profileImg";
	
	public static final String VIDEO_SCREENSHOTS ="vframe/jpg/offset/1/mode/2/h/%d|saveas/%s";
	public static final String IMG_ZOOM ="imageView2/jpg/mode/2/height/%d|saveas/%s";
	
	//删除
	public static boolean deleteFile(String fileUrl){
		String fileKey = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
		String bucket = fileKey.endsWith("mp4")?BUCKET_VIDEO:BUCKET_IMG;
		return deleteFile(bucket, fileKey);
	}
	public static boolean deleteFile(String bucket,String fileKey){
		String domain = fileKey.endsWith("mp4")?DOWNLOAD_DOMAIN_VIDEO:DOWNLOAD_DOMAIN_IMG;
		//1.初始化AK&SK信息 
		Config.init(AK,SK,"http://"+UPLOAD_DOMAIN,"http://"+domain,"http://"+MGR_DOMAIN); 
		//2.删除文件 
		OperationManager fileManageCommand = new OperationManager();
		HttpClientResult httpClientResult = null;
		try {
			httpClientResult = fileManageCommand.delete(bucket, fileKey);
		} catch (WsClientException e) {
			log.error("delete file error.",e);
		} 
		log.info("delete invalid file,bucket="+bucket+",key="+fileKey +",result="+httpClientResult.getStatus()+"----"+httpClientResult.getResponse()); 
		return httpClientResult.getStatus()==200;
	}
	
	//缩放
	@Deprecated
	public static String zoomFile(String fileUrl,int maxHeight){
		String notifyURL = "http://"+Constant.DOMAIN+"/callback/cdn1/upload_test";
		String fileKey = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
		String domain = fileKey.endsWith("mp4")?DOWNLOAD_DOMAIN_VIDEO:DOWNLOAD_DOMAIN_IMG;
		Config.init(AK,SK,"http://"+UPLOAD_DOMAIN,"http://"+domain,"http://"+MGR_DOMAIN); 
		String newFile = null;
		HttpClientResult httpClientResult = null;
		String bucket = fileKey.endsWith("mp4")?BUCKET_VIDEO:BUCKET_IMG;
		try {
			String newKey = ObjectId.get().toString()+".jpg";
			newFile = "http://"+domain+"/"+newKey;
			String destFile = new String(urlSafeEncodeBytes((bucket+":"+newKey).getBytes("utf-8")), "utf-8");
			String fops = "imageView2/jpg/mode/2/height/"+maxHeight+"|saveas/"+destFile;
			String force = "1";
			String separate = "1";
			OperationManager fileManageCommand = new OperationManager();
			httpClientResult = fileManageCommand.fops(bucket, fileKey, fops, notifyURL, force,separate);
			log.info("zoom file,preUrl="+fileUrl+",newUrl="+newFile+",persistentId="+httpClientResult.getResponse());
		} catch (Exception e) {
			log.error("zoom file error.",e);
		} 
		boolean r = httpClientResult==null?false:httpClientResult.getStatus()==200;
		return r?newFile:null;
	}
	
	public static String getNotifyUrl(int type){
		String notifyUrl = "";
		if(type==UPLOAD_USE_TYPE.WORK.ordinal()){
			notifyUrl="http://"+Constant.DOMAIN+"/callback/cdn1/upload";
		}else if(type==UPLOAD_USE_TYPE.PHOTOS.ordinal()){
			notifyUrl="http://"+Constant.DOMAIN+"/callback/cdn1/upload_photos";
		}else if(type==UPLOAD_USE_TYPE.TEST.ordinal()){
			notifyUrl="http://"+Constant.DOMAIN+"/callback/cdn1/upload_test";
		}else if(type==UPLOAD_USE_TYPE.PROFILE.ordinal()){
			notifyUrl="http://"+Constant.DOMAIN+"/callback/cdn1/upload_profileImg";
		}
		return notifyUrl;
	}
	
	//缩放图片
	public static String zoomImg(String fileUrl,int type,int height){
		String commendTemplate = fileUrl.endsWith("mp4")?VIDEO_SCREENSHOTS:IMG_ZOOM;
		String comments = "";
		try {
			String destFile = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
			comments = String.format(commendTemplate, height ,destFile);
		} catch (UnsupportedEncodingException e) {
			log.error("zoomImg error.",e);
		}
		return handleFile(fileUrl, getNotifyUrl(type), comments);
	}
	
	//处理文件
	public static String handleFile(String fileUrl,String notifyUrl,String commend){
		String bucket = fileUrl.endsWith("mp4")?BUCKET_VIDEO:BUCKET_IMG;
		String fileKey = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
		Config.init(AK,SK,"http://"+UPLOAD_DOMAIN,"http://"+DOWNLOAD_DOMAIN_IMG,"http://"+MGR_DOMAIN); 
		HttpClientResult httpClientResult = null;
		try {
			String force = "1";
			String separate = "1";
			OperationManager fileManageCommand = new OperationManager();
			httpClientResult = fileManageCommand.fops(bucket, fileKey, commend, notifyUrl, force,separate);
			log.info("zoom file,preUrl="+fileUrl+",persistentId="+httpClientResult.getResponse());
		} catch (Exception e) {
			log.error("zoom file error.",e);
		} 
		boolean r = httpClientResult==null?false:httpClientResult.getStatus()==200;
		String persistentId = null;
		if(r){
			Map map = JacksonUtil.readJsonToObject(Map.class, httpClientResult.getResponse());
			persistentId = map.get("persistentId").toString();
		}
		return persistentId;
	}
	
	public static String zoomProfileImg(String fileUrl){
		String fileKey = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
		Config.init(AK,SK,"http://"+UPLOAD_DOMAIN,"http://"+DOWNLOAD_DOMAIN_IMG,"http://"+MGR_DOMAIN); 
		HttpClientResult httpClientResult = null;
		try {
			String destFile = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
			String destFile_min = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
			String fops = "imageView2/jpg/mode/2/height/1334|saveas/"+destFile+";imageView2/jpg/mode/2/height/480|saveas/"+destFile_min;
			String force = "1";
			String separate = "1";
			OperationManager fileManageCommand = new OperationManager();
			httpClientResult = fileManageCommand.fops(BUCKET_IMG, fileKey, fops, NOTIFY_URL_PROFILEIMG, force,separate);
			log.info("zoom file,preUrl="+fileUrl+",persistentId="+httpClientResult.getResponse());
		} catch (Exception e) {
			log.error("zoom file error.",e);
		} 
		boolean r = httpClientResult==null?false:httpClientResult.getStatus()==200;
		String persistentId = null;
		if(r){
			Map map = JacksonUtil.readJsonToObject(Map.class, httpClientResult.getResponse());
			persistentId = map.get("persistentId").toString();
		}
		return persistentId;
	}
	
	//加水印
	public static String waterImg(String fileUrl){
		String fileKey = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
		String bucket = fileKey.endsWith("mp4")?BUCKET_VIDEO:BUCKET_IMG;
		String domain = fileKey.endsWith("mp4")?DOWNLOAD_DOMAIN_VIDEO:DOWNLOAD_DOMAIN_IMG;
		Config.init(AK,SK,"http://"+UPLOAD_DOMAIN,"http://"+domain,"http://"+MGR_DOMAIN); 
		String newFile = null;
		HttpClientResult httpClientResult = null;
		try {
			String newKey = ObjectId.get().toString()+".jpg";
			newFile = "http://"+domain+"/"+newKey;
			String destFile = new String(urlSafeEncodeBytes((bucket+":"+newKey).getBytes("utf-8")), "utf-8");
			String waterFile = new String(urlSafeEncodeBytes(("http://play.xiubi.come/"+WATER_FILE_KEY).getBytes("utf-8")), "utf-8");
			String fops = "watermark/jpg/mode/1/gravity/TOP_RIGHT/dx/30/dy/30/image/"+waterFile+"|saveas/"+destFile;
			String notifyURL = "http://"+Constant.DOMAIN+"/callback/cdn1/upload_test";
			String force = "1";
			String separate = "1";
			OperationManager fileManageCommand = new OperationManager();
			httpClientResult = fileManageCommand.fops(bucket, fileKey, fops, notifyURL, force,separate);
			log.info("water img,preUrl="+fileUrl+",newUrl="+newFile+",persistentId="+httpClientResult.getResponse());
		} catch (Exception e) {
			log.error("water img error.",e);
		} 
		boolean r = httpClientResult==null?false:httpClientResult.getStatus()==200;
		return r?newFile:null;
	}
	
	public static String concat(String bucket,long liveid,int format,List<String> keys){
		Map<String, String> params = new HashMap<String, String>();
		params.put("bucket", EncodeUtils.urlsafeEncode(bucket));
		params.put("fops", EncodeUtils.urlsafeEncode(getFops(format, bucket,liveid, keys)));
		params.put("force", "1");
		String keyFile = keys.get(0).substring(keys.get(0).indexOf(":")+1);
		params.put("key", EncodeUtils.urlsafeEncode(keyFile));
		params.put("notifyURL", EncodeUtils.urlsafeEncode(CONCAT_NOTIFYURL));
		String accessToken="";
		try {
			accessToken = createAccessToken("/fops", params);
		} catch (UnsupportedEncodingException e) {
			log.error("createAccessToken error.",e);
		}
		
		Map<String, String> header = new HashMap<String, String>();
		header.put("Authorization", accessToken);
	
		String persistentId = null;
		String paramStr = "";
		for(String key:params.keySet()){
			if(!paramStr.equals("")){
				paramStr+="&";
			}
			paramStr+=key+"="+params.get(key);
		}
		String response = URLRequestUtil.getPostRequestStr("http://xiubi.mgr7.v1.wcsapi.com/fops", header, paramStr);
		log.info("concat video response:"+response);
		if(!StringUtil.isEmpty(response)){
			try {
				JSONObject json = new JSONObject(response);
				persistentId = json.getString("persistentId");
			} catch (JSONException e) {
				log.error("json parse error",e);
			}
		}
		return persistentId;
	}
	
	private static String createAccessToken(String url,Map<String, String> params) throws UnsupportedEncodingException{
		String str = "";
		int n = 0;
		for (String key:params.keySet()) {
			if(n>0){
				str+="&";
			}
			str+=key+"="+params.get(key);
			n++;
		}
		log.info("ak:"+AK);
		log.info("SK:"+SK);
		log.info("path:"+url);
		log.info("str:"+str);
		String signStr = buildSigningStr(url,null,str);
		String sign = getSignatureHmacSHA1(signStr.getBytes(),SK);
		log.info("sign:"+sign);
		String encodeSign = new String(urlSafeEncodeBytes(sign.getBytes("utf-8")), "utf-8");
		log.info("encodeSign:"+encodeSign);
		String accessToken = String.format("%s:%s", AK, encodeSign);
		log.info("accessToken:"+accessToken);
		return accessToken;
	}
	
	private static String buildSigningStr(String path, String query, String body) {
		String connector = "\n";
		StringBuilder signingStrBuilder = new StringBuilder(path);
		if (StringUtils.isNotEmpty(query)){
			signingStrBuilder.append("?").append(query);
		}
		signingStrBuilder.append(connector);
		if (StringUtils.isNotEmpty(body)){
			signingStrBuilder.append(body);
		}
		return signingStrBuilder.toString();
	}
	
	//列举资源
	public static FileListObject listFile(String bucket,String prefix,String cursor,Integer count){
		Config.init(AK,SK,"http://"+UPLOAD_DOMAIN,"http://"+DOWNLOAD_DOMAIN_VIDEO,"http://"+MGR_DOMAIN); 
		FileListObject list = null;
		HttpClientResult result = null;
		OperationManager fileManageCommand = new OperationManager();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			result = fileManageCommand.fileList(bucket, count.toString(), prefix,"",cursor);
			if(result!=null){
				list = objectMapper.readValue(result.getResponse(), FileListObject.class);
			}
		} catch (Exception e) {
			log.info("list file error.",e);
		}
		return list;
	}
	
	public static HttpClientResult deletem3u8(List<FmgrParam> fmgrList,String notifyUrl,String separate){
		Config.init(AK,SK,"http://"+UPLOAD_DOMAIN,"http://"+DOWNLOAD_DOMAIN_VIDEO,"http://"+MGR_DOMAIN); 
		String url = Config.MGR_URL + "/fmgr/deletem3u8";
        StringBuffer bodySB = new StringBuffer("");
        StringBuffer fopsSB = new StringBuffer("");
        for (FmgrParam fmgrParam : fmgrList){
            fopsSB.append("bucket/").append(EncodeUtils.urlsafeEncode(fmgrParam.getBucket()));
            if (StringUtils.isNotEmpty(fmgrParam.getFileKey())){
                fopsSB.append("/key/").append(EncodeUtils.urlsafeEncode(fmgrParam.getFileKey()));
            }
            fopsSB.append("/deletets/1;");
        }
        fopsSB = fopsSB.deleteCharAt(fopsSB.length() - 1);
        bodySB.append("fops=").append(fopsSB.toString());
        if (StringUtils.isNotEmpty(notifyUrl)){
            bodySB.append("&notifyURL=").append(notifyUrl);
        }
        if (StringUtils.isNotEmpty(separate)){
            bodySB.append("&separate=").append(separate);
        }
        String value = EncodeUtils.urlsafeEncode(EncryptUtil.sha1Hex(("/fmgr/deletem3u8" + "\n" + bodySB.toString()).getBytes(), Config.SK));
        String authorization = Config.AK + ":" + value;
        Map<String, String> headMap = new HashMap<String, String>();
        headMap.put("Authorization", authorization);
        HttpClientResult result = null;
        try {
			result =  HttpClientUtil.httpPostStringEntity(url,headMap,bodySB.toString());
		} catch (WsClientException e) {
			log.error("deletem3u8 error.",e);
		}
        return result;
	}
	
	public static void main(String[] args) {
		
		System.out.println(getFastIp());
	}
	
	private static String getFops(int format,String bucket,long liveid,List<String> keys){
		if(keys==null||keys.size()==0){
			return null;
		}
		String filekeys = "";
		String str = "avconcat";
		str+="/"+(format==Constant.FORMAT.M3U8.ordinal()?"m3u8":(format==Constant.FORMAT.FLV.ordinal()?"flv":"mp4"));
		str+="/mode/1";
		str+="/concatorder/1";
		for (int i = 1; i <keys.size(); i++) {
			str+="-"+(i+1);
			if(i>1){
				filekeys+="/";
			}
			String kf = keys.get(i).substring(keys.get(i).indexOf(":")+1);
			filekeys+=EncodeUtils.urlsafeEncode(kf);
		}
		str+="/"+filekeys;
		String newFile = bucket+":"+"live-"+liveid+(format==Constant.FORMAT.M3U8.ordinal()?"m3u8":(format==Constant.FORMAT.FLV.ordinal()?"flv":"mp4"));
		str+="|saveas/"+EncodeUtils.urlsafeEncode(newFile);
		log.info("fops keys size:"+keys.size()+",   str="+str);
		return str;
	}
	
	public static String getUploadToken(String fileName,String ext){
		return getUploadToken(fileName,ext,UPLOAD_USE_TYPE.WORK.ordinal());
	}
	
	public static String getUploadToken(String fileName,String ext,int type){
		return getUploadToken(fileName, ext, type, WCSSDK.WATER_FILE_KEY_ANDR);
	}
	
	public static String getUploadToken(String fileName,String ext,int type,String videoWaterKey){
		String encodePutPolicy = "";
		String encodeSign="";
		try {
			Map<String, Object> policyMap = new HashMap<>();
			if(ext.equals("mp4") || ext.equals("apk")){
				policyMap.put("scope", BUCKET_VIDEO);
				if(type==UPLOAD_USE_TYPE.WORK.ordinal()||type==UPLOAD_USE_TYPE.PHOTOS.ordinal()){
//					String screenshots = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
//					String screenshots_min = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
//					String watermp4 = new String(urlSafeEncodeBytes((BUCKET_VIDEO+":"+ObjectId.get().toString()+".mp4").getBytes("utf-8")), "utf-8");
//					String vagueFile = new String(urlSafeEncodeBytes((BUCKET_VIDEO+":"+ObjectId.get().toString()+".mp4").getBytes("utf-8")), "utf-8");
//					String waterFile = new String(urlSafeEncodeBytes((BUCKET_VIDEO+":"+videoWaterKey).getBytes("utf-8")), "utf-8");
					//包含水印
//					String persistentOps = "vframe/jpg/offset/1/mode/2/h/1334|saveas/"+screenshots+";vframe/jpg/offset/1/mode/2/h/480|saveas/"+screenshots_min+";avthumb/m4a/wmImage2/"+waterFile+"/wmGravity/TOP_RIGHT|saveas/"+watermp4+";avthumb/m4a/s/32x64|saveas/"+vagueFile;
//					String persistentOps = "vframe/jpg/offset/1/mode/2/h/1334|saveas/"+screenshots+";vframe/jpg/offset/1/mode/2/h/480|saveas/"+screenshots_min+";avthumb/m4a/s/32x64|saveas/"+vagueFile;
//					policyMap.put("persistentOps",persistentOps);
				}else if(type==UPLOAD_USE_TYPE.TEST.ordinal()){
					String screenshots = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".gif").getBytes("utf-8")), "utf-8");
					String screenshots_min = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
					String watermp4 = new String(urlSafeEncodeBytes((BUCKET_VIDEO+":"+ObjectId.get().toString()+".mp4").getBytes("utf-8")), "utf-8");
					String waterFile = new String(urlSafeEncodeBytes((BUCKET_VIDEO+":"+videoWaterKey).getBytes("utf-8")), "utf-8");
					String persistentOps = "avthumb/m4a/wmImage2/"+waterFile+"/wmGravity/TOP_RIGHT/s/32x64|saveas/"+watermp4+";vframe/gif/offset/1/t/3/mode/2/h/239|saveas/"+screenshots+";vframe/jpg/offset/1/mode/2/h/480|saveas/"+screenshots_min;
					policyMap.put("persistentOps",persistentOps);
				}
			}else{
				policyMap.put("scope", BUCKET_IMG);
				if(type==UPLOAD_USE_TYPE.WORK.ordinal()||type==UPLOAD_USE_TYPE.PHOTOS.ordinal()){
//					String waterJpg = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
//					String screenshots_min = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
//					String waterFile = new String(urlSafeEncodeBytes(("http://"+DOWNLOAD_DOMAIN_VIDEO+"/"+WATER_FILE_KEY).getBytes("utf-8")), "utf-8");
//					String persistentOps = "watermark/jpg/mode/1/gravity/TOP_RIGHT/dx/30/dy/30/image/"+waterFile+"|saveas/"+waterJpg+";imageView2/jpg/mode/2/height/480|saveas/"+screenshots_min;
//					String persistentOps = "imageView2/jpg/mode/2/height/480|saveas/"+screenshots_min;
//					if(type==UPLOAD_USE_TYPE.WORK.ordinal()){
//						String vagueFile = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
//						persistentOps += ";imageView2/jpg/mode/2/height/20|saveas/"+vagueFile;
//					}
//					policyMap.put("persistentOps",persistentOps);
				}else if(type==UPLOAD_USE_TYPE.TEST.ordinal()){
					String screenshots = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
					String screenshots_min = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
					String waterFile = new String(urlSafeEncodeBytes(("http://"+DOWNLOAD_DOMAIN_VIDEO+"/"+WATER_FILE_KEY).getBytes("utf-8")), "utf-8");;
					String persistentOps = "watermark/jpg/mode/1/gravity/TOP_RIGHT/dx/30/dy/30/image/"+waterFile+"|saveas/"+screenshots+";imageView2/jpg/mode/2/height/480|saveas/"+screenshots_min;
					policyMap.put("persistentOps",persistentOps);
				}else if(type==UPLOAD_USE_TYPE.PROFILE.ordinal()){
//					String screenshots = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
//					String screenshots_min = new String(urlSafeEncodeBytes((BUCKET_IMG+":"+ObjectId.get().toString()+".jpg").getBytes("utf-8")), "utf-8");
//					String persistentOps = "imageView2/jpg/mode/2/height/1334|saveas/"+screenshots+";imageView2/jpg/mode/2/height/480|saveas/"+screenshots_min;
//					policyMap.put("persistentOps",persistentOps);
				}
			}
			policyMap.put("deadline", String.valueOf(System.currentTimeMillis()+300000));
			policyMap.put("returnBody","persistentId=$(persistentId)&url=$(url)&fsize=$(fsize)");
			if(type==UPLOAD_USE_TYPE.WORK.ordinal()){
//				policyMap.put("persistentNotifyUrl","http://"+Constant.CALLBACK_DOMAIN+"/callback/cdn1/upload");
			}else if(type==UPLOAD_USE_TYPE.PHOTOS.ordinal()){
//				policyMap.put("persistentNotifyUrl","http://"+Constant.CALLBACK_DOMAIN+"/callback/cdn1/upload_photos");
			}else if(type==UPLOAD_USE_TYPE.TEST.ordinal()){
				policyMap.put("persistentNotifyUrl","http://"+Constant.DOMAIN+"/callback/cdn1/upload_test");
			}else if(type==UPLOAD_USE_TYPE.PROFILE.ordinal()){
//				policyMap.put("persistentNotifyUrl","http://"+Constant.CALLBACK_DOMAIN+"/callback/cdn1/upload_profileImg");
			}
			policyMap.put("overwrite", 1);
			log.info(TypeUtil.typeToString("policyMap", policyMap));
			
			String putPolicy = JacksonUtil.writeToJsonString(policyMap);
			encodePutPolicy = new String(urlSafeEncodeBytes(putPolicy.getBytes("utf-8")), "utf-8");
			String sign = getSignatureHmacSHA1(encodePutPolicy.getBytes(),SK);
			encodeSign = new String(urlSafeEncodeBytes(sign.getBytes("utf-8")), "utf-8");
		} catch (Exception e) {
			log.error("getUploadToken error.",e);
		}
		return AK+":"+encodeSign+":"+encodePutPolicy;
	}
	
	/**
	 * 上传文件
	 * @param inputStream
	 * @param fileName
	 * @return
	 * @throws ServiceException 
	 */
	public static WCSResponse uploadFile(File file,String fileName,String ext) throws ServiceException{
		return uploadFile(file, fileName, ext,UPLOAD_USE_TYPE.WORK.ordinal());
	}
	public static WCSResponse uploadFile(File file,String fileName,String ext,int type) throws ServiceException{
		CloseableHttpClient client = HttpClientBuilder.create().build();    
		
		HttpPost post = new HttpPost("http://"+UPLOAD_DOMAIN+"/file/upload");
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Encoding", "*/*");
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();         
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addBinaryBody
		  ("file", file, ContentType.create("application/octet-stream"), fileName+"."+ext);
		builder.addTextBody("token", getUploadToken(fileName,ext,type), ContentType.TEXT_PLAIN);
		// 
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		HttpResponse response=null;
		try {
			response = client.execute(post);
		} catch (Exception e) {
			log.error("wscdn.upload error",e);
			throw new ServiceException("upload request error.");
		}
		HttpEntity httpEntity = response.getEntity();
		StringBuffer sb = new StringBuffer();
		String rsp = "";
		try {
			//将H中返回实体转化为输入流
			InputStream is = httpEntity.getContent();
			//读取输入流，即返回文本内容
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while((line=br.readLine())!=null){
			sb.append(line);
			}
			br.close();
			int status = response.getStatusLine().getStatusCode();
			if(status==200){
				rsp = EncodeUtils.urlsafeDecodeString(sb.toString());
			}else{
				rsp = sb.toString();
			}
		} catch (Exception e) {
			log.error("read response error.",e);
			if(!StringUtils.isEmpty(sb.toString())){
				throw new ServiceException(sb.toString());
			}else{
				throw new ServiceException(e.getMessage());
			}
		}
		WCSResponse wcsRes = new WCSResponse();
		wcsRes.setResult(rsp);
		log.info("upload rsp:"+TypeUtil.typeToString("", wcsRes));
		return wcsRes;
	}
	
	public static WCSResponse uploadFile(byte[] content,String fileName,String ext) throws Exception{
		return uploadFile(content, fileName, ext, UPLOAD_USE_TYPE.NORMAL.ordinal());
	}
	
	public static WCSResponse uploadFile(byte[] content,String fileName,String ext, int type) throws Exception{
		CloseableHttpClient client = HttpClientBuilder.create().build();    
		
		HttpPost post = new HttpPost("http://"+UPLOAD_DOMAIN+"/file/upload");
		post.setHeader("Accept", "*/*");
		post.setHeader("Accept-Encoding", "*/*");
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();         
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addBinaryBody
		  ("file", content, ContentType.create("application/octet-stream"), fileName+"."+ext);
		builder.addTextBody("token", getUploadToken(fileName,ext,type), ContentType.TEXT_PLAIN);
		// 
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		HttpResponse response=null;
		try {
			response = client.execute(post);
		} catch (Exception e) {
			log.error("wscdn.upload error",e);
			throw new Exception("upload request error.");
		}
		HttpEntity httpEntity = response.getEntity();
		StringBuffer sb = new StringBuffer();
		String rsp = "";
		try {
			//将H中返回实体转化为输入流
			InputStream is = httpEntity.getContent();
			//读取输入流，即返回文本内容
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while((line=br.readLine())!=null){
			sb.append(line);
			}
			br.close();
			int status = response.getStatusLine().getStatusCode();
			if(status==200){
				rsp = EncodeUtils.urlsafeDecodeString(sb.toString());
			}else{
				rsp = sb.toString();
			}
		} catch (Exception e) {
			log.error("read response error.",e);
			if(!StringUtils.isEmpty(sb.toString())){
				throw new Exception(sb.toString());
			}else{
				throw new Exception(e.getMessage());
			}
		}
		log.info("upload rsp:"+rsp);
		
		WCSResponse wcsRes = new WCSResponse();
		wcsRes.setResult(rsp);
		return wcsRes;
	}
	
	/**
	 * 
	 * @param inStream  输入流
	 * @param fileName  文件名
	 * @param ext 扩展名
	 * @return
	 * @throws Exception
	 */
	public static WCSResponse uploadFile(InputStream inStream,String fileName,String ext) throws Exception{
		
		return uploadFile(input2byte(inStream), fileName, ext);
		
	}
	/**
	 * 
	 * @param inStream
	 * @param fileName
	 * @param ext
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static WCSResponse uploadFile(InputStream inStream,String fileName,String ext, int type) throws Exception{
		
		return uploadFile(input2byte(inStream), fileName, ext, type);
		
	}
	
	private static final byte[] input2byte(InputStream inStream)  
	            throws IOException {  
		 int read = 0;
			byte[] bytes = new byte[10 * 1024];
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			while ((read = inStream.read(bytes)) >= 0) {
				byteArrayOutputStream.write(bytes, 0, read);

			}
			inStream.close();
			return byteArrayOutputStream.toByteArray();
	}  
	
	
	/**
     * HMAC-SHA1加密方案<br>
     * @param content-待加密内容
     * @param secretKey-密钥
     * @return HMAC_SHA1加密后的字符串
     */
	public static String getSignatureHmacSHA1(byte[] data, String key) {
		byte[] keyBytes = key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
		Mac mac;
		StringBuffer sb = new StringBuffer();
		try {
			mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(data);

			for (byte b : rawHmac) {
				sb.append(byteToHexString(b));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
		"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static byte[] urlSafeEncodeBytes(byte[] src) {
		if (src.length % 3 == 0)
			return encodeBase64Ex(src);
		byte[] b = encodeBase64Ex(src);
		if (b.length % 4 == 0)
			return b;

		int pad = 4 - b.length % 4;
		byte[] b2 = new byte[b.length + pad];
		System.arraycopy(b, 0, b2, 0, b.length);
		b2[b.length] = '=';
		if (pad > 1)
			b2[b.length + 1] = '=';
		return b2;
	}

	private static byte[] encodeBase64Ex(byte[] src) {
		// urlsafe version is not supported in version 1.4 or lower.
		byte[] b64 = Base64.encodeBase64(src);

		for (int i = 0; i < b64.length; i++) {
			if (b64[i] == '/') {
				b64[i] = '_';
			} else if (b64[i] == '+') {
				b64[i] = '-';
			}
		}
		return b64;
	}
	
	public static String getFastIp(){
		String ip = null;
		try {
			String ips = ShellUtil.exec("/usr/bin/curl -v sdk.wscdns.com -H WS_URL:play.showonelive.cn/ -H WS_RETIP_NUM:2 -H WS_URL_TYPE:1".split(" "));
			ip = ips.split("\n")[0];
		} catch (Exception e) {
			log.error("wangsu getFastIp error.",e);
		}
		return ip;
	}
	
}
