/**
 * 
 */
package one.show.user.service;

import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.domain.ThirdData;

/**
 * @author Haliaeetus leucocephalus 2018年1月4日 下午8:28:38
 *
 */
public interface ThirdDataService {
	
	/**
	 * 获取第三方用户绑定信息
	 * @param tid
	 * @param type
	 * @return
	 */
	public ThirdData findByTidAndType(String tid, String type);

	/**
	 *
	 * @param thirdData
	 * @return -1 duplicate key
	 * @throws ServiceException
	 */
	public int save(ThirdData thirdData) throws ServiceException;
	
	
	public void updateThridData(String tid,String type, Map<String,String> map) throws ServiceException;
	
	public void deleteThirdData(String tid,String type) throws ServiceException;
	
}


