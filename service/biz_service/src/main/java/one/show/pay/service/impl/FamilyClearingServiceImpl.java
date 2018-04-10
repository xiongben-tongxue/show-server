
package one.show.pay.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.pay.dao.FamilyClearingMapper;
import one.show.pay.dao.FamilyMemberClearingMapper;
import one.show.pay.domain.FamilyClearing;
import one.show.pay.domain.FamilyMemberClearing;
import one.show.pay.domain.ReturnList;
import one.show.pay.service.FamilyClearingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Haliaeetus leucocephalus  2016年9月5日 下午7:30:57
 *
 * 
 */
@Component
public class FamilyClearingServiceImpl implements FamilyClearingService {

	private static final Logger log = LoggerFactory.getLogger(FamilyClearingServiceImpl.class);
	
	@Autowired
	public FamilyClearingMapper familyClearingMapper;
	
	@Autowired
	public FamilyMemberClearingMapper familyMemberClearingMapper;
	
	@Override
	public void addFamilyClearing(FamilyClearing familyClearing) throws ServiceException{
		try {
			familyClearingMapper.insertFamilyClearing(familyClearing);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public void addFamilyMemberClearing(FamilyMemberClearing familyMemberClearing) throws ServiceException{
		try {
			familyMemberClearingMapper.insertFamilyMemberClearing(familyMemberClearing);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ReturnList<FamilyClearing> getFamilyClearingList(
			Map<String, String> paramMap, Integer start, Integer count)
			throws ServiceException {

		ReturnList<FamilyClearing> familyClearingList = new ReturnList<FamilyClearing>();
        try {
        	 List<FamilyClearing> list = familyClearingMapper.getFamilyClearingList(paramMap, start, count);
        	
        	 familyClearingList.count = familyClearingMapper.getFamilyClearingListCount(paramMap);
        	 familyClearingList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return familyClearingList;
        
	}

	@Override
	public ReturnList<FamilyMemberClearing> getFamilyMemberClearingListByCidAndFamilyId(
			Integer cid, Long familyId, Integer start, Integer count)
			throws ServiceException {
		
		ReturnList<FamilyMemberClearing> familyMemberClearingList = new ReturnList<FamilyMemberClearing>();
        try {
        	 List<FamilyMemberClearing> list = familyMemberClearingMapper.findFamilyMemberClearingListByCidAndFamilyId(cid, familyId, start, count);
        	
        	 familyMemberClearingList.count = familyMemberClearingMapper.findFamilyMemberClearingListCountByCidAndFamilyId(cid, familyId);
        	 familyMemberClearingList.objects = list;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return familyMemberClearingList;
	
	}

	@Override
	public FamilyClearing getFamilyClearingById(long id) {
		return familyClearingMapper.getFamilyClearingById(id);
	}

	@Override
	public void updateFamilyClearing(long id, Map<String, String> updateContent) {
		familyClearingMapper.updateFamilyClearing(id,updateContent);
	}

}


