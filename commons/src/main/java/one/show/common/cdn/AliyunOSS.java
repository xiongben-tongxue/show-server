package one.show.common.cdn;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;

public class AliyunOSS {
	
//	String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//	// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
//	String accessKeyId = "<yourAccessKeyId>";
//	String accessKeySecret = "<yourAccessKeySecret>";
//	// 创建OSSClient实例
//	OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	
	 // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
    // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
	public static final String accessKeyId = "LTAIGJd0LJ3pPfuy";
	public static final String accessKeySecret = "eGeOWkOFSuG02Jl6uuUiwrSogs8S0X";
	
	// 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
	  public static final String REGION_CN_HANGZHOU = "cn-hangzhou";
	  // 当前 STS API 版本
	  public static final String STS_API_VERSION = "2015-04-01";
	  static AssumeRoleResponse assumeRole(String accessKeyId, String accessKeySecret,
	                                       String roleArn, String roleSessionName, String policy,
	                                       ProtocolType protocolType) throws ClientException {
	    try {
	      // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
	      IClientProfile profile = DefaultProfile.getProfile(REGION_CN_HANGZHOU, accessKeyId, accessKeySecret);
	      DefaultAcsClient client = new DefaultAcsClient(profile);
	      // 创建一个 AssumeRoleRequest 并设置请求参数
	      final AssumeRoleRequest request = new AssumeRoleRequest();
	      request.setVersion(STS_API_VERSION);
	      request.setMethod(MethodType.POST);
	      request.setProtocol(protocolType);
	      request.setRoleArn(roleArn);
	      request.setRoleSessionName(roleSessionName);
	      request.setPolicy(policy);
	      request.setDurationSeconds(3600l);
	      // 发起请求，并得到response
	      final AssumeRoleResponse response = client.getAcsResponse(request);
	      return response;
	    } catch (ClientException e) {
	      throw e;
	    }
	  }
	  
	  
  public static Map<String, Object> getToken() throws ClientException{
	  	Map<String, Object>  map = new HashMap<String, Object>();
	  
	  	// 只有 RAM用户（子账号）才能调用 AssumeRole 接口
	    // 阿里云主账号的AccessKeys不能用于发起AssumeRole请求
	    // 请首先在RAM控制台创建一个RAM用户，并为这个用户创建AccessKeys
	   
	    // AssumeRole API 请求参数: RoleArn, RoleSessionName, Policy, and DurationSeconds
	    // RoleArn 需要在 RAM 控制台上获取
	    String roleArn = "acs:ram::1793257590020474:role/oss";
	    // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
	    // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
	    // 具体规则请参考API文档中的格式要求
	    String roleSessionName = "oss-001";
	    // 如何定制你的policy?
	    String policy = "{\n" +
	            "    \"Version\": \"1\", \n" +
	            "    \"Statement\": [\n" +
	            "        {\n" +
	            "            \"Action\": \"oss:*\" , \n" +
	            "            \"Resource\": \"*\",  \n" +
	            "            \"Effect\": \"Allow\"\n" +
	            "        }\n" +
	            "    ]\n" +
	            "}";
	    // 此处必须为 HTTPS
	    ProtocolType protocolType = ProtocolType.HTTPS;
	    final AssumeRoleResponse response = assumeRole(accessKeyId, accessKeySecret,
              roleArn, roleSessionName, policy, protocolType);
    
	    map.put("accessKeyId", response.getCredentials().getAccessKeyId());
	    map.put("accessKeySecret", response.getCredentials().getAccessKeySecret());
	    map.put("securityToken", response.getCredentials().getSecurityToken());
	    map.put("expiration", response.getCredentials().getExpiration());
	    map.put("requestId", response.getRequestId());
	    return map;
		      
	  
  }
	
	public static void main(String[] args) {
		// 只有 RAM用户（子账号）才能调用 AssumeRole 接口
	    // AssumeRole API 请求参数: RoleArn, RoleSessionName, Policy, and DurationSeconds
	    // RoleArn 需要在 RAM 控制台上获取
	    String roleArn = "acs:ram::1793257590020474:role/oss";
	    // RoleSessionName 是临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
	    // 但是注意RoleSessionName的长度和规则，不要有空格，只能有'-' '_' 字母和数字等字符
	    // 具体规则请参考API文档中的格式要求
	    String roleSessionName = "oss-001";
	    // 如何定制你的policy?
	    String policy = "{\n" +
	            "    \"Version\": \"1\", \n" +
	            "    \"Statement\": [\n" +
	            "        {\n" +
	            "            \"Action\": \"oss:*\" , \n" +
	            "            \"Resource\": \"*\",  \n" +
	            "            \"Effect\": \"Allow\"\n" +
	            "        }\n" +
	            "    ]\n" +
	            "}";
	    // 此处必须为 HTTPS
	    ProtocolType protocolType = ProtocolType.HTTPS;
	    try {
		      final AssumeRoleResponse response = assumeRole(accessKeyId, accessKeySecret,
		              roleArn, roleSessionName, policy, protocolType);
		      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		      formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		     
		      try {
				System.out.println("Expiration: " + formatter.parse(response.getCredentials().getExpiration()).toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		      System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
		      System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
		      System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
	    } catch (ClientException e) {
		      System.out.println("Failed to get a token.");
		      System.out.println("Error code: " + e.getErrCode());
		      System.out.println("Error message: " + e.getErrMsg());
	    }
		
	}
	

}
