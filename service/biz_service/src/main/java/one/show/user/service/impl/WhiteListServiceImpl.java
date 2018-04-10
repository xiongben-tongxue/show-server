
package one.show.user.service.impl;

import java.util.List;

import one.show.common.exception.ServiceException;
import one.show.user.dao.WhiteListMapper;
import one.show.user.domain.WhiteList;
import one.show.user.service.WhiteListService;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Haliaeetus leucocephalus  2016年11月11日 下午7:07:43
 *
 * 
 */

public class WhiteListServiceImpl implements WhiteListService {

	@Autowired
    private WhiteListMapper whiteListMapper;
	
	
	@Override
	public void save(long uid, int time) throws ServiceException {

		try {
			whiteListMapper.save(uid, time);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void remove(long uid) throws ServiceException {

		try {
			whiteListMapper.remove(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<WhiteList> findWhiteList(int cursor, int count) throws ServiceException{

		
		try {
			return whiteListMapper.findWhiteList(cursor, count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public WhiteList findWhiteListByUid(long uid) throws ServiceException {

		try {
			return whiteListMapper.findWhiteListByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	
	}

}


