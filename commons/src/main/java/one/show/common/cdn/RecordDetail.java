package one.show.common.cdn;

public class RecordDetail{
	private String key;
	private String url;
	private float duration;
	private String hash;
	private int fsize;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public double getDuration() {
		return duration;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public int getFsize() {
		return fsize;
	}
	public void setFsize(int fsize) {
		this.fsize = fsize;
	}
	public void setDuration(float duration) {
		this.duration = duration;
	}
}