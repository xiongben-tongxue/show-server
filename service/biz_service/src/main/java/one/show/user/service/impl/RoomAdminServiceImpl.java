package one.show.user.service.impl;

import java.util.List;

import one.show.common.TimeUtil;
import one.show.common.exception.ServiceException;
import one.show.user.dao.RoomAdminMapper;
import one.show.user.domain.BlackList;
import one.show.user.domain.RoomAdmin;
import one.show.user.service.RoomAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangwei on 15/8/19.
 */
@Component
public class RoomAdminServiceImpl implements RoomAdminService {
    @Autowired
    private RoomAdminMapper roomAdminMapper;

    @Override
    public void save(long uid, long tid)throws ServiceException {
    	try {
    		roomAdminMapper.save(uid, tid, TimeUtil.getCurrentTimestamp());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
        
    }


	@Override
    public void delete(long uid, long tid) throws ServiceException{
		try {
			roomAdminMapper.delete(uid, tid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
        
    }



	@Override
	public List<Long> findListByUid(long uid, int cursor,
			int count) throws ServiceException {
		try {
			return roomAdminMapper.findAdminListByUid(uid, cursor, count);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	
	@Override
	public Integer findCountByUid(long uid) throws ServiceException {
		int count =0;
		try {
			count=roomAdminMapper.findCountAdminListByUid(uid);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		return count;
	}


	@Override
	public boolean isRoomAdmin(long uid, long tid) throws ServiceException{
		try {
			RoomAdmin admin = roomAdminMapper.find(uid, tid);
			if (admin != null)
				return true;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		return false;
	
	}
	
	
}
