package one.show.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

/**
 * @author wanglei
 * 调用方式如：LocaleUtil.getString(LOCALE_BASE_NAME.MESS.getName(), "Chinese", "system_error")
 */
public class LocaleUtil {
	private static final Logger log = LoggerFactory.getLogger(LocaleUtil.class);
	
	public static final List<Map<String,String>> LANGUAGES = new ArrayList<Map<String,String>>();
	
	static {
		LANGUAGES.add(ImmutableMap.of("name","English","code","English"));
		LANGUAGES.add(ImmutableMap.of("name","简体中文","code","Chinese"));
	}
	
	public static String getString(String baseName,String language,String key) {
		Locale myLocale = null;
		try {
			if (language.equalsIgnoreCase("English")) {
				myLocale = Locale.US;
			} else if (language.equalsIgnoreCase("Chinese")) {
				myLocale = Locale.CHINA;
			}
			
			ResourceBundle bundle = ResourceBundle.getBundle(baseName , myLocale);
			return bundle.getString(key);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "";
	}
	
	/**
	 * 多语言配置，baseName枚举
	 */
	public enum LOCALE_BASE_NAME{
		MESS("mess",1), //系统消息
		PUSH("push",2), //push消息
		RESOURCE("resource",3); //资源地址，如图片等

		private String name;
		private int index;

		private LOCALE_BASE_NAME(String name,int index){
			this.name = name;
			this.index = index;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}
	
	
	public static void main(String[] args) {
		Locale[] localeList = Locale.getAvailableLocales();
		//遍历数组的每个元素，依次获取所支持的国家和语言
		for (int i = 0; i < localeList.length ; i++ ) {
			//打印出所支持的国家和语言
			System.out.println(localeList[i].getDisplayCountry() + "=" + localeList[i].getCountry()+ " " + localeList[i].getDisplayLanguage() + "=" + localeList[i].getLanguage());
		}
		System.out.println(LocaleUtil.getString(LOCALE_BASE_NAME.MESS.getName(), "English", "error_2019"));
	}
}
