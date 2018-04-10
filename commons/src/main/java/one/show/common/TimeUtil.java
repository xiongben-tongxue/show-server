package one.show.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Haliaeetus leucocephalus on 18/1/17.
 */
public class TimeUtil {
	private final static int[] dayArr = new int[] { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };  
	private final static String[] constellationArr = new String[] { "摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座" };  
	  
	public static String getConstellation(int month, int day) {  
	    return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];  
	}
	
	public static String getConstellation(String dateString) {
		if (StringUtils.isEmpty(dateString)){
			return "";
		}
		try {
			Date date = parseDate(dateString, "yyyy-MM-dd");
			return getConstellation(date);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getConstellation(Date date) {
		if (date == null) {
	        return "";
	    }
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int month = cal.get(Calendar.MONTH)+1;
	    int day = cal.get(Calendar.DAY_OF_MONTH);
		return getConstellation(month,day);
	}
	

    /**
     * 解析日期的通用方法
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date d = null;
        try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return d;
    }
	
    public static int getSecondsTillMidnight() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        int delta = (int) ((c.getTimeInMillis() - System.currentTimeMillis()) / 1000);
        return delta > 0 ? delta : 86400;
    }
    
    public static String hmsTime(Integer time) {
    	if (time == null || time == 0){
			return "--";
		}
    	int hour = time / 3600;
    	int minute = time % 3600 / 60;
    	int second = time % 3600 % 60;
    	
    	String hourStr = hour<10 ? ("0"+hour) : hour+"";
    	String minuteStr = minute<10 ? ("0"+minute) : minute+"";
    	String secondStr = second<10 ? ("0"+second) : second+"";
    	return hourStr+":"+minuteStr+":"+secondStr;
    }

    public static int getCurrentTimestamp() {
        return (int) (System.currentTimeMillis() / 1000);
    }
    
    public static int getSecondsByString(String dateStr){
    	return getSecondsByString(dateStr,"yyyyMMddHHmmss");
    }
    
    public static int getSecondsByString(String dateStr,String pattern){
    	SimpleDateFormat sdf=new SimpleDateFormat(pattern);
    	Date date = new Date();
    	try {
    		date = sdf.parse(dateStr);
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	return (int) (date.getTime()/1000);
    }

	/**
	 * 将转化为秒数的日期，转化为给定pattern格式的字符串
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String intDate2String(Integer time, String pattern) {
		if (time == null || time == 0){
			return "--";
		}
	    DateFormat format = new SimpleDateFormat(pattern);
	    Calendar c = Calendar.getInstance();
	    c.setTimeInMillis(time * 1000l);
	    return format.format(c.getTime());
	}
	
	/**
	 * 获取日期是一周中的第几天
	 * @param date
	 * @return
	 */
	public static Integer getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
	}
	
	
	/**
	 * 获取日期是一个月中第几天
	 * @param date
	 * @return
	 */
	public static Integer getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();

        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
	}
	
	public static Integer getHour(Date date) {
		Calendar c = Calendar.getInstance();
		
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

    /**
     * 将日期转化为整数的形式，如20120525
     * @param date
     * @return
     */
    public static Integer dateToIntegerDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(date);
        return Integer.parseInt(str);
    }
	
    public static int getTimestampDaysBefore(int days) {
    	return getCurrentTimestamp() - days * 86400;
    }
    
    /** 
     * 获取xxxx-xx-xx的日 
     * @param d 
     * @return 
     */  
    public static int getDay(Date d)  
    {  
        Calendar now = Calendar.getInstance(TimeZone.getDefault());  
        now.setTime(d);  
        return now.get(Calendar.DAY_OF_MONTH);  
    }  
      
    /** 
     * 获取月份，1-12月 
     * @param d 
     * @return 
     */  
    public static int getMonth(Date d)  
    {  
        Calendar now = Calendar.getInstance(TimeZone.getDefault());  
        now.setTime(d);  
        return now.get(Calendar.MONTH) + 1;  
    }  
      
    /** 
     * 获取19xx,20xx形式的年 
     * @param d 
     * @return 
     */  
    public static int getYear(Date d)  
    {  
        Calendar now = Calendar.getInstance(TimeZone.getDefault());  
        now.setTime(d);  
        return now.get(Calendar.YEAR);  
    } 
    

	public static boolean isSameDay(int t1, int t2) {
		return isSameDay(new Date(t1*1000L), new Date(t2*1000L));
	}

    /** 
     * 判断是否是同一天
     * @param d1
     * @param d2
     * @return
     */
	public static boolean isSameDay(Date d1, Date d2) {
		if(d1==null || d2==null){
			return false;
		}
		if(getYear(d1)==getYear(d2)&&getMonth(d1)==getMonth(d2)&&getDay(d1)==getDay(d2)){
			return true;
		}
		return false;
	}
	
	/**
	 * 格式化
	 * 
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String parseDate(Date date, String parsePatterns) {
		SimpleDateFormat sdf = new SimpleDateFormat(parsePatterns);
		return sdf.format(date);
	}
	

	/**
	 *  - 时间显示为24 时制
		- 1分钟内，时间显示为“刚刚”
		- 超过1分钟，小于60分钟，时间显示格式为“X分种前”
		- 超过60分钟， 小于当日 ( 自然日 )00:00，时间显示为“16:02” 
		- 超过(含)00:00时，历史消息显示为“昨天 16:02”
		- 发送时间超过一个自然日时，历史消息显示为“2017/6/8 16:02”
	 * @param now
	 * @param last
	 * @return
	 */
	public static String timeDiff(int now, int last) {
		if(last==0||now==0){
			return "";
		}
		//当日结束时间
		String today = intDate2String(last, "yyyy-MM-dd");
		int todayEnd = getSecondsByString(today+" 23:59:59","yyyy-MM-dd HH:mm:ss");
		
		String year = intDate2String(last, "yyyy");
		int yearEnd = getSecondsByString(year+"-12-31 23:59:59","yyyy-MM-dd HH:mm:ss");
		
		int diff = now - last;
		if (diff < 60) {
			return   "刚刚";
		} else if (diff >= 60 && diff < 3600) {
			return diff / 60 + "分钟前";
		} else if (diff >= 3600 && now <= todayEnd) {
			return intDate2String(last , "HH:mm");
		} else if (now > todayEnd && diff / (3600 * 24) < 1){
			return "昨天 "+ intDate2String(last , "HH:mm");
		}else if (now <= yearEnd){
			return intDate2String(last , "MM-dd HH:mm");
		}else {
			return intDate2String(last , "yyyy-MM-dd HH:mm");
		}
	}
	
	/**
     * 获取某个时间的0:0:0
     * @param d
     * @return
     */
    public static Date getStartTimeOfDay(int time) {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());  
        cal.setTime(new Date(time*1000L));
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.HOUR_OF_DAY,0);
        return cal.getTime();
    }
    
    /**
     * 获得当天还剩余的时间，单位：秒
     * @return
     */
    public static int getCurrentDayRemainTime() {
    	Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    long diff = cal.getTimeInMillis() - System.currentTimeMillis();
	    
	    return (int)(diff/1000);
    }
    
    public static int getDiffDayBetweenDates(int time1,int time2){
    	Date date1 = getStartTimeOfDay(time1);
    	Date date2 = getStartTimeOfDay(time2);
    	return Math.abs((int)((date2.getTime()-date1.getTime())/(24*3600000)));
    }
    
    public static int getConstellationMatch(String key) {
		if (key.equals("摩羯座_摩羯座")){
			return 88;
		}
		if (key.equals("摩羯座_水瓶座")){
			return 74;
		}
		if (key.equals("摩羯座_双鱼座")){
			return 77;
		}
		if (key.equals("摩羯座_白羊座")){
			return 43;
		}
		if (key.equals("摩羯座_金牛座")){
			return 97;
		}
		if (key.equals("摩羯座_双子座")){
			return 70;
		}
		if (key.equals("摩羯座_巨蟹座")){
			return 80;
		}
		if (key.equals("摩羯座_狮子座")){
			return 59;
		}
		if (key.equals("摩羯座_处女座")){
			return 92;
		}
		if (key.equals("摩羯座_天秤座")){
			return 51;
		}
		if (key.equals("摩羯座_天蝎座")){
			return 85;
		}
		if (key.equals("摩羯座_射手座")){
			return 64;
		}
		if (key.equals("水瓶座_摩羯座")){
			return 69;
		}
		if (key.equals("水瓶座_水瓶座")){
			return 87;
		}
		if (key.equals("水瓶座_双鱼座")){
			return 60;
		}
		if (key.equals("水瓶座_白羊座")){
			return 72;
		}
		if (key.equals("水瓶座_金牛座")){
			return 41;
		}
		if (key.equals("水瓶座_双子座")){
			return 91;
		}
		if (key.equals("水瓶座_巨蟹座")){
			return 58;
		}
		if (key.equals("水瓶座_狮子座")){
			return 78;
		}
		if (key.equals("水瓶座_处女座")){
			return 64;
		}
		if (key.equals("水瓶座_天秤座")){
			return 96;
		}
		if (key.equals("水瓶座_天蝎座")){
			return 51;
		}
		if (key.equals("水瓶座_射手座")){
			return 82;
		}
		if (key.equals("双鱼座_摩羯座")){
			return 82;
		}
		if (key.equals("双鱼座_水瓶座")){
			return 69;
		}
		if (key.equals("双鱼座_双鱼座")){
			return 88;
		}
		if (key.equals("双鱼座_白羊座")){
			return 71;
		}
		if (key.equals("双鱼座_金牛座")){
			return 78;
		}
		if (key.equals("双鱼座_双子座")){
			return 46;
		}
		if (key.equals("双鱼座_巨蟹座")){
			return 93;
		}
		if (key.equals("双鱼座_狮子座")){
			return 61;
		}
		if (key.equals("双鱼座_处女座")){
			return 65;
		}
		if (key.equals("双鱼座_天秤座")){
			return 74;
		}
		if (key.equals("双鱼座_天蝎座")){
			return 99;
		}
		if (key.equals("双鱼座_射手座")){
			return 54;
		}
		if (key.equals("白羊座_摩羯座")){
			return 58;
		}
		if (key.equals("白羊座_水瓶座")){
			return 88;
		}
		if (key.equals("白羊座_双鱼座")){
			return 79;
		}
		if (key.equals("白羊座_白羊座")){
			return 90;
		}
		if (key.equals("白羊座_金牛座")){
			return 75;
		}
		if (key.equals("白羊座_双子座")){
			return 82;
		}
		if (key.equals("白羊座_巨蟹座")){
			return 47;
		}
		if (key.equals("白羊座_狮子座")){
			return 94;
		}
		if (key.equals("白羊座_处女座")){
			return 65;
		}
		if (key.equals("白羊座_天秤座")){
			return 85;
		}
		if (key.equals("白羊座_天蝎座")){
			return 70;
		}
		if (key.equals("白羊座_射手座")){
			return 99;
		}
		if (key.equals("金牛座_摩羯座")){
			return 97;
		}
		if (key.equals("金牛座_水瓶座")){
			return 41;
		}
		if (key.equals("金牛座_双鱼座")){
			return 78;
		}
		if (key.equals("金牛座_白羊座")){
			return 75;
		}
		if (key.equals("金牛座_金牛座")){
			return 88;
		}
		if (key.equals("金牛座_双子座")){
			return 76;
		}
		if (key.equals("金牛座_巨蟹座")){
			return 82;
		}
		if (key.equals("金牛座_狮子座")){
			return 56;
		}
		if (key.equals("金牛座_处女座")){
			return 91;
		}
		if (key.equals("金牛座_天秤座")){
			return 74;
		}
		if (key.equals("金牛座_天蝎座")){
			return 80;
		}
		if (key.equals("金牛座_射手座")){
			return 70;
		}
		if (key.equals("双子座_摩羯座")){
			return 64;
		}
		if (key.equals("双子座_水瓶座")){
			return 99;
		}
		if (key.equals("双子座_双鱼座")){
			return 48;
		}
		if (key.equals("双子座_白羊座")){
			return 79;
		}
		if (key.equals("双子座_金牛座")){
			return 76;
		}
		if (key.equals("双子座_双子座")){
			return 89;
		}
		if (key.equals("双子座_巨蟹座")){
			return 71;
		}
		if (key.equals("双子座_狮子座")){
			return 81;
		}
		if (key.equals("双子座_处女座")){
			return 57;
		}
		if (key.equals("双子座_天秤座")){
			return 93;
		}
		if (key.equals("双子座_天蝎座")){
			return 69;
		}
		if (key.equals("双子座_射手座")){
			return 86;
		}
		if (key.equals("巨蟹座_摩羯座")){
			return 87;
		}
		if (key.equals("巨蟹座_水瓶座")){
			return 74;
		}
		if (key.equals("巨蟹座_双鱼座")){
			return 97;
		}
		if (key.equals("巨蟹座_白羊座")){
			return 52;
		}
		if (key.equals("巨蟹座_金牛座")){
			return 82;
		}
		if (key.equals("巨蟹座_双子座")){
			return 78;
		}
		if (key.equals("巨蟹座_巨蟹座")){
			return 89;
		}
		if (key.equals("巨蟹座_狮子座")){
			return 61;
		}
		if (key.equals("巨蟹座_处女座")){
			return 84;
		}
		if (key.equals("巨蟹座_天秤座")){
			return 66;
		}
		if (key.equals("巨蟹座_天蝎座")){
			return 92;
		}
		if (key.equals("巨蟹座_射手座")){
			return 70;
		}
		if (key.equals("狮子座_摩羯座")){
			return 77;
		}
		if (key.equals("狮子座_水瓶座")){
			return 84;
		}
		if (key.equals("狮子座_双鱼座")){
			return 62;
		}
		if (key.equals("狮子座_白羊座")){
			return 97;
		}
		if (key.equals("狮子座_金牛座")){
			return 56;
		}
		if (key.equals("狮子座_双子座")){
			return 79;
		}
		if (key.equals("狮子座_巨蟹座")){
			return 69;
		}
		if (key.equals("狮子座_狮子座")){
			return 87;
		}
		if (key.equals("狮子座_处女座")){
			return 72;
		}
		if (key.equals("狮子座_天秤座")){
			return 81;
		}
		if (key.equals("狮子座_天蝎座")){
			return 45;
		}
		if (key.equals("狮子座_射手座")){
			return 92;
		}
		if (key.equals("处女座_摩羯座")){
			return 95;
		}
		if (key.equals("处女座_水瓶座")){
			return 55;
		}
		if (key.equals("处女座_双鱼座")){
			return 84;
		}
		if (key.equals("处女座_白羊座")){
			return 61;
		}
		if (key.equals("处女座_金牛座")){
			return 91;
		}
		if (key.equals("处女座_双子座")){
			return 76;
		}
		if (key.equals("处女座_巨蟹座")){
			return 88;
		}
		if (key.equals("处女座_狮子座")){
			return 66;
		}
		if (key.equals("处女座_处女座")){
			return 89;
		}
		if (key.equals("处女座_天秤座")){
			return 49;
		}
		if (key.equals("处女座_天蝎座")){
			return 81;
		}
		if (key.equals("处女座_射手座")){
			return 72;
		}
		if (key.equals("天秤座_摩羯座")){
			return 47;
		}
		if (key.equals("天秤座_水瓶座")){
			return 95;
		}
		if (key.equals("天秤座_双鱼座")){
			return 64;
		}
		if (key.equals("天秤座_白羊座")){
			return 85;
		}
		if (key.equals("天秤座_金牛座")){
			return 74;
		}
		if (key.equals("天秤座_双子座")){
			return 98;
		}
		if (key.equals("天秤座_巨蟹座")){
			return 58;
		}
		if (key.equals("天秤座_狮子座")){
			return 88;
		}
		if (key.equals("天秤座_处女座")){
			return 77;
		}
		if (key.equals("天秤座_天秤座")){
			return 90;
		}
		if (key.equals("天秤座_天蝎座")){
			return 71;
		}
		if (key.equals("天秤座_射手座")){
			return 80;
		}
		if (key.equals("天蝎座_摩羯座")){
			return 76;
		}
		if (key.equals("天蝎座_水瓶座")){
			return 57;
		}
		if (key.equals("天蝎座_双鱼座")){
			return 92;
		}
		if (key.equals("天蝎座_白羊座")){
			return 60;
		}
		if (key.equals("天蝎座_金牛座")){
			return 80;
		}
		if (key.equals("天蝎座_双子座")){
			return 68;
		}
		if (key.equals("天蝎座_巨蟹座")){
			return 97;
		}
		if (key.equals("天蝎座_狮子座")){
			return 65;
		}
		if (key.equals("天蝎座_处女座")){
			return 84;
		}
		if (key.equals("天蝎座_天秤座")){
			return 73;
		}
		if (key.equals("天蝎座_天蝎座")){
			return 87;
		}
		if (key.equals("天蝎座_射手座")){
			return 47;
		}
		if (key.equals("射手座_摩羯座")){
			return 75;
		}
		if (key.equals("射手座_水瓶座")){
			return 78;
		}
		if (key.equals("射手座_双鱼座")){
			return 44;
		}
		if (key.equals("射手座_白羊座")){
			return 92;
		}
		if (key.equals("射手座_金牛座")){
			return 70;
		}
		if (key.equals("射手座_双子座")){
			return 81;
		}
		if (key.equals("射手座_巨蟹座")){
			return 65;
		}
		if (key.equals("射手座_狮子座")){
			return 98;
		}
		if (key.equals("射手座_处女座")){
			return 58;
		}
		if (key.equals("射手座_天秤座")){
			return 86;
		}
		if (key.equals("射手座_天蝎座")){
			return 68;
		}
		if (key.equals("射手座_射手座")){
			return 89;
		}
		return 85;

    }
    
	public static void main(String[] args) {
		System.out.println(intDate2String(getCurrentTimestamp(), "MM月dd日"));
//		System.out.println(getConstellation("2038-1-31"));
		System.out.println(timeDiff(getCurrentTimestamp(), getSecondsByString("2016-06-07 21:55:00","yyyy-MM-dd HH:mm:ss")));
		System.out.println(getStartTimeOfDay((int)(System.currentTimeMillis()/1000)));
		
		 Calendar c = Calendar.getInstance();
	        SimpleDateFormat dsf = new SimpleDateFormat("yyyy-MM-dd");
	        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String beginTime = dsf.format(new Date());
	        try {
				c.setTime(sf.parse(beginTime + " 00:00:00"));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        String startTimeOfDay = String.valueOf(c.getTimeInMillis() / 1000);
	        System.out.println(startTimeOfDay);
	
	}

}
