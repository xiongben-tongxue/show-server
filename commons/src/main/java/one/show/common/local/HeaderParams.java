package one.show.common.local;

import java.io.Serializable;

public class HeaderParams implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1989623934379604714L;
	//MAC地址，仅对WIFI连接时有效。android独有
	private String mac;
	//IMEI串号, android独有
	private String im;
	//Idfa号，常用语广告推广, ios独有
	private String idfa;
	//Idfv号，ios独有
	private String idfv;
	//终端运营商  1移动  2联通  3电信
	private String op;
	//终端所处国家
	private String co;
	//屏幕分辨率
	private String sc;
	//操作系统名称
	private String os;
	//操作系统版本
	private String kernelVersion;
	//应用版本号
	private String appVersion;
	//应用版本名
	private String vn;
	//纬度
	private String latitude;
	//经度
	private String longitude;
	//经纬度对应的城市
	private String city;
	//设备id
	private String deviceId;
	
	//设备名称
	private String deviceName;
	//手机类型
	private String phoneType;
	//手机号
	private String phoneNum;
	//渠道号　
	private String channel;
	//推送ID
	private String pushId;
	//网络类型1：未知、 2：2g、 3：3g、 4：4g、 5：WIFI
	private String netType;
	//扩展字段
	private String tag;
	
	private String uid;
	
	private String explorer;
	
	private String ip;
	
	private String referer;
	
	private String language;
	
	

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getExplorer() {
		return explorer;
	}

	public void setExplorer(String explorer) {
		this.explorer = explorer;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}


	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}



	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getKernelVersion() {
		return kernelVersion;
	}

	public void setKernelVersion(String kernelVersion) {
		this.kernelVersion = kernelVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getPushId() {
		return pushId;
	}

	public void setPushId(String pushId) {
		this.pushId = pushId;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getIdfa() {
		return idfa;
	}

	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}

	public String getIdfv() {
		return idfv;
	}

	public void setIdfv(String idfv) {
		this.idfv = idfv;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getSc() {
		return sc;
	}

	public void setSc(String sc) {
		this.sc = sc;
	}

	public String getVn() {
		return vn;
	}

	public void setVn(String vn) {
		this.vn = vn;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	

}
