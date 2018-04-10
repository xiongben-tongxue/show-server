/**
 * 
 */
package one.show.common.globalconf;


/**
 * @author Haliaeetus leucocephalus 2018年3月29日 上午12:33:07
 *
 */
public class GlobalConf {
	
	
	private int selectorThreads;
	
	private int workerThreads;
	
	//0测试 1正式 
	private int env = 1; 
	
	//0网宿, 1阿里云
	private int cdnType = 0;
	
	private int sign = 1;
	

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public int getCdnType() {
		return cdnType;
	}

	public void setCdnType(int cdnType) {
		this.cdnType = cdnType;
	}

	public int getSelectorThreads() {
		return selectorThreads;
	}

	public void setSelectorThreads(int selectorThreads) {
		this.selectorThreads = selectorThreads;
	}

	public int getWorkerThreads() {
		return workerThreads;
	}

	public void setWorkerThreads(int workerThreads) {
		this.workerThreads = workerThreads;
	}

	public int getEnv() {
		return env;
	}

	public void setEnv(int env) {
		this.env = env;
	}
	
	
	
	
	

}


