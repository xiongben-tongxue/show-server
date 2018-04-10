package one.show.user.domain;

import java.io.Serializable;

public class NickNameUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 886417717938432581L;
	private String nickName;
	private long uid;
	
	private int createTime;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public int getCreateTime() {
		return createTime;
	}
	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}
	
	
	
}
