package one.show.common.util;

public class PushVo {

	private int type;//3个人消息4官方消息
	
	private int pushType;//统计用
	
	private String content;
	
	private String title;
	
	private int redirectType;
	
	private long redirectId;
	
	private int topicType;
	
	private String pushImage;
	
	private String url;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPushType() {
		return pushType;
	}

	public void setPushType(int pushType) {
		this.pushType = pushType;
	}

	public int getRedirectType() {
		return redirectType;
	}

	public void setRedirectType(int redirectType) {
		this.redirectType = redirectType;
	}

	public long getRedirectId() {
		return redirectId;
	}

	public void setRedirectId(long redirectId) {
		this.redirectId = redirectId;
	}

	public int getTopicType() {
		return topicType;
	}

	public void setTopicType(int topicType) {
		this.topicType = topicType;
	}

	public String getPushImage() {
		return pushImage;
	}

	public void setPushImage(String pushImage) {
		this.pushImage = pushImage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
