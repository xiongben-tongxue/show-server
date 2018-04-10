
package one.show.manage.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.manage.dao.UserCDNMapper;
import one.show.manage.domain.ReturnList;
import one.show.manage.domain.UserCDN;
import one.show.manage.domain.Word;
import one.show.manage.service.UserCDNService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年6月27日 下午9:50:14
 *
 * 
 */
@Component
public class UserCDNServiceImpl implements UserCDNService {

	@Autowired
	UserCDNMapper userCDNMapper;
	
	@Override
	public ReturnList<UserCDN> findUserCDNList(Integer start, Integer count) throws ServiceException {

		ReturnList<UserCDN> userCdnList = new ReturnList<UserCDN>();
        try {
        	 List<UserCDN> list = userCDNMapper.findUserCDNList(start, count);
        	
            userCdnList.count = userCDNMapper.findUserCDNListCount();
            userCdnList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return userCdnList;
        
	}

	@Override
	public void save(UserCDN userCDN) throws ServiceException {
		try {
			userCDNMapper.saveUserCDN(userCDN);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void update(UserCDN userCDN) throws ServiceException {

		try {
			userCDNMapper.updateUserCDN(userCDN);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(Long uid) throws ServiceException {

		try {
			userCDNMapper.deleteUserCDN(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public UserCDN findUserCDNById(long uid) throws ServiceException {
		
		try {
			return userCDNMapper.findUserCDNByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


