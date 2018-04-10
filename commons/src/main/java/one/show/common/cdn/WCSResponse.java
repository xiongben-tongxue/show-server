
package one.show.common.cdn;

import one.show.common.Adapter;

/**
 * @author Haliaeetus leucocephalus  2017年1月17日 下午8:24:28
 *
 * 
 */

public class WCSResponse {
	
	private String url;
	
	private String persistentId;
	
	private String hash;
	
	private String result;
	
	private long fsize;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPersistentId() {
		return persistentId;
	}

	public void setPersistentId(String persistentId) {
		this.persistentId = persistentId;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getResult() {
		return result;
	}

	public long getFsize() {
		return fsize;
	}

	public void setFsize(long fsize) {
		this.fsize = fsize;
	}

	public void setResult(String result) {
		this.result = result;
		
		String[] strArray = result.split("&");
		for (String str:strArray) {
			String[] tempArray = str.split("=");
			switch (tempArray[0]) {
			case "url":
				this.url = tempArray[1];
				break;
			case "fsize":
				this.fsize = Integer.parseInt(tempArray[1]);
				break;
			case "persistentId":
				if(tempArray.length==2){
					persistentId = tempArray[1];
				}
				break;
				
			default:
				break;
			}
		}
	}
	
	

}


