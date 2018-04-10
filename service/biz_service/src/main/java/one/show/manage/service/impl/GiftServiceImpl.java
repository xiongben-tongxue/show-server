/**
 * 
 */
package one.show.manage.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.Constant.ADMIN_STATUS;
import one.show.common.exception.ServiceException;
import one.show.manage.dao.GiftMapper;
import one.show.manage.domain.Gift;
import one.show.manage.domain.GiftType;
import one.show.manage.domain.ReturnList;
import one.show.manage.service.GiftService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus 2018年3月8日 下午3:45:30
 *
 */
@Component
public class GiftServiceImpl implements GiftService {
	
	@Autowired
	private GiftMapper giftMapper;

	/* (non-Javadoc)
	 * @see one.show.manage.service.GiftService#findGiftList(java.lang.Integer, one.show.common.Constant.ADMIN_STATUS, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ReturnList<Gift> getGiftList(Integer giftType, ADMIN_STATUS status,
			Integer start, Integer count)throws ServiceException {

		ReturnList<Gift> giftList = new ReturnList<Gift>();
        try {
        	 List<Gift> list = giftMapper.findGiftList(giftType, status == null ? null :status.ordinal(), start, count);
        	
            giftList.count = giftMapper.findGiftListCount(giftType, status == null ? null :status.ordinal());
            giftList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return giftList;
        
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.GiftService#save(one.show.manage.domain.Gift)
	 */
	@Override
	public void save(Gift gift) throws ServiceException{
		try {
			giftMapper.save(gift);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.GiftService#delete(java.lang.Integer)
	 */
	@Override
	public void delete(Integer giftId) throws ServiceException{

		try {
			giftMapper.delete(giftId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.GiftService#getGiftTypeList(one.show.common.Constant.ADMIN_STATUS)
	 */
	@Override
	public List<GiftType> getGiftTypeList(ADMIN_STATUS status)throws ServiceException {
		try {
			return giftMapper.findGiftTypeList(status==null?null:status.ordinal());
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.GiftService#getGiftById(java.lang.Integer)
	 */
	@Override
	public Gift getGiftById(Integer giftId) throws ServiceException {
		try {
			return giftMapper.findGiftById(giftId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see one.show.manage.service.GiftService#update(java.util.Map)
	 */
	@Override
	public void update(Integer giftId, Map<String, String> updateContent)
			throws ServiceException {
		try {
			giftMapper.update(updateContent, giftId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

}


