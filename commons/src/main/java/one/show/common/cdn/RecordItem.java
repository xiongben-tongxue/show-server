package one.show.common.cdn;

import java.util.List;

public class RecordItem{
	private String persistentId;
	private String streamname;
	private String ops;
	private String bucket;
    private int code;
    private String desc;
    private String error;
    private List<String> keys;
    private List<String> urls;
    private RecordDetail detail;
    
	public String getPersistentId() {
		return persistentId;
	}
	public void setPersistentId(String persistentId) {
		this.persistentId = persistentId;
	}
	public String getStreamname() {
		return streamname;
	}
	public void setStreamname(String streamname) {
		this.streamname = streamname;
	}
	public String getOps() {
		return ops;
	}
	public void setOps(String ops) {
		this.ops = ops;
	}
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<String> getKeys() {
		return keys;
	}
	public void setKeys(List<String> keys) {
		this.keys = keys;
	}
	public List<String> getUrls() {
		return urls;
	}
	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
	public RecordDetail getDetail() {
		return detail;
	}
	public void setDetail(RecordDetail detail) {
		this.detail = detail;
	}
}
