package one.show.common.pay;

import java.io.Serializable;

public class WXPrePayInfo  implements Serializable{
	
	private String partnerid;

	private String prepayid;
	
	private String noncestr;
	
	private String timestamp;
	
	private String sign;

	public WXPrePayInfo() {
		super();
	}

	public WXPrePayInfo(String partnerid,String prepay_id, String noncestr,String timestamp, String sign) {
		this.partnerid = partnerid;
		this.prepayid = prepay_id;
		this.noncestr = noncestr;
		this.timestamp = timestamp;
		this.sign = sign;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
