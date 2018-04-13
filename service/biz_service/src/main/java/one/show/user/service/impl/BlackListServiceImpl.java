package one.show.user.service.impl;

import java.util.List;

import one.show.common.TimeUtil;
import one.show.common.exception.ServiceException;
import one.show.user.dao.BlackListMapper;
import one.show.user.domain.BlackList;
import one.show.user.domain.RoomAdmin;
import one.show.user.service.BlackListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangwei on 15/8/19.
 */
@Component("blackListService")
public class BlackListServiceImpl implements BlackListService {
    @Autowired
    private BlackListMapper blackListMapper;

    @Override
    public int save(long currentUser, long target_userid) {
        return blackListMapper.save(currentUser, target_userid, TimeUtil.getCurrentTimestamp());
    }


	@Override
    public void remove(long currentUser, long target_userid) throws ServiceException{
		try {
			 blackListMapper.remove(currentUser, target_userid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
        
    }

    @Override
    public List<Long> findBlackList(long currentUser, int cursor, int count) {
        return blackListMapper.findBlackList(currentUser, cursor, count);
    }



	@Override
	public List<BlackList> findBlackListByUid(long uid, int cursor,
			int count) throws ServiceException {
		List<BlackList> list = null;
		try {
			list = blackListMapper.findBlackListByUid(uid, cursor, count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return list;
	}

	@Override
	public List<BlackList> findBlackListByTid(long uid, int cursor,
			int count) throws ServiceException {
		List<BlackList> list = null;
		try {
			list = blackListMapper.findBlackListByTid(uid, cursor, count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return list;
	}

	@Override
	public Integer findCountBlackListByUid(long uid) throws ServiceException {
		int count =0;
		try {
			count=blackListMapper.findCountBlackListByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return count;
	}
	
	@Override
	public Integer findCountBlackListByTid(long tid) throws ServiceException {
		int count =0;
		try {
			count=blackListMapper.findCountBlackListByTid(tid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return count;
	}


	@Override
	public boolean isBlack(long uid, long tid) throws ServiceException {

		try {
			BlackList black = blackListMapper.find(uid, tid);
			if (black != null)
				return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		return false;
	}
}
