package one.show.common;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.PropertyResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author weizhangbj8024 
 * Jun 6, 2012
 */
public class Loader {
	
	private static Logger logger = LoggerFactory.getLogger(Loader.class);
	
	private PropertyResourceBundle bundle;
	
	private static Loader loader = new Loader();

	private Loader(){
		
	
		try{
			InputStream in = Loader.class.getResourceAsStream("/ApplicationResources.properties");
			InputStreamReader r;
			r = new InputStreamReader(in, "UTF-8");
			bundle = new PropertyResourceBundle(r);
			r.close();
			
		}catch(Exception e){
			logger.error("Exception:"+e.getMessage(), e);
		}
		
	
	}
	
	public final static Loader getInstance() {
		return loader;
	}
	
	
	public String getProps(String key){
		return bundle.getString(key);
	}

}
