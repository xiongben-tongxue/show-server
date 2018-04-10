package one.show.common;

import java.math.BigDecimal;

import one.show.common.Constant.CDN;

import org.apache.commons.lang3.StringUtils;

/**
 * 对返回值进行一些适配处理 Created by Haliaeetus leucocephalus on 18/1/8.
 */
public class Adapter {

	/**
	 * 如果头像不存在就返回nobody头像 需要把头像url中直接使用ali-oss域名的替换为aliv。
	 * 
	 * @param profile_img
	 * @return
	 */
	public static String getAvatar(String profile_img) {
		if (StringUtils.isBlank(profile_img) || profile_img.equals("null")) {
			return Loader.getInstance().getProps("default.profileimg");
		}
		return profile_img;
	}

	public static String getShareAddr4Live(Long liveId){
	      
	       return "http://www.xiubi.com/share/live/"+liveId;
	    		   
	}
	
	public static String getShareAddr4Vod(Long liveId){
	      
	       return "http://www.xiubi.com/share/vod/"+liveId;
	    		   
	}
	
	public static String getVideoAddr4Mp4(String streamName, Integer cdnType){
	       String addr = "";
	       if (CDN.values()[cdnType] == CDN.WANGSU){
	    	   addr = "http://video.souyu.tv/show/"+streamName+".mp4";
	       }
	       return addr;
	    		   
	    }
	
	public static String getStreamAddr4Flv(String streamName, Integer cdnType){
       String addr = "";
       if (CDN.values()[cdnType] == CDN.WANGSU){
    	   addr = "http://pull.souyu.tv/show/"+streamName+".flv";
       }
       return addr;
    		   
    }
	
	
	public static String getRtmpAddr(String streamName, Integer cdnType){
	       String addr = "";
	       if (CDN.values()[cdnType] == CDN.WANGSU){
	    	   //wsRecord=off/on
	    	   addr = "rtmp://push.souyu.tv/show/"+streamName+"?wsRecord=on";
	       }
	       return addr;
	    		   
	}
	
	public static double getReceive(double spend){
		return spend;
	    		   
	}
	
	public static double getRmb(double showCoin){
		return showCoin*1000;
	    		   
	}
	
	public static boolean isEffectiveUser(int liveDuration, int liveEffectiveDays){
		//每月直播时长大于等于40小时，直播有效天数大于等于18
		if (liveDuration >= 3600*40 && liveEffectiveDays >= 18){
			return true;
		}else{
			return false;
		}
	}
	
	public static double getFamilyRmbIncomeRatio(int effectiveUsers){
		double familyIncomRatio = 0;
		/*
		if (effectiveUsers < 10){
    		familyIncomRatio = 0.01;
    	}else if (effectiveUsers >= 10 && effectiveUsers < 20){
    		familyIncomRatio = 0.01125;
    	}else if (effectiveUsers >= 20 && effectiveUsers < 30){
    		familyIncomRatio = 0.01225;
    	}else if (effectiveUsers >= 30){
    		familyIncomRatio = 0.01375;
    	}
    	*/
		familyIncomRatio = 0.3;
		return familyIncomRatio;
	}
	

	public static String getColor(int index) {
		String[] strArr = new String[] { "f9f3ff", "fcf7ed", "f0f2f1",
				"f4ece9", "ebf3f7", "f9efef", "f7ebf2", "e7f7f5", "e8eef7",
				"f9f9ef" };
		String color = "";
		if (index > 0) {
			int attrIndex = index % 9;
			color = strArr[attrIndex];
		} else {
			color = strArr[0];
		}

		return color;
	}

	public static String stringWrap(String s) {
		return s == null ? "" : s;
	}

	public static Integer isVip(int vipExpire) {
		return vipExpire > System.currentTimeMillis() / 1000 ? 1 : 0;
	}

	public static String getVideoLen(double len) {
		int minute = (int) Math.floor(len / 60);
		int second = (int) len % 60;
		return String.format("%d\'%02d\"", minute, second);
	}

	public static String getVideoURL(String filePath) {
		if (filePath == null || "".equals(filePath.trim()))
			return null;
		return filePath + ".m3u8";
	}

	public static String getScreenshot(String defaultImg, String filePath) {
		if (StringUtils.isBlank(defaultImg) || !defaultImg.endsWith(".jpg")) {
			return filePath + ".jpg";
		}
		return defaultImg;
	}

	public static int isOnRank(int rankExpire) {
		return rankExpire > System.currentTimeMillis() / 1000 ? 1 : 0;
	}

	public static void main(String[] args) {
		System.out.println(18 % 9);
		System.out.println(getScreenshot("13213.jpg", "sdadsd"));
	}
}
