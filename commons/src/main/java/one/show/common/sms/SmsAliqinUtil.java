package one.show.common.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * Created by hank on 1/22/17.
 */
public class SmsAliqinUtil{

    private static final Logger log = LoggerFactory.getLogger(SmsAliqinUtil.class);
    
    public static final String url = "http://gw.api.taobao.com/router/rest";
    public static final String appkey = "23613329";
    public static final String secret = "f38f4d200b8531904d850bf4f7f9f4f3";


    // todo 切换生产环境
    public static boolean sendMessage(String templateId, String signName, String phoneNumber, String params) {
    	TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
    	AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
    	req.setExtend( "" );
    	req.setSmsType( "normal" );
    	req.setSmsFreeSignName(signName);
    	req.setSmsParamString(params);
    	req.setRecNum(phoneNumber);
    	req.setSmsTemplateCode(templateId);
    	AlibabaAliqinFcSmsNumSendResponse rsp=null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			log.error("sms to "+phoneNumber+" error.error_code="+e.getErrCode()+",error_msg="+e.getErrMsg());
			return false;
		}
    	System.out.println(rsp.getBody());
    	log.info("aliqin.sms.response=" + rsp.getBody());
    	//{"alibaba_aliqin_fc_sms_num_send_response":{"result":{"err_code":"0","model":"105754052340^1107805227002","success":true},"request_id":"3acbu212ph5k"}}
    	boolean result = false;
    	if(rsp.getResult()==null){
    		log.error("sms to "+phoneNumber+" error.error_code="+rsp.getSubCode()+",error_msg="+rsp.getSubCode());
    	}else if(rsp.getResult().getSuccess()!=null){
    		result = rsp.getResult().getSuccess();
    	}
    	
        return result;
    }
}
