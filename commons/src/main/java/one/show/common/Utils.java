package one.show.common;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.zip.CRC32;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Haliaeetus leucocephalus on 15/10/13.
 */
public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);


    public static long string2numberHash(String input, int range) {
        CRC32 crc32 = new CRC32();
        crc32.update(input.getBytes());
        return crc32.getValue() % range;
    }


    public static long getCRC32(String input) {
        CRC32 crc32 = new CRC32();
        crc32.update(input.getBytes());
        return crc32.getValue();
    }
    
    public static int getByteLength(String input){
    	if (input == null){
    		return 0;
    	}
    	try {
			return input.getBytes("GB2312").length;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return 0 ;
    }
    
    public static String string2Unicode(String string) {
    	 
        StringBuffer unicode = new StringBuffer();
     
        for (int i = 0; i < string.length(); i++) {
     
            // 取出每一个字符
            char c = string.charAt(i);
     
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
     
        return unicode.toString();
    }
    
    public static String unicode2String(String unicode) {
    	 
        StringBuffer string = new StringBuffer();
     
        String[] hex = unicode.split("\\\\u");
     
        for (int i = 1; i < hex.length; i++) {
     
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
     
            // 追加成string
            string.append((char) data);
        }
     
        return string.toString();
    }
    
    public static int compareVersion(String v1, String v2) {
    	if (v1 == null){
    		return -1;
    	}
    	if (v2 == null){
    		return 1;
    	}
    	String[] array1 = v1.split("\\.");
    	String[] array2 = v2.split("\\.");
    	int length = array1.length>array2.length?array2.length:array1.length;
    	int result = 0;
    	for (int i = 0; i < length; i++) {
    		if(array1[i].equals(array2[i])){
    			continue;
    		}
    		try {
    			result = Integer.parseInt(array1[i])-Integer.parseInt(array2[i]);
			} catch (Exception e) {
			}
    		
    		break;
    	}
    	return result;
    }
    
    public static boolean exists(String URLName) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName)
			.openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
    public static long toGMT(Date date) {
    	   SimpleDateFormat format = new SimpleDateFormat(
    	     "yyyy-MM-dd HH:mm:ss");
    	   Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
    	   return cal.getTimeInMillis();
    	}
    
    public static boolean isJson(String content){
        try {
            JSONObject jsonStr= JSONObject.parseObject(content);
            return  true;
       } catch (Exception e) {
            return false;
      }
    }
    
    public static void main(String[] args) {
  		System.out.println(getCRC32("13295800961") % 100);
  		String str = "叶宝宝✨‮脸小的你了亲并‭";
  		System.out.println(string2Unicode(str));
  		System.out.println(string2Unicode(str).replaceAll("\\\\u202e", "").replaceAll("\\\\u202d", ""));
  		System.out.println(unicode2String(string2Unicode(str).replaceAll("\\\\u202e", "").replaceAll("\\\\u202d", "")));
  		System.out.println(getByteLength("jkikjkkidkikskjk"));
  		System.out.println(compareVersion("2.0.0.2", "2.0.0.1"));
  		
  		System.out.println(TimeUtil.intDate2String((int)(toGMT(new Date())/1000), "yyyy-MM-dd HH:mm:ss"));
   }

}
