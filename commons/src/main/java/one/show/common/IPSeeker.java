package one.show.common;


import org.apache.log4j.Logger;


public class IPSeeker {

	private static Logger log = Logger.getLogger(IPSeeker.class);

	private IpSearcher ipSearcher;

	private String defaultIpmsg = Province.OTHER_NETTYPE + "," + Province.OTHER;

	public IpSearcher getIpgeter() {
		return new IpSearcherImpl();
	}

	public void setIpSearcher(IpSearcher ipSearcher) {
		this.ipSearcher = ipSearcher;
	}

	public boolean flushIpList() {
		if (ipSearcher.reload() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @param ip
	 * @return
	 */
	public String getCountryCodeORAreaCode(String ip) {
		// 先检查cache中是否已经包含有这个ip的结果，没有再搜索文件
		try {
			String ipmsg = "";//DispatcherCacheManager.getIpInfo(ip);
			if (ipmsg != null) {
				return ipmsg;
			}
			long t3 = System.currentTimeMillis();
			ipmsg = ipSearcher.search(ip);
			long t4 = System.currentTimeMillis();
			if (t4 - t3 > 0) {
				log.error("from File get IP cost time:" + (t4 - t3));
			}
			if (ipmsg == null || ipmsg.equals("")) {
				log.error("ipmsg:" + ipmsg + "ip:" + ip);
				ipmsg = defaultIpmsg;
			}
			//DispatcherCacheManager.putIpInfo(ip, ipmsg);
			return ipmsg;
		} catch (Exception e) {
			log.error(e + ";ip=" + ip);
			e.printStackTrace();
			return defaultIpmsg;
		}
	}
}

