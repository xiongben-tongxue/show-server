/**
 * 
 */
package one.show.common.datasource;

import one.show.common.DESUtil;

/**
 * @author Haliaeetus leucocephalus 2018年1月13日 下午9:42:55
 * 
 */
public class DSConf {

	private String host;
	private Integer port;
	private String userName;
	private String password;
	private Integer maxPoolSize;
	private Integer minPoolSize;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public Integer getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(Integer minPoolSize) {
		this.minPoolSize = minPoolSize;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "host="+host+", port="+port+", username="+userName+",maxpoolsize="+maxPoolSize+", minpoolsize="+minPoolSize;
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(new DESUtil().decrypt("a6fc04251932b93b"));
	}
	
	

}
