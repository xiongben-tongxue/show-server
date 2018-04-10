package one.show.common.cdn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinanetcenter.api.util.EncodeUtils;

public class WCSUPLOADSDK {

	private static final Logger log = LoggerFactory
			.getLogger(WCSUPLOADSDK.class);
	
//	public final static  String AK = "6469b2d7500f1eec52d17c7618854c9ab009a227";
//	
//	public final static String SK = "952af9e547d9d2ea683f6112a60438d2c3be735c";
//	
//	public final static String UPLOAD_DOMAIN = "video-test.hifun.mobi";
//	
//	private FileUploadManage fileUploadManage = new FileUploadManage();
//	
//
//    /**
//     * 通过本地的文件路径上传文件
//     * 默认覆盖上传
//     */
//    public void uploadFile(String bucketName,String fileKey,String srcFilePath){
//        try {
//            HttpClientResult result = fileUploadManage.upload(bucketName,fileKey,srcFilePath);
//            System.out.println(result.getStatus() + ":" + result.getResponse());
//        } catch (WsClientException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 通过文件流上传文件，方法里会关闭InputStream
//     * 默认覆盖上传
//     */
//    public void uploadFile(String bucketName,String fileKey,InputStream in){
//        try {
//            HttpClientResult result = fileUploadManage.upload(bucketName,fileKey,in);
//            System.out.println(result.getStatus() + ":" + result.getResponse());
//        } catch (WsClientException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 上传后需要回调、返回信息等，可通过PutPolicy指定上传策略
//     * callbackurl、callbackbody、returnurl 类似这个方法
//     */
//    public void uploadReturnBody(String bucketName,String fileKey,String srcFilePath){
//        String returnBody = "key=$(key)&fname=$(fname)&fsize=$(fsize)&url=$(url)&hash=$(hash)&mimeType=$(mimeType)";
//        PutPolicy putPolicy = new PutPolicy();
//        putPolicy.setOverwrite(1); //覆盖上传
//        putPolicy.setDeadline(String.valueOf(String.valueOf(System.currentTimeMillis()+300000)));
//        putPolicy.setReturnBody(returnBody);
//        putPolicy.setScope(bucketName + ":" + fileKey);
//        try {
//            HttpClientResult result = fileUploadManage.upload(bucketName,fileKey,srcFilePath,putPolicy);
//            System.out.println(result.getStatus() + ":" + result.getResponse());
//        } catch (WsClientException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 上传指定文件类型，服务端默认按照文件后缀或者文件内容
//     * 指定了mimeType，在下载的时候Content-type会指定该类型
//     */
//    public void uploadMimeType(String bucketName,String fileKey,String srcFilePath){
//        PutPolicy putPolicy = new PutPolicy();
//        putPolicy.setOverwrite(1);
//        putPolicy.setDeadline(String.valueOf(System.currentTimeMillis()+300000));
//        putPolicy.setScope(bucketName + ":" + fileKey);
//        try {
//            String uploadToken = TokenUtil.getUploadToken(putPolicy);
//            Map<String, String> paramMap = new HashMap<String, String>();
//            paramMap.put("token", uploadToken);
//            paramMap.put("mimeType", "application/UQ");
//            HttpClientResult result = fileUploadManage.upload(paramMap,srcFilePath);
//            System.out.println(result.getStatus() + ":" + result.getResponse());
//        } catch (WsClientException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 上传文件后对该文件做转码
//     * 上传成功后返回persistentId应答，可以通过这个id去查询转码情况
//     */
//    public void uploadPersistent(String bucketName,String fileKey,String srcFilePath){
//        PutPolicy putPolicy = new PutPolicy();
//        String returnBody = "key=$(key)&persistentId=$(persistentId)&fsize=$(fsize)";
//        putPolicy.setOverwrite(1);
//        putPolicy.setDeadline(String.valueOf(System.currentTimeMillis()+300000));
//        putPolicy.setScope(bucketName + ":" + fileKey);
//        putPolicy.setPersistentOps("imageMogr2/jpg/crop/500x500/gravity/CENTER/lowpoly/1|saveas/ZnV5enRlc3Q4Mi0wMDE6ZG9fY3J5c3RhbGxpemVfZ3Jhdml0eV9jZW50ZXJfMTQ2NTkwMDg0Mi5qcGc="); // 设置视频转码操作
//        putPolicy.setPersistentNotifyUrl("http://demo1/notifyUrl"); // 设置转码后回调的接口
//        putPolicy.setReturnBody(returnBody);
//        try {
//            HttpClientResult result = fileUploadManage.upload(bucketName,fileKey,srcFilePath,putPolicy);
//            System.out.println(result.getStatus() + ":" + result.getResponse());
//        } catch (WsClientException e) {
//            e.printStackTrace();
//        }
//    }
	
	public static void main(String[] args) {
		System.out.println(EncodeUtils.urlsafeDecodeString("cGVyc2lzdGVudElkPTEwMDVhYWQ0ZjZlMWQ5ZDc0ZGU2OGI5YTdkMWY5N2MzZTE4YyZ1cmw9aHR0cDovL2ltYWdlcy5zZWV1bGl2ZS5jbi81OTU5ZGJiMzk5M2RkY2MyYTVhMDAyN2UuanBlZyZmc2l6ZT0xMzMzNjQmaGFzaD1Gb2swRk82ZEE4ejc2ejZ4TTM4eWhPcmU1azBo"));
	}
}
