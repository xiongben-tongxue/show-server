package one.show.user.domain;

import java.io.Serializable;

public class PopularUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3610423045956983005L;

	private long popularNo;
	
	private long uid;
	
	private int createTime;

	

	public long getPopularNo() {
		return popularNo;
	}

	public void setPopularNo(long popularNo) {
		this.popularNo = popularNo;
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
