
package one.show.user.thrift.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import one.show.common.exception.ServiceException;
import one.show.user.domain.Family;
import one.show.user.domain.FamilyMember;
import one.show.user.domain.ReturnList;
import one.show.user.service.FamilyService;

import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.show.user.thrift.iface.FamilyServiceProxy.Iface;
import one.show.user.thrift.view.FamilyListView;
import one.show.user.thrift.view.FamilyMemberListView;
import one.show.user.thrift.view.FamilyMemberView;
import one.show.user.thrift.view.FamilyView;

/**
 * @author zhangwei  2016年8月10日 下午7:23:53
 *
 * 
 */
@Component("familyServiceProxyImpl")
public class FamilyServiceProxyImpl implements Iface {

	@Autowired
	private FamilyService familyService;
	
	@Override
	public FamilyView findFamilyById(long familyId) throws TException {
		try {
			Family family = familyService.findFamilyById(familyId);
			
			FamilyView familyView = new FamilyView();
			
			if (family != null){
				BeanUtils.copyProperties(family, familyView);
				return familyView;
			}
			
		} catch (Exception e) {
			throw new TException(e);
		}
		return null;
	
	}

	@Override
	public long saveFamily(FamilyView familyView) throws TException {

		try {
			Family family = new Family();
			BeanUtils.copyProperties(familyView, family);
			return familyService.insertFamily(family);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateFamilyById(long familyId, Map<String, String> updateContent)
			throws TException {

		try {
			familyService.updateFamilyById(familyId, updateContent);
		} catch (Exception e) {
			throw new TException(e);
		}
	}


	@Override
	public void saveFamilyMember(FamilyMemberView familyMemberView)
			throws TException {

		try {
			if(familyMemberView!=null){
				FamilyMember familyMember = new FamilyMember();
				BeanUtils.copyProperties(familyMemberView, familyMember);
				familyService.insertFamilyMember(familyMember);
			}
			
		} catch (Exception e) {
			throw new TException(e);
		}
	}


	@Override
	public void deleteFamilyMemberById(long familyId, long userId)
			throws TException {

		try {
			familyService.deleteFamilyMemberById(familyId, userId);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public FamilyListView findFamilyList(Map<String, String> condition,
			int start, int count) throws TException {

		FamilyListView familyListView = new FamilyListView();
		try {
			ReturnList<Family> returnList = familyService.findFamilyList(condition, start, count);
			familyListView.count = returnList.count;
			
			familyListView.familyViewList = new ArrayList<FamilyView>();
			
			if (returnList != null && returnList.objects != null){
				for(Family family : returnList.objects){
					FamilyView familyView = new FamilyView();
					BeanUtils.copyProperties(family, familyView);
					familyListView.familyViewList.add(familyView);
				}
			}
			
		} catch (Exception e) {
			throw new TException(e);
		}
		
		return familyListView;
	}

	@Override
	public FamilyMemberListView findFamilyMemberListByFamilyId(long familyId,
			int start, int count) throws TException {

		FamilyMemberListView familyMemberListView = new FamilyMemberListView();
		try {
			ReturnList<FamilyMember> returnList = familyService.findFamilyMemberListByFamilyId(familyId, start, count);
			familyMemberListView.count = returnList.count;
			
			familyMemberListView.familyMemberList = new ArrayList<FamilyMemberView>();
			
			if (returnList != null && returnList.objects != null){
				for(FamilyMember familyMember : returnList.objects){
					FamilyMemberView familyMemberView = new FamilyMemberView();
					BeanUtils.copyProperties(familyMember, familyMemberView);
					familyMemberListView.familyMemberList.add(familyMemberView);
				}
			}
			
		} catch (Exception e) {
			throw new TException(e);
		}
		
		return familyMemberListView;
	}

	@Override
	public List<FamilyView> findByIds(List<Long> ids) throws TException {
		List<FamilyView> list = new ArrayList<FamilyView>();
		try {
			List<Family> familys = familyService.findByIds(ids);
			if(familys!=null){
				for(Family f:familys){
					FamilyView fv = new FamilyView();
					BeanUtils.copyProperties(f, fv);
					list.add(fv);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return list;
	}
	
}


