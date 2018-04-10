package one.show.struc.activity;

import java.util.List;

import one.show.common.JacksonUtil;
import one.show.common.TypeUtil;

public class ActivityShareConfig {
	//{"qq":5,"weibo":3,"weixin":4,"qzone":2,"maxPerUser":18,"percentByTimezone":[20,30,50]}
	public static void main(String[] args) {
		String str = "{\"qq\":5,\"weibo\":3,\"weixin\":4,\"qzone\":2,\"maxPerDay\":200,\"maxPerUser\":[20,30,50],\"percentByTimezone\":[20,30,50]}";
		System.out.println(TypeUtil.typeToString("share", JacksonUtil.readJsonToObject(ActivityShareConfig.class, str)));
	}
	private int qq;
	private int weibo;
	private int qzone;
	private int weixin;
	private List<Integer> maxPerUser;
	private List<Integer> percentByTimezone;
	private int maxPerDay;
	
	public int getQq() {
		return qq;
	}
	public void setQq(int qq) {
		this.qq = qq;
	}
	public int getWeibo() {
		return weibo;
	}
	public void setWeibo(int weibo) {
		this.weibo = weibo;
	}
	public int getQzone() {
		return qzone;
	}
	public void setQzone(int qzone) {
		this.qzone = qzone;
	}
	public int getWeixin() {
		return weixin;
	}
	public void setWeixin(int weixin) {
		this.weixin = weixin;
	}
	public List<Integer> getMaxPerUser() {
		return maxPerUser;
	}
	public void setMaxPerUser(List<Integer> maxPerUser) {
		this.maxPerUser = maxPerUser;
	}
	public List<Integer> getPercentByTimezone() {
		return percentByTimezone;
	}
	public void setPercentByTimezone(List<Integer> percentByTimezone) {
		this.percentByTimezone = percentByTimezone;
	}
	public int getMaxPerDay() {
		return maxPerDay;
	}
	public void setMaxPerDay(int maxPerDay) {
		this.maxPerDay = maxPerDay;
	}
	
	public int maxPerTimezone(int hour){
		int index = 0;
		if(hour>=3&&hour<12){
			index = 1;
		}else if(hour>=12&&hour<18){
			index = 2;
		}
		return maxPerDay*percentByTimezone.get(index)/100;
	}
	public int getRewardByChannel(String channel) {
		int reward = 0;
		if(channel.equals("qq")){
			reward = qq;
		}else if(channel.equals("weibo")){
			reward = weibo;
		}else if(channel.equals("weixin")){
			reward = weixin;
		}else if(channel.equals("qzone")){
			reward = qzone;
		}
		return reward;
	}

}
