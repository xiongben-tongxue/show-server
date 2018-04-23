package one.show.user.thrift.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import one.show.common.Constant.POPULAR_NO_STATUS;
import one.show.common.TimeUtil;
import one.show.common.exception.ServerException;
import one.show.common.exception.ServiceException;
import one.show.user.domain.BlackList;
import one.show.user.domain.Contact;
import one.show.user.domain.Forbidden;
import one.show.user.domain.Robot;
import one.show.user.domain.Setting;
import one.show.user.domain.ThirdData;
import one.show.user.domain.User;
import one.show.user.domain.UserPopular;
import one.show.user.service.BlackListService;
import one.show.user.service.ContactService;
import one.show.user.service.ForbiddenService;
import one.show.user.service.RobotService;
import one.show.user.service.RoomAdminService;
import one.show.user.service.SettingService;
import one.show.user.service.ThirdDataService;
import one.show.user.service.UserPopularService;
import one.show.user.service.UserService;
import one.show.user.thrift.iface.UserServiceProxy.Iface;
import one.show.user.thrift.view.BlackListView;
import one.show.user.thrift.view.ContactView;
import one.show.user.thrift.view.RobotView;
import one.show.user.thrift.view.SettingView;
import one.show.user.thrift.view.ThirdDataView;
import one.show.user.thrift.view.UserForbiddenView;
import one.show.user.thrift.view.UserPopularNoView;
import one.show.user.thrift.view.UserView;
import one.show.user.thrift.view.WhiteListView;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userServiceProxyImpl")
public class UserServiceProxyImpl implements Iface{
	
	private static final Logger log = LoggerFactory.getLogger("UserServiceProxyImpl");
	@Autowired
	private UserService userService;
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ThirdDataService thirdDataService;
	@Autowired
	private ForbiddenService forbiddenService;
	@Autowired
	private BlackListService blackListService;
	@Autowired
	private RoomAdminService roomAdminService;
	@Autowired
	private UserPopularService userPopularService;
	@Autowired
	private RobotService robotService;
	@Override
	public UserView findUserById(long id) throws TException {
		try {
			User user = userService.findUserById(id);
			
			UserView userView = new UserView();
			
			if (user != null){
				BeanUtils.copyProperties(user, userView);
				return userView;
			}
			
		} catch (Exception e) {
			throw new TException(e);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see one.show.user.thrift.iface.UserServiceProxy.Iface#findContactListByUid(long)
	 */
	@Override
	public List<ContactView> findContactListByUid(String uid)
			throws TException {
		
		List<ContactView> viewList = null;
		List<Contact> list = contactService.findListByUid(uid);
		if (list != null){
			viewList = new ArrayList<ContactView>();
			for(Contact phoneBook : list){
				ContactView contactView = new ContactView();
				BeanUtils.copyProperties(phoneBook, contactView);
				viewList.add(contactView);
			}
		}
		return viewList;
	}
	
	@Override
	public void insertContactList(List<ContactView> contactViewList)
			throws TException {
 		List<Contact> list = new ArrayList<Contact>();
 		for (ContactView contactView : contactViewList) {
 			Contact contact = new Contact();
 			BeanUtils.copyProperties(contactView, contact);
 			list.add(contact);
		}
		try {
			contactService.batchSave(list);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see one.show.user.thrift.iface.UserServiceProxy.Iface#findThridDataByTidAndType(long, java.lang.String)
	 */
	@Override
	public ThirdDataView findThirdDataByTidAndType(String tid, String type) throws TException {
		try {
			ThirdData thirdData = thirdDataService.findByTidAndType(tid, type);

			ThirdDataView thirdDataView = new ThirdDataView();

			if (thirdData != null){
				BeanUtils.copyProperties(thirdData, thirdDataView);
				return thirdDataView;
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return null;

	}

	@Override
	public void updateUserById(long id, Map<String, String> updateContent) throws TException {
		try {
			 userService.updateUserById(id, updateContent);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void insertUser(UserView userview) throws ServerException, TException {
		if(userview!=null){
			int now = TimeUtil.getCurrentTimestamp();
			try {
				//用户所持有靓号列表
				UserPopular userPopular = new UserPopular();
				userPopular.setUid(userview.getId());
				userPopular.setPopularNo(userview.getPopularNo());
				userPopular.setStatus(POPULAR_NO_STATUS.USEING.ordinal());
				userPopular.setCreateTime(now);
				userPopularService.save(userPopular);
				
				User user = new User();
				BeanUtils.copyProperties(userview, user);
				userService.instertUser(user);
				
				
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				throw new ServerException(e.getMessage());
			}
			
	}
	
		
	}

	@Override
	public List<ThirdDataView> findThirdDataListByUid(long uid) throws TException {
		
		try {
			List<ThirdData> thirdDataList=thirdDataService.findThirdDataListByUid(uid);
			if(thirdDataList!=null && thirdDataList.size()!=0){
				List<ThirdDataView> thirdDataViewList = new ArrayList<ThirdDataView>();
				for(ThirdData tb: thirdDataList){
					ThirdDataView thirdBindView = new ThirdDataView();
					BeanUtils.copyProperties(tb, thirdBindView);
					thirdDataViewList.add(thirdBindView);
				}
				return thirdDataViewList;
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		
		return null;
	}

	@Override
	public void deleteThirdData(String tid, String type) throws TException {
		try {
			thirdDataService.deleteThirdData(tid, type);
		} catch (Exception e) {
			throw new TException(e);
		}
	}


	@Override
	public int saveThirdData(ThirdDataView thirdDataView) throws TException {
		try {
			ThirdData thirdData = new ThirdData();
			BeanUtils.copyProperties(thirdDataView, thirdData);
			return thirdDataService.save(thirdData);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public void updateThirdData(String tid, String type, Map<String,String> map) throws TException {
		try {
			thirdDataService.updateThridData(tid,type, map);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}


	/* (non-Javadoc)
	 * @see one.show.user.thrift.iface.UserServiceProxy.Iface#findUserListByIds(java.util.List)
	 */
	@Override
	public List<UserView> findUserListByIds(List<Long> ids) throws TException {
		List<User> userList = userService.findUserListByIds(ids);
		if (userList != null){
			return (List<UserView>) CollectionUtils.collect(userList, new Transformer<User, UserView>() {
				@Override
				public UserView transform(User user) {
					if (user == null){
						return new UserView();
					}
					UserView userView = new UserView();
					BeanUtils.copyProperties(user, userView);
					return userView;
				}
			});
		}
		return null;
		
	}

	@Override
	public ThirdDataView findThirdDataByUidAndType(long uid, String type)
			throws TException {
		
		ThirdDataView thirdDataView = null;
		try {
			ThirdData thirdData = thirdDataService.findThirdDataByUidAndType(uid, type);
			if(thirdData!=null){
				thirdDataView = new ThirdDataView();
				BeanUtils.copyProperties(thirdData, thirdDataView);
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return thirdDataView;
	}

	

	@Override
	public List<UserForbiddenView> findForbiddenListByUid(long uid)
			throws TException {
		List<UserForbiddenView> forbiddenViewList = null;
		try {
			List<Forbidden> forbiddenList = forbiddenService.findForbiddenListByUid(uid);
			if (forbiddenList!=null) {
				forbiddenViewList = new ArrayList<UserForbiddenView>();
				for (Forbidden forbidden : forbiddenList) {
					try {
						UserForbiddenView forbiddenView = new UserForbiddenView();
						BeanUtils.copyProperties(forbidden, forbiddenView);
						forbiddenViewList.add(forbiddenView);
					} catch (Exception e) {
						log.error(e.getMessage(),e);
					}
				}
			}
			
			return forbiddenViewList;
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}
	
	@Override
	public List<Integer> getForbiddenActionListByUid(long uid) throws TException{
		try {
			return forbiddenService.getForbiddenActionListByUid(uid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}



	@Override
	public int add2BlackList(long currentUser, long target_userid) throws TException {
		try {
			return blackListService.save(currentUser, target_userid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void removeFromBlackList(long currentUser, long target_userid) throws TException {
		try {
			blackListService.remove(currentUser, target_userid);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	@Override
	public List<Long> findBlackList(long currentUser, int cursor, int count) throws TException {
		return blackListService.findBlackList(currentUser, cursor, count);
	}


	@Override
	public void saveForbiddenView(UserForbiddenView forbiddenView) throws ServerException, TException {

		try {
			if(forbiddenView!=null){
				Forbidden forbidden = new Forbidden();
				BeanUtils.copyProperties(forbiddenView,forbidden);
				forbiddenService.saveForbidden(forbidden);
			}
		} catch (Exception e) {
			throw new ServerException(e.getMessage());
		}
	}

	@Override
	public void deleteForbiddenByUid(long uid) throws TException {
		try {
			forbiddenService.deleteForbiddenByUid(uid);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void deleteForbiddenByUidAndAction(long uid, int action)
			throws TException {
		try {
			forbiddenService.deleteForbiddenByUidAndAction(uid, action);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}

	@Override
	public List<BlackListView> findBlackListByUid(long uid, int cursor,
			int count) throws TException {
		List<BlackListView> list = null;
		try {
			List<BlackList> blackList = blackListService.findBlackListByUid(uid, cursor, count);
			if(blackList!=null){
				list = new ArrayList<BlackListView>();
				for(BlackList blacklist:blackList){
					BlackListView BlackListView = new BlackListView();
					BeanUtils.copyProperties(blacklist, BlackListView);
					list.add(BlackListView);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return list;
	}

	@Override
	public List<BlackListView> findBlackListByTid(long uid, int cursor,
			int count) throws TException {
		List<BlackListView> list = null;
		try {
			List<BlackList> blackList = blackListService.findBlackListByTid(uid, cursor, count);
			if(blackList!=null){
				list = new ArrayList<BlackListView>();
				for(BlackList blacklist:blackList){
					BlackListView BlackListView = new BlackListView();
					BeanUtils.copyProperties(blacklist, BlackListView);
					list.add(BlackListView);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return list;
	}
	
	@Override
	public int findCountBlackListByUid(long uid) throws TException {
		int count = 0;
		try {
			count = blackListService.findCountBlackListByUid(uid);
		} catch (Exception e) {
			throw new TException(e);
		}
		return count;
	}
	
	@Override
	public int findCountBlackListByTid(long uid) throws TException {
		int count = 0;
		try {
			count = blackListService.findCountBlackListByTid(uid);
		} catch (Exception e) {
			throw new TException(e);
		}
		return count;
	}

	@Override
	public boolean isAllow(String nickName) throws TException {
		boolean flag = false;
		try {
			flag = userService.isAllow(nickName);
		} catch (Exception e) {
			throw new TException(e);
		}
		return flag;
	}

	@Override
	public boolean userIsForbidden(long uid, int action) throws TException {
		boolean flag = false;
		try {
			flag = forbiddenService.findForbiddenByActionAndUid(uid, action);
		} catch (Exception e) {
			throw new TException();
		}
		return flag;
	}

	@Override
	public UserView findUserByNickName(String nickName) throws TException {
		UserView userView = null;
		try {
			User user = userService.findUserByNickName(nickName);
			userView = new UserView();
			
			if (user != null){
				BeanUtils.copyProperties(user, userView);
				return userView;
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return userView;
	}

	@Override
	public List<Boolean> findForbiddenListByUidsAndAction(List<Long> uids,
			int action) throws TException {
		try {
			return forbiddenService.findForbiddenListByUidsAndAction(uids, action);
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}
	
	@Override
	public SettingView getSettingByUid(String uid){
		Setting setting = settingService.getSettingByUid(uid);
		SettingView settingView = null;
		if (setting!=null) {
			settingView = new SettingView();
			BeanUtils.copyProperties(setting, settingView);
		}
		return settingView;
	}


	@Override
	public void setSwitch(String uid,Map<String, String> paramMap) throws TException {
			try {
				settingService.setSwitch(uid,paramMap);
			} catch (Exception e) {
				throw new TException(e);
			}
	}

	@Override
	public List<UserView> findAllUserList(int start, int count) throws TException {
		
		List<UserView> list = null;
		try {
			List<User> users = userService.findAllUsersList(start, count);
			if(users!=null){
				list = new ArrayList<UserView>();
				for(User user:users){
					UserView userView = new UserView();
					BeanUtils.copyProperties(user, userView);
					list.add(userView);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return list;
	}

	@Override
	public UserView findUserByPid(long pid) throws TException {

		UserView userView = null;
		try {
			User user = userService.findUserByPopularNo(pid);
			
			userView = new UserView();
			
			if (user != null){
				BeanUtils.copyProperties(user, userView);
				return userView;
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return userView;
	}

	@Override
	public void saveRoomAdmin(long uid, long tid) throws TException {

		try {
			roomAdminService.save(uid, tid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public void deleteRoomAdmin(long uid, long tid) throws TException {

		try {
			roomAdminService.delete(uid, tid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<Long> findRoomAdminListByUid(long uid, int cursor,
			int count) throws TException {

		try {
			return roomAdminService.findListByUid(uid, cursor, count);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public int findRoomAdminCount(long uid) throws TException {
		try {
			return roomAdminService.findCountByUid(uid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public boolean isRoomAdmin(long uid, long tid) throws TException {

		try {
			return roomAdminService.isRoomAdmin(uid, tid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public boolean isBlack(long uid, long tid) throws TException {
		try {
			return blackListService.isBlack(uid, tid);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	
	}

	@Override
	public void saveUserPopularNo(UserPopularNoView userPopularNoView)
			throws TException {
		
		try {
			if(userPopularNoView!=null){
				UserPopular userPopular = new UserPopular();
				BeanUtils.copyProperties(userPopularNoView, userPopular);
				userPopularService.save(userPopular);
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void updateUserPopularNoStatus(long uid, long popularNo,
			int status) throws TException {
		try {
			userPopularService.updateStatus(uid, popularNo, status);
		} catch (ServiceException e) {
			throw new TException(e);
		}
		
	}

	@Override
	public void deleteUserPopularNo(long uid, long popularNo) throws TException {

		try {
			userPopularService.delete(uid, popularNo);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public List<UserPopularNoView> findUserPopularNoListByUid(long uid)
			throws TException {

		List<UserPopularNoView> list = null;
		try {
			List<UserPopular> userPopularList = userPopularService.findListByUid(uid);
			if(userPopularList!=null){
				list = new ArrayList<UserPopularNoView>();
				for(UserPopular userPopular:userPopularList){
					UserPopularNoView userPopularNoView = new UserPopularNoView();
					BeanUtils.copyProperties(userPopular, userPopularNoView);
					list.add(userPopularNoView);
				}
			}
		} catch (Exception e) {
			throw new TException(e);
		}
		return list;
	}

	@Override
	public List<RobotView> findAllRobot() throws TException {
		List<RobotView> robotViews = new ArrayList<RobotView>();
		
		List<Robot> list = null;
		try {
			list = robotService.findList();
		} catch (ServiceException e) {
			throw new TException(e);
		}
		if(list!=null&&list.size()>0){
			for(Robot r:list){
				RobotView rv = new RobotView();
				BeanUtils.copyProperties(r, rv);
				robotViews.add(rv);
			}
		}
		
		return robotViews;
	}

	@Override
	public void deleteUser(long id) throws TException {

		try {
			userService.deletetUser(id);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}

	@Override
	public int add2WhiteList(long uid, int time) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeFromWhiteList(long uid) throws TException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<WhiteListView> findWhiteList(int cursor, int count)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WhiteListView findWhiteListByUid(long uid) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerUser(UserView userView, ThirdDataView thirdDataView)
			throws TException {
		if(userView==null||thirdDataView==null){
			throw new TException("userView or thirdDataView is null");
		}
		User user = new User();
		BeanUtils.copyProperties(userView, user);
		ThirdData thirdData = new ThirdData();
		BeanUtils.copyProperties(thirdDataView, thirdData);
		try {
			userService.registerUser(user,thirdData);
		} catch (ServiceException e) {
			throw new TException(e);
		}
	}


}