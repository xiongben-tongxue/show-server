package one.show.user.domain;

import java.io.Serializable;

import one.show.common.Constant;

/**
 * 第三方绑定
 * @author Haliaeetus leucocephalus
 *
 */
public class ThirdBind implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1560156885053195091L;
	
	//绑定id
	private long bid;
	
	//用户id
	private long uid;
	
	//第三方登陆id
	private String tid;
	
	//是否公开
	private int publicStatus=Constant.THIRD_BIND_PUBLIC_STATUS.YES.ordinal();
	
	//类型
	private String type;
	
	private int createTime;

	public ThirdBind(ThirdData thirdData) {
		uid = thirdData.getUid();
		tid = thirdData.getTid();
		type = thirdData.getType();
		publicStatus = thirdData.getPublicStatus();
	}

	public long getBid() {
		return bid;
	}

	public ThirdBind() {
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPublicStatus() {
		return publicStatus;
	}

	public void setPublicStatus(int publicStatus) {
		this.publicStatus = publicStatus;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	
	
	
}
