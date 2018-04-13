/**
 * 
 */
package one.show.user.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.dao.ThirdDataMapper;
import one.show.user.domain.ThirdData;
import one.show.user.service.ThirdDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

/**
 * @author zhangwei 2015年8月4日 下午9:06:55
 *
 */

@Component("thridDataService")
public class ThirdDataServiceImpl implements ThirdDataService {

	
	@Autowired
	private ThirdDataMapper thirdDataMapper;
	
	/* (non-Javadoc)
	 * @see one.show.user.service.ThridDataService#findByTidAndType(java.lang.Long, java.lang.String)
	 */
	@Override
	public ThirdData findByTidAndType(String tid, String type){
		// TODO Auto-generated method stub
		return thirdDataMapper.findByTidAndType(tid, type);
	}

	/* (non-Javadoc)
	 * @see one.show.user.service.ThridDataService#save(one.show.user.domain.ThridData)
	 */
	@Override
	public int save(ThirdData thirdData) throws ServiceException{
		try {
			int ret = thirdDataMapper.save(thirdData);
			if (ret < 1)
				return -1;
//			ret = (int) thirdBindMapper.save(new ThirdBind(thirdData));
//			if (ret < 1)
//				return -1;
			return ret;
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				return -1;
			}
			throw new ServiceException(e);
		}
	}


	@Override
	public void updateThridData(String tid,String type, Map<String,String> map) throws ServiceException{
		try {
			thirdDataMapper.updateThirdDataById(tid,type,map);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void deleteThirdData(String tid, String type) throws ServiceException{
		try {
			thirdDataMapper.deleteThirdData(tid, type);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<ThirdData> findThirdDataListByUid(long uid) throws ServiceException {
		try {
			return thirdDataMapper.findThirdDataListByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ThirdData findThirdDataByUidAndType(long uid, String type) throws ServiceException {
		try {
			return thirdDataMapper.findThirdDataByUidAndType(uid,type);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


