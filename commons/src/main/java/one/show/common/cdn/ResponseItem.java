package one.show.common.cdn;

import java.io.Serializable;
import java.util.List;

public class ResponseItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cmd;
	
	private String code;
	
	private int fsize;
	
	private String url;
	
	private int costTime;
	
	private String desc;
	
	private String error;
	
	private String hash;
	
	private String key;
	
	private double duration;
	
	private int bit_rate;
	
	private String resolution;
	
	private List detail;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getFsize() {
		return fsize;
	}

	public void setFsize(int fsize) {
		this.fsize = fsize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCostTime() {
		return costTime;
	}

	public void setCostTime(int costTime) {
		this.costTime = costTime;
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public int getBit_rate() {
		return bit_rate;
	}

	public void setBit_rate(int bit_rate) {
		this.bit_rate = bit_rate;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public List getDetail() {
		return detail;
	}

	public void setDetail(List detail) {
		this.detail = detail;
	}
}
