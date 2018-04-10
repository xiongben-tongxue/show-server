package one.show.common.cdn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.proto.CreateRequest;
import org.apache.zookeeper.proto.CreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadImageRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadImageResponse;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse.PlayInfo;

public class AliyunVodSDK {

	private static final Logger log = LoggerFactory.getLogger(AliyunVodSDK.class);
	
	private static final String REGION_CN_HANGZHOU = "cn-shanghai";
	
	private static DefaultAcsClient client = new DefaultAcsClient(
			DefaultProfile.getProfile(REGION_CN_HANGZHOU,AliyunOSS.accessKeyId,AliyunOSS.accessKeySecret));
	 
	private static void refreshUploadVideo(String videoId) {
        RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
        RefreshUploadVideoResponse response = null;
        try {
              request.setVideoId(videoId);
              response = client.getAcsResponse(request);
        } catch (ServerException e) {
              System.out.println("RefreshUploadVideoRequest Server Exception:");
              e.printStackTrace();
        } catch (ClientException e) {
              System.out.println("RefreshUploadVideoRequest Client Exception:");
              e.printStackTrace();
        }
        System.out.println("RequestId:" + response.getRequestId());
        System.out.println("UploadAuth:" + response.getUploadAuth());
	}
	
	public static Map<String, Object> createUploadVideo(String fileName) throws ServerException, ClientException {
		Map<String, Object>  map = new HashMap<String, Object>();
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        CreateUploadVideoResponse response = null;
        
	      /*必选，视频源文件名称（必须带后缀, 支持 ".3gp", ".asf", ".avi", ".dat", ".dv", ".flv", ".f4v", ".gif", ".m2t", ".m3u8", ".m4v", ".mj2", ".mjpeg", ".mkv", ".mov", ".mp4", ".mpe", ".mpg", ".mpeg", ".mts", ".ogg", ".qt", ".rm", ".rmvb", ".swf", ".ts", ".vob", ".wmv", ".webm"".aac", ".ac3", ".acm", ".amr", ".ape", ".caf", ".flac", ".m4a", ".mp3", ".ra", ".wav", ".wma"）*/
	      request.setFileName(fileName);
	      //必选，视频标题
	      request.setTitle(fileName);
	      //可选，分类ID
	//              request.setCateId(0);
	      //可选，视频标签，多个用逗号分隔
	//              request.setTags("标签1,标签2");
	      //可选，视频描述
	//              request.setDescription("视频描述");
	      //可选，视频源文件字节数
	//              request.setFileSize(0);
	      response = client.getAcsResponse(request);

        map.put("objectId", response.getVideoId());
	    map.put("uploadAuth", response.getUploadAuth());
	    map.put("uploadAddress", response.getUploadAddress());
	    return map;
	    
	}
	
	public static Map<String, Object> createUploadImage(String ext) throws ServerException, ClientException {
		Map<String, Object>  map = new HashMap<String, Object>();
        CreateUploadImageRequest request = new CreateUploadImageRequest();
        CreateUploadImageResponse response = null;
        request.setImageType("cover");
        request.setImageExt(ext);
	    response = client.getAcsResponse(request);

        map.put("objectId", response.getImageURL());
	    map.put("uploadAuth", response.getUploadAuth());
	    map.put("uploadAddress", response.getUploadAddress());
	    return map;
	    
   }
	
	public static String getVideoUrl(String videoId) throws ServerException, ClientException {
		GetPlayInfoRequest request = new GetPlayInfoRequest();
		GetPlayInfoResponse response = null;
		request.setVideoId(videoId);
	    response = client.getAcsResponse(request);

	    List<PlayInfo> list = response.getPlayInfoList();
	    if (list != null){
	    	for(PlayInfo  playInfo : list){
		    	if (playInfo.getFormat().equals("mp4")){
		    		return playInfo.getPlayURL();
		    	}
		    }
		    
	    }
	    
	    return null;
	    
   }
	
	public static void main(String[] args) throws ServerException, ClientException {
	    
		createUploadImage("jpg");
		System.out.println(getVideoUrl("0d968da0775245758bcdd7517bc23bca"));
		//createUploadVideo(ObjectId.get().toString()+".mp4");
		
		System.out.println("0d968da0775245758bcdd7517bc23bca".indexOf("."));
	}

}
