package one.show.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期计算相关工具类
 */
public class DateCalculateUtil {

    // 1分钟的毫秒数
    public static final long TIME_1MINUTE = 1 * 60 * 1000;
    // 2分钟的毫秒数
    public static final long TIME_2MINUTES = 2 * 60 * 1000;
    // 5分钟的毫秒数
    public static final long TIME_5MINUTES = 5 * 60 * 1000;
    // 15分钟的毫秒数
    public static final long TIME_15MINUTES = 15 * 60 * 1000;
    // 1小时的毫秒数
    public static final long TIME_1HOUR = 60 * 60 * 1000;
    // 1天的毫秒数
    public static final long TIME_1DAY = 24 * 60 * 60 * 1000;
    // 1周的毫秒数
    public static final long TIME_1WEEK = 7 * 24 * 60 * 60 * 1000;

    // public static Date getPrecWeek() {
    // Calendar c = Calendar.getInstance();
    // c.add(Calendar.WEEK_OF_YEAR, -1);
    // return c.getTime();
    // }
    //
    // public static Date getPrecMonth() {
    // Calendar c = Calendar.getInstance();
    // c.add(Calendar.MONTH, -1);
    // return c.getTime();
    // }

    /**
     * 获取昨天的日期
     * @return
     */
    public static Date getYesterday() {
        return getPastDays(new Date(), -1);
    }

    /**
     * 将Calendar的日期设置为0点0分0秒000
     * @param c
     */
    private static void setTimeBeg(Calendar c) {
        c.set(Calendar.AM_PM, Calendar.AM);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 将Calendar的日期设置为23点59分59秒999
     * @param c
     */
    private static void setTimeEnd(Calendar c) {
        c.set(Calendar.AM_PM, Calendar.PM);
        c.set(Calendar.HOUR, 11);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
    }

    /**
     * 获取给定日期，前后n天的日期
     * @param date
     * @param days
     * @return
     */
    public static Date getPastDays(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        setTimeBeg(c);
        c.set(Calendar.DATE, c.get(Calendar.DATE) + days);
        return c.getTime();
    }

    /**
     * 获取给定日期当月的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        return getFirstOrLastDayOfMonth(date, false);
    }

    /**
     * 获取给定日期当月的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        return getFirstOrLastDayOfMonth(date, true);
    }

    /**
     * 月第一天或最后一天
     * @param date
     * @param isLast
     * @return
     */
    private static Date getFirstOrLastDayOfMonth(Date date, Boolean isLast) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (isLast) {
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            setTimeEnd(c);
        } else {
            c.set(Calendar.DAY_OF_MONTH, 1);
            setTimeBeg(c);
        }
        return c.getTime();
    }

    /**
     * 获取给定日期所在周的第一天
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        return getFirstOrLastDayOfWeek(date, false);
    }

    /**
     * 获取给定日期所在周的最后一天
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        return getFirstOrLastDayOfWeek(date, true);
    }

    /**
     * 获取给定日期所在周第一天或最后一天
     * @param isLast
     * @return
     */
    private static Date getFirstOrLastDayOfWeek(Date date, Boolean isLast) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY); // 设置周一为每周的第一天
        c.setTime(date);
        if (isLast) {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 按照周日为最后一天
            setTimeEnd(c);
        } else {
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 默认周一为第一天
            setTimeBeg(c);
        }
        return c.getTime();
    }

    /**
     * 获取给定日期的早晚日期，0点0分0秒000，或者23点59分59秒999
     * @param date
     * @param isEnd
     * @return
     * @throws ParseException
     */
    public static Date toDateSecond(Date date, Boolean isEnd) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        if (isEnd) {
            setTimeEnd(c);
        } else {
            setTimeBeg(c);
        }

        return c.getTime();
    }

    /**
     * 获取给定日期的秒数，0点0分0秒000，或者23点59分59秒999
     * @param date
     * @param isEnd
     * @return
     * @throws ParseException
     */
    public static Integer toDaySecond(Date date, Boolean isEnd) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        if (isEnd) {
            setTimeEnd(c);
        } else {
            setTimeBeg(c);
        }

        return toSeconds(c);
    }

    /**
     * 将Calendar转化为秒数，UTC的毫秒数除以1000
     * @param c
     * @return
     */
    private static Integer toSeconds(Calendar c) {
        return Long.valueOf(c.getTimeInMillis() / 1000l).intValue();
    }

    /**
     * 把日期转换为秒数
     * @param date
     * @return
     */
    public static Integer toSeconds(Date date) {
        return Long.valueOf(date.getTime() / 1000l).intValue();
    }

    /**
     * 获取给定日期的秒数，0点0分0秒000，或者23点59分59秒999
     * @param date
     * @param isEnd
     * @return
     * @throws ParseException
     */
    public static Integer toDaySecond(String date, Boolean isEnd) throws ParseException {
        Date newDate = parseDate(date, "yyyy-MM-dd");
        return toDaySecond(newDate, isEnd);
    }

    public static Date secondToDate(Integer second) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(second * 1000l);
        return c.getTime();
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

    /**
     * 将整数形式的日期转化为日期
     * @param value
     * @return
     * @throws Exception
     */
    public static Date integerDayToDate(Integer value) throws ParseException {
        return parseDate(value.toString(), "yyyyMMdd");
    }
    
    /**
     * 将整数形式的时间转化为日期格式
     * @param value
     * @return
     * @throws Exception
     */
    public static Date integerDayToDate2(Integer value) throws ParseException {
        return parseDate(value.toString(), "yyyy-MM-dd HH:mm:ss");
    }

    // public static Date integerMonthToDate(Integer value) throws Exception {
    // return parseDate(value.toString() + "01", "yyyyMMdd");
    // }

    /**
     * 将日期的月份转化为整数的形式，如201205
     * @param date
     * @return
     */
    public static Integer dateToIntegerMonth(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String str = sdf.format(date);
        return Integer.parseInt(str);
    }
    
    /**
     * 将日期由Date转化为String
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(date);
        return str;
    }
    
    
    /**
     * 将时间由Date转化为String
     * @param date
     * @return
     */
    public static String dateToString3(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;
    }
    /**
     * 将日期由Date转化为String
     * @param date
     * @return
     */
    public static String dateToString2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String str = sdf.format(date);
        return str;
    }

    // /**
    // * 将整数形式的日期转化为日期
    // *
    // * @param date
    // * @return
    // */
    // public static Integer dateToIntegerWeek(Date date) {
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyyww");
    // String str = sdf.format(date);
    // return Integer.parseInt(str);
    // }
    /**
     * 获取上个月第一天0点0分0秒的秒数
     * @param date
     * @return
     */
    public static Integer getFirstSecendOfLastMonth(Date date) {
        Date firstDate = getFirstDayOfMonth(date);
        Calendar c = Calendar.getInstance();
        c.setTime(firstDate);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        return toSeconds(c);
    }

    /**
     * 获取上月最后一天23点59分59秒的秒数
     * @param date
     * @return
     */
    public static Integer getLastSecendOfLastMonth(Date date) {
        Date lastDate = getLastDayOfMonth(date);
        Calendar c = Calendar.getInstance();
        c.setTime(lastDate);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        return toSeconds(c);
    }

    /**
     * 获取传入参数月的最后一秒的秒数
     * @param yearMonth
     * @return
     */
    public static Integer getLastSecondOfMonth(Integer yearMonth) {
        Date date = new Date();
        date.setYear(yearMonth / 100);
        date.setMonth(yearMonth % 100 - 1);
        Date lastDate = getLastDayOfMonth(date);
        Calendar c = Calendar.getInstance();
        c.setTime(lastDate);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        return toSeconds(c);
    }

    /**
     * 获取当月第一天0点0分0秒的秒数
     * @param date
     * @return
     */
    public static Integer getFirstSecendOfMonth(Date date) {
        Date firstDate = getFirstDayOfMonth(date);
        Calendar c = Calendar.getInstance();
        c.setTime(firstDate);
        return toSeconds(c);
    }

    /**
     * 获取当月最后一天23点59分59秒的秒数
     * @param date
     * @return
     */
    public static Integer getLastSecendOfMonth(Date date) {
        Date lastDate = getLastDayOfMonth(date);
        Calendar c = Calendar.getInstance();
        c.setTime(lastDate);
        return toSeconds(c);
    }

    /**
     * 获取给定日期当月的最后一天
     * @return yyyyMMdd格式的字符串
     * @throws Exception
     */
    public static String getLastDayOfMonth(String date) throws ParseException {
        Date newDate = parseDate(date, "yyyyMMdd");
        Date lastDate = getLastDayOfMonth(newDate);
        return formatDate(lastDate, "yyyyMMdd");
    }

    /**
     * 没看懂
     * @param calendar
     * @return
     */
    public static String getLastMonth(Calendar calendar) {
        String date = "";
        calendar.add(Calendar.MONTH, -1); // 得到前一个月

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;

        if (month < 10) {
            date = year + "0" + month;
        } else {
            date = year + "" + month;
        }
        return date;
    }

    public static Integer WeekToSeconds(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyww");
            String str = sdf.format(date);
            Date d = sdf.parse(str);
            d.setTime(d.getTime() + TIME_1DAY * 1L);
            if (d.after(date))
                d.setTime(d.getTime() - TIME_1DAY * 7L);
            return (int) (d.getTime() / 1000l);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 解析日期的通用方法
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(date);
    }

    /**
     * 格式化日期的通用方法
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
    	if(date == null)
    		return "";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 获得当前日期
     * @return
     */
    public static String currentDate() {
        return formatDate(new Date(), "yyyyMMdd");
    }
    
    /**
     * 获得当前日期
     * @return
     */
    public static String currentDate(String pattern) {
        return formatDate(new Date(), pattern);
    }

    /**
     * 日期加减运算的通用方法
     * @param date
     * @param field
     * @param amount
     * @return
     */
    private static Date add(Date date, int field, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(field, amount);
        return c.getTime();
    }

    /**
     * 将转化为秒数的日期，转化为给定pattern格式的字符串
     * @param time
     * @param pattern
     * @return
     */
    public static String intDate2String(Integer time, String pattern) {
    	if (time == null || time <= 0){
    		return "-";
    	}
        DateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time * 1000l);
        return format.format(c.getTime());
    }
    
    public static int stringDate2int(String time, String pattern) {
    	Calendar c = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		try {
			c.setTime(sf.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int)(c.getTimeInMillis() / 1000);
    }

    // public static Integer getCurrentWeekOfYear() {
    // return getWeekOfYear(new Date());
    // }

    // private static Integer getWeekOfYear(Date date) {
    // Calendar cal = Calendar.getInstance();
    // cal.setTime(date);
    // int woy = cal.get(Calendar.WEEK_OF_YEAR);
    // int year = cal.get(Calendar.YEAR);
    // return year * 100 + woy;
    // }
    //
    // public static Integer getNextWeekOfYear(Integer woy) {
    // Calendar cal = Calendar.getInstance();
    // int year = woy / 100;
    // int w = woy % 100;
    // cal.set(Calendar.YEAR, year);
    // cal.set(Calendar.WEEK_OF_YEAR, w);
    // cal.add(Calendar.WEEK_OF_YEAR, 1);
    // return getWeekOfYear(cal.getTime());
    // }

    /**
     * 在给定日期加上n天
     * @param date
     * @param amount
     * @return
     */
    public static Date addDay(Date date, int amount) {
        return add(date, Calendar.DAY_OF_YEAR, amount);
    }

    /**
     * 在给定日期的月份上加上n月
     * @param date
     * @param amount
     * @return
     */
    public static Date addMonth(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    /**
     * 在给定日期的月份上加上n月
     * @param date
     * @param amount
     * @return
     */
    public static Date addMinutes(Date date, int amount) {
        return add(date, Calendar.MINUTE, amount);
    }


    /**
     * 将给定的yyyyMMdd字符串，按照给定的format重新格式化
     * @param date
     * @param format
     * @return
     * @throws Exception
     */
    public static String getDateByFormat(String date, String format) throws Exception {
        Date newDate = parseDate(date, "yyyyMMdd");
        return formatDate(newDate, format);
    }

    public static void main(String[] args) {
        System.out.println(getFirstSecendOfMonth(new Date()));
        System.out.println(getLastSecendOfMonth(new Date()));
    }

    /**
     * 将分钟转化为a小时b分钟格式的字符串
     * @param minute
     * @return
     */
    public static String minute2Hour(int minute) {
        int hour = minute / 60;
        int min = minute - (hour * 60);
        return hour + "时" + min + "分";
    }

    /**
     * 获取一天所在的星期，是一年中的第几周
     * @param date
     * @return
     */
    public static Integer getNumOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);

        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    public static Integer getCurrSeconds() {
        return Long.valueOf(System.currentTimeMillis() / 1000l).intValue();
    }
    
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException{    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
        return Integer.parseInt(String.valueOf(between_days));           
    }    
    
    /**
     * 获取当前日期是星期几<br>
     * 
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
