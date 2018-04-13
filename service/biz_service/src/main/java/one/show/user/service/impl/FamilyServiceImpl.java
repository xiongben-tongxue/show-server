
package one.show.user.service.impl;

import java.util.List;
import java.util.Map;

import one.show.common.TypeUtil;
import one.show.common.exception.ServiceException;
import one.show.user.dao.FamilyMapper;
import one.show.user.dao.FamilyMemberMapper;
import one.show.user.domain.Family;
import one.show.user.domain.FamilyMember;
import one.show.user.domain.ReturnList;
import one.show.user.service.FamilyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangwei  2016年8月10日 下午6:21:22
 *
 * 
 */
@Component
public class FamilyServiceImpl implements FamilyService {
	private static final Logger log = LoggerFactory.getLogger(FamilyServiceImpl.class);

	@Autowired
	private FamilyMapper familyMapper;
	
	@Autowired
	private FamilyMemberMapper familyMemberMapper;
	
	@Override
	public Family findFamilyById(long familyId) throws ServiceException {
		try {
			return familyMapper.findById(familyId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public long insertFamily(Family family) throws ServiceException {

		try {
			familyMapper.insertFamily(family);
			return family.getId();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateFamilyById(long familyId,
			Map<String, String> updateContent) throws ServiceException {

		try {
			familyMapper.updateById(familyId, updateContent);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ReturnList<Family> findFamilyList(Map<String, String> condition,
			int start, int count) throws ServiceException {

		ReturnList<Family> list = new ReturnList<Family>();
		try {
			List<Family> familyList = familyMapper.findFamilyList(condition, start, count);
			list.objects = familyList;
			list.count = familyMapper.findFamilyListCount(condition);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		return list;
		
	}

	@Override
	public FamilyMember findFamilyMemberById(long familyId, long userId)
			throws ServiceException {
		try {
			return familyMemberMapper.findById(familyId, userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ReturnList<FamilyMember> findFamilyMemberListByFamilyId(long familyId,int start, int count)
			throws ServiceException {
		
		ReturnList<FamilyMember> list = new ReturnList<FamilyMember>();
		try {
			List<FamilyMember> familyMemberList = familyMemberMapper.findListByFamilyId(familyId, start, count);
			list.objects = familyMemberList;
			list.count = familyMemberMapper.findCountByFamilyId(familyId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		return list;
		
	}

	@Override
	public void insertFamilyMember(FamilyMember member) throws ServiceException {

		try {
			familyMemberMapper.insertFamilyMember(member);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteFamilyMemberById(long familyId, long userId) throws ServiceException{

		try {
			familyMemberMapper.deleteById(familyId, userId);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Family> findByIds(List<Long> ids) throws ServiceException {
		try {
			return familyMapper.findByIds(ids);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}


