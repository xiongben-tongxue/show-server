
package one.show.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import weibo4j.Tags;

/**
 * @author Haliaeetus leucocephalus  2017年2月16日 下午4:50:48
 *
 * 
 */

public class ListConvert<S, T> {

	public List<T> convert(List<S> sList, Class<T> clazz){
		
		if (sList == null){
			return null;
		}
		
		List<T> tList = new ArrayList<T>();
		for (S s : sList) {
			if(s==null){
				tList.add(null);
				continue;
			}
			try {
				T t = clazz.newInstance();
				BeanUtils.copyProperties(s, t);
				tList.add(t);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return tList;
	}
	
	
}


